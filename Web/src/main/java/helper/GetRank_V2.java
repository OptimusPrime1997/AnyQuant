package helper;

import data.ScoreDataImp;
import dataservice.ScoreService;
import strategy.getRank;
import utility.exception.NotFoundModel_exception;

public class GetRank_V2 implements getRank {

	@Override
	public int getRanks(String id) throws NotFoundModel_exception {
		ScoreService service = new ScoreDataImp();
		return (int) service.getScore(id);
	}

}
