package sample;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Test {

   public static void main(String[] args) throws IOException {
     Image image=new Image("D:\\javaTest\\图像.jpg");//original:"E://大学//OOP//大作业//Image encryption//ima//1.png"
     Util.jpg2png(image.getimage(),"D:\\javaTest\\1.png");//original:"E://大学//OOP//大作业//Image encryption//ima//1.png"
     image=new Image("D:\\javaTest\\1.png");//"E://大学//OOP//大作业//Image encryption//ima//1.png"
     Scanner in=new Scanner(System.in);
     System.out.println("Which mode do you want to choose?\n1.MessageEncryption by pic\n2.FileEncryption by pic");
     int flag=in.nextInt();
     in.nextLine();//清除回车
     switch (flag){
       case 1:
         System.out.println("请输入需要加密的文字");
         String content = in.nextLine();
         System.out.println("请输入您设置的密码");
         String password = in.nextLine();
         Test.encrypt(image,content,password);
         //解密
         Image image1=new Image("D:\\javaTest\\2_.png");
         System.out.println("请输入设置好的密码");
         String password_=in.nextLine();
         System.out.println("请输入生成的密码");
         String uPassword=in.nextLine();
         String deCodeContent=Test.deCode(image1,uPassword,password_);
         System.out.println("解密后："+deCodeContent);
         break;
       case 2:
         System.out.println("请输入需要加密的文件路径");
         String filePath = in.nextLine();
         System.out.println("请输入您设置的密码");
         String password2 = in.nextLine();
         String keyCoordinate=App.encryptFile(image,filePath,password2);
         System.out.println("生成密码："+keyCoordinate);
         //解密
         System.out.println("请输入解密图片路径");
         String filePath2=in.nextLine();
         //模拟解密文件路径
         Image image2=new Image("D:\\javaTest\\2_.png");
         System.out.println("请输入生成的密码");
         String uPassword2=in.nextLine();
         //先读出密钥并解密
         App.decryptFile(image2,filePath2,uPassword2);
         //System.out.println("解密后："+deCodeContent2);
         break;
       default: System.out.println("Please type in the right type");
     }

   }

   public static String encrypt(Image image,String content,String password) throws IOException {

     System.out.println("加密前：" + content);
     byte[] encode = AesEncrypt.aesEncrypt(content, password);//加密后密文

     String code = Util.Byte2Hex(encode);
     //System.out.println("密文字符串：" + code);
     String uPassword = Util.LSBEncryption(image,code);
     ImageIO.write(image.getimage(), "png",  new File("D:\\javaTest\\2_.png"));
     uPassword=Util.decodeUnicode(uPassword);
     System.out.println("生成密码："+uPassword);
     return uPassword;
   }

   public static String deCode(Image image1,String uPassword,String password)
       throws UnsupportedEncodingException {
     String recode=Util.LSBDecryption(image1,uPassword);
     byte[] decode = Util.Hex2Byte(recode);
     byte[] decryptResult = AesEncrypt.decrypt(decode, password);
     return new String(decryptResult, "UTF-8");
   }
}
