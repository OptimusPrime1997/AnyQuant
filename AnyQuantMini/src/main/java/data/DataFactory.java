/**
 * 
 */
package data;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import data.datasql.DBUtility;
import data.datasql.StockDataImp;
import data.datasql.UserDataImp;
import dataservice.DataFactoryDataService;
import dataservice.MaxMinDataService;
import dataservice.StockDataService;
import dataservice.UserDataService;
import utility.Constants;

/**
 * the singleton datafactory
 * 
 * @author 1
 *
 */
public class DataFactory implements DataFactoryDataService {
	private static java.sql.Connection connection;
	private static DBUtility dbUtility;

	/**
	* 
	*/
	private DataFactory() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName(Constants.DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("load database driver failed!");
			e.printStackTrace();
		}
		try {
			String url = Constants.DBURL + Constants.DBIP + ":" + Constants.DBPORT + "/" + Constants.DBSCHEMA;
			connection = DriverManager.getConnection(url, Constants.DBUSER, Constants.DBPSW);
		} catch (SQLException e) {
			System.out.println("Connect database failed!");
			e.printStackTrace();
		}
	}

	public static DataFactory getInstance() {
		return DataFactoryHolder.dataFactory;
	}

	private static class DataFactoryHolder {
		private static DataFactory dataFactory = new DataFactory();
	}

	private static StockDataService stockData;
	private static UserDataService userData;
	private static MaxMinDataService maxMinData;

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.DataFactoryDataService#getStockData()
	 */
	@Override
	public StockDataService getStockData() {
		// TODO Auto-generated method stub
		if (stockData == null) {
			stockData = new StockData();
		}
		return stockData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.DataFactoryDataService#getUserData()
	 */
	@Override
	public UserDataService getUserData() {
		// TODO Auto-generated method stub
		if (userData == null) {
			userData = new UserDataImp();
		}
		return userData;
	}

	@Override
	public MaxMinDataService getMaxMin() {
		if (maxMinData == null) {
			maxMinData = new MaxMinData();
		}
		return maxMinData;
	}

	public java.sql.Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName(Constants.DRIVER);
			} catch (ClassNotFoundException e) {
				System.out.println("load database driver failed!");
				e.printStackTrace();
			}
			try {
				String url = Constants.DBURL + Constants.DBIP + ":" + Constants.DBPORT + "/" + Constants.DBSCHEMA;
				connection = DriverManager.getConnection(url, Constants.DBUSER, Constants.DBPSW);
			} catch (SQLException e) {
				System.out.println("Connect database failed!");
				e.printStackTrace();
			}
		}
		return connection;
	}

	public DBUtility getDBUtility() {
		if (dbUtility == null) {
			dbUtility = new DBUtility();
		}
		return dbUtility;
	}

	public static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
