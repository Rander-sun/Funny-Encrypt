package sample;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class VideoEncryption{
    private File file;

    public File getNewFile() {
        return newFile;
    }

    private String code;
    private  File newFile;

    public String getPackingPath() {
        return packingPath;
    }

    private String packingPath;

    VideoEncryption(String pathname,String newName,String message,int flag){
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
