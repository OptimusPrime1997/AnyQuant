/**
 * 
 */
package bl.marketBL;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import data.DataFactory;
import dataservice.StockDataService;
import po.StockPO;
import utility.Range_Date;
import utility.exception.TimeOut_exception;

/**
 * @author run
 *this is the class provide the search function
 */
public class SearchHelper {
	StockDataService service;
	
	public SearchHelper(){
		service=DataFactory.getInstance().getStockData();;
	}
	/**
	 * search by id
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<StockPO> searchByID(String id) throws TimeOut_exception, IOException{
		Date now =new Date();
		Date yes =new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(calendar.DAY_OF_MONTH, -1);
		yes=calendar.getTime();
		
		SimpleDateFormat dFormat =new SimpleDateFormat("yyyy-MM-dd");
		String[] temp=dFormat.format(yes).split("-");
		String[] temp2=dFormat.format(now).split("-");
		
		utility.MyDate date1=new utility.MyDate(Integer.parseInt(temp[0]),Integer.parseInt(temp[1])
				, Integer.parseInt(temp[2]));
		
		utility.MyDate date2=new utility.MyDate(Integer.parseInt(temp2[0]),Integer.parseInt(temp2[1])
				, Integer.parseInt(temp2[2]));
		
//		System.out.println(date2.getYear()+"　"+date2.getMonth()+" "+date2.getDay());
		return service.getDataByID(id, new Range_Date(date1, date2));
	}
	
	/**
	 * search by name
	 * @param name
	 * @return
	 */
	public ArrayList<StockPO> searchByName(String name) throws TimeOut_exception{
		return null;
	}
}
