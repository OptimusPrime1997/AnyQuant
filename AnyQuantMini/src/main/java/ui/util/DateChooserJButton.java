package ui.util;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.xml.crypto.Data;

import ui.large.Large;
import ui.large.Market;
import ui.large.Select;
import ui.large.industry.IndustryDetail;
import ui.large.single.SingleCompare;
import ui.large.single.SingleDetail;
import utility.MyDate;

import javax.swing.event.ChangeEvent;
import javax.swing.border.LineBorder;

public class DateChooserJButton extends PaintButton {

	private DateChooser dateChooser = null;

	private Calendar calen;

	/**
	 * get the choosed calendar
	 * 
	 * @return
	 */
	public Calendar getCalen() {
		return calen;
	}

	public void setCalen(Calendar calen) {
		this.calen = calen;
	}

	/**
	 * get the duration
	 * 
	 * @return
	 */
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * get the last date
	 * 
	 * @return
	 */
	public MyDate getChartDate() {
		return new MyDate(getCalen());
	}

	private static int duration = DefaultData.Duration;

	public DateChooserJButton() {
		this("", getNowDate(), duration);
	}

	public DateChooserJButton(String preLabel, Date date, int dur) {
		super();

		setDuration(dur);
		setDate(date, duration);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		super.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dateChooser == null) {
					dateChooser = new DateChooser();
				}
				Point p = getLocationOnScreen();
				p.y = p.y + 30;
				dateChooser.showDateChooser(p);
			}
		});
	}

	private static Date getNowDate() {
		Calendar cal = Calendar.getInstance();
		MyDate date = new MyDate(cal);
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day - 1);
		MyDate t = new MyDate(cal);
		MyDate temp = t.getRecentDate();
		Calendar cal2 = temp.getCalendar();
		return cal2.getTime();
	}

	private static SimpleDateFormat getDefaultDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd"); // 按钮显示的日期格式
	}

	// 覆盖父类的方法
	@Override
	public void setText(String s) {
		Date date;
		try {
			date = getDefaultDateFormat().parse(s);
		} catch (ParseException e) {
			date = new Date(2007, 01, 01);
		}
		setDate(date, duration);
	}

	public void setText(SimpleDateFormat df, String s) {
		Date date;
		try {
			date = df.parse(s);
		} catch (ParseException e) {
			date = new Date(2006, 01, 01);
		}
		setDate(date, duration);
	}

	public void setDate(Date date, int d) {
		// System.out.println("set the date:" + date.toString());

		super.setText(getDefaultDateFormat().format(date) + " " + d + "天");
		Calendar cal = Calendar.getInstance();
		// System.out.println("-------Date:"+date.getDate());
		cal.set(date.getYear() + 1900, date.getMonth(), date.getDate());
		this.setCalen(cal);
	}

	public Date getDate() {
		String dateString = getText().substring(0);
		try {
			return getDefaultDateFormat().parse(dateString);
		} catch (ParseException e) {
			return new Date(2006, 02, 01);
		}
	}

	// 覆盖父类的方法使之无效
	public void addActionListener(ActionListener listener) {
	}

	private class DateChooser extends JPanel implements ActionListener, ChangeListener {
		int startYear = 2006; // 默认【最小】显示年份

		int lastYear = getCalen().get(Calendar.YEAR) + 1900; // 默认【最大】显示年份

		int width = 250; // 界面宽度

		int height = 200; // 界面高度

		Color backGroundColor = Color.gray; // 底色
		// 月历表格配色----------------//

		Color palletTableColor = Color.white; // 日历表底色

		Color todayBackColor = Color.orange; // 今天背景色

		Color weekFontColor = Color.blue; // 星期文字色

		Color dateFontColor = Color.black; // 日期文字色

		Color weekendFontColor = Color.red; // 周末文字色

		// 控制条配色------------------//
		Color controlLineColor = Color.pink; // 控制条底色

		Color controlTextColor = Color.white; // 控制条标签文字色

		Color rbFontColor = Color.white; // RoundBox文字色

		Color rbBorderColor = Color.red; // RoundBox边框色

		Color rbButtonColor = Color.pink; // RoundBox按钮色

		Color rbBtFontColor = Color.red; // RoundBox按钮文字色

		JDialog dialog;

		JSpinner yearSpin;

		JSpinner monthSpin;

		JSpinner durationSpin;

		JButton[][] daysButton = new JButton[6][7];

		PlainButton exitbt;

		DateChooser() {

			setLayout(new BorderLayout());
			setBorder(new LineBorder(backGroundColor, 2));
			setBackground(new Color(1, 140, 200, 200));

			JPanel topYearAndMonth = createYearAndMonthPanal();
			add(topYearAndMonth, BorderLayout.NORTH);
			JPanel centerWeekAndDay = createWeekAndDayPanal();
			add(centerWeekAndDay, BorderLayout.CENTER);
		}

		// @Override
		// public void paintComponent(Graphics g) {
		// g.drawImage(UIConstant.calendarBack.getImage(), 0, 0,
		// UIConstant.calendarBack.getIconWidth(),
		// UIConstant.calendarBack.getIconHeight(), null);
		// }

		private JPanel createYearAndMonthPanal() {
			Calendar c = getCalendar();
			int currentYear = c.get(Calendar.YEAR);
			int currentMonth = c.get(Calendar.MONTH) + 1;
			int dur = duration;
			JPanel result = new JPanel();
			result.setLayout(new FlowLayout());
			result.setBackground(controlLineColor);

			yearSpin = new JSpinner(new SpinnerNumberModel(currentYear, startYear, lastYear, 1));
			yearSpin.setPreferredSize(new Dimension(65, 20));
			yearSpin.setName("Year");
			yearSpin.setEditor(new JSpinner.NumberEditor(yearSpin, "####"));
			yearSpin.addChangeListener(this);
			result.add(yearSpin);

			JLabel yearLabel = new JLabel("年");
			// yearLabel.setFont(UIConstant.tenFont);
			yearLabel.setForeground(controlTextColor);
			// result.add(yearLabel);

			monthSpin = new JSpinner(new SpinnerNumberModel(currentMonth, 1, 12, 1));
			monthSpin.setPreferredSize(new Dimension(50, 20));
			monthSpin.setName("Month");
			monthSpin.addChangeListener(this);
			result.add(monthSpin);

			JLabel monthLabel = new JLabel("月");
			// monthLabel.setFont(UIConstant.tenFont);
			monthLabel.setForeground(controlTextColor);
			// result.add(monthLabel);

			durationSpin = new JSpinner(new SpinnerNumberModel(dur, 60, 150, 5));
			durationSpin.setPreferredSize(new Dimension(50, 20));
			durationSpin.setName("Hour");
			durationSpin.addChangeListener(this);
			result.add(durationSpin);

			JLabel hourLabel = new JLabel("天");
			// hourLabel.setFont(UIConstant.tenFont);
			hourLabel.setForeground(controlTextColor);
			// result.add(hourLabel);

			exitbt = new PlainButton(UIConstant.exit, UIConstant.exitBorder);
			exitbt.setPreferredSize(new Dimension(20, 20));
			exitbt.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					dialog.dispose();
				}
			});
			result.add(exitbt);

			return result;
		}

		private JPanel createWeekAndDayPanal() {
			String colname[] = { "日", "一", "二", "三", "四", "五", "六" };
			JPanel result = new JPanel();
			// 设置固定字体，以免调用环境改变影响界面美观
			result.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			result.setLayout(new GridLayout(7, 7));
			result.setBackground(Color.white);
			JLabel cell;

			for (int i = 0; i < 7; i++) {
				cell = new JLabel(colname[i]);
				cell.setHorizontalAlignment(JLabel.RIGHT);
				if (i == 0 || i == 6) {
					cell.setForeground(weekendFontColor);
					cell.setEnabled(false);
				} else {
					cell.setForeground(weekFontColor);
				}
				result.add(cell);
			}

			int actionCommandId = 0;
			for (int i = 0; i < 6; i++)
				for (int j = 0; j < 7; j++) {
					JButton numberButton = new JButton();
					numberButton.setBorder(null);
					numberButton.setHorizontalAlignment(SwingConstants.RIGHT);
					numberButton.setActionCommand(String.valueOf(actionCommandId));
					numberButton.addActionListener(this);
					numberButton.setBackground(palletTableColor);
					numberButton.setForeground(dateFontColor);
					if (j == 0 || j == 6) {
						numberButton.setForeground(weekendFontColor);
						numberButton.setEnabled(false);
					} else {
						numberButton.setForeground(dateFontColor);
					}
					daysButton[i][j] = numberButton;
					result.add(numberButton);
					actionCommandId++;
				}
			return result;
		}

		private JDialog createDialog(JFrame owner) {
			JDialog result = new JDialog(owner, "日期选择", true);
			result.setUndecorated(true);
			result.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			result.getContentPane().add(this, BorderLayout.CENTER);
			result.pack();
			result.setSize(width, height);
			result.setFocusable(true);
			DateChooserJButton.this.setFocusable(true);
			return result;
		}

		private void showDateChooser(Point position) {
			JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(DateChooserJButton.this);
			if (dialog == null || dialog.getOwner() != owner)
				dialog = createDialog(owner);
			dialog.setLocation(getAppropriateLocation(owner, position));
			flushWeekAndDay();
			dialog.show();
		}

		Point getAppropriateLocation(Frame owner, Point position) {
			Point result = new Point(position);
			Point p = owner.getLocation();
			int offsetX = (position.x + width) - (p.x + owner.getWidth());
			int offsetY = (position.y + height) - (p.y + owner.getHeight());

			if (offsetX > 0) {
				result.x -= offsetX;
			}

			if (offsetY > 0) {
				result.y -= offsetY;
			}
			return result;
		}

		private Calendar getCalendar() {
			Calendar result = Calendar.getInstance();
			result.setTime(getDate());
			return result;
		}

		private int getSelectedYear() {
			return ((Integer) yearSpin.getValue()).intValue();
		}

		private int getSelectedMonth() {
			return ((Integer) monthSpin.getValue()).intValue();
		}

		private int getSelectedHour() {
			return ((Integer) durationSpin.getValue()).intValue();
		}

		private void dayColorUpdate(boolean isOldDay) {
			Calendar c = getCalendar();
			int day = c.get(Calendar.DAY_OF_MONTH);
			c.set(Calendar.DAY_OF_MONTH, 1);
			int actionCommandId = day - 2 + c.get(Calendar.DAY_OF_WEEK);
			int i = actionCommandId / 7;
			int j = actionCommandId % 7;

			if (j == 0 || j == 6) {
				daysButton[i][j].setForeground(weekendFontColor);
				daysButton[i][j].setEnabled(false);
			} else {
				if (isOldDay) {
					daysButton[i][j].setForeground(dateFontColor);
				} else {
					daysButton[i][j].setForeground(todayBackColor);
				}
			}
		}

		private void flushWeekAndDay() {
			Calendar c = getCalendar();
			c.set(Calendar.DAY_OF_MONTH, 1);
			int maxDayNo = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dayNo = 2 - c.get(Calendar.DAY_OF_WEEK);
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					String s = "";
					if (dayNo >= 1 && dayNo <= maxDayNo)
						s = String.valueOf(dayNo);
					daysButton[i][j].setText(s);
					dayNo++;
				}
			}
			dayColorUpdate(false);
		}

		public void stateChanged(ChangeEvent e) {
			JSpinner source = (JSpinner) e.getSource();
			Calendar c = getCalendar();

			if (source.getName().equals("Hour")) {
				duration = (Integer) durationSpin.getValue();
				setDate(c.getTime(), duration);
				return;
			}
			dayColorUpdate(true);

			if (source.getName().equals("Year")) {
				c.set(Calendar.YEAR, getSelectedYear());
			} else {
				c.set(Calendar.MONTH, getSelectedMonth() - 1);
			}
			setDate(c.getTime(), duration);
			flushWeekAndDay();
		}

		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if (source.getText().length() == 0) {
				return;
			} else {
				int newDay = Integer.parseInt(source.getText());
				// newDay++;
				Calendar c = getCalendar();
				c.set(Calendar.DAY_OF_MONTH, newDay);
				Calendar nowCal = Calendar.getInstance();
				nowCal.set(Calendar.DATE, nowCal.get(Calendar.DATE) - 1);
				if (c.compareTo(nowCal) > 0) {
					return;
				}
				dayColorUpdate(true);
				source.setForeground(todayBackColor);
				setDate(c.getTime(), duration);
				switch (Large.info) {
				case market:
					Market.updateChartPanel(DateChooserJButton.this);
					break;
				case singleDetail:
					SingleDetail.updateChartPanel(DateChooserJButton.this);
					break;
				case singleCompare:
					SingleCompare.updateChartPanel(DateChooserJButton.this);
					break;
				case industryDetail:
					IndustryDetail.updateChartPanel(DateChooserJButton.this);
					break;
				default:
					break;
				}
				flushWeekAndDay();
				dayColorUpdate(true);
				source.setForeground(todayBackColor);
				dialog.dispose();

				setHover(false);
				// source.updateUI();
				// source.repaint();
				// source.validate();
			}
		}

	}

}
