package helper;

import java.util.ArrayList;
import java.util.Calendar;

import data.StockDataImp;
import dataservice.StockDataService;
import model.Stock;
import utility.MyDate;
import utility.Range_Date;
import utility.exception.NoStockInfo__exception;

/**
 * the class to carry out some simple statistic operations
 * 
 * @author jiaorun
 *
 */
public class Statistic {
	/**
	 * the method to get the mean of an array
	 * 
	 * @param a
	 * @return
	 */

	public static double mean(double[] a) {
		double result = 0;
		for (double t : a) {
			result += t;
		}
		return result / a.length;
	}

	/**
	 * the method to get the standard error of an array
	 * 
	 * @param a
	 * @return
	 */

	public static double std(double[] a) {
		double var = 0;
		double m = mean(a);
		for (double t : a) {
			var += (t - m) * (t - m);
		}
		var /= (a.length - 1);
		return Math.pow(var, 0.5);
	}

	/**
	 * according to the id and num of days to get the data array
	 * 
	 * @param id
	 * @param num
	 * @return
	 */
	public static double[] getArray(String id, int num) {
		double[] re = new double[num];
		StockDataService s = new StockDataImp();
		ArrayList<Stock> array = new ArrayList<Stock>();
		try {
			array = s.getDataByID(id, getRangeDate(new MyDate(Calendar.getInstance()), num));
		} catch (NoStockInfo__exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assert (array.size() != num) : ("get the array size is wrong!\n");
		// System.out.println(array.size() + " " + num);
		for (int i = 0; i < array.size(); ++i) {
			re[num - i - 1] = array.get(i).getEndprice();
		}
		return re;
	}

	/**
	 * get the range_date between today and "num" of days ago
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static Range_Date getRangeDate(MyDate date, int num) {
		return new Range_Date(getBef(date, num), date);
	}

	/**
	 * get the date "num" of days ago
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static MyDate getBef(MyDate date, int num) {
		MyDate d = date.clone();
		for (int i = 0; i < num; ++i) {
			d = d.beforeDay();
			while (!d.isWorkDay()) {
				d = d.beforeDay();
			}
		}
		return d;
	}

}
