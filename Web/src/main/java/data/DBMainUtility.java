package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import utility.Constants;

public class DBMainUtility {

	public static Connection getConn() {
		Connection conn = DBUtility.getConn();
		return conn;
		/*try {
			Class.forName(Constants.DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("load database driver failed!");
			e.printStackTrace();
		}
		try {
			String url = Constants.DBURL + Constants.DBIP + ":" + Constants.DBPORT + "/" + Constants.DBSCHEMA;
			conn = DriverManager.getConnection(url, Constants.DBUSER, Constants.DBPSW);
		} catch (SQLException e) {
			System.out.println("Connect database failed!");
			e.printStackTrace();
		}
		return conn;*/
	}

	public static PreparedStatement getPreSt(Connection conn, String sql) {
		PreparedStatement stmt = null;
		try {
			if (conn != null) {
				stmt = conn.prepareStatement(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}

	public static ResultSet getResultSet(PreparedStatement stmt) {
		ResultSet rs = null;
		try {
			if (stmt != null) {
				rs = stmt.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static int update(PreparedStatement stmt) {
		int result = -1;
		if (stmt != null) {
			try {
				result = stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeRs(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closePreStmt(PreparedStatement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
