package sample;

import java.io.IOException;

public class ImageFileController {
    public static String encBtn(MyImage myImage,String filePath,String password2){
        String keyCoordinate = null;
        try {
            Util.jpg2png(myImage.getimage(),Constants.IMAGENAME);
            myImage=new MyImage(Constants.IMAGENAME);
            keyCoordinate=App.encryptFile(myImage,filePath,password2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyCoordinate;
    };

    public static void decBtn(MyImage myImage,String filePath,String PWD){
        try {
            myImage=new MyImage(Constants.IMAGENAME);
            App.decryptFile(myImage,filePath,PWD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
