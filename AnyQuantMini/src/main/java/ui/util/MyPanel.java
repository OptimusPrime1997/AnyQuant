/**
 * 
 */
package ui.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.JPanel;

/**
 * @author bismuth
 *
 */
public class MyPanel extends JPanel{
	Image image = null;

	public MyPanel(Image i) {
		image = i;
	}

	// 固定背景图片，允许这个JPanel可以在图片上添加其他组件
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
}
