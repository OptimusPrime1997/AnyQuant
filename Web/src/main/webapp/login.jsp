<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Our CSS stylesheet file -->
<link rel="stylesheet" href="css/mystyle.css" />

<!--[if lt IE 9]>
          <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
   <script type="text/javascript" src="js/funcs.js"></script>
</head>

<body>

	<div id="formContainer">
		<form id="login" method="post" action="SelectServlet"
			onsubmit="return checkUserId(this.userId,this.userPsd);">
			<a href="http://www.jb51.net" id="flipToRecover" class="flipLink">Register?</a>
			<input type="text" name="userId" id="loginName" value="Name" /> <input
				type="password" name="userPsd" id="loginPass" value="pass" /> 
				<input type="hidden" name="manyDay" value="20"/>
				<input type="hidden" name="reqDate" value="2016-05-05"/>
				<input
				type="submit" name="submit1" value="登录" />
 		</form>
		<form id="recover" method="post" action="RegistServlet"
			onsubmit="return checkRegist();">
			<a href="#" id="flipToLogin" class="flipLink">Rgister?</a> <input
				type="text" name="userId" id="recoverName" value="Name"  /> <input
				type="password" name="userPsd" id="rePass" value="pass" /> <input
				type="password" name="conPass" id="conPass" value="pass" /> 
				<input type="hidden" name="manyDay" value=""/>
				<input type="hidden" name="reqDate" value=""/>
				<input
				type="submit" name="submit2" value="注册" />
		</form>
	</div>



	<!-- JavaScript includes -->
	<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script src="js/script.js"></script>
	<script>
	var xmlHttp;
	var flag;
	function createXMLHttp() {
		if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		} else {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	function checkUserId(userId,userPsd) {
		if(checkLogin()==false){
			return false;
		}
		createXMLHttp();
		xmlHttp.open("POST", "CheckServlet?userName=" + userId+"&userPsd="+userPsd);
		xmlHttp.onreadystatechange = checkUseridCallback;
		xmlHttp.send(null);
		return checkForm();
		/* document.getElementById("msg").innerHTML = "正在验证..."; */
	}
	function checkUseridCallback() {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				var text = xmlHttp.responseText;
				if (text == "true") {
					flag = false;
					 document.getElementById("loginName").value = "用户名或密码错误"; 
				} else {
					flag = true;
					/* document.getElementById("msg").innerHTML = "此用户ID可以注册"; */
				}
				/* document.getElementById("msg").className = "样式表名称";
				document.getElementById("msg").innerHTML = text; */
			}
		}
	}
	function checkForm() {
		return flag;
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
</body>

</html>

