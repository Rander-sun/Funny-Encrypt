package sample.Controller;

import sample.Utils.App;

/**
 * 视频加密控制器
 *
 * @author lxt
 * @date 2021/06/12
 */
public class VideoEncryptionController {

    /**
     * 中转页面接口数据，调用解密方法
     *
     * @param filePath  文件路径
     * @param clearText 明文
     */
    public static void Enc(String filePath,String clearText){
        App.encryptVideo(filePath,clearText);
    }

    /**
     * 中转页面接口数据，调用加密方法
     *
     * @param filePath 文件路径
     * @param encText  待解密内容
     * @param priKey   私钥
     */
    public static void Dec(String filePath,String encText,String priKey){
        App.decryptVideo(filePath,encText,priKey);
    }
}
