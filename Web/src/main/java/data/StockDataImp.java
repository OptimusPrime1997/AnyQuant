/**
 * 
 */
package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import dataservice.StockDataService;
import model.ChartVO;
import model.IndustryMoney;
import model.Stock;
import model.StockInfo;
import utility.MyDate;
import utility.NumHelper;
import utility.Range_Date;
import utility.emums.IndustryType;
import utility.emums.Market;
import utility.exception.NoStockInfo__exception;

/**
 * @author 1
 *
 */
public class StockDataImp implements StockDataService {
	/*
	 * get the stockpo by id attention:range_date contains the start date and
	 * not contains the end date
	 */
	@Override
	public ArrayList<Stock> getDataByID(String ID, Range_Date date) throws NoStockInfo__exception {
		// TODO Auto-generated method stub
		ArrayList<Stock> stockPOs = new ArrayList<Stock>();
		String sql = "select * from stockdetail where id = ? and date >= ? and date < ? order by date desc ;";
		Connection conn = DBMainUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, ID);
			statement.setDate(2, date.lowdate.getSqlDate());
			statement.setDate(3, date.highdate.getSqlDate());
			results = statement.executeQuery();
			while (results.next()) {
				Stock po = new Stock();
				po.setId(results.getString("id"));
				po.setName(results.getString("name"));
				po.setStartprice(results.getDouble("open"));
				po.setEndprice(results.getDouble("close"));
				po.setMaxprice(results.getDouble("high"));
				po.setMinprice(results.getDouble("low"));
				po.setVolume(results.getDouble("volume"));
				po.setTurnover(results.getDouble("turnover"));
				po.setAdjprice(results.getDouble("adj_price"));
				po.setPe(results.getDouble("pe"));
				po.setPb(results.getDouble("pb"));
				java.sql.Date temp = results.getDate("date");
				po.setDate(new MyDate(temp.getYear() + 1900, temp.getMonth() + 1, temp.getDate()));
				po.setMoney(results.getDouble("money"));
				po.setRange(results.getDouble("rangedata"));
				po.setTr(results.getDouble("tr"));
				stockPOs.add(po);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NoStockInfo__exception();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return stockPOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.StockDataService#getDataByDate(utility.MyDate,
	 * utility.enums.Market)
	 */
	@Override
	public ArrayList<Stock> getDataByDate(MyDate date, Market market) throws NoStockInfo__exception {
		// TODO Auto-generated method stub
		date = date.getRecentDate();
		ArrayList<Stock> pos = new ArrayList<Stock>();
		ArrayList<String> ids = getLoacalStockIds();
		for (Iterator<String> t = ids.iterator(); t.hasNext();) {
			String tmp = t.next();
			if (tmp.length() == 8 || tmp.length() == 5) {
				MyDate after = date.afterDay();
				ArrayList<Stock> stocks = getDataByID(tmp, new Range_Date(date, after));
				pos.addAll(stocks);
			}
		}
		return pos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.StockDataService#getLoacalStockIds()
	 */
	@Override
	public ArrayList<String> getLoacalStockIds() {
		// TODO Auto-generated method stub
		ArrayList<String> ids = new ArrayList<String>();
		String sql = "select id from stockchoosed ;";
		Connection conn = DBMainUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();
			while (results.next()) {
				ids.add(results.getString("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return ids;
	}

	//
	@Override
	public ArrayList<Stock> getRecentStocks(String stockId, MyDate date, int manyDays) throws NoStockInfo__exception {
		// TODO Auto-generated method stub
		ArrayList<Stock> stockPOs = new ArrayList<Stock>();
		String sql = "select * from stockdetail where id = ? and date <= ? order by date desc limit ?;";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, stockId);
			statement.setDate(2, date.getSqlDate());
			statement.setInt(3, manyDays);
			results = statement.executeQuery();
			while (results.next()) {
				Stock po = new Stock();
				po.setId(results.getString("id"));
				po.setName(results.getString("name"));
				po.setStartprice(results.getDouble("open"));
				po.setEndprice(results.getDouble("close"));
				po.setMaxprice(results.getDouble("high"));
				po.setMinprice(results.getDouble("low"));
				po.setVolume(results.getDouble("volume"));
				po.setTurnover(results.getDouble("turnover"));
				po.setAdjprice(results.getDouble("adj_price"));
				po.setPe(results.getDouble("pe"));
				po.setPb(results.getDouble("pb"));
				java.sql.Date temp = results.getDate("date");
				po.setDate(new MyDate(temp.getYear() + 1900, temp.getMonth() + 1, temp.getDate()));
				po.setMoney(results.getDouble("money"));
				po.setRange(results.getDouble("rangedata"));
				po.setTr(results.getDouble("tr"));
				stockPOs.add(po);
			}
			Collections.sort(stockPOs, new Comparator<Stock>() {
				@Override
				public int compare(Stock o1, Stock o2) {
					// TODO Auto-generated method stub
					return o1.getDate().compareTo(o2.getDate());
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NoStockInfo__exception();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return stockPOs;
	}

	@Override
	public ArrayList<IndustryMoney> getIndustryMoney(String stockId, MyDate date) throws NoStockInfo__exception {
		// TODO Auto-generated method stub
		ArrayList<IndustryMoney> industryMoneys = new ArrayList<IndustryMoney>();
		MyDate tempDate = date;
		double tempResult = 0;
		for (int i = 0; i < 12; i++) {
			tempDate = tempDate.beforeMonth();
			tempResult = getAvgMoney(stockId, tempDate);
			IndustryMoney temp = new IndustryMoney();
			temp.setIndustryId(stockId);
			temp.setIndustryName(IndustryType.getIndustryById(stockId).getName());
			temp.setIndustryMoney(tempResult);
			temp.setYearMonth(tempDate);
			industryMoneys.add(temp);
		}
		Collections.sort(industryMoneys, new Comparator<IndustryMoney>() {
			@Override
			public int compare(IndustryMoney o1, IndustryMoney o2) {
				// TODO Auto-generated method stub
				return o1.getYearMonth().compareTo(o2.getYearMonth());
			}
		});
		return industryMoneys;
	}

	public double getAvgMoney(String stockId, MyDate date) throws NoStockInfo__exception {
		double result = 0;
		String sql = "select avg(money) from stockdetail where year(date)= ? and month(date)= ? and id= ?;";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, date.getYear() + "");
			statement.setString(2, date.getMonth() + "");
			statement.setString(3, stockId);
			results = statement.executeQuery();
			if (results.next()) {
				double tempR = NumHelper.toFixed(results.getDouble(1), 2);
				result = tempR;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NoStockInfo__exception();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return result;
	}

	public ArrayList<String> getAllStockId() {
		ArrayList<String> allIds = new ArrayList<String>();
		String sql = "select distinct id from stockdetail where id like ?;";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, "s_______");
			results = statement.executeQuery();
			while (results.next()) {
				allIds.add(results.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return allIds;
	}

	public boolean addStockInfo(String id, String secFullName, String officeAddr, String primeOperation) {
		String sql = "insert into stockinfo(id,secFullName,officeAddr,primeOperation) values( ? , ? , ? , ?);";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		int rs = -1;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			statement.setString(2, secFullName);
			statement.setString(3, officeAddr);
			statement.setString(4, primeOperation);
			rs = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return true;
	}

	@Override
	public StockInfo getStockInfoById(String id) throws NoStockInfo__exception {
		// TODO Auto-generated method stub
		StockInfo po = null;
		String sql = "select * from stockinfo where id = ?;";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			results = statement.executeQuery();
			if (results.next()) {
				po = new StockInfo();
				po.setStockId(results.getString("id"));
				po.setSecFullName(results.getString("secFullName"));
				po.setOfficeAddr(results.getString("officeAddr"));
				po.setPrimeOperation(results.getString("primeOperation"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NoStockInfo__exception();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return po;
	}

	// @Override
	// public MaxMinChart getMaxMinChart() {
	// // TODO Auto-generated method stub
	// MaxMinChart po = null;
	// String sql = "select
	// max(turnover),max(volume),max(pe),max(pb),max(adj_price),"
	// + "min(turnover),min(volume),min(pe),min(pb),min(adj_price) from
	// stockdetail "
	// + "where isIndustry=1 and pe >0 and volume>0 and pb>0 ;";
	// Connection conn = DBUtility.getConn();
	// PreparedStatement statement = null;
	// ResultSet results = null;
	// try {
	// statement = conn.prepareStatement(sql);
	// results = statement.executeQuery();
	// if (results.next()) {
	// po = new MaxMinChart();
	// po.setMaxTurnover(NumHelper.toFixed(results.getDouble(1), 2));
	// po.setMaxVolume(NumHelper.toFixed(results.getDouble(2), 2));
	// po.setMaxPe(NumHelper.toFixed(results.getDouble(3), 2));
	// po.setMaxPb(NumHelper.toFixed(results.getDouble(4), 2));
	// po.setMaxAdjprice(NumHelper.toFixed(results.getDouble(5), 2));
	//
	// po.setMinTurnover(NumHelper.toFixed(results.getDouble(6), 2));
	// po.setMinVolume(NumHelper.toFixed(results.getDouble(7), 2));
	// po.setMinPe(NumHelper.toFixed(results.getDouble(8), 2));
	// po.setMinPb(NumHelper.toFixed(results.getDouble(9), 2));
	// po.setMinAdjprice(NumHelper.toFixed(results.getDouble(10), 2));
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// DBUtility.closeRs(results);
	// DBUtility.closePreStmt(statement);
	// DBUtility.closeConn(conn);
	// }
	// return po;
	// }

	@Override
	public ArrayList<ChartVO> getCharVOs(MyDate date, String sql) {
		// TODO Auto-generated method stub
		MyDate tempDate = date.beforeMonth();
		ChartVO po = null;
		ArrayList<ChartVO> chartVOs = new ArrayList<ChartVO>();
		// String sql = "select
		// id,name,(avg(turnover)-min(turnover))/(max(turnover)-min(turnover))*100,(avg(volume)-min(volume))/(max(volume)-min(volume))*100,(avg(pe)-min(pe))/(max(pe)-min(pe))*100,(avg(pb)-min(pb))/(max(pb)-min(pb))*100,(avg(adj_price)-min(adj_price))/(max(adj_price)-min(adj_price))*100
		// from stockdetail where isIndustry=1 and pe >0 and volume>0 and pb>0
		// and year(date)=? and month(date)=? group by id;";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, tempDate.getYear());
			statement.setInt(2, tempDate.getMonth());
			results = statement.executeQuery();
			while (results.next()) {
				po = new ChartVO();
				po.setChartID(results.getString(1));
				po.setName(results.getString(2));
				double tTurnover = results.getDouble(3);
				po.setTurnover(NumHelper.toFixed(tTurnover, 2));
				double tVolume = results.getDouble(4);
				po.setVolume(NumHelper.toFixed(tVolume, 2));
				double tPe = results.getDouble(5);
				po.setPe(NumHelper.toFixed(tPe, 2));
				double tPb = results.getDouble(6);
				po.setPb(NumHelper.toFixed(tPb, 2));
				double tAdjprice = results.getDouble(7);
				po.setAdjprice(NumHelper.toFixed(tAdjprice, 2));

				// System.out.println(po.toString());

				chartVOs.add(po);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return chartVOs;
	}

	@Override
	public ArrayList<String> getStockIdByIndustry(String industry) {
		ArrayList<String> pos = new ArrayList<String>();
		String po = null;
		String sql = "select id from stockchoosed where industry = ? order by id asc;";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, industry);
			results = statement.executeQuery();
			while (results.next()) {
				po = results.getString(1);
				pos.add(po);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return pos;
	}

	@Override
	public ArrayList<StockInfo> getStockInfoByIndustry(String industry) throws NoStockInfo__exception {
		ArrayList<StockInfo> pos = new ArrayList<StockInfo>();
		StockInfo po = null;
		String sql = "select * from stockinfo where id in (select s.id from stockchoosed as s where industry= ?) order by id asc;";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, industry);
			results = statement.executeQuery();
			while (results.next()) {
				po = new StockInfo();
				po.setStockId(results.getString("id"));
				po.setSecFullName(results.getString("secFullName"));
				po.setOfficeAddr(results.getString("officeAddr"));
				po.setPrimeOperation(results.getString("primeOperation"));
				pos.add(po);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return pos;
	}

	@Override
	public boolean isCorrectStockId(String id) {
		boolean isCorrect = false;
		String sql = "select * from stockchoosed where id = ?;";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			results = statement.executeQuery();
			if (results.next()) {
				isCorrect = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return isCorrect;
	}
}
