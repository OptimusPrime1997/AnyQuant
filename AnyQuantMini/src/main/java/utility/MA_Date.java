/**
 * 
 */
package utility;
/**
 * @author run
 *
 */
public class MA_Date {
	double ma;
	MyDate date;
	
	public MA_Date(MyDate d,double m){
		this.date=d;
		this.ma=m;
	}
	
	public MyDate getDate(){
		return this.date;
	}
	
	public double getMA(){
		return this.ma;
	}

}
