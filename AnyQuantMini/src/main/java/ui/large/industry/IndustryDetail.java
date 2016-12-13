/**
 * 
 */
package ui.large.industry;

import java.awt.GridBagLayout;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.XYDataset;

import bl.compareBL.getBusinessDetail;
import bl.detailBL.DetailBLController;
import po.StockPO;
import ui.large.GBC;
import ui.large.Large;
import ui.large.single.Single;
import ui.large.single.SingleDetail;
import ui.util.ChartLabel;
import ui.util.DateChooserJButton;
import ui.util.MyColor;
import ui.util.MyDataset;
import ui.util.MyDialog;
import ui.util.MyRenderer;
import ui.util.MyXYPlot;
import ui.util.PlotValue;
import ui.util.SetX;
import ui.util.SetY;
import ui.util.UIFactory;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.IndustryType;
import utility.enums.Info;
import utility.exception.TimeOut_exception;

/**
 * @author bismuth
 *
 */
public class IndustryDetail {

	private JPanel topPanel = new JPanel();
	private JPanel chartTablePanel = new JPanel();
	private JPanel chartPanel = new JPanel();
	private JPanel detailPanel = new JPanel();
	private JPanel dateButtonPanel = new JPanel();
	private JPanel detailStuffPanel = new JPanel();
	private JPanel dateButtonLeftStuffPanel = new JPanel();
	private JPanel dateButtonRightStuffPanel = new JPanel();
	private JPanel topRightStuffPanel = new JPanel();
	private JPanel topLeftStuffPanel = new JPanel();
	private JPanel chartTableStuffPanel = new JPanel();

	private JLabel nameLabel = new JLabel();
	private JLabel chartLabel = new JLabel();
	private ChartLabel volButton = new ChartLabel(MyColor.chartShow, MyColor.chartUnShow, true, "VOL");
	private ChartLabel atrButton = new ChartLabel(MyColor.chartShow, MyColor.chartUnShow, false, "ATR");

	private DateChooserJButton daterButton = new DateChooserJButton();

	private DateAxis xAxis = new DateAxis();

	private XYPlot plot1;
	private XYPlot plot2;
	private XYPlot plot3;
	
	private static IndustryType id;

	private ArrayList<StockPO> stockPOs = new ArrayList<StockPO>();

	private DetailBLController control = new DetailBLController(null);

	private void setButton(DateChooserJButton button) {
		daterButton = button;
	}

