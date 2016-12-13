package bl.marketBL;

import java.io.IOException;
import java.util.ArrayList;

import data.DataFactory;
import dataservice.StockDataService;
import po.StockPO;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.Field;
import utility.enums.IndustryType;
import utility.enums.Market;
import utility.exception.TimeOut_exception;

public class Datahelper {
	StockDataService service;

	public Datahelper() {
		service = DataFactory.getInstance().getStockData();
	}

	/**
	 * methods to get stocks at a certain day
	 * 
	 * @param date
	 * @return
	 * @throws TimeOut_exception
	 */
	public ArrayList<StockPO> getdata(MyDate date) throws TimeOut_exception{
		
		return service.getDataByDate(date, Market.ALL);

	}
	
}
