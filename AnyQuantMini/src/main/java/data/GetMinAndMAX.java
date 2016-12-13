///**
// * 
// */
//package data;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Comparator;
//
//import po.MaxMinPO;
//import po.StockPO;
//import utility.MyDate;
//import utility.Range_Date;
//import utility.enums.IndustryType;
//import utility.exception.ExistID_exception;
//import utility.exception.ExistName_exception;
//import utility.exception.NotFoundName_exception;
//import vo.ChartVO;
//
///**
// * @author run
// *
// */
//public class GetMinAndMAX {
//	private StockData data;
//	private MaxMinData maxMinData;
//
//	public GetMinAndMAX() {
//		data = new StockData();
//		maxMinData = new MaxMinData();
//	}
//
//	/**
//	 * save the max and min value
//	 * 
//	 * @param array
//	 */
//	public void saveMaxAndMin(ArrayList<StockPO> array) {
//		ChartVO maxVO = null;
//		ChartVO minVO = null;
//		String name = array.get(0).getName();
//		String stockId = array.get(0).getId();
//		double maxTurnover = 0;
//		double maxAdj = 0;
//		double maxVolume = 0;
//		double maxPe = 0;
//		double maxPb = 0;
//
//		double minTurnover = 0;
//		double minAdj = 0;
//		double minVolume = 0;
//		double minPe = 0;
//		double minPb = 0;
//
//		ArrayList<StockPO> tempList = array;
//		Collections.sort(tempList, new Comparator<StockPO>() {
//			@Override
//			public int compare(StockPO o1, StockPO o2) {
//				// TODO Auto-generated method stub
//				return (new Double(o1.getTurnover())).compareTo(new Double(o2.getTurnover()));
//			}
//		});
//		maxTurnover = tempList.get(tempList.size() - 1).getTurnover();
//		minTurnover = tempList.get(0).getTurnover();
//
//		ArrayList<StockPO> tempList1 = array;
//		Collections.sort(tempList1, new Comparator<StockPO>() {
//			@Override
//			public int compare(StockPO o1, StockPO o2) {
//				// TODO Auto-generated method stub
//				return (new Double(o1.getAdjprice())).compareTo(new Double(o2.getAdjprice()));
//			}
//		});
//		maxAdj = tempList1.get(tempList.size() - 1).getAdjprice();
//		minAdj = tempList1.get(0).getAdjprice();
//
//		ArrayList<StockPO> tempList2 = array;
//		Collections.sort(tempList2, new Comparator<StockPO>() {
//			@Override
//			public int compare(StockPO o1, StockPO o2) {
//				// TODO Auto-generated method stub
//				return (new Double(o1.getPe())).compareTo(new Double(o2.getPe()));
//			}
//		});
//		maxPe = tempList2.get(tempList.size() - 1).getPe();
//		minPe = tempList2.get(0).getPe();
//
//		ArrayList<StockPO> tempList3 = array;
//		Collections.sort(tempList3, new Comparator<StockPO>() {
//			@Override
//			public int compare(StockPO o1, StockPO o2) {
//				// TODO Auto-generated method stub
//				return (new Double(o1.getPb())).compareTo(new Double(o2.getPb()));
//			}
//		});
//		maxPb = tempList3.get(tempList.size() - 1).getPb();
//		minPb = tempList3.get(0).getPb();
//
//		ArrayList<StockPO> tempList4 = array;
//		Collections.sort(tempList4, new Comparator<StockPO>() {
//			@Override
//			public int compare(StockPO o1, StockPO o2) {
//				// TODO Auto-generated method stub
//				return (new Double(o1.getVolume())).compareTo(new Double(o2.getVolume()));
//			}
//		});
//		maxVolume = tempList4.get(tempList.size() - 1).getVolume();
//		minVolume = tempList4.get(0).getVolume();
//
//		ArrayList<StockPO> tempList5 = array;
//		Collections.sort(tempList5, new Comparator<StockPO>() {
//			@Override
//			public int compare(StockPO o1, StockPO o2) {
//				// TODO Auto-generated method stub
//				return o1.getDate().compareTo(o2.getDate());
//			}
//		});
//		MyDate maxDate = tempList5.get(0).getDate();
//
//		maxVO = new ChartVO(maxTurnover, maxVolume, maxAdj, maxPe, maxPb, name, stockId, maxDate);
//		minVO = new ChartVO(minTurnover, minVolume, minAdj, minPe, minPb, name, stockId, maxDate);
//		MaxMinPO mmPO = new MaxMinPO(maxVO, minVO);
//		try {
//			maxMinData.add(mmPO);
//		} catch (ExistID_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NotFoundName_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// System.out.println("Save the mmPO:" +mmPO.toString());
//	}
//
//	/**
//	 * @param rangeD
//	 */
//	public void saveStockMaxMin(Range_Date rangeD) {
//		ArrayList<String> idList = data.getLoacalStockIds();
//		for (int i = 0; i < 9; i++) {
//			String tempId = idList.get(i);
//			try {
//				ArrayList<StockPO> pos = data.getDataByID(tempId, rangeD);
//				// System.out.println("SaveStockMaxMin:"+pos.get(0).toString());
//				saveMaxAndMin(pos);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * @param rangeD
//	 */
//	public void saveIndustry() {
//		ArrayList<String> idList = data.getLoacalStockIds();
//		IndustryType[] types = { IndustryType.ESTATE, IndustryType.FINANCE, IndustryType.MATERIAL };
//		for (int i = 0; i < 3; i++) {
//			String tempId1 = idList.get(i * 3 + 0);
//			String tempId2 = idList.get(i * 3 + 1);
//			String tempId3 = idList.get(i * 3 + 2);
//			// System.out.println("ID1:" + tempId1 + "ID2:" + tempId2 + "ID3:" +
//			// tempId3);
//			MaxMinPO mm1 = null;
//			MaxMinPO mm2 = null;
//			MaxMinPO mm3 = null;
//			try {
//				mm1 = maxMinData.getChart(tempId1);
//				mm2 = maxMinData.getChart(tempId2);
//				mm3 = maxMinData.getChart(tempId3);
//			} catch (NotFoundName_exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			double maxTurnover = Math.max(Math.max(mm1.getMaxPO().getTurnover(), mm2.getMaxPO().getTurnover()),
//					mm3.getMaxPO().getTurnover());
//			double maxAdj = Math.max(Math.max(mm1.getMaxPO().getAdjprice(), mm2.getMaxPO().getAdjprice()),
//					mm3.getMaxPO().getAdjprice());
//			double maxVolume = Math.max(Math.max(mm1.getMaxPO().getVolume(), mm2.getMaxPO().getVolume()),
//					mm3.getMaxPO().getVolume());
//			double maxPe = Math.max(Math.max(mm1.getMaxPO().getPe(), mm2.getMaxPO().getPe()), mm3.getMaxPO().getPe());
//			double maxPb = Math.max(Math.max(mm1.getMaxPO().getPb(), mm2.getMaxPO().getPb()), mm3.getMaxPO().getPb());
//
//			double minTurnover = Math.min(Math.min(mm1.getMinPO().getTurnover(), mm2.getMinPO().getTurnover()),
//					mm3.getMinPO().getTurnover());
//			double minAdj = Math.min(Math.min(mm1.getMinPO().getAdjprice(), mm2.getMinPO().getAdjprice()),
//					mm3.getMinPO().getAdjprice());
//			double minVolume = Math.min(Math.min(mm1.getMinPO().getVolume(), mm2.getMinPO().getVolume()),
//					mm3.getMinPO().getVolume());
//			double minPe = Math.min(Math.min(mm1.getMinPO().getPe(), mm2.getMinPO().getPe()), mm3.getMinPO().getPe());
//			double minPb = Math.min(Math.min(mm1.getMinPO().getPb(), mm2.getMinPO().getPb()), mm3.getMinPO().getPb());
//			String name = types[i].getName();
//			String stockId = types[i].getID();
//			MyDate da = mm1.getMaxPO().getDate();
//			ChartVO maxV = new ChartVO(maxTurnover, maxVolume, maxAdj, maxPe, maxPb, name, stockId, da);
//			ChartVO minV = new ChartVO(minTurnover, minVolume, minAdj, minPe, minPb, name, stockId, da);
//			MaxMinPO mmPO = new MaxMinPO(maxV, minV);
//			try {
//				maxMinData.add(mmPO);
//				// System.out.println("savePO:" + mmPO.toString());
//			} catch (ExistID_exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (NotFoundName_exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//	}
//
//	public static void main(String[] args) {
//		GetMinAndMAX getMM = new GetMinAndMAX();
//		MyDate preDate = new MyDate("2016-01-01");
//		MyDate nowDate = (new MyDate(Calendar.getInstance())).beforeDay();
//		Range_Date range_Date = new Range_Date(preDate, nowDate);
//		getMM.saveStockMaxMin(range_Date);
//		getMM.saveIndustry();
//		System.out.println("Execution completed!");
//	}
//}
