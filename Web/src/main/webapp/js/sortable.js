function ieOrFireFox(ob)  
{  
    if (ob.textContent != null)  
    return ob.textContent;  
    var s = ob.innerText;  
    return s.substring(0, s.length);  
}  
  
//排序 tableId: 表的id,iCol:第几列 ；dataType：iCol对应的列显示数据的数据类型  
function sortAble(tableId, iCol, dataType) {  
    var table = document.getElementById(tableId);  
    var tbody = table.tBodies[0];  
    var colRows = tbody.rows;  
    var aTrs = new Array;  
         //将将得到的列放入数组，备用  
    for (var i=0; i < colRows.length; i++) {  
        aTrs[i] = colRows[i];  
    }  
                        
                   
    //判断上一次排列的列和现在需要排列的是否同一个。  
    if (table.sortCol == iCol) {  
        aTrs.reverse();  
    } else {  
        //如果不是同一列，使用数组的sort方法，传进排序函数  
        aTrs.sort(compareEle(iCol, dataType));  
    }  
          
    var oFragment = document.createDocumentFragment();  
                  
    for (var i=0; i < aTrs.length; i++) {  
        oFragment.appendChild(aTrs[i]);  
    }                  
    tbody.appendChild(oFragment);  
    //记录最后一次排序的列索引  
    table.sortCol = iCol;  
}  
//将列的类型转化成相应的可以排列的数据类型  
function convert(sValue, dataType) {  
    switch(dataType) {  
    case "int":  
        return parseInt(sValue);  
    case "float":  
        return parseFloat(sValue);  
    case "date":  
        return new Date(Date.parse(sValue));  
    default:  
        return sValue.toString();  
    }  
}  
              
//排序函数，iCol表示列索引，dataType表示该列的数据类型  
function compareEle(iCol, dataType) {  
    return  function (oTR1, oTR2) {  
        var vValue1 = convert(ieOrFireFox(oTR1.cells[iCol]), dataType);  
        var vValue2 = convert(ieOrFireFox(oTR2.cells[iCol]), dataType);  
        if (vValue1 < vValue2) {  
            return -1;  
        } else if (vValue1 > vValue2) {  
            return 1;  
        } else {  
            return 0;  
        }  
       };  
} 