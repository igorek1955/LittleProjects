package banking;

import java.util.*;

public class Account {

    InputSession inputSession;
    String cardNumber;
    static Database database = new Database();

    private Account(InputSession inputSession, String cardNumber) {
        this.cardNumber = cardNumber;
        this.inputSession = inputSession;
    }

    public void addIncome(double income) {
        database.addIncome(cardNumber, income);
        System.out.println("Income was added!");
    }


    public void doTransfer(String cardNumberIn, double amount) {
        String dbAnswer = database.doTransfer(this.cardNumber, cardNumberIn, amount);
        switch (dbAnswer) {
            case "card not found":
                System.out.println("Such a card does not exist.");
                break;
            case "success":
                System.out.println("Success!");
                break;
            case "not enough money":
                System.out.println("Not enough money!");
                break;
            default:
                System.out.println("There was some unexpected error");
                break;
        }
    }

    public void closeAccount() {
        database.closeAccount(this.cardNumber);
        inputSession.loggedIn = false;
        System.out.println("The account has been closed!\n");
    }


    private static String generateCard() {
        Random random = new Random();
        String cardNumber = String.format("400000%09d", random.nextInt(1000000000));
        int luhnCheck = Check.luhnCheckSum(cardNumber);
        int last;
        if (luhnCheck % 10 != 0) last = 10 - (luhnCheck % 10);
        else last = 0;
        return cardNumber + last;
    }

    public static void createAccount() {
        String cardNumber = generateCard();
        Random random = new Random();
        String pin = String.format("%04d", random.nextInt(10000));

        System.out.println("Your card has been created");
        printInfo(cardNumber, pin);

        database.insertRow(cardNumber, pin);
    }

    private static void printInfo(String cardNumber, String pin) {
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(pin + "\n");
    }

    public static Account logIn(String cardNumber, String pin, InputSession inputSession) {
        String pinCheck = database.getPinByCard(cardNumber);
        Account account = null;

        if (pinCheck.equals(pin)) {
            account = new Account(inputSession, cardNumber);
            inputSession.loggedIn = true;
            System.out.println("You have successfully logged in!\n");
            return account;
        } else {
            System.out.println("Wrong card number or PIN!\n");
        }

        return account;
    }

    public void logOut() {
        inputSession.loggedIn = false;
        System.out.println("You have been successfully logged out!");
    }

    public void getBalance() {
        double balance = database.getBalanceByCard(this.cardNumber);
        System.out.println(balance);
    }

}
