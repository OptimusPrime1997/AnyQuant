package data.datasql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockInfoDataImp {
	public ArrayList<String> getAllStockId() {
		ArrayList<String> allIds = new ArrayList<String>();
		String sql = "select distinct sd.id from stockdetail as sd where sd.id not in (select distinct si.id from stockinfo as si) and sd.id like ?";
		Connection conn = DBMainUtility.getConn();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, "s_______");
			results = statement.executeQuery();
			while (results.next()) {
				allIds.add(results.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBMainUtility.closeRs(results);
			DBMainUtility.closePreStmt(statement);
			DBMainUtility.closeConn(conn);
		}
		return allIds;
	}

	public boolean addStockInfo(String id, String secFullName, String officeAddr, String primeOperation) {
		String sql = "insert into stockinfo(id,secFullName,officeAddr,primeOperation) values( ? , ? , ? , ?);";
		Connection conn = DBMainUtility.getConn();
		PreparedStatement statement = null;
		int rs = -1;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			statement.setString(2, secFullName);
			statement.setString(3, officeAddr);
			statement.setString(4, primeOperation);
			rs = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			DBMainUtility.closePreStmt(statement);
			DBMainUtility.closeConn(conn);
		}
		return true;
	}
}
