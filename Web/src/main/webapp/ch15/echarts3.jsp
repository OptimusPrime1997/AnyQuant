<%@page import="utility.MA_Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<meta charset="UTF-8">
<title>Market</title>
 <link href="css/styles.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
    <link href="fullPage/examples/examples.css" rel="stylesheet">

    <script src="js/funcs.js"></script>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript" src="fullPage/jquery.fullPage.js"></script>
    <script type="text/javascript" src="fullPage/examples/examples.js"></script>
    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script src="http://echarts.baidu.com/dist/echarts.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
  
</head>
<style> 
    #marketTable{
        top: 60px;
    }
    #industryTable{
        top: 80px;
        
    }
    .chart{
        float: left;
        width: 420px;
        height: 200px;
        position: relative;
        top: 80px;
        left: 10px;
    }
    #chart1{
        width: 900px;
        height: 200px;
    }
    #VOL{
        margin-top: 20px;
    }
    #icBar{
        width: 500px;
        height: 270px;
        position: relative;
        left: 20px;
        top: 100px;
    }
    #icPadar{
        width: 350px;
        height: 380px;
        position: relative;
        left: 40px;
        top: 60px;
    }
    #title{
        position: relative;
        right: 340px;
        top: 90px;
    }
</style>

<%!ArrayList<Stock> stockList = null;
	String[] getDay = null;
	Stock nowStock = null;
	double[] volume = null;
	ArrayList<Stock> allStock = null;
	double[] tr = null;
	ArrayList<KDJVO> kdjList = null;
	
	ArrayList<ArrayList<Stock>> doubleStock = null;
		ArrayList< ArrayList<IndustryMoney> > dbIndustryMoney=null;
		ArrayList<ChartVO> chartVOs=null;
		ArrayList<ChartVO> charts=null;
		
		String date=null;
		boolean getDataSuc = false;%>
<%
	stockList=(ArrayList<Stock>) request.getAttribute("stockList");
	allStock = (ArrayList<Stock>) request.getAttribute("allStock");
	kdjList = (ArrayList<KDJVO>) request.getAttribute("kdjList");
	doubleStock = (ArrayList<ArrayList<Stock>>) request.getAttribute("doubleStock");
	dbIndustryMoney=(ArrayList<ArrayList<IndustryMoney> >)request.getAttribute("dbIndustryMoney");
	chartVOs=(ArrayList<ChartVO>)request.getAttribute("trueChartVOs");
	charts=(ArrayList<ChartVO>)request.getAttribute("chartVOs");
	date=(String)request.getAttribute("date");
	
	if (stockList != null && allStock != null && kdjList != null && doubleStock != null 
			&& dbIndustryMoney!=null &&chartVOs!=null
			&&charts!=null) {
		/* out.println("get data success!"); */
		getDataSuc = true;
		getDay = new String[stockList.size()];
		volume = new double[stockList.size()];
		tr = new double[stockList.size()];
		for (int i = 0; i < stockList.size(); i++) {
			Stock temp = stockList.get(i);
			getDay[i] = temp.getDate().toChartString();
			volume[i] = temp.getVolume();
			tr[i] = temp.getTr();
			//			out.println("<h1>" + getDay[i] + "</h1>");
		}
		nowStock = stockList.get(stockList.size() - 1);

	} else {
		out.println("<h1>Get stockList is null!</h1>");
	}
%>
<script>

/* 	var username= */

    
    
    

