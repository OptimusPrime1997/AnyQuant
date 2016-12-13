/**
 * 
 */
package vo;


import utility.MyDate;

/**
 * @author 1
 *
 */
public class KDJVO {
	private MyDate date;
	private String id;
	private double rsv;
	private double k;
	private double d;
	private double j;

	public KDJVO(MyDate date, String id, double rsv, double k, double d, double j) {
		super();
		this.date = date;
		this.id = id;
		this.rsv = rsv;
		this.k = k;
		this.d = d;
		this.j = j;
	}

	public MyDate getDate() {
		return date;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getRsv() {
		return rsv;
	}

	public void setRsv(double rsv) {
		this.rsv = rsv;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getJ() {
		return j;
	}

	public void setJ(double j) {
		this.j = j;
	}

	@Override
	public String toString() {
		return "StockID:" + getId() + "Date:" + getDate() + "RSV:" + getRsv() + "K：" + getK() + "D：" + getD() + "J："
				+ getJ() + "\n";
	}
}
