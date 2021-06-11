package sample.Utils;

import sample.Entity.MyImage;
import sample.Utils.AesEncrypt;
import sample.Utils.App;
import sample.Utils.Constants;
import sample.Utils.Util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * 测试
 *
 * @author Hasee
 * @date 2021/06/12
 */
public class Test {

  /**
   * 加密
   *
   * @param image    图像
   * @param content  内容
   * @param password 密码
   * @return {@link String}
   * @throws IOException ioexception
   */
  public static String encrypt(MyImage image,String content,String password) throws IOException {

     System.out.println("加密前：" + content);
     byte[] encode = AesEncrypt.aesEncrypt(content, password);//加密后密文

     String code = Util.Byte2Hex(encode);
     System.out.println("密文字符串：" + code);
     String uPassword = Util.LSBEncryption(image,code);
     System.out.println("坐标"+uPassword);

     ImageIO.write(image.getimage(), "png",  new File(Constants.IMAGENAME));
     //uPassword=Util.decodeUnicode(uPassword);
     uPassword=Util.Encode64(uPassword);
     System.out.println("生成密码："+uPassword);
     return uPassword;
   }

  /**
   * de代码
   *
   * @param image1    image1
   * @param uPassword 你的密码
   * @param password  密码
   * @return {@link String}
   * @throws UnsupportedEncodingException 不支持的编码异常
   */
  public static String deCode(MyImage image1,String uPassword,String password)
       throws UnsupportedEncodingException {
     String recode=Util.LSBDecryption(image1,uPassword);
     System.out.println("密文"+recode);
     byte[] decode = Util.Hex2Byte(recode);
     byte[] decryptResult = AesEncrypt.decrypt(decode, password);
     return new String(decryptResult, "UTF-8");
   }
}
