/**
 * 
 */
package ui.large;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import ui.util.CalibrationSpiderWebPlot;
import ui.util.MyColor;
import utility.enums.Attribute;
import vo.ChartVO;

/**
 * @author 1
 *
 */
public class SpiderWebdPlotGraph {
	/**
	 * 
	 */
	private String graphName = "label/radar.png";
	private ArrayList<ChartVO> dataList;

	public SpiderWebdPlotGraph(ArrayList<ChartVO> chartVOs) {
		// TODO Auto-generated constructor stub
		this.dataList = chartVOs;
	}

	public void init(int length) {
		CategoryDataset tmpSet = createDataset();
		JFreeChart freeChart = createChart(tmpSet);
		saveGraph(freeChart, length);
	}

	public JFreeChart createChart(CategoryDataset dataset) {
		CalibrationSpiderWebPlot spiderwebplot = new CalibrationSpiderWebPlot(dataset);
		spiderwebplot.setBackgroundPaint(MyColor.transparentColor);
		spiderwebplot.setToolTipGenerator(new StandardCategoryToolTipGenerator());// 标准分类提示器

		JFreeChart jfreechart = new JFreeChart("", TextTitle.DEFAULT_FONT, spiderwebplot, false);

		jfreechart.setBackgroundPaint(MyColor.transparentColor); // 设置背景颜色
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM); // X说明所在的位置
		jfreechart.addSubtitle(legendtitle);
		return jfreechart;
	}

	private CategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		assert(dataList.size() > 0) : ("The chartVOs size is 0");
		for (Iterator<ChartVO> t = dataList.iterator(); t.hasNext();) {
			ChartVO temp = t.next();
			System.out.println(temp.toString());
			dataset.addValue(temp.getTurnover(), temp.getName(), Attribute.TURNOVER.getValue());
			dataset.addValue(temp.getVolume(), temp.getName(), Attribute.VOLUME.getValue());
			dataset.addValue(temp.getAdjprice(), temp.getName(), Attribute.ADJPRICE.getValue());
			dataset.addValue(temp.getPe(), temp.getName(), Attribute.PE.getValue());
			dataset.addValue(temp.getPb(), temp.getName(), Attribute.PB.getValue());
		}
		return dataset;
	}

	public void saveGraph(JFreeChart chart, int length) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(graphName);
			ChartUtilities.writeChartAsPNG(out, chart, length, length);
			// chartLabel.setIcon(new ImageIcon(ImageIO.read(new
			// File("label/market.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
