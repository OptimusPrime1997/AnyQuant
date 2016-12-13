package utility.exception;
/**
 * this is the exception to be throw when the user's stock id exists
 * @author run
 *
 */
public class ExistID_exception extends Exception{
	/**
	 * 
	 */
	public ExistID_exception() {
		// TODO Auto-generated constructor stub
		super("该股票代码已存在用户的自选股列表");
	}
	
}
