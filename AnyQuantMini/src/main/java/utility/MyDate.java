package utility;

import java.io.Serializable;
import java.util.Calendar;

import org.jfree.data.time.Day;

public class MyDate implements Comparable<MyDate>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Calendar calendar;

	/**
	 * this is the construct method
	 * 
	 * @param Year
	 * @param Month
	 * @param Day
	 */
	public MyDate(int Year, int Month, int Day) {
		calendar = Calendar.getInstance();
		calendar.set(Year, Month - 1, Day);
	}
	
	public MyDate getNowDate(){
		calendar=Calendar.getInstance();
		return this.clone();
	}

	public MyDate(String date) {
		this(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)),
				Integer.parseInt(date.substring(8, 10)));
	}

	public MyDate(Calendar c) {
		this.calendar = (Calendar) c.clone();
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	public void setYear(int year) {
		calendar.set(Calendar.YEAR, year);
	}

	public int getMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	public void setMonth(int month) {
		calendar.set(Calendar.MONTH, month - 1);
	}

	public int getDay() {
		return calendar.get(Calendar.DATE);
	}

	public void setDay(int day) {
		calendar.set(Calendar.DATE, day);
	}

	public MyDate beforeDay() {
		int day = calendar.get(Calendar.DATE);
		Calendar c = (Calendar) calendar.clone();
		c.set(Calendar.DATE, day - 1);
		MyDate d = new MyDate(c);
		return d;
	}

	public MyDate afterDay() {
		int day = calendar.get(Calendar.DATE);
		Calendar c = (Calendar) calendar.clone();
		c.set(Calendar.DATE, day + 1);
		MyDate d = new MyDate(c);
		return d;
	}

	public MyDate beforeMonth() {
		int month = calendar.get(Calendar.MONTH);
		Calendar temp = (Calendar) calendar.clone();
		temp.set(Calendar.MONTH, month - 1);
		MyDate d = new MyDate(temp);
		return d;
	}

	public MyDate beforeYear() {
		int year = calendar.get(Calendar.YEAR);
		Calendar temp = (Calendar) calendar.clone();
		temp.set(Calendar.YEAR, year - 1);
		MyDate d = new MyDate(temp);
		return d;
	}

	/**
	 * get the recent open market date
	 * 
	 * @return Date
	 */
	public MyDate getRecentDate() {
		Calendar temp = (Calendar) calendar.clone();
		MyDate date = new MyDate(temp);
		for (; date.isWorkDay() == false;) {
			date = date.beforeDay();
		}
		return date;
	}

	public java.sql.Date getSqlDate() {
		return new java.sql.Date(getYear() - 1900, getMonth() - 1, getDay());
	}

	/**
	 * get the continue dates which open market not good implement
	 * 
	 * @param dates:how
	 *            many dates open market
	 * @return
	 */
	public MyDate getLastDate(int dates) {
		Calendar temp = (Calendar) calendar.clone();
		MyDate date = new MyDate(temp);
		for (int i = 0; i < dates; date = date.beforeDay()) {
			if (date.isWorkDay() == false) {
			} else {
				i++;
			}
		}
		return date;
	}

	/**
	 * to get the date is workday or not not perfect implement
	 * 
	 * @return
	 */
	public boolean isWorkDay() {
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * to get the jfreechart day
	 * 
	 * @return
	 */
	public Day getChartDay() {
		return new Day(this.getDay(), this.getMonth(), this.getYear());
	}

	public static Calendar setCalendar(int year, int month, int date) {
		Calendar cl = Calendar.getInstance();
		cl.set(year, month - 1, date);
		return cl;
	}

	@Override
	public String toString() {
		String y = String.format("%4d", this.getYear()).replace(" ", "0");
		String m = String.format("%2d", this.getMonth()).replace(" ", "0");
		String d = String.format("%2d", this.getDay()).replace(" ", "0");
		return y + "-" + m + "-" + d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MyDate o) {
		// TODO Auto-generated method stub
		return this.getCalendar().compareTo(o.getCalendar());
	}

	public MyDate clone() {
		return new MyDate(this.getYear(), this.getMonth(), this.getDay());
	}

}
