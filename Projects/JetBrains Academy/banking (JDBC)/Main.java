package banking;

public class Main {

    public static void main(String[] args) {
        if (args[1] != null)
            Database.createDatabase(args[1]);

        InputSession session = new InputSession();
        session.start();
    }
}


