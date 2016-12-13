/**
 * 
 */
package ui.util;

import java.awt.Color;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;

/**
 * @author bismuth
 *
 */
public class MyXYPlot {
	
	public static XYPlot getXYPlot(DateAxis x, NumberAxis y){
		XYPlot plot = new XYPlot();
		plot = new XYPlot(null, x, y, null);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinePaint(new Color(0, 0, 0));
		plot.setBackgroundPaint(new Color(0, 0, 0, 0));
		return plot;
	}
	
}