/*get stock ohlc date 3 dimension array  */
	<%if(getDataSuc == true) {%>
			var dbIndustry=new Array();
			<%for(int i=0;i<dbIndustryMoney.size();i++){%>
				var industryArr=new Array();
				<%ArrayList<IndustryMoney> temp=dbIndustryMoney.get(i);
				for(int j=0;j<temp.size();j++){%>
					var industry={};
					industry.industryId="<%=temp.get(i).getIndustryId()%>";
					industry.industryName="<%=temp.get(i).getIndustryName()%>";
					industry.yearMonth="<%=temp.get(i).getYearMonth().toMonthString()%>";
					industry.industryMoney=<%=temp.get(i).getIndustryMoney()%>;
					industryArr[j]=industry;
				<%}%>
					dbIndustry[<%=i%>]=industryArr;
			<%}%>
			
			
			var allArray = new Array();
			<%for (int i = 0; i < allStock.size(); i++) {%>
			var tempArray = new Array();
			tempArray[0] =
			"<%=allStock.get(i).getName()%>"
			;
			tempArray[1] =
			"<%=allStock.get(i).getId()%>"
			;
			tempArray[2] =
			<%=allStock.get(i).getStartprice()%>
			;
			tempArray[3] =
			<%=allStock.get(i).getMaxprice()%>
			;
			tempArray[4] =
			<%=allStock.get(i).getMinprice()%>
			;
			tempArray[5] =
			<%=allStock.get(i).getEndprice()%>
			;
			tempArray[6] =
			<%=allStock.get(i).getVolume()%>
			;
			tempArray[7] =
			<%=allStock.get(i).getTurnover() %>
			;
		
			allArray[
			<%=i%>
			] = deepCopy(tempArray);
			<%}%>
		
				var getDate="<%=date%>" ;
		
				var stockArray1=new Array(); 
			<%ArrayList<Stock> cStockList = new ArrayList<Stock>();%>
			<%for (int j = 0; j < doubleStock.size(); j++) {%>
			var stockObj=new Object();
					<%cStockList = doubleStock.get(j);%>
					
				stockObj.stockId=
					"<%=cStockList.get(0).getId()%>" ;
					var array2=new Array();
		<%for (int i = 0; i < cStockList.size(); i++) {%>
			var array3 = new Array();
				array3[0] =
		<%=cStockList.get(i).getStartprice()%>
				;
				array3[1] =
		<%=cStockList.get(i).getMaxprice()%>
				;
				array3[2] =
		<%=cStockList.get(i).getMinprice()%>
			;
				array3[3] =
		<%=cStockList.get(i).getEndprice()%>
			;
			array2[
			       <%=i%>]=deepCopy(array3);
		<%}%>
				stockObj.stockData = deepCopy(array2);
			stockArray1[<%=j%>]=stockObj;
			
			<%}%>
<%}%>

	function deepCopy(array) {
		var tempArray = [];
		//判断array参数不是null ,undefined,0,'',nan,false
		if (array) {
			for (var i = 0, len = array.length; i < len; i++) {
				if (array[i] instanceof Array) {
					tempArray[i] = deepCopy(array[i]);
				} else {
					tempArray[i] = array[i];
				}
			}
			return tempArray;
		}
		return null;
	}

	
	
	        
	function getTr() {
		var trArray = new Array();
<%for (int j = 0,len=tr.length; j < len; j++) {%>
	trArray[
<%=j%>
	] =
<%=tr[j]%>
	;
<%}%>
	return trArray;
	}

	 function init() {
	        $('#market').DataTable({
	            bPaginate: true,
	            bLengthChange: false,
	            bProcessing: true
	        });
	        $('#fullpage').fullpage({
	            // sectionsColor: ['#fff', '#fff', '#fff'],
	            anchors: ['firstPage', 'secondPage', '3rdPage'],
	            menu: '#menu',
	            loopTop: true,
	            loopBottom: true,

	        }); 
	    }
	 
	function date() {
		var sDate = new Array();
<%for (int j = 0,len=getDay.length; j < len; j++) {%>
	sDate[
<%=j%>
	] =
"<%=getDay[j]%>" ;
<%}%>
	return sDate;
	}
	/* [ '2013/05/2', '2013/05/3', '2013/05/6', '2013/05/7', '2013/05/8',
				'2013/05/9', '2013/05/10', '2013/05/13', '2013/05/14', '2013/05/15',
				'2013/05/16', '2013/05/17', '2013/05/20', '2013/05/21',
				'2013/05/22', '2013/05/23', '2013/05/24', '2013/05/27',
				'2013/05/27', '2013/05/28' ]; */
	//指数信息
	function setInfo() {
		var info = new Array();
		info[0] =
"<%=nowStock.getName()%>"
	;
		info[1] =
"<%=nowStock.getId()%>"
	;
		info[2] =
<%=nowStock.getStartprice()%>
	;
		info[3] =
<%=nowStock.getMaxprice()%>
	;
		info[4] =
<%=nowStock.getMinprice()%>
	;
		info[5] =
<%=nowStock.getEndprice()%>
	;
		info[6] =
<%=nowStock.getVolume()%>
	;
		return info;
		/* [ '上证指数', 'hs300', 2828.46, 2835.86, 2850.09, 2814.12,
				'1.1432亿' ] */
	}
	//大盘信息
	/* function setTableInfo() {

	return allArray;
	} */
		/* return [
				[ '沙河股份', 'sz000014', 17.9, 17.67, 18.38, 17.62, '492万', 2.44 ],
				[ '格力地产', 'sh600185', 18.84, 18.6, 18.98, 18.58, '806.04万', 1.4 ],
				[ '大智慧', 'sh601519', 8.81, 8.65, 8.85, 8.61, '877.69万', 0.44 ] ] */
	//详细信息
	
	 function setIndustryTableInfo(){
		var arr1=new Array();
		<%for(int i=0,size=charts.size();i<size;i++){%>
			var arr2=new Array();
			arr2[0]="<%=charts.get(i).getName()%>";
			arr2[1]=<%=charts.get(i).getTurnover()%>;
			arr2[2]=<%=charts.get(i).getVolume()%>;
			arr2[3]=<%=charts.get(i).getAdjprice()%>;
			arr2[4]=<%=charts.get(i).getPe()%>;
			arr2[5]=<%=charts.get(i).getPb()%>;
			
			arr2[1]=parseFloat(arr2[1].toFixed(2));
			arr2[2]=parseFloat(arr2[2].toFixed(2));
			arr2[3]=parseFloat(arr2[3].toFixed(2));
			arr2[4]=parseFloat(arr2[4].toFixed(2));
			arr2[5]=parseFloat(arr2[5].toFixed(2));
			
			arr1[<%=i%>]=deepCopy(arr2);
		<%}%>
			return arr1;
    }
        /* return[
            ['行业一',1,2,3,4,5],
            ['行业一',2,3,4,5,6],
            ['行业一',3,4,5,6,7]
        ] */
		
	function setCurrentInfo(id) {
		var currentInfo = setInfo();
		document.getElementById('stockName').innerHTML = currentInfo[0];
		document.getElementById('stockID').innerHTML = currentInfo[1];
		document.getElementById('openPriceLabel').innerHTML = '开盘价:';
		document.getElementById('openPrice').innerHTML = currentInfo[2];
		document.getElementById('closePriceLabel').innerHTML = '收盘价:';
		document.getElementById('closePrice').innerHTML = currentInfo[3];
		document.getElementById('highestPriceLabel').innerHTML = '最高价:';
		document.getElementById('highestPrice').innerHTML = currentInfo[4];
		document.getElementById('lowestPriceLabel').innerHTML = '最低价:';
		document.getElementById('lowestPrice').innerHTML = currentInfo[5];
		document.getElementById('volLabel').innerHTML = '成交量:';
		document.getElementById('vol').innerHTML = currentInfo[6];
		if (currentInfo[0] != '沪深300') {
			document.getElementById('ADJLabel').innerHTML = '后复权价:';
			document.getElementById('ADJ').innerHTML = currentInfo[7];
			document.getElementById('turnOverLabel').innerHTML = '换手率:';
			document.getElementById('turnOver').innerHTML = currentInfo[8];
			document.getElementById('PE').innerHTML = '市盈率: ' + currentInfo[9];
			document.getElementById('PB').innerHTML = '市净率: ' + currentInfo[10];
		}
	}
	function getVOL(id) {
		var sVol = new Array();
<%for (int j = 0,len=volume.length; j < len; j++) { %>
	sVol[
<%=j%>
	] =
<%=volume[j]%>
	;
<%}%>
	return sVol;
	}
	//k线图
	function getData(id) {
		
		for(var m=0,len=stockArray1.length;m<len;m++){
			var sArray2=stockArray1[m];
			if(sArray2.stockId==id){
				return deepCopy(sArray2.stockData);
			}
		}
	}
	
	function getK(id) {
		var kArray = new Array();
<%for (int j = 0,size=kdjList.size(); j < size; j++) {%>
	kArray[
<%=j%>
	] =
<%=kdjList.get(j).getK()%>
	;
<%}%>
	return kArray;
	}
	
	function getD(id) {
		var dArray = new Array();
<%for (int j = 0,size=kdjList.size(); j < size; j++) {%>
	dArray[
<%=j%>
	] =
<%=kdjList.get(j).getD()%>
	;
<%}%>
	return dArray;
	}
	
	function getJ(id) {
		var jArray = new Array();
<%for (int j = 0,size=kdjList.size(); j < size; j++) {%>
	jArray[
<%=j%>
	] =
<%=kdjList.get(j).getJ()%>
	;
<%}%>
	return jArray;
	}

	function getATR(id) {
		var trArray = getTr();
		var rsArray = calculateVOL(trArray, 5);
		return rsArray;
	}

	function calculateMA(data0, dayCount) {
		if(data0 instanceof Array){
			var result = [];
			for (var i = 0, len = data0.length; i < len; i++) {
				var sum = 0;
				if (i < dayCount) {
					sum = 0;
					for (var k = i; k < (i + Math.floor(dayCount / 2)); k++) {
						sum += data0[k][1];
					}
					var temp1=(sum * 1.0) / Math.floor(dayCount / 2);
					temp1=parseFloat(temp1.toFixed(2));
					result.push(temp1);
					continue;
				} else {
					sum = 0;
					for (var j = 0; j < dayCount; j++) {
						sum += data0[i - j][1];
					}
					var temp2=(sum * 1.0 /dayCount);
					temp2=parseFloat(temp2.toFixed(2));
					result.push(temp2)
				}
			}
			return result;
		}
	}
	function calculateVOL(data0, dayCount) {
		if(data0 instanceof Array){
			var result = [];
			for (var i = 0, len = data0.length; i < len; i++) {
				var sum = 0;
				if (i < dayCount) {
					for (var k = i; k < (i + Math.floor(dayCount / 2)); k++) {
						sum += data0[k];
					}
					var temp1=(sum * 1.0) / Math.floor(dayCount / 2);
					temp1=parseFloat(temp1.toFixed(2));
					result.push(temp1);
					continue;
				} else {
					for (var j = 0; j < dayCount; j++) {
						sum += data0[i - j];
					}
					var temp2=(sum * 1.0 /dayCount);
					temp2=parseFloat(temp2.toFixed(2));
					result.push(temp2)
				}
			}
			return result;
		}
	}
	function showThumbnail(name, id) {
		if (document.getElementById('thumbnailName').innerHTML == name) {
			return;
		}
		;
		document.getElementById('thumbnailName').innerHTML = name;
		document.getElementById('thumbnailID').innerHTML = id;

		var myChart = echarts.init(document.getElementById('thumbnail'));
		
		var data0 = getData(id);
		if(id=='sh600696'){
			console.log(data0);
		}
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
				data : date(),
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
	}
	function clearThumbnail() {
		document.getElementById('thumbnailName').innerHTML = '';
		document.getElementById('thumbnailID').innerHTML = '';
		document.getElementById('thumbnail').innerHTML = '';
	}

	/* function setTable() {
		var table = [ 'tr1', 'tr2', 'tr3' ,'tr4','tr5','tr6'];
		var tableInfos = setTableInfo();
		console.log(tableInfos);
		for (var i = 0,len=table.length; i < len; i++) {
			var tr = document.getElementById(table[i]);
			var info = tableInfos[i];

			tr.innerHTML = '<td>' + info[0] + '</td>' + '<td>' + info[1]
					+ '</td>' + '<td>' + info[2] + '</td>' + '<td>' + info[3]
					+ '</td>' + '<td>' + info[4] + '</td>' + '<td>' + info[5]
					+ '</td>' + '<td>' + info[6] + '</td>' + '<td>' + info[7]
					+ '</td>'
		}
		;
	} */
	function setIndustryTable() {
		var table = [ 'itr1', 'itr2', 'itr3' ];
		var tableInfos = setIndustryTableInfo();
		for (var i = 0,len=table.length; i < len; i++) {
			var tr = document.getElementById(table[i]);
			var info = deepCopy(tableInfos[i]);
			console.log(info);
			tr.innerHTML = '<td>' + info[0] + '</td>' + '<td>' + info[1]
					+ '</td>' + '<td>' + info[2] + '</td>' + '<td>' + info[3]
					+ '</td>' + '<td>' + info[4] + '</td>' + '<td>' + info[5]
					+ '</td>'
		}
		;
	}
	function showCell(tr) {
		var temp = document.getElementById(tr);
		var name = temp.cells[0].innerHTML;
		var id = temp.cells[1].innerHTML;
		showThumbnail(name, id);
	}
	function getIcMonth() {
		var icMonthArr=new Array();
		for (var i = 0, size = dbIndustryMoney[0].length; i < size; i++) {			
			icMonthArr[i]=dbIndustryMoney[0][i]["yearMonth"];
		}
		return icMonthArr;
	}
	
	function getIbc() {
			var industryBarArr=new Array();
			for(var i=0,len=dbIndustryMoney.length;i<len;i++){
				var moneyArr=new Array();
				for(var j=0;j<12;j++){
					moneyArr[j]=dbIndustry[i][j]["industryMoney"];
				}
				industryBarArr[i]=moneyArr;
			}
		    return industryBarArr;
	}
	
	function getIndustryName(){
		var industryNameArr=new Array();
		for(var i=0,len=dbIndustry.length;i<len;i++){
			industryNameArr[i]=dbIndustry[i][0]["industryName"];
		}
		return industryNameArr;
	}
	/* return [
			[ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0,
					6.4, 3.3 ],
			[ 2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8,
					6.0, 2.3 ],
			[ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0,
					6.4, 3.3 ] ]; */
	function getIrc() {
		var result = new Array();
<%for (int i = 0, size = chartVOs.size(); i < size; i++) {%>
	result[
<%=i%>
	] = new Array();
		result[
<%=i%>
	][0] =
<%=chartVOs.get(i).getTurnover()%>
	;
		result[
<%=i%>
	][1] =
<%=chartVOs.get(i).getVolume()%>
	;
		result[
<%=i%>
	][2] =
<%=chartVOs.get(i).getAdjprice()%>
	;
		result[
<%=i%>
	][3] =
<%=chartVOs.get(i).getPe()%>
	;
		result[
<%=i%>
	][4] =
<%=chartVOs.get(i).getPb()%>
	;
<%}%>
	return result;
	}

	function skipSingle(tr) {
		var temp = document.getElementById(tr);
		var id = temp.cells[1].innerHTML;
		/* SetCookie("single",id);
		document.location = "fullPage.html"; */
		document.getElementById("skipStockId").value = id;
		document.getElementById("skipDate").value = getDate;
		document.getElementById("skipForm").submit();
	}
					
	 $(document).ready(function() {
	     var table2=$('#market').DataTable(
	    		 /* {
	    		 "lengthMenu":[[3,5,10], [3,5,10]]	
	    		 }  */
	     );
	 });

	var upColor = 'rgb(235, 80, 80)'
	var downColor = 'rgb(7, 143, 50)'
	var color5 = 'rgb(230, 212, 18)'
	var color10 = 'rgb(106, 212, 207)'
	var color20 = 'rgb(27, 60, 191)'
	var color30 = 'rgb(191, 76, 201)'
