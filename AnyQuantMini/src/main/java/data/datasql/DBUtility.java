package data.datasql;

import java.sql.*;
import data.DataFactory;

/**
 * @author 1 database operation
 */
public class DBUtility {
	private Connection conn;
	private ResultSet rs = null;

	public DBUtility() {
		this.conn = DataFactory.getInstance().getConnection();
	}

	public synchronized ResultSet dbQuery(PreparedStatement stmt) {
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Get the query result failed!");
			e.printStackTrace();
		}
		return rs;
	}

	public synchronized int dbUpdate(PreparedStatement stmt) {
		int result = 0;
		try {
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update,insert or delete operation failed!");
			e.printStackTrace();
		}
		try {
			if (!stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void closeResultSet() {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void closeDatabase() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("Close database connection failed!");
				e.printStackTrace();
			}
			conn = null;
		}
	}

	// public void main(String[] args) {
	// rs = databaseQuery("select * from stockdetail;");
	// try {
	// while (rs.next()) {
	// System.out.print(rs.getString("id") + " ");
	// System.out.println(rs.getString("name") + " ");
	// }
	// closeDatabase();
	// } catch (SQLException e) {
	// System.out.println("Test query failed!");
	// e.printStackTrace();
	// }
	// }
}
