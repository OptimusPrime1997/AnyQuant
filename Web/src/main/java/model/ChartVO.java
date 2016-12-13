/**
 * 
 */
package model;

import java.io.Serializable;

import utility.MyDate;

/**
 * @author run
 *
 */
public class ChartVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3769052703825063034L;
	private String chartID;
	private String name;
	private MyDate date;
	private double turnover;
	private double volume;
	private double pe;
	private double pb;
	private double adjprice;

	public ChartVO() {
		// TODO Auto-generated constructor stub
	}

	public String getChartID() {
		return chartID;
	}

	public void setChartID(String chartID) {
		this.chartID = chartID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MyDate getDate() {
		return date;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}

	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getPe() {
		return pe;
	}

	public void setPe(double pe) {
		this.pe = pe;
	}

	public double getPb() {
		return pb;
	}

	public void setPb(double pb) {
		this.pb = pb;
	}

	public double getAdjprice() {
		return adjprice;
	}

	public void setAdjprice(double adjprice) {
		this.adjprice = adjprice;
	}

	@Override
	public String toString() {
		return "id:" + chartID + "name:" + name + "turnover:" + turnover + "volume:" + volume + "adjprice:" + adjprice
				+ "pe:" + pe + "pb:" + pb;
	}
}
