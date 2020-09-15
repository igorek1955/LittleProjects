package encryptdecrypt;

class Unicode implements EncodeDecode{
    String string;
    int key;

    public Unicode(String string, int key) {
        this.string = string;
        this.key = key;
    }

    @Override
    public String encode() {
        char[] chars = string.toCharArray();
        char[] encrypted = new char[chars.length];
        int i = 0;
        for (char ch : chars) {
            encrypted[i] = (char) ((ch + key));
            i++;
        }
        return String.valueOf(encrypted);
    }

    @Override
    public String decode() {
        char[] chars = string.toCharArray();
        char[] decrypted = new char[chars.length];
        int i = 0;
        for (char ch : chars) {
            decrypted[i] = (char) ((ch - key));
            i++;
        }
        return String.valueOf(decrypted);
    }
}
