/**
 * 
 */
package data;

import dataservice.DataFactoryDataService;
import dataservice.StockDataService;
import dataservice.StockStatusDataService;
import dataservice.UserDataService;

/**
 * the singleton datafactory
 * 
 * @author 1
 *
 */
public class DataFactory implements DataFactoryDataService {

	/**
	* 
	*/
	private DataFactory() {
		// TODO Auto-generated constructor stub
	}

	public static DataFactory getInstance() {
		return DataFactoryHolder.dataFactory;
	}

	private static class DataFactoryHolder {
		private static DataFactory dataFactory = new DataFactory();
	}

	private static StockDataService stockData;
	private static UserDataService userData;
	private static StockStatusDataService stockStatus;

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.DataFactoryDataService#getStockData()
	 */
	@Override
	public StockDataService getStockData() {
		// TODO Auto-generated method stub
		if (stockData == null) {
			stockData = new StockDataImp();
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
	public StockStatusDataService getStockStatus() {
		// TODO Auto-generated method stub
		if (stockStatus == null) {
			stockStatus = new StockStatusImp();
		}
		return stockStatus;
	}
}
