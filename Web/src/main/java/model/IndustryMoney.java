package model;

import utility.MyDate;

public class IndustryMoney {
	private String industryId;
	private String industryName;
	private MyDate yearMonth;
	private double industryMoney;

	public IndustryMoney() {
		// TODO Auto-generated constructor stub
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getIndustryId() {
		return industryId;
	}

	public MyDate getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(MyDate yearMonth) {
		this.yearMonth = yearMonth;
	}

	public double getIndustryMoney() {
		return industryMoney;
	}

	public void setIndustryMoney(double industryMoney) {
		this.industryMoney = industryMoney;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
}
