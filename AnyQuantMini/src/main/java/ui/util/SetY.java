/**
 * 
 */
package ui.util;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;

/**
 * @author bismuth
 *
 */
public class SetY {
	
	public static NumberAxis setCandleYAxis(double max, double min) {
		NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRange(false);
		yAxis.setRange(min * 0.95, max * 1.05);
		yAxis.setTickUnit(new NumberTickUnit((max * 1.05 - min * 0.95) / 10));
		return yAxis;
	}
	
	public static NumberAxis setYAxis(double max, double min) {
		NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRange(false);
		yAxis.setRange(min * 0.9, max * 1.1);
		yAxis.setTickUnit(new NumberTickUnit((max * 1.1 - min * 0.9) / 4));
		return yAxis;
	}
}
