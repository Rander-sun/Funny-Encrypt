package sample;

import java.io.IOException;

public class ImageFileController {
    public static void encBtn(MyImage myImage,String filePath,String password2){
        try {
            String keyCoordinate=App.encryptFile(myImage,filePath,password2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    public static void decBtn(MyImage myImage,String filePath,String PWD){
        try {
            App.decryptFile(myImage,filePath,PWD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
