package ui.small;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import bl.loginBL.loginBLController;
import blservice.loginBLService;
import ui.large.Large;
import ui.util.MyColor;
import ui.util.MyPasswordField;
import ui.util.MyTextField;
import ui.util.PaintButton;
import ui.util.PlainButton;
import ui.util.UIConstant;
import utility.exception.NotFoundName_exception;
import utility.exception.WrongPwd_exception;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {
	private JTextField textField;
	private JLabel headimag;
	private JPasswordField passwordField;
	static Point origin = new Point();
	private JButton jb_1;
	private JButton jb_2;
	private static String UserName = "";

	private PlainButton exitLoginjb;
	private PlainButton miniLoginjb;

	/**
	 * Create the panel.
	 */
	public LoginPanel(final JFrame frame, final JButton headLabel) {
		
		final LoginPanel lp = this;
		
		setLayout(null);
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		headimag = new JLabel();
		headimag.setBounds(77, 30, 100, 100);
		headimag.setIcon(UIConstant.login);

		headimag.setBackground(Color.GRAY);

		add(headimag);
		setBackground(new Color(245, 245, 245,100));

		JLabel lblNewLabel = new JLabel("账号：");
		lblNewLabel.setBounds(22, 159, 55, 28);
		add(lblNewLabel);

		miniLoginjb = new PlainButton(UIConstant.mini, UIConstant.miniBorder);
		miniLoginjb.setBounds(frame.getWidth() - 60, 0, 30, 30);
		miniLoginjb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});
		add(miniLoginjb);

		exitLoginjb = new PlainButton(UIConstant.exit, UIConstant.exitBorder);
		exitLoginjb.setBounds(frame.getWidth() - 30, 0, 30, 30);
		exitLoginjb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		add(exitLoginjb);

		textField = new MyTextField(20);
		textField.setBounds(89, 159, 134, 28);
		textField.setBackground(MyColor.transparentColor);
		textField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		add(textField);
//		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("密码：");
		lblNewLabel_1.setBounds(22, 199, 55, 16);
		add(lblNewLabel_1);

		jb_1 = new PaintButton("注册");
		jb_1.setBounds(22, 252, 75, 29);
		jb_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterPanel rp = new RegisterPanel(frame, headLabel);
				frame.remove(lp);
				frame.getContentPane().add(rp);
				frame.repaint();
				frame.setVisible(true);
			}
		});
		add(jb_1);

		jb_2 = new PaintButton("登录");
		jb_2.setBounds(160, 252, 75, 29);
		jb_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				loginBLService bl = new loginBLController();

				String id = textField.getText();
				char[] password = passwordField.getPassword();
				String pwd = new String(password);
				try {
					bl.login(id, pwd);
					frame.setVisible(false);
					UserName = id;
					headLabel.setIcon(UIConstant.header);
				} catch (WrongPwd_exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jb_2, "用户名或密码不正确！");
				} catch (NotFoundName_exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jb_2, "用户名或密码不正确！");
				}

			}
		});
		add(jb_2);

		passwordField = new MyPasswordField(20);
		passwordField.setBackground(MyColor.transparentColor);
		passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		passwordField.setBounds(89, 193, 134, 28);
		add(passwordField);

		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_ENTER)
					passwordField.requestFocus();
			}
		});

		passwordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_UP)
					textField.requestFocus();
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
					jb_2.doClick();
			}
		});

	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		g.drawImage(UIConstant.loginBack.getImage(), 0, 0, UIConstant.loginBack.getIconWidth(),
				UIConstant.loginBack.getIconHeight(), null);
	};

	public static String getUserName() {
		return UserName;
	}
}
