/**

 * 
 */
package ui.util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * @author 1
 *
 */
/**
 * @author 1
 *
 */
public class UIConstant {
	public final static int time = 4;

	/**
	 * button image
	 */
	public final static ImageIcon select = new ImageIcon(UIConstant.class.getResource("/image/button/select.png"));
	public final static ImageIcon selectBorder = new ImageIcon(
			UIConstant.class.getResource("/image/button/selectBorder.png"));
	public final static ImageIcon selectGray = new ImageIcon(
			UIConstant.class.getResource("/image/button/selecter.png"));
	public final static ImageIcon market = new ImageIcon(UIConstant.class.getResource("/image/button/market.png"));
	public final static ImageIcon marketBorder = new ImageIcon(
			UIConstant.class.getResource("/image/button/marketBorder.png"));
	public final static ImageIcon marketGray = new ImageIcon(
			UIConstant.class.getResource("/image/button/marketer.png"));
	public final static ImageIcon single = new ImageIcon(UIConstant.class.getResource("/image/button/detail.png"));
	public final static ImageIcon singleBorder = new ImageIcon(
			UIConstant.class.getResource("/image/button/detailBorder.png"));
	public final static ImageIcon singleGray = new ImageIcon(
			UIConstant.class.getResource("/image/button/detailer.png"));
	public final static ImageIcon industryGray = new ImageIcon(
			UIConstant.class.getResource("/image/button/comparer.png"));
	public final static ImageIcon industryborder = new ImageIcon(
			UIConstant.class.getResource("/image/button/compareborder.png"));
	public final static ImageIcon industry = new ImageIcon(UIConstant.class.getResource("/image/button/compare.png"));
	public final static ImageIcon header = new ImageIcon(UIConstant.class.getResource("/image/button/header.png"));
	public final static ImageIcon headerBorder = new ImageIcon(
			UIConstant.class.getResource("/image/button/headerBorder.png"));
	public final static ImageIcon headGray = new ImageIcon(UIConstant.class.getResource("/image/button/headgray.png"));

	public final static ImageIcon exit = new ImageIcon(UIConstant.class.getResource("/image/button/exit.png"));
	public final static ImageIcon exitBorder = new ImageIcon(
			UIConstant.class.getResource("/image/button/exitBorder.png"));
	public final static ImageIcon mini = new ImageIcon(UIConstant.class.getResource("/image/button/mini.png"));
	public final static ImageIcon miniBorder = new ImageIcon(
			UIConstant.class.getResource("/image/button/miniBorder.png"));
	public final static ImageIcon skinLabel = new ImageIcon(
			UIConstant.class.getResource("/image/button/skinLabel.png"));
	public final static ImageIcon logo = new ImageIcon(UIConstant.class.getResource("/image/label/logo.png"));

	/**
	 * background image
	 */
	public final static ImageIcon dialogue = new ImageIcon(
			UIConstant.class.getResource("/image/background/dialogue.png"));
	public final static ImageIcon mainback = new ImageIcon(
			UIConstant.class.getResource("/image/background/mainBack.png"));
	public final static ImageIcon direction = new ImageIcon(
			UIConstant.class.getResource("/image/background/direction.png"));
	public final static ImageIcon direction2 = new ImageIcon(
			UIConstant.class.getResource("/image/background_1/direction_1.png"));

	public final static ImageIcon skin = new ImageIcon(UIConstant.class.getResource("/image/background/skin_10.png"));
	public final static ImageIcon mainback_1 = new ImageIcon(
			UIConstant.class.getResource("/image/background/mainBack.png"));
	public final static ImageIcon direction_1 = new ImageIcon(
			UIConstant.class.getResource("/image/background_1/direction_1.png"));

	public static ImageIcon skin_1 = new ImageIcon(UIConstant.class.getResource("/image/background_1/skin_1.png"));

	public static ImageIcon calendarBack = new ImageIcon(
			UIConstant.class.getResource("/image/background/calendarBack.png"));

	public static ImageIcon loginBack = new ImageIcon(UIConstant.class.getResource("/image/background/loginBack.png"));
	/**
	 * label image
	 */
	public final static ImageIcon login = new ImageIcon(UIConstant.class.getResource("/image/label/login.png"));
	public final static ImageIcon test = new ImageIcon(UIConstant.class.getResource("/image/background/dialogue.png"));
	public final static ImageIcon batman = new ImageIcon(UIConstant.class.getResource("/image/label/bat.png"));
	public final static ImageIcon info = new ImageIcon(UIConstant.class.getResource("/image/button/info.png"));
	public final static ImageIcon infoBorder = new ImageIcon(
			UIConstant.class.getResource("/image/button/infoborder.png"));
	public final static ImageIcon infoGray = new ImageIcon(UIConstant.class.getResource("/image/button/infor.png"));

	/**
	 * Font
	 */
	public final static Font tenFont = new Font("微软雅黑", Font.PLAIN, 10);

	/**
	 * set the special color text
	 * 
	 * @param nowData
	 * @param previousData
	 * @param label
	 */
	public static void setColorText(double nowData, double previousData, JLabel label) {
		String temp = "";
		// if(nowData>previousData){
		// label.setForeground(MyColor.redLine);
		// }else if (nowData<previousData){
		// label.setForeground(MyColor.greenLine);
		// }else{
		// label.setForeground(Color.black);
		// }
		if (nowData > 10000) {
			temp = String.format("%.2f", nowData * 1.0 / 10000) + "万";
		} else {
			temp = String.format("%.2f", nowData);
		}
		label.setText(temp);
	}

	/**
	 * 更改默认
	 */
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
