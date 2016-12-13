/**
 * 
 */
package dataservice;

/**
 * the datafactoryService
 * 
 * @author 1
 *
 */
public interface DataFactoryDataService {
	public StockDataService getStockData();

	public UserDataService getUserData();
	
	public MaxMinDataService getMaxMin();
}
