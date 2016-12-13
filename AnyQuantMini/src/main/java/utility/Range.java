package utility;

public class Range {
	/**
	 * the abstract of all the situations when sifting
	 * It has many sub-class to implements different functions
	 * 
	 */
	public MyDate lowdate;
	public MyDate highdate;
	public double lowdouble;
	public double highdouble;
	public long lowlong;
	public long highlong;
	
	public Range(MyDate lowDate,MyDate highDate,double lowDouble,double highDouble,long lowInt,long highInt){
		lowdate=lowDate;
		highdate=highDate;
		lowdouble=lowDouble;
		highdouble=highDouble;
		lowlong=lowInt;
		highlong=highInt;
	}
}
