<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>AnyQuantStartPage</title>

<style>
body {
	font-family: "microsoft yahei";
}
#sub{
width:80px;
height:35px;
line-height:35px;
background:url("images/jump.png") no-repeat center;
border:none;
position:relative;
margin-top:10px;
left:140px;
cursor:hand;
font-size:20px;

 }
</style>

<link href="css/sequence-theme.mono.css" rel="stylesheet" media="all">
<style>
body {
	margin: 0;
	padding: 0;
}
</style>
</head>

<script>
	function sub() {
		document.myPost.submit();
	}
</script>

<body>

	<div id="sequence" class="seq">

		<!-- <input type="button" value="G O" id="skip" onclick="sub();" /> -->
	<form
		action="ShowBenchServlet?stockId=hs300&manyDay=20&reqDate=2016-05-05"
		method="post" name="myPost">

		<input type="submit" value="" id="sub">

		</form>
			<!-- <a
				href="http://localhost/AnyQuantWeb/ShowBenchServlet?stockId=hs300&manyDay=20&reqDate=2016-05-05">GO</a>
 -->
 
		<div class="seq-jump">
		
		</div>
		<ul class="seq-canvas">


			<li class="seq-step1 seq-valign seq-in">
				<div class="seq-vcenter">
					<img data-seq class="seq-feature" src="images/instruction_1.png"
						alt="A cartoon illustration of a bunch of bananas" width="389"
						height="300"
						srcset="images/instruction_1.png 1x, images/instruction_1.png 2x" />
					<h2 data-seq class="seq-title"></h2>
				</div>
			</li>
			<li class="seq-step2 seq-valign">
				<div class="seq-vcenter">
					<img data-seq class="seq-feature" src="images/instruction_2.png"
						alt="A cartoon illustration of half a coconut" width="325"
						height="300"
						srcset="images/instruction_2.png 1x, images/instruction_2.png 2x" />
					<h2 data-seq class="seq-title"></h2>
				</div>
			</li>
			<li class="seq-step3 seq-valign">
				<div class="seq-vcenter">
					<img data-seq class="seq-feature" src="images/instruction_3.png"
						alt="A cartoon illustration of a round orange" width="350"
						height="300"
						srcset="images/instruction_3.png 1x, images/instruction_3.png 2x" />
					<h2 data-seq class="seq-title"></h2>
				</div>
			</li>

		</ul>

		<fieldset class="seq-nav" aria-label="Slider buttons"
			aria-controls="sequence">

			<button type="button" class="seq-prev" aria-label="Previous">
				<img src="images/prev.svg" alt="Previous" />
			</button>

			<ul role="navigation" aria-label="Pagination" class="seq-pagination">
				<li class="seq-current"><a href="#step1" rel="step1"
					title="Go to bananas"> <img src="images/instruction_1.1.png"
						alt="Bananas" width="50" height="40" />
				</a></li>
				<li><a href="#step2" rel="step2" title="Go to coconut"> <img
						src="images/instruction_2.1.png" alt="Coconut" width="50"
						height="40" />
				</a></li>
				<li><a href="#step3" rel="step3" title="Go to orange"> <img
						src="images/instruction_3.1.png" alt="Orange" width="50"
						height="40" />
				</a></li>
			</ul>

			<button type="button" class="seq-next" aria-label="Next">
				<img src="images/next.svg" alt="Next" />
			</button>
		</fieldset>
	</div>

	<script src="js/imagesloaded.pkgd.min.js"></script>
	<script src="js/hammer.min.js"></script>
	<script src="js/sequence.min.js"></script>
	<script src="js/sequence-theme.mono.js"></script>
	<div style="text-align: center;"></div>
</body>
</html>
