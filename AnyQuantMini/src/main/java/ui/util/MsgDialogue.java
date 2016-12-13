/**
 * 
 */
package ui.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author 1
 *
 */
public class MsgDialogue extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JFrame jDialog;
	private JPanel panel;
	public MsgDialogue(String str, Point location) {
		super();
		final int width = 200;
		final int height = 150;
		jDialog = new JFrame();
		jDialog.toFront();
		jDialog.setUndecorated(true);
		label = new JLabel(str, JLabel.LEFT);
		label.setFont(new Font("黑体", Font.BOLD, 12));
		label.setOpaque(false);
		label.setBounds(15, 15, 130, 60);
		label.setBackground(new Color(255, 255, 255, 90));

		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				g.drawImage(UIConstant.dialogue.getImage(), 0, 0, width, height, null);
			}
		};

		panel.setBackground(new Color(255, 255, 255, 50));
		panel.setBounds(0, 0, width, height);
		panel.add(label);
		jDialog.add(panel);

		int x = location.x;
		int y = location.y;
		jDialog.toFront();
		jDialog.setBounds(x - width, y - height, width, height);
		jDialog.setMaximumSize(new Dimension(width, height));
		jDialog.setMinimumSize(new Dimension(width, height));
		jDialog.setBackground(new Color(255, 255, 255, 0));
		jDialog.setVisible(true);
	}

	@Override
	public void dispose() {
		jDialog.dispose();
	}

	public static void main(String[] args) {
		// setState("网络连接异常", 1, new Point(600, 600));
//		msg.setVisible(true);
		JFrame frame=new JFrame("A test Frame");
		frame.setUndecorated(true);
		frame.setBounds(0,0,700,700);
		frame.setVisible(true);
		frame.setBackground(new Color(85,125,205,255));
		
		final MsgDialogue msg = new MsgDialogue("网络异常", new Point(500, 500));
//		frame.add(msg);
		
		// final MsgDialogue msgDialogue = new MsgDialogue("just for test", new
		// Point(900, 500));
		msg.disappear(5, msg);

	}

	/**
	 * display the MsgDialogue for time seconds
	 * @param time
	 * @param m
	 */
	public void disappear(final long time, final MsgDialogue m) {
		m.setVisible(true);
		final Runnable setSateTextFieldText = new Runnable() {
			public void run() {
				System.out.println("recycle the frame");
				m.dispose();
				m.removeAll();
				m.removeNotify();
			}
		};
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(setSateTextFieldText, time, time,
				TimeUnit.SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {
				beeperHandle.cancel(true);
			}
		}, time, TimeUnit.SECONDS);
	}

}
