package sample.Utils;
import sample.Entity.MyImage;
import sample.Entity.VideoEncryption;
import sample.Utils.AesEncrypt;
import sample.Utils.Constants;
import sample.Utils.RSA;
import sample.Utils.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Base64;

/**
 * 封装对视频加密操作的
 *
 * @author lxt
 * @date 2021/06/12
 */
public class App {

    /**
     * 加密视频
     * 读入待加密视频以及明文，对明文进行RSA加密后输出公钥、私钥，并用密文对视频文件进行加密处理，最后打包输出视频文件和加密信息txt
     * @param src  源视频文件路径
     * @param code 明文
     */
    public static void encryptVideo(String src,String code){
        String encryptedName= Util.changeEncName(new File(src));
        System.out.println("Generating keys...");
        RSA rsa = new RSA(code);
        byte[] tmpPrivateKey=rsa.getPrivateKey().getEncoded();
        byte[] tmpPublicKey=rsa.getPublicKey().getEncoded();
        String privateKey= Base64.getEncoder().encodeToString(tmpPrivateKey);
        String publicKey = Base64.getEncoder().encodeToString(tmpPublicKey);
        String encryptedText=rsa.getEncryptedText();
        System.out.println("Encryption handling...");
        VideoEncryption entool = new VideoEncryption(src,encryptedName,code,1);
        String info = "Info For "+encryptedName+".txt";
        String absPath= entool.getPackingPath();
        try{
        File infoFile = new File(absPath+"\\"+info);
        FileWriter writer = new FileWriter(infoFile);
        writer.write("Original File:"+src+"\n\nYour public key is:\n"+publicKey+"\n\n"+"Your private key is(密钥):\n"+privateKey+"\n\n"+"Your original text is:\n"+code+"\n\n"+"Your encrypted text is(密文):\n"+encryptedText);
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Encryption Success!");
    }

    /**
     * 解密视频
     * 读入待解密视频、密文、私钥，先用私钥和RSA算法将密文解密为明文，再用明文解密视频文件，最后输出。
     * @param filePath 待解密文件路径
     * @param encText  密文
     * @param priKey   私钥
     */
    public static void decryptVideo(String filePath,String encText,String priKey){
        String nName=Util.changeDecName(new File(filePath));
        System.out.println("Decrypting...");
        RSA rsa =new RSA(encText,priKey);
        VideoEncryption detool = new VideoEncryption(filePath,nName,rsa.getOriginText(),0);
        System.out.println("Decryption Success!");
    }

    /**
     * 图像隐写文件加密的封装函数
     * 读入图像载体、待加密文件路径和密码，对文件进行加密操作，并将加密信息隐藏到图片像素内，最后输出加密的像素坐标值
     * @param image    图像载体
     * @param filePath 文件路径
     * @param password 密码
     * @return {@link String} 加密后像素坐标值
     * @throws IOException ioexception
     */
    public static String encryptFile(MyImage image, String filePath, String password) throws IOException {
        int readLength;//读入长度
        byte result[];//加密结果
        File file = new File(filePath);
        String newFile=Util.changeEncName(file);
        newFile=file.getParent()+"\\"+newFile;
        File encFile=new File(newFile);
        System.out.println(encFile.getAbsolutePath());
        FileInputStream inputStream =new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(encFile);
        int fileSize= (int) file.length();//提前说明输入大小
        byte datain[]=new byte[fileSize+1];//+1 in case anything happens
        if((readLength=inputStream.read(datain,0,fileSize))!=-1){
            result= AesEncrypt.aesEncrypt(datain,password);
            System.out.println("Length1"+result.length);
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
        byte[] passwordInByte=password.getBytes("utf-8");
        String newPWD=Util.Byte2Hex(passwordInByte);
        System.out.println("This is original:"+newPWD);
        String keyCoordinate=Util.LSBEncryption(image,newPWD);

        ImageIO.write(image.getimage(), "png",  new File(Constants.IMAGENAME));//mode 2//D:\\javaTest\\2_.png
        keyCoordinate=Util.Encode64(keyCoordinate);
        return keyCoordinate;
    }

    /**
     * 解密文件
     * 读入图像载体、待解密文件路径和加密后坐标值。解密坐标值后从图像中读取加密信息，并将该信息用于解密文件。
     * @param image    图像
     * @param filePath 文件路径
     * @param PWD      密码
     * @throws IOException ioexception
     */
    public static void decryptFile(MyImage image,String filePath,String PWD) throws IOException {

        //先获取原密码byte[]形式
        String recode=Util.LSBDecryption(image,PWD);
        System.out.println("This is after :"+recode);
        byte[] decode = Util.Hex2Byte(recode);
        String realCode=new String(decode,"utf-8");
        System.out.println(realCode);

            //打开待解密文件
        int readLength;
        byte[] result;
        File file = new File(filePath);
        String newFile=Util.changeDecName(file);
        newFile=file.getParent()+"\\"+newFile;
        File nFile=new File(newFile);
        FileInputStream inputStream =new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(nFile);
        int fileSize= (int) file.length();//提前说明输入大小
        byte datain[]=new byte[fileSize];//+1 in case anything happens
        if((readLength=inputStream.read(datain,0,fileSize))!=-1){
            System.out.println("Length2"+datain.length);
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
