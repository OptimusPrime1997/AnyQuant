package main;

import java.awt.Dimension;
import ui.large.Large;

/**
 * this is the main method
 * @author run
 *
 */
public class Start {
	public static void main(String[] arg) {
//		changeFont();
		Large main = new Large();
		main.init();
		main.setVisible(true);
		main.setBounds(100, 70, 1080, 650);
		main.setMinimumSize(new Dimension(800, 600));
		main.setMaximumSize(new Dimension(1100, 700));
		main.toBack();
	}
	
}
