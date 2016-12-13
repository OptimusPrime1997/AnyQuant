<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,model.*,utility.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<meta charset="UTF-8">
<title>Select</title>

<link href="css/styles.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
	<link href="css/styles.css" rel="stylesheet">
	<link href="css/mystyle.css" rel="stylesheet">
<script src="js/script.js"></script>
	
<script src="js/bootstrap.min.js"></script>
<script src="js/funcs.js"></script>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="http://echarts.baidu.com/dist/echarts.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
</head>

<style>
#selectTable {
	top: 50px;
}

#thumb {
	position: relative;
	top: 20px;
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
<%!
	ArrayList<ArrayList<Stock>> dbStock = null;
	boolean getDataSuc = false;
%>
<%
	dbStock = (ArrayList<ArrayList<Stock>>) request.getAttribute("doubleStock");
	if(dbStock!=null){
		getDataSuc=true;
	}
%>
<script>
if(<%=getDataSuc%>==true){
	
		var dbSkArr=new Array();
		<%for(int i=0;i<dbStock.size();i++){%>
			<%ArrayList<Stock> tempList=dbStock.get(i);%>
			var dbSkArr2=new Array();
			<%for(int j=0;j<tempList.size();j++){%>
				var sObj={};
				sObj.id="<%=tempList.get(j).getId()%>";
				sObj.name="<%=tempList.get(j).getName()%>";
				sObj.date="<%=tempList.get(j).getDate()%>";
		sObj.volume =
<%=tempList.get(j).getVolume()%>
	;
		sObj.startprice =
<%=tempList.get(j).getStartprice()%>
	;
		sObj.maxprice =
<%=tempList.get(j).getMaxprice()%>
	;
		sObj.minprice =
<%=tempList.get(j).getMinprice()%>
	;
		sObj.endprice =
<%=tempList.get(j).getEndprice()%>
	;
		sObj.pe =
<%=tempList.get(j).getPe()%>
	;
		sObj.pb =
<%=tempList.get(j).getPb()%>
	;
		sObj.ajdprice =
<%=tempList.get(j).getAdjprice()%>
	;
		sObj.turnover =
<%=tempList.get(j).getTurnover()%>
	;
		sObj.money =
<%=tempList.get(j).getMoney()%>
	;
		sObj.range =
<%=tempList.get(j).getRange()%>
	;
		sObj.tr =
<%=tempList.get(j).getTr()%>
	;
		dbSkArr2[
<%=j%>
	] = sObj;
<%}%>
	dbSkArr[
<%=i%>
	] = dbSkArr2;
<%}%>
	var lastSkArr = new Array();
		for (var i = 0; i < dbSkArr.length; i++) {
			lastSkArr[i] = dbSkArr[i][dbSkArr[i].length - 1];
		}
	}

function skipSingle(tr) {
	var temp = document.getElementById(tr);
	var id = temp.cells[0].innerHTML;
	/* SetCookie("single",id);
	document.location = "fullPage.html"; */
	document.getElementById("skipStockId").value = id;
	document.getElementById("skipDate").value = lastSkArr[0]["date"];
	document.getElementById("skipManyDay").value=20;
	document.getElementById("skipForm").submit();
}
function showCell(tr) {
	var temp = document.getElementById(tr);
	var name = temp.cells[1].innerHTML;
	var id = temp.cells[0].innerHTML;
	showThumbnail(name, id,dbSkArr);
}

	$(document).ready(function() {
		$('#select').DataTable({
			bPaginate : true,
			bLengthChange : false,
			bProcessing : true
		});
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

	<form action="SingleServlet" method="post" id="skipForm">
		<input type="hidden" name="stockId" value="" id="skipStockId"><br>
		<input type="hidden" name="manyDay" value="" id="skipManyDay"><br> <input
			type="hidden" name="reqDate" value="" id="skipDate"><br>
	</form>
 </div>   
 
	<div id="nav">
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="navbar-header">
		<a  href="javascript:openme();">登录／注册</a> 
           
		
		 </div>
                 		  
                 		
		<div>
			<ul class="nav navbar-nav">
				<li class="active"><a>自选</a></li>
                <li><a onclick="subMarket()">大盘</a></li>
                <li><a onclick="subSingle()">个股</a></li>
                <li><a href="strategy.jsp">策略</a></li>
                <li><a onclick="subIndustry()">行业</a></li>
			</ul>
		</div>
		<form class="form-inline" role="form" id="navItems">
			<input type="text" class="form-control" id="searchText">
			<button class="btn btn-default btn-sm" id='searchButton'>
				<span class="glyphicon glyphicon-search"></span> 搜索
			</button>
		</form>
		</nav>
	</div>

	<div class="JLtable" id="selectTable">
		<table class="table table-bordered table-hover" id="select">
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

	<div id="introduction">

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