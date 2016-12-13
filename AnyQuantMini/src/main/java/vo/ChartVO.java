/**
 * 
 */
package vo;

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
	private double turnover;
	private double volume;
	private double adjprice;
	private double pe;
	private double pb;
	private String name;
	private String chartID;
	private MyDate date;

	public ChartVO(double to, double vol, double mon, double pe, double pb, String name, String id, MyDate date) {
		this.turnover = to;
		this.volume = vol;
		this.adjprice = mon;
		this.pe = pe;
		this.pb = pb;
		this.chartID = id;
		this.name = name;
		this.date = date;
	}

	public double getTurnover() {
		return this.turnover;
	}

	public double getVolume() {
		return this.volume;
	}

	public double getAdjprice() {
		return this.adjprice;
	}

	public double getPe() {
		return this.pe;
	}

	public double getPb() {
		return this.pb;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @param chartID
	 *            the id to set
	 */
	public String getID() {
		return this.chartID;
	}

	public MyDate getDate() {
		return this.date;
	}

	@Override
	public String toString() {
		return "id:" + chartID + "name:" + name + "turnover:" + turnover + "volume:" + volume + "adjprice:" + adjprice
				+ "pe:" + pe + "pb:" + pb;

	}
	
	public void setTurnover(double d){
		this.turnover=d;
	}
	
	public void setVol(long d){
		this.volume=d;
	}
	
	public void setPe(double d){
		this.pe=d;
	}
	
	public void setPb(double d){
		this.pb=d;
	}
	
	public void setAdj(double d){
		this.adjprice=d;
	}

}
