/**
 * 
 */
package utility;

import java.io.File;
import java.util.ArrayList;

import ui.util.MyDataset;

/**
 * @author 1
 *
 */
public class Constants {
	/**
	 * header:X-Auth-Code
	 */
	public static final String HEADER = "X-Auth-Code";
	/**
	 * opencode:44895fc229fefbd07cc009a787d554c5
	 */
	public static final String OPENCODE = "44895fc229fefbd07cc009a787d554c5";
	/**
	 * url:http://121.41.106.89:8010/api/
	 */
	public static final String URL = "http://121.41.106.89:8010/api/";
	/**
	 * ?start=
	 */
	public static final String START = "?start=";
	/**
	 * &end=
	 */
	public static final String END = "&end=";
	/**
	 * &fields=
	 */
	public static final String FIELD = "&fields=";
	/**
	 * &
	 */
	public static final String LINK = "&";
	/**
	 * sina api header
	 */
	public static final String SINA = "http://hq.sinajs.cn/list=";
	/**
	 * local stock id file
	 */
	public static final String LOCALPATH = "saveData/stocksID.txt";
	/**
	 * the filepath save the user info
	 */
	public static final String USERPATH = "saveData/user";
	/**
	 * benchmarket hs300
	 */
	public static final String HS300 = "hs300";

	public static final String MaxMinPath = "saveData/maxMin";

	/**
	 * adddata seted start date
	 */
	public static final MyDate STARTDATE = new MyDate("2013-01-01");

	/**
	 * adddata seted end date
	 */
	public static final MyDate TESTDATE = new MyDate("2016-06-08");

	/**
	 * the database driver name
	 */
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	/**
	 * the database ip address
	 */
	public static final String DBIP = "115.159.79.145";
	/**
	 * the database port
	 */
	public static final String DBPORT = "3306";
	/**
	 * the database address front url
	 */
	public static final String DBURL = "jdbc:mysql://";
	/**
	 * the database user name
	 */
	public static final String DBUSER = "root";
	/**
	 * the database password
	 */
	public static final String DBPSW = "JLBuffett47";
	/**
	 * the schema name in the database
	 */
	public static final String DBSCHEMA = "stock";
}
