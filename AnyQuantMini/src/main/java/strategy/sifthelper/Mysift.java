package strategy.sifthelper;

import java.util.ArrayList;

import po.StockPO;
import utility.Range;
import utility.enums.SortOrder;

public interface Mysift {
	/**
	 * the common interface of all the sift methods
	 * @param array 	this is the data to be sifted 
	 * @param range		this is the range to be considered
	 * @return
	 */
	public ArrayList<StockPO> sift(ArrayList<StockPO> array,Range range);
}
