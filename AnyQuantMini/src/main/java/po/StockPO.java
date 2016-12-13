package po;

import java.io.IOException;
import java.io.Serializable;

import data.DataFactory;
import dataservice.StockDataService;
import utility.MyDate;
import utility.Range_Date;
import utility.exception.TimeOut_exception;

/**
 * this is the abstract of 'stock',the definition of attributes is shown in the
 * definition of construct method
 * 
 * @author run
 *
 */
public class StockPO implements Serializable, Comparable<StockPO> {

	private static final long serialVersionUID = 1L;
	/**
	 * 股票代号,market+代号
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	private double startprice;
	private double endprice;
	private double maxprice;
	private double minprice;

	/**
	 * 涨跌额
	 */
	private double range;

	/**
	 * 交易量
	 */
	private long volume;
	/**
	 * 换手率
	 */
	private double turnover;
	/**
	 * 市盈率
	 */
	private double pe;
	/**
	 * 市净率
	 */
	private double pb;
	/**
	 * 后复股权价
	 */
	private double adjprice;
	private MyDate date;
	/**
	 * 成交额
	 */
	private double money;
	/**
	 * 真实波动幅度
	 */
	private double tr;

	public StockPO(String id, String name, double startprice, double endprice, double maxprice, double minprice,
			long num, double turnover, double pe, double pb, double adjprice, MyDate date) {
		super();
		if (id.equals("benchmark/hs300")) {
			this.id = "hs300";
		} else {
			this.id = id;
		}
		this.name = name;
		this.startprice = startprice;
		this.endprice = endprice;
		this.maxprice = maxprice;
		this.minprice = minprice;
		this.volume = num;
		this.turnover = turnover;
		this.pe = pe;
		this.pb = pb;
		this.adjprice = adjprice;
		this.date = date;
		this.range = 0;
		this.money = adjprice * volume;
		this.tr = 0;
	}

	public StockPO(String id, String name, double open, double close, double high, double low, double volume,
			double turnover, double adjprice, double pe, double pb, java.sql.Date date, double money, double rangeData,
			double tr) {
		this.id = id;
		this.name = name;
		this.startprice = open;
		this.endprice = close;
		this.maxprice = high;
		this.minprice = low;
		this.volume = new Long((long) volume);
		this.turnover = turnover;
		this.adjprice = adjprice;
		this.pe = pe;
		this.pb = pb;
		this.date = new MyDate(date.toString());
		this.money = money;
		this.range = rangeData;
		this.tr = tr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getStartprice() {
		return startprice;
	}

	public void setStartprice(double startprice) {
		this.startprice = startprice;
	}

	public double getEndprice() {
		return endprice;
	}

	public void setEndprice(double endprice) {
		this.endprice = endprice;
	}

	public double getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(double maxprice) {
		this.maxprice = maxprice;
	}

	public double getMinprice() {
		return minprice;
	}

	public void setMinprice(double minprice) {
		this.minprice = minprice;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long num) {
		this.volume = num;
	}

	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public double getPe() {
		return pe;
	}

	public void setPe(double pe) {
		this.pe = pe;
	}

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double a) {
		this.money = a;
	}

	public double getPb() {
		return pb;
	}

	public void setPb(double pb) {
		this.pb = pb;
	}

	public double getAdjprice() {
		return adjprice;
	}

	public void setAdjprice(double adjprice) {
		this.adjprice = adjprice;
	}

	public MyDate getDate() {
		return date;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}

	public double getTr() {
		return tr;
	}

	public void setTr(double tr) {
		this.tr = tr;
	}

	@Override
	public boolean equals(Object p) {
		if (id.equals(((StockPO) p).getId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "name:" + name + "id:" + id + ",open:" + startprice + ",close:" + endprice + ",high:" + maxprice
				+ ",low:" + minprice + ",volume:" + volume + ",turnover:" + turnover + ",adjPrice:" + adjprice + ",pe:"
				+ pe + ",pb:" + pb + ",money:" + money + ",range:" + range + ",date：" + date;

	}

	/**
	 * @return the range
	 * @throws IOException
	 */
	public double getRange() {
		return this.range;
	}

	/**
	 * @param range
	 *            the range to set
	 */
	public void setRange() throws IOException {
		StockDataService data = DataFactory.getInstance().getStockData();
		;
		Range_Date rg = new Range_Date(this.date.beforeDay(), this.date);
		StockPO yes = null;
		try {
			yes = data.getDataByID(this.getId(), rg).get(0);
		} catch (TimeOut_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.range = (this.endprice - yes.getEndprice()) / yes.getEndprice() * 100;
	}

	public void setRangeData(double rg) {
		this.range = rg;
	}

	/*
	 * 
	 * use descend sort
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(StockPO o) {
		// TODO Auto-generated method stub
		if (date.compareTo(o.getDate()) > 0) {
			return -1;
		} else if (date.compareTo(o.getDate()) < 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
