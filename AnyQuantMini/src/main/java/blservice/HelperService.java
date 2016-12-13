/**
 * 
 */
package blservice;

import java.io.IOException;
import java.util.ArrayList;
import utility.exception.*;
import vo.KDJVO;
import utility.Range_Date;
import utility.ATR_Date;

/**
 * the interface to get different kinds of MA
 * 
 * @author run
 *
 */
public interface HelperService {

	public ArrayList<ATR_Date> get_ATR(String id, Range_Date range) throws TimeOut_exception, IOException;

	public ArrayList<KDJVO> getKDJ(String id, Range_Date range) throws TimeOut_exception, IOException;
}
