<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="action.*,java.util.*,model.*,utility.NumHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<title>single</title>

<link href="fullPage/examples/examples.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
    <link href="css/single.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/mystyle.css" rel="stylesheet">
  <script src="js/script.js"></script>
  
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
<%
	ArrayList<Stock> stockList = null;
	ArrayList<KDJVO> kdjList = null;
	String stockId = null;
	boolean getDataSuc = false;
/* 	MarketState[] marketState = null; */
	String stockStatus = null;
	String stockPredict = null;
	StockInfo skInfo=null;
	int score=-1;
	stockList = (ArrayList<Stock>) request.getAttribute("stockList");
	stockId = (String) request.getAttribute("stockId");
	kdjList = (ArrayList<KDJVO>) request.getAttribute("kdjList");
	/* marketState = (MarketState[]) request.getAttribute("marketState");
 */
	stockStatus = (String) request.getAttribute("stockStatus");
	score=(int)request.getAttribute("score");
	skInfo=(StockInfo)request.getAttribute("stockInfo");
	stockPredict = (String) request.getAttribute("stockPredict");
	
	if (stockId != null && stockList != null && kdjList != null 
			/* && marketState != null */ 
			&& stockStatus != null
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
    
    <%-- var predictArr=new Array();
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
		<%}%> --%>
	var skStatus="<%=stockStatus%>";
	var skPredict="<%=stockPredict%>";
	<%ArrayList<Double> stockLevels=NumHelper.getDoubles(stockPredict);%>
	var skPreArr=new Array();
	<%for(int i=0,size=stockLevels.size();i<size;i++){%>
		skPreArr[<%=i%>]=<%=stockLevels.get(i)%>;
	<%}%>
	
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
        anchors: ['firstPage', 'secondPage', '3rdPage', '4thPage', '5thPage'],
        menu: '#menu',
        loopTop: true,
        loopBottom: true,

        'afterLoad': function(anchorLink, index){
            if(index == 5&&(!hasPredict)){
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
		console.log(predictArr);
		console.log("preDateArr:");
		console.log(preDateArr);
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
	function changeAddPic(){
	    var temp = document.getElementById("addButton");
	    
	    if (add==0) {
	        temp.style.backgroundImage = "url("+'images/button-add-1.png'+")";
	        add = 1;
	    }
	    else{
	        temp.style.backgroundImage = "url("+'images/button-add.png'+")";
	        add = 0;
	    }
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
	 function openme(){
			
			document.getElementById('div2').style.display='block';
			}
		 function checkLogin() {
				var userName = document.getElementById("loginName");
				var userPsd = document.getElementById("loginPass");
				if (!(/^\w{6,15}$/.test(userName.value))) {
					userName.value = ("用户名必须是6-15位!");
					/* userName.focus(); */
					return false;
				}
				if (!(/^\w{6,15}$/.test(userPsd.value))) {
					userName.value = userName.value+("密码必须是6-15位!");
					userPsd.value = ("");
					 userPsd.focus(); 
					return false;
				}
				console.log("check login:return true;");
				var form=document.getElementById("login");
				form.manyDay.value=20;
				form.reqDate.value="2016-05-05";
				form.submit();
				return true;
			}
			function checkRegist() {
				var userName = document.getElementById("recoverName");
				var userPsd = document.getElementById("rePass");
				var confirmPsd = document.getElementById("rePass");
				if (!(/^\w{6,15}$/.test(userName.value))) {
					userName.value = ("用户名必须是6-15位!");
					/* userName.focus(); */
					return false;
				}
				if (!(userPsd === confirmPsd)) {
					userName.value = userName.value+"两次密码输入不符!";
					confirmPsd.value = '';
					userPsd.value='';
					 userPsd.focus(); 
					return false;
				}
				if (!(/^\w{6,15}$/.test(userPsd.value))) {
					userName.value = userName.value+("密码必须是6-15位!");
					userPsd.value="";
					confirmPsd.value = '';
					 userPsd.focus(); 
					return false;
				}
				console.log("checkRegist:return true");
				return true;
			}
			function clear(t){
				f=document.getElementById(t);
				if(f.value=="密码必须是6-15为!"){
					f.value='';
				}else if(f.value=="用户名必须是6-15位!"){
					f.value='';
				}else if(f.value=="两次密码输入不符!"){
					f.value='';
				}			
				console("cusor into "+t+" input");
			}

</script>

<body>
<div id="div2">
<div id="formContainer" style="display:none">
   <form id="login" method="post" action="LoginServlet"
			onsubmit="return checkLogin();">
			<a href="#" id="flipToRecover" class="flipLink">Register?</a>
			<input type="text" name="userId" id="loginName" value="Name" /> <input
				type="password" name="userPsd" id="loginPass" value="pass" /> 
				<input type="hidden" name="manyDay" value="20"/>
				<input type="hidden" name="reqDate" value="2016-05-05"/>
				<input
				type="submit" name="submit1" value="登录" />
 		</form>
		<form id="recover" method="post" action="RegistServlet"
			onsubmit="return checkRegist();">
			<a href="#" id="flipToLogin" class="flipLink">Rgister?</a> 
			<input type="text" name="userId" id="recoverName" value="Name"  /> 
			<input
				type="password" name="userPsd" id="rePass" value="pass" /> 
				<input
				type="password" name="conPass" id="conPass" value="pass" /> 
				<input type="hidden" name="manyDay" value=""/>
				<input type="hidden" name="reqDate" value=""/>
				<input
				type="submit" name="submit2" value="注册" />
		</form>
    <!-- <div class="ft"></div> -->
  </div>
  <!-- <span class="sd"></span> -->
</div>


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
	<form
		action="SelectServlet?manyDay=20&reqDate=2016-05-05"
		method="post" name="selectPost">
	</form>
    <div id="nav">

     <div id="nav">
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="navbar-header">
                 <a  href="javascript:openme();">登录／注册</a>
           
                 
                             </div>
                               
                             
            <div>
                <ul class="nav navbar-nav">
                    <li><a onclick="subSelect()">自选</a></li>
                    <li><a onclick="subMarket()">大盘</a></li>
                    <li class="active"><a>个股</a></li>
                    <li><a href="strategy.jsp">策略</a></li>
                    <li><a onclick="subIndustry()">行业</a></li>
                </ul>
            </div>
            <form class="form-inline" role="form" id="navItems">
                <button type="button" class="btn btn-default btn-sm" id='addButton' onclick="changeAddPic()">
                </button>
                <input type="text" class="form-control" id="searchText">
                <button type="button" class="btn btn-default btn-sm" id='searchButton'>
                    <span class="glyphicon glyphicon-search"></span> 搜索</button>
            </form>                   
        </nav>
    </div>

    <ul id="menu">
        <li data-menuanchor="firstPage" class="active"><a href="#firstPage">K线图</a></li>
        <li data-menuanchor="secondPage"><a href="#secondPage">VOL折线图</a></li>
        <li data-menuanchor="3rdPage"><a href="#3rdPage">KDJ折线图</a></li>
        <li data-menuanchor="4thPage"><a href="#4thPage">ATR折线图</a></li>
        <li data-menuanchor="5thPage"><a href="#5thPage">股票打分</a></li>
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
                                res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : ' + params[0].value[3];
                                res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : ' + params[0].value[2];
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
                                res += '<br/>' + (params[0].value/10000).toFixed(2)+'万';
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
                                        return Math.round(v/10000).toFixed(2) + ' 万'
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
