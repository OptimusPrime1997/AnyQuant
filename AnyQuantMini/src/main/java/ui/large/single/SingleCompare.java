/**
 * 
 */
package ui.large.single;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import bl.compareBL.CompareHelper;
import bl.compareBL.GetRangeArray;
import bl.detailBL.DetailBLController;
import blservice.DetailBLService;
import po.StockPO;
import ui.util.MyColor;
import ui.util.MyDataset;
import ui.util.MyDialog;
import ui.large.GBC;
import ui.large.Large;
import ui.util.ChartLabel;
import ui.util.DateChooserJButton;
import ui.util.MsgDialogue;
import ui.util.MyRenderer;
import ui.util.MyXYPlot;
import ui.util.PlotValue;
import ui.util.SetX;
import ui.util.SetY;
import ui.util.UIFactory;
import utility.Constants;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.Info;
import utility.exception.TimeOut_exception;

/**
 * @author bismuth
 *
 */
public class SingleCompare {

	private JPanel chartPanel = new JPanel();
	private JPanel chartTablePanel = new JPanel();
	private JPanel detailPanel = new JPanel();
	private JPanel categoryPanel = new JPanel();
	private JPanel dateButtonPanel = new JPanel();
	private JPanel dateButtonLeftStuffPanel = new JPanel();
	private JPanel dateButtonRightStuffPanel = new JPanel();
	private JPanel categoryLeftStuffPanel = new JPanel();
	private JPanel categoryRightStuffPanel = new JPanel();
	private JPanel chartStuffPanel = new JPanel();
	private JPanel detailUpStuffPanel = new JPanel();
	private JPanel detailCenterStuffPanel = new JPanel();
	private JPanel detailDownStuffPanel = new JPanel();

	private JLabel chartLabel = new JLabel();
	private JLabel name0Label = new JLabel();
	private JLabel name1Label = new JLabel();
	private JLabel name2Label = new JLabel();
	private JLabel name3Label = new JLabel();
	private JLabel name4Label = new JLabel();
	private JLabel name5Label = new JLabel();
	private JLabel color0Label = new JLabel();
	private JLabel color1Label = new JLabel();
	private JLabel color2Label = new JLabel();
	private JLabel color3Label = new JLabel();
	private JLabel color4Label = new JLabel();
	private JLabel color5Label = new JLabel();
	private ChartLabel rangeButton = new ChartLabel(MyColor.chartShow, MyColor.chartUnShow, true, "AOI");
	private ChartLabel turnOverButton = new ChartLabel(MyColor.chartShow, MyColor.chartUnShow, true, "TR");
	private ChartLabel peButton = new ChartLabel(MyColor.chartShow, MyColor.chartUnShow, false, "PE");
	private ChartLabel pbButton = new ChartLabel(MyColor.chartShow, MyColor.chartUnShow, false, "PB");

	private DateChooserJButton daterButton = new DateChooserJButton();

	private JTextArea detailTextArea = new JTextArea();

	private XYPlot plot1;
	private XYPlot plot2;
	private XYPlot plot3;
	private XYPlot plot4;

	private TimeSeriesCollection timeSeriesCollection1 = new TimeSeriesCollection();
	private TimeSeriesCollection timeSeriesCollection2 = new TimeSeriesCollection();
	private TimeSeriesCollection timeSeriesCollection3 = new TimeSeriesCollection();
	private TimeSeriesCollection timeSeriesCollection4 = new TimeSeriesCollection();

	private DateAxis xAxis = new DateAxis();

	private DetailBLService control = new DetailBLController(null);
	private CompareHelper helper = new CompareHelper();

	private ArrayList<StockPO> marketStockPOs;
	private ArrayList<StockPO> stockPOs1;
	private ArrayList<StockPO> stockPOs2;
	private ArrayList<StockPO> stockPOs3;
	private ArrayList<StockPO> stockPOs4;
	private ArrayList<StockPO> stockPOs5;
	private ArrayList<ArrayList<StockPO>> stockPOarrays;

	private static ArrayList<String> ids = new ArrayList<String>();

	public void setButton(DateChooserJButton button) {
		daterButton = button;
	}

