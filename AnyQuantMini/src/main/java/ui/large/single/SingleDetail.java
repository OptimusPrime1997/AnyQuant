package ui.large.single;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;

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

import bl.detailBL.DetailBLController;
import bl.detailBL.Helper;
import blservice.DetailBLService;
import blservice.HelperService;
import po.StockPO;
import ui.large.GBC;
import ui.large.Large;
import ui.large.SpiderWebdPlotGraph;
import ui.small.LoginPanel;
import ui.util.MyColor;
import ui.util.MyDataset;
import ui.util.DateChooserJButton;
import ui.util.MsgDialogue;
import ui.util.MyDialog;
import ui.util.MyRenderer;
import ui.util.MyXYPlot;
import ui.util.ChartLabel;
import ui.util.PaintButton;
import ui.util.PlainButton;
import ui.util.PlotValue;
import ui.util.SetX;
import ui.util.SetY;
import ui.util.UIConstant;
import ui.util.UIFactory;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.Info;
import utility.exception.ExistID_exception;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;
import vo.ChartVO;
import vo.KDJVO;

public class SingleDetail {

	private JPanel topPanel = new JPanel();
	private JPanel detailPanel = new JPanel();
	private JPanel chartTablePanel = new JPanel();
	private JPanel chartPanel = new JPanel();
	private JPanel smallDetailPanel = new JPanel();
	private JPanel dateButtonPanel = new JPanel();
	private JPanel dateButtonLeftStuffPanel = new JPanel();
	private JPanel dateButtonRightStuffPanel = new JPanel();
	private JPanel topRightStuffPanel = new JPanel();
	private JPanel topLeftStuffPanel = new JPanel();
	private JPanel detailUpStuffPanel = new JPanel();
	private JPanel detailDownStuffPanel = new JPanel();
	private JPanel chartTableStuffPanel = new JPanel();

	private JLabel nameLabel = new JLabel();
	private JLabel IDLabel = new JLabel();
	private JLabel openLabel = new JLabel("开盘价");
	private JLabel openerLabel = new JLabel("2");
	private JLabel closeLabel = new JLabel("收盘价");
	private JLabel closerLabel = new JLabel("3");
	private JLabel maxLabel = new JLabel("最高价");
	private JLabel maxerLabel = new JLabel("4");
	private JLabel minLabel = new JLabel("最低价");
	private JLabel minerLabel = new JLabel("5");
	private JLabel adjLabel = new JLabel("后复权价");
	private JLabel adjerLabel = new JLabel("6");
	private JLabel volLabel = new JLabel("成交量");
	private JLabel volerLabel = new JLabel("7");
	private JLabel turnLabel = new JLabel("换手率");
	private JLabel turnerLabel = new JLabel("8");
	private JLabel PeLabel = new JLabel("市盈率");
	private JLabel PerLabel = new JLabel("9");
	private JLabel PbLabel = new JLabel("市净率");
	private JLabel PberLabel = new JLabel("10");
	private JLabel chartLabel = new JLabel();
	private ChartLabel volButton = new ChartLabel(MyColor.chartShow, MyColor.chartUnShow, true, "VOL");
	private ChartLabel kdjButton = new ChartLabel(MyColor.chartShow, MyColor.chartUnShow, false, "KDJ");
	private ChartLabel atrButton = new ChartLabel(MyColor.chartShow, MyColor.chartUnShow, false, "ATR");
	private JLabel radarLabel = new JLabel();

	private PaintButton addButton = new PaintButton("添加为自选股");
	private DateChooserJButton daterButton = new DateChooserJButton();
	private PlainButton contractButton = new PlainButton(UIConstant.info, UIConstant.infoBorder);

	private JTextArea detailTextArea = new JTextArea();

	private DateAxis xAxis = new DateAxis();

	private XYPlot plot1;
	private XYPlot plot2;
	private XYPlot plot3;
	private XYPlot plot4;

	private ArrayList<StockPO> stockPOs = new ArrayList<StockPO>();
	private static ChartVO chartVO;
	private static String id;

