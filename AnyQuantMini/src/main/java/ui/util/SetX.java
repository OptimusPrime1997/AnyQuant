/**
 * 
 */
package ui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;

import po.StockPO;
import utility.MyDate;

/**
 * @author bismuth
 *
 */
public class SetX {

	@SuppressWarnings("deprecation")
	public static DateAxis setAxis(ArrayList<StockPO> stockPOs) {

		DateAxis xAxis = new DateAxis();

		xAxis.setAutoRange(false);

		MyDate today = stockPOs.get(0).getDate();
		int size = stockPOs.size();
		MyDate lastDay = stockPOs.get(size - 1).getDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			xAxis.setRange(dateFormat.parse(lastDay.toString()), dateFormat.parse(today.toString()));// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
		} catch (Exception e) {
			e.printStackTrace();
		}

		Calendar c1 = today.getCalendar();
		Calendar c2 = lastDay.getCalendar();
		long val = c1.getTimeInMillis() - c2.getTimeInMillis();  
		int n = (int) (val / (1000 * 60 * 60 * 24));  
		int unit = getUnit(n);

		xAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期
		xAxis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
		xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
		xAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
		xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, unit));// 设置时间刻度的间隔，一般以周为单位
		xAxis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));

		return xAxis;
	}

	private static int getUnit(int n) {
		if (n >= 90) {
			return 14;
		}
		if (n >= 150) {
			return 21;
		}
		return 7;
	}
}
