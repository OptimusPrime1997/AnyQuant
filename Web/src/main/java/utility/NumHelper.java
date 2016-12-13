package utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumHelper {
	/**
	 * to set the number of digital
	 * 
	 * @param value
	 * @param precesion
	 * @return
	 */
	public static double toFixed(double value, int precesion) {
		assert(!(new Double(value).isNaN())):("value is nan");
		return (new BigDecimal(value)).setScale(precesion, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * parse double from string
	 * @param input
	 * @return
	 */
	public static ArrayList<Double> getDoubles(String input) {
		ArrayList<Double> result = new ArrayList<Double>();
		Pattern p = Pattern.compile("-?\\d+\\.+?\\d+|-?\\d+");
		Matcher m = p.matcher(input);
		while (m.find()) {
			result.add(Double.parseDouble(m.group()));
		}
		return result;
	}
}
