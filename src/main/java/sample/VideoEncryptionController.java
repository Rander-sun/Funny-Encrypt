package sample;

public class VideoEncryptionController {
    public static void Enc(String filePath,String clearText){
        App.encryptVideo(filePath,clearText);
    }

    public static void Dec(String filePath,String encText,String priKey){
        App.decryptVideo(filePath,encText,priKey);
    }
}
