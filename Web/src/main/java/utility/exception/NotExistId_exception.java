/**
 * 
 */
package utility.exception;

/**
 * @author 1
 *when the stock id doesn't exist in user list
 */
public class NotExistId_exception extends Exception {
	/**
	 * 
	 */
	public NotExistId_exception() {
		// TODO Auto-generated constructor stub
		super("该股票代号不在该用户的自选股列表中");
	}
}