</script>

<body>
<form action="SingleServlet" method="post" id="skipForm">
		<input type="hidden" name="stockId" value="" id="skipStockId"><br>
		<input type="hidden" name="manyDay" value="20"><br> <input
			type="hidden" name="reqDate" value="" id="skipDate"><br>
	</form>
    <div id="nav">
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Flying Dutchman</a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li><a href="#">自选</a></li>
                    <li class="active"><a href="#">大盘</a></li>
                    <li><a href="#">个股</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            行业<b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" id="navDropDown">
                            <li><a href="#">详细</a></li>
                            <li><a href="#">比较</a></li>
                           <!--  <li class="divider"></li>
                            <li><a href="#">f</a></li> -->
                        </ul>
                    </li>
                </ul>
            </div>
            <div id="search">
                <form class="form-inline" role="form">
                    <input type="text" class="form-control" id="searchText">
                    <button class="btn btn-default btn-sm" id='searchButton'>
                        <span class="glyphicon glyphicon-search"></span> 搜索</button>
                </form>                   
            </div>
        </nav>
    </div>
    
    <ul id="menu">
        <li data-menuanchor="firstPage" class="active"><a href="#firstPage">大盘数据</a></li>
        <li data-menuanchor="secondPage"><a href="#secondPage">大盘图表</a></li>
        <li data-menuanchor="3rdPage"><a href="#3rdPage">行业数据与图表</a></li>
    </ul>

    <div id="fullpage">
        <div class="section" id="section0">
            <div id="marketTable">
                <table
						class="table table-striped table-bordered table-hover datatable"
						id="market">
						<thead>
							<tr>
								<th style="cursor: pointer">代码</th>
								<th style="cursor: pointer">名称</th>
								<th style="cursor: pointer">开盘价</th>
								<th style="cursor: pointer">收盘价</th>
								<th style="cursor: pointer">最高价</th>
								<th style="cursor: pointer">最低价</th>
								<th style="cursor: pointer">成交量</th>
								<th style="cursor: pointer">换手率</th>
							</tr>
						</thead>
						<tbody>
							<script>
								var str = '';
								for (var i = 0,len1=allArray.length; i < len1; i++) {
									str += "<tr id='tr"+i+1+"' onmouseover="+"showCell('tr"+i+1+"')"+" ondblclick= "+"skipSingle('tr"+i+1+"')"+">"
									for (var j=0,len2=allArray[i].length; j <len2 ; j++) {
										str += '<td>' +allArray[i][j]+ '</td>';
									}
									str += '</tr>'
								}
								document.write(str);
							</script>
						</tbody>
					</table>
            </div>
        </div>
        <div class="section" id="section1">
            <div id="MA" class="chart"></div>
            <div id="KDJ" class="chart"></div>
            <div id="VOL" class="chart"></div>
            <div id="ATR" class="chart"></div>
            <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
            <script type="text/javascript">

                require.config({
                    paths: {
                        echarts: 'http://echarts.baidu.com/build/dist'
                    }
                });
                     
                require(
                    [
                        'echarts',
                        'echarts/chart/bar',
                        'echarts/chart/line',
                        'echarts/chart/k'
                    ],
                    function (ec) { 
                        var myChart1 = ec.init(document.getElementById('MA'));
                        var myChart2 = ec.init(document.getElementById('VOL'));
                        var myChart3 = ec.init(document.getElementById('KDJ'));  
                        var myChart4 = ec.init(document.getElementById('ATR')); 
                        var data1 = getData('hs300');
                        var data2 = getVOL('hs300');
                        var data3K = getK('hs300');
                        var data3D = getD('hs300');
                        var data3J = getJ('hs300');
                        var data4 = getATR('hs300');

                        option1 = {
                            
                            // title : {
                            //     text: '2013年5月上证指数',
                            //     left: 'center'
                            // },
                            tooltip : {
                                trigger: 'axis',
                                formatter: function (params) {
                                    var res = params[0].name;
                                    res += '<br/>' + params[0].seriesName;
                                    res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : ' + params[0].value[3];
                                    res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : ' + params[0].value[2];
                                    return res;
                                }
                            },
                            // legend: {
                            //     data:['MA','VOL','KDJ','ATR']
                            // },
                            dataZoom : {
                                y: 200,
                                show : true,
                                realtime: true,
                                start : 50,
                                end : 100
                            },
                            grid: {
                                x: 50,  //left
                                y: 5,  //top
                                x2:20,  //right
                                y2:30   //bottom
                            },
                            xAxis : [
                                {
                                    type : 'category',
                                    boundaryGap : true,
                                    axisTick: {onGap:false},
                                    splitLine: {show:false},
                                    data : date()
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value',
                                    scale:true,
                                    boundaryGap: [0.05, 0.05],
                                    splitArea : {show : true}
                                }
                            ],
                            series : [
                                {
                                    name:'MA',
                                    type:'k',
                                    data:data1,
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
                                    name:'MA5',
                                    type:'line',
                                    data: calculateMA(data1, 5),
                                    smooth: true,                                      
                                    itemStyle: {
                                        normal: {color:color5}
                                    }
                                },
                                {
                                    name:'MA10',
                                    type:'line',
                                    data: calculateMA(data1, 10),
                                    smooth: true,
                                    itemStyle: {
                                        normal: {color:color10}
                                    }
                                }
                                // {
                                //     name:'VOL',
                                //     type:'bar',
                                //     data:[]
                                // },
                                // {
                                //     name:'KDJ',
                                //     type:'line',
                                //     data:[]
                                // },
                                // {
                                //     name:'ATR',
                                //     type:'line',
                                //     data:[]
                                // }
                            ]
                        };

                        option2 = {
                            tooltip : {
                                trigger: 'axis',
                                formatter: function (params) {
                                    var res = params[0].name;
                                	res += '<br/>'
										+ params[0].seriesName;
								res += '<br/>'
										+ (params[0].value / 100000000)
												.toFixed(2) + '亿';
                                    return res;
                                }
                            },
                            // legend: {
                            //     y : -30,
                            //     data:['MA','VOL','KDJ','ATR']
                            // },
                            dataZoom : {
                                show : true,
                                realtime: true,
                                start : 50,
                                end : 100
                            },
                            grid: {
                                x: 50,
                                y: 5,
                                x2:20,
                                y2:40
                            },
                            xAxis : [
                                {
                                    type : 'category',
                                    position:'top',
                                    boundaryGap : true,
                                    axisLabel:{show:false},
                                    axisTick: {onGap:false},
                                    splitLine: {show:false},
                                    data : date()
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value',
                                    scale:true,
                                    splitNumber: 5,
                                    boundaryGap: [0.05, 0.05],
                                    axisLabel: {
                                        formatter: function (v) {
                                        	return Math
											.round(v / 100000000)
											.toFixed(1)
											+ '亿';
                                        }
                                    },
                                    splitArea : {show : true}
                                }
                            ],
                            series : [
                                {
                                    name:'VOL',
                                    type:'bar',
                                    data:data2
                                },
                                {
                                    name:'VOL5',
                                    type:'line',
                                    data: calculateVOL(data2, 5),
                                    smooth: true,                                      
                                    itemStyle: {
                                        normal: {color:color5}
                                    }
                                },
                                {
                                    name:'VOL10',
                                    type:'line',
                                    data: calculateVOL(data2, 10),
                                    smooth: true,
                                    itemStyle: {
                                        normal: {color:color10}
                                    }
                                }
                            ]
                        };

                        option3 = {
                            tooltip : {
                                trigger: 'axis',
                                formatter: function (params) {
                                    var res = params[0].name;
                                    res += '<br/>' + params[0].seriesName;
                                    res += '<br/>' + params[0].value;
                                    return res;
                                }

                            },
                            // legend: {
                            //     y : -30,
                            //     data:['MA','VOL','KDJ','ATR']
                            // },
                            dataZoom : {
                                y:200,
                                show : true,
                                realtime: true,
                                start : 50,
                                end : 100
                            },
                            grid: {
                                x: 50,
                                y: 5,
                                x2:20,
                                y2:30
                            },
                            xAxis : [
                                {
                                    type : 'category',
                                    position:'bottom',
                                    boundaryGap : true,
                                    axisTick: {onGap:false},
                                    splitLine: {show:false},
                                    data : date()
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value',
                                    scale:true,
                                    boundaryGap: [0.05, 0.05],
                                    splitArea : {show : true}
                                }
                            ],
                            series : [
                                {
                                    name:'K',
                                    type:'line',
                                    data:data3K,
                                    smooth: true,                                      
                                    itemStyle: {
                                        normal: {color:color5}
                                    }                               
                                },
                                {
                                    name:'D',
                                    type:'line',
                                    data:data3D,
                                    smooth: true,                                      
                                    itemStyle: {
                                        normal: {color:color10}
                                    }                                 
                                },
                                {
                                    name:'J',
                                    type:'line',
                                    data:data3J,
                                    smooth: true,                                      
                                    itemStyle: {
                                        normal: {color:color20}
                                    }                                
                                }
                            ]
                        };

                        option4 = {
                            tooltip : {
                                trigger: 'axis',
                                formatter: function (params) {
                                    var res = params[0].name;
                                    res += '<br/>' + params[0].seriesName;
                                    res += '<br/>' + params[0].value;
                                    return res;
                                }
                            },
                            // legend: {
                            //     y : -30,
                            //     data:['MA','VOL','KDJ','ATR']
                            // },
                            dataZoom : {
                                y:200,
                                show : true,
                                realtime: true,
                                start : 50,
                                end : 100
                            },
                            grid: {
                                x: 50,
                                y: 5,
                                x2:20,
                                y2:30
                            },
                            xAxis : [
                                {
                                    type : 'category',
                                    position:'bottom',
                                    boundaryGap : true,
                                    axisTick: {onGap:false},
                                    splitLine: {show:false},
                                    data : date()
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value',
                                    scale:true,
                                    boundaryGap: [0.05, 0.05],
                                    splitArea : {show : true}
                                }
                            ],
                            series : [
                                {
                                    name:'ATR',
                                    type:'line',
                                    data:data4,
                                    smooth: true,                                      
                                    itemStyle: {
                                        normal: {color:color5}
                                    }                                      
                                }
                            ]
                        };

                        myChart1.setOption(option1);
                        myChart2.setOption(option2);
                        myChart3.setOption(option3);
                        myChart4.setOption(option4);

                        myChart1.connect([myChart2, myChart3, myChart4]);
                        myChart2.connect([myChart1, myChart3, myChart4]);
                        myChart3.connect([myChart1, myChart2, myChart4]);
                        myChart4.connect([myChart1, myChart2, myChart3]);
                    }
                );                  
            </script>
        </div>
        <div class="section" id="section2">
            <div id="industryTable">
                <table class="table table-bordered table-hover" id="industry">
                   <thead>
                      <tr>
                         <th>行业名称</th>
                         <th>换手率</th>
                         <th>成交量</th>
                         <th>后复股权价</th>
                         <th>市盈率</th>
                         <th>市净率</th>
                      </tr>
                   </thead>
                   <tbody>
                      <tr id='itr1'>
                      </tr>
                      <tr id='itr2'>
                      </tr>
                      <tr id='itr3'>
                      </tr>
                     
                   </tbody>
                </table>
            </div>
            <div class="chart" id="icBar">
                <script type="text/javascript">
                    var myChart = echarts.init(document.getElementById('icBar'));
                    var industry = getIbc();
                    var industryName = deepCopy(getIndustryName());
                    option = {
                        tooltip : {
                            trigger: 'axis'
                        },
                         legend: {
                            data: industryName
                        },
                        grid: {
                            x: 30,
                            y: 25,
                            x2:5,
                            y2:20
                        },
                        xAxis : [
                            {
                                type : 'category',
                                data : getIcMonth()
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value',
                                scale:true
                            }
                        ],
                        series : [
                            {
                                name:industryName[0],
                                type:'bar',
                                data: industry[0],
                                itemStyle: {
                                    normal: {color:'rgb(194,53,49)'}
                                } 
                            },
                            {
                                name:industryName[1],
                                type:'bar',
                                data: industry[1],
                                itemStyle: {
                                    normal: {color:'rgb(47,69,84)'}
                                }
                            },
                            {
                                name:industryName[2],
                                type:'bar',
                                data: industry[2],
                                itemStyle: {
                                    normal: {color:'rgb(97,160,168)'}
                                }
                            }
                        ]
                    };

                    myChart.setOption(option);
                </script>
            </div>
            <div class="chart" id="icPadar">
                <script type="text/javascript">
                    var myChart = echarts.init(document.getElementById('icPadar'));
                    var industry = getIrc();
                    option = {
                        
                        tooltip: {},
                        legend: {
                            data: industryName
                        },
                        grid: {
                            x: 0,
                            y: 0,
                            x2:0,
                            y2:0
                        },
                        radar: {
                            // shape: 'circle',
                            indicator: [
                               { name: '换手率', max: 10},
                               { name: '成交量', max: 10},
                               { name: '后复股权价', max: 10},
                               { name: '市盈率', max: 10},
                               { name: '市净率', max: 10}
                            ]
                        },
                        series: [{
                            // name: '行业一',
                            type: 'radar',
                            data : [
                                {
                                    value : industry[0],
                                    name : industryName[0],
                                },
                                 {
                                    value : industry[1],
                                    name : industryName[1],
                                },
                                {
                                    value : industry[2],
                                    name : industryName[2],
                                }
                            ]
                        }]
                    };

                    myChart.setOption(option);
                </script>
            </div>
        </div>
    </div>

    <div id="introduction">
        <div id="current">
            <h3 id="stockName"></h3>
            <h4 id="stockID"></h4>
            <div id="stockContent">
                <p id="openPriceLabel"></p>
                <p id="openPrice"></p>
                <p id="closePriceLabel"></p>
                <p id="closePrice"></p>
                <p id="highestPriceLabel"></p>
                <p id="highestPrice"></p>
                <p id="lowestPriceLabel"></p>
                <p id="lowestPrice"></p>
                <p id="volLabel"></p>
                <p id="vol"></p>
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
            <div id="thumbnail">                    
            </div>
        </div>
    </div>

</body>
    
</html>