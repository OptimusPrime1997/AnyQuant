package action;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DataBindingException;

import org.junit.Test;

import data.DataFactory;
import dataservice.DataFactoryDataService;
import dataservice.StockDataService;
import helper.AverageMAhelper;
import helper.KDJHelper;
import model.ChartVO;
import model.IndustryMoney;
import model.KDJVO;
import model.Stock;
import utility.Constants;
import utility.MA_Date;
import utility.MyDate;
import utility.Range_Date;
import utility.emums.Days;
import utility.emums.IndustryType;
import utility.emums.Market;
import utility.exception.NoStockInfo__exception;
import utility.exception.TimeOut_exception;

public class ShowBenchServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("+++++++++++use the servlet doget method!");
		String stockId = (String) req.getParameter("stockId");
		String date=MyDate.getCurVaildDate().toString();
		String manyDay=Constants.singleDay+"";
//		String date = (String) req.getParameter("reqDate");
//		String manyDay = (String) req.getParameter("manyDay");
		String path = "market.jsp";
		DataFactoryDataService dfService = DataFactory.getInstance();
		StockDataService stockDataService = dfService.getStockData();
		System.out.println(stockId + "+" + date + "+" + manyDay);
		if (stockId != null && date != null && manyDay != null) {
			System.out.println("Date:" + date);
			MyDate nowDate = new MyDate(date);

			req.setAttribute("date", date);
			ArrayList<Stock> stockList = null;
			try {
				stockList = stockDataService.getRecentStocks(stockId, nowDate, Integer.parseInt(manyDay));
			} catch (NoStockInfo__exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (stockList != null) {
				req.setAttribute("stockList", stockList);
			}
			req.setAttribute("manyDay", Integer.parseInt(manyDay));
			// for (Iterator<Stock> t = stockList.iterator(); t.hasNext();) {
			// System.out.println(t.next().getTr());
			// }

			ArrayList<String> stocks = stockDataService.getLoacalStockIds();
			ArrayList<Stock> allStock = new ArrayList<Stock>();

			ArrayList<ArrayList<Stock>> doubleStock = new ArrayList<ArrayList<Stock>>();
			for (Iterator<String> t = stocks.iterator(); t.hasNext();) {
				String tempId = t.next();
				ArrayList<Stock> temp = null;
				try {
					temp = stockDataService.getRecentStocks(tempId, nowDate, Constants.manyDay);
				} catch (NoStockInfo__exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (temp != null) {
					if (temp.size() >= 1) {
						allStock.add(temp.get(temp.size() - 1));
						doubleStock.add(temp);
					}
				}
			}
			doubleStock.add(stockList);
			req.setAttribute("doubleStock", doubleStock);
			req.setAttribute("allStock", allStock);

			// test1GetDataByID(stockDataService);
			// test2GetDataByDate(stockDataService);
			// test3GetLocalStockIds(stockDataService);
			// test4GetRecentStocks(stockDataService);
			KDJHelper kdjHelper = new KDJHelper();
			try {
				ArrayList<KDJVO> kdjvos = kdjHelper.getKDJ(stockId, nowDate, Integer.parseInt(manyDay));
				System.out.println("kdjvos.size():" + kdjvos.size());
				assert (kdjvos.size() == Integer.parseInt(manyDay)) : ("The kdj size is not right!");
				req.setAttribute("kdjList", kdjvos);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoStockInfo__exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<ArrayList<IndustryMoney>> dbIndustryMoney = new ArrayList<ArrayList<IndustryMoney>>();
			for (int i = 0; i < Constants.industryArray.length; i++) {

				ArrayList<IndustryMoney> tempIndustry = null;
				try {
					tempIndustry = stockDataService.getIndustryMoney(Constants.industryArray[i], nowDate);
				} catch (NoStockInfo__exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(tempIndustry!=null){
					dbIndustryMoney.add(tempIndustry);
				}
			}
			req.setAttribute("dbIndustryMoney",dbIndustryMoney);
			ArrayList<ChartVO> chartVOs = stockDataService.getCharVOs(nowDate, Constants.tableSql);
			req.setAttribute("chartVOs", chartVOs);

			// RadarHelper radarHelper = new RadarHelper();
			ArrayList<ChartVO> trueChartVOs = stockDataService.getCharVOs(nowDate, Constants.radarSql);
			System.out.println(trueChartVOs.get(0).toString());
			req.setAttribute("trueChartVOs", trueChartVOs);
			
			
			ArrayList<ArrayList<Stock>> dbIndustryStock = new ArrayList<ArrayList<Stock>>();
			for (int i=0;i<Constants.industryArray.length;i++) {
				String tempId =Constants.industryArray[i];
//				System.out.println(tempId);
				ArrayList<Stock> temp = null;
				try {
					temp = stockDataService.getRecentStocks(tempId, nowDate, Constants.manyDay);
				} catch (NoStockInfo__exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (temp != null) {
					if (temp.size() >= 1) {
//						System.out.println(temp.get(0));
						dbIndustryStock.add(temp);
					}
				}
			}
//			doubleStock.add(stockList);
			if(dbIndustryStock.size()>0){
				req.setAttribute("dbIndustryStock", dbIndustryStock);
			}
		}
		getServletConfig().getServletContext().getRequestDispatcher("/" + path).forward(req, resp);
		// req.getRequestDispatcher(path).forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

	@Test
	public void test1GetDataByID(StockDataService stockData) {
		ArrayList<Stock> result = null;
		try {
			result = stockData.getDataByID("sh600000",
					new Range_Date(new MyDate("2016-04-11"), new MyDate("2016-04-12")));
		} catch (NoStockInfo__exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Stock p = null;
		// for (Iterator<Stock> t = result.iterator(); t.hasNext();) {
		// p = t.next();
		//// System.out.println(p.toString());
		// }
		assertEquals(
				"name:浦发银行id:sh600000,open:17.6,close:17.67,high:17.82,low:17.55,volume:2.41176E7,turnover:0.13,adjPrice:17.67,pe:6.86,pb:1.16,money:4.2615799200000006E8,range:0.003977272727272743,date：2016-04-11",
				result.get(0).toString());
	}

	@Test
	public void test2GetDataByDate(StockDataService stockData) {
		ArrayList<Stock> stocks = null;
		try {
			stocks = stockData.getDataByDate(new MyDate("2016-03-08"), Market.SHANGHAI);
		} catch (NoStockInfo__exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// for (Iterator<Stock> t = stocks.iterator(); t.hasNext();) {
		// System.out.println(t.next().toString());
		// }
		assertEquals(
				"name:中信证券id:sh600030,open:15.98,close:16.16,high:16.18,low:15.4,volume:1.892085E8,turnover:1.93,adjPrice:16.16,pe:9.34,pb:1.47,money:3.05760936E9,range:0.011264080100125138,date：2016-03-08",
				stocks.get(0).toString());

	}

	@Test
	public void test3GetLocalStockIds(StockDataService stockData) {
		ArrayList<String> idStocks = stockData.getLoacalStockIds();
		// System.out.println("3"+idStocks.get(0));
		assertEquals(30, idStocks.size());
		assertEquals("sh600030", idStocks.get(0));
	}

	@Test
	public void test4GetRecentStocks(StockDataService stockData) {
		ArrayList<Stock> stocks = null;
		try {
			stocks = stockData.getRecentStocks("hs300", new MyDate("2016-05-05"), 5);
		} catch (NoStockInfo__exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("4"+stocks.get(0));
		if (stocks != null) {
			assertEquals(5, stocks.size());
			assertEquals(
					"name:沪深300id:hs300,open:3153.54,close:3156.75,high:3171.29,low:3149.4,volume:5.8029776E9,turnover:0.0,adjPrice:3156.75,pe:0.0,pb:0.0,money:1.83185495388E13,range:-0.0012118028969366151,date：2016-04-29",
					stocks.get(0).toString());
		}
	}
}
