/**
 * 
 */
package bl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import bl.selectBL.SelectBLController;
import po.StockPO;
import strategy.sorthelper.SortByMax;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;

/**
 * @author run
 *
 */
public class SelectTest {
	
	SelectBLController sc;
	
	public SelectTest(){
		sc=new SelectBLController(new SortByMax());
	}

	public void test1(){
		try {
			sc.delete("j", "sh600000");
		} catch (NotFoundName_exception e) {
			System.out.println("user not found");
		}
		System.out.println("add sh600000...");
	}
	
	public void test2(){
		ArrayList<StockPO> s=new ArrayList<StockPO>();
		try {
			s=sc.show("j");
			System.out.println("showing");
		} catch (NotFoundName_exception e) {
			System.out.println("user not found");
		} catch (TimeOut_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<StockPO> it=s.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getId());
		}
		System.out.println("end....");
	}
	
	public static void main(String[] args){
		SelectTest st=new SelectTest();
		st.test1();
		st.test2();
	}
}