package sample;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ImageTextEncrypt {
  private AnchorPane an = new AnchorPane();
  private HBox img=new HBox();
  private VBox form =new VBox();
  private ImageView iv = new ImageView();

  public AnchorPane getAn() {
    return an;
  }

  public void setAn(AnchorPane an) {
    this.an = an;
  }

  public HBox getImg() {
    return img;
  }

  public void setImg(HBox img) {
    this.img = img;
  }

  public VBox getForm() {
    return form;
  }

  public void setForm(VBox form) {
    this.form = form;
  }

  public ImageView getIv() {
    return iv;
  }

  public void setIv(ImageView iv) {
    this.iv = iv;
  }
//返回TAB2的内容
  public Pane ImagePane() {

    an.getStyleClass().add("ImagePane");

    //img.getStyleClass().add("Image");
    img.setPrefSize(1100,910);
    img.setStyle("-fx-background-color: #D3D3D3");
    iv.setPreserveRatio(true);
    iv.setFitWidth(1100);
    iv.setFitHeight(910);
    img.getChildren().add(iv);


    form.getStyleClass().add("RightForm");
    Text tip1 = new Text();
    tip1.setText("请输入需要加密的文字");
    TextArea textToEncrypt=new TextArea();
    Text tip2 = new Text();
    tip2.setText("请输入设置的密码");
    PasswordField password1=new PasswordField();
    Button encryptBtn=new Button();
    encryptBtn.setText("加密");
    Separator separator=new Separator();
    Text tip3 = new Text();
    tip3.setText("请输入设置的密码");
    PasswordField password2=new PasswordField();
    Text tip4 = new Text();
    tip4.setText("请输入程序输出的密码");
    PasswordField password3=new PasswordField();
    Button decryptBtn=new Button();
    decryptBtn.setText("解密");
    form.getChildren().addAll(tip1,textToEncrypt,tip2,password1,encryptBtn,separator,tip3,password2,tip4,password3,decryptBtn);
    form.setSpacing(10);
    an.setLeftAnchor(img,2.0);
    an.setTopAnchor(form,200.0);
    an.setRightAnchor(form,50.0);
    an.getChildren().addAll(img,form);
    return an;
  }
}
