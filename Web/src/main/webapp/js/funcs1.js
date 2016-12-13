function date(){
    return [
        '2013/5/2',
        '2013/5/3',
        '2013/5/6',
        '2013/5/7',
        '2013/5/8',
        '2013/5/9',
        '2013/5/10',
        '2013/5/13',
        '2013/5/14',
        '2013/5/15',
        '2013/5/16',
        '2013/5/17',
        '2013/5/20',
        '2013/5/21',
        '2013/5/22',
        '2013/5/23',
        '2013/5/24',
        '2013/5/27',
        '2013/5/28',
        '2013/5/29'
     ]; 
}

function setInfo(id){
    if (id=='sh000001') {
        return ['上证指数', id, 11.11, 22.22, 33.33, 44.44, '5.55万']
    }
    else{
        return ['个股', id, 11.11, 22.22, 33.33, 44.44, '5.55万', 66.66, 77.77, 88.88, 99.99]
    }
    
}

function setCurrentInfo(id){
    var currentInfo = setInfo(id);
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
    if (currentInfo[0]!='hs300') 
        {document.getElementById('ADJLabel').innerHTML = '后复权价:';
        document.getElementById('ADJ').innerHTML = currentInfo[7];
        document.getElementById('turnOverLabel').innerHTML = '换手率:';
        document.getElementById('turnOver').innerHTML = currentInfo[8];
        document.getElementById('PE').innerHTML = '市盈率: '+currentInfo[9];
        document.getElementById('PB').innerHTML = '市净率: '+currentInfo[10]; 
    }      
}

function setFactoryInfo(name){
    return [name, 11.11, 22.22, 33.33, 44.44, 55.55, 66.66]
}


function getData(id){
    return [ // 开盘，收盘，最低，最高
        [2320.26,2302.6,2287.3,2362.94],
        [2300,2291.3,2288.26,2308.38],
        [2295.35,2346.5,2295.35,2346.92],
        [2347.22,2358.98,2337.35,2363.8],
        [2360.75,2382.48,2347.89,2383.76],
        [2383.43,2385.42,2371.23,2391.82],
        [2377.41,2419.02,2369.57,2421.15],
        [2425.92,2428.15,2417.58,2440.38],
        [2411,2433.13,2403.3,2437.42],
        [2432.68,2434.48,2427.7,2441.73],
        [2430.69,2418.53,2394.22,2433.89],
        [2416.62,2432.4,2414.4,2443.03],
        [2441.91,2421.56,2415.43,2444.8],
        [2420.26,2382.91,2373.53,2427.07],
        [2383.49,2397.18,2370.61,2397.94],
        [2378.82,2325.95,2309.17,2378.82],
        [2322.94,2314.16,2308.76,2330.88],
        [2320.62,2325.82,2315.01,2338.78],
        [2313.74,2293.34,2289.89,2340.71],
        [2297.77,2313.22,2292.03,2324.63]
    ]
}

function getVOL(id){
    return [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
}
function getK(id){
    return [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
}
function getD(id){
    return [2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21]
}
function getJ(id){
    return [3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22]
}
function getATR(id){
    return [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
}

function calculateMA(data0, dayCount) {
    var result = [];
    for (var i = 0, len = data0.length; i < len; i++) {
        if (i < dayCount) {
            result.push('-');
            continue;
        }
        var sum = 0;
        for (var j = 0; j < dayCount; j++) {
            sum += data0[i - j][1];
        }
        result.push(sum / dayCount);
    }
    return result;
}

function calculateVOL(data0, dayCount){
    var result = [];
    for (var i = 0, len = data0.length; i < len; i++) {
        if (i < dayCount) {
            result.push('-');
            continue;
        }
        var sum = 0;
        for (var j = 0; j < dayCount; j++) {
            sum += data0[i - j];
        }
        result.push(sum / dayCount);
    }
    return result;
}

function SetCookie(cookieName,cookieValue) {
    /*当前日期*/
    var today = new Date();
    /*Cookie过期时间*/
    var expire = new Date();
    /*如果未设置nDays参数或者nDays为0，取默认值1*/
    // if(nDays == null || nDays == 0) nDays = 1;
    /*计算Cookie过期时间*/
    expire.setTime(today.getTime() + 5184000000);
    /*设置Cookie值*/
    document.cookie = cookieName + "=" + escape(cookieValue)
        + ";expires=" + expire.toGMTString();
}

function ReadCookie(cookieName) {
    var theCookie = "" + document.cookie;
    var ind = theCookie.indexOf(cookieName);
    if(ind==-1 || cookieName=="") return "";
    var ind1 = theCookie.indexOf(';',ind);
    if(ind1==-1) ind1 = theCookie.length;
    /*读取Cookie值*/
    return unescape(theCookie.substring(ind+cookieName.length+1,ind1));
}

function showThumbnail(name, id){
    if (document.getElementById('thumbnailName').innerHTML == name) {
        return;
    };
    document.getElementById('thumbnailName').innerHTML = name;
    document.getElementById('thumbnailID').innerHTML = id;
    
    var myChart = echarts.init(document.getElementById('thumbnail'));
    var data0 = getData(id);

    option = {
        grid: {
            x: 45,  //left
            y: 10,  //top
            x2:15,  //right
            y2:20   //bottom
        },
        xAxis: {
            type: 'category',
            data: date(),
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

    myChart.setOption(option);
}
function showCell(tr){
    var temp = document.getElementById(tr);
    var name = temp.cells[0].innerHTML;
    var id = temp.cells[1].innerHTML;
    showThumbnail(name, id);
}

var upColor = 'rgb(235, 80, 80)'
var downColor = 'rgb(7, 143, 50)'
var color5 = 'rgb(230, 212, 18)'//黄
var color10 = 'rgb(106, 212, 207)'//绿
var color20 = 'rgb(27, 60, 191)'//蓝
var color30 = 'rgb(191, 76, 201)'//紫







