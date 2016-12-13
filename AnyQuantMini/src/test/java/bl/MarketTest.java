/**
 * 
 */
package bl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import bl.marketBL.MarketBLController;
import blservice.marketBLService;
import po.StockPO;
import strategy.sifthelper.SiftByMax;
import strategy.sorthelper.SortByMax;
import utility.MyDate;
import utility.exception.TimeOut_exception;

/**
 * @author run
 *
 */
public class MarketTest {
	marketBLService bl;
	
	public MarketTest(){
		bl=new MarketBLController(new SortByMax(), new SiftByMax());
	}
	
	public static void print(ArrayList<StockPO> array){
		Iterator<StockPO> iterator=array.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next().toString());
		}
	}
	
	public void test1(){
		try {
			print(bl.searchByID("sh600004"));
		} catch (TimeOut_exception | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hello end");
	}
	
	public void test2(){
		try {
			System.out.println((new MyDate(2016,3,8)).getRecentDate().toString());
			print(bl.getData((new MyDate(2016,3,8)).getRecentDate()));
			System.out.println("end the process");
		} catch (TimeOut_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		MarketTest test=new MarketTest();
//		test.test1();
		test.test2();
	}

}
