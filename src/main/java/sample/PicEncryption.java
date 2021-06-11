package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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

import java.awt.image.BufferedImage;
import java.io.File;

public class PicEncryption {
    private AnchorPane an = new AnchorPane();
    private HBox file=new HBox();
    private BufferedImage bufferedImage;
    public AnchorPane getAn() {
        return an;
    }
    private VBox form =new VBox();
    private ImageView iv = new ImageView();

    public void setAn(AnchorPane an) {
        this.an = an;
    }

    public HBox getFile() {
        return file;
    }

    public void setFile(HBox file) {
        this.file = file;
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



    //返回TAB1的内容
    public Pane ImagePane(){
        an.getStyleClass().add("PicPane");
        file.setPrefSize(1100,910);
        file.setStyle("-fx-background-color: #EEE9E9");
        iv.setPreserveRatio(true);
        iv.setFitWidth(1100);
        iv.setFitHeight(910);
        file.getChildren().add(iv);
        form.getStyleClass().add("RightForm");
        Text tip0=new Text();
        tip0.setText("操作提示：\n将图片拖入左侧灰色框内\n在文本框内输入加密参数，点击加密\n注意，加密参数必须是0-1之间的小数\n否则无法加密\n在应用根目录获取加密好的图片\n解密时，输入加密时的加密参数");
        //选择文件

        final FileChooser fileChooser=new FileChooser();
        final Button openButton1 = new Button("浏览文件");
        //final Button openButton2 = new Button("选择解密文件");

        Text fileTip =new Text();
        fileTip.setText("请选择需要加密的文件");
        Text codeTip= new Text();
        codeTip.setText("请输入加密参数");
        TextArea code=new TextArea();
        Separator separator=new Separator();
        Text fileDecTip=new Text();
        fileDecTip.setText("请选择需要解密的文件");
        Button openButton2=new Button("浏览文件");

        Text codeDecText=new Text();
        codeDecText.setText("请输入加密时的参数");
        TextArea decode=new TextArea();
        //设计拖拽文件行
        HBox filesBox1=new HBox();
        TextField filePath=new TextField();
        filePath.setText("拖拽/浏览文件以输入");
        ChooseFile(filePath,openButton1,fileChooser);
        filesBox1.getChildren().addAll(filePath,openButton1);
        HBox filesBox2=new HBox();
        TextField filePath2=new TextField();
        filePath2.setText("拖拽/浏览文件以输入");
        ChooseFile(filePath2,openButton2,fileChooser);
        filesBox2.getChildren().addAll(filePath2,openButton2);
        JFXButton confirmButton1=new JFXButton("确认加密");
        confirmButton1.getStyleClass().add("button-raised");
        JFXButton confirmButton2=new JFXButton("确认解密");
        confirmButton2.getStyleClass().add("button2-raised");

        //对应加密controller

        confirmButton1.setOnAction(event->{
            Pic pic;
            if(Constants.IMAGENAME.equals("IMAGE.png")){
                pic=new Pic(filePath.getText());
            }
            else{
                pic=new Pic(Constants.IMAGENAME);
            }
            PicEncryptionController.en(pic,Double.parseDouble(String.valueOf(code.getText())));
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Congratulations!Encryption Success!");
        });

        confirmButton2.setOnAction(event->{
            Pic pic;
            if(Constants.IMAGENAME.equals("IMAGE.png")){
                pic=new Pic(filePath2.getText());
            }
            else{
                pic=new Pic(Constants.IMAGENAME);
            }
            PicEncryptionController.de(pic,Double.parseDouble(String.valueOf(decode.getText())));
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Congratulations!Decryption Success!");
        });

        form.getChildren().addAll(fileTip,filesBox1,codeTip, code,confirmButton1,separator,fileDecTip,filesBox2,codeDecText,decode,confirmButton2);
        form.setAlignment(Pos.CENTER);
        form.setSpacing(20);
        an.setLeftAnchor(file,2.0);
        an.setTopAnchor(form,200.0);
        an.setRightAnchor(form,50.0);
        an.getChildren().addAll(file,form);
        return an;

    }
    public static void ChooseFile(TextField textField, Button button, FileChooser fileChooser) {
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
}
