<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- this variance declare -->
<%!int count = 0;%>
<%!long fact = 1;

	void calFact(int temp) {
		for (int i = 1; i < temp; i++) {
			fact *= i;
		}
	}

	int x = 5;%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PageCounter</title>
</head>
<body>
	<%-- 这是JSP注释，客户端不可见 --%>
	<H1>This is a HelloWorld JSP page!</H1>
	<%
		out.println("Hello world!");
		//this is java commit
		out.println("这是JAVA注释示例");
	%>
	<%
		count++;
	%>
	<p>
		您是第<%=count%>位访问者！
	</p>
	<p>
		现在的时间为：<%=new java.util.Date()%>
	</p>
	<%
		int s = 0;
		for (int i = 0; i < 10; i++) {
			s += i;
		}
		out.println("s=" + s);
	%>
	<%
		calFact(5);
	%>
	<p>
		5的阶乘是<%=fact%></p>
	<%
		if (x > 0) {
	%>
	<%=x%>
	<%
		} else if (x < 0) {
	%>
	<%=x%>是负数
	<%
		} else {
	%>
	<%=x%>是零
	<%
		}
	%>



</body>
</html>