	protected void showIndustryDetail(IndustryType id) {

		JPanel panel = Industry.industryPanel;
		
		this.id = id;

		Calendar rencent = daterButton.getCalen();
		MyDate highDate = new MyDate(rencent);
		MyDate lowDate = highDate.getLastDate(daterButton.getDuration());
		highDate = highDate.afterDay();
		Range_Date range_Date = new Range_Date(lowDate, highDate);

		try {
			stockPOs = control.getIndustryByID(id, range_Date);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeOut_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		draw();

		topPanel.setOpaque(false);
		chartPanel.setOpaque(false);
		chartTablePanel.setOpaque(false);

		panel.add(topPanel, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(1, 0));
		panel.add(chartPanel, new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 1));
		panel.add(chartTablePanel, new GBC(0, 2).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0));
		panel.add(detailPanel, new GBC(1, 0, 1, 3).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0));

		chartPanel.add(chartLabel);

		setTop();
		setChartTable();
		setDetail();

		Large.info = Info.industryDetail;
	}

	private void setTop() {

		topPanel.setLayout(new GridBagLayout());
		topLeftStuffPanel.setOpaque(false);
		topRightStuffPanel.setOpaque(false);

		topPanel.add(topLeftStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(50, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
		topPanel.add(nameLabel, new GBC(1, 0).setFill(GBC.BOTH).setIpad(100, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(topRightStuffPanel,
				new GBC(3, 0).setFill(GBC.BOTH).setIpad(320, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));

		nameLabel.setText(stockPOs.get(0).getName());

	}

	public void draw() {

		double minK = 0;
		double maxK = 0;
		double minVol = 0;
		double maxVol = 0;
		double minAtr = 0;
		double maxAtr = 0;

		StockPO previous = null;
		if (stockPOs.size() > 0) {
			StockPO tmp = stockPOs.get(stockPOs.size() - 1);

			try {
				previous = control.getIndustryByID(IndustryType.getIndustryById(tmp.getId()),
						new Range_Date(tmp.getDate().beforeDay(), tmp.getDate())).get(0);
			} catch (TimeOut_exception | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("The stockPOs size is 0");
		}

		OHLCSeries series = new OHLCSeries("");// 高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
		TimeSeries seriesVol = new TimeSeries("");
		TimeSeries seriesAtr = new TimeSeries("");

		for (int i = 0; i < stockPOs.size(); i++) {
			StockPO stockPO = stockPOs.get(i);
			Day day = stockPO.getDate().getChartDay();
			series.add(day, stockPO.getStartprice(), stockPO.getMaxprice(), stockPO.getMinprice(),
					stockPO.getEndprice());
			seriesVol.add(day, stockPO.getVolume());
			if (i != (stockPOs.size() - 1)) {
				if (i == 0) {
					assert(previous != null) : ("The previous stockpo is null!");
					seriesAtr.add(day, Math.max(previous.getEndprice(), stockPO.getMaxprice())
							- Math.min(previous.getMinprice(), stockPO.getEndprice()));
				}
				if (i != (stockPOs.size() - 1)) {
					seriesAtr.addOrUpdate(stockPO.getDate().afterDay().getChartDay(),
							Math.max(stockPOs.get(i).getEndprice(), stockPOs.get(i + 1).getMaxprice())
									- Math.min(stockPOs.get(i).getMinprice(), stockPOs.get(i + 1).getEndprice()));
				}
			}
		}

		final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
		TimeSeriesCollection timeSeriesCollectionVol = new TimeSeriesCollection();
		TimeSeriesCollection timeSeriesCollectionAtr = new TimeSeriesCollection();

		seriesCollection.addSeries(series);
		timeSeriesCollectionVol.addSeries(seriesVol);
		timeSeriesCollectionAtr.addSeries(seriesAtr);

		maxK = PlotValue.getMaxValue(seriesCollection);
		minK = PlotValue.getMinValue(seriesCollection);
		maxVol = PlotValue.getMaxValue(timeSeriesCollectionVol);
		minVol = PlotValue.getMinValue(timeSeriesCollectionVol);
		maxAtr = PlotValue.getMaxValue(timeSeriesCollectionAtr);
		minAtr = PlotValue.getMinValue(timeSeriesCollectionAtr);

		// 1
		xAxis = SetX.setAxis(stockPOs);
		NumberAxis y1Axis = SetY.setCandleYAxis(maxK, minK);

		final CandlestickRenderer candlestickRender = new CandlestickRenderer();
		candlestickRender.setUseOutlinePaint(true);
		candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// 设置如何对K线图的宽度进行设定
		candlestickRender.setAutoWidthGap(0.001);// 设置各个K线图之间的间隔
		candlestickRender.setUpPaint(MyColor.redLine);
		candlestickRender.setDownPaint(MyColor.greenLine);

		plot1 = MyXYPlot.getXYPlot(xAxis, y1Axis);
		plot1.setDataset(0, seriesCollection);
		plot1.setRenderer(0, candlestickRender);

		XYDataset dataset1a = MyDataset.getDataset("-MA 5", seriesCollection, 5);
		StandardXYItemRenderer renderer1a = MyRenderer.getRenderer(MyColor.yellowLine);
		plot1.setDataset(1, dataset1a);
		plot1.setRenderer(1, renderer1a);

		XYDataset dataset1b = MyDataset.getDataset("-MA 10", seriesCollection, 10);
		StandardXYItemRenderer renderer1b = MyRenderer.getRenderer(MyColor.purpleLine);
		plot1.setDataset(2, dataset1b);
		plot1.setRenderer(2, renderer1b);

		XYDataset dataset1c = MyDataset.getDataset("-MA 20", seriesCollection, 20);
		StandardXYItemRenderer renderer1c = MyRenderer.getRenderer(MyColor.greenLine);
		plot1.setDataset(3, dataset1c);
		plot1.setRenderer(3, renderer1c);

		XYDataset dataset1d = MyDataset.getDataset("-MA 30", seriesCollection, 30);
		StandardXYItemRenderer renderer1d = MyRenderer.getRenderer(MyColor.whiteLine);
		plot1.setDataset(4, dataset1d);
		plot1.setRenderer(4, renderer1d);

		// 2
		NumberAxis y2Axis = SetY.setYAxis(maxVol, minVol);

		@SuppressWarnings("serial")
		XYBarRenderer xyBarRender1 = new XYBarRenderer() {
			public Paint getItemPaint(int i, int j) {
				if (seriesCollection.getCloseValue(i, j) > seriesCollection.getOpenValue(i, j)) {
					return MyColor.redLine;
				} else {
					return MyColor.greenLine;
				}
			}
		};
		xyBarRender1.setMargin(0.1);// 设置柱形图之间的间隔
		xyBarRender1.setShadowVisible(false);
		xyBarRender1.setBarPainter(new StandardXYBarPainter());

		plot2 = MyXYPlot.getXYPlot(null, y2Axis);
		plot2.setDataset(0, timeSeriesCollectionVol);
		plot2.setRenderer(0, xyBarRender1);

		XYDataset dataset2a = MyDataset.getDataset("-VOL 5", timeSeriesCollectionVol, 5);
		StandardXYItemRenderer renderer2a = MyRenderer.getRenderer(MyColor.yellowLine);
		plot2.setDataset(1, dataset2a);
		plot2.setRenderer(1, renderer2a);

		XYDataset dataset2b = MyDataset.getDataset("-VOL 10", timeSeriesCollectionVol, 10);
		StandardXYItemRenderer renderer2b = MyRenderer.getRenderer(MyColor.purpleLine);
		plot2.setDataset(2, dataset2b);
		plot2.setRenderer(2, renderer2b);

		XYDataset dataset2c = MyDataset.getDataset("-VOL 20", timeSeriesCollectionVol, 20);
		StandardXYItemRenderer renderer2c = MyRenderer.getRenderer(MyColor.greenLine);
		plot2.setDataset(3, dataset2c);
		plot2.setRenderer(3, renderer2c);

		// 3
		NumberAxis y4Axis = SetY.setYAxis(maxAtr, minAtr);

		plot3 = MyXYPlot.getXYPlot(null, y4Axis);

		XYDataset dataset3 = MyDataset.getDataset("MATR 7", timeSeriesCollectionAtr, 7);
		StandardXYItemRenderer renderer3 = MyRenderer.getRenderer(MyColor.yellowLine);

		plot3.setDataset(0, dataset3);
		plot3.setRenderer(0, renderer3);

		drawTwo(true, false);

	}

	private void drawTwo(boolean vol, boolean atr) {

		CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(xAxis);
		combineddomainxyplot.add(plot1, 2);
		if (vol) {
			combineddomainxyplot.add(plot2, 1);
		}
		if (atr) {
			combineddomainxyplot.add(plot3, 1);
		}
		combineddomainxyplot.setGap(10);
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
		chart.setAntiAlias(true);
		chart.setBackgroundPaint(MyColor.transparentColor);
		chart.setNotify(false);

		FileOutputStream out;
		try {
			out = new FileOutputStream("label/picture.png");
			ChartUtilities.writeChartAsPNG(out, chart, 750, 450);
			chartLabel.setIcon(new ImageIcon(ImageIO.read(new File("label/picture.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setChartTable() {

		chartTablePanel.setLayout(new GridBagLayout());
		chartTableStuffPanel.setOpaque(false);

		chartTablePanel.add(volButton, new GBC(1, 0).setFill(GBC.BOTH).setIpad(20, 0).setWeight(0, 0));
		chartTablePanel.add(atrButton, new GBC(2, 0).setFill(GBC.BOTH).setIpad(20, 0).setWeight(0, 0));
		chartTablePanel.add(chartTableStuffPanel, new GBC(3, 0).setFill(GBC.BOTH).setIpad(300, 0).setWeight(1, 0));

		volButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				volButtonMouseClicked(evt);
			}
		});

		atrButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				atrButtonMouseClicked(evt);
			}
		});

	}

	private void setDetail() {

		detailPanel.setOpaque(false);

		detailPanel.setLayout(new GridBagLayout());

		dateButtonPanel.setOpaque(false);
		dateButtonLeftStuffPanel.setOpaque(false);
		dateButtonRightStuffPanel.setOpaque(false);
		detailStuffPanel.setOpaque(false);

		dateButtonPanel.setLayout(new GridBagLayout());

		dateButtonPanel.add(dateButtonLeftStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		dateButtonPanel.add(daterButton,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		dateButtonPanel.add(dateButtonRightStuffPanel,
				new GBC(2, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));

		detailPanel.add(dateButtonPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		detailPanel.add(detailStuffPanel,
				new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 1));

	}
	
	public static void updateChartPanel(DateChooserJButton button) {
		IndustryDetail detail = UIFactory.getInstance().getIndustryDetail();
		detail.setButton(button);
		Industry.industryPanel.removeAll();
		Industry.industryPanel.repaint();
		detail.showIndustryDetail(id);
	}

	public static void infoShow() {
		new MyDialog("    该图显示了这个行业的详细信息。K线图的5天，10天，20天，30天移动平均线的名字分别为黄色，紫色，绿色，白色。" + "\n"
				+ "    下方的三个按钮分别表示成交量，KDJ指标和平均真实波幅。" + "\n" + "    左键按钮可以显示/隐藏图线，右键按钮可以显示该按钮所展示图线的详细信息。" + "\n"
				+ "    右侧界面显示某天的详细信息，右侧上方的按钮可以选择一个日期和一个时间段n，选择后图线就显示从所选日期往前n天的图线。时间段可选择范围是50~100天。");
	}

	private void volButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			boolean atr = false;
			if (atrButton.ifShowing()) {
				atr = true;
			}
			drawTwo(volButton.ifShowing(), atr);
		}

		else if (evt.getButton() == MouseEvent.BUTTON3) {
			new MyDialog("该图显示了每日的成交量，收盘价高于开盘价用红色柱表示，否则用绿色。" + "黄线为5日成交量平均线，紫线为10日均线，绿线为20日均线。");
		}

	}

	private void atrButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			boolean vol = false;
			if (volButton.ifShowing()) {
				vol = true;
			}
			drawTwo(vol, atrButton.ifShowing());
		}

		else if (evt.getButton() == MouseEvent.BUTTON3) {
			new MyDialog("该图显示了7日平均真实波幅。");
		}
	}

}
