/**
 * 
 */
package ui.util;

import org.jfree.data.time.MovingAverage;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * @author bismuth
 *
 */
public class MyDataset {

	public static XYDataset getCompareDataset(String name, TimeSeriesCollection timeSeriesCollectionArray, int num) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
		timeSeriesCollection.addSeries(timeSeriesCollectionArray.getSeries(num));
		XYDataset dataset = MovingAverage.createMovingAverage(timeSeriesCollection, name, 5 * 24 * 60 * 60 * 1000L, 0L);
		return dataset;
	}
	
	public static XYDataset getMarketDataset(String name, TimeSeries timeSeries) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
		timeSeriesCollection.addSeries(timeSeries);
		XYDataset dataset = MovingAverage.createMovingAverage(timeSeriesCollection, name, 5 * 24 * 60 * 60 * 1000L, 0L);
		return dataset;
	}

	public static XYDataset getDataset(String name, TimeSeriesCollection timeSeriesCollection, int num) {
		XYDataset dataset = MovingAverage.createMovingAverage(timeSeriesCollection, name, num * 24 * 60 * 60 * 1000L,
				0L);
		return dataset;
	}

	public static XYDataset getDataset(String name, OHLCSeriesCollection ohlcSeriesCollection, int num) {
		XYDataset dataset = MovingAverage.createMovingAverage(ohlcSeriesCollection, name, num * 24 * 60 * 60 * 1000L,
				0L);
		return dataset;
	}
}
