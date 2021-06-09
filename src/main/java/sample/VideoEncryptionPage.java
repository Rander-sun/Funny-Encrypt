package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;


public class VideoEncryptionPage {
    private AnchorPane an = new AnchorPane();

    public AnchorPane getAn() {
        return an;
    }

    public void setAn(AnchorPane an) {
        this.an = an;
    }


    public VBox getForm() {
        return form;
    }

    public void setForm(VBox form) {
        this.form = form;
    }

    public VBox getVideoFrame() {
        return videoFrame;
    }

    public void setVideoFrame(VBox videoFrame) {
        this.videoFrame = videoFrame;
    }

    private VBox videoFrame=new VBox();
    private VBox form =new VBox();
    //private MediaView mv=new MediaView();
    //private Video

    public Pane VideoPane(){
        an.getStyleClass().add("ImagePane");
        //初始化学
        videoFrame.setPrefSize(1100,910);
        videoFrame.setStyle("-fx-background-color: #D3D3D3");
        //mv.setPreserveRatio(true);
        //mv.setFitWidth(1100);
        //mv.setFitHeight(910);
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
        Button confirmButton1=new Button("确认加密");
        Button confirmButton2=new Button("确认解密");

        form.getChildren().addAll(fileTip,filesBox1,codeTip, code,confirmButton1,separator,fileDecTip,filesBox2,codeDecText,decode,privateKey,prikeyText,confirmButton2);
        form.setSpacing(10);
        an.setLeftAnchor(videoFrame,2.0);
        an.setTopAnchor(form,200.0);
        an.setRightAnchor(form,50.0);
        an.getChildren().addAll(videoFrame,form);


        return an;
    }

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
