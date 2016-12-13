/**
 * 
 */
package strategy.sorthelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import po.StockPO;
import strategy.sorthelper.SortByAmount.Sorthelp;
import utility.enums.SortOrder;

/**
 * @author run
 * sort by id
 *
 */
public class SortByID implements Mysort {

	/* (non-Javadoc)
	 * @see sort_interface.Mysort#sort(java.util.ArrayList, Enum.SortOrder)
	 */
	@Override
	public ArrayList<StockPO> sort(ArrayList<StockPO> array, SortOrder order) {
		Collections.sort(array, new Sorthelp(order));
		return array;
	}
	
	public class Sorthelp implements Comparator<StockPO>{
		
		SortOrder s;
		
		public Sorthelp(SortOrder order){
			s=order;
		}
		
		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(StockPO arg0, StockPO arg1) {
			if(s==SortOrder.UP){
				if(Integer.parseInt(arg0.getId())>Integer.parseInt(arg1.getId()))
					return 1;
				else
					return -1;
			}
			else{
				if(Integer.parseInt(arg0.getId())<Integer.parseInt(arg1.getId()))
					return 1;
				else
					return -1;
			}
		}
		
	}

}
