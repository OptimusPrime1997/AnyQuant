package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataFactory;
import dataservice.StockDataService;

public class CheckStockIdServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----use CheckStockIdServlet-----");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String stockId = request.getParameter("stockId");
		
		 StockDataService stockDataService=DataFactory.getInstance().getStockData();
			boolean isCorrect=stockDataService.isCorrectStockId(stockId);
			if(isCorrect==true){
				out.println("true");
			}else{
				out.println("false");
			}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
