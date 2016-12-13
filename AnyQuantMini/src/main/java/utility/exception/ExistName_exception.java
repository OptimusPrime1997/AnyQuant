/**
 * 
 */
package utility.exception;

/**
 * @author 1
 *when the regist name already exists in files
 */
public class ExistName_exception extends Exception {
	public ExistName_exception(){
		super("用户名存在");
	}
}
