package encryptdecrypt;

public class DecoderCreator implements DecodeFactory{
    @Override
    public EncodeDecode getDecoder(String name, String string, int key) {
        EncodeDecode encodeDecode = null;
        if("shift".equals(name)) encodeDecode = new Shift(string,key);
        else encodeDecode = new Unicode(string, key);
        return encodeDecode;
    }
}
