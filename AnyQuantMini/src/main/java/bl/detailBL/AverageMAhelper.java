/**
 * 
 */
package bl.detailBL;

import dataservice.StockDataService;
import utility.MA_Date;
import po.StockPO;
import data.DataFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import utility.MyDate;
import utility.Range_Date;
import utility.enums.Days;
import utility.exception.TimeOut_exception;
/**this class helps get the MA of a stock
 * @author run
 *
 */
public class AverageMAhelper implements getMA{
	StockDataService service;
	
	public AverageMAhelper(){
		service=DataFactory.getInstance().getStockData();
	}
	
	public ArrayList<MA_Date> get_MA(String id,Range_Date range,Days days) throws TimeOut_exception, IOException {
		ArrayList<MA_Date> result=new ArrayList<MA_Date>();
		int i=0;
		
		switch(days){
		case five:
			i=5;break;
		case ten:
			i=10;break;
		case thirty:
			i=30;break;
		case sixty:
			i=60;break;
		case onehundred_and_twenty:
			i=120;break;
		}
		
		MyDate high=range.highdate;
		MyDate t=range.lowdate;
		MyDate temp;
		while(t.compareTo(high)<=0){
			temp=t;
			for(int j=0;j<i;++j){
				temp=temp.beforeDay();
			}
			Range_Date date=new Range_Date(temp,t);
			MA_Date md=new MA_Date(t,compute(id,date)/i);
			result.add(md);
			t=t.afterDay();
		}
		
		return result;

	}
	
	
	/**
	 * compute the total endprice
	 * @param id
	 * @param range
	 * @return
	 * @throws TimeOut_exception
	 * @throws IOException
	 */
	double compute(String id,Range_Date range) throws TimeOut_exception, IOException{
		double result=0;
		ArrayList<StockPO> temp=service.getDataByID(id, range);
		Iterator<StockPO> it=temp.iterator();
		while(it.hasNext()){
			result=result+it.next().getEndprice();
		}
		return result;
	}
}