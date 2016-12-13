/**
 * 
 */
package utility.exception;

/**
 * @author run
 *
 */
public class TimeOut_exception extends Exception {
	public TimeOut_exception(){
		super("连接超时");
	}
}
