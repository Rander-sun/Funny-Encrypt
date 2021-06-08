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
     Image image=new Image("E://大学//OOP//大作业//Image encryption//ima//1.png");
     Util.jpg2png(image.getimage(),"E://大学//OOP//大作业//Image encryption//ima//1.png");
     image=new Image("E://大学//OOP//大作业//Image encryption//ima//1.png");
     Scanner in=new Scanner(System.in);
     System.out.println("请输入需要加密的文字");
     String content = in.nextLine();
     System.out.println("请输入您设置的密码");
     String password = in.nextLine();
     Test.encrypt(image,content,password);
     //解密
     Image image1=new Image("E://大学//OOP//大作业//Image encryption//ima//1_.png");
     System.out.println("请输入设置好的密码");
     String password_=in.nextLine();
     System.out.println("请输入生成的密码");
     String uPassword=in.nextLine();
     String deCodeContent=Test.deCode(image1,uPassword,password_);
     System.out.println("解密后："+deCodeContent);
   }

   public static String encrypt(Image image,String content,String password) throws IOException {
     int a=0,r=0,g=0,b=0;
     String uPassword = " ";

     System.out.println("加密前：" + content);
     byte[] encode = AesEncrypt.aesEncrypt(content, password);

     String code = Util.Byte2Hex(encode);
     //System.out.println("密文字符串：" + code);
     image.genCoordinate(image.getimage(), code.length()/4);
     for(int i=0;i<code.length()-3;i=i+4){
       a=code.charAt(i);
       r=code.charAt(i+1);
       g=code.charAt(i+2);
       b=code.charAt(i+3);
       Integer[] keys = image.coordinate.keySet().toArray(new Integer[0]);
       Random random = new Random();
       Integer x = keys[random.nextInt(keys.length)];
       Integer y = image.coordinate.get(x);
       uPassword=uPassword+image.setImagePixel(image.getimage(), a,r,g,b,x,y);
       image.coordinate.remove(x);
     }
     ImageIO.write(image.getimage(), "png",  new File("E://大学//OOP//大作业//Image encryption//ima//1_.png"));
     uPassword=Util.decodeUnicode(uPassword);
     System.out.println("生成密码："+uPassword);
     return uPassword;
   }

   public static String deCode(Image image1,String uPassword,String password)
       throws UnsupportedEncodingException {
     String recode="";
     uPassword=Util.encodeUnicode(uPassword);
     //System.out.println("解密坐标为"+uPassword);
     String[] passBuf=uPassword.split("\\s+");
     //System.out.println(passBuf);
     for(int i=0;i<passBuf.length-1;i+=2){
       int x=Integer.parseInt(passBuf[i]);
       int y=Integer.parseInt(passBuf[i+1]);
       int[] argb=image1.getImagePixel(image1.getimage(), x,y);
       recode=recode+(char)argb[0]+(char)argb[1]+(char)argb[2]+(char)argb[3];
     }
     //System.out.println(recode);
     byte[] decode = Util.Hex2Byte(recode);

     byte[] decryptResult = AesEncrypt.decrypt(decode, password);
     return new String(decryptResult, "UTF-8");
   }
}
