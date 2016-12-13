/**
 * 
 */
package ui.large.industry;

import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import bl.compareBL.getBusinessDetail;
import po.StockPO;
import ui.large.GBC;
import ui.large.Large;
import ui.large.SpiderWebdPlotGraph;
import ui.util.MyColor;
import ui.util.MyDialog;
import utility.enums.Info;
import vo.ChartVO;

/**
 * @author bismuth
 *
 */
public class IndustryCompare {

	private JPanel barPanel = new JPanel();
	private JPanel radarPanel = new JPanel();
	private JPanel radarStuffPanel = new JPanel();

	private JLabel barLabel = new JLabel();
	private JLabel radarLabel = new JLabel();

	private ArrayList<StockPO> stockPOs1 = new ArrayList<StockPO>();
	private ArrayList<StockPO> stockPOs2 = new ArrayList<StockPO>();
	private ArrayList<StockPO> stockPOs3 = new ArrayList<StockPO>();
	private ArrayList<ChartVO> chartVOs = new ArrayList<ChartVO>();

	private getBusinessDetail control = new getBusinessDetail();

	public void showIndustryCompare(ArrayList<ChartVO> chartVOs) {

		try {
			stockPOs1 = control.getMonth_One();
			stockPOs2 = control.getMonth_Two();
			stockPOs3 = control.getMonth_Three();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.chartVOs = chartVOs;

		JPanel panel = Industry.industryPanel;

		barPanel.setOpaque(false);
		radarLabel.setOpaque(false);

		panel.add(barLabel, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(1, 1));
		panel.add(radarPanel, new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 1));

		draw();
		setRadar();
		
		Large.info = Info.industryCompare;
	}

	private void draw() {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		final String series1 = "房地产";
		final String series2 = "金融";
		final String series3 = "建筑材料";

		int n1 = stockPOs1.get(0).getDate().getMonth();
		int n2 = stockPOs2.get(0).getDate().getMonth();
		int n3 = stockPOs3.get(0).getDate().getMonth();
		
		final String category1 = n1+"月";
		final String category2 = n2+"月";
		final String category3 = n3+"月";

		dataset.addValue(stockPOs1.get(0).getEndprice(), series1, category1);
		dataset.addValue(stockPOs1.get(1).getEndprice(), series1, category2);
		dataset.addValue(stockPOs1.get(2).getEndprice(), series1, category3);

		dataset.addValue(stockPOs2.get(0).getEndprice(), series2, category1);
		dataset.addValue(stockPOs2.get(1).getEndprice(), series2, category2);
		dataset.addValue(stockPOs2.get(2).getEndprice(), series2, category3);

		dataset.addValue(stockPOs3.get(0).getEndprice(), series3, category1);
		dataset.addValue(stockPOs3.get(1).getEndprice(), series3, category2);
		dataset.addValue(stockPOs3.get(2).getEndprice(), series3, category3);

		final JFreeChart chart = ChartFactory.createBarChart("", // chart title
				"", // domain axis label
				"", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				false, // tooltips?
				false // URLs?
		);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(MyColor.transparentColor);

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
//		renderer.setShadowVisible(false);

		chart.setBackgroundPaint(MyColor.transparentColor);

		FileOutputStream out;
		try {
			out = new FileOutputStream("label/picture.png");
			ChartUtilities.writeChartAsPNG(out, chart, 650, 500);
			barLabel.setIcon(new ImageIcon(ImageIO.read(new File("label/picture.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setRadar() {

		SpiderWebdPlotGraph radar = new SpiderWebdPlotGraph(chartVOs);
		radar.init(350);
		try {
			radarLabel.setIcon(new ImageIcon(ImageIO.read(new File("label/radar.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		radarPanel.setLayout(new GridBagLayout());
		radarPanel.setOpaque(false);
		radarStuffPanel.setOpaque(false);

		radarPanel.add(radarLabel, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		radarPanel.add(radarStuffPanel,
				new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 100).setInsets(5, 5, 5, 5).setWeight(0, 1));

	}
	
	public static void infoShow() {
		new MyDialog("   左侧柱状图显示");
	}

}
