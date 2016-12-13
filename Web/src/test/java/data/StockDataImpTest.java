package data;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.Stock;
import utility.MyDate;
import utility.Range_Date;
import utility.emums.Market;
import utility.exception.NoStockInfo__exception;
import utility.exception.TimeOut_exception;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StockDataImpTest {
	/**
	 * @throws java.lang.Exception
	 */
	private StockDataImp stockData;

	public StockDataImpTest() {
		// TODO Auto-generated constructor stub
		stockData = new StockDataImp();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test1GetDataByID() {
		ArrayList<Stock> result = null;
		try {
			result = stockData.getDataByID("sh600000",
					new Range_Date(new MyDate("2016-04-11"), new MyDate("2016-04-12")));
		} catch (NoStockInfo__exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stock p = null;
		for (Iterator<Stock> t = result.iterator(); t.hasNext();) {
			p = t.next();
			System.out.println(p.toString());
			assertEquals(
					"name:浦发银行id:sh600000,open:17.6,close:17.67,high:17.82,low:17.55,volume:24117600,turnover:0.13,adjPrice:17.67,pe:6.86,pb:1.16,money:4.2615799200000006E8,range:0.0,RangeRatio:0.0,date：2016-04-11",
					p.toString());
		}
	}

	@Test
	public void test2GetDataByDate() throws NoStockInfo__exception {
		ArrayList<Stock> stocks = null;
		stocks = stockData.getDataByDate(new MyDate("2016-03-08"), Market.SHANGHAI);
		for (Iterator<Stock> t = stocks.iterator(); t.hasNext();) {
			System.out.println(t.next().toString());
		}
		/*assertEquals(
				"name:沙河股份id:sz000014,open:18.08,close:17.9,high:18.23,low:16.86,volume:5017500,turnover:2.49,adjPrice:17.9,pe:77.68,pb:5.46,money:8.981325E7,range:0.0,RangeRatio:0.0,date：2016-03-08",
				stocks.get(0).toString());*/
		assertEquals(30, stocks.size());

	}

	@Test
	public void test3GetLocalStockIds() {
		ArrayList<String> idStocks = stockData.getLoacalStockIds();
		System.out.println(idStocks.get(0));
		assertEquals(30, idStocks.size());
		assertEquals("", "");
	}

	@Test
	public void test4GetRecentStocks() {
		ArrayList<Stock> stocks = null;
		try {
			stocks = stockData.getRecentStocks("hs300", new MyDate("2016-05-05"), 5);
		} catch (NoStockInfo__exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (stocks != null) {
			System.out.println(stocks.get(0));
			assertEquals(5, stocks.size());
			assertEquals("", "");
		}
	}
}
