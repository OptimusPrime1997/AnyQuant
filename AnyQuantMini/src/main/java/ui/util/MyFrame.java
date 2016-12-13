/**
 * 
 */
package ui.util;

/**
 * @author 1
 *
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame {
	/**
	 * just drag the frame,not change the size
	 */
	private Point origin = new Point();

	public MyFrame() {
		// TODO Auto-generated constructor stub
		super();
		setUndecorated(true);

		final JPanel p = (JPanel) getContentPane();
		// p.addMouseMotionListener(new MouseAdapter() {
		// private boolean top = false;
		// private boolean down = false;
		// private boolean left = false;
		// private boolean right = false;
		// private boolean drag = false;
		// private Point lastPoint = null;
		// private Point draggingAnchor = null;
		//
		//// @Override
		//// public void mouseMoved(MouseEvent e) {
		//// if (e.getPoint().getY() == 0) {
		//// setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
		//// top = true;
		//// } else if (Math.abs(e.getPoint().getY() - getSize().getHeight()) <=
		// 1) {
		//// setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
		//// down = true;
		//// } else if (e.getPoint().getX() == 0) {
		//// setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		//// left = true;
		//// } else if (Math.abs(e.getPoint().getX() - getSize().getWidth()) <=
		// 1) {
		//// setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		//// right = true;
		//// } else {
		//// setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		//// draggingAnchor = new Point(e.getX() + p.getX(), e.getY() +
		// p.getY());
		//// top = false;
		//// down = false;
		//// left = false;
		//// right = false;
		//// drag = true;
		//// }
		////
		//// }
		//
		// @Override
		// public void mouseDragged(MouseEvent e) {
		// Dimension dimension = getSize();
		// if (top) {
		//
		// dimension.setSize(dimension.getWidth(), dimension.getHeight() -
		// e.getY());
		// setSize(dimension);
		// setLocation(getLocationOnScreen().x, getLocationOnScreen().y +
		// e.getY());
		// } else if (down) {
		//
		// dimension.setSize(dimension.getWidth(), e.getY());
		// setSize(dimension);
		//
		// } else if (left) {
		//
		// dimension.setSize(dimension.getWidth() - e.getX(),
		// dimension.getHeight());
		// setSize(dimension);
		//
		// setLocation(getLocationOnScreen().x + e.getX(),
		// getLocationOnScreen().y);
		//
		// } else if (right) {
		//
		// dimension.setSize(e.getX(), dimension.getHeight());
		// setSize(dimension);
		// } else {
		// setLocation(e.getLocationOnScreen().x - draggingAnchor.x,
		// e.getLocationOnScreen().y - draggingAnchor.y);
		// }
		// }
		// });
		addMouseListener(new MouseAdapter() {
			// 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
			public void mousePressed(MouseEvent e) {
				// 当鼠标按下的时候获得窗口当前的位置
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			// 拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
			public void mouseDragged(MouseEvent e) {
				// 当鼠标拖动时获取窗口当前位置
				Point p = getLocation();
				// 设置窗口的位置
				// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// @Override
	// public void setMaximumSize(final Dimension d) {
	// addComponentListener(new ComponentAdapter() {
	// public void componentResized(final ComponentEvent e) {
	// int width = e.getComponent().getWidth();
	// int height = e.getComponent().getHeight();
	// if (width > d.getWidth()) {
	// width = (int) d.getWidth();
	// }
	// if (height > d.getHeight()) {
	// height = (int) d.getHeight();
	// }
	// setSize(width, height);
	// }
	// });
	// }

	public static void main(String[] args) {
		JFrame f = new MyFrame();
		f.setSize(300, 300);
		f.setBackground(new Color(85, 125, 205, 85));
		f.setVisible(true);
		// f.setResizable(false);
		f.setMinimumSize(new Dimension(100, 100));
		// f.preferredSize(new Dimension(200, 250));
		f.setMaximumSize(new Dimension(400, 400));
		f.revalidate();
		// label.setMinimumSize(newDim);
		// label.setPreferredSize(newDim);
		// label.setMaximumSize(newDim);
		// label.setSize(newDim);
		// label.revalidate();
	}

}
