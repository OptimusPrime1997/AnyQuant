/**
 * 
 */
package bl.marketBL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


import bl.compareBL.Compute;
import data.DataFactory;
import data.StockData;
import dataservice.DataFactoryDataService;
import dataservice.MaxMinDataService;
import po.MaxMinPO;
import po.StockPO;
import utility.MyDate;
import utility.Range_Date;
import vo.ChartVO;

import utility.enums.IndustryType;
import utility.exception.NotFoundName_exception;

/**
 * @author run
 *
 */
public class ChartHelper {

	StockData data;
	Compute c;
	final int Num = 3;
	ChartVO min1;
	ChartVO min2;
	ChartVO min3;

	ChartVO max1;
	ChartVO max2;
	ChartVO max3;

	MaxMinDataService ser;
	ArrayList<String> ids;
	DataFactoryDataService dataFactoryDataService;

	public ChartHelper() {
		this.dataFactoryDataService = DataFactory.getInstance();
		data = new StockData();
		c = new Compute();
		this.ids = data.getLoacalStockIds();
		ser = dataFactoryDataService.getMaxMin();
		try {
			MaxMinPO p1 = ser.getChart(IndustryType.ESTATE.getID());
			min1 = p1.getMinPO();
			max1 = p1.getMaxPO();

			MaxMinPO p2 = ser.getChart(IndustryType.FINANCE.getID());
			min2 = p2.getMinPO();
			max2 = p2.getMaxPO();

			MaxMinPO p3 = ser.getChart(IndustryType.MATERIAL.getID());
			min3 = p3.getMinPO();
			max3 = p3.getMaxPO();
		} catch (NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ChartVO Estate() {
		MyDate today = new MyDate(Calendar.getInstance());
		MyDate yes = today.beforeDay();
		ArrayList<StockPO> array = new ArrayList<StockPO>();
		for (int i = 0; i < Num; ++i) {
			try {
				array.add(data.getDataByID(ids.get(i), new Range_Date(yes, today)).get(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double turn = c.getEX_turnover(array);
		double vol = c.getEX_Volume(array);
		double adj = c.getEX_Adjprice(array);
		double pe = c.getEX_pe(array);
		double pb = c.getEX_pb(array);

		if (turn < min1.getTurnover()) {
			min1.setTurnover(turn);
		} else if (turn > max1.getTurnover()) {
			max1.setTurnover(turn);
		}

		if (vol < min1.getVolume()) {
			min1.setTurnover(vol);
		} else if (turn > max1.getVolume()) {
			max1.setVol((long) vol);
		}

		if (adj < min1.getAdjprice()) {
			min1.setAdj(adj);
		} else if (adj > max1.getAdjprice()) {
			max1.setAdj(adj);
		}

		if (pe < min1.getPe()) {
			min1.setPe(pe);
		} else if (pb > max1.getPe()) {
			max1.setPe(pe);
		}

		if (pb < min1.getPb()) {
			min1.setPb(pb);
		} else if (pb > max1.getPb()) {
			max1.setPb(pb);
		}
//		try {
//			ser.update(IndustryType.ESTATE.getID(), new MaxMinPO(max1, min1));
//		} catch (ExistName_exception e) {
//			System.out.println("update error");
//			e.printStackTrace();
//		}
		return new ChartVO((turn - min1.getTurnover()) / (max1.getTurnover() - min1.getTurnover()) * 100,
				(vol - min1.getVolume()) / (max1.getVolume() - min1.getVolume()) * 100,
				(adj - min1.getAdjprice()) / (max1.getAdjprice() - min1.getAdjprice()) * 100,
				(pe - min1.getPe()) / (max1.getPe() - min1.getPe()) * 100,
				(pb - min1.getPb()) / (max1.getPb() - min1.getPb()) * 100, IndustryType.ESTATE.getName(),
				IndustryType.ESTATE.getID(), yes);
	}

	public ChartVO Finance() {
		MyDate today = new MyDate(Calendar.getInstance());
		MyDate yes = today.beforeDay();
		ArrayList<StockPO> array = new ArrayList<StockPO>();
		for (int i = Num; i < 2 * Num; ++i) {
			try {
				array.add(data.getDataByID(ids.get(i), new Range_Date(yes, today)).get(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double turn = c.getEX_turnover(array);
		double vol = c.getEX_Volume(array);
		double adj = c.getEX_Adjprice(array);
		double pe = c.getEX_pe(array);
		double pb = c.getEX_pb(array);

		if (turn < min2.getTurnover()) {
			min2.setTurnover(turn);
		} else if (turn > max2.getTurnover()) {
			max2.setTurnover(turn);
		}

		if (vol < min2.getVolume()) {
			min2.setTurnover(vol);
		} else if (turn > max2.getVolume()) {
			max2.setVol((long) vol + 1);
		}

		if (adj < min2.getAdjprice()) {
			min2.setAdj(adj);
		} else if (adj > max2.getAdjprice()) {
			max2.setAdj(adj);
		}

		if (pe < min2.getPe()) {
			min2.setPe(pe);
		} else if (pe > max2.getPe()) {
			max2.setPe(pe);
		}

		if (pb < min2.getPb()) {
			min2.setPb(pb);
		} else if (pb > max2.getPb()) {
			max2.setPb(pb);
		}
//		try {
//			ser.update(IndustryType.FINANCE.getID(), new MaxMinPO(max2, min2));
//		} catch (ExistName_exception e) {
//			System.out.println("update error");
//			e.printStackTrace();
//		}
		return new ChartVO((turn - min2.getTurnover()) / (max2.getTurnover() - min2.getTurnover()) * 100,
				(vol - min2.getVolume()) / (max2.getVolume() - min2.getVolume()) * 200,
				(adj - min2.getAdjprice()) / (max2.getAdjprice() - min2.getAdjprice()) * 100,
				(pe - min2.getPe()) / (max2.getPe() - min2.getPe()) * 100,
				(pb - min2.getPb()) / (max2.getPb() - min2.getPb()) * 100, IndustryType.FINANCE.getName(),
				IndustryType.FINANCE.getID(), yes);
	}

	public ChartVO Material() {
		MyDate today = new MyDate(Calendar.getInstance());
		MyDate yes = today.beforeDay();
		ArrayList<StockPO> array = new ArrayList<StockPO>();
		for (int i = 2 * Num; i < 3 * Num; ++i) {
			try {
				array.add(data.getDataByID(ids.get(i), new Range_Date(yes, today)).get(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double turn = c.getEX_turnover(array);
		double vol = c.getEX_Volume(array);
		double adj = c.getEX_Adjprice(array);
		double pe = c.getEX_pe(array);
		double pb = c.getEX_pb(array);

		if (turn < min3.getTurnover()) {
			min3.setTurnover(turn);
		} else if (turn > max3.getTurnover()) {
			max3.setTurnover(turn);
		}

		if (vol < min3.getVolume()) {
			min3.setTurnover(vol);
		} else if (turn > max3.getVolume()) {
			max3.setVol((long) vol);
		}

		if (adj < min3.getAdjprice()) {
			min3.setAdj(adj);
		} else if (adj > max3.getAdjprice()) {
			max3.setAdj(adj);
		}

		if (pe < min3.getPe()) {
			min3.setPe(pe);
		} else if (pb > max3.getPe()) {
			max3.setPe(pe);
		}

		if (pb < min3.getPb()) {
			min3.setPb(pb);
		} else if (pb > max3.getPb()) {
			max3.setPb(pb);
		}
//		try {
//			ser.update(IndustryType.MATERIAL.getID(), new MaxMinPO(max3, min3));
//		} catch (ExistName_exception e) {
//			System.out.println("update error");
//			e.printStackTrace();
//		}

		return new ChartVO((turn - min3.getTurnover()) / (max3.getTurnover() - min3.getTurnover()) * 100,
				(vol - min3.getVolume()) / (max3.getVolume() - min3.getVolume()) * 100,
				(adj - min3.getAdjprice()) / (max3.getAdjprice() - min3.getAdjprice()) * 100,
				(pe - min3.getPe()) / (max3.getPe() - min3.getPe()) * 100,
				(pb - min3.getPb()) / (max3.getPb() - min3.getPb()) * 100, IndustryType.MATERIAL.getName(),
				IndustryType.MATERIAL.getID(), yes);
	}

	public ChartVO getDetail(String id) throws IOException, NotFoundName_exception {
		MyDate today = new MyDate(Calendar.getInstance());
		MyDate yes = today.beforeDay();
		ArrayList<StockPO> s = data.getDataByID(id, new Range_Date(yes, today));
		StockPO pre = s.get(0);
		String name = pre.getName();

		MaxMinPO po = ser.getChart(id);
		ChartVO min = po.getMinPO();
		ChartVO max = po.getMaxPO();

		double turn = pre.getTurnover();
		double vol = pre.getVolume();
		double adj = pre.getAdjprice();
		double pe = pre.getPe();
		double pb = pre.getPb();

		if (turn < min.getTurnover()) {
			min.setTurnover(turn);
		} else if (turn > max.getTurnover()) {
			max.setTurnover(turn);
		}

		if (vol < min.getVolume()) {
			min.setTurnover(vol);
		} else if (turn > max.getVolume()) {
			max.setVol((long) vol);
		}

		if (adj < min.getAdjprice()) {
			min.setAdj(adj);
		} else if (adj > max.getAdjprice()) {
			max.setAdj(adj);
		}

		if (pe < min.getPe()) {
			min.setPe(pe);
		} else if (pb > max.getPe()) {
			max.setPe(pe);
		}

		if (pb < min.getPb()) {
			min.setPb(pb);
		} else if (pb > max.getPb()) {
			max.setPb(pb);
		}
//		try {
//			ser.update(id, new MaxMinPO(max, min));
//		} catch (ExistName_exception e) {
//			System.out.println("update error");
//			e.printStackTrace();
//		}
		double t = (turn - min.getTurnover()) / (max.getTurnover() - min.getTurnover()) * 100;
		double v = (vol - min.getVolume()) / (max.getVolume() - min.getVolume()) * 100;
		double a = (adj - min.getAdjprice()) / (max.getAdjprice() - min.getAdjprice()) * 100;
		double e = (pe - min.getPe()) / (max.getPe() - min.getPe()) * 100;
		double b = (pb - min.getPb()) / (max.getPb() - min.getPb()) * 100;
		return new ChartVO(t, v, a, e, b, name, id, yes);
	}

	public ChartVO getMin(ArrayList<StockPO> a) {
		double turnover;
		double vol;
		double adjprice;
		double pe;
		double pb;
		String name = a.get(0).getName();
		String id = a.get(0).getId();
		MyDate date = a.get(0).getDate();

		int len = a.size();
		StockPO s = a.get(0);

		turnover = s.getTurnover();
		vol = s.getVolume();
		adjprice = s.getAdjprice();
		pe = s.getPe();
		pb = s.getPb();

		for (int i = 0; i < len; ++i) {
			if (a.get(i).getTurnover() < turnover) {
				turnover = a.get(i).getTurnover();
			}
			if (a.get(i).getVolume() < vol) {
				vol = a.get(i).getVolume();
			}
			if (a.get(i).getAdjprice() < adjprice) {
				adjprice = a.get(i).getAdjprice();
			}
			if (a.get(i).getPe() < pe) {
				pe = a.get(i).getPe();
			}
			if (a.get(i).getPb() < pb) {
				pb = a.get(i).getPb();
			}
		}

		return new ChartVO(turnover, vol, adjprice, pe, pb, name, id, date);
	}

	public ChartVO getMax(ArrayList<StockPO> a) {
		double turnover;
		double vol;
		double adjprice;
		double pe;
		double pb;
		String name = a.get(0).getName();
		String id = a.get(0).getId();
		MyDate date = a.get(0).getDate();

		int len = a.size();
		StockPO s = a.get(0);

		turnover = s.getTurnover();
		vol = s.getVolume();
		adjprice = s.getAdjprice();
		pe = s.getPe();
		pb = s.getPb();

		for (int i = 0; i < len; ++i) {
			if (a.get(i).getTurnover() > turnover) {
				turnover = a.get(i).getTurnover();
			}
			if (a.get(i).getVolume() > vol) {
				vol = a.get(i).getVolume();
			}
			if (a.get(i).getAdjprice() > adjprice) {
				adjprice = a.get(i).getAdjprice();
			}
			if (a.get(i).getPe() > pe) {
				pe = a.get(i).getPe();
			}
			if (a.get(i).getPb() > pb) {
				pb = a.get(i).getPb();
			}
		}
		return new ChartVO(turnover, vol, adjprice, pe, pb, name, id, date);
	}

}
