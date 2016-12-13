/**
 * 
 */
package blservice;

import java.io.IOException;
import java.util.ArrayList;

import po.StockPO;
import utility.enums.SortOrder;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;

/**
 * @author run
 *
 */
public interface SelectBLService {
	/**
	 * the sort method
	 * @param array
	 * @param order
	 * @return
	 */
	ArrayList<StockPO> sort(ArrayList<StockPO> array,SortOrder order);
	
	/**
	 * set a stock prefered
	 * @param id
	 * @return
	 */
	boolean delete(String name,String id)throws NotFoundName_exception;
	
	/**
	 * show the user's prefered stocks
	 * @param name
	 * @return
	 * @throws NotFoundName_exception
	 * @throws TimeOut_exception
	 * @throws IOException 
	 */
	ArrayList<StockPO> show(String name) throws NotFoundName_exception,TimeOut_exception, IOException;
}
