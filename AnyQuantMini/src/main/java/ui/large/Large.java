package ui.large;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import bl.marketBL.ChartHelper;
import ui.large.single.SingleCompare;
import ui.large.single.SingleDetail;
import ui.large.industry.Industry;
import ui.large.industry.IndustryCompare;
import ui.large.industry.IndustryDetail;
import ui.large.single.Single;
import ui.small.LoginPanel;
import ui.util.DefaultData;
import ui.util.MyFrame;
import ui.util.MyPanel;
import ui.util.PaintPanel;
import ui.util.PlainButton;
import ui.util.UIConstant;
import ui.util.UIFactory;
import utility.enums.IndustryType;
import utility.enums.Info;
import utility.exception.NotFoundName_exception;
import vo.ChartVO;

@SuppressWarnings("serial")
public class Large extends MyFrame {
	
	private JPanel tabStuffPanel = new JPanel();
	private JPanel topStuffPanel = new JPanel();
	private PaintPanel topPanel = new PaintPanel(new Color(153, 128, 84), new Color(88, 106, 150));
	private MyPanel tabPanel = new MyPanel(UIConstant.direction.getImage());
	public static MyPanel changePanel = new MyPanel(UIConstant.mainback.getImage());
	private JLabel selectName = new JLabel("自选");
	private JLabel marketName = new JLabel("大盘");
	private JLabel detailName = new JLabel("单股");
	private JLabel industryName = new JLabel("行业");
	private JLabel batmanLabel = new JLabel();

	private PlainButton headButton = new PlainButton(UIConstant.header, UIConstant.headerBorder);
	private PlainButton miniButton = new PlainButton(UIConstant.mini, UIConstant.miniBorder);
	private PlainButton exitButton = new PlainButton(UIConstant.exit, UIConstant.exitBorder);
	public static PlainButton selectButton = new PlainButton(UIConstant.select, UIConstant.selectBorder);
	public static PlainButton marketButton = new PlainButton(UIConstant.market, UIConstant.marketBorder);
	public static PlainButton singleButton = new PlainButton(UIConstant.single, UIConstant.singleBorder);
	public static PlainButton industryButton = new PlainButton(UIConstant.industry, UIConstant.industryborder);
	private JButton skinButton = new JButton(UIConstant.skinLabel);
	private PlainButton infoButton = new PlainButton(UIConstant.info, UIConstant.infoBorder);

	public static Info info;

	public static Point point = new Point(1100, 700);

	public Point getPoint() {
		return point;
	}

	@SuppressWarnings("static-access")
	public void setPoint(Point point) {
		this.point = point;
	}
	
	public static void main(String[] args) {
		Large main = new Large();
		main.init();
		main.setVisible(true);
		main.setBounds(100, 70, 1080, 650);

		int x = (int) (main.getX() + main.getSize().getWidth());
		int y = (int) (main.getY() + main.getSize().getHeight());
		Point p = new Point(x, y);
		main.setPoint(p);
	}

	public void init() {
		UIConstant.changeFont();

		this.setLayout(new GridBagLayout());
		changePanel.setLayout(new GridBagLayout());

		this.add(topPanel, new GBC(0, 0, 2, 1).setFill(GBC.BOTH).setIpad(0, 60).setWeight(0, 1));
		this.add(tabPanel, new GBC(0, 1).setFill(GBC.BOTH).setIpad(7, 0).setWeight(0, 1));
		this.add(changePanel, new GBC(1, 1).setFill(GBC.BOTH).setIpad(10, 0).setWeight(1, 1));
        
//		MyLine line=new MyLine();
//     	line.setBounds(0,0,0,0);
//     	add(line);
		addListener();
		setTab();
		setTop();

		Market market = UIFactory.getInstance().getMarket();
		market.showMarket();
//		Single single = UIFactory.getInstance().getSingle();
//		ChartHelper chartHelper = new ChartHelper();
//		ChartVO chartVO = null;
//		try {
//			chartVO = chartHelper.getDetail(DefaultData.showSingleID);
//		} catch (IOException | NotFoundName_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		single.showDetail(DefaultData.showSingleID, chartVO);
//		Industry industry = UIFactory.getInstance().getIndustry();
//		ArrayList<ChartVO> chartVOs = new ArrayList<ChartVO>();
//		ChartHelper control = new ChartHelper();
//		chartVOs.add(control.Estate());
//		chartVOs.add(control.Finance());
//		chartVOs.add(control.Material());
//		industry.showCompare(chartVOs);
	}

