/**
 * 
 */
package dataservice;

import java.io.IOException;
import java.util.ArrayList;

import model.ChartVO;
import model.IndustryMoney;
import model.MaxMinChart;
import model.Stock;
import model.StockInfo;
import utility.MyDate;
import utility.Range_Date;
import utility.emums.Market;
import utility.exception.NoStockInfo__exception;
import utility.exception.TimeOut_exception;

/**
 * @author run
 *
 */
public interface StockDataService {
	/**
	 * get data by id
	 * 
	 * @param ID
	 * @param market
	 * @return a month data
	 * @throws IOException
	 * @throws NoStockInfo__exception 
	 */

	ArrayList<Stock> getDataByID(String ID, Range_Date date) throws NoStockInfo__exception;

	/**
	 * get data by date
	 * 
	 * @param date
	 * @param market
	 * @return
	 * @throws NoStockInfo__exception 
	 */

	ArrayList<Stock> getDataByDate(MyDate date, Market market) throws  NoStockInfo__exception;

	/**
	 * get all the local stock id list
	 * 
	 * @return
	 */
	ArrayList<String> getLoacalStockIds();
	
	/**
	 * get the stocks of the industry
	 * @param industry
	 * @return
	 */
	ArrayList<String> getStockIdByIndustry(String industry);
 
	/**
	 * to get the special id whick date less or equal the date
	 * order by date asc
	 * 
	 * @param stockId
	 * @param date
	 * @param manyDays
	 * @return
	 * @throws NoStockInfo__exception 
	 */
	ArrayList<Stock> getRecentStocks(String stockId, MyDate date, int manyDays) throws NoStockInfo__exception;
	
	/**
	 * to get the last 12 month avg(money) from database.
	 * @param stockId
	 * @param date
	 * @return
	 * @throws NoStockInfo__exception 
	 */
	ArrayList<IndustryMoney> getIndustryMoney(String stockId,MyDate date) throws NoStockInfo__exception;
	
	/**
	 * get the stock base information by id
	 * @param id
	 * @return
	 * @throws NoStockInfo__exception 
	 */
	StockInfo getStockInfoById(String id) throws NoStockInfo__exception;
	
	/**
	 * get the stock list of the industry
	 * @param industry
	 * @return
	 * @throws NoStockInfo__exception
	 */
	ArrayList<StockInfo> getStockInfoByIndustry(String industry)throws NoStockInfo__exception;
	
//	/**
//	 * get the selected industry max and min data
//	 * @return
//	 */
//	MaxMinChart getMaxMinChart();
	
	/**
	 * get the selected industry charvos
	 * @param date
	 * @return
	 */
	ArrayList<ChartVO> getCharVOs(MyDate date, String sql);
	
	/**
	 * judge the sotckid is in database or not
	 * @param id
	 * @return
	 */
	public boolean isCorrectStockId(String id);
	
	
	
	
}
