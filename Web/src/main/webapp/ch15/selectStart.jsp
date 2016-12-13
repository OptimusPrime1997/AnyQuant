<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SelectStart</title>
</head>
<body>
<body onload="sub();">
	<%
		out.println("<h1>Hello world!This the select start page!</h1>");
		session.setAttribute("userName","Anyone");
	%>
	<form
		action="SelectServlet?manyDay=20&reqDate=2016-05-05"
		method="post" name="myPost">
	</form>
	<script>
		function sub() {
			document.myPost.submit();
		}
	</script>

</body>
</body>
</html>