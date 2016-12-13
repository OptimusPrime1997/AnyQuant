package utility.exception;

/**
 * this is the exception to be thrown when the user didn't input the password
 * when registering
 * 
 * @author run
 *
 */
public class NoPwd_exception extends Exception {
	/**
	 * 
	 */
	public NoPwd_exception() {
		// TODO Auto-generated constructor stub
		super("用户未输入密码");
	}
}
