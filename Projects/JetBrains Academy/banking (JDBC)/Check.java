package banking;

class Check {

    public static int luhnCheckSum(String cardNumber) {
        int checkSum = 0;
        for (int i = 0; i < 15; i++) {
            int num = 0;
            if (i % 2 == 0) {
                num = (Character.getNumericValue(cardNumber.charAt(i))) * 2;

                if (num > 9) {
                    num -= 9;
                }

            } else num = Character.getNumericValue(cardNumber.charAt(i));
            checkSum += num;
        }
        return checkSum;
    }

    public static boolean luhnCheck(String cardNumber) {
        int sum = luhnCheckSum(cardNumber);
        int last = Character.getNumericValue(cardNumber.charAt(15));
        return (sum + last) % 10 == 0;
    }
}
