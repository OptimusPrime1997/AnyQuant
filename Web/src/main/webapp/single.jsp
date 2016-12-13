<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="action.*,java.util.*,model.*,utility.NumHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<title>Single</title>

<link href="fullPage/examples/examples.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
    <link href="css/single.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript" src="fullPage/jquery.fullPage.js"></script>
    <script type="text/javascript" src="fullPage/examples/examples.js"></script>
    <script src="js/funcs.js"></script>
    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script src="http://echarts.baidu.com/dist/echarts.min.js"></script>
    <script src="circleProgress/js/radialIndicator.js"></script>
    
</head>
<style>
</style>
<%
	ArrayList<Stock> stockList = null;
	ArrayList<KDJVO> kdjList = null;
	String stockId = null;
	boolean getDataSuc = false;
	MarketState[] marketState = null;
	String stockStatus = null;
	String stockPredict = null;
	StockInfo skInfo=null;
	int score=-1;
	stockList = (ArrayList<Stock>) request.getAttribute("stockList");
	stockId = (String) request.getAttribute("stockId");
	kdjList = (ArrayList<KDJVO>) request.getAttribute("kdjList");
	marketState = (MarketState[]) request.getAttribute("marketState");

	stockStatus = (String) request.getAttribute("stockStatus");
	score=(int)request.getAttribute("score");
	skInfo=(StockInfo)request.getAttribute("stockInfo");
	stockPredict = (String) request.getAttribute("stockPredict");
	
	if (stockId != null && stockList != null && kdjList != null && marketState != null && stockStatus != null
		&& stockPredict!=null&&score!=-1&&skInfo!=null) {
		getDataSuc = true;
	}
%>
<script>

		var hasPredict = false;
<%if(getDataSuc==true){%>
	
	var stockArr=new Array();
	<%for (int i = 0, size = stockList.size(); i < size; i++) {%>
		var sObj={};
		sObj.id="<%=stockList.get(i).getId()%>";
		sObj.name="<%=stockList.get(i).getName()%>";
		sObj.date="<%=stockList.get(i).getDate()%>";
		sObj.volume=<%=stockList.get(i).getVolume()%>;
		sObj.startprice=<%=stockList.get(i).getStartprice()%>;
		sObj.maxprice=<%=stockList.get(i).getMaxprice()%>;
		sObj.minprice=<%=stockList.get(i).getMinprice()%>;
		sObj.endprice=<%=stockList.get(i).getEndprice()%>;
		sObj.pe=<%=stockList.get(i).getPe()%>;
		sObj.pb=<%=stockList.get(i).getPb()%>;
		sObj.ajdprice=<%=stockList.get(i).getAdjprice()%>;
		sObj.turnover=<%=stockList.get(i).getTurnover()%>;
		sObj.money=<%=stockList.get(i).getMoney()%>;
		sObj.range=<%=stockList.get(i).getRange()%>;
		sObj.tr=<%=stockList.get(i).getTr()%>;
		stockArr[<%=i%>]=sObj;
	<%}%>
	/* console.log("adjpice："+stockArr[stockArr.length-1]["adjprice"]); */
	
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
	
    var id = "<%=stockId%>";
    
    var predictArr=new Array();
		    <%for (int i = 0, len = marketState.length; i < len; i++) {%>
		    	var predict={};
		    	predict.date="<%=marketState[i].getDate().toString()%>";
				predict.up =
		<%=marketState[i].getUp()%>
			;
				predict.down =
		<%=marketState[i].getDown()%>
			;
				predictArr[
		<%=i%>
			] = predict;
		<%}%>
	var skStatus="<%=stockStatus%>";
	var skPredict="<%=stockPredict%>";
	<%ArrayList<Double> stockLevels=NumHelper.getDoubles(stockPredict);%>
	var skPreArr=new Array();
	<%for(int i=0,size=stockLevels.size();i<size;i++){%>
		skPreArr[<%=i%>]=<%=stockLevels.get(i)%>;
	<%}%>
	/* console.log(skPreArr); */
	var jsScore=<%=score%>;
	
	var skInfoObj={};
	skInfoObj.stockId="<%=skInfo.getStockId()%>";
	skInfoObj.secFullName="<%=skInfo.getSecFullName()%>";
	skInfoObj.officeAddr="<%=skInfo.getOfficeAddr()%>";
	skInfoObj.primeOperation="<%=skInfo.getPrimeOperation()%>";
<%}%>


