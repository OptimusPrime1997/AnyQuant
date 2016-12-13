package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import antlr.collections.List;
import data.DataFactory;
import dataservice.DataFactoryDataService;
import dataservice.StockDataService;
import dataservice.UserDataService;
import model.Stock;
import model.User;
import utility.Constants;
import utility.MyDate;
import utility.Range_Date;
import utility.exception.NoStockInfo__exception;
import utility.exception.NotFoundName_exception;

public class SelectServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("+++++++++++use the selectservlet doget method!");
		String path = "select.jsp";
		DataFactoryDataService dfService = DataFactory.getInstance();
		StockDataService stockDataService = dfService.getStockData();

		UserDataService userDataService = dfService.getUserData();
		String userName = (String) req.getSession().getAttribute("userName");
		String date =MyDate.getCurVaildDate().toString(); 
		String manyDay =Constants.manyDay+"";
		
		if(userName==null){
			userName="Anyone";
		}
				//(String) req.getParameter("reqDate");
				//(String) req.getParameter("manyDay");
		System.out.println(userName + "+" + date + "+" + manyDay);
		if (userName != null && date != null && manyDay != null) {
			System.out.println("Date:" + date);
			MyDate nowDate = new MyDate(date);

			User user = null;
			try {
				user = userDataService.getUser(userName);
			} catch (NotFoundName_exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			java.util.List<String> stocks = null;
			if (user != null) {
				stocks = user.getStocks();
			}
			if (stocks != null) {
				ArrayList<ArrayList<Stock>> doubleStock = new ArrayList<ArrayList<Stock>>();
				for (Iterator<String> t = stocks.iterator(); t.hasNext();) {
					String tempId = t.next();
					ArrayList<Stock> temp = null;
					try {
						temp = stockDataService.getRecentStocks(tempId, nowDate, Integer.parseInt(manyDay));
					} catch (NoStockInfo__exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (temp != null) {
						if (temp.size() >= 1) {
							doubleStock.add(temp);
						}
					}
				}
				req.setAttribute("doubleStock", doubleStock);
			}

		}
		getServletConfig().getServletContext().getRequestDispatcher("/" + path).forward(req, resp);
		// req.getRequestDispatcher(path).forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