	private HelperService helperService = new Helper();
	private DetailBLService control = new DetailBLController(null);

	private void setButton(DateChooserJButton button) {
		daterButton = button;
	}

	@SuppressWarnings("static-access")
	protected void showStockDetail(String id, ChartVO chartVO) {

		JPanel panel = Single.singlePanel;

		this.id = id;
		this.chartVO = chartVO;

		Calendar rencent = daterButton.getCalen();
		MyDate highDate = new MyDate(rencent);
		MyDate lowDate = highDate.getLastDate(daterButton.getDuration());
		highDate = highDate.afterDay();
		Range_Date range_Date = new Range_Date(lowDate, highDate);

		try {
			stockPOs = control.getData(id, null, range_Date);
		} catch (TimeOut_exception e1) {
			e1.printStackTrace();
			MsgDialogue msg3 = new MsgDialogue(e1.getMessage(), Large.point);
			msg3.disappear(7, msg3);
		} catch (IOException e) {
			e.printStackTrace();
			MsgDialogue msg6 = new MsgDialogue("网络连接异常", Large.point);
			msg6.disappear(7, msg6);
		}

		draw(range_Date);

		topPanel.setOpaque(false);
		chartPanel.setOpaque(false);
		chartTablePanel.setOpaque(false);
		detailPanel.setOpaque(false);

		panel.add(topPanel, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setWeight(1, 0));
		panel.add(chartPanel, new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setWeight(1, 1));
		panel.add(chartTablePanel, new GBC(0, 2).setFill(GBC.BOTH).setIpad(0, 0).setWeight(1, 0));
		panel.add(detailPanel, new GBC(1, 0, 1, 3).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 1));

		chartPanel.add(chartLabel);

		setTop();
		setDetail();
		setChartTable();

