<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
()

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>我的第一个 HTML 页面</title>
<link
	href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet">
<link href="theme.css" rel="stylesheet">
<link href="http://v3.bootcss.com/dist/css/bootstrap-theme.min.css"
	rel="stylesheet">
<script src="http://echarts.baidu.com/dist/echarts.min.js"></script>
<script type="text/javascript" src=tools.js></script>
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				<tton> <a class="navbar-brand" href="#">SCA 系统</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">首页</a><>
				<li><a href="#about">关于</a><>
				<li><a href="#contact">联系我们</a><>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">帮助<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">功能介绍</a><>
						<li><a href="#">使用说明</a><>
						<li role="separator" class="divider"><>
						<li><a href="#">git地址</a><>
					</ul><>
			</ul>
			<form class="navbar-form navbar-right">
				<div class="form-group">
					<input name=sid id=sid type="text" placeholder="股票编号"
						class="form-control">

				</div>
				<script type="text/javascript">
					function searchSId() {
						var sid = document.getElementById("sid").value;
						window.open("hello.jsp?sid=" + sid);
					}
				</script>
				<a onclick="searchSId()" class="btn btn-success" role="button">查询</a>
			</form>
		</div>
		<!--/.nav-collapse -->



	</div>

	</nav>
	<div class="jumbotron">
		<div class="container">
			<div class="col-md-6">
				<h3>沪深股市</h3>
				<h1>10011.12</h1>
				<p style="color: #ff0000">
					&uarr; 123 12.3% <a class="btn btn-primary" href="#" role="button">Learn
						more &raquo;</a>
				</p>
				<%
					Hello hello = new Hello();
					String[] test = {"1", "2", "3", "4", "5", "6"};
					hello.setText(test);
					String[] s = hello.getText();
				%>
			</div>

		</div>
	</div>

	<div id="main" class="col-md-8 col-md-offset-2" style="height: 400px;"></div>
	<script language=javascript type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('main'));
		var str = new Array();
	<%for (int i = 0; i < s.length; i++) {%>
		str[
	<%=i%>
		] =
	<%=s[i]%>
		;
	<%}%>
		// 指定图表的配置项和数据
		var option = {
			title : {
				text : '沪深行情'
			},
			tooltip : {},
			legend : {
				data : str
			},
			xAxis : {
				data : [ "衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子" ]
			},
			yAxis : {},
			series : [ {
				name : '开盘价',
				type : 'line',
				data : [ 5, 20, 36, 10, 10, 20 ]
			}, {
				name : '收盘价',
				type : 'line',
				data : [ 1, 22, 53, 23, 12, 43 ]
			}, {
				name : '最高价',
				type : 'line',
				data : [ 9, 89, 8, 45, 20, 21 ]
			}, {
				name : '最低价',
				type : 'line',
				data : [ 23, 20, 23, 3, 34, 12 ]
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	</script>

	<a href="hello.jsp?a=1&b=2">here</a>

	<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script
		src="http://v3.bootcss.com/assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
<html>