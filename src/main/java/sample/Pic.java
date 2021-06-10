package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pic {
    private BufferedImage Image;
    private int [][]RGBs;
    private int Width;
    private int Height;
    private String ImageName;
    private int [][]Red;
    private int [][]Green;
    private int [][]Blue;

    private int [][]A;

    //获取图片
    public Pic(String Address){
        File file=new File(Address);
        try {
            Image= ImageIO.read(file);
            ImageName=file.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pic() {

    }

    //获取图片上坐标为（x,y）的像素点的RGB值,并添加到RGBs(该图片的RGB列表)中
    public void setRGB_XY(int x,int y){
        int []RGB_XY=new int[6];
        RGB_XY[0]=x;
        RGB_XY[1]=y;
        if(y<Width&&x<Height){
            int p=Image.getRGB(y,x);
            RGB_XY[2]=(p>>16)&0xff;
            RGB_XY[3]=(p>>8)&0xff;
            RGB_XY[4]=p&0xff;
            RGB_XY[5]=(p>>24) & 0xff;
            RGBs[x][y]=p;
            Red[x][y]=RGB_XY[2];
            Green[x][y]=RGB_XY[3];
            Blue[x][y]=RGB_XY[4];
            A[x][y]=RGB_XY[5];
        }
    }

    //设置所有像素点的RGB
    public void setRGBs(){
        for (int i=0;i<Height;i++){
            for (int j=0;j<Width;j++){
                setRGB_XY(i,j);
            }
        }
    }

    //获取某一点的RGB
    public int getRGB_XY(int x,int y){
        return RGBs[x][y];
    }

    //获取图片的RGB
    public int[][] getRGBs(){
        return RGBs;
    }

    //修改某个像素点的RGB
    public void editRGB_XY(int x,int y){
        int p=RGBs[x][y];
        p=(A[x][y]<<24)|(Red[x][y]<<16)|(Green[x][y]<<8)|Blue[x][y];
        Image.setRGB(y,x,p);
        RGBs[x][y]=p;
    }

    //根据通道值修改RGBs
    public void editRGBs(){
        for(int i=0;i<Height;i++){
            for (int j=0;j<Width;j++){
                editRGB_XY(i,j);
            }
        }
    }

    public BufferedImage getImage(){
        return Image;
    }

    public int getWidth(){
        return Width;
    }

    public int getHeight(){
        return Height;
    }

    public void setWidth(int width){
        Width=width;
    }

    public void setHeight(int height){
        Height=height;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public int[][] getRed() {
        return Red;
    }

    public int[][] getGreen() {
        return Green;
    }

    public int[][] getBlue() {
        return Blue;
    }

    public void iniRed() {
        Red = new int[Height][Width];
    }

    public void iniGreen() {
        Green = new int[Height][Width];
    }

    public void iniBlue() {
        Blue = new int[Height][Width];
    }

    public void iniRGBs(){
        RGBs=new int[Height][Width];
    }

    public void iniA(){
        A=new int[Height][Width];
    }


    public void setRed(int[][] red) {
        Red = red;
    }

    public void setGreen(int[][] green) {
        Green = green;
    }

    public void setBlue(int[][] blue) {
        Blue = blue;
    }

}
