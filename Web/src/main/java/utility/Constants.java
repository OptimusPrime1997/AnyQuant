/**
 * 
 */
package utility;

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
	public static final MyDate TESTDATE = new MyDate("2016-05-07");

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
	/**
	 * find database from datasource
	 */
	public static final String DSNAME="java:comp/env/jdbc/stock";

	
	public static final int MAXMACOUNT=10;
	
	/**
	 * get the industry radar graph data from database
	 */
	public static final String radarSql="select id,name,(avg(turnover)-min(turnover))/(max(turnover)-min(turnover))*100,(avg(volume)-min(volume))/(max(volume)-min(volume))*100,(avg(pe)-min(pe))/(max(pe)-min(pe))*100,(avg(pb)-min(pb))/(max(pb)-min(pb))*100,(avg(adj_price)-min(adj_price))/(max(adj_price)-min(adj_price))*100 from stockdetail where isIndustry=1 and pe >0 and volume>0 and pb>0 and year(date)=? and month(date)=? group by id order by id asc;";
	
	/**
	 * get the industry table data from database
	 */
	public static final String tableSql="select id,name,avg(turnover),avg(volume),avg(pe),avg(pb),avg(adj_price) from stockdetail where isIndustry=1 and pe>0 and pb>0 and volume>0 and year(date)=? and month(date)=? group by id order by id asc;";
	
	/**
	 * the inddustryType array
	 */
	public static final String[] industryArray={"aviation","electInfo","estate","finance","material",/*,"environment","software","petrochemical"*/};
	
	/**
	 * the default days to display
	 */
	public static final int manyDay=20;
	
	/**
	 * 个股的大一点的图用60天的数据展示
	 */
	public static final int singleDay=60;
}