	@SuppressWarnings("static-access")
	protected void showStockCompare(ArrayList<String> ids) {
		
		JPanel panel = Single.singlePanel;

		this.ids = ids;

		Calendar rencent = daterButton.getCalen();
		MyDate highDate = new MyDate(rencent);
		MyDate lowDate = highDate.getLastDate(daterButton.getDuration());
		highDate = highDate.afterDay();
		Range_Date range_Date = new Range_Date(lowDate, highDate);

		stockPOarrays = new ArrayList<ArrayList<StockPO>>();
		try {
			marketStockPOs = control.getData(Constants.HS300, null, range_Date);
			stockPOs1 = control.getData(ids.get(0), null, range_Date);
			stockPOarrays.add(stockPOs1);
			stockPOs2 = control.getData(ids.get(1), null, range_Date);
			stockPOarrays.add(stockPOs2);
			if (ids.size() >= 3) {
				stockPOs3 = control.getData(ids.get(2), null, range_Date);
				stockPOarrays.add(stockPOs3);
			}
			if (ids.size() >= 4) {
				stockPOs4 = control.getData(ids.get(3), null, range_Date);
				stockPOarrays.add(stockPOs4);
			}
			if (ids.size() >= 5) {
				stockPOs5 = control.getData(ids.get(4), null, range_Date);
				stockPOarrays.add(stockPOs5);
			}
		} catch (TimeOut_exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MsgDialogue msg3 = new MsgDialogue(e1.getMessage(), Large.point);
			msg3.disappear(7, msg3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MsgDialogue msg6 = new MsgDialogue("网络连接异常", Large.point);
			msg6.disappear(7, msg6);
		}

		draw();

		chartPanel.setOpaque(false);
		categoryPanel.setOpaque(false);
		chartTablePanel.setOpaque(false);
		detailPanel.setOpaque(false);
		// chartPanel.setBackground(Color.yellow);
		// categoryPanel.setBackground(Color.GREEN);
		// chartTablePanel.setBackground(Color.white);
		// detailPanel.setBackground(Color.lightGray);

		panel.add(chartPanel, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(1, 1));
		panel.add(categoryPanel, new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 20).setWeight(1, 0));
		panel.add(chartTablePanel, new GBC(0, 2).setFill(GBC.BOTH).setIpad(0, 25).setWeight(1, 0));
		panel.add(detailPanel, new GBC(1, 0, 1, 3).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 1));

		chartPanel.add(chartLabel);

		setCateGory();
		setChartTable();
		setDetail();

