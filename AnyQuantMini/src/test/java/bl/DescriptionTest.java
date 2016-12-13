/**
 * 
 */
package bl;

import java.io.IOException;
import java.util.ArrayList;

import bl.detailBL.DetailBLController;
import data.StockData;
import po.StockPO;
import utility.MyDate;
import utility.Range_Date;
import utility.exception.TimeOut_exception;
/**
 * @author run
 *
 */
public class DescriptionTest {
	DetailBLController bl;
	public DescriptionTest(){
		bl = new DetailBLController(null);
	}
	
	public static void main(String[] args) throws IOException{
		StockData data = new StockData();
		
		Range_Date range = new Range_Date(new MyDate(2016,2,12),new MyDate(2016,4,12));
		DescriptionTest test = new DescriptionTest();
		ArrayList<StockPO> a=new ArrayList<StockPO>();
		try {
			a = test.bl.getData("sh600340", null,range);
		} catch (TimeOut_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(test.bl.getDescription(a));
	}
	
}
