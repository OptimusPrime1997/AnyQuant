package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataFactory;
import dataservice.UserDataService;
import model.User;
import utility.exception.ExistID_exception;
import utility.exception.ExistName_exception;
import utility.exception.NotFoundName_exception;

public class RegistServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "select.jsp";
		String userId = req.getParameter("userId");
		String userpass = req.getParameter("userPsd");
		UserDataService userDataService=DataFactory.getInstance().getUserData();
		try {
			boolean registSuc=userDataService.regist(userId, userpass);
			if(registSuc==true){
				User user=userDataService.getUser(userId);
				req.getSession().setAttribute("userName", user.getName());
			}
		} catch (ExistName_exception | NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getServletConfig().getServletContext().getRequestDispatcher("/" + path).forward(req, resp);
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
