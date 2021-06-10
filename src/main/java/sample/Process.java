package sample;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Process{
    //处理RGBs,输出加密/解密后的图片
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

    //加解密时使用，选择法排序，同时生成地址映射表
    public static void SelectSort(double[] arr, int length, HashMap<Double, Integer> m, int[] address_arr) {
        if (arr == null || length <= 0)return;
        int index = 0;
        for (int i = 0; i < length; ++i) {
            index = i;
            for (int j = i; j < length; ++j) {
                if (arr[j] < arr[index])index = j;
            }
            if (index != i) {
                double temp;
                temp=arr[i];
                arr[i]=arr[index];
                arr[index]=temp;
            }

            int address=m.get(arr[i]);
            address_arr[address] = i;
        }
    }

    //生成一个混沌序列，以x为初值
    public static void produce_logisticArray(double x, double[] arr, int N) {
        double u = 3.9999999;
        arr[0] = x;
        for (int i = 1; i < N; ++i) {
            arr[i] = u*arr[i - 1] * (1 - arr[i - 1]);
        }
    }

    //通过混沌序列，生成 值-下标 的反向映射
    public static void produce_map(HashMap<Double, Integer> m, double[] logistic_array, int N) {
        for (int i = 0; i < N; ++i) {
            m.put(logistic_array[i], i);
        }

    }

    //行置乱算法
    public double rowEncrypt(int[][] pixel, double x1, int i, int M, int N) {
        ArrayFunctions af=new ArrayFunctions();
        double[] logistic_array = new double[N] ;
        produce_logisticArray(x1, logistic_array, N);
        HashMap<Double, Integer> m=new HashMap<Double, Integer>();
        produce_map(m, logistic_array, N);
        double[] temp_logArr =new double[N];
        af.arr_copy(logistic_array, temp_logArr, N);
        int[] address_array =new int[N];
        SelectSort(temp_logArr, N, m, address_array);
        int[] temp =new int[N];
        for (int j = 0; j < N; ++j) {
            temp[address_array[j]] = pixel[i][j];
        }
        System.arraycopy(temp, 0, pixel[i], 0, N);
        return logistic_array[N - 1];
    }

    //行置乱接口
    public void rowEncrypt_interface(int[][] pixel, double x1, int M, int N)
    {
        double x = x1;
        for (int i = 0; i < M; ++i) {
            x = rowEncrypt(pixel, x, i, M,N);
        }
    }

    //列置乱算法
    public double columnEncrypt(int[][] pixel, double x1, int i, int M, int N)
    {
        ArrayFunctions af=new ArrayFunctions();
        double[] logistic_array =new double[N];
        produce_logisticArray(x1, logistic_array, N);
        HashMap<Double, Integer> m=new HashMap<Double, Integer>();
        produce_map(m, logistic_array, N);
        double[] temp_logArr =new double[N];
        af.arr_copy(logistic_array, temp_logArr, N);
        int[] address_array =new int[N];
        SelectSort(temp_logArr, N, m, address_array);
        int[] temp =new int[N];
        for (int j = 0; j < N; ++j) {
            temp[address_array[j]] = pixel[i][j];
        }
        System.arraycopy(temp, 0, pixel[i], 0, N);
        return logistic_array[N - 1];
    }

    //列置乱接口
    public void columnEncrypt_interface(int[][] pixel, double x1, int M, int N) {
        ArrayFunctions af=new ArrayFunctions();
        int[][] temp =new int[N][M];
        af.arr_change(pixel,temp, M, N);
        double x = x1;
        for (int i = 0; i < N; ++i) {
            x = columnEncrypt(temp, x, i,N ,M);
        }
        int[][] temp2 =new int[M][N];
        af.arr_change(temp,temp2, N, M);
        for (int i = 0; i < M; ++i) {
            System.arraycopy(temp2[i], 0, pixel[i], 0, N);
        }
    }

    //行解密算法
    public double rowDecrypt(int[][] pixel, double x1, int i, int N)
    {
        ArrayFunctions af=new ArrayFunctions();
        double[] logistic_array =new double[N];
        produce_logisticArray(x1, logistic_array, N);
        HashMap<Double, Integer> m=new HashMap<Double, Integer>();
        produce_map(m, logistic_array, N);
        double[] temp_logArr =new double[N];
        af.arr_copy(logistic_array, temp_logArr, N);
        int[] address_array =new int[N];
        SelectSort(temp_logArr, N, m, address_array);
        int[] temp =new int[N];
        for (int j = 0; j < N; ++j) {
            temp[j] = pixel[i][address_array[j]];
        }
        System.arraycopy(temp, 0, pixel[i], 0, N);
        return logistic_array[N - 1];
    }

    //行解密接口
    public void rowDecrypt_interface(int[][] pixel, double x1, int M, int N) {
        double x = x1;
        for (int i = 0; i < M; ++i) {
            x = rowDecrypt(pixel, x, i, N);
        }
    }

    //列解密算法
    public double columnDecrypt(int[][] pixel, double x1, int i, int M, int N)
    {
        ArrayFunctions af=new ArrayFunctions();
        double[] logistic_array =new double[N];
        produce_logisticArray(x1, logistic_array, N);
        HashMap<Double, Integer> m=new HashMap<Double, Integer>();
        produce_map(m, logistic_array, N);
        double[] temp_logArr =new double[N];
        af.arr_copy(logistic_array, temp_logArr, N);
        int[] address_array =new int[N];
        SelectSort(temp_logArr, N, m, address_array);
        int[] temp =new int[N];
        for (int j = 0; j < N; ++j) {
            temp[j] = pixel[i][address_array[j]];
        }
        System.arraycopy(temp, 0, pixel[i], 0, N);
        return logistic_array[N - 1];
    }

    //列解密接口
    public void columnDecrypt_interface(int[][] pixel, double x1, int M, int N) {
        ArrayFunctions af=new ArrayFunctions();
        int[][] temp =new int[N][M];
        af.arr_change(pixel, temp, M, N);
        double x = x1;
        for (int i = 0; i < N; ++i) {
            x = columnDecrypt(temp, x, i, N, M);

        }
        int[][] temp2 =new int[M][N];
        af.arr_change(temp, temp2, N, M);
        for (int i = 0; i < M; ++i) {
            System.arraycopy(temp2[i], 0, pixel[i], 0, N);
        }
    }

    //置乱
    public void encrypt(int[][] pixel, double x1, int M, int N) {
        rowEncrypt_interface(pixel, x1, M, N);
        columnEncrypt_interface(pixel, x1, M, N);
    }

    //解密
    void decrypt(int[][] pixel, double x1, int M, int N) {
        columnDecrypt_interface(pixel, x1, M, N);
        rowDecrypt_interface(pixel, x1, M, N);

    }
}
