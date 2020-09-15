package encryptdecrypt;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Cypher cypher = new Cypher();
        cypher.scanArgs(args); //scan program arguments
        cypher.execute(); //execute decoding or encoding, that was specified at app startup
        cypher.print(); //print results to console or save to specified destination
    }
}



class Cypher {
    String message = "";
    int key = 0;
    String action = "enc";
    String encryptedMessage;
    String decryptedMessage;
    String destinationFile;
    String encoding = "shift";


    public void execute() {
        switch (action) {
            case "enc":
                encrypt(encoding, message, key);
                break;
            case "dec":
                decrypt(encoding, message, key);
                break;
        }
    }

    public void scanArgs(String[] args) {

        for (int i = 0; i < args.length; i++) {
            if ("-mode".equals(args[i])) {
                action = args[i + 1];
            } else if ("-key".equals(args[i])) {
                key = Integer.parseInt(args[i + 1]);
            } else if ("-data".equals(args[i])) {
                message = args[i + 1];
            } else if("-in".equals(args[i])){
                try {
                    message = new String(Files.readAllBytes(Paths.get(args[i+1])));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if("-out".equals(args[i])){
                this.destinationFile = args[i+1];
            } else if("-alg".equals(args[i])){
                this.encoding = args[i+1];
            }
        }
    }

    public void saveToFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile))){
            if(this.encryptedMessage==null) writer.write(decryptedMessage);
            else writer.write(encryptedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void decrypt(String type, String message, int key) {
        EncodeDecode encodeDecode = new DecoderCreator().getDecoder(type, message, key);
        this.decryptedMessage = encodeDecode.decode();
    }

    public void encrypt(String type, String message, int key) {
        EncodeDecode encodeDecode = new DecoderCreator().getDecoder(type, message, key);
        this.encryptedMessage = encodeDecode.encode();
    }


    public void printEncMessage(){
        System.out.println(this.encryptedMessage);
    }

    public void printDecMessage(){
        System.out.println(this.decryptedMessage);
    }

    public void print() {
        if(destinationFile.isEmpty()){
            if (encryptedMessage == null) {
                printDecMessage();
            } else {
                printEncMessage();
            }
        }
        else saveToFile();
    }
}













