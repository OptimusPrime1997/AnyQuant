/**
 * 
 */
package bl.detailBL;

import java.util.ArrayList;
import utility.*;
import utility.enums.Days;
import utility.exception.TimeOut_exception;

import java.io.IOException;
/**
 * @author run
 *
 */
public interface getMA {
	public ArrayList<MA_Date> get_MA(String id,Range_Date range,Days day) throws TimeOut_exception,IOException;

}