		Large.info = Info.singleDetail;
	}

	private void setTop() {

		topPanel.setLayout(new GridBagLayout());
		topLeftStuffPanel.setOpaque(false);
		topRightStuffPanel.setOpaque(false);

		topPanel.add(topLeftStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
		topPanel.add(nameLabel, new GBC(1, 0).setFill(GBC.BOTH).setIpad(100, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(IDLabel, new GBC(2, 0).setFill(GBC.BOTH).setIpad(100, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(topRightStuffPanel,
				new GBC(3, 0).setFill(GBC.BOTH).setIpad(150, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
		topPanel.add(addButton, new GBC(4, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
		// topPanel.add(contractButton, new GBC(5,
		// 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5));

		IDLabel.setText(id);
		nameLabel.setText(stockPOs.get(0).getName());

		addButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				addButtonMouseClicked(evt);
			}
		});

		contractButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				contractButtonMouseClicked(evt);
			}
		});

	}

	public void draw(Range_Date range_Date) {

		double minK = 0;
		double maxK = 0;
		double minVol = 0;
		double maxVol = 0;
		double minKDJ = 0;
		double maxKDJ = 0;
		double minAtr = 0;
		double maxAtr = 0;

		StockPO previous = null;
		if (stockPOs.size() > 0) {
			StockPO tmp = stockPOs.get(stockPOs.size() - 1);
			try {
				previous = control.getData(tmp.getId(), null, new Range_Date(tmp.getDate().beforeDay(), tmp.getDate()))
						.get(0);
			} catch (TimeOut_exception | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("The stockPOs size is 0");
		}

		OHLCSeries series = new OHLCSeries("");// 高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
		TimeSeries seriesVol = new TimeSeries("");
		TimeSeries seriesK = new TimeSeries("");
		TimeSeries seriesD = new TimeSeries("");
		TimeSeries seriesJ = new TimeSeries("");
		TimeSeries seriesAtr = new TimeSeries("");

		ArrayList<KDJVO> kdjvos = null;
		try {
			kdjvos = helperService.getKDJ(id, range_Date);
		} catch (TimeOut_exception | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < stockPOs.size(); i++) {
			StockPO stockPO = stockPOs.get(i);
			Day day = stockPO.getDate().getChartDay();
			series.add(day, stockPO.getStartprice(), stockPO.getMaxprice(), stockPO.getMinprice(),
					stockPO.getEndprice());
			seriesVol.add(day, stockPO.getVolume());
			seriesK.add(day, kdjvos.get(i).getK());
			seriesD.add(day, kdjvos.get(i).getD());
			seriesJ.add(day, kdjvos.get(i).getJ());
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
		TimeSeriesCollection timeSeriesCollectionK = new TimeSeriesCollection();
		TimeSeriesCollection timeSeriesCollectionD = new TimeSeriesCollection();
		TimeSeriesCollection timeSeriesCollectionJ = new TimeSeriesCollection();
		TimeSeriesCollection timeSeriesCollectionAtr = new TimeSeriesCollection();

		seriesCollection.addSeries(series);
		timeSeriesCollectionVol.addSeries(seriesVol);
		timeSeriesCollectionK.addSeries(seriesK);
		timeSeriesCollectionD.addSeries(seriesD);
		timeSeriesCollectionJ.addSeries(seriesJ);
		timeSeriesCollectionAtr.addSeries(seriesAtr);

		maxK = PlotValue.getMaxValue(seriesCollection);
		minK = PlotValue.getMinValue(seriesCollection);
		maxVol = PlotValue.getMaxValue(timeSeriesCollectionVol);
		minVol = PlotValue.getMinValue(timeSeriesCollectionVol);
		maxKDJ = Math.max(PlotValue.getMaxValue(timeSeriesCollectionK), PlotValue.getMaxValue(timeSeriesCollectionD));
		maxKDJ = Math.max(maxKDJ, PlotValue.getMaxValue(timeSeriesCollectionJ));
		minKDJ = Math.min(PlotValue.getMinValue(timeSeriesCollectionK), PlotValue.getMinValue(timeSeriesCollectionD));
		minKDJ = Math.min(minKDJ, PlotValue.getMinValue(timeSeriesCollectionJ));
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
		NumberAxis y3Axis = SetY.setYAxis(maxKDJ, minKDJ);

		plot3 = MyXYPlot.getXYPlot(null, y3Axis);

		StandardXYItemRenderer renderer3K = MyRenderer.getRenderer(MyColor.yellowLine);
		StandardXYItemRenderer renderer3D = MyRenderer.getRenderer(MyColor.purpleLine);
		StandardXYItemRenderer renderer3J = MyRenderer.getRenderer(MyColor.greenLine);

		plot3.setDataset(0, timeSeriesCollectionK);
		plot3.setRenderer(0, renderer3K);

		plot3.setDataset(1, timeSeriesCollectionD);
		plot3.setRenderer(1, renderer3D);

		plot3.setDataset(2, timeSeriesCollectionJ);
		plot3.setRenderer(2, renderer3J);

		// 4
		NumberAxis y4Axis = SetY.setYAxis(maxAtr, minAtr);

		plot4 = MyXYPlot.getXYPlot(null, y4Axis);

		XYDataset dataset4 = MyDataset.getDataset("MATR 7", timeSeriesCollectionAtr, 7);
		StandardXYItemRenderer renderer4 = MyRenderer.getRenderer(MyColor.yellowLine);

		plot4.setDataset(0, dataset4);
		plot4.setRenderer(0, renderer4);

		drawThree(true, false, false);

	}

	private void drawThree(boolean vol, Boolean kdj, boolean atr) {

		CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(xAxis);
		combineddomainxyplot.add(plot1, 2);
		if (vol) {
			combineddomainxyplot.add(plot2, 1);
		}
		if (kdj) {
			combineddomainxyplot.add(plot3, 1);
		}
		if (atr) {
			combineddomainxyplot.add(plot4, 1);
		}
		combineddomainxyplot.setGap(10);
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
		chart.setAntiAlias(true);
		chart.setBackgroundPaint(MyColor.transparentColor);
		chart.setNotify(false);

		FileOutputStream out;
		try {
			out = new FileOutputStream("label/picture.png");
			ChartUtilities.writeChartAsPNG(out, chart, 710, 460);
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
		chartTablePanel.add(kdjButton, new GBC(2, 0).setFill(GBC.BOTH).setIpad(20, 0).setWeight(0, 0));
		chartTablePanel.add(atrButton, new GBC(3, 0).setFill(GBC.BOTH).setIpad(20, 0).setWeight(0, 0));
		chartTablePanel.add(chartTableStuffPanel, new GBC(4, 0).setFill(GBC.BOTH).setIpad(300, 0).setWeight(1, 0));

		volButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				volButtonMouseClicked(evt);
			}
		});

		kdjButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				kdjButtonMouseClicked(evt);
			}
		});

		atrButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				atrButtonMouseClicked(evt);
			}
		});

	}

	private void setTurnOver(double nowData, double preData) {
		// if (nowData > preData) {
		// turnerLabel.setForeground(MyColor.redLine);
		// } else if (nowData < preData) {
		// turnerLabel.setForeground(MyColor.greenLine);
		// } else {
		// turnerLabel.setForeground(Color.black);
		// }
		turnerLabel.setText(nowData + "%");
	}

	private void setDetail() {

		StockPO stockPO = stockPOs.get(0);
		StockPO previousPO = stockPOs.get(1);
		UIConstant.setColorText(stockPO.getStartprice(), previousPO.getStartprice(), openerLabel);
		UIConstant.setColorText(stockPO.getEndprice(), previousPO.getEndprice(), closerLabel);
		UIConstant.setColorText(stockPO.getMaxprice(), previousPO.getMaxprice(), maxerLabel);
		UIConstant.setColorText(stockPO.getMinprice(), previousPO.getMinprice(), minerLabel);
		UIConstant.setColorText(stockPO.getAdjprice(), previousPO.getAdjprice(), adjerLabel);
		UIConstant.setColorText(stockPO.getVolume(), previousPO.getVolume(), volerLabel);
		UIConstant.setColorText(stockPO.getPe(), previousPO.getPe(), PerLabel);
		UIConstant.setColorText(stockPO.getPb(), previousPO.getPb(), PberLabel);
		setTurnOver(stockPO.getTurnover(), previousPO.getTurnover());

		detailPanel.setLayout(new GridBagLayout());
		smallDetailPanel.setLayout(new GridBagLayout());

		detailPanel.setBorder(BorderFactory.createBevelBorder(0, MyColor.leftBorder, MyColor.rightBorder));

		dateButtonPanel.setOpaque(false);
		dateButtonLeftStuffPanel.setOpaque(false);
		dateButtonRightStuffPanel.setOpaque(false);
		smallDetailPanel.setOpaque(false);
		detailUpStuffPanel.setOpaque(false);
		detailDownStuffPanel.setOpaque(false);

		dateButtonPanel.add(dateButtonLeftStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		dateButtonPanel.add(daterButton,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		dateButtonPanel.add(dateButtonRightStuffPanel,
				new GBC(2, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));

		smallDetailPanel.add(openLabel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(openerLabel,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(closeLabel,
				new GBC(2, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(closerLabel,
				new GBC(3, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(maxLabel,
				new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(maxerLabel,
				new GBC(1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(minLabel,
				new GBC(2, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(minerLabel,
				new GBC(3, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(adjLabel,
				new GBC(0, 2).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(adjerLabel,
				new GBC(1, 2).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(volLabel,
				new GBC(2, 2).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(volerLabel,
				new GBC(3, 2).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(turnLabel,
				new GBC(0, 3).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(turnerLabel,
				new GBC(1, 3).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(PeLabel,
				new GBC(2, 3).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(PerLabel,
				new GBC(3, 3).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(PbLabel,
				new GBC(0, 4).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		smallDetailPanel.add(PberLabel,
				new GBC(1, 4).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));

		try {
			detailTextArea.setText('\n' + control.getDescription(stockPOs));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detailTextArea.setOpaque(false);
		detailTextArea.setEditable(false);

		setRadar();

		detailPanel.add(detailUpStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 1));
		detailPanel.add(dateButtonPanel,
				new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		detailPanel.add(smallDetailPanel,
				new GBC(0, 2).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		detailPanel.add(detailTextArea,
				new GBC(0, 3).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 10, 5, 5).setWeight(0, 0));
		detailPanel.add(radarLabel,
				new GBC(0, 4).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 0, 5, 0).setWeight(0, 0));
		detailPanel.add(detailDownStuffPanel,
				new GBC(0, 5).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 1));

	}

	private void setRadar() {

		ArrayList<ChartVO> chartVOs = new ArrayList<ChartVO>();
		chartVOs.add(chartVO);
		SpiderWebdPlotGraph radar = new SpiderWebdPlotGraph(chartVOs);
		radar.init(250);
		try {
			radarLabel.setIcon(new ImageIcon(ImageIO.read(new File("label/radar.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {
		String userName = LoginPanel.getUserName();
		if (!userName.equals("")) {
			try {
				control.select(userName, IDLabel.getText());
			} catch (NotFoundName_exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				MsgDialogue msg1 = new MsgDialogue(e1.getMessage(), Large.point);
				msg1.disappear(UIConstant.time, msg1);
			} catch (ExistID_exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				MsgDialogue msg2 = new MsgDialogue(e1.getMessage(), Large.point);
				msg2.disappear(UIConstant.time, msg2);
			}
		}
	}

	private void contractButtonMouseClicked(java.awt.event.MouseEvent evt) {

	}

	public static void infoShow() {
		new MyDialog("    该图显示了这只股票的详细信息。K线图的5天，10天，20天，30天移动平均线的名字分别为黄色，紫色，绿色，白色。" + "\n"
				+ "    下方的三个按钮分别表示成交量，KDJ指标和平均真实波幅。" + "\n" + "    左键按钮可以显示/隐藏图线，右键按钮可以显示该按钮所展示图线的详细信息。" + "\n"
				+ "    右侧界面显示某天的详细信息，右侧上方的按钮可以选择一个日期和一个时间段n，选择后图线就显示从所选日期往前n天的图线。时间段可选择范围是50~100天。");
	}

	public static void updateChartPanel(DateChooserJButton button) {
		SingleDetail detail = UIFactory.getInstance().getSingleDetail();
		detail.setButton(button);
		Single.singlePanel.removeAll();
		Single.singlePanel.repaint();
		detail.showStockDetail(id, chartVO);
	}

	private void volButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			boolean atr = false;
			boolean money = false;
			if (atrButton.ifShowing()) {
				atr = true;
			}
			if (kdjButton.ifShowing()) {
				money = true;
			}

			drawThree(volButton.ifShowing(), money, atr);
		}

		else if (evt.getButton() == MouseEvent.BUTTON3) {
			new MyDialog("该图显示了每日的成交量，收盘价高于开盘价用红色柱表示，否则用绿色。" + "黄线为5日成交量平均线，紫线为10日均线，绿线为20日均线。");
		}

	}

	private void kdjButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			boolean atr = false;
			boolean vol = false;
			if (atrButton.ifShowing()) {
				atr = true;
			}
			if (volButton.ifShowing()) {
				vol = true;
			}

			drawThree(vol, kdjButton.ifShowing(), atr);
		}

		else if (evt.getButton() == MouseEvent.BUTTON3) {
			new MyDialog("该图显示了每日的KDJ," + "黄线为K线，紫线为D线，绿线为J线。");
		}
	}

	private void atrButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			boolean vol = false;
			boolean money = false;
			if (volButton.ifShowing()) {
				vol = true;
			}
			if (kdjButton.ifShowing()) {
				money = true;
			}

			drawThree(vol, money, atrButton.ifShowing());
		}

		else if (evt.getButton() == MouseEvent.BUTTON3) {
			new MyDialog("该图显示了7日平均真实波幅。");
		}
	}

}
