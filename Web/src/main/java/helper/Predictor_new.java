package helper;

import java.io.IOException;
import java.util.ArrayList;

import data.StockDataImp;
import model.MarketState;
import model.Stock;
import utility.Constants;
import utility.HistoryData_new;
import utility.HistoryData_old;
import utility.MyDate;
import utility.Range_Date;
import utility.exception.Init_Fault_exception;
import utility.exception.NoStockInfo__exception;

public class Predictor_new implements Predict {

	static int Days = 7;
	static int N = 6;

	@Override
	public MarketState[] getPrediction(String id, MyDate date) throws Init_Fault_exception {
		double[] range = HistoryData_old.getRange(id);
		int i = getNum(id, date);
		StockDataImp ser = new StockDataImp();
		MyDate d = getDateBefore(date, 1);
		ArrayList<Stock> array = null;
		try {
			array = ser.getRecentStocks(id, date, Constants.manyDay);
		} catch (NoStockInfo__exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (array == null) {
			throw new Init_Fault_exception();
		}

		double pri = array.get(0).getEndprice();
		double low = pri * (1 + range[i]);
		double up = pri * (1 + range[i + 1]);

		MarketState[] ms = { new MarketState(up, low, date) };
		return ms;
	}

	static int getIndex(double d, double[] range) {
		int i = 0;
		assert (range != null) : ("The range is null!");
		while (d > range[i] && i <= 5) {
			++i;
		}
		if (i == 0) {
			i++;
		}
		return i - 1;
	}

	int getMax(double[] a) {
		int temp = 0;
		for (int i = 0; i < a.length; ++i) {
			if (a[i] >= a[temp]) {
				temp = i;
			}
		}
		return temp;
	}

	MyDate getDateBefore(MyDate d, int n) {
		MyDate t = d.clone();
		for (int i = 0; i < n; ++i) {
			t = t.beforeDay();
			while (!t.isWorkDay()) {
				t = t.beforeDay();
			}
		}
		return t;
	}

	public int getNum(String id, MyDate date) throws Init_Fault_exception {
		StockDataImp ser = new StockDataImp();
		// TODO Auto-generated method stub
		double[][] pro = new double[N][N];
		try {
			pro = HistoryData_new.getHistoryData(id);
		} catch (IOException e) {
			throw new Init_Fault_exception();
		}
		double[] rank = HistoryData_old.getRange(id);
		double[] state = new double[N];
		int[] index = new int[Days];

		MyDate d = date.clone();
		MyDate t = getDateBefore(d, Days);
		ArrayList<Stock> arraylist = new ArrayList<Stock>();
		try {
			arraylist = ser.getDataByID(id, new Range_Date(t, d));
		} catch (NoStockInfo__exception e) {
			throw new Init_Fault_exception();
		}

		while (arraylist.size() < 7) {
			t = getDateBefore(t, 1);
			try {
				arraylist = ser.getDataByID(id, new Range_Date(t, d));
			} catch (NoStockInfo__exception e) {
				throw new Init_Fault_exception();
			}
		}

		for (int i = 0; i < index.length; ++i) {
			if (Math.abs(arraylist.get(i).getRange()) < 200)
				index[i] = getIndex(arraylist.get(i).getRange(), rank);
		}

		/**
		 * initialize the state matrix
		 */
		int[] index2 = new int[Days];
		for (int i = 0; i < index.length; ++i) {
			index2[i] = index[Days - 1 - i];
		}

		state[index2[0]] = 0.5;
		for (int i = 0; i < N; ++i) {
			if (i != index2[0]) {
				state[i] = 0.1;
			}
		}

		for (int i = 1; i < Days; ++i) {
			int in = index2[i];
			double[] p = pro[in];
			for (int j = 0; j < N; ++j) {
				if (j != in) {
					state[j] += state[in] * p[j];
				}
			}
			state[in] = state[in] * p[in];
		}

		int i = getMax(state);
		double[] p_i = pro[i];
		for (int j = 0; j < N; ++j) {
			if (i != j) {
				state[j] += state[i] * p_i[j];
			}
		}
		state[i] = state[i] * p_i[i];

		i = getMax(state);
		return i;
	}

	public static void main(String[] args) throws NoStockInfo__exception, Init_Fault_exception {
		// String id = "sh600030";
		// double[] rank = HistoryData_old.getRange(id);
		Predictor_new pre = new Predictor_new();
		// StockDataImp s = new StockDataImp();
		// MyDate d = new MyDate(2016, 5, 10);
		// MyDate y = pre.getDateBefore(d, 100);
		// ArrayList<Stock> array = s.getDataByID(id, new Range_Date(y, d));
		// int tag = 0;
		// for (int i = 0; i < array.size(); ++i) {
		// d = array.get(i).getDate();
		// int t = Predictor_new.getIndex(array.get(i).getRange(), rank);
		// if (t == pre.getNum(id, pre.getDateBefore(d, 1))) {
		// tag++;
		// }
		// }
		//
		// System.out.println(tag + " " + array.size());
		MyDate d = new MyDate(2016, 5, 10);
		MarketState[] m = pre.getPrediction("sh600030", d);
		System.out.println(m[0].getUp() + " " + m[0].getDown());
	}

}
