package sample;

public class PicEncryptionController {
    public static void en(Pic pic,double key){
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
