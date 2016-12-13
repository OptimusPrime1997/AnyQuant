/**
 * 
 */
package bl.detailBL;
import java.io.IOException;
import java.util.ArrayList;
import po.StockPO;
/**the interface to get the description of a stock
 * @author run
 *
 */
public interface getDes {
	public String getDescription(ArrayList<StockPO> array) throws IOException;
}
