package encryptdecrypt;

public interface DecodeFactory{
    public EncodeDecode getDecoder(String name, String string, int key);
}
