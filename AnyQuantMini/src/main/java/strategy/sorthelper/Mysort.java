package strategy.sorthelper;

import java.util.ArrayList;

import po.StockPO;
import utility.enums.SortOrder;

public interface Mysort {
	/**
	 * the common interface shared by all the sort methods
	 * @param array
	 * @param order
	 * @return
	 */
	public ArrayList<StockPO> sort(ArrayList<StockPO> array,SortOrder order);
}
