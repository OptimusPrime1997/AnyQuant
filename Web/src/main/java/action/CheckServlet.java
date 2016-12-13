package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

import data.DataFactory;
import dataservice.DataFactoryDataService;
import dataservice.StockDataService;
import dataservice.UserDataService;
import model.Stock;
import model.User;
import utility.MyDate;
import utility.exception.NotFoundName_exception;

public class CheckServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----use CheckServlet-----");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userName");
		String userPsd=request.getParameter("userPsd");
		
		UserDataService userDataService=DataFactory.getInstance().getUserData();
		try {
			User user=userDataService.getUser(userId);
			
			if(user!=null){
				if(user.getPassword().equals(userPsd)){
					out.println("true");
				}else{
					out.println("false");
				}
			}else{
				out.println("false");
			}
		} catch (NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
