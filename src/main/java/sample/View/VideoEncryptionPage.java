package sample.View;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTooltip;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Controller.VideoEncryptionController;
import sample.Utils.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * 视频加密页面
 *
 * @author Hasee
 * @date 2021/06/12
 */
public class VideoEncryptionPage {
    private AnchorPane an = new AnchorPane();

    /**
     * 得到一个
     *
     * @return {@link AnchorPane}
     */
    public AnchorPane getAn() {
        return an;
    }

    /**
     * 设置一个
     *
     * @param an 一个
     */
    public void setAn(AnchorPane an) {
        this.an = an;
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
     * @param form 形式
     */
    public void setForm(VBox form) {
        this.form = form;
    }

    /**
     * 得到视频帧
     *
     * @return {@link VBox}
     */
    public VBox getVideoFrame() {
        return videoFrame;
    }

    /**
     * 设置视频帧
     *
     * @param videoFrame 视频帧
     */
    public void setVideoFrame(VBox videoFrame) {
        this.videoFrame = videoFrame;
    }

    private VBox videoFrame=new VBox();
    private VBox form =new VBox();

    private ImageView imageView= new ImageView();

    /**
     * 得到图像视图
     *
     * @return {@link ImageView}
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * 设置图像视图
     *
     * @param imageView 图像视图
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    /**
     * 视频面板
     *
     * @return {@link Pane}
     * @throws FileNotFoundException 文件未发现异常
     */
    public Pane VideoPane() throws FileNotFoundException {
        an.getStyleClass().add("ImagePane");
        //初始化学
        videoFrame.setPrefSize(1100,910);
        videoFrame.setStyle("-fx-background-color: #EEE9E9");
        videoFrame.setAlignment(Pos.CENTER);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(1100);
        imageView.setFitHeight(910);
        imageView.setImage(new Image(new FileInputStream(new File("src/main/resources/ima/9.jpg"))));
        Label label1=new Label();
        Label label2=new Label();
        Label label3=new Label();
        Label label4=new Label();
        Label label5=new Label();
        videoFrame.getChildren().addAll(label1,label2,label3,label4,label5);
        form.getStyleClass().add("RightForm");

        final FileChooser fileChooser=new FileChooser();
        final Button openButton1 = new Button("浏览文件");
        //final Button openButton2 = new Button("选择解密文件");

        JFXTooltip tooltip1=new JFXTooltip("操作提示：\n文件框内浏览或拖拽视频文件\n在密码框中输入自定的密码，点击加密\n在应用根目录获取加密好的ENC文件以及相关解密信息\n(也可以加密其他类型文件)");
        JFXButton tipBtn=new JFXButton("提示");
        tipBtn.getStyleClass().add("button1-raised");
        tipBtn.setTooltip(tooltip1);

        JFXTooltip tooltip2=new JFXTooltip("操作提示：\n解密时，输入程序加密时输出的密钥与密文\n点击解密后\n在原加密文件目录下获取解密好的_DEC文件\n");
        JFXButton tipBtn2=new JFXButton("提示");
        tipBtn2.getStyleClass().add("button1-raised");
        tipBtn2.setTooltip(tooltip2);

        Text tip0=new Text();
        tip0.setText("操作提示：\n文件框内浏览或拖拽视频文件\n在密码框中输入自定的密码，点击加密\n在应用根目录获取加密好的ENC文件以及相关解密信息\n解密时，输入程序加密时输出的密钥与密文\n点击解密后\n在原加密文件目录下获取解密好的_DEC文件\n(也可以加密其他文件)");
        Text fileTip =new Text();
        fileTip.setText("请选择需要加密的视频文件");
        Text codeTip= new Text();
        codeTip.setText("请输入您设置的明文");
        TextArea code=new TextArea();
        Separator separator=new Separator();
        Text fileDecTip=new Text();
        fileDecTip.setText("请选择需要解密的视频文件");
        Button openButton2=new Button("浏览文件");

        Text codeDecText=new Text();
        codeDecText.setText("请输入对应密文");
        TextArea decode=new TextArea();
        Text privateKey=new Text();
        privateKey.setText("请输入对应密钥");
        TextArea prikeyText=new TextArea();
        //设计拖拽文件行
        HBox filesBox1=new HBox();
        TextField filePath=new TextField();
        filePath.setText("拖拽/浏览文件以输入");
        ChooseFile2(filePath,openButton1,fileChooser);
        filesBox1.getChildren().addAll(filePath,openButton1);
        HBox filesBox2=new HBox();
        TextField filePath2=new TextField();
        filePath2.setText("拖拽/浏览文件以输入");
        ChooseFile2(filePath2,openButton2,fileChooser);
        filesBox2.getChildren().addAll(filePath2,openButton2);
        JFXButton confirmButton1=new JFXButton("确认加密");
        confirmButton1.getStyleClass().add("button-raised");
        JFXButton confirmButton2=new JFXButton("确认解密");
        confirmButton2.getStyleClass().add("button2-raised");

        confirmButton1.setOnAction(event -> {
            File file =new File(filePath.getText());
            String nFileName= Util.changeEncName(file);
            VideoEncryptionController.Enc(filePath.getText(),code.getText());
            new AlertBuilder(confirmButton2).setMessage("请前往源文件目录下获取加密文件"+nFileName+"和加密信息").setBtn("确认").create();
            //Alert alert = new Alert(Alert.AlertType.INFORMATION);
            //alert.setTitle("加密结果");
            //alert.setHeaderText("Congratulations!Encryption Success!");
            //alert.setContentText("请前往源文件目录下获取加密文件"+nFileName+"和加密信息");
        });

        confirmButton2.setOnAction(event -> {
            File file =new File(filePath2.getText());
            String nFileName=Util.changeDecName(file);
            VideoEncryptionController.Dec(filePath2.getText(),decode.getText(),prikeyText.getText());
            new AlertBuilder(confirmButton2).setMessage("请前往源文件目录下获取解密文件"+nFileName).setBtn("确认").create();
            //Alert alert = new Alert(Alert.AlertType.INFORMATION);
            //alert.setTitle("加密结果");
            //alert.setHeaderText("Congratulations!Encryption Success!");
            //alert.setContentText("请前往源文件目录下获取解密文件:"+nFileName);
        });

        form.getChildren().addAll(tipBtn,fileTip,filesBox1,codeTip, code,confirmButton1,separator,tipBtn2,fileDecTip,filesBox2,codeDecText,decode,privateKey,prikeyText,confirmButton2);
        form.setAlignment(Pos.CENTER);
        form.setSpacing(20);
        an.setLeftAnchor(videoFrame,2.0);
        an.setTopAnchor(form,100.0);
        an.setRightAnchor(form,50.0);
        an.getChildren().addAll(videoFrame,form);


        return an;
    }

    /**
     * 选择file2
     *
     * @param textField   文本字段
     * @param button      按钮
     * @param fileChooser 文件选择器
     */
    public static void ChooseFile2(TextField textField, Button button, FileChooser fileChooser) {
        Stage fileStage = new Stage();
        fileStage.setTitle("选择文件...");
        textField.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != textField) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }
        });
        textField.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard dragboard = event.getDragboard();
                if (dragboard.hasFiles()) {
                    try {
                        File targetFile = dragboard.getFiles().get(0);
                        if (targetFile != null) {
                            textField.setText(targetFile.getAbsolutePath());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();//here
                    }
                }
            }
        });
        button.setOnAction(
                (final ActionEvent e) -> {
                    File file = fileChooser.showOpenDialog(fileStage);
                    if (file != null) {
                        textField.setText(file.getAbsolutePath());
                    }
                }
        );
    }

//这边要调用controller然后在label输出内容
}
