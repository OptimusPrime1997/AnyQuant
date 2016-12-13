package utility.exception;

/**
 * this is the exception thrown when the password is wrong
 * 
 * @author run
 *
 */
public class WrongPwd_exception extends Exception {
	/**
	 * 
	 */
	public WrongPwd_exception() {
		// TODO Auto-generated constructor stub
		super("用户名或密码错误");
	}
}
