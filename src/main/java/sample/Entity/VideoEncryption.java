package sample.Entity;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 视频加密工具
 *
 * @author lxt
 * @date 2021/06/12
 */
public class VideoEncryption{
    private File file;

    /**
     * 得到待处理文件
     *
     * @return {@link File} 返回待处理文件
     */
    public File getNewFile() {
        return newFile;
    }

    private String code;
    private  File newFile;

    /**
     * 得到打包路径
     *
     * @return {@link String} 返回文件根目录路径
     */
    public String getPackingPath() {
        return packingPath;
    }

    private String packingPath;

    /**
     * 视频加密，封装了对视频文件的加密
     * 函数通过判断传入flag值（0或1）确定输出文件内容、形式，并通过密钥对文件进行对称呢个加密、解密操作
     * @param pathname 文件路径名
     * @param newName  待输出文件名
     * @param message  用于加密文件的密钥
     * @param flag     判断本函数加密文件或解密文件的函数
     */
    public VideoEncryption(String pathname,String newName,String message,int flag){
        this.code=message;
        file = new File(pathname);
        if(flag==0){
        String nFilePath=file.getParent()+"\\"+newName;
        this.newFile=new File(nFilePath);}
        else if (flag==1) {
            this.packingPath=file.getParent()+"\\"+"EncryptionResult";
            File encDir=new File(packingPath);
            encDir.mkdir();
            this.newFile = new File(this.packingPath+"\\"+newName);
        }
        else System.out.println("Video decryption mode error!");
        try {
            FileInputStream inputStream =new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(newFile);
            byte msg[]=code.getBytes(StandardCharsets.UTF_8);
            int length=msg.length;
            byte datain[]=new byte[1024*1024];
            int readLength;
            int i;
            int j=0;
            while((readLength=inputStream.read(datain,0,length))!=-1){
                for(i=0;i<readLength;i++,j++){
                    datain[i]= (byte) (datain[i]^msg[j]);
                    if((j+1)%length==0){
                        j=-1;
                    }
                }
                outputStream.write(datain,0,readLength);
            }
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
