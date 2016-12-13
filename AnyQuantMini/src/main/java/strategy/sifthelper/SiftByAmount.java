/**
 * 
 */
package strategy.sifthelper;

import java.util.ArrayList;
import java.util.Iterator;

import po.StockPO;
import utility.Range;

/**
 * @author run sift by the amount sold or bought
 */
public class SiftByAmount implements Mysift {

	/*
	 * (non-Javadoc)
	 * 
	 * @see sift_interface.Mysift#sift(java.util.ArrayList, utility.Range)
	 */
	@Override
	public ArrayList<StockPO> sift(ArrayList<StockPO> array, Range range) {

		ArrayList<StockPO> s = new ArrayList<StockPO>();
		Iterator<StockPO> it = s.iterator();
		StockPO temp;
		long low = range.lowlong;
		long high = range.highlong;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getVolume() < high && temp.getVolume() > low) {
				s.add(temp);
			}
		}
		return s;
	}

}
