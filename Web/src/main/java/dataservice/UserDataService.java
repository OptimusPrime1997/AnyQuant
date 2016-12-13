/**
 * 
 */
package dataservice;


import model.User;
import utility.exception.ExistID_exception;
import utility.exception.ExistName_exception;
import utility.exception.NotExistId_exception;
import utility.exception.NotFoundName_exception;

/**
 * @author run
 *
 */
public interface UserDataService {
	/**
	 * getUser by id
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(String id) throws NotFoundName_exception;

	/**
	 * to regist the user
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public boolean regist(String name, String password) throws ExistName_exception;

	/**
	 * to add the stock into user list
	 * @param name:user name
	 * @param id:stock id
	 */
	public boolean  add(String name,String id) throws ExistID_exception, NotFoundName_exception ;

	/**
	 * to remove the stock in user list
	 * 
	 * @param name:user
	 *            name
	 * @param id:stock
	 *            id
	 */
	public boolean remove(String name, String id) throws NotExistId_exception,NotFoundName_exception;
}