var add = 0;

function init() {
    var temp = ReadCookie("single");
    if (temp!="") {
        id = temp;
    };
    $('#fullpage').fullpage({
        // sectionsColor: ['#fff', '#fff', '#fff'],
        anchors: ['firstPage', 'secondPage', '3rdPage', '4thPage', '5thPage', '6thPage'],
        menu: '#menu',
        loopTop: true,
        loopBottom: true,

        'afterLoad': function(anchorLink, index){
            if(index == 6&&(!hasPredict)){
                realScore();
                hasPredict = true;
            }
        },
    });
}
	function getBeforePredict(id) {
		var existArr = new Array();
		var start = stockArr.length - 5;
		for (var i = start, len = stockArr.length; i < len; i++) {
			existArr[i - start] = (stockArr[i])["endprice"];
		}
		return existArr;
		/* return [4,5,6,7,8] */
	}
	function getPredict(id) {
		var len = predictArr.length;
		var preArr = new Array();
		var preArr2 = new Array();
		var preArr3 = new Array();
		for (var i = 0; i < len; i++) {
			preArr2[i] = predictArr[i]["up"];
			preArr3[i] = predictArr[i]["down"];
		}
		preArr[0] = preArr2;
		preArr[1] = preArr3;
		return preArr;
		/* return [
		    [7,8,9],
		    [3,4,5]
		] */
	}
	function predictDate() {
		var preDateArr = new Array();
		var allLength = stockArr.length;
		var start = stockArr.length - 5;
		for (var i = start, len = stockArr.length; i < len; i++) {
			preDateArr[i - start] = (stockArr[i])["date"];
		}
		for (var j = 0, len2 = predictArr.length; j < len2; j++) {
			preDateArr[i - start + j] = (predictArr[j])["date"];
		}
		/* console.log(predictArr);
		console.log("preDateArr:");
		console.log(preDateArr); */
		return preDateArr;
	}
	
	function getScore(id) {	
		return jsScore;
	}
	
	function getPointerPos(){
	    return Math.round(skPreArr[0]);
	}

	function realScore(){
	    var score0 = getScore(id);
	    var radialObj4 = $('#score').radialIndicator({
	        barColor: {
	            0: '#FF0000',
	            33: '#FFFF00',
	            66: '#0066FF',
	            100: '#33CC33'
	        },
	        radius : 100
	    }).data('radialIndicator');
	    radialObj4.animate(score0);
	}
	function barPointer(){
	    var pos = getPointerPos();
	    var temp = document.getElementById("bar");
	    temp.style.backgroundImage = "url("+'images/bar_'+pos+'.png'+")";
	}
	
	 $(document).ready(function() {
	    init();
	    barPointer();
	    setCurrentInfo(id,stockArr);
	});

</script>

