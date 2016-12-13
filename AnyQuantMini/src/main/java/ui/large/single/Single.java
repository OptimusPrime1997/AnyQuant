/**
 * 
 */
package ui.large.single;

import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import bl.marketBL.ChartHelper;
import ui.large.GBC;
import ui.large.Large;
import ui.util.DefaultData;
import ui.util.PaintButton;
import ui.util.UIConstant;
import ui.util.UIFactory;
import utility.exception.NotFoundName_exception;
import vo.ChartVO;

/**
 * @author bismuth
 *
 */
public class Single {

	public static JPanel singlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel topStuffPanel = new JPanel();

	private PaintButton detailButton = new PaintButton("单只股票");
	private PaintButton compareButton = new PaintButton("股票比较");

	private String id;
	private ArrayList<String> IDs = new ArrayList<String>();
	private ChartVO chartVO;

	private void showSingle() {
		
		Large.marketButton.setIcon(UIConstant.marketGray);
		Large.selectButton.setIcon(UIConstant.selectGray);
		Large.industryButton.setIcon(UIConstant.industryGray);
		Large.singleButton.setIcon(UIConstant.single);

		JPanel panel = Large.changePanel;

		topPanel.setOpaque(false);
		singlePanel.setOpaque(false);
		singlePanel.setLayout(new GridBagLayout());

		panel.add(topPanel, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
		panel.add(singlePanel, new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 1));

		detailButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				detailButtonMouseClicked(evt);
			}
		});

		compareButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				compareButtonMouseClicked(evt);
			}
		});

		setTop();

	}

	private void setTop() {

		topStuffPanel.setOpaque(false);

		topPanel.setLayout(new GridBagLayout());

		topPanel.add(detailButton, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(compareButton,
				new GBC(1, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(0, 0));
		topPanel.add(topStuffPanel,
				new GBC(2, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
	}

	public void showDetail(String id, ChartVO chartVO) {
		
		Large.changePanel.removeAll();
		Large.changePanel.repaint();
		showSingle();
		if(id==null){
			id = DefaultData.showSingleID;
		}
		if (chartVO==null) {
			ChartHelper chartHelper = new ChartHelper();
			try {
				chartVO = chartHelper.getDetail(DefaultData.showSingleID);
			} catch (IOException | NotFoundName_exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.id = id;
		this.chartVO = chartVO;
		SingleDetail detail = UIFactory.getInstance().getSingleDetail();
		singlePanel.removeAll();
		singlePanel.repaint();
		detail.showStockDetail(id, chartVO);
		singlePanel.repaint();
		singlePanel.validate();
	}

	public void showCompare(ArrayList<String> ids) {
		Large.changePanel.removeAll();
		Large.changePanel.repaint();
		if (ids.size() == 0) {
			ids.add(DefaultData.showSingleID);
			ids.add(DefaultData.showSingleID2);
		}
		showSingle();
		IDs = ids;
		SingleCompare compare = UIFactory.getInstance().getSingleCompare();
		singlePanel.removeAll();
		singlePanel.repaint();
		compare.showStockCompare(ids);
		singlePanel.repaint();
		singlePanel.validate();
	}

	private void detailButtonMouseClicked(java.awt.event.MouseEvent evt) {
		showDetail(id, chartVO);
	}

	private void compareButtonMouseClicked(java.awt.event.MouseEvent evt) {
		showCompare(IDs);
	}
}
