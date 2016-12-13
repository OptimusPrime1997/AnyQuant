/**
 * 
 */
package ui.util;

import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

/**
 * @author bismuth
 *
 */
public class PlotValue {

	public static double getMaxValue(TimeSeriesCollection timeSeriesCollection) {
		double max = 0;
		int seriesCount = timeSeriesCollection.getSeriesCount();
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = timeSeriesCollection.getItemCount(i);
			for (int j = 0; j < itemCount; j++) {
				if (max < timeSeriesCollection.getYValue(i, j)) {
					max = timeSeriesCollection.getYValue(i, j);
				}
			}
		}
		return max;
	}

	public static double getMinValue(TimeSeriesCollection timeSeriesCollection) {
		double min = timeSeriesCollection.getYValue(0, 0);
		int seriesCount = timeSeriesCollection.getSeriesCount();
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = timeSeriesCollection.getItemCount(i);
			for (int j = 0; j < itemCount; j++) {
				if (min > timeSeriesCollection.getYValue(i, j)) {
					min = timeSeriesCollection.getYValue(i, j);
				}
			}
		}
		return min;
	}
	
	public static double getMaxValue(OHLCSeriesCollection timeSeriesCollection) {
		double max = 0;
		int seriesCount = timeSeriesCollection.getSeriesCount();
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = timeSeriesCollection.getItemCount(i);
			for (int j = 0; j < itemCount; j++) {
				if (max < timeSeriesCollection.getYValue(i, j)) {
					max = timeSeriesCollection.getYValue(i, j);
				}
			}
		}
		return max;
	}

	public static double getMinValue(OHLCSeriesCollection ohlcSeriesCollection) {
		double min = ohlcSeriesCollection.getYValue(0, 0);
		int seriesCount = ohlcSeriesCollection.getSeriesCount();
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = ohlcSeriesCollection.getItemCount(i);
			for (int j = 0; j < itemCount; j++) {
				if (min > ohlcSeriesCollection.getYValue(i, j)) {
					min = ohlcSeriesCollection.getYValue(i, j);
				}
			}
		}
		return min;
	}
}
