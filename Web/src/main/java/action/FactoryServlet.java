package action;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import data.DataFactory;
import dataservice.DataFactoryDataService;
import dataservice.StockDataService;
import helper.KDJHelper;
import model.KDJVO;
import model.Stock;
import model.StockInfo;
import utility.Constants;
import utility.MyDate;
import utility.exception.NoStockInfo__exception;

/**
 * @author 1
 *
 */
public class FactoryServlet extends HttpServlet{
	/**
	 * 20 day industry stock info
	 * 20 day the stock of industry stock and stock info
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("+++++++++++use the factoryservlet doget method!");
		String path = "factory.jsp";
		DataFactoryDataService dfService = DataFactory.getInstance();
		StockDataService stockDataService = dfService.getStockData();
		String stockId = (String) req.getParameter("stockId");
		String date=MyDate.getCurVaildDate().toString();
		String manyDay=Constants.manyDay+"";
//		String date = (String) req.getParameter("reqDate");
//		String manyDay = (String) req.getParameter("manyDay");
		System.out.println(stockId + "+" + date + "+" + manyDay);
		if (stockId != null && date != null && manyDay != null) {
			System.out.println("Date:" + date);
			MyDate nowDate = new MyDate(date);

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
			
			//req.setAttribute("manyDay", Integer.parseInt(manyDay));

			ArrayList<String> stocks = stockDataService.getStockIdByIndustry(stockId);

			ArrayList<StockInfo> stockInfoList=null;
			try {
				stockInfoList = stockDataService.getStockInfoByIndustry(stockId);
			} catch (NoStockInfo__exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(stockInfoList!=null){
				req.setAttribute("stockInfoList", stockInfoList);
			}
			ArrayList<ArrayList<Stock>> doubleStock = new ArrayList<ArrayList<Stock>>();
			for (Iterator<String> t = stocks.iterator(); t.hasNext();) {
				String tempId = t.next();
//				System.out.println(tempId);
				
				ArrayList<Stock> temp = null;
				StockInfo stockInfo=null;
				try {
					temp = stockDataService.getRecentStocks(tempId, nowDate, Integer.parseInt(manyDay));
				} catch (NoStockInfo__exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (temp != null) {
					if (temp.size() >= 1) {
//						System.out.println(temp.get(0));
						doubleStock.add(temp);
					}
				}
			}
//			doubleStock.add(stockList);
			if(doubleStock.size()>0){
				req.setAttribute("doubleStock", doubleStock);
			}
			

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
			
			
//			ArrayList<ArrayList<IndustryMoney>> dbIndustryMoney = new ArrayList<ArrayList<IndustryMoney>>();
//			for (int i = 0; i < Constants.industryArray.length; i++) {
//
//				ArrayList<IndustryMoney> tempIndustry = null;
//				try {
//					tempIndustry = stockDataService.getIndustryMoney(Constants.industryArray[i], nowDate);
//				} catch (NoStockInfo__exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if(tempIndustry!=null){
//					dbIndustryMoney.add(tempIndustry);
//				}
//			}
//			req.setAttribute("dbIndustryMoney",dbIndustryMoney);
//
//			ArrayList<ChartVO> chartVOs = stockDataService.getCharVOs(nowDate, Constants.tableSql);
//			req.setAttribute("chartVOs", chartVOs);
//
//			ArrayList<ChartVO> trueChartVOs = stockDataService.getCharVOs(nowDate, Constants.radarSql);
//			System.out.println(trueChartVOs.get(0).toString());
//			req.setAttribute("trueChartVOs", trueChartVOs);
			
		}
		getServletConfig().getServletContext().getRequestDispatcher("/" + path).forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
