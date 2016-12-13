package utility;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

import data.StockDataImp;
import model.Stock;
import utility.exception.NoStockInfo__exception;

public class FileHelper {
	static StockDataImp ser = new StockDataImp();
	static String dir = "/home/jiaorun/workspace/octave/ids/";

	public static void main(String[] args) {
		getData();
	}

	static void getData() {
		ArrayList<String> arr = new ArrayList<String>();
		arr = ser.getLoacalStockIds();

		for (String t : arr) {
			ArrayList<Stock> a = new ArrayList<>();
			MyDate to = new MyDate(Calendar.getInstance());
			MyDate bef = getBef(to, 1000);
			try {
				a = ser.getDataByID(t, new Range_Date(bef, to));
			} catch (NoStockInfo__exception e) {
				// TODO Auto-generated catch block
				System.err.println("error");
				e.printStackTrace();
			}

			try {
				print(a);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("error when store");
				e.printStackTrace();
			}
			System.out.println("end");
		}
	}

	public static void storeID() throws IOException {
		ArrayList<String> arr = new ArrayList<String>();
		arr = ser.getLoacalStockIds();

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir + "localids.txt")));
		for (String s : arr) {
			bw.write(s);
			bw.newLine();
			bw.flush();
		}
		bw.close();
	}

	public static MyDate getBef(MyDate d, int n) {
		MyDate a = d.clone();
		for (int i = 0; i < n; ++i) {
			a = a.beforeDay();
			while (!a.isWorkDay()) {
				a = a.beforeDay();
			}
		}
		return a;

	}

	static void print(ArrayList<Stock> a) throws IOException {
		if (a.size() != 0) {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(dir + a.get(0).getId() + ".txt")));
			for (int i = 0; i < a.size(); ++i) {
				if (a.get(i).getEndprice() != 0) {
					bw.write(i + " " + a.get(i).getEndprice());
					bw.newLine();
					bw.flush();
				}
			}
			bw.close();
		}
	}

}
