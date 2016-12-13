//package helper;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//
//import data.StockDataImp;
//import model.ChartVO;
//import model.MaxMinChart;
//import utility.Constants;
//import utility.MyDate;
//
//public class RadarHelper {
//	private StockDataImp stockDataImp;
//
//	public RadarHelper() {
//		stockDataImp = new StockDataImp();
//	}
//
//	public ArrayList<ChartVO> getRadarChartVOs(MyDate date) {
////		ArrayList<ChartVO> result = new ArrayList<ChartVO>();
////		MaxMinChart maxMinChart = stockDataImp.getMaxMinChart();
//		ArrayList<ChartVO> chartVOs = stockDataImp.getCharVOs(date,Constants.radarSql);
////		for (ChartVO temp : chartVOs) {
////			double turnover = normalize(maxMinChart.getMaxTurnover(), maxMinChart.getMinTurnover(), temp.getTurnover());
////			temp.setTurnover(turnover);
////			double volume = normalize(maxMinChart.getMaxVolume(), maxMinChart.getMinVolume(), temp.getVolume());
////			temp.setVolume(volume);
////			double pe = normalize(maxMinChart.getMaxPe(), maxMinChart.getMinPe(), temp.getPe());
////			temp.setPe(pe);
////			double pb = normalize(maxMinChart.getMaxPb(), maxMinChart.getMinPb(), temp.getPb());
////			temp.setPb(pb);
////			double adjprice = normalize(maxMinChart.getMaxAdjprice(), maxMinChart.getMinAdjprice(), temp.getAdjprice());
////			temp.setAdjprice(adjprice);
////			result.add(temp);
////		}
//		return chartVOs;
//	}
//
//	public double normalize(double maxValue, double minValue, double trueValue) {
//		double temp = ((trueValue - minValue) / (maxValue - minValue)) * 100;
//		double result = (new BigDecimal(temp)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//		return result;
//	}
//}