		Large.info = Info.singleCompare;
	}

	private void draw() {

		int size = stockPOarrays.size();

		double minRange = 0;
		double maxRange = 0;
		double minTurnOver = 0;
		double maxTurnOver = 0;
		double minPe = 0;
		double maxPe = 0;
		double minPb = 0;
		double maxPb = 0;

		ArrayList<StockPO> previous = new ArrayList<StockPO>();
		for (int i = 0; i < size; i++) {
			StockPO tmp = stockPOarrays.get(i).get(0);
			StockPO stock;
			try {
				stock = control.getData(tmp.getId(), null, new Range_Date(tmp.getDate().beforeDay(), tmp.getDate()))
						.get(0);
				previous.add(stock);
			} catch (TimeOut_exception | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 把大盘数据加到timeSeriesCollection中,方便计算max和min
		double marketRange[] = new double[size];
		try {
			marketRange = GetRangeArray.getRangeArray(marketStockPOs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TimeSeries marketSeries = new TimeSeries("");
		for (int i = 0; i < marketStockPOs.size(); i++) {
			StockPO stockPO = marketStockPOs.get(i);
			Day today = stockPO.getDate().getChartDay();
			marketSeries.add(today, marketRange[i]);
		}
		timeSeriesCollection1.addSeries(marketSeries);

		for (int j = 0; j < size; j++) {

			ArrayList<StockPO> stockPOs = stockPOarrays.get(j);

			TimeSeries series1 = new TimeSeries("");
			TimeSeries series2 = new TimeSeries("");
			TimeSeries series3 = new TimeSeries("");
			TimeSeries series4 = new TimeSeries("");

			double range[] = new double[size];
			try {
				range = GetRangeArray.getRangeArray(stockPOs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < stockPOs.size(); i++) {

				StockPO stockPO = stockPOs.get(i);
				Day today = stockPO.getDate().getChartDay();

				series1.add(today, range[i]);
				series2.add(today, stockPO.getTurnover());
				series3.add(today, stockPO.getPe());
				series4.add(today, stockPO.getPb());
			}

			timeSeriesCollection1.addSeries(series1);
			timeSeriesCollection2.addSeries(series2);
			timeSeriesCollection3.addSeries(series3);
			timeSeriesCollection4.addSeries(series4);
		}

		maxRange = PlotValue.getMaxValue(timeSeriesCollection1);
		minRange = PlotValue.getMinValue(timeSeriesCollection1);
		maxTurnOver = PlotValue.getMaxValue(timeSeriesCollection2);
		minTurnOver = PlotValue.getMinValue(timeSeriesCollection2);
		maxPe = PlotValue.getMaxValue(timeSeriesCollection3);
		minPe = PlotValue.getMinValue(timeSeriesCollection3);
		maxPb = PlotValue.getMaxValue(timeSeriesCollection4);
		minPb = PlotValue.getMinValue(timeSeriesCollection4);

		xAxis = SetX.setAxis(stockPOarrays.get(0));

		NumberAxis y1Axis = SetY.setYAxis(maxRange, minRange);
		NumberAxis y2Axis = SetY.setYAxis(maxTurnOver, minTurnOver);
		NumberAxis y3Axis = SetY.setYAxis(maxPe, minPe);
		NumberAxis y4Axis = SetY.setYAxis(maxPb, minPb);

		plot1 = MyXYPlot.getXYPlot(xAxis, y1Axis);
		plot2 = MyXYPlot.getXYPlot(xAxis, y2Axis);
		plot3 = MyXYPlot.getXYPlot(xAxis, y3Axis);
		plot4 = MyXYPlot.getXYPlot(xAxis, y4Axis);

		setPlot(0, MyColor.yellowLine);
		setPlot(1, MyColor.purpleLine);

		if (size >= 3) {
			setPlot(2, MyColor.greenLine);
		}
		if (size >= 4) {
			setPlot(3, MyColor.whiteLine);
		}
		if (size >= 5) {
			setPlot(4, MyColor.redLine);
		}

		XYDataset dataset1 = MyDataset.getMarketDataset("marketK", marketSeries);
		plot1.setDataset(0, dataset1);
		StandardXYItemRenderer renderer = MyRenderer.getRenderer(MyColor.blueLine);
		plot1.setRenderer(0, renderer);

		drawThree(true, true, false, false);

	}

	private void setPlot(int i, Color color) {
		XYDataset dataset1 = MyDataset.getCompareDataset("k" + i, timeSeriesCollection1, i + 1);
		XYDataset dataset2 = MyDataset.getCompareDataset("vol" + i, timeSeriesCollection2, i);
		XYDataset dataset3 = MyDataset.getCompareDataset("money" + i, timeSeriesCollection3, i);
		XYDataset dataset4 = MyDataset.getCompareDataset("atr" + i, timeSeriesCollection4, i);
		plot1.setDataset(i + 1, dataset1);
		plot2.setDataset(i, dataset2);
		plot3.setDataset(i, dataset3);
		plot4.setDataset(i, dataset4);
		StandardXYItemRenderer renderer = MyRenderer.getRenderer(color);
		plot1.setRenderer(i + 1, renderer);
		plot2.setRenderer(i, renderer);
		plot3.setRenderer(i, renderer);
		plot4.setRenderer(i, renderer);
	}

	private void drawThree(Boolean range, boolean turnOver, Boolean pe, boolean pb) {

		CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(xAxis);
		if (range) {
			combineddomainxyplot.add(plot1, 1);
		}
		if (turnOver) {
			combineddomainxyplot.add(plot2, 1);
		}
		if (pe) {
			combineddomainxyplot.add(plot3, 1);
		}
		if (pb) {
			combineddomainxyplot.add(plot4, 1);
		}
		if (!range && !turnOver && !pe && !pb) {
			combineddomainxyplot.add(plot1, 1);
		}
		combineddomainxyplot.setGap(10);
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
		chart.setAntiAlias(true);
		chart.setBackgroundPaint(MyColor.transparentColor);
		chart.setNotify(false);

		FileOutputStream out;
		try {
			out = new FileOutputStream("label/picture.png");
			ChartUtilities.writeChartAsPNG(out, chart, 750, 470);
			chartLabel.setIcon(new ImageIcon(ImageIO.read(new File("label/picture.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setCateGory() {

		int size = stockPOarrays.size();

		name0Label.setOpaque(true);
		color0Label.setOpaque(true);
		name0Label.setBackground(MyColor.transparentColor);
		name0Label.setText("大盘指数");
		color0Label.setBackground(MyColor.blueLine);

		setCategoryItem(name1Label, color1Label, stockPOs1, MyColor.yellowLine);
		setCategoryItem(name2Label, color2Label, stockPOs2, MyColor.purpleLine);

		if (size >= 3) {
			setCategoryItem(name3Label, color3Label, stockPOs3, MyColor.greenLine);
		}
		if (size >= 4) {
			setCategoryItem(name4Label, color4Label, stockPOs4, MyColor.whiteLine);
		}
		if (size >= 5) {
			setCategoryItem(name5Label, color5Label, stockPOs5, MyColor.redLine);
		}

		categoryPanel.setLayout(new GridBagLayout());
		categoryLeftStuffPanel.setOpaque(false);
		categoryRightStuffPanel.setOpaque(false);

		categoryPanel.add(categoryLeftStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(1, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(name0Label,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(color0Label,
				new GBC(2, 0).setFill(GBC.BOTH).setIpad(20, 10).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(name1Label,
				new GBC(3, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(color1Label,
				new GBC(4, 0).setFill(GBC.BOTH).setIpad(20, 10).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(name2Label,
				new GBC(5, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(color2Label,
				new GBC(6, 0).setFill(GBC.BOTH).setIpad(20, 10).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(name3Label,
				new GBC(7, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(color3Label,
				new GBC(8, 0).setFill(GBC.BOTH).setIpad(20, 10).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(name4Label,
				new GBC(9, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(color4Label,
				new GBC(10, 0).setFill(GBC.BOTH).setIpad(20, 10).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(name5Label,
				new GBC(11, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(color5Label,
				new GBC(12, 0).setFill(GBC.BOTH).setIpad(20, 10).setWeight(0, 0).setInsets(5, 5, 5, 5));
		categoryPanel.add(categoryRightStuffPanel,
				new GBC(13, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));

	}

	private void setCategoryItem(JLabel nameLabel, JLabel colorLabel, ArrayList<StockPO> stockPOs, Color color) {
		nameLabel.setOpaque(true);
		colorLabel.setOpaque(true);
		nameLabel.setBackground(MyColor.transparentColor);
		nameLabel.setText(stockPOs.get(0).getName());
		colorLabel.setBackground(color);
	}

	private void setChartTable() {

		chartTablePanel.setLayout(new GridBagLayout());
		chartStuffPanel.setOpaque(false);

		chartTablePanel.add(rangeButton, new GBC(0, 0).setFill(GBC.BOTH).setIpad(20, 0).setWeight(0, 0));
		chartTablePanel.add(turnOverButton, new GBC(1, 0).setFill(GBC.BOTH).setIpad(20, 0).setWeight(0, 0));
		chartTablePanel.add(peButton, new GBC(2, 0).setFill(GBC.BOTH).setIpad(20, 0).setWeight(0, 0));
		chartTablePanel.add(pbButton, new GBC(3, 0).setFill(GBC.BOTH).setIpad(20, 0).setWeight(0, 0));
		chartTablePanel.add(chartStuffPanel, new GBC(4, 0).setFill(GBC.BOTH).setIpad(300, 0).setWeight(1, 0));

		rangeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				rangeButtonMouseClicked(evt);
			}
		});

		turnOverButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				turnOverButtonMouseClicked(evt);
			}
		});

		peButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				peButtonMouseClicked(evt);
			}
		});

		pbButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				pbButtonMouseClicked(evt);
			}
		});

	}

	private void rangeButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			boolean turnOver = false;
			boolean pe = false;
			boolean pb = false;
			if (turnOverButton.ifShowing()) {
				turnOver = true;
			}
			if (peButton.ifShowing()) {
				pe = true;
			}
			if (pbButton.ifShowing()) {
				pb = true;
			}

			drawThree(rangeButton.ifShowing(), turnOver, pe, pb);
		}

		else if (evt.getButton() == MouseEvent.BUTTON3) {
			new MyDialog("该图显示了每日涨幅。");
		}
	}

	private void turnOverButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			boolean range = false;
			boolean pe = false;
			boolean pb = false;
			if (rangeButton.ifShowing()) {
				range = true;
			}
			if (peButton.ifShowing()) {
				pe = true;
			}
			if (pbButton.ifShowing()) {
				pb = true;
			}

			drawThree(range, turnOverButton.ifShowing(), pe, pb);
		}

		else if (evt.getButton() == MouseEvent.BUTTON3) {
			new MyDialog("该图显示了每日换手率。");
		}
	}

	private void peButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			boolean range = false;
			boolean turnOver = false;
			boolean pb = false;
			if (rangeButton.ifShowing()) {
				range = true;
			}
			if (turnOverButton.ifShowing()) {
				turnOver = true;
			}
			if (pbButton.ifShowing()) {
				pb = true;
			}

			drawThree(range, turnOver, peButton.ifShowing(), pb);
		}

		else if (evt.getButton() == MouseEvent.BUTTON3) {
			new MyDialog("该图显示了每日的市盈率。");
		}
	}

	private void pbButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			boolean range = false;
			boolean turnOver = false;
			boolean pe = false;
			if (rangeButton.ifShowing()) {
				range = true;
			}
			if (turnOverButton.ifShowing()) {
				turnOver = true;
			}
			if (peButton.ifShowing()) {
				pe = true;
			}

			drawThree(range, turnOver, pe, pbButton.ifShowing());
		}

		else if (evt.getButton() == MouseEvent.BUTTON3) {
			new MyDialog("该图显示了每日市净率。");
		}
	}

	public static void infoShow() {
		new MyDialog("    该图显示了两只股票在四个方面的比较信息，下方四个按钮分别表示涨幅，换手率，市盈率，市净率。" + "\n"
				+ "    涨幅的比较是所选股票和大盘一起做比较，其余三个比较是这所选股票做比较。" + "\n" + "    左键按钮可以显示/隐藏图线，右键按钮可以显示该按钮所展示图线的详细信息。" + "\n"
				+ "    若选择隐藏全部图线，默认显示涨幅的比较。" + "\n" + "    右侧界面显示比较的详细信息。");
	}

	private void setDetail() {
		try {
			detailTextArea.setText(helper.getCompareInfo(stockPOarrays));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detailTextArea.setOpaque(false);
		detailTextArea.setEditable(false);
		detailUpStuffPanel.setOpaque(false);
		detailCenterStuffPanel.setOpaque(false);
		detailDownStuffPanel.setOpaque(false);
		dateButtonPanel.setOpaque(false);
		dateButtonLeftStuffPanel.setOpaque(false);
		dateButtonRightStuffPanel.setOpaque(false);

		detailPanel.setLayout(new GridBagLayout());
		dateButtonPanel.setLayout(new GridBagLayout());

		dateButtonPanel.add(dateButtonLeftStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
		dateButtonPanel.add(daterButton,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		dateButtonPanel.add(dateButtonRightStuffPanel,
				new GBC(2, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));

		detailPanel.setBorder(BorderFactory.createBevelBorder(0, MyColor.leftBorder, MyColor.rightBorder));

		detailPanel.add(detailUpStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 10).setWeight(0, 1));
		detailPanel.add(dateButtonPanel,
				new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		detailPanel.add(detailCenterStuffPanel,
				new GBC(0, 2).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 1));
		detailPanel.add(detailTextArea,
				new GBC(0, 3).setFill(GBC.BOTH).setIpad(0, 0).setInsets(0, 5, 5, 5).setWeight(0, 0));
		detailPanel.add(detailDownStuffPanel,
				new GBC(0, 4).setFill(GBC.BOTH).setIpad(0, 300).setInsets(5, 5, 5, 5).setWeight(0, 1));

	}

	public static void updateChartPanel(DateChooserJButton button) {
		SingleCompare compare = UIFactory.getInstance().getSingleCompare();
		compare.setButton(button);
		Large.changePanel.removeAll();
		Large.changePanel.repaint();
		compare.showStockCompare(ids);
	}
}
