package CRUD;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* 
CRUD 2
*/

public class Solution {
    public static volatile List<Person> allPeople = new ArrayList<Person>();

    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) {

        switch (args[0]) {
            case "-c": synchronized (allPeople) {addPerson(args);}
                break;
            case "-u": synchronized (allPeople) {updatePerson(args);}
                break;
            case "-d": synchronized (allPeople) {logicDeletePerson(args);}
                break;
            case "-i": synchronized (allPeople) {consolPrintPerson(args);}
                break;
            default: synchronized (allPeople) {
                System.out.println("Неверный параметр!");}
        }
    }

    private static Date stringToDate(final String text) {

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private static void addPerson(String[] param) {
        int count = (param.length - 1)/3;
        for (int i = 0; i < count; i++) {
            if (param[2 + (3 * i)].contains("м")) {
                allPeople.add(Person.createMale(param[1 + (3 * i)], stringToDate(param[3 + (3 * i)])));
            }
            if (param[2 + (3 * i)].contains("ж")) {
                allPeople.add(Person.createFemale(param[1 + (3 * i)], stringToDate(param[3 + (3 * i)])));
            }
            System.out.println(allPeople.size() - 1);
        }
    }

    private static void updatePerson(String[] param) { //обновляет соответствующие данные людей с заданными id
        int count = (param.length - 1)/4;
        Person person = null;
        for (int i = 0; i < count; i++) {
            int updateIndex = Integer.parseInt(param[1 + (4 * i)]);
            person = allPeople.get(updateIndex);
            person.setName(param[2 + (4 * i)]);
            if (param[3 + (4 * i)].contains("м")) {
                person.setSex(Sex.MALE);
            }
            if (param[3 + (4 * i)].contains("ж")) {
                person.setSex(Sex.FEMALE);
            }
            person.setBirthDay(stringToDate(param[4 + (4 * i)]));
            allPeople.set(updateIndex, person);
        }
    }

    private static void logicDeletePerson(String[] param) {
        Person person = null;
        int count = (param.length - 1);
        for (int i = 0; i < count; i++) {
            int logicDeleteIndex = Integer.parseInt(param[1 + (1 * i)]);
            person = allPeople.get(logicDeleteIndex);
            person.setName(null);
            person.setSex(null);
            person.setBirthDay(null);
            allPeople.set(logicDeleteIndex, person);
        }

    }

    private static void consolPrintPerson(String[] param) {
        Person person = null;
        int count = (param.length - 1);
        for (int i = 0; i < count; i++) {
            int consolPrintIndex = Integer.parseInt(param[1 + (1 * i)]);
            person = allPeople.get(consolPrintIndex);
            String sex = null;
            String bd = null;
            if (person.getBirthDay() != null) {
                bd = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(person.getBirthDay());
            }
            if (person.getSex() != null) {
                if (person.getSex().equals(Sex.MALE)) sex = "м";
                if (person.getSex().equals(Sex.FEMALE)) sex = "ж";
            }
            System.out.println(person.getName() + " " + sex + " " + bd);
        }
    }
}
