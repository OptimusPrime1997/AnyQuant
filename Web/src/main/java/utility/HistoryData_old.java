package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.StockDataImp;
import utility.exception.Init_Fault_exception;

/**
 * the class to get the history data of the probability
 * 
 * @author jiaorun
 *
 */
public class HistoryData_old {

	/**
	 * the number of chosed stocks
	 */
	static int Num = 51;

	/**
	 * size of arrays
	 */
	static int N = 6;

	static boolean inited = false;
	static Map<String, double[][][]> value = new HashMap<String, double[][][]>();

	static String[] ids = { "sh600030", "sh600036", "sh600048", "sh600050", "sh600052", "sh600118", "sh600151",
			"sh600159", "sh600185", "sh600271", "sh600284", "sh600293", "sh600316", "sh600330", "sh600345", "sh600369",
			"sh600391", "sh600449", "sh600455", "sh600533", "sh600568", "sh600677", "sh600678", "sh600696", "sh600764",
			"sh600837", "sh600855", "sh600893", "sh600999", "sh601166", "sh601519", "sh601688", "sh601788", "sh601789",
			"sz000014", "sz000029", "sz000558", "sz000619", "sz000728", "sz000768", "sz002027", "sz002177", "sz002302",
			"sz002398", "sz002609", "sz002652", "sz002671", "sz300183", "sz300279", "sz300330" };

	static Map<String, double[]> margin = new HashMap<String, double[]>();

	/**
	 * the function to get the probability matrix of stock id
	 * 
	 * @param id
	 * @return
	 * @throws Init_Fault_exception
	 */
	public static double[][][] getHistoryData(String id) throws Init_Fault_exception {
		boolean bool = false;
		if (!inited) {
			bool = initAll();
			if (bool) {
				inited = true;
				return value.get(id);
			} else {
				throw new Init_Fault_exception();
			}
		} else {
			return value.get(id);
		}
	}

	/**
	 * get the range correspond to the number
	 * 
	 * @param id
	 * @return
	 */
	public static double[] getRange(String id) {
		if (inited)
			return margin.get(id);
		else {
			boolean bool = false;
			bool = initAll();
			if (bool) {
				inited = true;
				return margin.get(id);
			} else {
				return null;
			}
		}
	}

	/**
	 * the function to init the value map
	 * 
	 * @return
	 */
	public static boolean initAll() {
		StockDataImp ser = new StockDataImp();
		ArrayList<String> arr = ser.getLoacalStockIds();
		ids = convert2Array(arr);

		boolean ready = false;
		int n = 0;
		for (String s : ids) {
			value.put(s, new double[N][N][N]);
			try {
				init(value.get(s), s);
				n++;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (n == Num) {
			ready = true;
		}
		try {
			initMargin();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ready = false;
		}
		return ready;
	}

	/**
	 * init the value of the matrix of the specified stock
	 * 
	 * @param x
	 * @param s
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	static boolean init(double[][][] x, String s) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(HistoryData_old.class.getClassLoader().getResourceAsStream(getAddr(s))));

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				for (int k = 0; k < N; ++k) {
					value.get(s)[k][j][i] = Double.parseDouble(br.readLine());
				}
			}
		}

		br.close();
		return true;
	}

	/**
	 * convert an arraylist to an array
	 * 
	 * @param a
	 * @return
	 */
	static String[] convert2Array(ArrayList<String> a) {
		String[] s = new String[a.size() + 1];
		for (int i = 1; i < a.size() + 1; ++i) {
			s[i] = a.get(i - 1);
		}
		s[0] = "hs300";
		return s;
	}

	/**
	 * get file address
	 * 
	 * @param s
	 * @return
	 */
	public static String getAddr(String s) {
		return "pro/" + s + ".txt";
	}

	public static void initMargin() throws IOException {
		System.out.println(HistoryData_old.class.getClassLoader().getResourceAsStream("range/range.txt") + "\n----\n"
				+ HistoryData_old.class.getClassLoader().getResourceAsStream("range/range.txt"));
		BufferedReader br = new BufferedReader(
				new InputStreamReader(HistoryData_old.class.getClassLoader().getResourceAsStream("range/range.txt")));
		for (int i = 0; i < ids.length; ++i) {
			String string = br.readLine();
			String[] str = string.split(" ");
			double[] temp = new double[7];
			int k = 0;
			for (int j = 0; j < str.length; ++j) {
				if (str[j] != null && !str[j].equals(" ")) {
					temp[k] = Double.parseDouble(str[j]);
					++k;
				}
			}
			margin.put(ids[i], temp);

		}
		br.close();
	}

	public static void main(String[] args) throws Init_Fault_exception {

		double[] x = getRange("sh600533");
		for (double y : x) {
			System.out.println(y);
		}

		// for (String s : ids) {
		// System.out.println(s);
		// double[][][] t = getHistoryData(s);
		// for (int i = 0; i < 5; ++i) {
		// System.out.println("t(:,:," + i + ")");
		// for (int j = 0; j < 5; ++j) {
		// for (int k = 0; k < 5; ++k) {
		// System.out.print(t[j][k][i] + " ");
		// }
		// System.out.println();
		// }
		// }
		//
		// }

		System.out.println("Success..");

	}

}
