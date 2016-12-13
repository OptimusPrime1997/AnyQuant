package helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.DataFactory;
import dataservice.StockDataService;
import model.KDJVO;
import model.Stock;
import utility.MyDate;
import utility.NumHelper;
import utility.Range_Date;
import utility.exception.NoStockInfo__exception;
import utility.exception.TimeOut_exception;

public class KDJHelper {
	final int period = 9;
	private ArrayList<Stock> stocks;
	private StockDataService stockDataService;

	public KDJHelper() {
		stockDataService = DataFactory.getInstance().getStockData();
	}

	public ArrayList<KDJVO> getKDJ(String id, MyDate date, int manyDay) throws NoStockInfo__exception {
		double k = 0;
		double d = 0;
		double j = 0;
		System.out.println("stockId:"+id+"date:"+date+"days:"+(manyDay+period-1));
		stocks = stockDataService.getRecentStocks(id, date, manyDay + period - 1);
		System.out.println(stocks.size());
		ArrayList<KDJVO> kdjList = new ArrayList<KDJVO>();
		
		ArrayList<Double> rsvList = getRSV(stocks);
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
			KDJVO kdj = new KDJVO(stocks.get(i + period - 1).getDate(), id, NumHelper.toFixed(rsv, 2),
					NumHelper.toFixed(k, 2), NumHelper.toFixed(d, 2), NumHelper.toFixed(j, 2));
			kdjList.add(kdj);
		}
		return kdjList;
	}

	public ArrayList<Double> getRSV(ArrayList<Stock> StockS) {
		assert (StockS.size() >= period) : ("KDJHelper.getRSV() Stock.size() is not enough!:"+StockS.size());
		ArrayList<Double> RSVList = new ArrayList<Double>();
		for (int i = 0; i <= (StockS.size() - period); i++) {
			List<Stock> subList = StockS.subList(i, i + period);
			utility.Range limit = getLimits(subList);

			double close = subList.get(period - 1).getEndprice();
			double rsv = ((close - limit.lowdouble) / (limit.highdouble - limit.lowdouble)) * 100;

			RSVList.add(rsv);
		}
		return RSVList;
	}

	public utility.Range getLimits(List<Stock> stockList) {
		double high = -1;
		double low = 1000000000;
		for (Iterator<Stock> t = stockList.iterator(); t.hasNext();) {
			Stock p = t.next();
			if (high < p.getMaxprice()) {
				high = p.getMaxprice();
			}
			if (low > p.getMinprice()) {
				low = p.getMinprice();
			}
		}
		if(high==low){
			high=low+0.0001;
		}
		return new utility.Range(null, null, low, high, 0, 0);
	}

}
