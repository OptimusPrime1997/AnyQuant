<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,utility.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<meta charset="UTF-8">
<title>Strategy</title>
<link href="fullPage/examples/examples.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/styles.css" rel="stylesheet">
<link href="fullPage/examples/examples.css" rel="stylesheet">

<script src="js/funcs.js"></script>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="http://echarts.baidu.com/dist/echarts.min.js"></script>
</head>

</head>
<%
	double[] resultArray = null;
	ArrayList<MyDate> dateArray = null;
	String benchProfit=null;
	
	
	double[] resultArray2 = null;
	ArrayList<MyDate> dateArray2 = null; 
	String benchProfit2=null; 
	
	resultArray = (double[]) request.getAttribute("resultArr");
	dateArray = (ArrayList<MyDate>) request.getAttribute("dateArr");
	benchProfit= (String) request.getAttribute("profitRatio");
	
	resultArray2 = (double[]) request.getAttribute("resultArr2");
	dateArray2 = (ArrayList<MyDate>) request.getAttribute("dateArr2");
	benchProfit2= (String) request.getAttribute("profitRatio2");
	
	boolean getDataSuc = false;
	boolean getDataSuc2=false;
	if (dateArray != null && resultArray != null&&benchProfit!=null) {
		getDataSuc = true;
	}
	if (dateArray2 != null && resultArray2 != null&&benchProfit2!=null) {
		getDataSuc2= true;
	}
%>
<script>

/*
var xmlHttp;
var flag;
function createXMLHttp() {
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
}
function excuteS1() {
	createXMLHttp();
	xmlHttp.open("POST", "StrategyServlet?stockId=" + document
			.getElementById("stockIdS1").value+"&rangeUp="+document
			.getElementById("rangeUpS1").value+"&rangeDown="+document
			.getElementById("rangeDownS1").value+"&buy="+document
			.getElementById("buyS1").value+"&money="+document
			.getElementById("moneyS1").value+"&init="+document
			.getElementById("initS1").value+"&highbound="+document
			.getElementById("highboundS1").value+"&lowbound="+document
			.getElementById("lowboundS1").value+"&endDay="+document
			.getElementById("endDayS1").value+"&sell="+document
			.getElementById("sellS1").value+"&numOfDays="+document
			.getElementById("numOfDaysS1").value);
	xmlHttp.onreadystatechange = checkUseridCallback;
	xmlHttp.send(null);
}
function checkUseridCallback() {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var json = xmlHttp.responseText;
			$('#testMsg').html(json);
			document.getElementById("testMsg").innerHTML=json;
			alert(json);
		}
	}
}
 */

	var resultArr = new Array();
	var dateArr=new Array();
	var profitArr=new Array();
	
	<%if (getDataSuc == true) {%>
		<%for (int i = 0; i < resultArray.length; i++) {%>
			resultArr[
		<%=i%>
			] =
		<%=resultArray[i]%>
			;
		<%}%>
		
		<%for (int j = 0; j < dateArray.size(); j++) {%>
			dateArr[<%=j%>]="<%=dateArray.get(j).toChartString()%>";
<%}%>
		profitArr[0]="<%=benchProfit%>";
		profitArr[0]=parseFloat(profitArr[0]);
		if(resultArr[0]==0){
			profitArr[1]=1.08;
		}else{
			profitArr[1]=( ( (resultArr[resultArr.length-1])/resultArr[0]-1)*100);
			profitArr[1]=parseFloat(profitArr[1]).toFixed(2);
		}
<%}else{%>
	for (var i = 0; i < 10; i++) {
		resultArr[i] = 0;
	}
	dateArr = [ '2016/5/23', '2016/5/24', '2016/5/25', '2016/5/26',
			'2016/5/27', '2016/5/30', '2016/5/31', '2016/6/1', '2016/6/2',
			'2016/6/3' ];
	profitArr[0]=0;
	profitArr[1]=0;
<%}%>


