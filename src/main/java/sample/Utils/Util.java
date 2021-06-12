package sample.Utils;

import sample.Entity.MyImage;

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

/**
 * 跑龙套
 *
 * @author zy lxt
 * @date 2021/06/12
 */
public class Util {

  /**
   * byte to 十六进制
   *
   * @param buf 缓冲区
   * @return {@link String}
   */
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

  /**
   * 十六进制 to 字节
   *
   * @param hexStr 十六进制str
   * @return {@link byte[]}
   */
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

  /**
   * jpg to png
   *
   * @param image   图像
   * @param outFile 输出文件
   * @throws IOException ioexception
   */
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

  /**
   * jpg2png
   *
   * @param image 图像
   * @return {@link BufferedImage}
   * @throws IOException ioexception
   */
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

  /**
   * lsbencryption
   *
   * @param image 图像
   * @param code  密码
   * @return {@link String}
   *///此处code为密钥
  public static String LSBEncryption(MyImage image, String code){
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

  /**
   * lsbdecryption
   *
   * @param image  图像
   * @param decode 解码
   * @return {@link String}
   */
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

  /**
   * encode64
   * 对生成像素坐标值串用Base64加密
   * @param code 坐标值
   * @return {@link String}
   */
  public static String Encode64(String code) {
    byte[] bytes = code.getBytes();

    //Base64 加密
    String encoded = Base64.getEncoder().encodeToString(bytes);
    //System.out.println("Base 64 加密后：" + encoded);

    return encoded;
  }

  /**
   * decode64
   * 对生成坐标像素值串用Base64解密
   * @param encoded 已加密坐标值
   * @return {@link String}
   */
  public static String Decode64(String encoded){
    //Base64 解密
    byte[] decoded = Base64.getDecoder().decode(encoded);

    String decodeStr = new String(decoded);
    //System.out.println("Base 64 解密后：" + decodeStr);
    return decodeStr;
  }

  /**
   * 改变文件名DEC
   * 在文件名中加上DEC标识，以标记解密后文件
   * @param file 文件
   * @return {@link String} 返回新加密文件名
   */
  public static String changeDecName(File file){
    String ori=file.getName();
    int position=ori.lastIndexOf(".");
    String newName=ori.substring(0,position)+"_DEC"+ori.substring(position);
    return newName;
  }

  /**
   * 改变文件名ENC
   * 在文件名中加上ENC标识，以标记加密后文件
   * @param file 文件
   * @return {@link String} 返回新解密文件名
   */
  public static String changeEncName(File file){
    String ori=file.getName();
    int position=ori.lastIndexOf(".");
    String newName=ori.substring(0,position)+"ENC"+ori.substring(position);
    return newName;
  }
}
