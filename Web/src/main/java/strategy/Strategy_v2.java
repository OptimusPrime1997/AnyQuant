package strategy;

import java.util.ArrayList;
import java.util.Calendar;

import data.StockDataImp;
import dataservice.StockDataService;
import helper.Statistic;
import model.Stock;
import utility.MyDate;
import utility.NumHelper;
import utility.Range_Date;
import utility.exception.Init_Fault_exception;
import utility.exception.NoStockInfo__exception;

/**
 * this is simular to the strategy, the only diff is we use MEAN+2*STD to stand
 * for the upbound and MEAN-2*STD for the low bound
 * 
 * @author jiaorun
 *
 */
public class Strategy_v2 implements Simulate {
	static int N = 100;

	@Override
	public double[] getPayBack(String id, double rangeUp, double rangeDown, double buy, double sell, double money,
			int init, double upbound, double lowbound, int numOfDays, MyDate endDay) throws Init_Fault_exception {

		double[] result = new double[numOfDays];
		buy = buy / 100.0;
		sell = sell / 100.0;
		StockDataService ser = new StockDataImp();
		ArrayList<Stock> temp = new ArrayList<Stock>();
		try {
			temp = ser.getDataByID(id, getRangeDate(endDay));
		} catch (NoStockInfo__exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new Init_Fault_exception();
		}
		reverse(temp);
		double[] array = con2arr(temp);
		double mean = Statistic.mean(array);
		double std = Statistic.std(array);

		/**
		 * define the up and low bound
		 */
		double up = mean + 2 * std;
		double low = mean - 2 * std;

		double[] price = new double[numOfDays];
		double[] range = new double[numOfDays];

		int i = numOfDays / N;
		MyDate mydate = endDay.clone();
		for (int j = i - 1; j >= 0; --j) {
			try {
				temp = ser.getDataByID(id, getRangeDate(mydate));
				reverse(temp);
			} catch (NoStockInfo__exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Init_Fault_exception();
			}

			assert (temp != null);

			upbound = temp.get(0).getEndprice() * (1 + upbound * 1.0 / 100);
			upbound = temp.get(0).getEndprice() * (1 + upbound * 1.0 / 100);

			for (int k = 0; k < temp.size(); ++k) {
				// System.out.println(temp.size());
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
		for (int j = 0; j < numOfDays; ++j) {
			if (price[j] > up) {
				amount = Math.floor((init * sell));
				init -= amount;
				money += amount * price[j];
			} else if (price[j] < low && price[j] != 0) {
				amount = Math.floor(money * buy / price[j]);
				init += amount;
				money -= amount * price[j];
			} else if (range[j] < -rangeDown) {
				amount = Math.floor((init * sell));
				init -= amount;
				money += amount * price[j];
			} else if (range[j] > rangeUp && price[j] != 0) {
				amount = Math.floor(money * buy / price[j]);
				init += amount;
				money -= amount * price[j];
			}
			if (price[j] != 0) {
				result[j] = money + init * price[j];
				result[j] = NumHelper.toFixed(result[j], 2);
			}
		}
		return result;
	}

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

	void reverse(ArrayList<Stock> a) {
		int l = a.size();
		Stock s = null;
		for (int i = 0; i < l / 2; ++i) {
			s = a.get(i);
			a.set(i, a.get(l - i - 1));
			a.set(l - i - 1, s);
		}
	}

	double[] con2arr(ArrayList<Stock> a) {
		double[] re = new double[a.size()];
		for (int i = 0; i < a.size(); ++i) {
			re[i] = a.get(i).getEndprice();
		}
		return re;
	}

	public static void main(String[] args) {
		Strategy_v2 s = new Strategy_v2();
		MyDate today = new MyDate(Calendar.getInstance());
		try {
			double[] t = s.getPayBack("sh600030", 3, 3, 0.25, 0.25, 100000, 2000, 20, 8, 100, today);
			for (int i = 0; i < N; ++i) {
				System.out.println(t[i]);
			}
		} catch (Init_Fault_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(s.getBef(today).toString());
	}

}
