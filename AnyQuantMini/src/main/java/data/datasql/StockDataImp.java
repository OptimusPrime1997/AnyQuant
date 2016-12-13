/**
 * 
 */
package data.datasql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import data.DataFactory;
import dataservice.MaxMinDataService;
import dataservice.StockDataService;
import po.MaxMinPO;
import po.StockPO;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.Market;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;

/**
 * @author 1
 *
 */
public class StockDataImp implements StockDataService {
	private java.sql.Connection conn;
	private DBUtility dbUtility;

	public StockDataImp() {
		this.conn = DataFactory.getInstance().getConnection();
		this.dbUtility = DataFactory.getInstance().getDBUtility();
	}

	/*
	 * get the stockpo by id attention:range_date contains the start date and
	 * not contains the end date
	 */
	@Override
	public ArrayList<StockPO> getDataByID(String ID, Range_Date date) {
		// TODO Auto-generated method stub
		ArrayList<StockPO> stockPOs = new ArrayList<StockPO>();
		String sql = "select * from stockdetail where id = ? and date >= ? and date < ? order by date desc ;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, ID);
			statement.setDate(2, date.lowdate.getSqlDate());
			statement.setDate(3, date.highdate.getSqlDate());
			ResultSet results = dbUtility.dbQuery(statement);
			while (results.next()) {
				StockPO po = new StockPO(results.getString("id"), results.getString("name"), results.getDouble("open"),
						results.getDouble("close"), results.getDouble("high"), results.getDouble("low"),
						results.getDouble("volum"), results.getDouble("turnover"), results.getDouble("adj_price"),
						results.getDouble("pe"), results.getDouble("pb"), results.getDate("date"),
						results.getDouble("money"), results.getDouble("rangedata"), results.getDouble("tr"));
				stockPOs.add(po);
			}
			results.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public ArrayList<StockPO> getDataByDate(MyDate date, Market market) throws TimeOut_exception {
		// TODO Auto-generated method stub
		date = date.getRecentDate();
		ArrayList<StockPO> pos = new ArrayList<StockPO>();
		ArrayList<String> ids = getLoacalStockIds();
		for (Iterator<String> t = ids.iterator(); t.hasNext();) {
			String tmp = t.next();
			if (tmp.length() == 8 || tmp.length() == 5) {
				MyDate after = date.afterDay();
				ArrayList<StockPO> stocks = getDataByID(tmp, new Range_Date(date, after));
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
		String sql = "select * from stockchoosed ;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet results = dbUtility.dbQuery(statement);
			while (results.next()) {
				ids.add(results.getString("id"));
			}
			results.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}

}
