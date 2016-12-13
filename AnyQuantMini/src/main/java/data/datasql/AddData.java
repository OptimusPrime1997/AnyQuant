/**
 * 
 */
package data.datasql;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.print.DocFlavor.URL;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.utils.URLEncodedUtils;

import com.mysql.jdbc.util.ResultSetUtil;

import data.DataFactory;
import data.StockData;
import po.StockPO;
import utility.MyDate;
import utility.Range_Date;

/**
 * @author 1 to add data to mysql
 */
public class AddData {
	private StockData stockData;
	private StockDataImp stockDataImp;
	private java.sql.Connection conn;
	private DBUtility dbUtility;

	public AddData() {
		this.stockData = new StockData();
		this.stockDataImp = new StockDataImp();
		this.conn = DataFactory.getInstance().getConnection();
		this.dbUtility = new DBUtility();
	}

	public void addAllData(Range_Date dateRange) throws SQLException {
		ArrayList<String> ids = null;
		
		//ids = getToSaveIds(dateRange.lowdate);
		ids=getAllIds();
		assert (ids != null) : ("get all stock ids failed!\n");
		System.out.println("To save data :" + ids.size());
		int skip = 0;
		Range_Date bigRange = new Range_Date(dateRange.lowdate.getLastDate(1), dateRange.highdate);
		for (Iterator<String> t = ids.iterator(); t.hasNext() && skip < 15;) {
			ArrayList<StockPO> stockPOs = null;
			try {
				stockPOs = stockData.getDataByID(t.next(), dateRange);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				skip++;
				continue;
			}
			for (int i = 0; i < (stockPOs.size() - 1); ++i) {
				double tr = (Math.max(stockPOs.get(i + 1).getEndprice(), stockPOs.get(i).getMaxprice())
						- Math.min(stockPOs.get(i).getMinprice(), stockPOs.get(i + 1).getEndprice()));
				stockPOs.get(i).setTr(tr);
				if (stockPOs.get(i + 1).getEndprice() > 0) {
					double rangeRatio = (stockPOs.get(i).getEndprice() - stockPOs.get(i + 1).getEndprice())
							/ stockPOs.get(i + 1).getEndprice() + 0.0;
					stockPOs.get(i).setRangeData(rangeRatio);
				}
			}
		//	System.out.println("stockName:"+stockPOs.get(0).getName());
			if (stockPOs.size() >= 1) {
				stockPOs.remove(stockPOs.size() - 1);
			}
			insertData(stockPOs);
		}
	}

