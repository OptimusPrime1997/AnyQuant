/**
 * 
 */
package ui.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.swing.JPasswordField;

/**
 * @author 1
 *
 */
public class MyPasswordField extends JPasswordField{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyPasswordField(int columns) {
		super(columns);
		setOpaque(false);
		setMargin(new Insets(0, 0, 0, 0));
	}

	@Override
	protected void paintBorder(Graphics g) {
		int h = getHeight();
		int w = getWidth();

		Graphics2D g2d = (Graphics2D) g.create();
		Shape shape = g2d.getClip();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setClip(shape);
		g2d.drawRoundRect(0, 5, w - 2, h - 10, h - 15, h - 15);
		g2d.dispose();
		super.paintBorder(g2d);
	}
	

}
