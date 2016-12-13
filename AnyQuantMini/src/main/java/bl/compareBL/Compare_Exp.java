/**
 * 
 */
package bl.compareBL;

import java.io.IOException;
import java.util.ArrayList;

import po.StockPO;

/**
 * @author run
 *
 */
public class Compare_Exp implements Compare {
	Compute c;

	public Compare_Exp() {
		c = new Compute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bl.compareBL.Compare#Compare_Max(java.util.ArrayList)
	 */
	@Override
	public CompareInfo Compare_Max(ArrayList<ArrayList<StockPO>> array) throws IOException {
		double turnover = c.getEX_turnover(array.get(0));
		double range = c.getEX_range(array.get(0));
		double pe = c.getEX_pe(array.get(0));
		double pb = c.getEX_pb(array.get(0));

		String str_turnover = array.get(0).get(0).getName();
		String str_range = array.get(0).get(0).getName();
		String str_pe = array.get(0).get(0).getName();
		String str_pb = array.get(0).get(0).getName();

		for (int i = 0; i < array.size(); ++i) {
			double j = getExp_turnover(array.get(i));
			if (j > turnover) {
				turnover = j;
				str_turnover = array.get(i).get(0).getName();
			}
		}

		for (int i = 0; i < array.size(); ++i) {
			double j = getExp_range(array.get(i));
			if (j > range) {
				range = j;
				str_range = array.get(i).get(0).getName();
			}
		}

		for (int i = 0; i < array.size(); ++i) {

			double j = getExp_pe(array.get(i));
			if (j > pe) {
				pe = j;
				str_pe = array.get(i).get(0).getName();
			}
		}

		for (int i = 0; i < array.size(); ++i) {
			double j = getExp_pb(array.get(i));
			if (j > pb) {
				pb = j;
				str_pb = array.get(i).get(0).getName();
			}
		}
		return new CompareInfo(turnover, str_turnover, range, str_range, pe, str_pe, pb, str_pb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bl.compareBL.Compare#Compare_Min(java.util.ArrayList)
	 */
	@Override
	public CompareInfo Compare_Min(ArrayList<ArrayList<StockPO>> array) throws IOException {
		double turnover = getExp_turnover(array.get(0));
		double range = getExp_range(array.get(0));
		double pe = getExp_pe(array.get(0));
		double pb = getExp_pb(array.get(0));

		String str_turnover = array.get(0).get(0).getName();
		String str_range = array.get(0).get(0).getName();
		String str_pe = array.get(0).get(0).getName();
		String str_pb = array.get(0).get(0).getName();

		for (int i = 0; i < array.size(); ++i) {
			double j = getExp_turnover(array.get(i));
			if (j < turnover) {
				turnover = j;
				str_turnover = array.get(i).get(0).getName();
			}
		}
		for (int i = 0; i < array.size(); ++i) {
			double j = getExp_range(array.get(i));
			if (j < range) {
				range = j;
				str_range = array.get(i).get(0).getName();
			}
		}
		for (int i = 0; i < array.size(); ++i) {
			double j = getExp_pe(array.get(i));
			if (j < pe) {
				pe = j;
				str_pe = array.get(i).get(0).getName();
			}
		}
		for (int i = 0; i < array.size(); ++i) {
			double j = getExp_pb(array.get(i));
			if (j < pb) {
				pb = j;
				str_pb = array.get(i).get(0).getName();
			}
		}
		return new CompareInfo(turnover, str_turnover, range, str_range, pe, str_pe, pb, str_pb);
	}

	double getExp_turnover(ArrayList<StockPO> a) {
		return c.getEX_turnover(a);
	}

	double getExp_range(ArrayList<StockPO> a) throws IOException {
		return c.getEX_range(a);
	}

	double getExp_pe(ArrayList<StockPO> a) {
		return c.getEX_pe(a);
	}

	double getExp_pb(ArrayList<StockPO> a) {
		return c.getEX_pb(a);
	}
}
