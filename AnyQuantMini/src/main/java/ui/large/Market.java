package ui.large;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.XYDataset;

import bl.compareBL.CompareWithMarket;
import bl.detailBL.DetailBLController;
import bl.marketBL.ChartHelper;
import bl.marketBL.MarketBLController;
import blservice.DetailBLService;
import blservice.marketBLService;
import po.StockPO;
import strategy.sifthelper.SiftByAmount;
import ui.util.PaintButton;
import ui.util.PlotValue;
import ui.large.single.Single;
import ui.util.ChoosingButton;
import ui.util.DateChooserJButton;
import ui.util.UIFactory;
import ui.util.MyColor;
import ui.util.MyDataset;
import ui.util.MyDialog;
import ui.util.MsgDialogue;
import ui.util.MyPanel;
import ui.util.MyRenderer;
import ui.util.MyTextField;
import ui.util.MyXYPlot;
import ui.util.Scroller;
import ui.util.SetX;
import ui.util.SetY;
import ui.util.UIConstant;
import utility.Constants;
import utility.MyDate;
import utility.Range_Date;
import utility.enums.Info;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;
import vo.ChartVO;

public class Market {

	private class myDialog {

		JDialog jDialog = new JDialog();

		myDialog(ArrayList<StockPO> stockPOs) {
			Vector<Object> items = new Vector<Object>();
			Vector<String> head = new Vector<String>();
			head.add("");
			head.add("");
			for (int i = 0; i < stockPOs.size(); i++) {
				Vector<String> arr = new Vector<String>();
				StockPO stockPO = stockPOs.get(i);
				arr.add(stockPO.getId());
				arr.add(stockPO.getName());
				items.add(arr);
			}
			@SuppressWarnings("serial")
			DefaultTableModel defaultModel = new DefaultTableModel(items, head) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			final JTable searchTable = new JTable(defaultModel);
			searchTable.setRowHeight(30);
			searchTable.setSize(new Dimension(200, 200));
			searchTable.getColumnModel().getColumn(0).setPreferredWidth(80);
			searchTable.getColumnModel().getColumn(1).setPreferredWidth(120);
			searchTable.setShowGrid(true);
			searchTable.setGridColor(new Color(175, 230, 212));
			searchTable.setSelectionBackground(new Color(91, 153, 207));
			searchTable.setSelectionForeground(Color.black);
			searchTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			searchTable.setOpaque(false);
			searchTable.setBackground(MyColor.transparentColor);
			searchTable.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					int row = table.getSelectedRow();
					if (e.getClickCount() == 2) {
						jDialog.dispose();
						String id = table.getValueAt(row, 0).toString();
						ChartHelper chartHelper = new ChartHelper();
						ChartVO chartVO = null;
						try {
							chartVO = chartHelper.getDetail(id);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NotFoundName_exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Single single = UIFactory.getInstance().getSingle();
						single.showDetail(id, chartVO);

					}
				}
			});

