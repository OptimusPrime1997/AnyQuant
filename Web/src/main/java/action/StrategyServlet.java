package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import data.DataFactory;
import dataservice.DataFactoryDataService;
import dataservice.StockDataService;
import model.Stock;
import net.sf.json.JSONObject;
import strategy.Simulate;
import strategy.Strategy;
import utility.MyDate;
import utility.NumHelper;
import utility.exception.Init_Fault_exception;
import utility.exception.NoStockInfo__exception;

public class StrategyServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		System.out.println("3-----use the strategy servlet doget method!");
		String path = "strategy.jsp";
		System.out.println("stockId:"+req.getParameter("stockId")
				+ "\nrangeUp:"+req.getParameter("rangeUp")
				+ "\nrangeDown:"+req.getParameter("rangeDown")
				+ "\nbuy:"+req.getParameter("buy")
				+ "\nsell:"+req.getParameter("sell")
				+ "\nmoney:"+req.getParameter("money")
				+ "\ninit:"+req.getParameter("init")
				+ "\nupbound:"+req.getParameter("highbound")
				+ "\nlowbound:"+req.getParameter("lowbound")
				+ "\nnumOfDays:"+req.getParameter("numOfDays")
				+ "\nendDay:"+req.getParameter("endDay")
				);
		String stockId = (String) req.getParameter("stockId");
		double rangeUp = Double.parseDouble(req.getParameter("rangeUp"));
		double rangeDown = Double.parseDouble(req.getParameter("rangeDown"));
		double buy = Double.parseDouble(req.getParameter("buy"));
		double sell = Double.parseDouble(req.getParameter("sell"));
		double money = Double.parseDouble(req.getParameter("money"));
		int init = Integer.parseInt(req.getParameter("init"));
		double upbound = Double.parseDouble(req.getParameter("highbound"));
		double lowbound = Double.parseDouble(req.getParameter("lowbound"));
		int numOfDays = Integer.parseInt(req.getParameter("numOfDays"));
		MyDate endDay = new MyDate(req.getParameter("endDay"));
		/*
		 * StockStatusDataService stockStatusDS=dfService.getStockStatus();
		 */
		double[] result=null;
		if (endDay != null) {
			Simulate simulate = new Strategy();
			try {
				 result = simulate.getPayBack(stockId, rangeUp, rangeDown, buy/100, sell/100, money, init, upbound,
						lowbound, numOfDays, endDay);
				req.setAttribute("resultArr", result);
			} catch (Init_Fault_exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ArrayList<MyDate> dates = new ArrayList<MyDate>();
			MyDate temp = endDay;
			for (int i = 0; i < numOfDays; i++) {
				dates.add(0, temp);
				temp = temp.beforeDay();
				while (!temp.isWorkDay()) {
					temp = temp.beforeDay();
				}
			}
			req.setAttribute("dateArr", dates);

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
			req.setAttribute("profitRatio", profitRatio+"");
			
			/*Map map=new HashMap();
//			map.put("resultArr", result);
			for(int i=0;i<result.length;i++){
				map.put("r"+i, result[i]);
			}
			for(int j=0;j<dates.size();j++){
				map.put("d"+j, dates.get(j));
			}
			map.put("profitRatio", profitRatio);
			System.out.println(map);
			JSONObject json=(JSONObject) JSONObject.fromObject(map);
			System.out.println(json);
				resp.getWriter().print(json);*/
		}
		getServletConfig().getServletContext().getRequestDispatcher("/" + path).forward(req, resp);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
