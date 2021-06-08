package sample;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
        writer.write("Original File:"+src+"Your public key is:\n"+publicKey+"\n\n"+"Your private key is:\n"+privateKey+"\n\n"+"Your original text is:\n"+code+"\n\n"+"Your encrypted text is:\n"+encryptedText);
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
    public static String encryptFile(Image image,String filePath,String password) throws IOException {
        int readLength;//读入长度
        byte result[];//加密结果
        File file = new File(filePath);
        String newFile=file.getAbsolutePath()+"_enc";
        FileInputStream inputStream =new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(newFile);
        int fileSize= (int) file.length();//提前说明输入大小
        byte datain[]=new byte[fileSize+1];//+1 in case anything happens
        if((readLength=inputStream.read(datain,0,fileSize))!=-1){
            result=AesEncrypt.aesEncrypt(datain,password);
        }
        else {
            System.out.println("Read File Error!Unable to Read Efficient bytes!");
            return null;//was the return necessary?
        }
        //先加密
        //写入对应新文件
        outputStream.write(result,0,result.length);
        outputStream.close();
        inputStream.close();
        //关闭对应输出流，然后开始图像加密：对密钥加密(处理后加密）
        byte[] passwordInByte=password.getBytes(StandardCharsets.UTF_8);
        String newPWD=Util.Byte2Hex(passwordInByte);
        String keyCoordinate=Util.LSBEncryption(image,newPWD);
        ImageIO.write(image.getimage(), "png",  new File("E://大学//OOP//大作业//Image encryption//ima//2_.png"));//mode 2
        keyCoordinate=Util.decodeUnicode(keyCoordinate);
        return keyCoordinate;
    }

    public static void decryptFile(Image image,String filePath,String PWD) throws IOException {
        //先获取原密码byte[]形式
        String recode=Util.LSBDecryption(image,PWD);
        String realCode=Util.decodeUnicode(recode);
        //打开待解密文件
        int readLength;
        byte[] result;
        File file = new File(filePath);
        String newFile=file.getAbsolutePath()+"_denc";
        FileInputStream inputStream =new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(newFile);
        int fileSize= (int) file.length();//提前说明输入大小
        byte datain[]=new byte[fileSize+1];//+1 in case anything happens
        if((readLength=inputStream.read(datain,0,fileSize))!=-1){
            result=AesEncrypt.decrypt(datain,realCode);
        }
        else {
            System.out.println("Read File Error!Unable to Read Efficient bytes!");
            return ;//was the return necessary?
        }
        outputStream.write(result,0,result.length);
        outputStream.close();
        inputStream.close();
        System.out.println("Decrypt file success!");
    }
}
