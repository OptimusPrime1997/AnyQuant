<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%!int count = 0;

	int f() {
		return count;
	}%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>chap4-1</title>
</head>
<body>
	<!-- java程序片段 -->
	<%
		count++;
	%>
	<p>变量count的值为：
		<%=f()%><br>
	</p>
	<!--jsp:include page="hello.jsp"></body>-->
</html>