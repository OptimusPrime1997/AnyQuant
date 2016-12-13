<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,action.*,model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FactoryDetail</title>

<link href="fullPage/examples/examples.css" rel="stylesheet">
<link href="css/styles.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link
	href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css"
	rel="stylesheet">

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<link href="css/styles.css" rel="stylesheet">
<script src="js/script.js"></script>


<script type="text/javascript" src="fullPage/jquery.fullPage.js"></script> 
<script type="text/javascript" src="fullPage/examples/examples.js"></script>
<script src="js/funcs.js"></script>
<script src="js/cities.js"></script>
<script src="js/echarts.js"></script>
<script src="http://echarts.baidu.com/asset/map/js/china.js"></script>
 <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="js/script.js"></script>


</head>
<style>
#aoiTable {
	top: 70px;
}

#map {
	width: 800px;
	height: 500px;
	position: relative;
	top: 70px;
	left: 30px;
}

 #div2 {
 display: none;
 position: absolute;
 height: 100%;
 width: 100%;
 padding-top: 10%;
 z-index: 1001;
 left: 0px;
 top: 0px;
}
 
</style>
<%!ArrayList<Stock> stockList = null;
	ArrayList<KDJVO> kdjList = null;
	ArrayList<ArrayList<Stock>> doubleStock = null;
	
	ArrayList<StockInfo> stockInfoList=null;
		boolean getDataSuc = false;%>
<%
	stockList=(ArrayList<Stock>) request.getAttribute("stockList");
	kdjList = (ArrayList<KDJVO>) request.getAttribute("kdjList");
	doubleStock = (ArrayList<ArrayList<Stock>>) request.getAttribute("doubleStock");
	
	stockInfoList=(ArrayList<StockInfo>)request.getAttribute("stockInfoList");
	if (stockList != null &&  kdjList != null && doubleStock != null 
			&&stockInfoList!=null) {
		getDataSuc = true;
	} else {
		out.println("<h1>Get stockList is null!</h1>");
	}
