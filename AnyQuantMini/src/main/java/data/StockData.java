/**
 * 
 */
package data;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import dataservice.StockDataService;
import po.StockPO;
import utility.Constants;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.Field;
import utility.enums.Market;
import utility.exception.LocalFile_exception;
import utility.exception.TimeOut_exception;

/**
 * @author 1 to handle stock data
 */
public class StockData implements StockDataService {

	/**
	 * the constructor of StockData
	 */
	private Network network;
	private java.sql.Connection conn;

	public StockData() {
		// TODO Auto-generated constructor stub
		network = new Network();
	}

	/* return the stock list descend
	 * (non-Javadoc)
	 * @see dataservice.StockDataService#getDataByID(java.lang.String, utility.Range_Date)
	 */
	@Override
	public ArrayList<StockPO> getDataByID(String ID, Range_Date date) throws IOException {
		// TODO Auto-generated method stub
		// System.out.println(date.highdate.toString()+"++"+date.lowdate.toString());
		MyDate start = date.lowdate.getRecentDate();
		MyDate end = date.highdate;
	//	System.out.println("lowDate:"+start.toString()+"highDate:"+end.toString()+":"+(start.compareTo(end)));
		assert (start.compareTo(end) <0) : ("The date range is wrong!");
		ArrayList<StockPO> pos = new ArrayList<StockPO>();
		pos = network.getSingleStock(ID, start, end, Field.ALL);
		Collections.sort(pos, new Comparator<StockPO>() {
			@Override
			public int compare(StockPO o1, StockPO o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		return pos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.StockDataService#getDataByDate(utility.Date,
	 * enums.Market)
	 */
	@Override
	public ArrayList<StockPO> getDataByDate(MyDate date, Market market) throws TimeOut_exception {
		// TODO Auto-generated method stub
		date = date.getRecentDate();

		ArrayList<StockPO> pos = new ArrayList<StockPO>();
		try {
			ArrayList<String> ids = network.getStockId(market, date.getYear());

			for (Iterator<String> t = ids.iterator(); t.hasNext();) {
				String tmp = t.next();
				if (tmp.length() == 8 || tmp.length() == 5) {

					// System.out.println(tmp);
					MyDate d = new MyDate(date.toString());
					MyDate e = new MyDate(date.toString());
					MyDate f = new MyDate(e.afterDay().toString());
					// System.out.println(" " + d + " ");
					ArrayList<StockPO> stocks = network.getSingleStock(tmp, d, f, Field.ALL);
					// if (stocks.size() > 0) {
					// System.out.println("stocks:" + stocks.get(0).toString());
					// }
					pos.addAll(stocks);
					// System.out.println(pos.size());
				}
			}

			// System.out.println(pos.toString());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("get data from network failed");
			throw new TimeOut_exception();
		} catch (LocalFile_exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("read from local file failed");
		}

		// System.out.println(pos.size());
		return pos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.StockDataService#getConstructList()
	 */
	@Override
	public ArrayList<String> getLoacalStockIds() {
		// TODO Auto-generated method stub
		ArrayList<String> allStockIds = null;
		try {
			allStockIds = network.getLocalList(Constants.LOCALPATH);
		} catch (LocalFile_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Network.getLocalList() failed!");
		}
		assert (allStockIds != null && allStockIds.size() > 0) : ("Network.getLocalList() failed!");
		return allStockIds;
	}

	

	public java.sql.Connection getConn() {
		return conn;
	}
}
