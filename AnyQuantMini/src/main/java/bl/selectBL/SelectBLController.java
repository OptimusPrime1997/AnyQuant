/**
 * 
 */
package bl.selectBL;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import blservice.SelectBLService;
import data.DataFactory;
import data.StockData;
import data.UserData;
import dataservice.StockDataService;
import dataservice.UserDataService;
import po.StockPO;
import strategy.sorthelper.Mysort;
import utility.Range_Date;
import utility.enums.Market;
import utility.enums.SortOrder;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;

/**
 * @author run
 *
 */
public class SelectBLController implements SelectBLService {
	
	Mysort Sort;
	ChooseHelper choosehelper;
	StockDataService service;
	
	public SelectBLController(Mysort mysort){
		Sort=mysort;
		choosehelper=new ChooseHelper();
		service=DataFactory.getInstance().getStockData();;
	}

	
	/* (non-Javadoc)
	 * @see BLService.SelectBLService#sort(java.util.ArrayList, Enum.SortOrder)
	 */
	@Override
	public ArrayList<StockPO> sort(ArrayList<StockPO> array, SortOrder order) {
		// TODO Auto-generated method stub
		return Sort.sort(array, order);
	}

	/* (non-Javadoc)
	 * @see BLService.SelectBLService#choose(java.lang.String)
	 */
	@Override
	public boolean delete(String name,String id) throws NotFoundName_exception {
		// TODO Auto-generated method stub
		return choosehelper.delete(name,id);
	}

	/* (non-Javadoc)
	 * @see blservice.SelectBLService#show(java.lang.String)
	 */
	@Override
	public ArrayList<StockPO> show(String name) throws NotFoundName_exception, TimeOut_exception, IOException {
		ArrayList<StockPO> result=new ArrayList<StockPO>();
		
		Date now =new Date();
		Date yes =new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(calendar.DAY_OF_MONTH, -1);
		yes=calendar.getTime();
		
		SimpleDateFormat dFormat =new SimpleDateFormat("yyyy-MM-dd");
		String[] temp=dFormat.format(yes).split("-");
		utility.MyDate date=new utility.MyDate(Integer.parseInt(temp[0]),Integer.parseInt(temp[1])
				, Integer.parseInt(temp[2]));
		
		UserDataService userservice=new UserData();
		ArrayList<String> list=userservice.getUser(name).getStocks();
		assert list!=null;
		Iterator<String> it=list.iterator();
		ArrayList<StockPO> temp1;
		
		utility.MyDate d=date.clone();
		
		while(it.hasNext()){
			temp1=service.getDataByID(it.next(),  new Range_Date(date.beforeDay(), d));
			Iterator<StockPO> iterator=temp1.iterator();
			while(iterator.hasNext()){
				result.add(iterator.next());
			}
		}
		return result;
	}

}
