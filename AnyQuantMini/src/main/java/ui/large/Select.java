package ui.large;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import bl.marketBL.ChartHelper;
import bl.selectBL.SelectBLController;
import blservice.SelectBLService;
import po.StockPO;
import ui.large.single.Single;
import ui.small.LoginPanel;
import ui.util.MyColor;
import ui.util.MyDialog;
import ui.util.UIFactory;
import ui.util.MsgDialogue;
import ui.util.PaintButton;
import ui.util.Scroller;
import ui.util.UIConstant;
import utility.enums.Info;
import utility.exception.NotFoundName_exception;
import utility.exception.TimeOut_exception;
import vo.ChartVO;

public class Select {
	private JPanel tablePanel = new JPanel();
	private JPanel detailPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel bottomStuffPanel = new JPanel();

	private PaintButton deleteButton = new PaintButton("从自选股中删除");

	private JTable table;

	private Vector<Object> items = new Vector<Object>();
	private Vector<String> heading = new Vector<String>();

	@SuppressWarnings("serial")
	private DefaultTableModel defaultModel = new DefaultTableModel(items, heading) {
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

	private SelectBLService control = new SelectBLController(null);

	protected void showSelect() {

		JPanel panel = Large.changePanel;

		tablePanel.setOpaque(false);
		detailPanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		panel.add(tablePanel, new GBC(0, 0).setFill(GBC.BOTH).setIpad(1000, 0).setWeight(1, 1));
		panel.add(detailPanel, new GBC(1, 0, 1, 2).setFill(GBC.BOTH).setIpad(0, 0).setWeight(1, 1));
		panel.add(bottomPanel, new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 30).setWeight(0, 0));
		setTableModel();
		setBottom();

		deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				deleteButtonMouseClicked(evt);
			}
		});

		Large.info = Info.select;

	}

	private void setTableModel() {
		ArrayList<StockPO> stockPOs = new ArrayList<StockPO>();
		String userName = LoginPanel.getUserName();
		try {
			stockPOs = control.show(userName);
		} catch (NotFoundName_exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MsgDialogue m1 = new MsgDialogue(e1.getMessage(), Large.point);
			m1.disappear(UIConstant.time, m1);
		} catch (TimeOut_exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MsgDialogue m2 = new MsgDialogue(e1.getMessage(), Large.point);
			m2.disappear(UIConstant.time, m2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MsgDialogue m3 = new MsgDialogue(e.getMessage(), Large.point);
			m3.disappear(3, m3);
		}

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
			arr.add(stockPO.getPe());
			arr.add(stockPO.getPb());
			arr.add(stockPO.getEndprice() - stockPO.getStartprice());
			try {
				stockPO.setRange();
				arr.add(stockPO.getRange());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			items.add(arr);
		}

		heading.add("代码");
		heading.add("名称");
		heading.add("开盘价");
		heading.add("收盘价");
		heading.add("最高价");
		heading.add("最低价");
		heading.add("成交量");
		heading.add("换手率");
		heading.add("市盈率");
		heading.add("市净率");
		heading.add("涨跌额");
		heading.add("涨跌幅");

		setTable();
	}

	private void setTable() {

		table = new JTable(defaultModel);
		tablePanel.setLayout(new GridBagLayout());
		Color tableChooseColor = new Color(175, 230, 212);

		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(defaultModel);
		table.setRowSorter(sorter);
		table.setSize(new Dimension(1000, 500));
		table.getColumnModel().getColumn(0).setPreferredWidth(85);
		table.getColumnModel().getColumn(1).setPreferredWidth(85);
		table.getColumnModel().getColumn(2).setPreferredWidth(85);
		table.getColumnModel().getColumn(3).setPreferredWidth(85);
		table.getColumnModel().getColumn(4).setPreferredWidth(85);
		table.getColumnModel().getColumn(5).setPreferredWidth(85);
		table.getColumnModel().getColumn(6).setPreferredWidth(85);
		table.getColumnModel().getColumn(7).setPreferredWidth(85);
		table.getColumnModel().getColumn(8).setPreferredWidth(85);
		table.getColumnModel().getColumn(9).setPreferredWidth(85);
		table.getColumnModel().getColumn(10).setPreferredWidth(85);
		table.getColumnModel().getColumn(11).setPreferredWidth(85);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(25);
		table.setShowGrid(true);
		table.setGridColor(tableChooseColor);
		table.setSelectionBackground(new Color(91, 153, 207));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setOpaque(false);
		table.setBackground(MyColor.transparentColor);

		if (table.getRowCount() != 0) {
			table.setRowSelectionInterval(0, 0);
		}
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

		tablePanel.add(scrollPane, new GBC(0, 0).setIpad(0, 0).setFill(GBC.BOTH).setWeight(1, 1));

	}

	private void setBottom() {
		bottomPanel.setLayout(new GridBagLayout());
		bottomStuffPanel.setOpaque(false);
		bottomPanel.add(bottomStuffPanel,
				new GBC(0, 0).setFill(GBC.BOTH).setIpad(470, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		bottomPanel.add(deleteButton,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 10).setInsets(5, 5, 5, 5).setWeight(0, 0));

	}

	private void deleteButtonMouseClicked(java.awt.event.MouseEvent evt) {
		int row = table.getSelectedRow();
		String id = table.getValueAt(row, 0).toString();
		String userName = LoginPanel.getUserName();
		try {
			control.delete(userName, id);
		} catch (NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		defaultModel.removeRow(row);
		table.setModel(defaultModel);
	}

	public static void infoShow() {
		new MyDialog("该表格显示了已登录用户的自选股票。");
	}
}
