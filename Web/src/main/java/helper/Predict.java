package helper;

import model.MarketState;
import utility.MyDate;
import utility.exception.Init_Fault_exception;

public interface Predict {
	/**
	 * get prediction for 3 days after date
	 * @param id
	 * @param date
	 * @return
	 */
	public MarketState[] getPrediction(String id,MyDate date) throws Init_Fault_exception;

}
