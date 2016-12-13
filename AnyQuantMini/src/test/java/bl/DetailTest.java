/**
 * 
 */
package bl;

import java.io.IOException;
import java.util.ArrayList;

import bl.detailBL.DetailBLController;
import po.StockPO;
import strategy.sorthelper.SortByMax;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.Market;
import utility.exception.ExistID_exception;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;

/**
 * @author run
 *
 */
public class DetailTest {
	
	DetailBLController dc; 
	
	public DetailTest(){
		dc= new DetailBLController(new SortByMax());
	}
	
	public void test1(){
		try {
			System.out.println(dc.select("jr", "sh600000"));
			System.out.println("removed/added");
		} catch (NotFoundName_exception e) {
			System.out.println("user not found");
		} catch (ExistID_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test2(){
		MyDate date1=new MyDate(2016,3,3);
		MyDate date2=new MyDate(2016,3,4);
		try {
			ArrayList<StockPO> array=dc.getData("sh600000",Market.ALL,new Range_Date(date1,date2));
			System.out.println(array.get(0).toString());
		} catch (TimeOut_exception | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		DetailTest d=new DetailTest();
//		d.test1();
		d.test2();
	}

}
