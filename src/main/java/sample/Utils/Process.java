package sample.Utils;

import sample.Entity.Pic;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * 图片加密处理过程
 *
 * @author yzh
 * @date 2021/06/12
 */
public class Process{

    /**
     * 处理RGBs,输出加密/解密后的图片
     *
     * @param src 被加密图片
     */
    public void CreateResultPic(Pic src){
        String newName="result"+src.getImageName();
        src.setImageName(newName);
        File file=new File(src.getImageName());
        try {
            ImageIO.write(src.getImage(),"JPG",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择排序，生成地址映射
     *
     * @param arr         混沌序列
     * @param length      长度
     * @param m           值——下标对应表
     * @param addressArr  地址映射数组
     */
    public static void SelectSort(double[] arr, int length, HashMap<Double, Integer> m, int[] addressArr) {
        if (arr == null || length <= 0){
            return;
        }
        int index = 0;
        for (int i = 0; i < length; ++i) {
            index = i;
            for (int j = i; j < length; ++j) {
                if (arr[j] < arr[index]){
                    index = j;
                }
            }
            if (index != i) {
                double temp;
                temp=arr[i];
                arr[i]=arr[index];
                arr[index]=temp;
            }
            int address=m.get(arr[i]);
            addressArr[address] = i;
        }
    }

    /**
     * 生成混沌数列
     *
     * @param x   初值
     * @param arr 混沌序列
     * @param N  arr长度
     */
    public static void produceLogisticArray(double x, double[] arr, int N) {
        double u = 3.9999999;
        arr[0] = x;
        for (int i = 1; i < N;++i) {
            arr[i] = u*arr[i - 1] * (1 - arr[i - 1]);
        }
    }

    /**
     * 生成值——下标对应表
     *
     * @param m             值——下标对应表
     * @param logisticArray 逻辑阵列
     * @param N         logisticArray长度
     */
    public static void produceMap(HashMap<Double, Integer> m, double[] logisticArray, int N) {
        for (int i = 0; i < N; ++i) {
            m.put(logisticArray[i], i);
        }

    }

    /**
     * 行加密
     *
     * @param color 颜色通道
     * @param key    加密参数
     * @param i     第i行
     * @param Width   图片宽
     * @return double
     */
    public double rowEncrypt(int[][] color, double key, int i, int Width) {
        ArrayFunctions af=new ArrayFunctions();
        double[] logisticArray = new double[Width] ;
        produceLogisticArray(key, logisticArray, Width);
        HashMap<Double, Integer> m=new HashMap<Double, Integer>();
        produceMap(m, logisticArray, Width);
        double[] tempLogArr =new double[Width];
        af.arr_copy(logisticArray, tempLogArr, Width);
        int[] addressArray =new int[Width];
        SelectSort(tempLogArr, Width, m, addressArray);
        int[] temp =new int[Width];
        for (int j = 0; j < Width; ++j) {
            temp[addressArray[j]] = color[i][j];
        }
        System.arraycopy(temp, 0, color[i], 0, Width);
        return logisticArray[Width - 1];
    }

    /**
     * 加密全部行
     *
     * @param color 颜色通道
     * @param key   加密参数
     * @param Height 图片高
     * @param Width  图片宽
     */
    public void allRowEncrypt(int[][] color, double key, int Height, int Width) {
        double x = key;
        for (int i = 0; i < Height; ++i) {
            x = rowEncrypt(color, x, i, Width);
        }
    }

    /**
     * 行解密
     *
     * @param color 颜色通道
     * @param key    加密参数
     * @param i     第i行
     * @param Width   图片宽
     * @return double
     */
    public double rowDecrypt(int[][] color, double key, int i, int Width) {
        ArrayFunctions af=new ArrayFunctions();
        double[] logisticArray =new double[Width];
        produceLogisticArray(key, logisticArray, Width);
        HashMap<Double, Integer> m=new HashMap<Double, Integer>();
        produceMap(m, logisticArray, Width);
        double[] tempLogArr =new double[Width];
        af.arr_copy(logisticArray, tempLogArr, Width);
        int[] addressArray =new int[Width];
        SelectSort(tempLogArr, Width, m, addressArray);
        int[] temp =new int[Width];
        for (int j = 0; j < Width; ++j) {
            temp[j] = color[i][addressArray[j]];
        }
        System.arraycopy(temp, 0, color[i], 0, Width);
        return logisticArray[Width - 1];
    }

    /**
     * 解密全部行
     *
     * @param color 颜色通道
     * @param key   加密参数
     * @param Height 图片高
     * @param Width  图片宽
     */
    public void allRowDecrypt(int[][] color, double key, int Height, int Width) {
        double x = key;
        for (int i = 0; i < Height; ++i) {
            x = rowDecrypt(color, x, i, Width);
        }
    }

    /**
     * 列加密
     *
     * @param color 颜色通道
     * @param key    加密参数
     * @param i     第i行
     * @param Width   图片宽
     * @return double
     */
    public double columnEncrypt(int[][] color, double key, int i, int Width) {
        ArrayFunctions af=new ArrayFunctions();
        double[] logisticArray =new double[Width];
        produceLogisticArray(key, logisticArray, Width);
        HashMap<Double, Integer> m=new HashMap<Double, Integer>();
        produceMap(m, logisticArray, Width);
        double[] tempLogArr =new double[Width];
        af.arr_copy(logisticArray, tempLogArr, Width);
        int[] addressArray =new int[Width];
        SelectSort(tempLogArr, Width, m, addressArray);
        int[] temp =new int[Width];
        for (int j = 0; j < Width; ++j) {
            temp[addressArray[j]] = color[i][j];
        }
        System.arraycopy(temp, 0, color[i], 0, Width);
        return logisticArray[Width - 1];
    }

    /**
     * 加密全部列
     *
     * @param color 颜色通道
     * @param key    加密参数
     * @param Height     图片高
     * @param Width   图片宽
     */
    public void allColumnEncrypt(int[][] color, double key, int Height, int Width) {
        ArrayFunctions af=new ArrayFunctions();
        int[][] temp =new int[Width][Height];
        af.arr_change(color,temp, Height, Width);
        double x = key;
        for (int i = 0; i < Width; ++i) {
            x = columnEncrypt(temp, x, i, Height);
        }
        int[][] temp2 =new int[Height][Width];
        af.arr_change(temp,temp2, Width, Height);
        for (int i = 0; i < Height; ++i) {
            System.arraycopy(temp2[i], 0, color[i], 0, Width);
        }
    }

    /**
     * 列解密
     *
     * @param color 颜色通道
     * @param key    加密参数
     * @param i     第i行
     * @param Width   图片宽
     * @return double
     */
    public double columnDecrypt(int[][] color, double key, int i, int Width) {
        ArrayFunctions af=new ArrayFunctions();
        double[] logisticArray =new double[Width];
        produceLogisticArray(key, logisticArray, Width);
        HashMap<Double, Integer> m=new HashMap<Double, Integer>();
        produceMap(m, logisticArray, Width);
        double[] tempLogArr =new double[Width];
        af.arr_copy(logisticArray, tempLogArr, Width);
        int[] addressArray =new int[Width];
        SelectSort(tempLogArr, Width, m, addressArray);
        int[] temp =new int[Width];
        for (int j = 0; j < Width; ++j) {
            temp[j] = color[i][addressArray[j]];
        }
        System.arraycopy(temp, 0, color[i], 0, Width);
        return logisticArray[Width - 1];
    }

    /**
     * 解密全部列
     *
     * @param color 颜色通道
     * @param key   加密参数
     * @param Height 图片高
     * @param Width  图片宽
     */
    public void allColumnDecrypt(int[][] color, double key, int Height, int Width) {
        ArrayFunctions af=new ArrayFunctions();
        int[][] temp =new int[Width][Height];
        af.arr_change(color, temp, Height, Width);
        double x = key;
        for (int i = 0; i < Width; ++i) {
            x = columnDecrypt(temp, x, i, Height);
        }
        int[][] temp2 =new int[Height][Width];
        af.arr_change(temp, temp2, Width, Height);
        for (int i = 0; i < Height; ++i) {
            System.arraycopy(temp2[i], 0, color[i], 0, Width);
        }
    }

    /**
     * 加密
     *
     * @param color 颜色通道
     * @param key   加密参数
     * @param Height 图片高
     * @param Width  图片宽
     */
    public void encrypt(int[][] color, double key, int Height, int Width) {
        allRowEncrypt(color, key, Height, Width);
        allColumnEncrypt(color, key, Height, Width);
    }

    /**
     * 解密
     *
     * @param color 颜色通道
     * @param key   加密参数
     * @param Height 图片高
     * @param Width  图片宽
     */
    public void decrypt(int[][] color, double key, int Height, int Width) {
        allColumnDecrypt(color, key, Height, Width);
        allRowDecrypt(color, key, Height, Width);
    }
}
