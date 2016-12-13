/**
 * 
 */
package dataservice;

import utility.exception.NotFoundModel_exception;

/**
 * @author 1
 *
 */
public interface ScoreService {
	/**
	 * get the score of stock
	 * @param stockId
	 * @return
	 * @throws NotFoundModel_exception
	 */
	public int getScore(String stockId) throws NotFoundModel_exception;

//	/**
//	 * add the score of stock
//	 * @param stockId
//	 * @param value
//	 * @return
//	 * @throws ExistModel_exception
//	 */
//	public boolean insertScore(String stockId, double value) throws ExistModel_exception;

	/**
	 * update the score of stock
	 * @param stockId
	 * @param value
	 * @return
	 * @throws NotFoundModel_exception
	 */
	public boolean updateScore(String stockId, int value) throws NotFoundModel_exception;

//	/**
//	 * delete the score of stock
//	 * @param stockId
//	 * @return
//	 * @throws NotFoundModel_exception
//	 */
//	public boolean deleteScore(String stockId) throws NotFoundModel_exception;
}
