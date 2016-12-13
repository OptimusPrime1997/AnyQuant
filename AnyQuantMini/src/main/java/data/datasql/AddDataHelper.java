/**
 * 
 */
package data.datasql;

import java.sql.SQLException;
import java.util.Calendar;

import data.DataFactory;
import utility.Constants;
import utility.MyDate;
import utility.Range_Date;

/**
 * @author 1
 *
 */
public class AddDataHelper {

	private AddData addData;

	public AddDataHelper() {
		// TODO Auto-generated constructor stub
		addData = new AddData();
	}

	public AddData getAddData() {
		return addData;
	}

	public void addAllDataHelper() {
		Range_Date dateRange = new Range_Date(new MyDate("2016-05-06"), new MyDate("2016-06-08"));
		try {
			addData.addAllData(dateRange);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DataFactory.closeConnection();
		}
	}

	public void addNewDataHelper() {
		try {
			addData.addNewData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DataFactory.closeConnection();
		}
	}

	public static void main(String[] args) {
		AddDataHelper helper = new AddDataHelper();
		// helper.getAddData().addStockChoosed();
//		helper.addAllDataHelper();
		 helper.addNewDataHelper();
		 DataFactory.closeConnection();
	
		
//		System.out.println((new MyDate(2016,6,9).compareTo(new MyDate(2016,6,8))+""));
		
//		MyDate date=new MyDate(2016,5,6);
//		java.sql.Date date2=date.getSqlDate();
//		MyDate date3=new MyDate(date2.getYear()+1900,date2.getMonth()+1,date2.getDate());
//		System.out.println("sqlDate:"+date2.toString()+"myDate:"+date.toString()+"date3:"+date3.toString());
	}
}