	private void addListener() {

		headButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				headButtonMouseClicked(evt);
			}
		});

		singleButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				singleButtonMouseClicked(evt);
			}
		});

		marketButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				marketButtonMouseClicked(evt);
			}
		});

		selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				selectButtonMouseClicked(evt);
			}
		});

		skinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateALLUI(changePanel);
			}
		});

		industryButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				industryButtonMouseClicked(evt);
			}
		});

		exitButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		miniButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});

		infoButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				infoButtonMouseClicked(evt);
			}
		});

	}

	private void setTop() {

		batmanLabel.setIcon(UIConstant.batman);

		topPanel.setLayout(new GridBagLayout());
		topStuffPanel.setOpaque(false);

		topPanel.add(batmanLabel, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(topStuffPanel,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
		topPanel.add(infoButton, new GBC(2, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(miniButton, new GBC(3, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(exitButton, new GBC(4, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
	}

	private void setTab() {

		tabPanel.setLayout(new GridBagLayout());
		tabStuffPanel.setOpaque(false);

		tabPanel.add(headButton, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		tabPanel.add(selectButton, new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 0, 5).setWeight(0, 0));
		tabPanel.add(selectName, new GBC(0, 2).setFill(GBC.BOTH).setIpad(0, 0).setInsets(0, 20, 5, 5).setWeight(0, 0));
		tabPanel.add(marketButton, new GBC(0, 3).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 0, 5).setWeight(0, 0));
		tabPanel.add(marketName, new GBC(0, 4).setFill(GBC.BOTH).setIpad(0, 0).setInsets(0, 20, 5, 5).setWeight(0, 0));
		tabPanel.add(singleButton, new GBC(0, 5).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 0, 5).setWeight(0, 0));
		tabPanel.add(detailName, new GBC(0, 6).setFill(GBC.BOTH).setIpad(0, 0).setInsets(0, 20, 5, 5).setWeight(0, 0));
		tabPanel.add(industryButton,
				new GBC(0, 7).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 0, 5).setWeight(0, 0));
		tabPanel.add(industryName, new GBC(0, 8).setFill(GBC.BOTH).setIpad(0, 0).setInsets(0, 20, 5, 5).setWeight(0, 0));
		tabPanel.add(tabStuffPanel,
				new GBC(0, 9).setFill(GBC.BOTH).setIpad(10, 230).setInsets(5, 5, 5, 5).setWeight(0, 1));
		headButton.setIcon(UIConstant.headGray);
		selectButton.setIcon(UIConstant.selectGray);
		marketButton.setIcon(UIConstant.market);
		singleButton.setIcon(UIConstant.singleGray);
		industryButton.setIcon(UIConstant.industryGray);
		selectButton.setIcon(UIConstant.selectGray);
		skinButton.setBackground(new Color(255, 0, 0));
		selectButton.setOpaque(false);
	}

	private void headButtonMouseClicked(java.awt.event.MouseEvent evt) {
		String userName = LoginPanel.getUserName();
		if (userName.equals("")) {
			final JFrame frame = new MyFrame();
			frame.setUndecorated(true);
			frame.setSize(251, 316);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation((dim.width - frame.getWidth()) / 2, (dim.height - frame.getHeight()) / 2);
			frame.setVisible(true);
			LoginPanel LP = new LoginPanel(frame, headButton);
			frame.getContentPane().add(LP);
			frame.setVisible(true);
		}
	}

	private void selectButtonMouseClicked(java.awt.event.MouseEvent evt) {
		String userName = LoginPanel.getUserName();
		if (!userName.equals("")) {
			marketButton.setIcon(UIConstant.marketGray);
			singleButton.setIcon(UIConstant.singleGray);
			industryButton.setIcon(UIConstant.industryGray);
			selectButton.setIcon(UIConstant.select);
			Select select = UIFactory.getInstance().getSelect();
			changePanel.removeAll();
			changePanel.repaint();
			select.showSelect();
			changePanel.repaint();
			changePanel.validate();
		} else {
			JOptionPane.showMessageDialog(selectButton, "用户未登录！");
		}
	}
	
	private void marketButtonMouseClicked(java.awt.event.MouseEvent evt) {
		singleButton.setIcon(UIConstant.singleGray);
		selectButton.setIcon(UIConstant.selectGray);
		industryButton.setIcon(UIConstant.industryGray);
		marketButton.setIcon(UIConstant.market);
		Market market = UIFactory.getInstance().getMarket();
		changePanel.removeAll();
		changePanel.repaint();
		market.showMarket();
		changePanel.repaint();
		changePanel.validate();
	}

	private void singleButtonMouseClicked(java.awt.event.MouseEvent evt) {
		ChartHelper chartHelper = new ChartHelper();
		
		ChartVO chartVO = null;
		try {
			chartVO = chartHelper.getDetail(DefaultData.showSingleID);
		} catch (IOException | NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		marketButton.setIcon(UIConstant.marketGray);
		selectButton.setIcon(UIConstant.selectGray);
		industryButton.setIcon(UIConstant.industryGray);
		singleButton.setIcon(UIConstant.single);
		changePanel.removeAll();
		changePanel.repaint();
		Single single = UIFactory.getInstance().getSingle();
		single.showDetail(DefaultData.showSingleID, chartVO);
		changePanel.repaint();
		changePanel.validate();
	}

	private void industryButtonMouseClicked(java.awt.event.MouseEvent evt) {
		IndustryType id = DefaultData.showIndustryID;
		marketButton.setIcon(UIConstant.marketGray);
		singleButton.setIcon(UIConstant.singleGray);
		selectButton.setIcon(UIConstant.selectGray);
		industryButton.setIcon(UIConstant.industry);
		Industry industry = UIFactory.getInstance().getIndustry();
		changePanel.removeAll();
		changePanel.repaint();
		industry.showDetail(id);
		changePanel.repaint();
		changePanel.validate();
	}

	private void infoButtonMouseClicked(java.awt.event.MouseEvent evt) {
		switch (info) {
		case select:
			Select.infoShow();
			break;
		case market:
			Market.infoShow();
			break;
		case singleDetail:
			SingleDetail.infoShow();
			break;
		case singleCompare:
			SingleCompare.infoShow();
			break;
		case industryDetail:
			IndustryDetail.infoShow();
			break;
		case industryCompare:
			IndustryCompare.infoShow();
			break;
		default:
			break;
		}
	}

	private static void updateALLUI(Component c) {
		if (c == null)
			return;
		try {
			if (c instanceof JComponent) {
				((JComponent) c).updateUI();

			} else {
				c.repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c instanceof Container) {
			if (c instanceof JMenu) {
				Component[] cs = ((JMenu) c).getMenuComponents();
				for (Component c2 : cs) {
					updateALLUI(c2);
				}
			}
			Component[] cs = ((Container) c).getComponents();
			for (Component c2 : cs) {
				updateALLUI(c2);
			}
		}
	}

	public static void changeFont() {
		Font font = new Font("微软雅黑", Font.PLAIN, 15);
		@SuppressWarnings("rawtypes")
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
	}

}
