package bl.marketBL;

import java.io.IOException;
import java.util.ArrayList;

import blservice.marketBLService;
import po.StockPO;
import strategy.sifthelper.Mysift;
import strategy.sorthelper.Mysort;
import utility.MyDate;
import utility.Range;
import utility.enums.SortOrder;
import utility.exception.TimeOut_exception;

public class MarketBLController implements marketBLService {
	// SortHelper sortHelper;
	// SiftHelper siftHelper;

	Mysort sort;
	Mysift sift;
	Datahelper datahelper;
	SearchHelper searchhelper;

	/**
	 * construct methods using strategies
	 * 
	 * @param sorthelper
	 * @param sithelper
	 */
	public MarketBLController(Mysort mysort,Mysift mysift){
		sort=mysort;
		sift=mysift;
		searchhelper=new SearchHelper();
		datahelper=new Datahelper();
	}

	public void setSiftStrategy(Mysift mysift) {
		sift = mysift;
	}

	public void setSortStrategy(Mysort mysort) {
		sort = mysort;
	}

	/**
	 * the sort methods of controller,with jobs send to sorthelper to complete
	 */
	@Override
	public ArrayList<StockPO> sort(ArrayList<StockPO> array, SortOrder order) {
		// TODO Auto-generated method stub
		return sort.sort(array, order);
	}

	/**
	 * the sift methods of controller,with jobs send to sifthelper to complete
	 */

	@Override
	public ArrayList<StockPO> sift(ArrayList<StockPO> array, Range range) {
		// TODO Auto-generated method stub
		return sift.sift(array, range);
	}

	/**
	 * the method to search by id
	 * @throws IOException 
	 */
	@Override
	public ArrayList<StockPO> searchByID(String id) throws TimeOut_exception, IOException {
		// TODO Auto-generated method stub
		return searchhelper.searchByID(id);
	}

	/**
	 * the method to search by name
	 */
	@Override
	public ArrayList<StockPO> searchByName(String name) throws TimeOut_exception {
		// TODO Auto-generated method stub
		return searchhelper.searchByName(name);
	}

	/**
	 * the method to get data in a day
	 * 
	 * @throws TimeOut_exception
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see marketBL_interface.marketBLService#getData(utility.Date)
	 */
	@Override
	public ArrayList<StockPO> getData(MyDate date) throws TimeOut_exception {
		// TODO Auto-generated method stub
//		System.out.println(date.getRecentDate());
		return datahelper.getdata(date.getRecentDate());
	}
	
	

}