%>
<script>
	if(<%=getDataSuc%>==true){
			
		var kdjArr=new Array();
		<%for (int i = 0, size = kdjList.size(); i < size; i++) {%>
			var kObj={};
			kObj.id="<%=kdjList.get(i).getId()%>";
			kObj.date="<%=kdjList.get(i).getDate()%>";
			kObj.k=<%=kdjList.get(i).getK()%>;
			kObj.d=<%=kdjList.get(i).getD()%>;
			kObj.j=<%=kdjList.get(i).getJ()%>;
			
			kdjArr[<%=i%>]=kObj;
		<%}%>
		
		var skArr=new Array();
		<%for(int i=0;i<stockList.size();i++){%>
			var sObj={};
			sObj.id="<%=stockList.get(i).getId()%>";
			sObj.name="<%=stockList.get(i).getName()%>";
			sObj.date="<%=stockList.get(i).getDate()%>";
			sObj.maxprice=<%=stockList.get(i).getMaxprice()%>;
			sObj.volume=<%=stockList.get(i).getVolume()%>;
			sObj.startprice=<%=stockList.get(i).getStartprice()%>;
			sObj.minprice=<%=stockList.get(i).getMinprice()%>;
			sObj.endprice=<%=stockList.get(i).getEndprice()%>;
			sObj.pe=<%=stockList.get(i).getPe()%>;
			sObj.pb=<%=stockList.get(i).getPb()%>;
			sObj.adjprice=<%=stockList.get(i).getAdjprice()%>;
			sObj.turnover=<%=stockList.get(i).getTurnover()%> ;
			sObj.money=<%=stockList.get(i).getMoney()%>;
			sObj.range=<%=stockList.get(i).getRange()%>;
			sObj.tr=<%=stockList.get(i).getTr()%>;
			skArr[<%=i%>]=sObj;
	<%}%>

		
		var dbSkArr=new Array();
		<%for(int i=0;i<doubleStock.size();i++){%>
			<%ArrayList<Stock> tempList=doubleStock.get(i);%>
			var dbSkArr2=new Array();
			<%for(int j=0;j<tempList.size();j++){%>
				var sObj={};
				sObj.id="<%=tempList.get(j).getId()%>";
				sObj.name="<%=tempList.get(j).getName()%>";
				sObj.date="<%=tempList.get(j).getDate()%>";
				sObj.volume=<%=tempList.get(j).getVolume()%>;
				sObj.startprice=<%=tempList.get(j).getStartprice()%>;
				sObj.maxprice=<%=tempList.get(j).getMaxprice()%>;
				sObj.minprice=<%=tempList.get(j).getMinprice()%>;
				sObj.endprice=<%=tempList.get(j).getEndprice()%>;
				sObj.pe=<%=tempList.get(j).getPe()%>;
				sObj.pb=<%=tempList.get(j).getPb()%>;
				sObj.ajdprice=<%=tempList.get(j).getAdjprice()%>;
				sObj.turnover=<%=tempList.get(j).getTurnover()%>;
				sObj.money=<%=tempList.get(j).getMoney()%>;
				sObj.range=<%=tempList.get(j).getRange()%>;
				sObj.tr=<%=tempList.get(j).getTr()%>;
				dbSkArr2[<%=j%>]=sObj;
			<%}%>
			dbSkArr[<%=i%>]=dbSkArr2;
		<%}%>
		
		
		
		var lastSkArr=new Array();
		for(var i=0;i<dbSkArr.length;i++){
			lastSkArr[i]=dbSkArr[i][dbSkArr[i].length-1];
		}
		
		var skInfoList=new Array();
		<%for(int i=0;i<stockInfoList.size();i++){%>
			var skInfoObj={};
			skInfoObj.stockId="<%=stockInfoList.get(i).getStockId()%>";
			skInfoObj.secFullName="<%=stockInfoList.get(i).getSecFullName()%>";
			skInfoObj.officeAddr="<%=stockInfoList.get(i).getOfficeAddr()%>";
			skInfoObj.primeOperation="<%=stockInfoList.get(i).getPrimeOperation()%>";
			skInfoList[<%=i%>]=skInfoObj;
		<%}%>
		/* console.log(skInfoList); */
	}
	
	<%String baseDir="http://" + request.getServerName() +request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);%>
	var baseUrl="<%=baseDir%>";
	var stockId = skArr[0]['id'];

	function init() {
		var temp = ReadCookie("factory");
		if (temp != "") {
			name = temp;
		}
		;
		$('#aoi').DataTable({
			bPaginate : true,
			bLengthChange : false,
			bProcessing : true
		});
		$('#fullpage').fullpage(
				{
					anchors : [ 'firstPage', 'secondPage', '3rdPage',
							'4thPage', '5thPage', '6thPage' ],
					menu : '#menu',
					loopTop : true,
					loopBottom : true
				});
	}
	function getPE() {
		var peArr = new Array();
		for (var i = 0; i < skArr.length; i++) {
			peArr[i] = skArr[i]["pe"];
		}
		return peArr;
	}
	function getPB() {
		var pbArr = new Array();
		for (var i = 0; i < skArr.length; i++) {
			pbArr[i] = skArr[i]["pb"];
		}
		return pbArr;
	}

	function getCitiesData() {
		var addrArr = new Array();
		for (var i = 0,len= skInfoList.length;i<len ;i++) {
			var obj={};
			obj.name=readCity(skInfoList[i]["officeAddr"]);
			obj.value=1;
			addrArr[i]=obj; 
		}
		var cityArr = new Array();
		for (var j = 0; j < addrArr.length; j++) {
			var cityObj = {};
			cityObj.name = addrArr[j]["name"];
			var value = 0;
			for (k = 0; k < addrArr.length; k++) {
				if (cityObj.name == addrArr[k]["name"]) {
					value++;
				}
			}
			cityObj.value = value;
			cityArr[j] = cityObj;
		} 
		return cityArr;
	}
	
	function showThumbnail(name, id){
	    if (document.getElementById('thumbnailName').innerHTML == name) {
	        return;
	    };
	    document.getElementById('thumbnailName').innerHTML = name;
	    document.getElementById('thumbnailID').innerHTML = id;
	    

		var myChart = echarts.init(document.getElementById('thumbnail'));
		/* console.log(dbSkArr); */
		var data0 = getOHLCData(id,dbSkArr);
		/* console.log("id:"+id);
		console.log(data0); */
		option = {
			grid : {
				x : 45, //left
				y : 10, //top
				x2 : 15, //right
				y2 : 20
			//bottom
			},
			xAxis : {
				type : 'category',
				data : getOHLCDate(id,dbSkArr),
				scale : true,
				boundaryGap : false,
				axisLine : {
					onZero : false
				},
				splitLine : {
					show : false
				},
				min : 'dataMin',
				max : 'dataMax'
			},
			yAxis : {
				scale : true,
				splitArea : {
					show : true
				}
			},
			series : [ {
				name : '日K',
				type : 'k',
				data : data0,
				itemStyle : {
					normal : {
						color : upColor,
						color0 : downColor,
						borderColor : upColor,
						borderColor0 : downColor
					}
				}
			}, {
				name : 'MA5',
				type : 'line',
				data : calculateMA(data0, 5),
				smooth : true,
				itemStyle : {
					normal : {
						color : color5
					}
				}
			}, {
				name : 'MA10',
				type : 'line',
				data : calculateMA(data0, 10),
				smooth : true,
				itemStyle : {
					normal : {
						color : color10
					}
				}
			}

			]
		};

		myChart.setOption(option);
	    /* var myChart = echarts.init(document.getElementById('thumbnail'));
	    var data0 = getOHLCData(id,dbSkArr);
		console.log("data0:");
		console.log(data0);
	    option = {
	        grid: {
	            x: 45,  //left
	            y: 10,  //top
	            x2:15,  //right
	            y2:20   //bottom
	        },
	        xAxis: {
	            type: 'category',
	            data: getOHLCDate(id,dbSkArr),
	            scale: true,
	            boundaryGap : false,
	            axisLine: {onZero: false},
	            splitLine: {show: false},
	            min: 'dataMin',
	            max: 'dataMax'
	        },
	        yAxis: {
	            scale: true,
	            splitArea: {
	                show: true
	            }
	        },           
	        series: [
	            {
	                name: '日K',
	                type: 'k',
	                data: data0,
	                itemStyle:{
	                    normal:{
	                        color:upColor,
	                        color0:downColor,
	                        borderColor:upColor,
	                        borderColor0:downColor
	                    }
	                }
	            },
	            {
	                name: 'MA5',
	                type: 'line',
	                data: calculateMA(data0, 5),
	                smooth: true,
	                itemStyle: {
	                    normal: {color:color5}
	                }
	            },
	            {
	                name: 'MA10',
	                type: 'line',
	                data: calculateMA(data0, 10),
	                smooth: true,
	                itemStyle: {
	                    normal: {color:color10}
	                }
	            }

	        ]
	    };

	    myChart.setOption(option); */
	}
	function showCell(tr){
	    var temp = document.getElementById(tr);
	    var id = temp.cells[0].innerHTML;
	    var name = temp.cells[1].innerHTML;
	    /* console.log(id+'-id--name-'+name); */
	    showThumbnail(name, id);
	}
	 function skipSingle(tr) {
		var temp = document.getElementById(tr);
		var id = temp.cells[0].innerHTML;
		document.getElementById("skipStockId").value = id;
		/* document.getElementById("skipDate").value = getDate; */
		document.getElementById("skipForm").submit();
	} 
	$(document).ready(function() {
		init();
		setFactoryCurrentInfo(stockId,skArr);
	});
	 
	</script>

