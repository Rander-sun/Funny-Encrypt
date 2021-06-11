package sample.Utils;

public class ArrayFunctions {
	//把二维数组行列倒换
	void arr_change(int[][] arr, int[][] temp, int M, int N)
	{
		for (int i = 0; i < M; ++i)
		{
			for (int j = 0; j < N; ++j)
				temp[j][i] = arr[i][j];
		}
	}

	//把arr1复制给arr2
	void arr_copy(double[] arr1, double[] arr2, int N)
	{
		if (N >= 0) System.arraycopy(arr1, 0, arr2, 0, N);
	}
}
