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
public class CompareHelper {
	Compare var;
	Compare exp;

	public CompareHelper() {
		var = new Compare_Var();
		exp = new Compare_Exp();
	}

	public String getCompareInfo(ArrayList<ArrayList<StockPO>> array) throws IOException {
		String s = "";
		// CompareInfo varinfo_Max = var.Compare_Max(array);
		// CompareInfo varinfo_Min = var.Compare_Min(array);
		CompareInfo expinfo_Max = exp.Compare_Max(array);
		CompareInfo expinfo_Min = exp.Compare_Min(array);
		s = "涨跌幅均值最高：" + format(expinfo_Max.getRange()) + "(" + expinfo_Max.getrangeID() + ")" + "\n" + "涨跌幅均值最低："
				+ format(expinfo_Min.getRange()) + "(" + expinfo_Min.getrangeID() + ")" + "\n" + "\n" + "转手率均值最高："
				+ format(expinfo_Max.getTurnover()) + "(" + expinfo_Max.getturnoverID() + ")" + "\n" + "转手率均值最低："
				+ format(expinfo_Min.getTurnover()) + "(" + expinfo_Min.getturnoverID() + ")" + "\n" + "\n" + "市盈率均值最高："
				+ format(expinfo_Max.getPe()) + "(" + expinfo_Max.getpeID() + ")" + "\n" + "市盈率均值最低："
				+ format(expinfo_Min.getPe()) + "(" + expinfo_Min.getpeID() + ")" + "\n" + "\n" + "市净率均值最高："
				+ format(expinfo_Max.getPb()) + "(" + expinfo_Max.getpbID() + ")" + "\n" + "市净率均值最低："
				+ format(expinfo_Min.getPb()) + "(" + expinfo_Min.getpbID() + ")";

		return s;
	}

	String format(double d) {
		return String.format("%.2f", d);
	}

}
