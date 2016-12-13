package model;

import utility.MyDate;

/**
 * the class to tell the up and low increase-rate of one day
 * 
 * @author jiaorun
 *
 */
public class MarketState {
	/**
	 * the up and low bound and the date
	 */
	double up;
	double down;
	MyDate date;

	public MarketState(double up, double down, MyDate date) {
		this.up = up;
		this.down = down;
		this.date = date;
	}

	public double getUp() {
		return up;
	}

	public void setUp(double up) {
		this.up = up;
	}

	public double getDown() {
		return down;
	}

	public void setDown(double down) {
		this.down = down;
	}

	public MyDate getDate() {
		return date;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}

	public String display() {
		return "UP:" + this.getUp() + "DOWN:" + this.getDown() + "DATE:" + this.getDate().toString() + "\n";
	}
}
