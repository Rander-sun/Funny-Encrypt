package sample.Controller;

import sample.Utils.App;
import sample.Utils.Constants;
import sample.Entity.MyImage;
import sample.Utils.Util;

import java.io.IOException;

/**
 * 图像文件控制器
 *
 * @author lxt
 * @date 2021/06/12
 */
public class ImageFileController {

    /**
     * 加密按钮的相应方法
     *
     * @param myImage   图像
     * @param filePath  文件路径
     * @param password2 密码
     * @return {@link String}
     */
    public static String encBtn(MyImage myImage, String filePath, String password2){
        String keyCoordinate = null;
        try {
            Util.jpg2png(myImage.getimage(), Constants.IMAGENAME);
            myImage=new MyImage(Constants.IMAGENAME);
            keyCoordinate= App.encryptFile(myImage,filePath,password2);
            System.out.println(keyCoordinate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyCoordinate;
    };

    /**
     * 解密按钮的响应方法
     *
     * @param myImage  图像
     * @param filePath 文件路径
     * @param PWD      密码
     */
    public static void decBtn(MyImage myImage,String filePath,String PWD){
        try {
            myImage=new MyImage(Constants.IMAGENAME);
            App.decryptFile(myImage,filePath,PWD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
