/**
 * 
 */
package ui.large.industry;

import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import bl.marketBL.ChartHelper;
import ui.large.GBC;
import ui.large.Large;
import ui.util.PaintButton;
import ui.util.UIConstant;
import ui.util.UIFactory;
import utility.enums.IndustryType;
import vo.ChartVO;

/**
 * @author bismuth
 *
 */
public class Industry {

	public static JPanel industryPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel topStuffPanel = new JPanel();

	private PaintButton detailButton = new PaintButton("单个行业");
	private PaintButton compareButton = new PaintButton("行业比较");

	private IndustryType id;
	
	private ArrayList<ChartVO> chartVOs = new ArrayList<ChartVO>();
	
	private ChartHelper control = new ChartHelper();

	private void showIndustry() {
		
		Large.marketButton.setIcon(UIConstant.marketGray);
		Large.singleButton.setIcon(UIConstant.singleGray);
		Large.selectButton.setIcon(UIConstant.selectGray);
		Large.industryButton.setIcon(UIConstant.industry);

		JPanel panel = Large.changePanel;

		topPanel.setOpaque(false);
		industryPanel.setOpaque(false);
		industryPanel.setLayout(new GridBagLayout());

		panel.add(topPanel, new GBC(0, 0).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 0));
		panel.add(industryPanel, new GBC(0, 1).setFill(GBC.BOTH).setIpad(0, 0).setInsets(5, 5, 5, 5).setWeight(1, 1));

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

	public void showDetail(IndustryType id) {
		Large.changePanel.removeAll();
		Large.changePanel.repaint();
		showIndustry();
		if (id==null) {
			id = IndustryType.ESTATE;
		}
		this.id = id;
		IndustryDetail detail = UIFactory.getInstance().getIndustryDetail();
		industryPanel.removeAll();
		industryPanel.repaint();
		detail.showIndustryDetail(id);
		industryPanel.repaint();
		industryPanel.validate();
	}

	public void showCompare(ArrayList<ChartVO> chartVOs) {
		Large.changePanel.removeAll();
		Large.changePanel.repaint();
		if (chartVOs.size() == 0) {
			chartVOs.add(control.Estate());
			chartVOs.add(control.Finance());
			chartVOs.add(control.Material());
		}
		showIndustry();
		IndustryCompare compare = UIFactory.getInstance().getIndustryCompare();
		industryPanel.removeAll();
		industryPanel.repaint();
		compare.showIndustryCompare(chartVOs);
		industryPanel.repaint();
		industryPanel.validate();
	}

	private void detailButtonMouseClicked(java.awt.event.MouseEvent evt) {
		showDetail(id);
	}

	private void compareButtonMouseClicked(java.awt.event.MouseEvent evt) {
		showCompare(chartVOs);
	}

}
