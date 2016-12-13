package utility.exception;

/**
 * this is the exception to be thrown when the user didn't input the name when
 * registering
 * 
 * @author run
 *
 */
public class NoName_exception extends Exception {
	/**
	 * 
	 */
	public NoName_exception() {
		// TODO Auto-generated constructor stub
		super("用户未输入用户名");
	}
}
