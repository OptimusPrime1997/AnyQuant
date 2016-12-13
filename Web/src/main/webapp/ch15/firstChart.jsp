<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>ECharts</title>
<!-- 引入 echarts.js -->
<script src="http://echarts.baidu.com/dist/echarts.min.js"></script>
</head>
<body>
	<%
		int[] test = (int[]) request.getAttribute("nums");
		if (test == null) {
			int[] test1 = {10, 20, 30, 40, 50, 50};
			test = test1;
		}
	%>
	<form action="../FirstChart?num=6" method="post" name="myForm">
		<input type="button" value="submit" onClick="changeData()">
	</form>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="width: 600px; height: 400px;"></div>
	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		function sub() {
			document.myForm.submit();
		}
		
		var myChart = echarts.init(document.getElementById('main'));
		var volume = new Array();
		
		function changeData(){
			for(var i=0;i<6;i++){
				volume[i]=i*1000;
			}
		}
	<%for (int i = 0; i < 6; i++) {%>
		volume[
	<%=i%>
		] =
	<%=test[i]%>
		;
	<%}%>
		
		// 指定图表的配置项和数据
		var option = {
			title : {
				text : 'ECharts 入门示例'
			},
			tooltip : {},
			legend : {
				data : [ '销量' ]
			},
			xAxis : {
				data : [ "衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子" ]
			},
			yAxis : {},
			series : [ {
				name : '销量',
				type : 'bar',
				data : volume
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	</script>
</body>
</html>