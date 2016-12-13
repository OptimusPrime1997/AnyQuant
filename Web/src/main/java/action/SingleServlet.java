package action;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import data.DataFactory;
import dataservice.DataFactoryDataService;
import dataservice.StockDataService;
import dataservice.StockStatusDataService;
import helper.GetRank;
import helper.GetRank_V2;
import helper.KDJHelper;
import helper.Predict;
import helper.Predictor_old;
import model.KDJVO;
import model.MarketState;
import model.Stock;
import model.StockInfo;
import strategy.getRank;
import utility.Constants;
import utility.MyDate;
import utility.exception.Init_Fault_exception;
import utility.exception.NoStockInfo__exception;
import utility.exception.NotFoundModel_exception;

public class SingleServlet extends HttpServlet {
	/**
	 * id arraylist<stock> 20 predict arraylist 3 5 exist date and predict 3
	 * dates stock score
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("2-----use the single servlet doget method!");
		
		String stockId = (String) req.getParameter("stockId");
		String date=MyDate.getCurVaildDate().toString();
		String manyDay=Constants.singleDay+"";
//		String date = (String) req.getParameter("reqDate");
//		String manyDay = (String) req.getParameter("manyDay");
		DataFactoryDataService dfService = DataFactory.getInstance();
		StockDataService stockDataService = dfService.getStockData();
		StockStatusDataService stockStatusDS = dfService.getStockStatus();
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

			req.setAttribute("stockId", stockId);
			// req.setAttribute("manyDay", Integer.parseInt(manyDay));
			if (stockList != null) {
				req.setAttribute("stockList", stockList);
			}

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
			} catch (NumberFormatException | NoStockInfo__exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			

			String stockStatus = stockStatusDS.getStockStatus(stockId);
			req.setAttribute("stockStatus", stockStatus);

			String stockPredict = stockStatusDS.getStockPrediction(stockId);
			req.setAttribute("stockPredict", stockPredict);

			StockInfo stockInfo = null;
			try {
				stockInfo = stockDataService.getStockInfoById(stockId);
			} catch (NoStockInfo__exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (stockInfo != null) {
				req.setAttribute("stockInfo", stockInfo);
			}

			getRank getRankService = new GetRank_V2();
			int score;
			try {
				score = getRankService.getRanks(stockId);
				req.setAttribute("score", score);
			} catch (NotFoundModel_exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean isCorrect=stockDataService.isCorrectStockId(stockId);
			if(isCorrect==true){
				
				Predict predict = new Predictor_old();
				try {
					MarketState[] marketState = predict.getPrediction(stockId, nowDate);
					req.setAttribute("marketState", marketState);
				} catch (Init_Fault_exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getServletConfig().getServletContext().getRequestDispatcher("/" + "single.jsp").forward(req, resp);
			
			}else{
				getServletConfig().getServletContext().getRequestDispatcher("/" + "single2.jsp").forward(req, resp);
				
			}

		}
		// req.getRequestDispatcher(path).forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
