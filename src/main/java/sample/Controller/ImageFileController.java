package sample.Controller;

import sample.Utils.App;
import sample.Utils.Constants;
import sample.Entity.MyImage;
import sample.Utils.Util;

import java.io.IOException;

/**
 * 图像文件控制器
 *
 * @author Hasee
 * @date 2021/06/12
 */
public class ImageFileController {

    /**
     * 内附btn
     *
     * @param myImage   我的形象
     * @param filePath  文件路径
     * @param password2 2
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
     * 12月btn
     *
     * @param myImage  我的形象
     * @param filePath 文件路径
     * @param PWD      松材线虫病
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
