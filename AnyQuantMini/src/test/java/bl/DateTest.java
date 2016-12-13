/**
 * 
 */
package bl;

import utility.MyDate;

/**
 * @author run
 *
 */
public class DateTest {
	public static void main(String[] args){
		MyDate d1=new MyDate(2015,3,3);
		MyDate d2=new MyDate(2015,3,3);
		System.out.println(d1.getDay());
		System.out.println(d1.getRecentDate().toString());
	}

}