			final JScrollPane scrollPane = new JScrollPane(searchTable);
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);

			MyPanel panel = new MyPanel(UIConstant.direction.getImage());
			panel.setLayout(new GridBagLayout());
			panel.add(scrollPane,
					new GBC(0, 0).setFill(GBC.BOTH).setIpad(200, 200).setInsets(5, 5, 5, 5).setWeight(0, 0));

			location = searchLabel.getLocationOnScreen();
			int x = location.x;
			int y = location.y;

			jDialog.setUndecorated(true);
			jDialog.setLayout(new GridBagLayout());
			jDialog.add(panel, new GBC(0, 0).setFill(GBC.BOTH));
			jDialog.setLocation(x, y + 25);
			jDialog.setSize(200, 200);
			jDialog.setMaximumSize(new Dimension(200, 200));
			jDialog.setMinimumSize(new Dimension(200, 200));
			jDialog.setVisible(true);

			searchTable.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					jDialog.dispose();
				}

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
				}
			});

		}
	}

	private JPanel topPanel = new JPanel();
	private JPanel siftPanel = new JPanel();
	private JPanel tablePanel = new JPanel();
	private JPanel chartPanel = new JPanel();
	private JPanel detailPanel = new JPanel();
	private JPanel dateButtonPanel = new JPanel();
	private JPanel dateButtonLeftStuffPanel = new JPanel();
	private JPanel dateButtonRightStuffPanel = new JPanel();
	private JPanel topStuffPanel = new JPanel();
	private JPanel chartStuffPanel = new JPanel();
	private JPanel detailStuffPanel = new JPanel();

	private JLabel searchLabel = new JLabel("搜索");
	private JLabel startLabel = new JLabel("开盘价");
	private JLabel closeLabel = new JLabel("收盘价");
	private JLabel maxLabel = new JLabel("最高价");
	private JLabel minLabel = new JLabel("最低价");
	private JLabel accountLabel = new JLabel("成交量");
	private JLabel turnOverLabel = new JLabel("换手率");
	private JLabel l1 = new JLabel("至");
	private JLabel l2 = new JLabel("至");
	private JLabel l3 = new JLabel("至");
	private JLabel l4 = new JLabel("至");
	private JLabel l5 = new JLabel("至");
	private JLabel l6 = new JLabel("至");
	private JLabel chartLabel = new JLabel();

	private MyTextField searchText = new MyTextField(20);
	private MyTextField x11 = new MyTextField(20);
	private MyTextField x12 = new MyTextField(20);
	private MyTextField x21 = new MyTextField(20);
	private MyTextField x22 = new MyTextField(20);
	private MyTextField x31 = new MyTextField(20);
	private MyTextField x32 = new MyTextField(20);
	private MyTextField x41 = new MyTextField(20);
	private MyTextField x42 = new MyTextField(20);
	private MyTextField x51 = new MyTextField(20);
	private MyTextField x52 = new MyTextField(20);
	private MyTextField x61 = new MyTextField(20);
	private MyTextField x62 = new MyTextField(20);

	private JTextArea detailTextArea = new JTextArea();

	private PaintButton searchButton = new PaintButton("GO");
	private PaintButton siftClearButton = new PaintButton("全部清空");
	private PaintButton siftButton = new PaintButton("筛选");
	private ChoosingButton compareButton = new ChoosingButton("比较");
	private PaintButton d1 = new PaintButton("清空");
	private PaintButton d2 = new PaintButton("清空");
	private PaintButton d3 = new PaintButton("清空");
	private PaintButton d4 = new PaintButton("清空");
	private PaintButton d5 = new PaintButton("清空");
	private PaintButton d6 = new PaintButton("清空");
	private DateChooserJButton daterButton = new DateChooserJButton();

	private Point location;

	private JTable table;

	private ArrayList<StockPO> stockPOs;

	private int[] selected = new int[5];

	private marketBLService control = new MarketBLController(null, new SiftByAmount());
	private DetailBLService control2 = new DetailBLController(null);

	private void setButton(DateChooserJButton button) {
		daterButton = button;
	}

	protected void showMarket() {

		JPanel panel = Large.changePanel;

		Calendar rencent = daterButton.getCalen();
		MyDate highDate = new MyDate(rencent);
		MyDate lowDate = highDate.getLastDate(daterButton.getDuration());
		highDate = highDate.afterDay();
		Range_Date range_Date = new Range_Date(lowDate, highDate);

		try {
			stockPOs = control2.getData(Constants.HS300, null, range_Date);
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

		topPanel.setOpaque(false);
		siftPanel.setOpaque(false);
		tablePanel.setOpaque(false);
		chartPanel.setOpaque(false);
		detailPanel.setOpaque(false);

		panel.add(topPanel, new GBC(1, 0, 2, 1).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0));
		panel.add(siftPanel, new GBC(0, 0, 1, 2).setFill(GBC.BOTH).setIpad(10, 0).setWeight(0, 1));
		panel.add(tablePanel, new GBC(1, 1, 2, 1).setFill(GBC.BOTH).setIpad(0, 0).setWeight(1, 1));
		panel.add(chartPanel, new GBC(0, 2, 2, 1).setFill(GBC.BOTH).setIpad(450, 0).setWeight(1, 0));
		panel.add(detailPanel, new GBC(2, 2).setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0));

		setTop();
		setSift();
		setTable();
		setChartPanel();
		setDetail();

		addListener();

		Large.info = Info.market;

	}

	private void addListener() {
		searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				searchButtonMouseClicked(evt);
			}
		});

		compareButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				compareButtonMouseClicked(evt);
			}
		});

		searchText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				searchTextKeyPressed(e);
			}
		});

		siftButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				siftButtonMouseClicked(evt);
			}
		});

		d1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				d1MouseClicked(evt);
			}
		});

		d2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				d2MouseClicked(evt);
			}
		});

		d3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				d3MouseClicked(evt);
			}
		});

		d4.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				d4MouseClicked(evt);
			}
		});

		d5.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				d5MouseClicked(evt);
			}
		});

		d6.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				d6MouseClicked(evt);
			}
		});

		siftClearButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				siftClearButtonMouseClicked(evt);
			}
		});

		x11.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x11KeyPressed(e);
			}
		});

		x12.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x12KeyPressed(e);
			}
		});

		x21.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x21KeyPressed(e);
			}
		});

		x22.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x22KeyPressed(e);
			}
		});

		x31.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x31KeyPressed(e);
			}
		});

		x32.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x32KeyPressed(e);
			}
		});

		x41.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x41KeyPressed(e);
			}
		});

		x42.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x42KeyPressed(e);
			}
		});

		x51.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x51KeyPressed(e);
			}
		});

		x52.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x52KeyPressed(e);
			}
		});

		x61.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x61KeyPressed(e);
			}
		});

		x62.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				x62KeyPressed(e);
			}
		});
	}

	private void setTextField(ArrayList<MyTextField> myTextFields) {
		for (int i = 0; i < myTextFields.size(); i++) {
			MyTextField myTextField = myTextFields.get(i);
			myTextField.setBackground(MyColor.transparentColor);
			myTextField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		}
	}

	private void search() {
		ArrayList<StockPO> stockPOs = null;
		try {
			stockPOs = control.searchByID(searchText.getText());
		} catch (TimeOut_exception e) {
			e.printStackTrace();
			MsgDialogue msg5 = new MsgDialogue(e.getMessage(), Large.point);
			msg5.disappear(UIConstant.time, msg5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MsgDialogue msg7 = new MsgDialogue("网络连接异常", Large.point);
			msg7.disappear(UIConstant.time, msg7);
		}
		new myDialog(stockPOs);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sift() {
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
		if (!x11.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Double.parseDouble(x11.getText()), 2));
		if (!x12.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Double.parseDouble(x12.getText()), 2));
		if (!x21.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Double.parseDouble(x21.getText()), 3));
		if (!x22.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Double.parseDouble(x22.getText()), 3));
		if (!x31.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Double.parseDouble(x31.getText()), 4));
		if (!x32.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Double.parseDouble(x32.getText()), 4));
		if (!x41.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Double.parseDouble(x41.getText()), 5));
		if (!x42.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Double.parseDouble(x42.getText()), 5));
		if (!x51.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Double.parseDouble(x51.getText()), 6));
		if (!x52.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Double.parseDouble(x52.getText()), 6));
		if (!x61.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Double.parseDouble(x61.getText()), 7));
		if (!x62.getText().equals(""))
			filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Double.parseDouble(x62.getText()), 7));

		RowFilter<Object, Object> fooBarFilter = RowFilter.andFilter(filters);
		((TableRowSorter) table.getRowSorter()).setRowFilter(fooBarFilter);
	}

	private void compare() {

		compareButton.setChoosing(!compareButton.ifChoosing());

		if (compareButton.ifChoosing()) {
			table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			table.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					tableMouseClicked(evt);
				}
			});

			table.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					tableKeyPressed(e);
				}
			});
		} else {
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

	}

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {

		int[] selection = table.getSelectedRows();

		if (selection.length > 3) {
			table.clearSelection();
			table.setRowSelectionInterval(selected[0], selected[2]);

			for (int i = 0; i < 2; i++) {
				if (selected[i + 1] - selected[i] > 1) {
					table.removeRowSelectionInterval(selected[i] + 1, selected[i + 1] - 1);
				}
			}

		} else {
			selected = selection;
		}
	}

	private void tableKeyPressed(java.awt.event.KeyEvent evt) {

		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			int[] selection = table.getSelectedRows();
			if (selection.length >= 2) {
				ArrayList<String> ids = new ArrayList<String>();
				for (int i = 0; i < selection.length; i++) {
					ids.add(table.getValueAt(selection[i], 0).toString());
				}
				Single single = UIFactory.getInstance().getSingle();
				single.showCompare(ids);
			}
		}
	}

	private void setTop() {

		ArrayList<MyTextField> myTextFields = new ArrayList<MyTextField>();
		myTextFields.add(searchText);
		setTextField(myTextFields);

		topPanel.setLayout(new GridBagLayout());
		topStuffPanel.setOpaque(false);

		topPanel.add(topStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(300, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
		topPanel.add(compareButton,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(searchLabel, new GBC(2, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(searchText, new GBC(3, 0).setFill(GBC.BOTH).setIpad(100, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(searchButton, new GBC(4, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
	}

	private void setSift() {

		ArrayList<MyTextField> myTextFields = new ArrayList<MyTextField>();
		myTextFields.add(x11);
		myTextFields.add(x12);
		myTextFields.add(x21);
		myTextFields.add(x22);
		myTextFields.add(x31);
		myTextFields.add(x32);
		myTextFields.add(x41);
		myTextFields.add(x42);
		myTextFields.add(x51);
		myTextFields.add(x52);
		myTextFields.add(x61);
		myTextFields.add(x62);
		setTextField(myTextFields);

		siftPanel.setLayout(new GridBagLayout());

		siftPanel.add(siftClearButton,
				new GBC(0, 0, 2, 1).setFill(GBC.BOTH).setIpad(0, 5).setInsets(5, 5, 0, 5).setWeight(0, 0));

		siftPanel.add(startLabel,
				new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x11, new GBC(1, 1, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(l1, new GBC(3, 1, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x12, new GBC(4, 1, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(d1, new GBC(6, 1, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));

		siftPanel.add(closeLabel,
				new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x21, new GBC(1, 2, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(l2, new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x22, new GBC(4, 2, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(d2, new GBC(6, 2, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));

		siftPanel.add(maxLabel,
				new GBC(0, 3, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x31, new GBC(1, 3, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(l3, new GBC(3, 3, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x32, new GBC(4, 3, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(d3, new GBC(6, 3, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));

		siftPanel.add(minLabel,
				new GBC(0, 4, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x41, new GBC(1, 4, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(l4, new GBC(3, 4, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x42, new GBC(4, 4, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(d4, new GBC(6, 4, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));

		siftPanel.add(accountLabel, new GBC(0, 5, 1, 1).setFill(GBC.BOTH).setInsets(5, 5, 5, 5));
		siftPanel.add(x51, new GBC(1, 5, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(l5, new GBC(3, 5, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x52, new GBC(4, 5, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(d5, new GBC(6, 5, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));

		siftPanel.add(turnOverLabel,
				new GBC(0, 6, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x61, new GBC(1, 6, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(l6, new GBC(3, 6, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(x62, new GBC(4, 6, 2, 1).setFill(GBC.BOTH).setIpad(70, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		siftPanel.add(d6, new GBC(6, 6, 1, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));

		siftPanel.add(siftButton,
				new GBC(0, 7, 2, 1).setFill(GBC.BOTH).setIpad(0, 5).setInsets(0, 5, 5, 5).setWeight(0, 0));

	}

	private void setTable() {
		Calendar today = daterButton.getCalen();
		ArrayList<StockPO> stockPOs = new ArrayList<StockPO>();
		try {
			stockPOs = control.getData(new MyDate(today));
		} catch (TimeOut_exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MsgDialogue msg4 = new MsgDialogue(e1.getMessage(), Large.point);
			msg4.disappear(UIConstant.time, msg4);

		}
		Vector<Object> items = new Vector<Object>();
		for (int i = 0; i < stockPOs.size(); i++) {
			Vector<Object> arr = new Vector<Object>();
			StockPO stockPO = stockPOs.get(i);
			arr.add(stockPO.getId());
			arr.add(stockPO.getName());
			arr.add(stockPO.getStartprice());
			arr.add(stockPO.getEndprice());
			arr.add(stockPO.getMaxprice());
			arr.add(stockPO.getMinprice());
			arr.add(stockPO.getVolume());
			arr.add(stockPO.getTurnover());
			items.add(arr);
		}
		Vector<String> heading = new Vector<String>();
		heading.add("代码");
		heading.add("名称");
		heading.add("开盘价");
		heading.add("收盘价");
		heading.add("最高价");
		heading.add("最低价");
		heading.add("成交量");
		heading.add("换手率");

		@SuppressWarnings("serial")
		DefaultTableModel defaultModel = new DefaultTableModel(items, heading) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
				Class returnValue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnValue = getValueAt(0, column).getClass();
				} else {
					returnValue = Object.class;
				}
				return returnValue;
			}
		};

		table = new JTable(defaultModel);
		tablePanel.setLayout(new GridBagLayout());
		Color tableChooseColor = new Color(175, 230, 212);

		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(defaultModel);
		table.setRowSorter(sorter);
		table.setSize(new Dimension(500, 300));
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setPreferredWidth(80);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(25);
		table.setShowGrid(true);
		table.setGridColor(tableChooseColor);
		table.setSelectionBackground(new Color(91, 153, 207));
		table.setSelectionForeground(Color.black);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setOpaque(false);
		table.setBackground(MyColor.transparentColor);

		// 双击某行显示该股票detail
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int row = table.getSelectedRow();
				if (e.getClickCount() == 2) {
					String id = table.getValueAt(row, 0).toString();
					ChartHelper chartHelper = new ChartHelper();
					ChartVO chartVO = null;
					try {
						chartVO = chartHelper.getDetail(id);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotFoundName_exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Single single = UIFactory.getInstance().getSingle();
					single.showDetail(id, chartVO);
				}
			}
		});

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		header.setBackground(tableChooseColor);
		header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getVerticalScrollBar().setUI(new Scroller());
		scrollPane.getHorizontalScrollBar().setUI(new Scroller());

		tablePanel.add(scrollPane, new GBC(0, 0).setIpad(0, 0).setFill(GBC.BOTH).setInsets(0, 0, 0, 0).setWeight(1, 1));

	}

	private void draw() {

		double minK = 0;
		double maxK = 0;
		OHLCSeries series = new OHLCSeries("");// 高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
		for (int i = 0; i < stockPOs.size(); i++) {
			StockPO stockPO = stockPOs.get(i);
			Day today = stockPO.getDate().getChartDay();
			series.add(today, stockPO.getStartprice(), stockPO.getMaxprice(), stockPO.getMinprice(),
					stockPO.getEndprice());
		}
		final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();// 保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
		seriesCollection.addSeries(series);

		maxK = PlotValue.getMaxValue(seriesCollection);
		minK = PlotValue.getMinValue(seriesCollection);

		final CandlestickRenderer candlestickRender = new CandlestickRenderer();// 设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
		candlestickRender.setUseOutlinePaint(true);
		candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// 设置如何对K线图的宽度进行设定
		candlestickRender.setAutoWidthGap(0.001);// 设置各个K线图之间的间隔
		candlestickRender.setUpPaint(MyColor.redLine);
		candlestickRender.setDownPaint(MyColor.greenLine);

		DateAxis xAxis = SetX.setAxis(stockPOs);
		NumberAxis yAxis = SetY.setCandleYAxis(maxK, minK);

		XYPlot plot = MyXYPlot.getXYPlot(xAxis, yAxis);
		plot.setDataset(0, seriesCollection);
		plot.setRenderer(0, candlestickRender);

		XYDataset dataset1 = MyDataset.getDataset("-MA 5", seriesCollection, 5);
		StandardXYItemRenderer renderer1 = MyRenderer.getRenderer(MyColor.yellowLine);
		plot.setDataset(1, dataset1);
		plot.setRenderer(1, renderer1);

		XYDataset dataset2 = MyDataset.getDataset("-MA 10", seriesCollection, 10);
		StandardXYItemRenderer renderer2 = MyRenderer.getRenderer(MyColor.purpleLine);
		plot.setDataset(2, dataset2);
		plot.setRenderer(2, renderer2);

		XYDataset dataset3 = MyDataset.getDataset("-MA 20", seriesCollection, 20);
		StandardXYItemRenderer renderer3 = MyRenderer.getRenderer(MyColor.greenLine);
		plot.setDataset(3, dataset3);
		plot.setRenderer(3, renderer3);

		XYDataset dataset4 = MyDataset.getDataset("-MA 30", seriesCollection, 30);
		StandardXYItemRenderer renderer4 = MyRenderer.getRenderer(MyColor.whiteLine);
		plot.setDataset(4, dataset4);
		plot.setRenderer(4, renderer4);

		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		chart.setAntiAlias(true);
		chart.setBackgroundPaint(MyColor.transparentColor);
		chart.setNotify(false);

		FileOutputStream out;
		try {
			out = new FileOutputStream("label/picture.png");
			ChartUtilities.writeChartAsPNG(out, chart, 800, 275);
			chartLabel.setIcon(new ImageIcon(ImageIO.read(new File("label/picture.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setChartPanel() {

		chartPanel.setLayout(new GridBagLayout());
		chartStuffPanel.setOpaque(false);

		chartPanel.add(chartStuffPanel,
				new GBC(0, 0).setIpad(0, 0).setFill(GBC.BOTH).setInsets(5, 5, 5, 5).setWeight(1, 0));
		chartPanel.add(chartLabel, new GBC(1, 0).setIpad(0, 0).setFill(GBC.BOTH).setInsets(5, 5, 5, 5).setWeight(0, 0));
	}

	private void setDetail() {

		CompareWithMarket c = new CompareWithMarket();
		try {
			detailTextArea.setText(c.compare(stockPOs));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detailTextArea.setOpaque(false);
		detailTextArea.setEditable(false);
		dateButtonPanel.setOpaque(false);
		dateButtonLeftStuffPanel.setOpaque(false);
		dateButtonRightStuffPanel.setOpaque(false);
		detailStuffPanel.setOpaque(false);

		detailPanel.setLayout(new GridBagLayout());
		dateButtonPanel.setLayout(new GridBagLayout());

		dateButtonPanel.add(dateButtonLeftStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 0, 5, 0).setWeight(1, 0));
		dateButtonPanel.add(daterButton,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 0, 5, 5).setWeight(0, 0));
		dateButtonPanel.add(dateButtonRightStuffPanel,
				new GBC(2, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));

		detailPanel.add(dateButtonPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(0, 5, 5, 5).setWeight(0, 0));
		detailPanel.add(detailTextArea,
				new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 15, 5, 5).setWeight(0, 0));
		detailPanel.add(detailStuffPanel,
				new GBC(0, 2).setFill(GBC.BOTH).setIpad(0, 80).setInsets(5, 5, 5, 5).setWeight(0, 0));
		
	}

	private void compareButtonMouseClicked(java.awt.event.MouseEvent evt) {
		compare();
	}

	private void searchButtonMouseClicked(java.awt.event.MouseEvent evt) {
		search();
	}

	private void siftButtonMouseClicked(java.awt.event.MouseEvent evt) {
		sift();
	}

	private void searchTextKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			search();
		}
	}

	private void d1MouseClicked(java.awt.event.MouseEvent evt) {
		x11.setText("");
		x12.setText("");
	}

	private void d2MouseClicked(java.awt.event.MouseEvent evt) {
		x21.setText("");
		x22.setText("");
	}

	private void d3MouseClicked(java.awt.event.MouseEvent evt) {
		x31.setText("");
		x32.setText("");
	}

	private void d4MouseClicked(java.awt.event.MouseEvent evt) {
		x41.setText("");
		x42.setText("");
	}

	private void d5MouseClicked(java.awt.event.MouseEvent evt) {
		x51.setText("");
		x52.setText("");
	}

	private void d6MouseClicked(java.awt.event.MouseEvent evt) {
		x61.setText("");
		x62.setText("");
	}

	private void siftClearButtonMouseClicked(java.awt.event.MouseEvent evt) {
		x11.setText("");
		x12.setText("");
		x21.setText("");
		x22.setText("");
		x31.setText("");
		x32.setText("");
		x41.setText("");
		x42.setText("");
		x51.setText("");
		x52.setText("");
		x61.setText("");
		x62.setText("");
		sift();
	}

	private void x11KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x21.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x12KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x22.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x21KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x11.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x31.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x22KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x12.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x32.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x31KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x21.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x41.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x32KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x22.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x42.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x41KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x31.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x51.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x42KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x32.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x52.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x51KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x41.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x61.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x52KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x42.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_DOWN)
			x62.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x61KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x51.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	private void x62KeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_UP)
			x52.requestFocus();
		if (evt.getKeyCode() == KeyEvent.VK_ENTER)
			sift();
	}

	public static void infoShow() {
		new MyDialog("该图显示了近70天的大盘K线图。5天，10天，20天，30天移动平均线的名字分别为黄色，紫色，绿色，白色。");
	}

	public static void updateChartPanel(DateChooserJButton button) {
		Market market = UIFactory.getInstance().getMarket();
		market.setButton(button);
		Large.changePanel.removeAll();
		Large.changePanel.repaint();
		market.showMarket();
	}

}