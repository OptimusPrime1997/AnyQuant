/**
 * 
 */
package helper;

import utility.MA_Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.DataFactory;
import dataservice.StockDataService;
import model.Stock;
import utility.MyDate;
import utility.Range_Date;
import utility.emums.Days;
import utility.exception.NoStockInfo__exception;
import utility.exception.TimeOut_exception;

/**
 * this class helps get the MA of a stock
 * 
 * @author run
 *
 */
public class AverageMAhelper {
	StockDataService service;

	public AverageMAhelper() {
		service = DataFactory.getInstance().getStockData();
	}

	public ArrayList<MA_Date> get_MA(String id, Range_Date range, Days days)  {
		int i = 0;

		switch (days) {
		case five:
			i = 5;
			break;
		case ten:
			i = 10;
			break;
		case thirty:
			i = 30;
			break;
		case sixty:
			i = 60;
			break;
		case onehundred_and_twenty:
			i = 120;
			break;
		}
		MyDate high = range.highdate;
		MyDate t = range.lowdate;
		MyDate computeDate = t.getLastDate(i - 1);
		ArrayList<Stock> tempStock=new ArrayList<Stock>();
		try {
			tempStock = service.getDataByID(id, new Range_Date(computeDate, high));
		} catch (NoStockInfo__exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("get "+id+" failed!");
		}
		Collections.sort(tempStock, new Comparator<Stock>() {
			@Override
			public int compare(Stock o1, Stock o2) {
				// TODO Auto-generated method stub
				return -o1.compareTo(o2);
			}
		});
		ArrayList<MA_Date> result = new ArrayList<MA_Date>();
		for (int k = 0; k < tempStock.size(); k++) {
			if (k + i < tempStock.size()) {
				List<Stock> tmpList = tempStock.subList(k, k + i - 1);
				double avg=getAvg(tmpList);
				result.add(new MA_Date(id,tempStock.get(k+i-1).getDate(), avg));
			}
		}
		return result;

	}

	private double getAvg(List<Stock> list) {
		assert (list != null) : ("getAvg list is null!");
		assert (list.size() > 0) : ("getAvg size equals zero");
		double sum = 0;
		for (Stock temp : list) {
			sum += temp.getEndprice();
		}
		return sum / list.size();
	}
}