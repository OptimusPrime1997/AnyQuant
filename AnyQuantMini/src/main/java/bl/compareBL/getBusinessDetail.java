/**
 * 
 */
package bl.compareBL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import data.StockData;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.IndustryType;
import po.StockPO;

/**
 * @author run
 *
 */
public class getBusinessDetail {
	ArrayList<StockPO> array1;
	ArrayList<StockPO> array2;
	ArrayList<StockPO> array3;

	final int N = 3;
	MyDate date1;
	MyDate date2;
	MyDate date3;
	MyDate date4;

	StockData data;
	Compute c;

	public getBusinessDetail() {
		array1 = new ArrayList<StockPO>();
		array2 = new ArrayList<StockPO>();
		array3 = new ArrayList<StockPO>();
		date4 = new MyDate(Calendar.getInstance());
		date3 = date4.beforeMonth();
		date2 = date3.beforeMonth();
		date1 = date2.beforeMonth();
		data = new StockData();
		c = new Compute();
	}

	public ArrayList<StockPO> getMonth_One() throws IOException {
		ArrayList<String> ids = data.getLoacalStockIds();
		for (int i = 0; i < N; ++i) {
			double endprice = 0;
			double vol = 0;
			double startprice = 0;
			double maxprice = 0;
			double minprice = 0;
			for (int j = 0; j < N; ++j) {
				ArrayList<StockPO> t1 = data.getDataByID(ids.get(i * N + j), new Range_Date(date1, date2));
				endprice += c.getEX_endprice(t1);
				startprice += c.getEX_Startprice(t1);
				maxprice += c.getEX_Maxprice(t1);
				minprice += c.getEX_Minprice(t1);
				vol += c.getEX_Volume(t1);
			}
			String name = "", id = "";
			if (i == 0) {
				name = IndustryType.ESTATE.getName();
				id = IndustryType.ESTATE.getID();
			} else if (i == 1) {
				name = IndustryType.FINANCE.getName();
				id = IndustryType.FINANCE.getID();
			} else if (i == 2) {
				name = IndustryType.MATERIAL.getName();
				id = IndustryType.MATERIAL.getID();
			}
			array1.add(new StockPO(id, name, startprice / N, endprice / N, maxprice / N, minprice / N, (long) (vol / N),
					0, 0, 0, 0, date2));
		}

		return array1;
	}

	public ArrayList<StockPO> getMonth_Two() throws IOException {
		ArrayList<String> ids = data.getLoacalStockIds();
		for (int i = 0; i < N; ++i) {
			double endprice = 0;
			double vol = 0;
			double startprice = 0;
			double maxprice = 0;
			double minprice = 0;
			for (int j = 0; j < N; ++j) {
				ArrayList<StockPO> t1 = data.getDataByID(ids.get(i * N + j), new Range_Date(date2, date3));
				endprice += c.getEX_endprice(t1);
				startprice += c.getEX_Startprice(t1);
				maxprice += c.getEX_Maxprice(t1);
				minprice += c.getEX_Minprice(t1);
				vol += c.getEX_Volume(t1);
			}
			String name = "", id = "";
			if (i == 0) {
				name = IndustryType.ESTATE.getName();
				id = IndustryType.ESTATE.getID();
			} else if (i == 1) {
				name = IndustryType.FINANCE.getName();
				id = IndustryType.FINANCE.getID();
			} else if (i == 2) {
				name = IndustryType.MATERIAL.getName();
				id = IndustryType.MATERIAL.getID();
			}
			array2.add(new StockPO(id, name, startprice / N, endprice / N, maxprice / N, minprice / N, (long) (vol / N),
					0, 0, 0, 0, date3));
		}

		return array2;
	}

	public ArrayList<StockPO> getMonth_Three() throws IOException {
		ArrayList<String> ids = data.getLoacalStockIds();
		for (int i = 0; i < N; ++i) {
			double endprice = 0;
			double vol = 0;
			double startprice = 0;
			double maxprice = 0;
			double minprice = 0;
			for (int j = 0; j < N; ++j) {
				ArrayList<StockPO> t1 = data.getDataByID(ids.get(i * N + j), new Range_Date(date3, date4));
				endprice += c.getEX_endprice(t1);
				startprice += c.getEX_Startprice(t1);
				maxprice += c.getEX_Maxprice(t1);
				minprice += c.getEX_Minprice(t1);
				vol += c.getEX_Volume(t1);
			}
			String name = "", id = "";
			if (i == 0) {
				name = IndustryType.ESTATE.getName();
				id = IndustryType.ESTATE.getID();
			} else if (i == 1) {
				name = IndustryType.FINANCE.getName();
				id = IndustryType.FINANCE.getID();
			} else if (i == 2) {
				name = IndustryType.MATERIAL.getName();
				id = IndustryType.MATERIAL.getID();
			}
			array3.add(new StockPO(id, name, startprice / N, endprice / N, maxprice / N, minprice / N, (long) (vol / N),
					0, 0, 0, 0, date4));
		}

		return array3;
	}

}
