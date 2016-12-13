/**
 * 
 */
package data;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.Test;

import data.StockData;
import po.StockPO;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.Market;
import utility.exception.TimeOut_exception;

/**
 * @author 1
 *
 */
public class StockDataTest {
	/**
	 * @throws java.lang.Exception
	 */
	private StockData stockData;

	public StockDataTest() {
		// TODO Auto-generated constructor stub
		stockData = new StockData();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetDataByID() {
		ArrayList<StockPO> result = null;
		try {
			result = stockData.getDataByID("sh600000",
					new Range_Date(new MyDate("2016-04-11"), new MyDate("2016-04-12")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StockPO p = null;
		for (Iterator<StockPO> t = result.iterator(); t.hasNext();) {
			p = t.next();
			System.out.println(p.toString());
			assertEquals(
					"name:浦发银行id:sh600000,open:17.6,close:17.67,high:17.82,low:17.55,volume:24117600,turnover:0.13,adjPrice:17.67,pe:6.86,pb:1.16,money:4.2615799200000006E8,range:0.0,RangeRatio:0.0,date：2016-04-11",
					p.toString());
		}
	}

	@Test
	public void testGetDataByDate() {
		ArrayList<StockPO> stocks = null;
		try {
			stocks = stockData.getDataByDate(new MyDate("2016-03-08"), Market.SHANGHAI);
		} catch (TimeOut_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator<StockPO> t = stocks.iterator(); t.hasNext();) {
			System.out.println(t.next().toString());
		}
		assertEquals(
				"name:沙河股份id:sz000014,open:18.08,close:17.9,high:18.23,low:16.86,volume:5017500,turnover:2.49,adjPrice:17.9,pe:77.68,pb:5.46,money:8.981325E7,range:0.0,RangeRatio:0.0,date：2016-03-08",
				stocks.get(0).toString());

	}

}
