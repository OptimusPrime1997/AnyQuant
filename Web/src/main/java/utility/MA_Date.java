/**
 * 
 */
package utility;
/**
 * @author run
 *
 */
public class MA_Date {
	String stockId;
	double ma;
	MyDate date;
	
	public MA_Date(String id,MyDate d,double m){
		this.stockId=id;
		this.date=d;
		this.ma=m;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public double getMa() {
		return ma;
	}

	public void setMa(double ma) {
		this.ma = ma;
	}

	public MyDate getDate() {
		return date;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}
}
