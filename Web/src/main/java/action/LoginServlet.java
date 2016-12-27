package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class LoginServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "SelectServlet";
		String userId = req.getParameter("userId");
		req.getSession().setAttribute("userName", userId);
//		String userpass = req.getParameter("userPsd");
		/*UserDataService userDataService=DataFactory.getInstance().getUserData();
		try {
			User user=userDataService.getUser(userId);
			
			if(user!=null){
				if(user.getPassword().equals(userpass)){
					req.getSession().setAttribute("userName", user.getName());
					getServletConfig().getServletContext().getRequestDispatcher("/" + path).forward(req, resp);
				}else{
					req.setAttribute("isLogin", false);
				}
			}
		} catch (NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		getServletConfig().getServletContext().getRequestDispatcher("/" + path).forward(req, resp);
		
//		System.out.println("form:" + req.getParameter("action"));
//		System.out.println("request:" + req.getAttribute("request"));
//		System.out.println("session" + req.getSession().getAttribute("session"));
//		System.out.println("href:" + req.getParameter("href"));
//		System.out.println(userid + "," + userpass);
//
//		System.out.println("+++++++++++use the servlet doget method!");
//		ArrayList<Stock> stockList = new ArrayList<Stock>();
//		DataFactoryDataService dfService = DataFactory.getInstance();
//		StockDataService stockDataService = dfService.getStockData();
//		String stockId = (String) req.getSession().getAttribute("stockId");
//		String d = (String) req.getSession().getAttribute("date");
//		String md = req.getParameter("manyDay");
//		if (stockId != null && d != null && md != null) {
//			System.out.println(stockId);
//			MyDate date = new MyDate(d);
//			int manyDay = Integer.parseInt(req.getParameter("manyDay"));
//			try {
//				stockList = stockDataService.getRecentStocks(stockId, date, manyDay);
//			} catch (NoStockInfo__exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			ArrayList<String> dates = new ArrayList<String>();
//			for (Iterator<Stock> t = stockList.iterator(); t.hasNext();) {
//				Stock temp = t.next();
//				dates.add(temp.getDate().toString());
////				System.out.println(temp.toString());
//			}
//			req.setAttribute("stockList", stockList);
//			req.getSession().setAttribute("getDate", dates);
//			req.setAttribute("test", "Test get request!");
//		}
//		List<String> info = new ArrayList<String>();
//		if (userid == null || "".equals(userid)) {
//			info.add("用户ID不能为 空");
//		}
//		if (userpass == null || "".equals(userpass)) {
//			info.add("密码不能为空");
//		}
//		if (info.size() == 0) {
//			if (userid.equals("anyone") && userpass.equals("123456")) {
//				info.add("用户登录成功，欢迎" + userid + "光临！\n");
//			} else {
//				info.add("用户登录失败，用户名或密码错误\n");
//			}
//		}
//		req.setAttribute("info", info);
//		getServletConfig().getServletContext().getRequestDispatcher("/" + path).forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