<body>
<div id="formContent">
	<form
		action="ShowBenchServlet?stockId=hs300&manyDay=20&reqDate=2016-05-05"
		method="post" name="marketPost" id="marketPost">
	</form>
	<form
		action="SingleServlet?stockId=sh600030&manyDay=20&reqDate=2016-05-05"
		method="post" name="singlePost" id="singlePostId">
	</form>
	<form
		action="FactoryServlet?stockId=estate&manyDay=20&reqDate=2016-05-05"
		method="post" name="factoryPost">
	</form>
	
	<!-- onsubmit="return testStockId(this.stockId.value)" -->

<form action="SingleServlet" method="post" id="skipForm" >
		<input type="hidden" name="stockId" value="" id="skipStockId"><br>
		<input type="hidden" name="manyDay" value="20"><br> 
		<input type="hidden" name="reqDate" value="" id="skipDate"><br>
</form>
	
<form action="FactoryServlet" method="post" id="skipIndustryForm">
		<input type="hidden" name="stockId" value="" id="skipIndustryId"><br>
		<input type="hidden" name="manyDay" value="" id="skipIndustryManyDay"><br> 
		<input 	type="hidden" name="reqDate" value="" id="skipIndustryDate"><br> 
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
	                <li><a href="strategy.jsp">策略</a></li>
	                <li class="active"><a>行业</a></li>
				</ul>
			</div>
		</nav>
	</div>
	<ul id="menu">
		<li data-menuanchor="firstPage" class="active"><a
			href="#firstPage">今日涨跌排行</a></li>
		<li data-menuanchor="secondPage"><a href="#secondPage">平均K线图</a></li>
		<li data-menuanchor="3rdPage"><a href="#3rdPage">平均成交量</a></li>
		<li data-menuanchor="4thPage"><a href="#4thPage">平均市盈率</a></li>
		<li data-menuanchor="5thPage"><a href="#5thPage">平均市净率</a></li>
		<li data-menuanchor="6thPage"><a href="#6thPage">行业地图</a></li>
	</ul>
	<div id="fullpage">
		<div class="section" id="section0">
			<div class="JLtable" id="aoiTable">
				<table class="table table-bordered table-hover" id="aoi">
					<thead>
						<tr>
							<th>代码</th>
							<th>名称</th>
							<th>开盘价</th>
							<th>收盘价</th>
							<th>最高价</th>
							<th>最低价</th>
							<th>成交量</th>
							<th>涨跌幅(%)</th>
						</tr>
					</thead>
						<tbody>
							<script>
								var str = '';
								for (var i = 0,len1=lastSkArr.length; i < len1; i++) {
									str += "<tr id='tr"+i+1+"' onmouseover="+"showCell('tr"+i+1+"')"+" ondblclick= "+"skipSingle('tr"+i+1+"')"+">"
										str += '<td>' +lastSkArr[i]['id']+ '</td>';
										str += '<td>' +lastSkArr[i]['name']+ '</td>';
										str += '<td>' +lastSkArr[i]['startprice']+ '</td>';
										str += '<td>' +lastSkArr[i]['endprice']+ '</td>';
										str += '<td>' +lastSkArr[i]['maxprice']+ '</td>';
										str += '<td>' +lastSkArr[i]['minprice']+ '</td>';
										str += '<td>' +lastSkArr[i]['volume']+ '</td>';
										str += '<td>' +parseFloat(lastSkArr[i]['range']*100).toFixed(2)+ '</td>';
									str += '</tr>'
								}
								document.write(str);
							</script>
						</tbody>
				</table>
			</div>
		</div>
		<div class="section" id="section1">
			<div class="chart" id="MA">
				<script type="text/javascript">
					var myChart = echarts.init(document.getElementById('MA'));
					var data0 = getData(stockId,skArr);

					option = {
						tooltip : {
							trigger : 'axis',
							formatter : function(params) {
								var res = params[0].name;
								res += '<br/>' + params[0].seriesName;
								res += '<br/>  开盘 : ' + params[0].value[0]
										+ '  最高 : ' + params[0].value[3];
								res += '<br/>  收盘 : ' + params[0].value[1]
										+ '  最低 : ' + params[0].value[2];
								return res;
							}
						},
						dataZoom : {
							show : true,
							realtime : true,
							start : 50,
							end : 100
						},
						grid : {
							x : 70,
							y : 10,
							x2 : 90,
							y2 : 65
						},
						xAxis : [ {
							type : 'category',
							boundaryGap : true,
							axisTick : {
								onGap : false
							},
							splitLine : {
								show : false
							},
							data : date(skArr)
						} ],
						yAxis : {
							scale : true,
							splitArea : {
								show : true
							}
						},
						series : [ {
							name : '日K',
							type : 'k',
							data : data0,
							itemStyle : {
								normal : {
									color : upColor,
									color0 : downColor,
									borderColor : upColor,
									borderColor0 : downColor
								}
							}
						}, {
							name : 'MA5',
							type : 'line',
							data : calculateMA(data0, 5),
							smooth : true,
							itemStyle : {
								normal : {
									color : color5
								}
							}
						}, {
							name : 'MA10',
							type : 'line',
							data : calculateMA(data0, 10),
							smooth : true,
							itemStyle : {
								normal : {
									color : color10
								}
							}
						}

						]
					};

					myChart.setOption(option);
				</script>
			</div>
		</div>
		<div class="section" id="section2">
			<div class="chart" id="VOL">
				<script type="text/javascript">
					var myChart = echarts.init(document.getElementById('VOL'));
					var data0 = getVOL(stockId, skArr);
					option = {
						tooltip : {
							trigger : 'axis',
							formatter : function(params) {
								var res = params[0].name;
								res += '<br/>' + params[0].seriesName;
								res += '<br/>' + (params[0].value / 10000.0).toFixed(2) + '万';
								return res;
							}
						},
						dataZoom : {
							show : true,
							realtime : true,
							start : 50,
							end : 100
						},
						grid : {
							x : 70,
							y : 10,
							x2 : 90,
							y2 : 65
						},
						xAxis : [ {
							type : 'category',
							boundaryGap : true,
							axisTick : {
								onGap : false
							},
							splitLine : {
								show : false
							},
							data : date(skArr)
						} ],
						yAxis : [ {
							type : 'value',
							scale : true,
							splitNumber : 5,
							boundaryGap : [ 0.05, 0.05 ],
							axisLabel : {
								formatter : function(v) {
									return Math.round(v / 10000.0).toFixed(2) + ' 万'
								}
							},
							splitArea : {
								show : true
							}
						} ],
						series : [ {
							name : 'VOL',
							type : 'bar',
							data : data0
						}, {
							name : 'VOL5',
							type : 'line',
							data : calculateVOL(data0, 5),
							smooth : true,
							itemStyle : {
								normal : {
									color : color5
								}
							}
						}, {
							name : 'VOL10',
							type : 'line',
							data : calculateVOL(data0, 10),
							smooth : true,
							itemStyle : {
								normal : {
									color : color10
								}
							}
						} ]
					};
					myChart.setOption(option);
				</script>
			</div>
		</div>
		<div class="section" id="section3">
			<div class="chart" id="PEchart">
				<script type="text/javascript">
					var myChart = echarts.init(document
							.getElementById('PEchart'));
					var data0 = getPE();
					option = {
						tooltip : {
							trigger : 'axis',
							formatter : function(params) {
								var res = params[0].name;
								res += '<br/>' + params[0].seriesName;
								res += '<br/>' + params[0].value;
								return res;
							}
						},
						dataZoom : {
							show : true,
							realtime : true,
							start : 50,
							end : 100
						},
						grid : {
							x : 70,
							y : 10,
							x2 : 90,
							y2 : 65
						},
						xAxis : [ {
							type : 'category',
							position : 'bottom',
							boundaryGap : true,
							axisTick : {
								onGap : false
							},
							splitLine : {
								show : false
							},
							data : date(skArr)
						} ],
						yAxis : [ {
							type : 'value',
							scale : true,
							boundaryGap : [ 0.05, 0.05 ],
							splitArea : {
								show : true
							}
						} ],
						series : [ {
							name : 'PE',
							type : 'line',
							data : data0,
							smooth : true,
							itemStyle : {
								normal : {
									color : color5
								}
							}
						} ]
					};
					myChart.setOption(option);
				</script>
			</div>
		</div>
		<div class="section" id="section4">
			<div class="chart" id="PBchart">
				<script type="text/javascript">
					var myChart = echarts.init(document
							.getElementById('PBchart'));
					var data0 = getPB();
					option = {
						tooltip : {
							trigger : 'axis',
							formatter : function(params) {
								var res = params[0].name;
								res += '<br/>' + params[0].seriesName;
								res += '<br/>' + params[0].value;
								return res;
							}
						},
						dataZoom : {
							show : true,
							realtime : true,
							start : 50,
							end : 100
						},
						grid : {
							x : 70,
							y : 10,
							x2 : 90,
							y2 : 65
						},
						xAxis : [ {
							type : 'category',
							position : 'bottom',
							boundaryGap : true,
							axisTick : {
								onGap : false
							},
							splitLine : {
								show : false
							},
							data : date(skArr)
						} ],
						yAxis : [ {
							type : 'value',
							scale : true,
							boundaryGap : [ 0.05, 0.05 ],
							splitArea : {
								show : true
							}
						} ],
						series : [ {
							name : 'PB',
							type : 'line',
							data : data0,
							smooth : true,
							itemStyle : {
								normal : {
									color : color5
								}
							}
						} ]
					};
					myChart.setOption(option);
				</script>
			</div>
		</div>
		<div class="section" id="section5">
			<div id="map">
				<script type="text/javascript">
					var myChart = echarts.init(document.getElementById('map'));

					var geoCoordMap = cityLoc();

					var convertData = function(data) {
						var res = [];
						for (var i = 0; i < data.length; i++) {
							var geoCoord = geoCoordMap[data[i].name];
							if (geoCoord) {
								res.push({
									name : data[i].name,
									value : geoCoord.concat(data[i].value)
								});
							}
						}
						return res;
					};

					option = {
						backgroundColor : '#fff',
						tooltip : {
							trigger : 'item',
							formatter : function(params) {
								return params.name + ' : ' + params.value[2];
							}
						},
						dataRange : {
							min : 0,
							max : 10,
							calculable : true,
							color : [ '#d94e5d', '#eac736', '#50a3ba' ],
							textStyle : {
								color : '#000'
							}
						},
						geo : {
							map : 'china',
							label : {
								emphasis : {
									show : false
								}
							},
							itemStyle : {
								normal : {
									areaColor : '#323c48',
									borderColor : '#111'
								},
								emphasis : {
									areaColor : '#2a333d'
								}
							}
						},
						series : [ {
							name : '公司总部数量',
							type : 'scatter',
							coordinateSystem : 'geo',
							data : convertData(getCitiesData()),
							symbolSize : 10,
							label : {
								normal : {
									show : false
								},
								emphasis : {
									show : false
								}
							},
							itemStyle : {
								emphasis : {
									borderColor : '#000',
									borderWidth : 1
								}
							}
						} ]
					}

					myChart.setOption(option);
				</script>
			</div>
		</div>
	</div>

	<div id="introduction">
		<div id="current">
			<h3 id="stockName"></h3>
			<p id="currentDate"></p>
			<div id="stockContent">
				<p id="openPriceLabel"></p>
				<p id="openPrice"></p>
				<p id="closePriceLabel"></p>
				<p id="closePrice"></p>
				<p id="ADJLabel"></p>
				<p id="ADJ"></p>
				<p id="turnOverLabel"></p>
				<p id="turnOver"></p>
				<p id="PE"></p>
				<p id="PB"></p>
			</div>
		</div>

		<div id="thumb">
			<div id="thumbnailHead">
				<h3 id="thumbnailName"></h3>
				<h4 id="thumbnailID"></h4>
			</div>
			<div id="thumbnail"></div>
		</div>
	</div>
	
</body>
</html>
