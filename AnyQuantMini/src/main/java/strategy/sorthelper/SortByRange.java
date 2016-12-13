package strategy.sorthelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import po.StockPO;
import strategy.sorthelper.SortByMax.Sorthelp;
import utility.enums.SortOrder;

/**
 * sort by the range of price
 * @author run
 *
 */
//public class SortByRange implements Mysort {
//
//	/* (non-Javadoc)
//	 * @see sort_interface.Mysort#sort(java.util.ArrayList, Enum.SortOrder)
//	 */
//	@Override
//	public ArrayList<StockPO> sort(ArrayList<StockPO> array, SortOrder order) {
//		Collections.sort(array, new Sorthelp(order));
//		return array;
//	}
//	
//	public class Sorthelp implements Comparator<StockPO>{
//		
//		SortOrder s;
//		
//		public Sorthelp(SortOrder order){
//			s=order;
//		}
//		
//		/* (non-Javadoc)
//		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
//		 */
//		@Override
//		public int compare(StockPO arg0, StockPO arg1) {
//			if(s==SortOrder.UP){
//				if(arg0.getRange()>arg1.getRange())
//					return 1;
//				else
//					return -1;
//			}
//			else{
//				if(arg0.getRange()<arg1.getRange())
//					return 1;
//				else
//					return -1;
//			}
//		}
//		
//	}
//
//
//}
