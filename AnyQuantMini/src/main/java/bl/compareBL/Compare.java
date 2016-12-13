/**
 * 
 */
package bl.compareBL;
import java.io.IOException;
import java.util.ArrayList;

import po.StockPO;

/**
 * @author run
 *
 */
public interface Compare {
	public CompareInfo Compare_Max(ArrayList< ArrayList<StockPO> > array) throws IOException;
	public CompareInfo Compare_Min(ArrayList< ArrayList<StockPO> > array) throws IOException;
}
