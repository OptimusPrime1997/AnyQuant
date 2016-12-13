/**
 * 
 */
package ui.util;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

/**
 * @author bismuth
 *
 */
public class MyRenderer {

	@SuppressWarnings("deprecation")
	public static StandardXYItemRenderer getRenderer(Color color) {
		StandardXYToolTipGenerator xytool = new StandardXYToolTipGenerator();
		StandardXYItemRenderer renderer = new StandardXYItemRenderer();
		renderer.setPaint(color);
		renderer.setToolTipGenerator(xytool);
		renderer.setStroke(new BasicStroke(1.2f));
		return renderer;
	}
}
