package dataservice;

public interface StockStatusDataService {
	
	/**
	 * get the stock money circulation information
	 * @param stockId
	 * @return
	 */
	public String getStockStatus(String stockId);
	
	/**
	 * get the prediction price of stock
	 * @param stockId
	 * @return
	 */
	public String getStockPrediction(String stockId);
}
