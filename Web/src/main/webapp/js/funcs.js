function date(stockList) {
	var dateArr = new Array();
	for (var i = 0, len = stockList.length; i < len; i++) {
		dateArr[i] = (stockList[i])["date"];
	}
	return dateArr;
}

function setInfo(id, stockList) {
	var infoArr = new Array();
	var i = stockList.length - 1;
	infoArr[0] = (stockList[i])["name"];
	infoArr[1] = (stockList[i])["id"];
	infoArr[2] = (stockList[i])["startprice"];
	infoArr[3] = (stockList[i])["endprice"];
	infoArr[4] = (stockList[i])["minprice"];
	infoArr[5] = (stockList[i])["maxprice"];
	infoArr[6] = (stockList[i])["money"];
	infoArr[7] = (stockList[i])["volume"];
	infoArr[8] = (stockList[i])["adjprice"];
	
	infoArr[9] = (stockList[i])["turnover"];
	console.log("infoArr:"+infoArr[9]);
	infoArr[10] = (stockList[i])["pe"];
	infoArr[11] = (stockList[i])["pb"];

	return infoArr;
}
function setCurrentInfo(id, stockArr) {
	var i=stockArr.length-1;
	document.getElementById('stockName').innerHTML = stockArr[i]["name"];
	document.getElementById('stockID').innerHTML = stockArr[i]["id"];
	console.log("date:"+stockArr[i]["date"]);
	document.getElementById('currentDate').innerHTML = stockArr[i]["date"];
	document.getElementById('openPriceLabel').innerHTML = '开盘价:';
	document.getElementById('openPrice').innerHTML = stockArr[i]["startprice"];
	document.getElementById('closePriceLabel').innerHTML = '收盘价:';
	document.getElementById('closePrice').innerHTML = stockArr[i]["endprice"];
	document.getElementById('highestPriceLabel').innerHTML = '最低价:';
	document.getElementById('highestPrice').innerHTML = stockArr[i]["maxprice"];
	document.getElementById('lowestPriceLabel').innerHTML = '最高价:';
	document.getElementById('lowestPrice').innerHTML = stockArr[i]["minprice"];
	document.getElementById('volLabel').innerHTML = '成交量:';
	document.getElementById('vol').innerHTML = (((stockArr[i]["volume"])/10000)).toFixed(2)+"万";
	if (stockArr[i]["name"] != '沪深300') {
		document.getElementById('ADJLabel').innerHTML = '后复权价:';
		console.log("adjprice:"+stockArr[i]["adjprice"]);
		document.getElementById('ADJ').innerHTML = stockArr[i]["ajdprice"];
		document.getElementById('turnOverLabel').innerHTML = '换手率:';
		document.getElementById('turnOver').innerHTML = stockArr[i]["turnover"];
		document.getElementById('PE').innerHTML = '市盈率: ' + stockArr[i]["pe"];
		document.getElementById('PB').innerHTML = '市净率: ' + stockArr[i]["pb"];
	}
}
function getData(id, dataStockList) {
	var KDataArr1 = new Array();
	for (var i = 0, len = dataStockList.length; i < len; i++) {
		var KDataArr2 = new Array();
		KDataArr2[0] = (dataStockList[i])["startprice"];
		KDataArr2[1] = (dataStockList[i])["endprice"];
		KDataArr2[2] = (dataStockList[i])["minprice"];
		KDataArr2[3] = (dataStockList[i])["maxprice"];
		KDataArr1[i] = KDataArr2;
	}
	return KDataArr1;
}
function getOHLCData(id, dbSkArr) {
	var resultArr1 = new Array();
	for (var i = 0, len = dbSkArr.length; i < len; i++) {
		if (id == dbSkArr[i][0]['id']) {
			for (var j = 0, len2 = dbSkArr[i].length; j < len2; j++) {
				var resultArr2 = new Array();
				resultArr2[0] = dbSkArr[i][j]['startprice'];
				resultArr2[1] = dbSkArr[i][j]['endprice'];
				resultArr2[2] = dbSkArr[i][j]['minprice'];
				resultArr2[3] = dbSkArr[i][j]['maxprice'];
				resultArr1[j] = resultArr2;
			}
			break;
		}
	}
	return resultArr1;
}
function getOHLCDate(id, dbSkArr) {
	var resultArr1 = new Array();
	for (var i = 0, len = dbSkArr.length; i < len; i++) {
		if (id == dbSkArr[i][0]['id']) {
			for (var j = 0, len2 = dbSkArr[i].length; j < len2; j++) {
				resultArr1[j] = dbSkArr[i][j]['date'];
			}
			break;
		}
	}
	return resultArr1;
}

