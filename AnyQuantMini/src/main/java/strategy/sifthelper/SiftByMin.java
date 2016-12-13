/**
 * 
 */
package strategy.sifthelper;

import java.util.ArrayList;
import java.util.Iterator;

import po.StockPO;
import utility.Range;

/**
 * @author run
 *sift by lowest price
 */
public class SiftByMin implements Mysift {

	/* (non-Javadoc)
	 * @see sift_interface.Mysift#sift(java.util.ArrayList, utility.Range)
	 */
	@Override
	public ArrayList<StockPO> sift(ArrayList<StockPO> array, Range range) {
		ArrayList<StockPO> s=new ArrayList<StockPO>();
		Iterator<StockPO> it=s.iterator();
		StockPO temp;
		double low=range.lowdouble;
		double high=range.highdouble;
		while(it.hasNext()){
			temp=it.next();
			if(temp.getMinprice()<high&&temp.getMinprice()>low){
				s.add(temp);
			}
		}
		return s;
	}

}
