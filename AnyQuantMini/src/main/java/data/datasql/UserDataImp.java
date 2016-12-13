/**
 * 
 */
package data.datasql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import data.DataFactory;
import dataservice.UserDataService;
import po.UserPO;
import utility.exception.ExistID_exception;
import utility.exception.ExistName_exception;
import utility.exception.NotExistId_exception;
import utility.exception.NotFoundName_exception;

/**
 * @author 1
 *
 */
public class UserDataImp implements UserDataService {
	private java.sql.Connection conn;
	private DBUtility dbUtility;

	/**
	 * 
	 */
	public UserDataImp() {
		// TODO Auto-generated constructor stub
		this.conn = DataFactory.getInstance().getConnection();
		this.dbUtility = DataFactory.getInstance().getDBUtility();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.UserDataService#getUser(java.lang.String)
	 */
	@Override
	public UserPO getUser(String id) throws NotFoundName_exception {
		// TODO Auto-generated method stub
		UserPO userPO = null;
		String userName = "";
		String userPwd = "";
		ArrayList<String> ids = new ArrayList<String>();
		String sql = "select user.name , user.password , userstock.id from user,userstock where user.name = userstock.name and user.name = ? ;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet results = dbUtility.dbQuery(statement);
			if (results.next()) {
				do {
					userName = results.getString(1);
					userPwd = results.getString(2);
					ids.add(results.getString(3));
				} while (results.next());
				userPO = new UserPO(userName, userPwd, ids);
				results.close();
				statement.close();
			} else {
				results.close();
				statement.close();
				throw new NotFoundName_exception();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, password);
			dbUtility.dbUpdate(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			rs = dbUtility.dbQuery(stmt);
			rs.next();
			count = rs.getInt(1);
			rs.close();
			stmt.close();
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
				dbUtility.dbUpdate(statement);
				return true;
			} else {
				throw new NotFoundName_exception();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExistID_exception();
		}
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
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, id);
			dbUtility.dbUpdate(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(String name) throws NotFoundName_exception {
		// TODO Auto-generated method stub
		String sql = "delete from user where name = ? ";
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			dbUtility.dbUpdate(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