	public void addStockChoosed() {
		ArrayList<String> localList = stockData.getLoacalStockIds();
		for (Iterator<String> t = localList.iterator(); t.hasNext();) {
			String sql = "insert into stockchoosed(id) values( ? ) ;";
			PreparedStatement statement = null;
			try {
				statement = conn.prepareStatement(sql);
				statement.setString(1, t.next());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dbUtility.dbUpdate(statement);
		}
	}

	public void addNewData() throws SQLException {
		String sql = "select  max(date) from stockdetail limit 1";
		PreparedStatement statement = null;
		java.sql.Date d = null;
		try {
			statement = conn.prepareStatement(sql);
			ResultSet result = dbUtility.dbQuery(statement);
			if (result.next()) {
				d = result.getDate(1);
			} else {
				System.out.println("The result is empty!");
			}
			result.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MyDate start = new MyDate(d.getYear() + 1900, d.getMonth() + 1, d.getDate());
		Calendar calendar = Calendar.getInstance();
		MyDate date = (new MyDate(calendar)).getRecentDate();

		addAllData(new Range_Date(start, date));
		addIndustryData(start.afterDay());
	}

	public void insertData(ArrayList<StockPO> pos) throws SQLException {
		String str="";
		if (pos.size() >= 1) {
			System.out.println(pos.get(0).toString());
		}
		for (Iterator<StockPO> t = pos.iterator(); t.hasNext();) {
			PreparedStatement statement = null;
			int result = -1;
			StockPO po = t.next();
			String sql = "insert into stockdetail(id,name,open,close,high,low,volume,turnover,adj_price,pe,pb,date,money,rangedata,tr) (select ?,name,?,?,?,?,?,?,?,?,?,?,?,?,?  from stockdetail where id=? and date=\"2016-05-05\" limit 1);";
			try {
				statement = conn.prepareStatement(sql);
				InputStream   inputStream   =   new   ByteArrayInputStream(po.getName().getBytes());
			
				// 对应的字符编码转换
				Reader reader = new InputStreamReader(inputStream, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(reader);
				 str  = bufferedReader.readLine();
//				 name=URLEncoder.encode(po.getName(),"UTF-8");
//				name=URLDecoder.decode(name, "UTF-8");
//				name=new String(po.getName().getBytes("GBK"), "UTF-8");
				statement.setString(1, po.getId());
				
				/*statement.setString(2, str);*/
				
				statement.setDouble(2, po.getStartprice());
				statement.setDouble(3, po.getEndprice());
				statement.setDouble(4, po.getMaxprice());
				statement.setDouble(5, po.getMinprice());
				statement.setDouble(6, new Double(po.getVolume()));
				statement.setDouble(7, po.getTurnover());
				statement.setDouble(8, po.getAdjprice());
				statement.setDouble(9, po.getPe());
				statement.setDouble(10, po.getPb());
				statement.setDate(11, po.getDate().getSqlDate());
				statement.setDouble(12, po.getMoney());
				statement.setDouble(13, po.getRange());
				statement.setDouble(14, po.getTr());
				statement.setString(15, po.getId());
			} catch (SQLException
					 | IOException
					e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (statement != null) {
				result = dbUtility.dbUpdate(statement);
			}
		}
		System.out.println(str);
	}

	public ArrayList<String> getToSaveIds(MyDate startDate) {
		ArrayList<String> ids = new ArrayList<String>();
		String sql = "select distinct a.id  from stockdetail as a "
				+ "where a.isIndustry =0 and a.id not in (select  distinct b.id  from stockdetail as b where b.date >= ? );;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setDate(1, startDate.getSqlDate());
			ResultSet results = dbUtility.dbQuery(statement);
			while (results.next()) {
				ids.add(results.getString(1));
			}
			results.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}
	public ArrayList<String> getAllIds() {
		ArrayList<String> ids = new ArrayList<String>();
		String sql = "select distinct a.id  from stockdetail as a where a.isIndustry =0 ";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			//statement.setDate(1, startDate.getSqlDate());
			ResultSet results = dbUtility.dbQuery(statement);
			while (results.next()) {
				ids.add(results.getString(1));
			}
			results.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}

	public void addIndustryData(MyDate date) {
		// TODO Auto-generated method stub
		String sql = "insert into stockdetail(id,name,open,close,high,low,volume,turnover,adj_price,pe,pb,date,money,rangedata,tr,isIndustry)"
				+ " (select sc.industry,sc.industryname,avg(s.open),avg(s.close),avg(s.high),avg(s.low),avg(s.volume),avg(s.turnover),avg(s.adj_price),avg(s.pe),avg(s.pb),s.date,avg(s.money),avg(s.rangedata),avg(s.tr),1 "
				+ "from stockchoosed  sc,stockdetail  s " + " where s.id = sc.id and s.date>= ? "
				+ " group by sc.industry,sc.industryname,s.date) ";
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setDate(1, date.getSqlDate());
			dbUtility.dbUpdate(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void correctIndustry(Range_Date dateRange) {
	//
	// ArrayList<String> ids = new ArrayList<String>();
	// ids.add("estate");
	// ids.add("finance");
	// ids.add("material");
	// System.out.println("To save data :" + ids.size());
	// for (Iterator<String> t = ids.iterator(); t.hasNext() ;) {
	// ArrayList<StockPO> stockPOs = null;
	// try {
	// stockPOs = stockData.getDataByID(t.next(), dateRange);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// for (int i = 0; i < (stockPOs.size() - 1); ++i) {
	// double tr = (Math.max(stockPOs.get(i + 1).getEndprice(),
	// stockPOs.get(i).getMaxprice())
	// - Math.min(stockPOs.get(i).getMinprice(), stockPOs.get(i +
	// 1).getEndprice()));
	// stockPOs.get(i).setTr(tr);
	// if (stockPOs.get(i + 1).getEndprice() > 0) {
	// double rangeRatio = (stockPOs.get(i).getEndprice() - stockPOs.get(i +
	// 1).getEndprice())
	// / stockPOs.get(i + 1).getEndprice() + 0.0;
	// stockPOs.get(i).setRangeData(rangeRatio);
	// }
	// }
	// insertData(stockPOs);
	// }
	//
	// }
}
