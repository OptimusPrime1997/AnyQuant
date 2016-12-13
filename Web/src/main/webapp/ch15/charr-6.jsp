<%@ page language="java" errorPage="chapError.jsp"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*" info="今天是星期几?"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Page Test</title>
</head>
<body>
	<h5>
		<%
			Date date = new Date();
			String s = getServletInfo();
			out.println("<br>" + s);
		%>
		<p>
			今天是<%=date.getMonth() + 1%>月
			<%=date.getDate()%>号，星期<%=date.getDay()%>
		</p>
	</h5>
	<%
		int div1 = 0;
		int div2 = 1;
		out.println("div2/div1=" + div2 / div1);
	%>
</body>
</html>