var resultArr2 = new Array();
var dateArr2=new Array();
var profitArr2=new Array();
<%if (getDataSuc2 == true) {%>
	<%for (int i = 0; i < resultArray2.length; i++) {%>
		resultArr2[
	<%=i%>
		] =
	<%=resultArray2[i]%>
		;
	<%}%>
	
	<%for (int j = 0; j < dateArray2.size(); j++) {%>
		dateArr2[<%=j%>]="<%=dateArray2.get(j).toChartString()%>";
<%}%>

profitArr2[0]="<%=benchProfit2%>";
profitArr2[0]=parseFloat(profitArr2[0]);
if(resultArr2[0]==0){
	profitArr2[1]=1.08;
}else{
	profitArr2[1]=( ( (resultArr2[resultArr2.length-1])/resultArr2[0]-1)*100);
	profitArr2[1]=parseFloat(profitArr2[1]).toFixed(2);
}

<%}else{%>
for (var i = 0; i < 10; i++) {
	resultArr2[i] = 0;
}
dateArr2 = [ '2016/5/23', '2016/5/24', '2016/5/25', '2016/5/26',
		'2016/5/27', '2016/5/30', '2016/5/31', '2016/6/1', '2016/6/2',
		'2016/6/3' ];
profitArr2[0]=0;
profitArr2[1]=0;
<%}%>
/* console.log("profitArr2:"+profitArr2); */


	function getMoneyDate1() {
		return dateArr;
	}
	function getMoney1() {
		return resultArr;
	}
	function getComp1(){
		return profitArr;
	}

	function getMoneyDate2() {
		return dateArr2;
		}

	function getMoney2() {
		return resultArr2;
	}
	
	function getComp2(){
		return profitArr2;
	}

	function subForm1() {
		document.getElementById("stockId1").value = document
				.getElementById("stockIdS1").value;
		document.getElementById("rangeUp1").value = document
				.getElementById("rangeUpS1").value;
		document.getElementById("rangeDown1").value = document
				.getElementById("rangeDownS1").value;
		document.getElementById("buy1").value = document
				.getElementById("buyS1").value;
		document.getElementById("money1").value = document
				.getElementById("moneyS1").value;
		document.getElementById("init1").value = document
				.getElementById("initS1").value;
		document.getElementById("highbound1").value = document
				.getElementById("highboundS1").value;
		document.getElementById("lowbound1").value = document
				.getElementById("lowboundS1").value;
		document.getElementById("endDay1").value = document
				.getElementById("endDayS1").value;
		document.getElementById("sell1").value = document
				.getElementById("sellS1").value;
		document.getElementById("numOfDays1").value = document
				.getElementById("numOfDaysS1").value;
		
		document.getElementById("strategyForm1").submit();
	}
	function subForm2() {
		document.getElementById("stockId2").value = document
				.getElementById("stockIdS2").value;
		document.getElementById("buy2").value = document
				.getElementById("buyS2").value;
		
		document.getElementById("money2").value = document
				.getElementById("moneyS2").value;
		document.getElementById("init2").value = document
				.getElementById("initS2").value;
		document.getElementById("highbound2").value = document
				.getElementById("highboundS2").value;
		document.getElementById("lowbound2").value = document
				.getElementById("lowboundS2").value;
		document.getElementById("numOfDays2").value = document
				.getElementById("numOfDaysS2").value;
		
		document.getElementById("endDay2").value = document
				.getElementById("endDayS2").value;
		
		document.getElementById("sell2").value = 10;
			//document.getElementById("sellS2").value;
		document.getElementById("strategyForm2").submit();
	}
	
	function checkForm1() {
		var stockId1 = document.getElementById("stockId1").value;
		var rangeUp1 = document.getElementById("rangeUp1").value;
		var rangeDown1 = document.getElementById("buy1").value;
		var money1 = document.getElementById("money1").value;
		var init1 = document.getElementById("init1").value;
		var highbound1 = document.getElementById("highbound1").value;
		var lowbound1 = document.getElementById("lowbound1").value;
		var endDay1 = document.getElementById("endDay1").value;
		var sell1 = document.getElementById("sell1").value;
		var numOfDays1 = document.getElementById("numOfDays1").value;
		var checkForm1 = false;
		if ((/^\w{8,8}$/.test(stockId1))
				&& (stockId1.substring(0, 2) == 'sh' || stockId1
						.substring(0, 2) == 'sz')) {
			if (rangeUp1<=100&&rangeUp1>= 0) {
				if (rangeDown1 >= 0 && rangeDown1 <= 100) {
					if (money1 >= 0) {
						if (init1 >= 0) {
							if (sell1 > 0) {
								if (highbound1 > 0 && lowbound1 >= 0
										&& highbound1 >= lowbound1) {
									if ((/^\w{10,10}$/.test(endDay1))) {
										if (numOfDays1 > 0) {
											checkForm1 = true;
										}
									}
								}

							}
						}
					}
				}
			}
		}
		return checkForm1;
	}
	function checkForm2() {
		var stockId2 = document.getElementById("stockId2").value;
		var buy2 = document.getElementById("buy2").value;
		var sell2 = document.getElementById("sell2").value;
		var money2 = document.getElementById("money2").value;
		var init2 = document.getElementById("init2").value;
		var highbound2 = document.getElementById("highbound2").value;
		var lowbound2 = document.getElementById("lowbound2").value;
		var numOfDays2 = document.getElementById("numOfDays2").value;
		var endDay2 = document.getElementById("endDay2").value;
		var checkForm2 = false;
		if ((/^\w{8,8}$/.test(stockId2))
				&& (stockId2.substring(0, 2) == 'sh' || stockId2.substring(0, 2) == 'sz')) {
			if (money2 >= 0) {
				if (init2 >= 0) {
					if (highbound2 > 0 && lowbound2 >= 0
							&& highbound2 >= lowbound2) {
						if (numOfDays2 > 0) {
							if ((/^\w{10,10}$/.test(endDay2))) {
								checkForm2 = true;
							}
						}
					}
				}
			}
		}
		return checkForm2;
	}
	function checkStockId(inputId, divId) {
		var stockId = $('#' + inputId).val();
		if ((/^\w{8,8}$/.test(stockId))
				&& (stockId.substring(0, 2) == 'sh' || stockId.substring(0, 2) == 'sz')) {
			if ($('#' + divId).hasClass('has-error')) {
				$('#' + divId).removeClass('has-error');
			}
			if (!($('#' + divId).hasClass('has-success'))) {
				$('#' + divId).addClass('has-success');
			}
		} else {
			if (!($('#' + divId).hasClass('has-error'))) {
				$('#' + divId).addClass('has-error');
			}
			if (($('#' + divId).hasClass('has-success'))) {
				$('#' + divId).removeClass('has-success');
			}
		}
	}
	function choose1(){
		var i=document.getElementById("stra1").value;
		if(i==1){
			diy();
			
		}else{
			defaultStrategy1();
		}
	}
	
	function choose2(){
		var i=document.getElementById("stra2").value;
		if(i==1){
			diy();
			
		}else{
			defaultStrategy2();
		}
	}
	function defaultStrategy1(){
		/* console.log("Test the code!3"); */
document.getElementById("stockIdS1").value="sh600030";
document.getElementById("rangeUpS1").value = "10";
document.getElementById("rangeDownS1").value = "10";
document.getElementById("buyS1").value = "20";
document.getElementById("moneyS1").value = "10000";
document.getElementById("initS1").value = "100";
document.getElementById("highboundS1").value = "15";
document.getElementById("lowboundS1").value = "15";
document.getElementById("endDayS1").value = "2016-05-20";
document.getElementById("sellS1").value = "20";
document.getElementById("numOfDaysS1").value = "100";

//document.getElementById("strategyForm1").submit();
	}
	function defaultStrategy2(){
		/* console.log("Test the code!2"); */
		document.getElementById("stockIdS2").value = "sh600030";
		document.getElementById("buyS2").value = "10";
		
		document.getElementById("moneyS2").value ="10000";
		document.getElementById("initS2").value = "100";
		document.getElementById("highboundS2").value = "15";
		document.getElementById("lowboundS2").value ="15";
		document.getElementById("numOfDaysS2").value = "100";
		
		document.getElementById("endDayS2").value = "2016-05-20";
		
		document.getElementById("sellS2").value = "10";
			//document.getElementById("sellS2").value;
		//document.getElementById("strategyForm2").submit();
	
	}
	function diy(){
		/* console.log("Test the code3!"); */
		document.getElementById("stockIdS1").value = "";
		document.getElementById("rangeUpS1").value = "";
		document.getElementById("rangeDownS1").value = "";
		document.getElementById("buyS1").value = "";
		document.getElementById("moneyS1").value = "";
		document.getElementById("initS1").value = "";
		document.getElementById("highboundS1").value = "";
		document.getElementById("lowboundS1").value = "";
		document.getElementById("endDayS1").value = "";
		document.getElementById("sellS1").value = "";
		document.getElementById("numOfDaysS1").value = "";

		
		document.getElementById("stockIdS2").value = "";
		document.getElementById("buyS2").value = "";
		document.getElementById("moneyS2").value ="";
		document.getElementById("initS2").value = "";
		document.getElementById("highboundS2").value = "";
		document.getElementById("lowboundS2").value ="";
		document.getElementById("numOfDaysS2").value = "";
		document.getElementById("endDayS2").value = "";
		document.getElementById("sellS2").value = "";
	}
