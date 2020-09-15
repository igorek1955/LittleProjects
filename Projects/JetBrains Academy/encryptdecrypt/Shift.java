package encryptdecrypt;

import org.junit.Test;

class Shift implements EncodeDecode {
    String string;
    int key;

    public Shift(String string, int key) {
        this.string = string;
        this.key = key;
    }


    @Override
    public String encode() {
        char[] chars = string.toCharArray();
        char[] encrypted = new char[chars.length];
        int i = 0;
        for(char ch: chars){
            if(ch>=97 && ch<=122){
                int position = 122-ch;
                if(position < key) encrypted[i] = (char)((97+key-position-1));
                else encrypted[i] = (char)(ch+key);
            } else if(ch>=65 && ch<=90){
                int position = 90-ch;
                if(position < key) encrypted[i] = (char)((65+key-position-1));
                else encrypted[i] = (char)(ch+key);
            } else{
                encrypted[i] = ch;
            }
            i++;
        }
        return String.valueOf(encrypted);
    }

    @Test
    @Override
    public String decode() {
        char[] chars = string.toCharArray();
        char[] encrypted = new char[chars.length];
        int i = 0;
        for(char ch: chars){
            if(ch>=97 && ch<=122){
                int position = ch-97;
                if(position < key) encrypted[i] = (char)((122-key+position+1));
                else encrypted[i] = (char)(ch-key);
            } else if(ch>=65 && ch<=90){
                int position = ch-65;
                if(position < key) encrypted[i] = (char)((90-key+position+1));
                else encrypted[i] = (char)(ch-key);
            } else{
                encrypted[i] = ch;
            }
            i++;
        }
        return String.valueOf(encrypted);
    }
}
