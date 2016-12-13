package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import strategy.getRank;

public class GetRank implements getRank {

	/**
	 * the class to get the rank of a stock datas are stored in txt in the home
	 * dir (only the 30 chosed stocks)
	 */

	/**
	 * all the stock ids being chosed
	 */
	static String[] ids = { "sh600030", "sh600036", "sh600185", "sh600284", "sh600528", "sh600533", "sh600568",
			"sh600615", "sh600678", "sh600696", "sh600816", "sh600999", "sh601166", "sh601519", "sh601688", "sh601788",
			"sh601789", "sz000014", "sz000029", "sz000558", "sz000619", "sz000728", "sz000732", "sz000776", "sz000928",
			"sz002077", "sz002302", "sz002398", "sz002652", "sz002671" };

	/**
	 * the dir where datas are stored
	 */
	static String dir = "range/rank.txt";

	/**
	 * the hash map to store ranks
	 */
	static Map<String, int[]> map = new HashMap<>();

	static boolean inited = false;

	static double u = 0.5;

	static double scale = 0.02;

	/**
	 * init the map work out the rank according to the datas stored
	 */
	void init() {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(GetRank.class.getClassLoader().getResourceAsStream(dir)));
			for (int i = 0; i < ids.length; ++i) {
				String temp = br.readLine();
				String[] s = temp.split(" ");
				int[] v = new int[2];
				v[0] = (int) Double.parseDouble(s[0]);
				v[1] = (int) Double.parseDouble(s[1]);
				map.put(ids[i], v);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * set the true flag
		 */

		inited = true;
	}

	@Override
	public int getRanks(String id) {
		if (!inited) {
			init();
		}
		int a = 0, b = 0;
		a = map.get(id)[0];
		b = map.get(id)[1];
		double temp = a * u + b * (1 - u);
		return (int) (1 / (1 + Math.exp(-temp * scale)) * 100);

	}

}
