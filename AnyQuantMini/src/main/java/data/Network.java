/**
 * 
 */
package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import po.StockPO;
import utility.Constants;
import utility.MyDate;
import utility.enums.Field;
import utility.enums.Market;
import utility.exception.LocalFile_exception;

/**
 * @author 1
 *
 */
public class Network {
	// http://121.41.106.89:8010/api/stocks/?year=2014&exchange=sh这个请求获取2014中上交所的股票列表信息``
	// //
	// http://121.41.106.89:8010/api/stock/sh600000/?start=2014-10-10&end=2015-10-10&fields=open+high+close
	// // # http://121.41.106.89:8010/api/benchmark/all
	// //
	// http://121.41.106.89:8010/api/benchmark/hs300?start=2015-01-01&end=2015-01-30&fields=open+close
	// // # http://121.41.106.89:8010/api/stock/fields

	/**
	 * to get single stock all info from start to end
	 * 
	 * @param id
	 * @param start
	 * @param end
	 * @param field
	 * @return
	 * @throws IOException
	 */
	public ArrayList<StockPO> getSingleStock(String id, MyDate start, MyDate end, Field field) throws IOException {
		if (id.equals("hs300")) {
			return getBenchStock(start, end, field);
		} else {
			String tempId = Market.SHANGHAI.getValue() + id;
			String tempUrl = "";
			tempUrl = Constants.URL + tempId + "/" + Constants.START + start + Constants.END + end + Constants.FIELD
					+ field.getField();
			String str = getJsonString(tempUrl);
			return getStockPO(str, id);
		}
	}

	/**
	 * to get hs300 all stock info from start to end
	 * 
	 * @param id
	 * @param start
	 * @param end
	 * @param field
	 * @return
	 * @throws IOException
	 */
	public ArrayList<StockPO> getBenchStock(MyDate start, MyDate end, Field field) throws IOException {
		String tempUrl = "";
		tempUrl = Constants.URL + Market.ALL.getValue() + Constants.HS300 + Constants.START + start + Constants.END
				+ end + Constants.FIELD + field.getField();
		String str = getJsonString(tempUrl);
		return getBenchStockPO(str, Constants.HS300);
	}

	public ArrayList<String> getStockId(Market market, int year) throws IOException, LocalFile_exception {
		assert (year >= 2006 && year <= Calendar.getInstance().getTime().getTime()) : ("the year out of bounds");
		// if (market == Market.ALL) {
		// String tempUrl = Constants.URL + "stocks/?year=" + year;
		// String str = getJsonString(tempUrl);
		// // System.out.println("url:" + tempUrl);
		// return getIdList(str);
		// } else {
		// String tempUrl = Constants.URL + "stocks/?year=" + year +
		// "&exchange=" + market.getAbbr();
		// String str = getJsonString(tempUrl);
		// // System.out.println("url:" + tempUrl);
		// return getIdList(str);
		// }
		ArrayList<String> stocks = this.getLocalList(Constants.LOCALPATH);
		return stocks;
	}

	public ArrayList<String> getAllStockId() throws IOException {
			String tempUrl = Constants.URL + "stocks/";
			String str = getJsonString(tempUrl);
			return getAllIdList(str);
	}

	public String getStockName(String id) throws IOException {
		if (id.equals("hs300")) {
			return "沪深300";
		} else {
			String temp = getJsonString(Constants.SINA + id);
			String str = temp.replaceAll("\"", ",");
			String[] s = str.split(",");
			if (s.length >= 2) {
				return s[1];
			} else {
				throw new IOException();
			}
		}

	}

	public ArrayList<String> getLocalList(String path) throws LocalFile_exception {
		ArrayList<String> lists = new ArrayList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(
					new InputStreamReader(Network.class.getClassLoader().getResourceAsStream(path)));
			// new FileReader(new
			// File(Network.class.getClassLoader().getResource(path).getFile())));
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				lists.add(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LocalFile_exception();
		}
		return lists;
	}

	public ArrayList<String> getIdList(String str) throws JSONException, IOException {
		JSONObject jsonObject = new JSONObject(str);
		JSONArray childs = jsonObject.getJSONArray("data");

		// System.out.println(childs.toString());

		ArrayList<String> ids = new ArrayList<String>();
		for (int i = 0; i < (childs.length() >= 12 ? 12 : childs.length()); i++) {
			JSONObject object = childs.getJSONObject(i);
			String temp = object.getString("name");

			// System.out.println(temp);

			ids.add(temp);
		}
		return ids;
	}
	
