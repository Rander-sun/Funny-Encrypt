package sample;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

public class App {

    public static void encryptVideo(Scanner in){
        String src;
        String code;
        String nName;
        System.out.println("Please input file path for encryption");
        src=in.nextLine();
        System.out.println("Please input your cleartext");
        code=in.nextLine();
        System.out.println("Please input your new file name");
        nName=in.nextLine();
        String encryptedName=nName+".enc";
        System.out.println("Generating keys...");
        RSA rsa = new RSA(code);
        byte[] tmpPrivateKey=rsa.getPrivateKey().getEncoded();
        byte[] tmpPublicKey=rsa.getPublicKey().getEncoded();
        String privateKey= Base64.getEncoder().encodeToString(tmpPrivateKey);
        String publicKey = Base64.getEncoder().encodeToString(tmpPublicKey);
        String encryptedText=rsa.getEncryptedText();
        System.out.println("Encryption handling...");
        VideoEncryption entool = new VideoEncryption(src,encryptedName,code,1);
        String info = "Info For "+nName+".txt";
        String absPath= entool.getPackingPath();
        try{
        File infoFile = new File(absPath+"\\"+info);
        FileWriter writer = new FileWriter(infoFile);
        writer.write("Original File:"+src+"Your public key is:\n"+publicKey+"\n\n"+"Your private key is:\n"+privateKey+"\n\n"+"Your original text is:\n"+code+"\n\n"+"Your encrypted text is:\n"+rsa.getEncryptedText());
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Encryption Success!");
    }
    //DecMethods
    public static void decryptVideo(Scanner in){
        String src;
        String encryptedCode;
        String nName;
        String privateKey;
        System.out.println("Please input file path for decryption");
        src=in.nextLine();
        System.out.println("Please input your encrypted text");
        encryptedCode=in.nextLine();
        System.out.println("Please input your privateKey");
        privateKey=in.nextLine();
        System.out.println("Please input your new file name");
        nName=in.nextLine();
        System.out.println("Decrypting...");
        RSA rsa =new RSA(encryptedCode,privateKey);
        VideoEncryption detool = new VideoEncryption(src,nName,rsa.getOriginText(),0);
        System.out.println("Decryption Success!");
    }
    //PackItUp
}
