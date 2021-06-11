package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sample.View.ImageFileEncryption;
import sample.View.ImageTextEncrypt;
import sample.View.PicEncryption;
import sample.View.VideoEncryptionPage;
import sample.Utils.Constants;

import javax.imageio.ImageIO;


/**
 * Hello World
 */
// 继承javafx.application.Application是JavaFX的开始
public class Main extends Application {

  /**
   * Stage：就是你能看到的整个软件界面（窗口）
   * Scene：就是除了窗口最上面有最大、最小化及关闭按钮那一行及窗口边框外其它的区域（场景）
   * 场景（Scene）是一个窗口（Stage）必不可少的
   */
  @Override
  public void start(Stage stage) throws Exception {
    // （如果需要的话）定位横纵坐标,避免太靠边上遮盖菜单栏,，这两行如果不屑，一般电脑默认是居中屏幕显示，但在有些电脑会跑偏
    // stage和Scene不再注释
    stage.setTitle(Constants.TITLE);
    //stage.getIcons().add(new Image(Constants.IMG + "icon.png"));
    stage.setWidth(1400);
    // 窗口的最小宽度
    stage.setMinWidth(800);
    // 窗口的高度
    stage.setHeight(1000);
    // 窗口的最小高度
    stage.setMinHeight(600);
    // 设置宽高尺寸可调整，true:可以拖拽边缘调整窗口尺寸，false：不可调整
    stage.setResizable(true);

    //使用tab切换界面，tab1是子豪，tab2是赵阳，tab3、4是罗晓彤
    TabPane tabPane=new TabPane();
    tabPane.getStyleClass().add("Menu");
    Tab tab1=new Tab("图像转换加密");
    tab1.getStyleClass().add("menu-button");
    Tab tab2=new Tab("图像隐写加密信息");
    tab2.getStyleClass().add("menu-button");
    Tab tab3=new Tab("视频加密");
    tab3.getStyleClass().add("menu-button");
    Tab tab4=new Tab("图像隐写加密文件");
    tab4.getStyleClass().add("menu-button");
    tabPane.getTabs().addAll(tab1,tab2,tab4,tab3);
    //给tab1传内容
    PicEncryption picEncryption=new PicEncryption();
    tab1.setContent(picEncryption.ImagePane());
    File file1=new File("src/main/resources/ima/photo1.png");
    Image photo1=new Image(new FileInputStream(file1));
    ImageView btnphoto1=new ImageView(photo1);
    btnphoto1.setFitHeight(40);
    btnphoto1.setFitWidth(40);
    tab1.setGraphic(btnphoto1);
    //给tab2传内容
    ImageTextEncrypt ite=new ImageTextEncrypt();
    tab2.setContent(ite.ImagePane());
    File file2=new File("src/main/resources/ima/photo3.png");
    Image photo2=new Image(new FileInputStream(file2));
    ImageView btnphoto2=new ImageView(photo2);
    btnphoto2.setFitHeight(40);
    btnphoto2.setFitWidth(40);
    tab2.setGraphic(btnphoto2);
    //传给tab4
    ImageFileEncryption imageFileEncryption=new ImageFileEncryption();
    tab4.setContent(imageFileEncryption.ImagePane());
    File file3=new File("src/main/resources/ima/photo2.png");
    Image photo3=new Image(new FileInputStream(file3));
    ImageView btnphoto3=new ImageView(photo3);
    btnphoto3.setFitHeight(40);
    btnphoto3.setFitWidth(40);
    tab4.setGraphic(btnphoto3);
    //传给tab3
    VideoEncryptionPage videoEncryptionPage = new VideoEncryptionPage();
    tab3.setContent(videoEncryptionPage.VideoPane());
    File file4=new File("src/main/resources/ima/video.png");
    Image photo4=new Image(new FileInputStream(file4));
    ImageView btnphoto4=new ImageView(photo4);
    btnphoto4.setFitHeight(35);
    btnphoto4.setFitWidth(35);
    tab3.setGraphic(btnphoto4);

    // 1、初始化一个场景
    Scene scene = new Scene(tabPane, 1400, 1000);
    scene.getStylesheets().add(getClass().getClassLoader().getResource("css/Bar.css").toExternalForm());
    // 2、将场景放入窗口
    stage.setScene(scene);
    // 3、打开窗口
    stage.show();
    dragImage(picEncryption.getFile(),picEncryption.getIv(),picEncryption);
    dragImage(ite.getImg(), ite.getIv(),ite);
    dragImage(imageFileEncryption.getFile(), imageFileEncryption.getIv(),imageFileEncryption);
  }

  public static void main( String[] args ){
    // 启动软件
    Application.launch(args);
  }

  public static void dragImage(HBox hBox,ImageView imageView,ImageTextEncrypt ite){
    //下面可以实现拖拽图片进入图片框
    hBox.setOnDragEntered(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        hBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#00ffff"),
                BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1))));
      }
    });
    hBox.setOnDragExited(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        hBox.setBorder(null);
      }
    });
    hBox.setOnDragOver(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        event.acceptTransferModes(event.getTransferMode());
      }
    });
    hBox.setOnDragDropped(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        List<File> files= db.getFiles();
        Constants.IMAGENAME=files.get(0).getName();
        if(files.size()>0){
          try{
                ite.setBufferedImageImage(ImageIO.read(files.get(0)));
            imageView.setImage(new Image(new FileInputStream(files.get(0))));
          }catch (FileNotFoundException e){
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  public static void dragImage(HBox hBox,ImageView imageView,PicEncryption picEncryption) {
    //下面可以实现拖拽图片进入图片框
    hBox.setOnDragEntered(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        hBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#00ffff"),
                BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
      }
    });
    hBox.setOnDragExited(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        hBox.setBorder(null);
      }
    });
    hBox.setOnDragOver(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        event.acceptTransferModes(event.getTransferMode());
      }
    });
    hBox.setOnDragDropped(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        List<File> files = db.getFiles();
        Constants.IMAGENAME = files.get(0).getAbsolutePath();
        if (files.size() > 0) {
          try {
            imageView.setImage(new Image(new FileInputStream(files.get(0))));

          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  public static void dragImage (HBox hBox, ImageView imageView, ImageFileEncryption ife){
    //下面可以实现拖拽图片进入图片框
    hBox.setOnDragEntered(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        hBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#00ffff"),
                BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
      }
    });
    hBox.setOnDragExited(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        hBox.setBorder(null);
      }
    });
    hBox.setOnDragOver(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        event.acceptTransferModes(event.getTransferMode());
      }
    });
    hBox.setOnDragDropped(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        List<File> files = db.getFiles();
        Constants.IMAGENAME = files.get(0).getAbsolutePath();
        if (files.size() > 0) {
          try {
            ife.setBufferedImage(ImageIO.read(files.get(0)));
            imageView.setImage(new Image(new FileInputStream(files.get(0))));
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }
}
