package ui.small;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ui.large.Large;
import ui.util.MyColor;
import ui.util.MyPasswordField;
import ui.util.MyTextField;
import ui.util.PaintButton;
import ui.util.PlainButton;
import ui.util.UIConstant;
import utility.exception.ExistName_exception;
import utility.exception.NoName_exception;
import utility.exception.NoPwd_exception;
import utility.exception.NotSamePwd_exception;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import blservice.loginBLService;
import bl.loginBL.*;

public class RegisterPanel extends JPanel {
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JButton jb;
	private PlainButton miniRegistjb;
	private PlainButton exitRegistjb;

	public RegisterPanel(final JFrame frame, final JButton headLabel) {
		final RegisterPanel rp = this;
		setLayout(null);
		
		miniRegistjb=new PlainButton(UIConstant.mini, UIConstant.miniBorder);
		miniRegistjb.setBounds(frame.getWidth()-60,0,30,30);
		miniRegistjb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});
		add(miniRegistjb);
		
		exitRegistjb=new PlainButton(UIConstant.exit, UIConstant.exitBorder);
		exitRegistjb.setBounds(frame.getWidth()-30,0,30,30);
		exitRegistjb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		add(exitRegistjb);

		JLabel lblNewLabel = new JLabel("用户名：");
		lblNewLabel.setBounds(26, 159, 75, 16);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("密码：");
		lblNewLabel_1.setBounds(40, 187, 61, 16);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("确认密码：");
		lblNewLabel_2.setBounds(16, 215, 87, 16);
		add(lblNewLabel_2);

		textField = new MyTextField(20);
		textField.setBounds(102, 153, 134, 28);
		textField.setBackground(MyColor.transparentColor);
		textField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		add(textField);

		jb = new PaintButton("注册");
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginBLService bl = new loginBLController();

				String id = textField.getText();
				char[] password = passwordField.getPassword();
				char[] confirm = passwordField_1.getPassword();
				String pwd = new String(password);
				String cf = new String(confirm);
				try {
					bl.register(id, pwd, cf);
				} catch (ExistName_exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jb, "用户名已存在");
				} catch (NoName_exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jb, "用户名不能为空");
				} catch (NoPwd_exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jb, "密码不能为空");
				} catch (NotSamePwd_exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jb, "密码不一致");
				}

			}
		});
		jb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				LoginPanel lp = new LoginPanel(frame, headLabel);
				frame.remove(rp);
				frame.getContentPane().add(lp);
				frame.repaint();
				frame.setVisible(true);

			}

		});

		jb.setBounds(161, 261, 75, 29);
		add(jb);

		JButton button_1 = new PaintButton("取消");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginPanel lp = new LoginPanel(frame, headLabel);
				frame.remove(rp);
				frame.getContentPane().add(lp);
				frame.repaint();
				frame.setVisible(true);

			}

		});
		button_1.setBounds(30, 261, 75, 29);
		add(button_1);

		JLabel lblNewLabel_3 = new JLabel();
		lblNewLabel_3.setIcon(UIConstant.login);
		lblNewLabel_3.setOpaque(false);
		lblNewLabel_3.setBounds(77, 30, 100, 100);
		add(lblNewLabel_3);

		passwordField = new MyPasswordField(0);
		passwordField.setBounds(102, 181, 134, 28);
		passwordField.setBackground(MyColor.transparentColor);
		passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		add(passwordField);

		passwordField_1 = new MyPasswordField(0);
		passwordField_1.setBounds(102, 209, 134, 28);
		passwordField_1.setBackground(MyColor.transparentColor);
		passwordField_1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		add(passwordField_1);

	}
	@Override
	protected void paintComponent(java.awt.Graphics g) {
		g.drawImage(UIConstant.loginBack.getImage(), 0, 0, UIConstant.loginBack.getIconWidth(),
				UIConstant.loginBack.getIconHeight(), null);
	};

}
