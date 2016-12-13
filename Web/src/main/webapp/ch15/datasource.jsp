<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AnyQuant,股票信息系统</title>
</head>
<body>
	<%
		String DSNAME = "java:comp/env/jdbc/stock";
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup(DSNAME);
		Connection conn = ds.getConnection();
	%>
	<%=conn%>
	<%
		conn.close();
	%>

</body>
</html>