package sample.Controller;

import sample.Utils.App;

/**
 * 视频加密控制器
 *
 * @author Hasee
 * @date 2021/06/12
 */
public class VideoEncryptionController {

    /**
     * 内附
     *
     * @param filePath  文件路径
     * @param clearText 明文
     */
    public static void Enc(String filePath,String clearText){
        App.encryptVideo(filePath,clearText);
    }

    /**
     * 12月
     *
     * @param filePath 文件路径
     * @param encText  内附文本
     * @param priKey   革命制度党关键
     */
    public static void Dec(String filePath,String encText,String priKey){
        App.decryptVideo(filePath,encText,priKey);
    }
}
