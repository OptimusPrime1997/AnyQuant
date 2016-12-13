/**
 * 
 */
package vo;

import po.StockPO;
import utility.MyDate;

/**
 * @author 1
 *
 */
public class StockVO {
	public String id;
	public double startprice;
	public double endprice;
	public double maxprice;
	public double minprice;
	public long num;
	public double money;
	public MyDate date;
	public double rangerate;
	public double range;

	/**
	 * the struct methods
	 * 
	 * @param Id
	 *            the id of a stock
	 * @param Startprice
	 *            the price at the start
	 * @param Endprice
	 *            the price at the end
	 * @param Maxprice
	 *            the max price of the day
	 * @param Minprice
	 *            the minimal price of the day
	 * @param Num
	 *            the total number bought or sold
	 * @param Money
	 *            the total money bought or sold
	 * @param day
	 *            the date
	 */
	public StockVO(String Id, double Startprice, double Endprice, double Maxprice, double Minprice, long Num,
			double Money, MyDate day) {
		id = Id;
		startprice = Startprice;
		endprice = Endprice;
		maxprice = Maxprice;
		minprice = Minprice;
		num = Num;
		money = Money;
	}

	@Override
	public boolean equals(Object o) {
		if (id.equals(((StockVO) o).id)) {
			return true;
		} else {
			return false;
		}
	}
	
	public StockVO(StockPO po){
		this.id=po.getId();
		this.date=po.getDate();
		this.maxprice=po.getMaxprice();
		this.minprice=po.getMinprice();
		this.endprice=po.getEndprice();
		this.startprice=po.getStartprice();
		this.range=po.getMaxprice()-po.getMinprice();
		this.money=po.getMoney();
		this.num=po.getVolume();
		this.rangerate=0;
	}
}
