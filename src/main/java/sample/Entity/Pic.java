package sample.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片
 *
 * @author Hasee
 * @date 2021/06/12
 */
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

    /**
     * 图片
     *
     * @param Address 地址
     *///获取图片
    public Pic(String Address){
        File file=new File(Address);
        try {
            Image= ImageIO.read(file);
            ImageName=file.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片
     */
    public Pic() {

    }

    /**
     * 设置rgb xy
     *
     * @param x x
     * @param y y
     *///获取图片上坐标为（x,y）的像素点的RGB值,并添加到RGBs(该图片的RGB列表)中
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

    /**
     * 设置rgb
     *///设置所有像素点的RGB
    public void setRGBs(){
        for (int i=0;i<Height;i++){
            for (int j=0;j<Width;j++){
                setRGB_XY(i,j);
            }
        }
    }

    /**
     * 得到的rgb xy
     *
     * @param x x
     * @param y y
     * @return int
     *///获取某一点的RGB
    public int getRGB_XY(int x,int y){
        return RGBs[x][y];
    }

    /**
     * 得到的rgb
     *
     * @return {@link int[][]}
     *///获取图片的RGB
    public int[][] getRGBs(){
        return RGBs;
    }

    /**
     * 编辑rgb xy
     *
     * @param x x
     * @param y y
     *///修改某个像素点的RGB
    public void editRGB_XY(int x,int y){
        int p=RGBs[x][y];
        p=(A[x][y]<<24)|(Red[x][y]<<16)|(Green[x][y]<<8)|Blue[x][y];
        Image.setRGB(y,x,p);
        RGBs[x][y]=p;
    }

    /**
     * 编辑rgb
     *///根据通道值修改RGBs
    public void editRGBs(){
        for(int i=0;i<Height;i++){
            for (int j=0;j<Width;j++){
                editRGB_XY(i,j);
            }
        }
    }

    /**
     * 得到图像
     *
     * @return {@link BufferedImage}
     */
    public BufferedImage getImage(){
        return Image;
    }

    /**
     * 得到宽度
     *
     * @return int
     */
    public int getWidth(){
        return Width;
    }

    /**
     * 得到高度
     *
     * @return int
     */
    public int getHeight(){
        return Height;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     */
    public void setWidth(int width){
        Width=width;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     */
    public void setHeight(int height){
        Height=height;
    }

    /**
     * 得到图像名称
     *
     * @return {@link String}
     */
    public String getImageName() {
        return ImageName;
    }

    /**
     * 设置图片名称
     *
     * @param imageName 图片的名字
     */
    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    /**
     * 红起来
     *
     * @return {@link int[][]}
     */
    public int[][] getRed() {
        return Red;
    }

    /**
     * 得到绿色
     *
     * @return {@link int[][]}
     */
    public int[][] getGreen() {
        return Green;
    }

    /**
     * 得到蓝
     *
     * @return {@link int[][]}
     */
    public int[][] getBlue() {
        return Blue;
    }

    /**
     * ini红
     */
    public void iniRed() {
        Red = new int[Height][Width];
    }

    /**
     * ini绿色
     */
    public void iniGreen() {
        Green = new int[Height][Width];
    }

    /**
     * ini蓝色
     */
    public void iniBlue() {
        Blue = new int[Height][Width];
    }

    /**
     * ini rgb
     */
    public void iniRGBs(){
        RGBs=new int[Height][Width];
    }

    /**
     * inia
     */
    public void iniA(){
        A=new int[Height][Width];
    }


    /**
     * 设置红
     *
     * @param red 红色的
     */
    public void setRed(int[][] red) {
        Red = red;
    }

    /**
     * 集绿色
     *
     * @param green 绿色
     */
    public void setGreen(int[][] green) {
        Green = green;
    }

    /**
     * 设置蓝色
     *
     * @param blue 蓝色的
     */
    public void setBlue(int[][] blue) {
        Blue = blue;
    }

}
