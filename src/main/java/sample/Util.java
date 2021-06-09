package sample;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class Util {
  public static String Byte2Hex(byte buf[]) {
    StringBuffer strBuf = new StringBuffer();
    for (int i = 0; i < buf.length; i++) {
      String hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      strBuf.append(hex.toUpperCase());
    }
    return strBuf.toString();
  }

  public static byte[] Hex2Byte(String hexStr) {
    if (hexStr.length() < 1)
      return null;
    byte[] result = new byte[hexStr.length() / 2];
    for (int i = 0; i < hexStr.length() / 2; i++) {
      int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
      int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
      result[i] = (byte) (high * 16 + low);
    }
    return result;
  }

  public static String decodeUnicode(String unicode){

    StringBuffer string = new StringBuffer();
    String[] hex = unicode.split("\\s+");
    for (int i = 1; i < hex.length; i++) {
      int data = Integer.parseInt(hex[i], 16);
      string.append((char) data);
    }
    return string.toString();
  }


  public static String encodeUnicode(String string){
    StringBuffer unicode = new StringBuffer();
    for (int i = 0; i < string.length(); i++) {
      char c = string.charAt(i);
      unicode.append(Integer.toHexString(c)+" ");
    }
    return unicode.toString();
  }

  public static void jpg2png(BufferedImage image,String outFile) throws IOException {

    int width= image.getWidth();
    int height= image.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, image.getType());
    Graphics2D gr = bufferedImage.createGraphics();
    bufferedImage = gr.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

    gr = bufferedImage.createGraphics();
    gr.drawImage(image.getScaledInstance(width, height, image.SCALE_DEFAULT), 0, 0, width, height, null);
    gr.dispose();

    File file = new File(outFile);
    ImageIO.write(bufferedImage, "png", file);
  }

  public static BufferedImage jpg2png(BufferedImage image) throws IOException {

    int width= image.getWidth();
    int height= image.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, image.getType());
    Graphics2D gr = bufferedImage.createGraphics();
    bufferedImage = gr.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

    gr = bufferedImage.createGraphics();
    gr.drawImage(image.getScaledInstance(width, height, image.SCALE_DEFAULT), 0, 0, width, height, null);
    gr.dispose();
    return bufferedImage;
  }
//此处code为密文或者密钥
  public static String LSBEncryption(MyImage image,String code){
    String uPassword="";
    int a=0,r=0,g=0,b=0;//标明坐标
    //新增更改：向上取舍，原参数code.length()/4
    float num=((float)code.length())/4;
    int newNum=(int)Math.ceil(num);
    image.genCoordinate(image.getimage(), newNum);
    String aCode="";
    String rCode="";
    String gCode="";
    String bCode="";
    for(int i=1;i<=code.length();i++){
      int flag=i%4;
      switch (flag){
        case 1:
          aCode+=code.charAt(i-1);
          break;
        case 2:
          rCode+=code.charAt(i-1);
          break;
        case 3:
          gCode+=code.charAt(i-1);
          break;
        case 0:
          bCode+=code.charAt(i-1);
          break;
      }
    }
    for(int i=0;i<newNum;i++){
      a=i<aCode.length()?aCode.charAt(i):0;
      r=i<rCode.length()?rCode.charAt(i):0;
      g=i<gCode.length()?gCode.charAt(i):0;
      b=i<bCode.length()?bCode.charAt(i):0;
      Integer[] keys = image.coordinate.keySet().toArray(new Integer[0]);
      Random random = new Random();
      Integer x = keys[random.nextInt(keys.length)];
      Integer y = image.coordinate.get(x);
      uPassword=uPassword+image.setImagePixel(image.getimage(), a,r,g,b,x,y);
      image.coordinate.remove(x);
    }
    //System.out.println(uPassword);
    return uPassword;//返回对应信息坐标
  }

  public static String LSBDecryption(MyImage image,String decode){
    String recode="";
    decode=Util.Decode64(decode);
    System.out.println("坐标解密"+decode);
    String[] passBuf=decode.split("\\s+");
    //System.out.println(passBuf);
    List<String> xBuf=new ArrayList<>();
    List<String> yBuf=new ArrayList<>();
    for(int i=0;i< passBuf.length;i++){
      int flag=i%2;
      if(flag==0) xBuf.add(passBuf[i]);
      else yBuf.add(passBuf[i]);
    }
    for(int i=0;i<passBuf.length/2;i++){
      int x=Integer.parseInt(xBuf.get(i),16);
      int y=Integer.parseInt(yBuf.get(i),16);
      int[] argb=image.getImagePixel(image.getimage(), x,y);
      for(int j=0;j<4;j++){
        if(argb[j]!=0)
          recode+=(char)argb[j];
      }
    }
    return recode;
  }
  public static String Encode64(String code) {
    byte[] bytes = code.getBytes();

    //Base64 加密
    String encoded = Base64.getEncoder().encodeToString(bytes);
    //System.out.println("Base 64 加密后：" + encoded);

    return encoded;
  }
  public static String Decode64(String encoded){
    //Base64 解密
    byte[] decoded = Base64.getDecoder().decode(encoded);

    String decodeStr = new String(decoded);
    //System.out.println("Base 64 解密后：" + decodeStr);
    return decodeStr;
  }
}