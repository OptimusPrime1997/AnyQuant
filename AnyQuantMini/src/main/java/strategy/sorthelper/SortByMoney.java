package strategy.sorthelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import po.StockPO;
import strategy.sorthelper.SortByMin.Sorthelp;
/**
 * sort by money sold or bought
 * @author run
 *
 */
//public class SortByMoney implements Mysort {
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
//				if(arg0.getMoney()>arg1.getMoney())
//					return 1;
//				else
//					return -1;
//			}
//			else{
//				if(arg0.getMoney()<arg1.getMoney())
//					return 1;
//				else
//					return -1;
//			}
//		}
//		
//	}
//
//}
import utility.enums.SortOrder;
