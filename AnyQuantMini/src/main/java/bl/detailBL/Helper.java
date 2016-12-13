/**
 * 
 */
package bl.detailBL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import blservice.HelperService;
import data.DataFactory;
import dataservice.StockDataService;
import po.StockPO;
import utility.ATR_Date;
import utility.MyDate;
import utility.Range_Date;
import utility.exception.TimeOut_exception;
import vo.KDJVO;

/**
 * @author run
 *
 */
public class Helper implements HelperService {
	StockDataService stockDataService;
	final int N = 7;
	private ArrayList<StockPO> stockPOs;
	final int period = 9;

	public Helper() {
		stockDataService = DataFactory.getInstance().getStockData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bl.detailBL.getATR#get_ATR(java.lang.String, utility.Range_Date)
	 */
	@Override
	public ArrayList<ATR_Date> get_ATR(String id, Range_Date range) throws TimeOut_exception, IOException {
		ArrayList<StockPO> pos = new ArrayList<StockPO>();
		MyDate low = range.lowdate;
		MyDate high = range.highdate;
		MyDate temp2 = low;

		for (int j = 0; j <= N; ++j) {
			low = getBeforeWorkDay(low);
		}

		ArrayList<MyDate> dates = new ArrayList<MyDate>();

		while (temp2.compareTo(high) <= 0) {
			System.out.println(temp2.toString());
			if (temp2.isWorkDay()) {
				dates.add(temp2);
				temp2 = getAfterWorkDay(temp2);
			} else
				temp2 = getAfterWorkDay(temp2);
		}

		pos = stockDataService.getDataByID(id, new Range_Date(low, high.afterDay()));
		ArrayList<ATR_Date> re = new ArrayList<ATR_Date>();

		ArrayList<Double> tr = compute(pos);
		ArrayList<Double> atr = computeAve(tr);

		assert (dates.size() == atr.size());
		int c = 0;
		System.out.println(dates.size() + " ------ " + tr.size());
		while (c < dates.size()) {
			re.add(new ATR_Date(dates.get(c), atr.get(c)));
			++c;
		}
		return re;
	}

	MyDate getBeforeWorkDay(MyDate d) {
		d = d.beforeDay();
		while (!d.isWorkDay()) {
			d = d.beforeDay();
		}
		return d;
	}

	MyDate getAfterWorkDay(MyDate d) {
		d = d.afterDay();
		while (!d.isWorkDay()) {
			d = d.afterDay();
		}
		return d;
	}

	ArrayList<Double> compute(ArrayList<StockPO> pos) {
		ArrayList<Double> re = new ArrayList<Double>();
		for (int i = 0; i < pos.size() - 1; ++i) {
			re.add(max(pos.get(i).getEndprice(), pos.get(i + 1).getMaxprice())
					- min(pos.get(i).getMinprice(), pos.get(i + 1).getEndprice()));
		}
		return re;
	}

	double max(double a, double b) {
		return (a > b) ? a : b;
	}

	double min(double a, double b) {
		return (a > b) ? b : a;
	}

	ArrayList<Double> computeAve(ArrayList<Double> tr) {
		ArrayList<Double> re = new ArrayList<Double>();
		double result = 0;
		for (int i = 0; i < tr.size() - (N - 1); ++i) {
			result = 0;
			for (int j = i; j < i + N; j++) {
				result = result + tr.get(j);
			}
			re.add(result / N);
		}
		return re;
	}

	public ArrayList<KDJVO> getKDJ(String id, Range_Date range) throws TimeOut_exception, IOException {
		double k = 0;
		double d = 0;
		double j = 0;
		MyDate lowD = range.lowdate;
		lowD = lowD.getLastDate(period - 1);
		range.lowdate = lowD;
		stockPOs = stockDataService.getDataByID(id, range);
		ArrayList<KDJVO> kdjList = new ArrayList<KDJVO>();
		ArrayList<Double> rsvList = getRSV(stockPOs);
		for (int i = 0; i < rsvList.size(); i++) {
			double rsv = rsvList.get(i);
			if (i == 0) {
				k = 50 * 2 / 3 + rsv / 3;
				d = 50 * 2 / 3 + k / 3;
			}
			if (i > 0) {
				k = k * 2 / 3 + rsv / 3;
				d = d * 2 / 3 + k / 3;
			}
			j = 3 * k - 2 * d;
			KDJVO kdj = new KDJVO(stockPOs.get(i + period - 1).getDate(), id, rsv, k, d, j);
			kdjList.add(kdj);
		}
		return kdjList;
	}

	public ArrayList<Double> getRSV(ArrayList<StockPO> stockPOS) {
		assert (stockPOS.size() >= period) : ("KDJHelper.getRSV() stockPO.size() is not enough!");
		ArrayList<Double> RSVList = new ArrayList<Double>();
		for (int i = 0; i <= (stockPOS.size() - period); i++) {
			List<StockPO> subList = stockPOS.subList(i, i + period);
			utility.Range limit = getLimits(subList);
			
//			System.out.println(subList.get(period - 1).toString());
			
			double close = subList.get(period - 1).getEndprice();
			double rsv = ((close - limit.lowdouble) / (limit.highdouble - limit.lowdouble)) * 100;

//			System.out.println(
//					"Num:" + i + "High:" + limit.highdouble + "Low:" + limit.lowdouble + "Endprice:" + close + "\n");

			RSVList.add(rsv);
		}
		return RSVList;
	}

	public utility.Range getLimits(List<StockPO> stockList) {
		double high = -1;
		double low = 1000000000;
		for (Iterator<StockPO> t = stockList.iterator(); t.hasNext();) {
			StockPO p = t.next();
			if (high < p.getMaxprice()) {
				high = p.getMaxprice();
			}
			if (low > p.getMinprice()) {
				low = p.getMinprice();
			}
		}
		return new utility.Range(null, null, low, high, 0, 0);
	}

}
