/**
 * 
 */
package bl.detailBL;

import utility.ATR_Date;
import utility.MA_Date;
import utility.MyDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import blservice.DetailBLService;
import blservice.HelperService;
import data.DataFactory;
import dataservice.StockDataService;
import po.StockPO;
import strategy.sorthelper.Mysort;
import utility.Range_Date;
import utility.enums.Days;
import utility.enums.Field;
import utility.enums.IndustryType;
import utility.enums.Market;
import utility.enums.SortOrder;
import utility.exception.ExistID_exception;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;

/**
 * @author run
 *
 */
public class DetailBLController implements DetailBLService {

	Mysort Sort;
	RemoveHelper removehelper;
	StockDataService service;
	getMA MA;
	HelperService atr;
	getDes des;

	public DetailBLController(Mysort mysort) {
		Sort = mysort;
		removehelper = new RemoveHelper();
		service = DataFactory.getInstance().getStockData();
		MA = new AverageMAhelper();
		atr = new Helper();
		des = new DesHelper();
	}

	public void setSortStrategy(Mysort mysort) {
		Sort = mysort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detailBL_interface.DetailBLService#sort(java.util.ArrayList,
	 * Enum.SortOrder)
	 */
	@Override
	public ArrayList<StockPO> sort(ArrayList<StockPO> array, SortOrder order) {
		return Sort.sort(array, order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detailBL_interface.DetailBLService#remove(java.lang.String)
	 */
	@Override
	public boolean select(String name, String id) throws NotFoundName_exception, ExistID_exception {
		return removehelper.select(name, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blservice.DetailBLService#getData(java.lang.String)
	 */
	@Override
	public ArrayList<StockPO> getData(String id, Market market, Range_Date range_Date)
			throws TimeOut_exception, IOException {

		ArrayList<StockPO> lists = service.getDataByID(id, range_Date);
		// descend sort
		Comparator<StockPO> comparatorDes = new Comparator<StockPO>() {
			public int compare(StockPO p1, StockPO p2) {
				return p1.compareTo(p2);
			}
		};
		Collections.sort(lists, comparatorDes);

		return lists;
	}

	@Override
	public ArrayList<StockPO> getIndustryByID(IndustryType industry, Range_Date date)
			throws IOException, TimeOut_exception {
		MyDate start = date.lowdate.getRecentDate();
		MyDate end = date.highdate;
		assert (start.compareTo(end) < 0) : ("The date range is wrong!");
		ArrayList<String> list = service.getLoacalStockIds();
		String Id1 = list.get(industry.getNo() * 3 + 0);
		String Id2 = list.get(industry.getNo() * 3 + 1);
		String Id3 = list.get(industry.getNo() * 3 + 2);
		ArrayList<StockPO> pos1 = service.getDataByID(Id1, date);
		ArrayList<StockPO> pos2 = service.getDataByID(Id2, date);
		ArrayList<StockPO> pos3 = service.getDataByID(Id3, date);
		ArrayList<StockPO> result = new ArrayList<StockPO>();
		String name = industry.getName();
		String id = industry.getID();
		StockPO p1 = null;
		StockPO p2 = null;
		StockPO p3 = null;
		StockPO p = null;
		for (int i = 0; i < pos1.size(); i++) {
			p1 = pos1.get(i);
			p2 = pos2.get(i);
			p3 = pos3.get(i);
			double open = (p1.getStartprice() + p2.getStartprice() + p3.getStartprice()) / 3;
			double close = (p1.getEndprice() + p2.getEndprice() + p3.getEndprice()) / 3;
			double high = (p1.getMaxprice() + p2.getMaxprice() + p3.getMaxprice()) / 3;
			double low = (p1.getMinprice() + p2.getMinprice() + p3.getMinprice()) / 3;
			double adjprice = (p1.getAdjprice() + p2.getAdjprice() + p3.getAdjprice()) / 3;
			long volume = (p1.getVolume() + p2.getVolume() + p3.getVolume()) / 3;
			double pe = (p1.getPe() + p2.getPe() + p3.getPe()) / 3;
			double pb = (p1.getPb() + p2.getPb() + p3.getPb()) / 3;
			double turnover = (p1.getTurnover() + p2.getTurnover() + p3.getTurnover()) / 3;
			p = new StockPO(id, name, open, close, high, low, volume, turnover, pe, pb, adjprice, p1.getDate());
			result.add(i, p);
		}
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blservice.DetailBLService#getATR(java.lang.String,
	 * utility.Range_Date)
	 */
	@Override
	public ArrayList<ATR_Date> getATR(String id, Range_Date range) throws TimeOut_exception, IOException {
		return atr.get_ATR(id, range);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blservice.DetailBLService#getMA(java.lang.String,
	 * utility.Range_Date, utility.enums.Days)
	 */
	@Override

	public ArrayList<MA_Date> getMA(String id, Range_Date range, Days days) throws TimeOut_exception, IOException {
		return MA.get_MA(id, range, days);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blservice.DetailBLService#getDescription(java.util.ArrayList)
	 */
	@Override
	public String getDescription(ArrayList<StockPO> array) throws IOException {
		return des.getDescription(array);
	}
}
