package strategy;

import utility.MyDate;
import utility.exception.Init_Fault_exception;

public interface Simulate {
	/**
	 * 
	 * @param id
	 *            (id of stock)
	 * @param rangeUp
	 *            (the range edge to buy)
	 * @param rangeDown
	 *            (the range edge to sell)
	 * @param buy
	 *            (the percent of money to buy)
	 * @param sell
	 *            (the percent of stocks to sell)
	 * @param money
	 *            (the free money)
	 * @param init
	 *            (the initial amount of stocks)
	 * @param upbound
	 *            (the unbound to sell)
	 * @param lowbound
	 *            (the lower bound to buy)
	 * @param numOfDays
	 *            (number of days to simulate,assume 100,200,300)
	 * @param endDay
	 *            (the end date)
	 * @return the double array of asset
	 */
	public double[] getPayBack(String id, double rangeUp, double rangeDown, double buy, double sell, double money,
			int init, double upbound, double lowbound, int numOfDays, MyDate endDay) throws Init_Fault_exception;

}
