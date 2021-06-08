package sample;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
}
