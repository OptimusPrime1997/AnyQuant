package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HistoryData_new {

	static int N = 6;

	static String getAddr(String s) {
		return "out/" + s + ".txt";
	}

	public static double[][] getHistoryData(String id) throws IOException {
		double[][] result = new double[N][N];
		BufferedReader br = new BufferedReader(
				new InputStreamReader(HistoryData_old.class.getClassLoader().getResourceAsStream(getAddr(id))));
		String s = "";

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				s = br.readLine();
				result[i][j] = Double.parseDouble(s);
			}
		}
		return result;
	}

	public static void print(double[][] a) {
		for (int i = 0; i < a.length; ++i) {
			for (int j = 0; j < a[0].length; ++j) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		try {
			String[] str = { "sh600030", "sh600036", "sh600048", "sh600050", "sh600052", "sh600118", "sh600151",
					"sh600159", "sh600185", "sh600271", "sh600284", "sh600293", "sh600316", "sh600330", "sh600345",
					"sh600369", "sh600391", "sh600449", "sh600455", "sh600533", "sh600568", "sh600677", "sh600678",
					"sh600696", "sh600764", "sh600837", "sh600855", "sh600893", "sh600999", "sh601166", "sh601519",
					"sh601688", "sh601788", "sh601789", "sz000014", "sz000029", "sz000558", "sz000619", "sz000728",
					"sz000768", "sz002027", "sz002177", "sz002302", "sz002398", "sz002609", "sz002652", "sz002671",
					"sz300183", "sz300279", "sz300330" };

			for (String s : str) {
				print(HistoryData_new.getHistoryData(s));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
