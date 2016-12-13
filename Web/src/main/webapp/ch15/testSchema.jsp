<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TestShema</title>
<script language="JavaScript">
	function show() {
		var name = document.myform.stockId.value + ","
				+ document.myform.date.value + ","
				+ document.myform.manyDay.value + "!"
		document.write(name);
		alert(name);
		//alert(document.myform.stockId.value + "," + document.myform.date.value + ","
		//		+ document.myform.manyDay.value + "!");
	}
</script>
</head>
<body>
	<h2>测试程序</h2>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<%
		ArrayList<Stock> stockList = (ArrayList<Stock>) request.getAttribute("stockList");
		if (stockList != null) {
			Iterator<Stock> t = stockList.iterator();
			while (t.hasNext()) {
	%>
	<%="+-+-+" + t.next()%>
	<%
		}
		}
	%>
	<form action="ShowBenchServlet?stockId=hs300&date=2016-05-05&manyDay=20"
		method="post" name="theForm">
		<br> <input
			type="submit" value="提交">
		</form>
	<!-- <form action="ShowBenchServlet" method="post" name="myform">
		<br>股票名称：<input type="text" name="stockId"><br> <br>截止日期:<input
			type="text" name="date"><br> <br>天数：<input
			type="text" name="manyDay"><br> <br> <input
			type="submit" value="提交">
	</form> -->


</body>
</html>