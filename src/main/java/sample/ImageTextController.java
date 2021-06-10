package sample;

import java.io.IOException;

public class ImageTextController {

  public static String btnEncode(String content,String password, MyImage image){
    String uPassword;
    try {
      Util.jpg2png(image.getimage(),"IMAGE.png");
      image=new MyImage("IMAGE.png");
      uPassword=Test.encrypt(image,content,password);
    } catch (IOException e) {
      return "WARNING:"+e.getMessage();
    }
    return "请记住这个密码:"+uPassword;
  }
  public static String btnDecode(String password,String uPassword, MyImage image){
    String content;
    try {
      image=new MyImage("IMAGE.png");
      content=Test.deCode(image,uPassword,password);
    } catch (IOException e) {
      return "WARNING:"+e.getMessage();
    }
    return "解密内容为:"+content;
  }

}
