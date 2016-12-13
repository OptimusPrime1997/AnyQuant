/**
 * 
 */
package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataservice.ScoreService;
import utility.exception.NotFoundModel_exception;

/**
 * @author 1
 *
 */
public class ScoreDataImp implements ScoreService {

	/*
	 * return -1 the method failed
	 */
	@Override
	public int getScore(String stockId) throws NotFoundModel_exception {
		// TODO Auto-generated method stub
		String sql = "select score from stockinfo where id = ? ;";
		int result = -1;
		Connection conn = DBUtility.getConn();

		PreparedStatement statement = DBUtility.getPreSt(conn, sql);
		ResultSet results = null;
		try {
			statement.setString(1, stockId);
			results = DBUtility.getResultSet(statement);
			if (results.next()) {
				result = results.getInt(1);
			} else {
				throw new NotFoundModel_exception();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return result;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see dataservice.ModelService#insertModel(java.lang.String, double)
	// */
	// @Override
	// public boolean insertScore(String stockId, double value) throws
	// ExistModel_exception {
	// // TODO Auto-generated method stub
	// boolean flag = false;
	// String sql = "insert into model(id,value) values( ? , ? ) ;";
	// PreparedStatement statement = null;
	// Connection conn = DBUtility.getConn();
	// try {
	// statement = conn.prepareStatement(sql);
	// statement.setString(1, stockId);
	// statement.setDouble(2, value);
	// statement.executeUpdate();
	// flag = true;
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// DBUtility.closePreStmt(statement);
	// DBUtility.closeConn(conn);
	// }
	// return flag;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.ModelService#updateModel(java.lang.String, double)
	 */
	@Override
	public boolean updateScore(String stockId, int value) throws NotFoundModel_exception {
		// TODO Auto-generated method stub
		String sql = "update stockinfo set score = ? where id = ? ";
		Connection conn = DBMainUtility.getConn();
		PreparedStatement statement = null;
		boolean flag = false;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, value);
			statement.setString(2, stockId);
			statement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NotFoundModel_exception();
		} finally {
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return flag;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see dataservice.ModelService#deleteModel(java.lang.String)
	// */
	// @Override
	// public boolean deleteScore(String stockId) throws NotFoundModel_exception
	// {
	// // TODO Auto-generated method stub
	// boolean flag = false;
	// String sql = "delete from model where stockId = ? ";
	// Connection conn = DBUtility.getConn();
	// PreparedStatement statement = null;
	// try {
	// statement = conn.prepareStatement(sql);
	// statement.setString(1, stockId);
	// statement.executeUpdate();
	// flag = true;
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// throw new NotFoundModel_exception();
	// } finally {
	// DBUtility.closePreStmt(statement);
	// DBUtility.closeConn(conn);
	// }
	// return flag;
	// }

}