	public ArrayList<String> getAllIdList(String str){
		JSONObject jsonObject = new JSONObject(str);
		JSONArray childs = jsonObject.getJSONArray("data");

		ArrayList<String> ids = new ArrayList<String>();
		for (int i = 0; i < childs.length(); i++) {
			JSONObject object = childs.getJSONObject(i);
			String temp = object.getString("name");
			ids.add(temp);
		}
		return ids;
	}
	

	public static void main(String[] args) {
		Network n = new Network();
		try {
			// n.getIdList("http://121.41.106.89:8010/api/stocks/?");
			n.getStockId(Market.SHANGHAI, 2014);
			String t = n.getStockName("sh600000");
			System.out.println(t);
		} catch (JSONException | IOException | LocalFile_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<StockPO> getStockPO(String str, String id) throws IOException {
		JSONObject jsonObject = new JSONObject(str);
		// String st = jsonObject.getString("status");
		// System.out.println("Status:"+st);
		// System.out.println(jsonObject.get("data").getClass());
		JSONObject childs = jsonObject.getJSONObject("data");
		// System.out.println(childs.toString());
		// System.out.println(childs.get("trading_info").getClass());
		JSONArray info = childs.getJSONArray("trading_info");

		ArrayList<StockPO> stock = new ArrayList<StockPO>();
		for (int i = 0; i < info.length(); i++) {
			jsonObject = info.getJSONObject(i);
			String name = this.getStockName(id);
			double open = jsonObject.getDouble(Field.OPEN.getField());
			double close = jsonObject.getDouble(Field.CLOSE.getField());
			double high = jsonObject.getDouble(Field.HIGH.getField());
			double low = jsonObject.getDouble(Field.LOW.getField());
			double adjprice = jsonObject.getDouble(Field.ADJPRICE.getField());
			long volume = jsonObject.getLong(Field.VOLUME.getField());
			double turnover = jsonObject.getDouble(Field.TURNOVER.getField());
			double pe = jsonObject.getDouble(Field.PE.getField());
			double pb = jsonObject.getDouble(Field.PB.getField());
			MyDate date = new MyDate(jsonObject.getString("date"));
			StockPO tmpStockPO = new StockPO(id, name, open, close, high, low, volume, turnover, pe, pb, adjprice,
					date);
			stock.add(tmpStockPO);
		}
		return stock;
	}

	public ArrayList<StockPO> getBenchStockPO(String str, String id) throws IOException {
		JSONObject jsonObject = new JSONObject(str);
		// String st = jsonObject.getString("status");
		// System.out.println("Status:"+st);
		// System.out.println(jsonObject.get("data").getClass());
		JSONObject childs = jsonObject.getJSONObject("data");
		// System.out.println(childs.toString());
		// System.out.println(childs.get("trading_info").getClass());
		JSONArray info = childs.getJSONArray("trading_info");

		ArrayList<StockPO> stock = new ArrayList<StockPO>();
		for (int i = 0; i < info.length(); i++) {
			jsonObject = info.getJSONObject(i);
			String name = this.getStockName(id);
			double open = jsonObject.getDouble(Field.OPEN.getField());
			double close = jsonObject.getDouble(Field.CLOSE.getField());
			double high = jsonObject.getDouble(Field.HIGH.getField());
			double low = jsonObject.getDouble(Field.LOW.getField());
			double adjprice = jsonObject.getDouble(Field.ADJPRICE.getField());
			long volume = jsonObject.getLong(Field.VOLUME.getField());
			double turnover = 0;
			double pe = 0;
			double pb = 0;
			MyDate date = new MyDate(jsonObject.getString("date"));
			StockPO tmpStockPO = new StockPO(id, name, open, close, high, low, volume, turnover, pe, pb, adjprice,
					date);
			stock.add(tmpStockPO);
		}
		return stock;
	}

	public String getJsonString(String tempUrl) throws IOException {
		URL url = new URL(tempUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty(Constants.HEADER, Constants.OPENCODE);
		connection.setConnectTimeout(10000);
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		// 对应的字符编码转换
		Reader reader = new InputStreamReader(inputStream, "GBK");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String str = null;
		StringBuffer sb = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}
		reader.close();
		connection.disconnect();

		// System.out.println(sb.toString());

		return sb.toString();
	}
}
