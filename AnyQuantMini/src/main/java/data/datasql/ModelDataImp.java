/**
 * 
 */
package data.datasql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.DataFactory;
import dataservice.ScoreService;
import utility.exception.ExistModel_exception;
import utility.exception.NotFoundModel_exception;

/**
 * @author 1
 *
 */
public class ModelDataImp implements ScoreService {
	private java.sql.Connection conn;
	private DBUtility dbUtility;

	/**
	 * 
	 */
	public ModelDataImp() {
		// TODO Auto-generated constructor stub
		this.conn = DataFactory.getInstance().getConnection();
		this.dbUtility = DataFactory.getInstance().getDBUtility();
	}

	/*
	 * return -1 the method failed
	 */
	@Override
	public int getScore(String modelId) throws NotFoundModel_exception {
		// TODO Auto-generated method stub
		String sql = "select value from model where modelId = ? ;";
		int result = -1;
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, modelId);
			ResultSet results = dbUtility.dbQuery(statement);
			if (results.next()) {
				result = results.getInt(1);
				results.close();
				statement.close();
			} else {
				results.close();
				statement.close();
				throw new NotFoundModel_exception();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see dataservice.ModelService#insertModel(java.lang.String, double)
//	 */
//	@Override
//	public boolean insertScore(String modelId, int value) throws ExistModel_exception {
//		// TODO Auto-generated method stub
//		String sql = "insert into model(modelId,value) values( ? , ? ) ;";
//		PreparedStatement statement = null;
//		try {
//			statement = conn.prepareStatement(sql);
//			statement.setString(1, modelId);
//			statement.setDouble(2, value);
//			dbUtility.dbUpdate(statement);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.ModelService#updateModel(java.lang.String, double)
	 */
	@Override
	public boolean updateScore(String modelId, int value) throws NotFoundModel_exception {
		// TODO Auto-generated method stub
		String sql = "update model set value = ? where modelId = ? ";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setDouble(1, value);
			statement.setString(2, modelId);
			dbUtility.dbUpdate(statement);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NotFoundModel_exception();
		}
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see dataservice.ModelService#deleteModel(java.lang.String)
//	 */
//	@Override
//	public boolean deleteScore(String modelId) throws NotFoundModel_exception{
//		// TODO Auto-generated method stub
//		String sql = "delete from model where modelId = ? ";
//		PreparedStatement statement = null;
//		try {
//			statement = conn.prepareStatement(sql);
//			statement.setString(1, modelId);
//			dbUtility.dbUpdate(statement);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new NotFoundModel_exception();
//		}
//		return true;
//	}

}
