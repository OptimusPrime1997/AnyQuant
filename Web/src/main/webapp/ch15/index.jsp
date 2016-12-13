<%@page import="utility.MyDate"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>StartPage</title>
</head>
<body onload="sub();">
	<%
		out.println("<h1>Hello world!This a start page!</h1>");
	%>
	<form
		action="ShowBenchServlet?stockId=hs300&manyDay=20&reqDate=2016-05-05"
		method="post" name="myPost">
		<!-- <input type="hidden" name="reqDate" value="2016-05-05"> -->
	</form>
	<script>
		/* function setValue() {
			var utc = new Date().toJSON().slice(0, 10);
			document.write(utc);
			document.myPost.reqDate = utc;
			document.write(document.myPost.reqDate);
		} */
		function sub() {
			document.myPost.submit();
		}
	</script>

</body>
</html>