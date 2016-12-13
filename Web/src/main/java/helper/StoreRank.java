package helper;

import java.util.ArrayList;

import data.ScoreDataImp;
import data.StockDataImp;
import dataservice.ScoreService;
import utility.exception.NotFoundModel_exception;

/**
 * the class to compute all the stock ranks and store them into the database
 * 
 * @author jiaorun
 *
 */
public class StoreRank {

	/*
	 * we consider using 3 different ranges' data to compute
	 */
	static int NUM = 3;

	/*
	 * a single group has 40 datas
	 */
	static int gap = 40;

	/*
	 * the weight between 3 ranges' data
	 */
	static double[] u = { 0.6, 0.2, 0.2 };

	/*
	 * the weight for MEAN and STD
	 */
	static double[] weight = { 0.4, 0.6 };

	/**
	 * the method to store ranks
	 */
	public static void store() {
		ScoreService score = new ScoreDataImp();
		StockDataImp service = new StockDataImp();
		ArrayList<String> ids = service.getAllStockId();
		double[] MEAN = new double[NUM];
		double[] STD = new double[NUM];

		/*
		 * for every stock
		 */
		for (String s : ids) {
			for (int i = 0; i < NUM; ++i) {
				double[] a = Statistic.getArray(s, gap * (i + 1));
				MEAN[i] = Statistic.mean(a);
				STD[i] = Statistic.std(a);
				double r1 = weight[0] * 100 * sigmoid(multi(u, MEAN));
				double r2 = weight[1] * 100 * sigmoid(multi(u, div(STD, MEAN)));
				try {
					score.updateScore(s, (int) (r1 + r2));
				} catch (NotFoundModel_exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		System.out.println("finish input");
	}

	/*
	 * use the s-function to regular ranks
	 */
	static double sigmoid(double a) {
		return 1 / (1 + Math.exp(-a * 4));
	}

	/*
	 * multi 2 vectors
	 */
	static double multi(double[] a, double[] b) {
		double re = 0;
		for (int i = 0; i < a.length; ++i) {
			re += a[i] * b[i];
		}
		return re;
	}

	/*
	 * div 2 vectors
	 */
	static double[] div(double[] a, double[] b) {
		for (int i = 0; i < a.length; ++i) {
			if (b[i] != 0)
				a[i] = a[i] / b[i];
		}
		return a;
	}

	public static void main(String[] args) {
		StoreRank.store();
		System.out.println("finish");
	}
}
