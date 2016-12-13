package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataFactory;
import dataservice.DataFactoryDataService;
import dataservice.StockDataService;
import model.Stock;
import strategy.Simulate;
import strategy.Strategy;
import strategy.Strategy_v2;
import utility.MyDate;
import utility.NumHelper;
import utility.exception.Init_Fault_exception;
import utility.exception.NoStockInfo__exception;

public class StrategyServlet2 extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("3-----use the strategy servlet doget method!");
		String path = "strategy.jsp";

		String stockId = (String) req.getParameter("stockId");
//		double rangeUp = Double.parseDouble(req.getParameter("rangeUp"));
//		double rangeDown = Double.parseDouble(req.getParameter("rangeDown"));
		double buy = Double.parseDouble(req.getParameter("buy"));
		double sell = Double.parseDouble(req.getParameter("sell"));
		double money = Double.parseDouble(req.getParameter("money"));
		int init = Integer.parseInt(req.getParameter("init"));
//		double upbound = Double.parseDouble(req.getParameter("upbound"));
//		double lowbound = Double.parseDouble(req.getParameter("lowbound"));
		int numOfDays = Integer.parseInt(req.getParameter("numOfDays"));
		MyDate endDay = new MyDate(req.getParameter("endDay"));
		/*
		 * StockStatusDataService stockStatusDS=dfService.getStockStatus();
		 */
		Simulate simulate = new Strategy_v2();
		try {
			double[] result = simulate.getPayBack(stockId, 0, 0, buy, sell, money, init, 0,
					0, numOfDays, endDay);
			req.setAttribute("resultArr2", result);
		} catch (Init_Fault_exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<MyDate> dates=new ArrayList<MyDate>();
		MyDate temp=endDay;
		for(int i=0;i<numOfDays;i++){
			dates.add(0,temp);
			temp=temp.beforeDay();
			while(!temp.isWorkDay()){
				temp=temp.beforeDay();
			}
		}
		req.setAttribute("dateArr2", dates);
		
		DataFactoryDataService dfService = DataFactory.getInstance();
		StockDataService stockDataService = dfService.getStockData();
		ArrayList<Stock> hsStocks = null;
		try {
			hsStocks = stockDataService.getRecentStocks("hs300", endDay, numOfDays);
		} catch (NoStockInfo__exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assert (hsStocks != null) : ("get hs300 stock iformateion failed!\n");
		double profitRatio = (hsStocks.get(hsStocks.size() - 1).getEndprice() / hsStocks.get(0).getAdjprice()-1) * 100;
		profitRatio=NumHelper.toFixed(profitRatio,2);
		req.setAttribute("profitRatio2", profitRatio+"");
		getServletConfig().getServletContext().getRequestDispatcher("/" + path).forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