<body>
<div id="formContent" style="display:none">
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
                    <li class="active"><a>个股</a></li>
                    <li><a href="strategy.jsp">策略</a></li>
                    <li><a onclick="subIndustry()">行业</a></li>
                </ul>
			</div>
		</nav>
	</div>

    <ul id="menu">
        <li data-menuanchor="firstPage" class="active"><a href="#firstPage">K线图</a></li>
        <li data-menuanchor="secondPage"><a href="#secondPage">VOL折线图</a></li>
        <li data-menuanchor="3rdPage"><a href="#3rdPage">KDJ折线图</a></li>
        <li data-menuanchor="4thPage"><a href="#4thPage">ATR折线图</a></li>
        <li data-menuanchor="5thPage"><a href="#5thPage">股价预测</a></li>
        <li data-menuanchor="6thPage"><a href="#6thPage">股票打分</a></li>
     </ul>

    <div id="fullpage">
        <div class="section" id="section0">
            <div class="chart" id="MA">
                <script type="text/javascript">
                    var myChart = echarts.init(document.getElementById('MA'));
                    var data0 = getData(id,stockArr);

                    option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter: function (params) {
                                var res = params[0].name;
                                res += '<br/>' + params[0].seriesName;
                                res += '<br/>  开盘 : ' + params[0].value[0] + '  收盘 : ' + params[0].value[1];
                                res += '<br/>  最低 : ' + params[0].value[2] + '  最高 : ' + params[0].value[3];
                                return res;
                            }
                        },
                        dataZoom : {
                            show : true,
                            realtime: true,
                            start : 50,
                            end : 100
                        },
                        grid: {
                            x: 70,
                            y: 10,
                            x2:90,
                            y2:65
                        },
                        xAxis : [
                            {
                                type : 'category',
                                boundaryGap : true,
                                axisTick: {onGap:false},
                                splitLine: {show:false},
                                data : date(stockArr)
                            }
                        ],
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
                                },
                                markLine:{
                                    data:[
                                        {
                                            name: '最高',
                                            yAxis: 2400
                                        },
                                        {
                                            name: '最低',
                                            yAxis: 2300
                                        }
                                    ]
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

                    myChart.setOption(option);
                </script>
            </div>          
        </div>
        <div class="section" id="section1">
            <div class="chart" id="VOL">
                <script type="text/javascript">
                    var myChart = echarts.init(document.getElementById('VOL'));
                    var data0 = getVOL(id,stockArr);
                    option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter: function (params) {
                                var res = params[0].name;
                                res += '<br/>' + params[0].seriesName;
                                res += '<br/>' + ((params[0].value/10000)).toFixed(2);
                                return res;
                            }
                        },
                        dataZoom : {
                            show : true,
                            realtime: true,
                            start : 50,
                            end : 100
                        },
                        grid: {
                            x: 70,
                            y: 10,
                            x2:90,
                            y2:65
                        },
                        xAxis : [
                            {
                                type : 'category',
                                boundaryGap : true,
                                axisTick: {onGap:false},
                                splitLine: {show:false},
                                data : date(stockArr)
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
                                        return ((v/10000)).toFixed(2) +'万'
                                    }
                                },
                                splitArea : {show : true}
                            }
                        ],
                        series : [
                            {
                                name:'VOL',
                                type:'bar',
                                data:data0
                            },
                            {
                                name:'VOL5',
                                type:'line',
                                data: calculateVOL(data0, 5),
                                smooth: true,                                      
                                itemStyle: {
                                    normal: {color:color5}
                                }
                            },
                            {
                                name:'VOL10',
                                type:'line',
                                data: calculateVOL(data0, 10),
                                smooth: true,
                                itemStyle: {
                                    normal: {color:color10}
                                }
                            }
                        ]
                    };
                    myChart.setOption(option);
                </script>
            </div>
        </div>
        <div class="section" id="section2">
            <div class="chart" id="KDJ">
                <script type="text/javascript">
                    var myChart = echarts.init(document.getElementById('KDJ'));
                    var dataK = getK(id, kdjArr);
					var dataD = getD(id, kdjArr);
					var dataJ = getJ(id, kdjArr);
                    option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter: function (params) {
                                var res = params[0].name;
                                res += '<br/>' + params[0].seriesName;
                                res += '<br/>' + params[0].value;
                                return res;
                            }

                        },
                        dataZoom : {
                            show : true,
                            realtime: true,
                            start : 50,
                            end : 100
                        },
                        grid: {
                            x: 70,
                            y: 10,
                            x2:90,
                            y2:65
                        },
                        xAxis : [
                            {
                                type : 'category',
                                position:'bottom',
                                boundaryGap : true,
                                axisTick: {onGap:false},
                                splitLine: {show:false},
                                data : date(stockArr)
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
                                data:dataK,
                                smooth: true,                                      
                                itemStyle: {
                                    normal: {color:color5}
                                }                               
                            },
                            {
                                name:'D',
                                type:'line',
                                data:dataD,
                                smooth: true,                                      
                                itemStyle: {
                                    normal: {color:color10}
                                }                                 
                            },
                            {
                                name:'J',
                                type:'line',
                                data:dataJ,
                                smooth: true,                                      
                                itemStyle: {
                                    normal: {color:color20}
                                }                                
                            }
                        ]
                    };
                    myChart.setOption(option);
                </script>
            </div>
        </div>
        <div class="section" id="section3">
            <div class="chart" id="ATR">
                <script type="text/javascript">
                    var myChart = echarts.init(document.getElementById('ATR'));
                    var data0 = getATR(id,stockArr);
                    option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter: function (params) {
                                var res = params[0].name;
                                res += '<br/>' + params[0].seriesName;
                                res += '<br/>' + params[0].value;
                                return res;
                            }
                        },
                        dataZoom : {
                            show : true,
                            realtime: true,
                            start : 50,
                            end : 100
                        },
                        grid: {
                            x: 70,
                            y: 10,
                            x2:90,
                            y2:65
                        },
                        xAxis : [
                            {
                                type : 'category',
                                position:'bottom',
                                boundaryGap : true,
                                axisTick: {onGap:false},
                                splitLine: {show:false},
                                data : date(stockArr)
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
                                data:data0,
                                smooth: true,                                      
                                itemStyle: {
                                    normal: {color:color5}
                                }                                      
                            }
                        ]
                    };
                    myChart.setOption(option);
                </script>
            </div>
        </div>
        <div class="section" id="section4">
            <div class="chart" id="predict">
                <script type="text/javascript">
                    var myChart = echarts.init(document.getElementById('predict'));
                    var date0 = predictDate();
                    var data1 = getBeforePredict(id);
                    var data02 = getPredict(id)[0];
                    var data03 = getPredict(id)[1];
                    var data2 = ['-','-','-','-'];
                    var data3 = ['-','-','-','-'];
                    data2.push(data1[data1.length-1]);
                    data3.push(data1[data1.length-1]);
                    for (var i = 0; i < 3; i++) {
                        data2.push(data02[i]);
                        data3.push(data03[i]);
                    };
                    option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter: function (params) {
                                if (params[0].name<=date0[4]) {
                                    var res = params[2].name;
                                    res += '<br/>' + params[2].seriesName;
                                    res += ' ' + params[2].value;
                                    return res;
                                };
                                if (params[0].name>=date0[5]) {
                                    var res = params[0].name;
                                    res += '<br/>' + params[0].seriesName;
                                    res += ' ' + params[0].value;
                                    res += '<br/>' + params[1].seriesName;
                                    res += ' ' + params[1].value;
                                    return res;
                                };
                            }
                        },
                        grid: {
                            x: 70,
                            y: 10,
                            x2:90,
                            y2:65
                        },
                        xAxis : [
                            {
                                type : 'category',
                                position:'bottom',
                                boundaryGap : true,
                                axisTick: {onGap:false},
                                splitLine: {show:false},
                                data : date0
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
                                name:'预测最高价',
                                type:'line',
                                data:data2,
                                smooth: true,                                      
                                itemStyle: {
                                    normal: {color:color10}
                                }                                      
                            },
                            {
                                name:'预测最低价',
                                type:'line',
                                data:data3,
                                smooth: true,                                      
                                itemStyle: {
                                    normal: {color:color20}
                                }                                      
                            },
                            {
                                name:'股票价格',
                                type:'line',
                                data:data1,
                                smooth: true,                                      
                                itemStyle: {
                                    normal: {color:color5}
                                }                                      
                            }
                        ]
                    };
                    myChart.setOption(option);
                </script>
            </div>
        </div>
        <div class="section" id="section5">
            <div id="score">
            </div>
            <div class="comment" id="scoreComment">
            <script>
           		 document.write(getScoreComment(jsScore));
            </script>
            </div>
            <div id="bar">
            </div>
            <div class="comment" id="barComment">
            <script>
           		 document.write(skPredict);
            </script>
            </div>
        </div>
    </div>

    <div id="introduction">
        <div id="current">
            <h3 id="stockName"></h3>
            <h4 id="stockID"></h4>
            <p id="currentDate"></p>
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
        <div id="info">
        	<div id="fullName">
                <h3>公司全称</h3>
                <p>
                <script>
            	    document.write(skInfoObj["secFullName"]);
                </script>
                </p>
            </div>
            <div id="address">
                <h3>总部地址</h3>
                <p>
                <script>
                document.write(skInfoObj["officeAddr"]);
                </script>
                </p>
            </div>
            <div id="businessScope">
                <h3>经营范围</h3>
                <p>
                <script>
                	document.write(skInfoObj["primeOperation"]);
                </script>
                </p>
            </div>
            <div id="moneyFlow">
                <h3>资金流向</h3>
                <p>
                <script>
                	document.write(skStatus);
                </script>
                </p>
            </div>
        </div>
        
    </div>
    

</body>

</html>
