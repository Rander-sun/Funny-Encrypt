package sample.View;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTooltip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.Utils.Constants;
import sample.Controller.ImageTextController;
import sample.Entity.MyImage;

import javax.imageio.ImageIO;


/**
 * 图像文本加密
 *
 * @author Hasee
 * @date 2021/06/12
 */
public class ImageTextEncrypt {
  private AnchorPane an = new AnchorPane();
  private HBox img=new HBox();
  private VBox form =new VBox();
  private ImageView iv = new ImageView();

  /**
   * 得到缓冲图像
   *
   * @return {@link BufferedImage}
   */
  public BufferedImage getBufferedImage() {
    return bufferedImage;
  }

  /**
   * 设置缓冲图片
   *
   * @param bufferedImage 缓冲图像
   */
  public void setBufferedImage(BufferedImage bufferedImage) {
    this.bufferedImage = bufferedImage;
  }

  private BufferedImage bufferedImage;

  /**
   * 得到一个Pane
   *
   * @return {@link AnchorPane}
   */
  public AnchorPane getAn() {
    return an;
  }

  /**
   * 设置一个Pane
   *
   * @param an AnchorPane实例
   */
  public void setAn(AnchorPane an) {
    this.an = an;
  }

  /**
   * 得到img
   *
   * @return {@link HBox}
   */
  public HBox getImg() {
    return img;
  }

  /**
   * 设置img
   *
   * @param img img
   */
  public void setImg(HBox img) {
    this.img = img;
  }

  /**
   * 获得表单
   *
   * @return {@link VBox}
   */
  public VBox getForm() {
    return form;
  }

  /**
   * 设置形式
   *
   * @param form 表单
   */
  public void setForm(VBox form) {
    this.form = form;
  }

  /**
   * 获得图片
   *
   * @return {@link ImageView}
   */
  public ImageView getIv() {
    return iv;
  }

  /**
   * 设置图片
   *
   * @param iv 图片
   */
  public void setIv(ImageView iv) {
    this.iv = iv;
  }

  /**
   * 得到图像
   *
   * @return {@link BufferedImage}
   */
  public BufferedImage getImage() {
    return bufferedImage;
  }

  /**
   * 设置缓冲形象图片
   *
   * @param image 图像
   */
  public void setBufferedImageImage(BufferedImage image) {
    this.bufferedImage = image;
  }

  //返回TAB2的内容
  public Pane ImagePane() {

    an.getStyleClass().add("ImagePane");

    //img.getStyleClass().add("Image");
    img.setPrefSize(1100,910);
    img.setStyle("-fx-background-color: #EEE9E9");
    Tooltip tooltip=new Tooltip();
    //tooltip.setText("请把图片拖拽到这里");
    img.setAlignment(Pos.CENTER);
    iv.setPreserveRatio(true);
    iv.setFitWidth(1100);
    iv.setFitHeight(910);
    img.getChildren().addAll(iv);

    form.getStyleClass().add("RightForm");
    JFXTooltip tooltip1=new JFXTooltip("操作提示：\n请将图片拖入左侧灰色框内\n在密码框中输入自定的密码\n复制密码\n在应用根目录获取加密好的图片\n输入自定的密码和程序输出的密码解密");
    JFXButton tipBtn=new JFXButton("提示");
    tipBtn.getStyleClass().add("button1-raised");
    tipBtn.setTooltip(tooltip1);

    Text tip0=new Text();
    //tip0.setText("操作提示：\n将图片拖入左侧灰色框内\n在文本框内输入需要加密的文字\n在密码框中输入自定的密码，点击加密\n点击弹窗点击ctrl+c复制密码\n在应用根目录获取加密好的图片\n解密时，输入自定的密码和程序输出的密码");
    Text tip1 = new Text();
    tip1.setText("请输入需要加密的文字");
    TextArea textToEncrypt=new TextArea();
    Text tip2 = new Text();
    tip2.setText("请输入设置的密码");
    PasswordField password1=new PasswordField();
    JFXButton encryptBtn=new JFXButton("确认加密");
    encryptBtn.getStyleClass().add("button-raised");


    encryptBtn.setOnAction(event -> {
      MyImage image=new MyImage();
      image.setimage(bufferedImage);
      String message= ImageTextController.btnEncode(textToEncrypt.getText(), password1.getText(),image);
      File file=new File(Constants.IMAGENAME);
      try {
        bufferedImage= ImageIO.read(file);
        iv.setImage(new Image(new FileInputStream(file)));
      } catch (IOException e) {
        e.printStackTrace();
      }
      new AlertBuilder(encryptBtn).setTitle("提示").setTextField(new AlertBuilder.OnInputListener(){
        @Override
        public void onGetText(String result) {
          //返回一个输入结果result
          //相关的逻辑操作
        }
      },message).setBtn("确定").create();
      //Alert alert = new Alert(AlertType.NONE);
      //alert.setTitle("加密结果");
      //alert.setContentText(message);
      //alert.getDialogPane().getButtonTypes().add(new ButtonType("确认", ButtonBar.ButtonData.OK_DONE));
      //alert.showAndWait();
    });

    Separator separator=new Separator();
    Text tip3 = new Text();
    tip3.setText("请输入设置的密码");
    PasswordField password2=new PasswordField();
    Text tip4 = new Text();
    tip4.setText("请输入程序输出的密码");
    PasswordField password3=new PasswordField();
    JFXButton decryptBtn=new JFXButton("确认解密");
    decryptBtn.getStyleClass().add("button2-raised");

    decryptBtn.setOnAction(event -> {
      MyImage image=new MyImage();
      image.setimage(bufferedImage);
      String message=ImageTextController.btnDecode(password2.getText(),password3.getText(),image);
      new AlertBuilder(decryptBtn).setMessage(message).setBtn("确认").create();
      //Alert alert = new Alert(AlertType.NONE);
      //alert.setTitle("解密结果");
      //alert.setContentText(message);
      //alert.getDialogPane().getButtonTypes().add(new ButtonType("确认", ButtonBar.ButtonData.OK_DONE));
      //alert.showAndWait();
    });
    form.setAlignment(Pos.CENTER);
    form.getChildren().addAll(tipBtn,tip1,textToEncrypt,tip2,password1,encryptBtn,separator,tip3,password2,tip4,password3,decryptBtn);
    form.setSpacing(20);
    an.setLeftAnchor(img,2.0);
    an.setTopAnchor(form,100.0);
    an.setRightAnchor(form,40.0);
    an.getChildren().addAll(img,form);
    return an;
  }

}
