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
public class GetRangeArray {
	public static double[] getRangeArray(ArrayList<StockPO> array) throws IOException {
		int len = array.size();
		double[] result = new double[len];
		int i = 0, j = 0;
		StockPO s, t;
		while (j <= len - 1) {
			if (j == 0) {
				array.get(i).setRange();
				result[0] = array.get(i).getRange();
				++j;
			} else {
				s = array.get(i);
				t = array.get(j);
				result[j] = (t.getEndprice() - s.getEndprice()) / s.getEndprice() * 100;
				++i;
				++j;
			}
		}
		// System.out.println( result );
		return result;
	}

}
