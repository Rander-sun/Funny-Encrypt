package sample.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片
 *
 * @author yzh
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
     * 设置图片
     *
     * @param Address 地址
     */
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
     * 设置坐标为（x,y）的像素的rgb，并添加到RGBs(该图片的RGB列表)中
     *
     * @param x x
     * @param y y
     */
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
     * 设置所有像素点rgb
     */
    public void setRGBs(){
        for (int i=0;i<Height;i++){
            for (int j=0;j<Width;j++){
                setRGB_XY(i,j);
            }
        }
    }

    /**
     * 获取某一点的RGB
     *
     * @param x x
     * @param y y
     * @return int RGB值
     */
    public int getRGB_XY(int x,int y){
        return RGBs[x][y];
    }

    /**
     * 获取图片的RGB
     *
     * @return {@link int[][]}
     */
    public int[][] getRGBs(){
        return RGBs;
    }

    /**
     * 修改某个像素点的RGB
     *
     * @param x x
     * @param y y
     */
    public void editRGB_XY(int x,int y){
        int p=RGBs[x][y];
        p=(A[x][y]<<24)|(Red[x][y]<<16)|(Green[x][y]<<8)|Blue[x][y];
        Image.setRGB(y,x,p);
        RGBs[x][y]=p;
    }

    /**
     * 根据通道值修改RGBs
     */
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
     * 获得红色通道值
     *
     * @return {@link int[][]}
     */
    public int[][] getRed() {
        return Red;
    }

    /**
     * 获得绿色通道值
     *
     * @return {@link int[][]}
     */
    public int[][] getGreen() {
        return Green;
    }

    /**
     * 获得蓝色通道值
     *
     * @return {@link int[][]}
     */
    public int[][] getBlue() {
        return Blue;
    }

    /**
     * 初始化红通道
     */
    public void iniRed() {
        Red = new int[Height][Width];
    }

    /**
     * 初始化绿通道
     */
    public void iniGreen() {
        Green = new int[Height][Width];
    }

    /**
     * 初始化蓝通道
     */
    public void iniBlue() {
        Blue = new int[Height][Width];
    }

    /**
     * 初始化RGB
     */
    public void iniRGBs(){
        RGBs=new int[Height][Width];
    }

    /**
     * 初始化灰度
     */
    public void iniA(){
        A=new int[Height][Width];
    }


    /**
     * 设置红
     *
     * @param red 红通道值
     */
    public void setRed(int[][] red) {
        Red = red;
    }

    /**
     * 设置绿
     *
     * @param green 绿通道值
     */
    public void setGreen(int[][] green) {
        Green = green;
    }

    /**
     * 设置蓝
     *
     * @param blue 蓝通道值
     */
    public void setBlue(int[][] blue) {
        Blue = blue;
    }

}
