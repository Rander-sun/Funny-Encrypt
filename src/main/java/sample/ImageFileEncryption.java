package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ImageFileEncryption {
    private AnchorPane an = new AnchorPane();
    private HBox file=new HBox();
    private final Desktop desktop=Desktop.getDesktop();
    public AnchorPane getAn() {
        return an;
    }

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

    private VBox form =new VBox();
    private ImageView iv = new ImageView();

    public Pane ImagePane(){
        an.getStyleClass().add("ImageFilePane");
        file.setPrefSize(1100,910);
        file.setStyle("-fx-background-color: #D3D3D3");
        iv.setPreserveRatio(true);
        iv.setFitWidth(1100);
        iv.setFitHeight(910);
        file.getChildren().add(iv);
        form.getStyleClass().add("RightForm");
        //选择文件

        final FileChooser fileChooser=new FileChooser();
        final Button openButton1 = new Button("浏览文件");
        //final Button openButton2 = new Button("选择解密文件");

        Text fileTip =new Text();
        fileTip.setText("请选择需要加密的文件");
        Text codeTip= new Text();
        codeTip.setText("请输入设置的密码");
        TextArea code=new TextArea();
        Separator separator=new Separator();
        Text fileDecTip=new Text();
        fileDecTip.setText("请选择需要解密的文件");
        Button openButton2=new Button("浏览文件");

        Text codeDecText=new Text();
        codeDecText.setText("请输入对应密钥");
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
        Button confirmButton1=new Button("确认加密");
        Button confirmButton2=new Button("确认解密");

        form.getChildren().addAll(fileTip,filesBox1,codeTip, code,confirmButton1,separator,fileDecTip,filesBox2,codeDecText,decode,confirmButton2);


        form.setSpacing(10);
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
