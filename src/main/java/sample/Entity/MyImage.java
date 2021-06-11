package sample.Entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;

/**
 * 我的形象
 *
 * @author Hasee
 * @date 2021/06/12
 */
public class MyImage {
  private BufferedImage image;
  public Map<Integer,Integer> coordinate;
  private ArrayList<Integer[]> RGB=new ArrayList<Integer[]>();

  /**
   * getimage
   *
   * @return {@link BufferedImage}
   */
  public BufferedImage getimage() {
    return image;
  }

  /**
   * setimage
   *
   * @param image 图像
   */
  public void setimage(BufferedImage image) {
    this.image = image;
  }

  /**
   * 我的形象
   */
  public MyImage(){}

  /**
   * 我的形象
   *
   * @param imageAddress 图片地址
   */
  public MyImage(String imageAddress){
    File file = new File(imageAddress);
    try{
      image= ImageIO.read(file);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
   * 得到图像的像素
   *
   * @param image 图像
   * @param x     x
   * @param y     y
   * @return {@link int[]}
   */
  public int[] getImagePixel(BufferedImage image,int x,int y) {
    int width = image.getWidth();
    int height = image.getHeight();
    int[] argb = new int[4];
    if(x<width&&y<height) {
      int p = image.getRGB(x, y);
      argb[0] = (p >> 24) & 0xff;
      argb[1] = (p >> 16) & 0xff;
      argb[2] = (p >> 8) & 0xff;
      argb[3] = p & 0xff;
    }else return null;
    return argb;
  }

  /**
   * 设置图像像素
   *
   * @param image 图像
   * @param a     一个
   * @param r     r
   * @param g     g
   * @param b     b
   * @param x     x
   * @param y     y
   * @return {@link String}
   */
  public String  setImagePixel(BufferedImage image,int a,int r,int g,int b,int x,int y){
    int p=(a<<24)|(r<<16)|(g<<8)|b;
    image.setRGB(x,y,p);
    return String.format("%03x",x)+" "+String.format("%03x",y)+" ";
  }

  /**
   * 创协调
   *
   * @param image 图像
   * @param n     n
   */
  public void genCoordinate(BufferedImage image,int n){
    int width = image.getWidth();
    int height = image.getHeight();
    int x,y;
    Random random=new Random();
    Map<Integer,Integer> coo=new HashMap<>();
    while(coo.size()<n){
      x=random.nextInt(width-1)+1;
      y=random.nextInt(height-1)+1;
      coo.put(x,y);
    }
    this.coordinate=new HashMap<>(coo);

  }
}
