package strategy;

import java.util.ArrayList;

import data.StockDataImp;
import dataservice.StockDataService;
import model.Stock;
import utility.MyDate;
import utility.NumHelper;
import utility.Range_Date;
import utility.exception.Init_Fault_exception;
import utility.exception.NoStockInfo__exception;

/**
 * the class implement the Simulate interface
 * 
 * @author jiaorun
 *
 */
public class Strategy implements Simulate {

	static int N = 100;

	/**
	 * range_up range_down 是百分数,buy,sell 也是百分数
	 */
	@Override
	public double[] getPayBack(String id, double rangeUp, double rangeDown, double buy, double sell, double money,
			int init, double upbound, double lowbound, int numOfDays, MyDate endDay) throws Init_Fault_exception {
		StockDataService ser = new StockDataImp();
		ArrayList<Stock> temp = new ArrayList<Stock>();
		double[] result = new double[numOfDays];
		buy = buy / 100.0;
		sell = sell / 100.0;

		double[] price = new double[numOfDays];
		double[] range = new double[numOfDays];

		int i = numOfDays / N;
		MyDate mydate = endDay.clone();

		/**
		 * get the data
		 */
		for (int j = i - 1; j >= 0; --j) {
			try {
				temp = ser.getDataByID(id, getRangeDate(endDay));
				resverse(temp);

			} catch (NoStockInfo__exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Init_Fault_exception();
			}

			assert (temp != null);

			upbound = temp.get(0).getEndprice() * (1 + upbound * 1.0 / 100);
			lowbound = temp.get(0).getEndprice() * (1 - lowbound * 1.0 / 100);

			/*
			 * compute the range of an array of data
			 */
			for (int k = 0; k < temp.size(); ++k) {
				price[j * N + k] = temp.get(k).getEndprice();
				if (k == 0 && j == 0) {
					range[j * N + k] = temp.get(k).getRange();
				} else {
					if (temp.get(j * N + k - 1).getEndprice() != 0) {
						range[j * N + k] = (temp.get(j * N + k).getEndprice() - temp.get(j * N + k - 1).getEndprice())
								/ temp.get(j * N + k - 1).getEndprice() * 100;
					}
				}
			}
			mydate = getBef(mydate);
		}

		money = money - init * price[0];
		double amount = 0;

		/**
		 * the loop to carry out the plan
		 * 
		 */
		for (int j = 0; j < numOfDays; ++j) {
			if (price[j] > upbound) {
				/**
				 * larger than the up,we sell
				 */
				amount = Math.floor((init * sell));
				init -= amount;
				money += amount * price[j];
			} else if (price[j] < lowbound && price[j] != 0) {
				/**
				 * lower than the low,we buy
				 */
				amount = Math.floor(money * buy / price[j]);
				init += amount;
				money -= amount * price[j];
			} else if (range[j] < -rangeDown) {
				/**
				 * drop too much,we sell
				 */
				amount = Math.floor((init * sell));
				init -= amount;
				money += amount * price[j];
			} else if (range[j] > rangeUp && price[j] != 0) {
				/**
				 * increase too much we buy
				 */
				amount = Math.floor(money * buy / price[j]);
				init += amount;
				money -= amount * price[j];
			}
			if (price[j] != 0) {
				result[j] = money + init * price[j];
			}
			System.out.println("result[" + j + "]:" + result[j]);
			result[j] = NumHelper.toFixed(result[j], 2);
		}
		return result;
	}

	/**
	 * get the range_date
	 * 
	 * @param date
	 * @return
	 */
	public Range_Date getRangeDate(MyDate date) {
		return new Range_Date(getBef(date), date);
	}

	public MyDate getBef(MyDate date) {
		MyDate d = date.clone();
		for (int i = 0; i < N; ++i) {
			d = d.beforeDay();
			while (!d.isWorkDay()) {
				d = d.beforeDay();
			}
		}
		return d;
	}

	/**
	 * reverse the arraylist
	 * 
	 * @param a
	 */
	void resverse(ArrayList<Stock> a) {
		int l = a.size();
		Stock s = null;
		for (int i = 0; i < l / 2; ++i) {
			s = a.get(i);
			a.set(i, a.get(l - i - 1));
			a.set(l - i - 1, s);
		}
	}

	public static void main(String[] args) {
		Strategy s = new Strategy();
		try {
			double[] a = s.getPayBack("sh600030", 5, 5, 0.1, 0.1, 10000, 50, 15, 6, 100, new MyDate(2016, 6, 6));

			for (int i = 0; i < a.length; ++i) {
				System.out.println(a[i]);
			}
		} catch (Init_Fault_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
