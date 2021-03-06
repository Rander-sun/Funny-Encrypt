package sample.Controller;

import sample.Entity.Pic;
import sample.Utils.Process;

/**
 * 图片加密控制器
 *
 * @author yzh
 * @date 2021/06/12
 */
public class PicEncryptionController {

    /**
     * 在
     *
     * @param pic 图片
     * @param key 加密参数
     */
    public static void en(Pic pic, double key){
        pic.setHeight(pic.getImage().getHeight());
        pic.setWidth(pic.getImage().getWidth());
        pic.iniA();
        pic.iniRed();
        pic.iniGreen();
        pic.iniBlue();
        pic.iniRGBs();
        pic.setRGBs();
        Process process=new Process();
        process.encrypt(pic.getRed(),key,pic.getHeight(),pic.getWidth());
        process.encrypt(pic.getGreen(),key,pic.getHeight(),pic.getWidth());
        process.encrypt(pic.getBlue(),key,pic.getHeight(),pic.getWidth());
        pic.editRGBs();
        process.CreateResultPic(pic);
    }

    /**
     * 德
     *
     * @param pic 图片
     * @param key 加密参数
     */
    public static void de(Pic pic,double key){
        pic.setHeight(pic.getImage().getHeight());
        pic.setWidth(pic.getImage().getWidth());
        pic.iniA();
        pic.iniRed();
        pic.iniGreen();
        pic.iniBlue();
        pic.iniRGBs();
        pic.setRGBs();
        Process process=new Process();
        process.decrypt(pic.getBlue(),key,pic.getHeight(),pic.getWidth());
        process.decrypt(pic.getRed(),key,pic.getHeight(),pic.getWidth());
        process.decrypt(pic.getGreen(),key,pic.getHeight(),pic.getWidth());
        pic.editRGBs();
        process.CreateResultPic(pic);
    }

}
