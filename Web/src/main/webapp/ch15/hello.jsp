<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>anyquant,股票信息系统</title>
</head>
<body>
	<h1>AnyQuant股票信息系统</h1>
	<br>
	<script>
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
		var array1 = [ [ 1, 2, 3 ], 2, 3, 4, 5 ];
		var array2 = [];
		array2 = deepCopy(array1);
		console.log(array1);
		console.log(array2);
		
		var obj={};
		obj.name="TestObject";
		obj.array=[1,[10,9,8],2,3];
		console.log("-----Test Object---");
		console.log(obj);

		console.log("---change the array1 value");
		array1[0][0] = 10;
		console.log(array1);
		console.log(array2);
		document.write(array2);
		document.write("<h1>-----end the debug info!----<h1>"); 
	/* 	Array.prototype.deepCopy = function(array){
		    var tempArray = [];
		    //判断array参数不是null ,undefined,0,'',nan,false
		    if (array){
		        for (var i =0,len = array.length;i<len;i++){
		            tempArray[i] = array[i];
		        }
		        return tempArray;
		    }
		    return null;
		}
		var array1 =[1,2,3,4];
		var array2 = array1;
		var array3 = [];
		array2[2] =5;
		console.log(array1);
		console.log(array2);

		array3 = array3.deepCopy(array1);
		console.log(array3);
		array3[0] = 78;
		console.log(array1);
		console.log(array3); */
	</script>


</body>
</html>