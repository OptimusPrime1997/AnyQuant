package helper;

import java.util.ArrayList;

import data.StockDataImp;
import dataservice.StockDataService;
import model.MarketState;
import model.Stock;
import utility.Constants;
import utility.HistoryData_old;
import utility.MyDate;
import utility.NumHelper;
import utility.exception.Init_Fault_exception;
import utility.exception.NoStockInfo__exception;

/**
 * the class to implement the prediction methods
 * 
 * @author jiaorun
 */

public class Predictor_old implements Predict {

	/*
	 * 
	 * to use the history data,we find it difficult to get all the history data
	 * involved ,and we only have about 2000 days data,so we try to classify a
	 * single day's data into several classes,and use 2 days data to predict the
	 * next day. we predict according to the max probability
	 *
	 * 
	 */

	StockDataService service;

	/*
	 * number of history data used
	 */
	static int N = 2;

	public Predictor_old() {
		service = new StockDataImp();
	}

	/**
	 * the interface to get prediction of a stock
	 */

	@Override
	public MarketState[] getPrediction(String id, MyDate date) throws Init_Fault_exception {
		MyDate bef = date.clone();

		/*
		 * get the date N days ago
		 */
		for (int i = 0; i < N; ++i) {
			bef = bef.beforeDay();
			while (!bef.isWorkDay()) {
				bef = bef.beforeDay();
			}
		}

		ArrayList<Stock> array = null;

		/*
		 * get the two days data recently
		 */
		try {
			array = service.getRecentStocks(id, date, Constants.manyDay);
		} catch (NoStockInfo__exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new Init_Fault_exception();
		}

		/*
		 * get the statistic data probability and margins
		 */
		double[][][] pro = HistoryData_old.getHistoryData(id);
		double[] range = HistoryData_old.getRange(id);

		/*
		 * up bound and the low bound
		 */
		double[] up = new double[4];
		double[] down = new double[4];

		up[0] = array.get(0).getEndprice();
		down[0] = up[0];

		int[] index = new int[5];

		/*
		 * history data used
		 */
		index[0] = getIndex(array.get(1).getRange(), range);
		index[1] = getIndex(array.get(0).getRange(), range);

		/*
		 * set the up and low bound
		 */
		for (int i = 1; i < 4; ++i) {
			index[i + 1] = getPro(index[i - 1], index[i], pro);
			int t = index[i + 1];
			up[i] = up[i - 1] * range[t + 1] + up[i - 1];
			down[i] = down[i - 1] * range[t] + down[i - 1];
		}
		MyDate date1 = date.afterWorkDay();
		MyDate date2 = date1.afterWorkDay();
		MyDate date3 = date2.afterWorkDay();

		MarketState[] result = { new MarketState(NumHelper.toFixed(up[1], 2), NumHelper.toFixed(down[1], 2), date1),
				new MarketState(NumHelper.toFixed(up[2], 2), NumHelper.toFixed(down[2], 2), date2),
				new MarketState(NumHelper.toFixed(up[3], 2), NumHelper.toFixed(down[3], 2), date3) };

		return result;
	}

	static int getIndex(double d, double[] range) {
		int i = 0;
		assert (range != null) : ("The range is null!");
		while (d > range[i] && i <= 5) {
			++i;
		}
		if (i == 0) {
			++i;
		}
		return i;
	}

	/*
	 * get the index of probability according to the indexs
	 */
	static int getPro(int a, int b, double[][][] po) {
		int temp = 0;
		// System.out.print(a + " " + b);
		for (int i = 0; i < po[0][0].length; ++i) {
			if (po[a - 1][b - 1][i] >= po[a - 1][b - 1][temp]) {
				temp = i;
			}
		}
		System.out.println(" " + (temp + 1));
		return temp + 1;
	}
}
