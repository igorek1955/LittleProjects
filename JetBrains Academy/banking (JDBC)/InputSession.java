package banking;

import java.util.Scanner;

class InputSession {

    boolean loggedIn = false;
    Account account;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!loggedIn) {
                System.out.println("1. Create an account\n" +
                        "2. Log into account\n" +
                        "0. Exit");
                int o = scanner.nextInt();
                if (o == 1) {
                    Account.createAccount();
                } else if (o == 2) {
                    System.out.println("Enter your card number:");
                    String cardNumber = scanner.next();
                    System.out.println("Enter your PIN:");
                    String pin = scanner.next();
                    account = Account.logIn(cardNumber, pin, this);
                } else if (o == 0) {
                    System.out.println("Bye!");
                    break;
                }

            } else {
                System.out.println("1. Balance\n" +
                        "2. Add income\n" +
                        "3. Do transfer\n" +
                        "4. Close account\n" +
                        "5. Log out\n" +
                        "0. Exit");
                int o = scanner.nextInt();

                if (o == 1) {
                    account.getBalance();
                } else if (o == 2) {
                    System.out.println("Enter income:");
                    double amount = scanner.nextDouble();
                    account.addIncome(amount);
                } else if (o == 3) {
                    System.out.println("Transfer");
                    System.out.println("Enter card number");
                    String cardNumberIn = scanner.next();
                    if (Check.luhnCheck(cardNumberIn) && cardNumberIn.length()==16) {
                        System.out.println("Enter how much money you want to transfer:");
                        double amount = scanner.nextDouble();
                        account.doTransfer(cardNumberIn, amount);
                    } else {
                        System.out.println("Probably you made mistake in the card number. " +
                                "Please try again!\n");
                    }
                } else if (o == 4) {
                    account.closeAccount();
                } else if (o == 5) {
                    account.logOut();
                } else if (o == 0) {
                    System.out.println("Bye!");
                    break;
                }
            }
        }
    }
}
