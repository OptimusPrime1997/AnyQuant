/**
 * 
 */
package utility.exception;

/**
 * when input user name no exist in file throw the exception
 * 
 * @author 1
 *
 */
public class NotFoundName_exception extends Exception {
	/**
	 * 
	 */
	public NotFoundName_exception() {
		// TODO Auto-generated constructor stub
		super("未找到该用户名");
	}
}
