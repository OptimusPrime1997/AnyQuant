<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<script language="JavaScript">
	function validate(f) {
		if (!(/^\w{5,15)$/.test(f.userid.value))) {
			alert("用户ID必须是5-15为！\n");
			f.userid.focus();
			return false;
		}
		if (!(/^\w{5,15)$/.test(f.userpass.value))) {
			alert("密码必须是5-15为！\n");
			f.userid.focus();
			return false;
		}
		return true;
	}
</script>
<body>
	<%
		request.setAttribute("request", "Get request info succeed!");
		session.setAttribute("session", "Get session info succeed!");
		session.setAttribute("stockId", "hs300");
		session.setAttribute("date", "2016-05-05");
	%>

	<h2>用户登录程序</h2>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<%
		List<String> info = (List<String>) request.getAttribute("info");
		if (info != null) {
			Iterator<String> t = info.iterator();
			while (t.hasNext()) {
	%>
	<%=t.next()%>
	<%
		}
		}
	%>
	<%
		List<String> date = (List<String>) session.getAttribute("getDate");
		if (date != null) {
			Iterator<String> t = date.iterator();
			while (t.hasNext()) {
	%>
	<%="------" + t.next()%><br>
	<%
		}
		}
	%>
	<%
		List<Stock> stockList = (List<Stock>) request.getAttribute("stockList");
		if (stockList != null) {
			Iterator<Stock> t = stockList.iterator();
			while (t.hasNext()) {
	%>
	<%="+++++" + t.next()%><br>
	<%
		}
		}
	%>
	<%
		String test = (String) request.getAttribute("test");
		if (test != null) {
	%>
	<%="++++" + test%><br>
	<%
		}
	%>
	<form action="LoginServlet" method="post"
		onSubmit="return Validate(this)">
		用户ID:<input type="hidden" name="userid" value="anyone"><br>
		密&nbsp;&nbsp;码:<input type="hidden" name="userpass" value="123456"><br>
		<input type="submit" value="登录"> <input type="reset"
			value="重置">
	</form>
	<form action="LoginServlet?action=toServlet" method="post" name="form">
		<a href="http://localhost/AnyQuantWeb/ch15/LoginServlet?href=TestHref">click
			me</a> <a
			href="http://localhost/AnyQuantWeb/ch15/LoginServlet?manyDay=20">Print
			stocklist!</a>
</body>
</html>