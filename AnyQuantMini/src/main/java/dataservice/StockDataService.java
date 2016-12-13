/**
 * 
 */
package dataservice;

import java.io.IOException;
import java.util.ArrayList;

import model.StockInfo;
import po.StockPO;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.Market;
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
	 */

	ArrayList<StockPO> getDataByID(String ID, Range_Date date) throws TimeOut_exception, IOException;

	/**
	 * get data by date
	 * 
	 * @param date
	 * @param market
	 * @return
	 */

	ArrayList<StockPO> getDataByDate(MyDate date, Market market) throws TimeOut_exception;

	/**
	 * get  all the local stock id list
	 * @return
	 */
	ArrayList<String> getLoacalStockIds();

	
}