function getVOL(id, stockList) {
	var volArr = new Array();
	for (var i = 0, len = stockList.length; i < len; i++) {
		volArr[i] =  (stockList[i])["volume"] ;
	}
	return volArr;
	/* return [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20] */
}
function getK(id, kdjArr) {
	var kArr = new Array();
	for (var i = 0, len = kdjArr.length; i < len; i++) {
		kArr[i] = (kdjArr[i])["k"];
	}
	return kArr;
	/* return [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20] */
}
function getD(id, kdjArr) {
	var dArr = new Array();
	for (var i = 0, len = kdjArr.length; i < len; i++) {
		dArr[i] = (kdjArr[i])["d"];
	}
	return dArr;
	/* return [2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21] */
}
function getJ(id, kdjArr) {
	var jArr = new Array();
	for (var i = 0, len = kdjArr.length; i < len; i++) {
		jArr[i] = (kdjArr[i])["j"];
	}
	return jArr;
	/* return [3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22] */
}
function getATR(id, stockList) {
	var trArr = new Array();
	for (var i = 0, len = stockList.length; i < len; i++) {
		trArr[i] = (stockList[i])["tr"];
	}
	return calculateVOL(trArr, 5);
	/* return [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20] */
}

function calculateMA(data0, dayCount) {
	if (data0 instanceof Array) {
		var result = [];
		for (var i = 0, len = data0.length; i < len; i++) {
			var sum = 0;
			if (i < dayCount-1) {
				/*sum = 0;
				if (i < dayCount / 3) {
					for (var k = i; k < Math.min((i + Math.floor(dayCount / 3),dayCount)); k++) {
						sum += data0[k][1];
					}
					var temp1 = (sum * 1.0) / Math.min(( Math.floor(dayCount / 3),dayCount));
					temp1 = parseFloat(temp1.toFixed(2));
					result.push(temp1);
				} else {
					for (var k = 0; k < i; k++) {
						sum += data0[k][1];
					}
					var temp1 = (sum * 1.0) / i;
					temp1 = parseFloat(temp1.toFixed(2));
					result.push(temp1);
				}*/
				result.push('-');
			} else {
				sum = 0;
				for (var j = 0; j < dayCount; j++) {
					sum += data0[i - j][1];
				}
				var temp2 = (sum * 1.0 / dayCount);
				temp2 = parseFloat(temp2.toFixed(2));
				result.push(temp2)
			}
		}
		return result;
	}
}

function calculateVOL(data0, dayCount) {
	if (data0 instanceof Array) {
		var result = [];
		for (var i = 0, len = data0.length; i < len; i++) {
			var sum = 0;
			if (i < dayCount-1) {
				/*
				for (var k = i; k < (i + Math.floor(dayCount / 2)); k++) {
					sum += data0[k];
				}
				var temp1 = (sum * 1.0) / Math.floor(dayCount / 2);
				temp1 = parseFloat(temp1.toFixed(2));
				result.push(temp1);
				continue;*/
				result.push('-');
			} else {
				for (var j = 0; j < dayCount; j++) {
					sum += data0[i - j];
				}
				var temp2 = (sum * 1.0 / dayCount);
				temp2 = parseFloat(temp2.toFixed(2));
				result.push(temp2)
			}
		}
		return result;
	}
}
function setFactoryInfo(stockId, skArr) {
	var result = new Array();
	var i = skArr.length - 1;
	result[0] = skArr[i]['name'];
	result[1] = skArr[i]['startprice'];
	result[2] = skArr[i]['endprice'];
	result[3] = skArr[i]['adjprice'];
	result[4] = skArr[i]['turnover'];
	result[5] = skArr[i]['pe'];
	result[6] = skArr[i]['pb'];
	return result;
}
function setFactoryCurrentInfo(stockId, skArr) {
	var currentInfo = setFactoryInfo(stockId, skArr);
	document.getElementById('stockName').innerHTML = currentInfo[0];
	document.getElementById('openPriceLabel').innerHTML = '平均开盘价:';
	document.getElementById('openPrice').innerHTML = currentInfo[1];
	document.getElementById('closePriceLabel').innerHTML = '平均收盘价:';
	document.getElementById('closePrice').innerHTML = currentInfo[2];
	document.getElementById('ADJLabel').innerHTML = '平均后复权价:';
	document.getElementById('ADJ').innerHTML = currentInfo[3];
	document.getElementById('turnOverLabel').innerHTML = '平均换手率:';
	document.getElementById('turnOver').innerHTML = currentInfo[4];
	document.getElementById('PE').innerHTML = '平均市盈率: ' + currentInfo[5];
	document.getElementById('PB').innerHTML = '平均市净率: ' + currentInfo[6];
}
function readCity(s){
	var str = s.split("省");
	if (str.length == 1) {
		str = str[0].split("自治区");
		if (str.length == 1) {
			str = str[0].split("市");
			return str[0];
		} else {
			str = str[1].split("市");
			return str[0];
		}
	} else {
		str = str[1].split("市");
		return str[0];
	}
}