</script>


<style>
td {
	vertical-align: top;
}

.form-control {
	width: 150px;
	height: 30px;
}
</style>


<body>
<div id="formContent">
	<form
		action="ShowBenchServlet?stockId=hs300&manyDay=20&reqDate=2016-05-05"
		method="post" name="marketPost" id="marketPost">
	</form>
	<form
		action="SingleServlet?stockId=sh600030&manyDay=20&reqDate=2016-05-05"
		method="post" name="singlePost">
	</form>
	<form
		action="FactoryServlet?stockId=estate&manyDay=20&reqDate=2016-05-05"
		method="post" name="factoryPost">
	</form>
</div>
 	<div id="nav">
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="navbar-header">
				<a class="navbar-brand">AnyQuant</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
                    <li><a onclick="subMarket()">大盘</a></li>
                    <li><a onclick="subSingle()">个股</a></li>
                    <li class="active"><a>策略</a></li>
                    <li><a onclick="subIndustry()">行业</a></li>
                </ul>
			</div>
		</nav>
	</div>
	

	<div id="strategy">
		<div id="up1" style="height: 80px; width: 66%;"></div>
		<div id="topmenu" style="height: 40px; float: top">
			<ul id="menu" style="background-color: white">
				<li><a href="#strategy">策略</a></li>
				<li><a href="#comp2">帮助</a></li>
			</ul>
		</div>
		<div id="head1" style="height: 40px; width: 66%;float: left"> 
			<p id="para"
				style="margin-left: 40%; font-family: ubuntu; font-size: 150%; color: teal">策略一</p>
		</div>
		<div id="up2" style="float:left; margin-left: 20px">

			<div id="up2_in1" style="float: left;">
				<form class="form-inline" role="form">
					<label>股票编号</label> <input type="text" class="form-control"
						id="stockIdS1" style="width: 100px"
						onblur="checkStockId('stockIdS1','up2_in1');">
				</form>				
				<select id="stra1" class="form-control" style="margin-top: 30px;float:right" onclick="choose1()" >
				<option id="op11" onclick="diy()" value="1">自定义</option>
				<option id="op12" onclick="defaultStrategy1()" value="2">模拟实例一</option>
				</select>
			</div>
			<div id="up2_in2"
				style="float: left;margin-left: 50px">
				<table id="in2">
					<tr id="1">
						<td><label for="x11">初始金额</label> <br></td>
						<td><input type="number" class="form-control" id="moneyS1">
							<br></td>

						<td><label for="x11" style="margin-left: 50px">初始数量</label> <br>
						</td>
						<td><input type="number" class="form-control" id="initS1">
							<br></td>
					</tr>

					<tr id="2">
						<td><label for="x11">上涨(%)</label> <br></td>
						<td><input type="number" class="form-control" id="rangeUpS1">
							<br></td>
						<td><label for="x11" style="margin-left: 50px">买入(%)</label> <br>
						</td>
						<td><input type="number" class="form-control" id="buyS1">
							<br></td>
					</tr>

					<tr id="3">
						<td><label for="x11">下降(%)</label> <br></td>
						<td><input type="number" class="form-control"
							id="rangeDownS1"> <br></td>
						<td><label for="x11" style="margin-left: 50px">卖出(%)</label> <br>
						</td>
						<td><input type="number" class="form-control" id="sellS1">
							<br></td>
					</tr>

					<tr id="4">
						<td><label for="x11">测试天数</label> <br></td>
						<td><input type="number" id="numOfDaysS1"
							class="form-control"> <br></td>
						<td><label for="x11" style="margin-left: 50px">截止日期</label> <br>
						</td>
						<td><input type="date" class="form-control" id="endDayS1">
							<br></td>
					</tr>

					<tr id="5">
						<td><label for="x11">上界(%)</label> <br></td>
						<td><input type="number" class="form-control"
							id="highboundS1"> <br></td>
						<td><label for="x11" style="margin-left: 50px">下界(%)</label> <br>
						</td>
						<td><input type="number" class="form-control" id="lowboundS1">
							<br></td>
						<td>
							<button class="btn btn-default btn-sm" onclick="subForm1()"
								id="submit" style="margin-left: 50px; width: 80px">执行</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="up3" style="width: 66%;float: left"> 
			<div id="up3_in1" style="float: left; margin-left: 25px">
				<div class="chart" id="simulate1"
					style="height: 250px;top: 20px;float:top">
					<script type="text/javascript">
							var myChart = echarts.init(document
									.getElementById('simulate1'));
							option1 = {
								title : {
									text : '资产总额变化'
								},
								tooltip : {
									trigger : 'axis'
								},
								dataZoom : {
									show : true,
									realtime : true,
									start : 0,
									end : 100
								},

								toolbox : {
									show : true,
									feature : {
										mark : {
											show : true,
											readOnly : false
										},
										magicType : {
											show : true,
											type : [ 'line', 'bar' ]
										},
										restore : {
											show : true
										},
										saveAsImage : {
											show : true
										}
									}
								},
								calculable : true,
								xAxis : [ {
									type : 'category',
									boundaryGap : false,
									data : getMoneyDate1()
								} ],

								yAxis : [ {
									type : 'value',
									splitArea : {
										show : true
									},
									axisLabel : {
										formatter : '{value}$'
									}
								} ],

								series : [ {
									name : 'money',
									type : 'line',
									data : getMoney1(),
									markPoint : {
										data : [ {
											type : 'max',
											name : 'max money'
										}, {
											type : 'min',
											name : 'min money'
										} ]
									},

									markLine : {
										data : [ {
											type : 'average',
											name : 'average money'
										} ]
									},

								} ]

							}
							myChart.setOption(option1);
					</script>
				</div>
				
				<div class="chart" id="comp1" style="height: 200px;margin-top: 20px">
                 <script type="text/javascript">
	                 var chart_comp1 = echarts.init(document.getElementById('comp1'));
	                 option_comp1={
	                		 title:{
	                			 text:'净盈率比较'
	                		 },
	                		 tooltip: {
	                		        trigger: 'axis',
	                		        axisPointer: {
	                		            type: 'shadow'
	                		        }
	                		 },
	                		 xAxis: {
	                		        type: 'value',
	                		        boundaryGap: [0, 0.01]
	                		 },
	                		 yAxis: {
	                			 type:'category',
	                			 data: ['大盘','本股']
	                		 },
	                		 series:[
	                		         {	
	                		        	 name:'money',
	                		        	 type:'bar',
	                		        	 data:getComp1()
	                		         }
	                 		 ]		
	                 	}
	                 chart_comp1.setOption(option_comp1);
                 </script>
                 </div>
			</div>
		</div>

		<div id="head2"
			style="height: 40px; width: 66%; float: left; margin-top: 80px">
			<p id="para"
				style="margin-left: 40%; font-family: ubuntu; font-size: 150%; color: teal">策略二</p>
		</div>
		<div id="up4" style="float: left; margin-left: 20px; margin-top: 40px">
			<div id="up4_in1" style="float: left">
				<form class="form-inline" role="form">
					<label>股票编号</label> <input type="text" class="form-control"
						onblur="checkStockId('stockIdS2','up4_in1');" id="stockIdS2"
						style="width: 100px">
				</form>
				
				<select id="stra2" class="form-control" style="margin-top: 30px;float: right" onclick="choose2()">
				<option id="21" onclick="diy()" value=1>自定义</option>
				<option id="23" onclick="defaultStrategy2()" value =2 >模拟实例二</option>
				</select>
				
			</div>
			<div id="up4_in2" style="float: left; margin-left: 50px">
				<table id="in2">
					<tr id="21">
						<td><label>初始金额</label> <br></td>
						<td><input type="number" class="form-control" id="moneyS2">
							<br></td>

						<td><label style="margin-left: 50px">初始数量</label> <br></td>
						<td><input type="number" class="form-control" id="initS2">
							<br></td>
					</tr>

					<tr id="22">
						<td><label>买入(%)</label> <br></td>
						<td><form>
								<input type="number" class="form-control" id="buyS2">
							</form> <br></td>
						<td><label style="margin-left: 50px">卖出(%)</label> <br></td>
						<td><input type="number" class="form-control" id="sellS2">
							<br></td>
					</tr>

					<tr id="23">
						<td><label>测试天数</label> <br></td>
						<td><input type="number" class="form-control"
							id="numOfDaysS2"> <br></td>
						<td><label style="margin-left: 50px">截止日期</label> <br></td>
						<td><input type="date" class="form-control" id="endDayS2">
							<br></td>
					</tr>

					<tr id="24">
						<td><label>上界(%)</label> <br></td>
						<td><input type="number" class="form-control"
							id="highboundS2"> <br></td>
						<td><label style="margin-left: 50px">下界(%)</label> <br></td>
						<td><input type="number" class="form-control" id="lowboundS2">
							<br></td>
						<td>
							<button class="btn btn-default btn-sm" onclick="subForm2()"
								id=" submit2" style="margin-left: 50px; width: 80px">执行</button>
						</td>
					</tr>
				</table>
			</div>

			<div class="chart" id="simulate2"
				style="height: 250px;top: 20px; float: top; float: left;">
				<script type="text/javascript">
						var myChart = echarts.init(document
								.getElementById('simulate2'));
						option2 = {
							title : {
								text : '资产总额变化'
							},
							tooltip : {
								trigger : 'axis'
							},
							dataZoom : {
								show : true,
								realtime : true,
								start : 0,
								end : 100
							},

							toolbox : {
								show : true,
								feature : {
									mark : {
										show : true,
										readOnly : false
									},
									magicType : {
										show : true,
										type : [ 'line', 'bar' ]
									},
									restore : {
										show : true
									},
									saveAsImage : {
										show : true
									}
								}
							},
							calculable : true,
							xAxis : [ {
								type : 'category',
								boundaryGap : false,
								data : getMoneyDate2()
							} ],

							yAxis : [ {
								type : 'value',
								splitArea : {
									show : true
								},
								axisLabel : {
									formatter : '{value}$'
								}
							} ],

							series : [ {
								name : 'money',
								type : 'line',
								data : getMoney2(),
								markPoint : {
									data : [ {
										type : 'max',
										name : 'max money'
									}, {
										type : 'min',
										name : 'min money'
									} ]
								},

								markLine : {
									data : [ {
										type : 'average',
										name : 'average money'
									} ]
								},

							} ]

						}
						myChart.setOption(option2);
				</script>
			</div>
			
			<div class="chart" id="comp2" style="height: 200px;float:left;width:800px;margin-top: 20px;background-color: black"> 
                 <script type="text/javascript">
	                 var chart_comp2 = echarts.init(document.getElementById('comp2'));
	                 option_comp2={
	                		 title:{
	                			 text:'净盈率比较'
	                		 },
	                		 tooltip: {
	                		        trigger: 'axis',
	                		        axisPointer: {
	                		            type: 'shadow'
	                		        }
	                		 },
	                		 xAxis: {
	                		        type: 'value',
	                		        boundaryGap: [0, 0.01]
	                		 },
	                		 yAxis: {
	                			 type:'category',
	                			 data: ['大盘','本股']
	                		 },
	                		 series:[
	                		         {	
	                		        	 name:'money',
	                		        	 type:'bar',
	                		        	 data:getComp2()
	                		         }
	                 		 ]		
	                 	}
	                 chart_comp2.setOption(option_comp2);
                 </script>
                 </div>
			
		</div>
	</div>
	
	<div id="help">
	<p id="help_text" style="float:left;font-size:small;margin-left: 30px;font-family: sans-serif;margin-top:100px">
	<br>
		策略一：根据输入的数据，模拟一段时间的经营情况<br>要求输入 <br>1.股票编码 <br>
						2.初始金额 <br>
						3.初始购买数量，按当天收盘价购买，购买这些股票的钱从初始金额扣除<br>
						4.增长率上涨，下降的阀值，上涨超过该值抛出股票，下降超过该值购买股票，具体购买（抛出）数量见”买入，卖出“<br>
						5.价格的上界，下界：价格相对于当天价格的增长幅度。价格低于下界，立即购买股票；高于上界，立即抛出股票，具体购买（抛出）数量见”买入，卖出“<br>
						6.买入，卖出：买入是一个百分数，购买股票金额等于‘当前可用金额’*（买入）；卖出的数量等于‘拥有股票数量’*（卖出）<br>
						7.模拟的天数，模拟的截止日期。<br>
						
						<br>
						<br>
						
		策略二：策略二是策略一的改进版，取消了价格的上界和下界，在实际计算时使用u+2*theta,u-2*theta代替。(u为股票这一时期的增长率均值，theta为其方差)<br>
				要求输入 <br>1.股票编码 <br>
						2.初始金额<br>
						3.初始购买数量，按当天收盘价购买，购买这些股票的钱从初始金额扣除<br>
						4.增长率上涨，下降的阀值，上涨超过该值抛出股票，下降超过该值购买股票，具体购买（抛出）数量见”买入，卖出“<br>
						5.买入，卖出：买入是一个百分数，购买股票金额等于‘当前可用金额’*（买入）；卖出的数量等于‘拥有股票数量’*（卖出）<br>
						6.模拟的天数，模拟的截止日期。<br>
						<br>
		
	</p>
	</div>
	
	<div id="tail"></div>
	
	<form action="StrategyServlet" method="post"
		onsubmit="return checkForm1()" id="strategyForm1">
		<input type="hidden" name="stockId" value="" id="stockId1"><br>
		<input type="hidden" name="rangeUp" value="" id="rangeUp1"><br>
		<input type="hidden" name="rangeDown" value="" id="rangeDown1"><br>
		<input type="hidden" name="buy" value="" id="buy1"><br> <input
			type="hidden" name="sell" value="" id="sell1"><br> <input
			type="hidden" name="money" value="" id="money1"><br> <input
			type="hidden" name="init" value="" id="init1"><br> <input
			type="hidden" name="highbound" value="" id="highbound1"><br>
		<input type="hidden" name="lowbound" value="" id="lowbound1"><br>
		<input type="hidden" name="numOfDays" value="" id="numOfDays1"><br>
		<input type="hidden" name="endDay" value="" id="endDay1"><br>
	</form>
	<form action="StrategyServlet2" method="post"
		onsubmit="return checkForm2()" id="strategyForm2">
		<input type="hidden" name="stockId" value="" id="stockId2"><br>
		<input type="hidden" name="buy" value="" id="buy2"><br>
		<input type="hidden" name="sell" value="" id="sell2"><br> 
		<input
			type="hidden" name="money" value="" id="money2"><br> <input
			type="hidden" name="init" value="" id="init2"><br> <input
			type="hidden" name="highbound" value="" id="highbound2"><br>
		<input type="hidden" name="lowbound" value="" id="lowbound2"><br>
		<input type="hidden" name="numOfDays" value="" id="numOfDays2"><br>
		<input type="hidden" name="endDay" value="" id="endDay2"><br>
	</form>
</body>
</html>
