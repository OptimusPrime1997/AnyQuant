/**
 * 
 */
package utility;

import utility.MyDate;

/**
 * @author run
 *
 */
public class ATR_Date {
	private MyDate d;
	private double atr;

	public ATR_Date(MyDate date, double ATR) {
		d = date;
		atr = ATR;
	}

	public MyDate getDate() {
		return this.d;
	}

	public double getATR() {
		return this.atr;
	}

}
