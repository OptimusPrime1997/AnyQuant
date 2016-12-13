/**
 * 
 */
package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataservice.UserDataService;
import model.User;
import utility.exception.ExistID_exception;
import utility.exception.ExistName_exception;
import utility.exception.NotExistId_exception;
import utility.exception.NotFoundName_exception;

/**
 * @author 1
 *
 */
public class UserDataImp implements UserDataService {
	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.UserDataService#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String id) throws NotFoundName_exception {
		// TODO Auto-generated method stub
		User userPO = null;
		String userName = "";
		String userPwd = "";
		ArrayList<String> ids = new ArrayList<String>();
		String sql = "select user.name , user.password , userstock.id from user,userstock where user.name = userstock.name and user.name = ? ;";
		Connection conn = DBUtility.getConn();
		assert(conn!=null):("get the connection of DB failed!");
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			results = statement.executeQuery();
			if (results.next()) {
				do {
					userName = results.getString(1);
					userPwd = results.getString(2);
					ids.add(results.getString(3));
				} while (results.next());
				userPO = new User();
				userPO.setName(userName);
				userPO.setPassword(userPwd);
				userPO.setStocks(ids);
			} else {
				throw new NotFoundName_exception();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closeRs(results);
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return userPO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.UserDataService#regist(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean regist(String name, String password) throws ExistName_exception {
		// TODO Auto-generated method stub
		String sql = "insert into user(name,password) values( ? , ? ) ;";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		boolean flag = false;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, password);
			statement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.UserDataService#add(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean add(String name, String id) throws ExistID_exception, NotFoundName_exception {
		// TODO Auto-generated method stub
		String query = "select count(*) from user where name = ? ";
		Connection conn = DBUtility.getConn();
		int count = 0;
		boolean flag = false;
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (count != 0) {
				String sql = "insert into userstock(name,id) values( ? , ? ) ";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, name);
				statement.setString(2, id);
				statement.executeUpdate();
				flag = true;
			} else {
				throw new NotFoundName_exception();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExistID_exception();
		} finally {
			DBUtility.closeConn(conn);
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.UserDataService#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean remove(String name, String id) throws NotExistId_exception, NotFoundName_exception {
		// TODO Auto-generated method stub
		String sql = "delete from userstock where name = ? and id = ? ";
		Connection conn = DBUtility.getConn();
		PreparedStatement statement = null;
		boolean flag = true;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, id);
			statement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return flag;
	}

	public boolean delete(String name) throws NotFoundName_exception {
		// TODO Auto-generated method stub
		String sql = "delete from user where name = ? ";
		PreparedStatement statement = null;
		Connection conn = DBUtility.getConn();
		boolean flag = false;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			statement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.closePreStmt(statement);
			DBUtility.closeConn(conn);
		}
		return flag;
	}

}
