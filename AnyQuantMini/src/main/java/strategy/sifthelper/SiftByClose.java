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
 *sift by the price when close
 */
public class SiftByClose implements Mysift {

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
			if(temp.getEndprice()<high&&temp.getEndprice()>low){
				s.add(temp);
			}
		}
		return s;
	}

}
