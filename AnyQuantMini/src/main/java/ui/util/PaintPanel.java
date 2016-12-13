/**
 * 
 */
package ui.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

/**
 * @author bismuth
 *
 */
public class PaintPanel extends JPanel{
	
	private Color PANEL_COLOR1;
	private Color PANEL_COLOR2;
	
	public PaintPanel(Color a, Color b){
		PANEL_COLOR1 = a;
		PANEL_COLOR2 = b;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g.create();
		int h = getHeight();
		int w = getWidth();
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w, h, 0, 0);
		g2d.clip(r2d);
		GradientPaint gp = new GradientPaint(0.0F, 0.0F, PANEL_COLOR1, w, h, PANEL_COLOR2, true);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
		
	}
}
