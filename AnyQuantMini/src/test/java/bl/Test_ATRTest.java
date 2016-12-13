/**
 * 
 */
package bl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import utility.*;
import bl.detailBL.Helper;
import blservice.HelperService;
import utility.ATR_Date;
import utility.exception.TimeOut_exception;

/**
 * @author run
 *
 */
public class Test_ATRTest {
	HelperService atrh;
	
	public Test_ATRTest(){
		this.atrh=new Helper();
	}
	
	public void test(){
		Range_Date range =new Range_Date(new MyDate(2016,3,5),new MyDate(2016,3,13));
		try {
			print(atrh.get_ATR("sh600533",range));
		} catch (TimeOut_exception e) {
			System.out.println("Time out");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		}
	}
	
	void print(ArrayList<ATR_Date> r){
		Iterator<ATR_Date> it=r.iterator();
		while(it.hasNext()){
			ATR_Date temp=it.next();
			System.out.println("Date: "+temp.getDate().toString());
			System.out.println("ATR: "+temp.getATR());
		}
	}
	
	public static void main(String[] args){
		Test_ATRTest s=new Test_ATRTest();
		s.test();
	}

}
