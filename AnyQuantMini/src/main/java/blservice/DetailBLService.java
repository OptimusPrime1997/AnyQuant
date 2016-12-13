/**
 * 
 */
package blservice;

import java.io.IOException;
import java.util.ArrayList;

import po.StockPO;
import utility.Range_Date;
import utility.enums.Days;
import utility.enums.IndustryType;
import utility.enums.Market;
import utility.enums.SortOrder;
import utility.exception.ExistID_exception;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;
import utility.ATR_Date;
import utility.MA_Date;

/**
 * @author run the interface of detailBL
 */
public interface DetailBLService {
	/**
	 * return the data according to the id
	 * 
	 * @param id,market
	 * @return
	 * @throws IOException
	 */
	ArrayList<StockPO> getData(String id, Market market, Range_Date range_Date) throws TimeOut_exception, IOException;

	/**
	 * the sort method
	 * 
	 * @param array
	 * @param order
	 * @return
	 */
	ArrayList<StockPO> sort(ArrayList<StockPO> array, SortOrder order);

	/**
	 * the method to remove prefered
	 * 
	 * @param id
	 * @return
	 */
	boolean select(String name, String id) throws NotFoundName_exception, ExistID_exception;

	/**
	 * the method to get ATR of a stock of a peromid
	 * 
	 * @param id
	 * @param range
	 * @return
	 */
	ArrayList<ATR_Date> getATR(String id, Range_Date range) throws IOException, TimeOut_exception;
	/**
	 * @param id
	 * @param range
	 * @param days
	 * @return
	 * @throws TimeOut_exception
	 * @throws IOException
	 */

	/**
	 * the method to get the MAs of a stock
	 * 
	 * @param id
	 * @param range
	 * @param days
	 * @return
	 */

	ArrayList<MA_Date> getMA(String id, Range_Date range, Days days) throws IOException, TimeOut_exception;

	/**
	 * get the description of a stock
	 * 
	 * @param array
	 * @return
	 * @throws IOException
	 */
	String getDescription(ArrayList<StockPO> array) throws IOException;

	/**
	 * @param industry
	 * @param date
	 * @return
	 * @throws IOException
	 * @throws TimeOut_exception
	 */
	ArrayList<StockPO> getIndustryByID(IndustryType industry, Range_Date date) throws IOException, TimeOut_exception;

}
