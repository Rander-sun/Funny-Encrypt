package sample.Controller;

import sample.Utils.Constants;
import sample.Entity.MyImage;
import sample.Utils.Test;
import sample.Utils.Util;

import java.io.IOException;

/**
 * 图像文本控制器
 *
 * @author Hasee
 * @date 2021/06/12
 */
public class ImageTextController {

  /**
   * 点击按钮加密
   *
   * @param content  内容
   * @param password 密码
   * @param image    图像
   * @return {@link String}
   */
  public static String btnEncode(String content,String password, MyImage image){
    String uPassword;
    try {
      Util.jpg2png(image.getimage(), Constants.IMAGENAME);
      image=new MyImage(Constants.IMAGENAME);
      uPassword= Test.encrypt(image,content,password);
    } catch (IOException e) {
      return "WARNING:"+e.getMessage();
    }
    return "请记住这个密码:"+uPassword;
  }

  /**
   * 点击按钮解密
   *
   * @param password  密码
   * @param uPassword 你的密码
   * @param image     图像
   * @return {@link String}
   */
  public static String btnDecode(String password,String uPassword, MyImage image){
    String content;
    try {
      image=new MyImage(Constants.IMAGENAME);
      content=Test.deCode(image,uPassword,password);
    } catch (IOException e) {
      return "WARNING:"+e.getMessage();
    }
    return "解密内容为:"+content;
  }

}
