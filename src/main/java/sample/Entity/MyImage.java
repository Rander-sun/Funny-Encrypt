package sample.Entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;

public class MyImage {
  private BufferedImage image;
  public Map<Integer,Integer> coordinate;
  private ArrayList<Integer[]> RGB=new ArrayList<Integer[]>();
  public BufferedImage getimage() {
    return image;
  }

  public void setimage(BufferedImage image) {
    this.image = image;
  }
  public MyImage(){}
  public MyImage(String imageAddress){
    File file = new File(imageAddress);
    try{
      image= ImageIO.read(file);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
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
  public String  setImagePixel(BufferedImage image,int a,int r,int g,int b,int x,int y){
    int p=(a<<24)|(r<<16)|(g<<8)|b;
    image.setRGB(x,y,p);
    return String.format("%03x",x)+" "+String.format("%03x",y)+" ";
  }
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
