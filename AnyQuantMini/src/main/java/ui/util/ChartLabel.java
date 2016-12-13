/**
 * 
 */
package ui.util;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * @author bismuth
 *
 */
public class ChartLabel extends JLabel {

	boolean showing;
	private Color show;
	private Color unShow;

	public ChartLabel(Color show, Color unShow, boolean initShow, String name) {
		this.show = show;
		this.unShow = unShow;
		this.setText(name);
		this.setBorder(BorderFactory.createLineBorder(show));
		showing = initShow;
		this.setOpaque(true);
		if (showing) {
			this.setBackground(show);
		} else {
			this.setBackground(unShow);
		}

		this.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mousePressed(java.awt.event.MouseEvent evt) {
				labelMousePressed(evt);
			}

		});
	}

	private void labelMousePressed(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			if (showing) {
				this.setBackground(unShow);
				showing = false;
			} else {
				this.setBackground(show);
				showing = true;
			}
		}

	}

	public boolean ifShowing() {
		return showing;
	}

	public void setShowing(boolean show) {
		showing = show;
	}

	public Color getUnShowingColor() {
		return unShow;
	}
}
