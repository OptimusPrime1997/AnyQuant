package blservice;

import java.io.IOException;
import java.util.ArrayList;

import po.StockPO;
import utility.MyDate;
import utility.Range;
import utility.enums.SortOrder;
import utility.exception.TimeOut_exception;

/**
 * this is the interface for the marketBL to interact with marketUI
 * @author run
 *
 */
public interface marketBLService {
	/**
	 * the interface to sort
	 * @param array
	 * @param order
	 * @return
	 */
	ArrayList<StockPO> sort(ArrayList<StockPO> array,SortOrder order);
	
	/**
	 * the interface to sift
	 * @param array
	 * @param range
	 * @return
	 */
	ArrayList<StockPO> sift(ArrayList<StockPO> array,Range range);
	
	/**
	 * get the stocks at a certain day
	 * @param date
	 * @return
	 */
	ArrayList<StockPO> getData(MyDate date) throws TimeOut_exception;
	
	/**
	 * search according to id
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	ArrayList<StockPO> searchByID(String id) throws TimeOut_exception, IOException;
	
	/**
	 * search according to name
	 * @param name
	 * @return
	 */
	ArrayList<StockPO> searchByName(String name) throws TimeOut_exception;
}
