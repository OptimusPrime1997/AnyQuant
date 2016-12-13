/**
 * 
 */
package ui.util;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author liuyin
 *
 */
public class MyLine extends JPanel{
	private static final long serialVersionUID = 1L;

	public MyLine() {

	}

//   public MyLine(String text) {
//	super(text);
//	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(220,220,220));
		g.drawLine(0, 0, this.getWidth(), 0);
	}

}
