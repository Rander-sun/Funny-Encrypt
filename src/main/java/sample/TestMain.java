package sample;
import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    App app = new App();
        String nName;
        System.out.println("Please input file path for encryption");
        String src=in.nextLine();
        System.out.println("Please input your cleartext");
        String code=in.nextLine();
        System.out.println("Please input your new file name");//DISCARD
        nName=in.nextLine();
    App.encryptVideo(src,code);//tmp
        String src1;
        String encryptedCode;
        String nName1;
        String privateKey;
        System.out.println("Please input file path for decryption");
        src1=in.nextLine();
        System.out.println("Please input your encrypted text");
        encryptedCode=in.nextLine();
        System.out.println("Please input your privateKey");
        privateKey=in.nextLine();
        System.out.println("Please input your new file name");
        nName1=in.nextLine();
    App.decryptVideo(src1,encryptedCode,privateKey);
    return;
    }
}
