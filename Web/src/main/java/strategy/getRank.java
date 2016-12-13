package strategy;

import utility.exception.NotFoundModel_exception;

public interface getRank {
	/**
	 * @param id
	 * @return get the score
	 */
	public int getRanks(String id) throws NotFoundModel_exception;
}
