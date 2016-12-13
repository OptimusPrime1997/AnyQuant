/**
 * 
 */
package dataservice;

import po.MaxMinPO;
import utility.exception.ExistID_exception;
import utility.exception.ExistName_exception;
import utility.exception.NotExistId_exception;
import utility.exception.NotFoundName_exception;
import vo.ChartVO;

/**
 * @author 1
 *
 */
public interface MaxMinDataService {
	/**
	 * getUser by id
	 * 
	 * @param id
	 * @return
	 */
	public MaxMinPO getChart(String id) throws NotFoundName_exception;

	boolean add(MaxMinPO maxMinPO) throws ExistID_exception, NotFoundName_exception;

	boolean update(String stockId, MaxMinPO maxMinPO) throws ExistName_exception;

	boolean addOrUpdate(MaxMinPO mmPO) throws ExistName_exception, ExistID_exception, NotFoundName_exception;

	boolean remove(String id) throws NotExistId_exception, NotFoundName_exception;
	
}
