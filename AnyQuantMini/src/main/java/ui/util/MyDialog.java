/**
 * 
 */
package ui.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;


import ui.large.GBC;
import ui.large.Large;

/**
 * @author bismuth
 *
 */
public class MyDialog extends JDialog {

	private Point location;
	MyPanel panel;
	JTextArea textArea = new JTextArea();
	Dimension dimension = new Dimension(300, 300);
	JButton okButton = new JButton("好的");

	public MyDialog(String content) {
		setUndecorated(true);
		setLayout(new GridBagLayout());
		panel = new MyPanel(UIConstant.direction.getImage());
		panel.setLayout(new GridBagLayout());
		setLayout(new GridBagLayout());
		textArea.setText("\n"+content);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setPreferredSize(dimension);
		textArea.setEditable(false);
		panel.setLayout(new GridBagLayout());
		panel.add(textArea, new GBC(0, 0).setFill(GBC.BOTH));
		
		add(panel, new GBC(0, 0).setFill(GBC.BOTH));
		location = Large.changePanel.getLocationOnScreen();
		int x = location.x;
		int y = location.y;
		setLocation(x + 300, y + 200);
		setSize(dimension);
		setMaximumSize(dimension);
		setMinimumSize(dimension);
		setVisible(true);
		
		textArea.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				dispose();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}
}
