package sample.View;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.sun.istack.internal.Nullable;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * 警报建设者
 *
 * @author Hasee
 * @date 2021/06/12
 */
public class AlertBuilder {
  private String title, message;
  private JFXButton btn = null;
  private Window window;
  private Paint btnPaint = Paint.valueOf("#0099ff");
  private Hyperlink hyperlink = null;
  private TextField textField = null;
  private JFXAlert<String> alert;
  private OnInputListener onInputListener = null;


  /**
   * 警报建设者
   *
   * @param control 控制
   */
  public AlertBuilder(Control control) {
    window = control.getScene().getWindow();
  }

  /**
   * 设置标题
   *
   * @param title 标题
   * @return {@link AlertBuilder}
   */
  public AlertBuilder setTitle(String title) {
    this.title = title;
    return this;
  }

  /**
   * 设置消息
   *
   * @param message 消息
   * @return {@link AlertBuilder}
   */
  public AlertBuilder setMessage(String message) {
    this.message = message;
    return this;
  }

  /**
   * 设置btn
   *
   * @param btnText btn文本
   * @return {@link AlertBuilder}
   */
  public AlertBuilder setBtn(String btnText) {
    return setBtn(btnText, null);
  }


  /**
   * 设置btn
   *
   * @param positiveBtnText    积极btn文本
   * @param btnOnclickListener btn onclick侦听器
   * @return {@link AlertBuilder}
   */
  public AlertBuilder setBtn(String positiveBtnText, @Nullable OnClickListener btnOnclickListener) {
    btn = new JFXButton(positiveBtnText);
    btn.setDefaultButton(true);
    btn.setTextFill(btnPaint);
    btn.setOnAction(closeEvent -> {
      alert.hideWithAnimation();
      if (btnOnclickListener != null) {
        btnOnclickListener.onClick();//回调onClick方法
      }
    });
    return this;
  }

  /**
   * 设置链接
   *
   * @param text 文本
   * @return {@link AlertBuilder}
   */
  public AlertBuilder setLink(String text) {
    hyperlink = new Hyperlink(text);
    hyperlink.setBorder(Border.EMPTY);
    hyperlink.setOnMouseClicked(event -> {
      if (text.contains("www") || text.contains("com") || text.contains(".")) {
        try {
          Desktop.getDesktop().browse(new URI(text));
        } catch (IOException | URISyntaxException e) {
          e.printStackTrace();
        }
      } else if (text.contains(File.separator)) {
        try {
          Desktop.getDesktop().open(new File(text));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    return this;
  }

  /**
   * 设置文本框
   *
   * @param onInputListener 在输入监听器
   * @param message         消息
   * @return {@link AlertBuilder}
   */
  public AlertBuilder setTextField(OnInputListener onInputListener,String message) {
    this.textField = new TextField();
    textField.setText(message);
    this.onInputListener = onInputListener;
    return this;
  }


  /**
   * 创建
   *
   * @return {@link JFXAlert<String>}
   */
  public JFXAlert<String> create() {
    alert = new JFXAlert<>((Stage) (window));
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.setOverlayClose(false);

    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(new Label(title));
    //添加hyperlink超链接文本或者是输入框
    if (hyperlink != null) {
      layout.setBody(new HBox(new Label(this.message), hyperlink));
    } else if (textField != null) {
      layout.setBody(new VBox(new Label(this.message), textField));
      btn.setOnAction(event -> {
        alert.setResult(textField.getText());
        alert.hideWithAnimation();
      });
    } else {
      layout.setBody(new VBox(new Label(this.message)));
    }

    //添加确定和取消按钮
    if ( btn != null) {
      layout.setActions( btn);
    }
    alert.setContent(layout);
    Optional<String> input = alert.showAndWait();
    //不为空，则回调接口
    //if (input.isPresent()) {
    //  onInputListener.onGetText(input.get());
    //}
    return alert;
  }


  /**
   * 在点击监听器
   *
   * @author Hasee
   * @date 2021/06/12
   */
  public interface OnClickListener {

    /**
     * 在点击
     */
    void onClick();
  }

  /**
   * 在输入监听器
   *
   * @author Hasee
   * @date 2021/06/12
   */
  public interface OnInputListener {

    /**
     * 得到文本
     *
     * @param result 结果
     */
    void onGetText(String result);
  }

}