function getScoreComment(score) {
	var str = '';
	if (score < 20) {
		str += "该股近期表现很差，尽快抛出";
	} else if (score >= 20 && score < 40) {
		str += "该股近期表现较差，不推荐买入";
	} else if (score >= 40 && score < 60) {
		str += "该股近期表现不佳";
	} else if (score >= 60 && score < 80) {
		str += "该股近期表现平稳，未来可能趋平";
	} else if (score >= 80 && score < 100) {
		str += "该股表现非常好，有较大可能会上涨，属于绩优股";
	}
	return str;
}

function deepCopy(array) {
	var tempArray = [];
	// 判断array参数不是null ,undefined,0,'',nan,false
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

function SetCookie(cookieName, cookieValue) {
	/* 当前日期 */
	var today = new Date();
	/* Cookie过期时间 */
	var expire = new Date();
	/* 如果未设置nDays参数或者nDays为0，取默认值1 */
	// if(nDays == null || nDays == 0) nDays = 1;
	/* 计算Cookie过期时间 */
	/* expire.setTime(today.getTime() + 60000); */
	/* 设置Cookie值 */
	document.cookie = cookieName + "=" + escape(cookieValue) + ";expires="
			+ expire.toGMTString();
}

function ReadCookie(cookieName) {
	var theCookie = "" + document.cookie;
	var ind = theCookie.indexOf(cookieName);
	if (ind == -1 || cookieName == "")
		return "";
	var ind1 = theCookie.indexOf(';', ind);
	if (ind1 == -1)
		ind1 = theCookie.length;
	/* 读取Cookie值 */
	return unescape(theCookie.substring(ind + cookieName.length + 1, ind1));
}


function showThumbnail(name, id,stockArr) {
	if (document.getElementById('thumbnailName').innerHTML == name) {
		return;
	}
	;
	document.getElementById('thumbnailName').innerHTML = name;
	document.getElementById('thumbnailID').innerHTML = id;

	var myChart = echarts.init(document.getElementById('thumbnail'));
	var data0 = getOHLCData(id,stockArr);

	option = {
		grid : {
			x : 45, // left
			y : 10, // top
			x2 : 15, // right
			y2 : 20
		// bottom
		},
		xAxis : {
			type : 'category',
			data : getOHLCDate(id,stockArr),
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
function showCell(tr) {
	var temp = document.getElementById(tr);
	var name = temp.cells[1].innerHTML;
	var id = temp.cells[0].innerHTML;
	showThumbnail(name, id);
}

function getCurrentDate(stockArr){
	return stockArr[stockArr.length-1]["date"];
}

function subSingle() {
	document.singlePost.submit();
}
function subMarket() {
	document.marketPost.submit();
}
function subIndustry() {
	document.factoryPost.submit();
}
function addZero(str,length){        
	return new Array(length - str.length + 1).join("0") + str;
}

function getBeforeDay(){
	var d=new Date();
	var y=d.getFullYear();
	var m=addZero((d.getMonth()+1)+"",2);
	var date=d.getDate()-1;
	return y+"-"+m+"-"+date;
}

var upColor = 'rgb(235, 80, 80)'
var downColor = 'rgb(7, 143, 50)'
var color5 = 'rgb(230, 212, 18)'
var color10 = 'rgb(106, 212, 207)'
var color20 = 'rgb(27, 60, 191)'
var color30 = 'rgb(191, 76, 201)'
