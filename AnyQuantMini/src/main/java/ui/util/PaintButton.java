/**
 * 
 */
package ui.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class PaintButton extends JButton {

	private Color BUTTON_COLOR1;
	private Color BUTTON_COLOR2;
	private Color BUTTON_BAK_COLOR1;
	private Color BUTTON_BAK_COLOR2;
	private boolean hover;

	public PaintButton() {
		this("");
	}

	public PaintButton(String text) {
		super(text);
		BUTTON_COLOR1 = new Color(180, 230, 250);
		BUTTON_COLOR2 = new Color(255, 250, 205);
		BUTTON_BAK_COLOR1 = new Color(255, 250, 205, 179);
		BUTTON_BAK_COLOR2 = new Color(180, 230, 250, 179);
		// 步骤1
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		int h = getHeight() - 5;
		int w = getWidth();
		float tran = 1F;
		if (!hover) {
			tran = 0.7F;
		}
		// 步骤2
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// GradientPaint是Java2D中专门用来控制渐变的类，它提供了使用线性颜色渐变模式填充 Shape
		// 的方法。其构造函数GradientPaint(float x1, float y1, Color color1, float x2,
		// float y2, Color color2)，充分说明了它的作用，即从点(x1,y1)到点(x2,y2)进行渐变。如果在用户空间指定了
		// Point P1 的 Color 为 C1，Point P2 的 Color 为 C2，则 P1、P2 连接线上的 Color 是逐渐地从
		// C1 变化到 C2 的。
		GradientPaint p1;
		GradientPaint p2;
		if (getModel().isPressed()) {
			p1 = new GradientPaint(0, 4, new Color(0, 0, 0), 0, h - 1, new Color(100, 100, 100));
			p2 = new GradientPaint(0, 5, new Color(0, 0, 0, 50), 0, h - 3, new Color(255, 255, 255, 100));
		} else {
			p1 = new GradientPaint(0, 4, new Color(100, 100, 100), 0, h - 1, new Color(0, 0, 0));
			p2 = new GradientPaint(0, 5, new Color(255, 255, 255, 100), 0, h - 3, new Color(0, 0, 0, 50));
		}
		// 设置透明度
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tran));
		Shape clip = g2d.getClip();
		// 绘制整个按钮
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 4, w - 1, h - 1, h, h);
		g2d.clip(r2d);
		GradientPaint gp = new GradientPaint(0.0F, 4.0F, BUTTON_COLOR1, 0.0F, h, BUTTON_COLOR2, true);
		g2d.setPaint(gp);
		g2d.fillRect(0, 4, w, h);
		// 鼠标移入就绘制立体效果
		if (hover) {
			RoundRectangle2D.Float r2d2 = new RoundRectangle2D.Float(5, 6, w - 10, h / 2 - 1, h / 2, h / 2);
			g2d.clip(r2d2);
			GradientPaint gp2 = new GradientPaint(0.0F, 4.0F, BUTTON_BAK_COLOR2, 0.0F, h / 2, BUTTON_BAK_COLOR1, true);
			g2d.setPaint(gp2);
			g2d.fillRect(5, 6, w - 10, h / 2);
		}
		g2d.setClip(clip);
		// 绘制边框
		g2d.setPaint(p1);
		g2d.drawRoundRect(0, 4, w - 1, h - 1, h, h);
		g2d.setPaint(p2);
		g2d.drawRoundRect(1, 5, w - 3, h - 3, h - 2, h - 2);
		g2d.dispose();
		super.paintComponent(g);
	}

	public void setHover(boolean isHover) {
		this.hover = isHover;
	}

}