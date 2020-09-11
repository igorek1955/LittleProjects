package machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class CoffeeMachine {
    //default values
    private static int money = 550;
    private static int cups = 9;
    private static int water = 400;
    private static int milk = 540;
    private static int coffeeBeans = 120;

    public static void main(String[] args) throws IOException {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.loopAsking();
    }

    //ask to choose action in a loop
    public void loopAsking() throws IOException {
        BufferedReader reader;
        String request = "";
        do {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            try{
                request = reader.readLine();
            } catch (IOException ignored){}

            if ("buy".equals(request)) {
                buy();
            } else if ("fill".equals(request)) {
                fill();
            } else if ("take".equals(request)) {
                take();
            } else if ("remaining".equals(request)) {
                remaining();
            }
        }while((!"exit".equals(request)));
        reader.close();
    }


    //print remaining resources to the console
    private void remaining() {
        System.out.println(toString());
    }


    //choose coffee
    public void buy() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String coffee = reader.readLine();
        if (coffee.equals("1")) {
            money = money + 4;
            water = water - 250;
            coffeeBeans = coffeeBeans - 16;
            cups = cups - 1;
            if (water >= 0 && coffeeBeans >= 0 && cups >= 0) {
                System.out.println("I have enough resources, making you a coffee!");
            } else {
                if (water < 0) System.out.println("Sorry, not enough water!");
                else if (coffeeBeans < 0) System.out.println("Sorry, not enough coffee beans!");
                else if (cups < 0) System.out.println("Sorry, not enough cups!");
                else if (milk < 0) System.out.println("Sorry, not enough milk!");
                money = money - 4;
                water = water + 250;
                coffeeBeans = coffeeBeans + 16;
                cups = cups + 1;
            }
        } else if (coffee.equals("2")) {
            money = money + 7;
            water = water - 350;
            milk = milk - 75;
            coffeeBeans = coffeeBeans - 20;
            cups = cups - 1;
            if (water >= 0 && coffeeBeans >= 0 && cups >= 0 && milk >= 0) {
                System.out.println("I have enough resources, making you a coffee!");
            } else {
                if (water < 0) System.out.println("Sorry, not enough water!");
                else if (coffeeBeans < 0) System.out.println("Sorry, not enough coffee beans!");
                else if (cups < 0) System.out.println("Sorry, not enough cups!");
                else if (milk < 0) System.out.println("Sorry, not enough milk!");
                money = money - 7;
                water = water + 350;
                milk = milk + 75;
                coffeeBeans = coffeeBeans + 20;
                cups = cups + 1;
            }
        } else if (coffee.equals("3")) {
            money = money + 6;
            water = water - 200;
            milk = milk - 100;
            coffeeBeans = coffeeBeans - 12;
            cups = cups - 1;
            if (water >= 0 && coffeeBeans >= 0 && cups >= 0 && milk >= 0) {
                System.out.println("I have enough resources, making you a coffee!");
            } else {
                if (water < 0) System.out.println("Sorry, not enough water!");
                else if (coffeeBeans < 0) System.out.println("Sorry, not enough coffee beans!");
                else if (cups < 0) System.out.println("Sorry, not enough cups!");
                else if (milk < 0) System.out.println("Sorry, not enough milk!");
                money = money - 6;
                water = water + 200;
                milk = milk + 100;
                coffeeBeans = coffeeBeans + 12;
                cups = cups + 1;
            }
        }
        System.out.println();
    }


    //replenish resources
    public void fill() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water do you want to add:");
        water = water + scanner.nextInt();
        System.out.println("Write how many ml of milk do you want to add:");
        milk = milk + scanner.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add:");
        coffeeBeans = coffeeBeans + scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        cups = cups + scanner.nextInt();
        System.out.println();
    }


    //withdraw all the money from the machine
    public void take() {
        System.out.println("I gave you $" + money);
        money = 0;
        System.out.println();
    }


    @Override
    public String toString() {
        return "The coffee machine has:\n" +
                water + " of water\n" +
                milk + " of milk\n" +
                coffeeBeans + " of coffee beans\n" +
                cups + " of disposable cups\n" +
                money + " of money\n\r";
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(int m) {
        money = m;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int c) {
        cups = c;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int w) {
        water = w;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int m) {
        milk = m;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public void setCoffeeBeans(int beans) {
        coffeeBeans = beans;
    }


    public static void countingCups() {
        Scanner scanner = new Scanner(System.in);
        int[] ints = new int[3];
        System.out.println("Write how many ml of water the coffee machine has:");
        ints[0] = scanner.nextInt() / 200;
        System.out.println("Write how many ml of milk the coffee machine has:");
        ints[1] = scanner.nextInt() / 50;
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        ints[2] = scanner.nextInt() / 15;
        System.out.println("Write how many cups of coffee you will need:");
        int min = Arrays.stream(ints).min().getAsInt();
        int cups = scanner.nextInt();
        if (cups > min) System.out.printf("No, I can make only %d cup(s) of coffee", min);
        else if (cups == min) System.out.printf("Yes, I can make that amount of coffee", min);
        else System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)", min - cups);
    }

    public static void justPrinting() {
        System.out.println("Starting to make a coffee");
        System.out.println("Grinding coffee beans");
        System.out.println("Boiling water");
        System.out.println("Mixing boiled water with crushed coffee beans");
        System.out.println("Pouring coffee into the cup");
        System.out.println("Pouring some milk into the cup");
        System.out.println("Coffee is ready!");
    }

    public static void countingIngredients() {
        System.out.println("Write how many cups of coffee you will need: \n");
        Scanner scanner = new Scanner(System.in);
        int cups = scanner.nextInt();
        int water = cups * 200;
        int milk = cups * 50;
        int beans = cups * 15;
        System.out.println("For 25 cups of coffee you will need:");
        System.out.printf("%d ml of water\n", water);
        System.out.printf("%d ml of milk\n", milk);
        System.out.printf("%d g of coffee beans\n", beans);
    }
}
