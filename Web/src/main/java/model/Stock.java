package model;

import utility.MyDate;
import utility.NumHelper;

/**
 * this is the abstract of 'stock',the definition of attributes is shown in the
 * definition of construct method
 * 
 * @author run
 *
 */
public class Stock implements Comparable<Stock> {

	/**
	 * 股票代号,market+代号
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 开盘价
	 */
	private double startprice;
	/**
	 * 收盘价
	 */
	private double endprice;
	/**
	 * 最高价
	 */
	private double maxprice;
	/**
	 * 最低价
	 */
	private double minprice;

	/**
	 * 涨跌额
	 */
	private double range;

	/**
	 * 交易量
	 */
	private double volume;
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
	/**
	 * 日期
	 */
	private MyDate date;
	/**
	 * 成交额
	 */
	private double money;
	/**
	 * 真实波动幅度
	 */
	private double tr;

	public Stock() {
		// TODO Auto-generated constructor stub
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
		this.startprice = NumHelper.toFixed(startprice, 2);
	}

	public double getEndprice() {
		return endprice;
	}

	public void setEndprice(double endprice) {
		this.endprice = NumHelper.toFixed(endprice, 2);
	}

	public double getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(double maxprice) {
		this.maxprice = NumHelper.toFixed(maxprice, 2);
	}

	public double getMinprice() {
		return minprice;
	}

	public void setMinprice(double minprice) {
		this.minprice = NumHelper.toFixed(minprice, 2);
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = NumHelper.toFixed(range, 4);
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double d) {
		this.volume = NumHelper.toFixed(d, 2);
	}

	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = NumHelper.toFixed(turnover, 2);
	}

	public double getPe() {
		return pe;
	}

	public void setPe(double pe) {
		this.pe = NumHelper.toFixed(pe, 2);
	}

	public double getPb() {
		return pb;
	}

	public void setPb(double pb) {
		this.pb = NumHelper.toFixed(pb, 2);
	}

	public double getAdjprice() {
		return adjprice;
	}

	public void setAdjprice(double adjprice) {
		this.adjprice = NumHelper.toFixed(adjprice, 2);
	}

	public MyDate getDate() {
		return date;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = NumHelper.toFixed(money, 2);
	}

	public double getTr() {
		return tr;
	}

	public void setTr(double tr) {
		this.tr = tr;
	}

	@Override
	public String toString() {
		return "name:" + name + "id:" + id + ",open:" + startprice + ",close:" + endprice + ",high:" + maxprice
				+ ",low:" + minprice + ",volume:" + volume + ",turnover:" + turnover + ",adjPrice:" + adjprice + ",pe:"
				+ pe + ",pb:" + pb + ",money:" + money + ",range:" + range + ",date：" + date;

	}

	/*
	 * use descend sort
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Stock o) {
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
