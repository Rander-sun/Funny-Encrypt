package sample.Utils;

/**
 * 数组函数
 *
 * @author Hasee
 * @date 2021/06/12
 */
public class ArrayFunctions {

	/**
	 * 加勒比海盗的变化
	 *
	 * @param arr  加勒比海盗
	 * @param temp 临时
	 * @param M    米
	 * @param N    n
	 *///把二维数组行列倒换
	void arr_change(int[][] arr, int[][] temp, int M, int N)
	{
		for (int i = 0; i < M; ++i)
		{
			for (int j = 0; j < N; ++j)
				temp[j][i] = arr[i][j];
		}
	}

	/**
	 * 加勒比海盗副本
	 *
	 * @param arr1 arr1
	 * @param arr2 arr2
	 * @param N    n
	 *///把arr1复制给arr2
	void arr_copy(double[] arr1, double[] arr2, int N)
	{
		if (N >= 0) System.arraycopy(arr1, 0, arr2, 0, N);
	}
}
