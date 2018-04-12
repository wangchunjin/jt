<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>bar</title>
<link rel="stylesheet" href="${sessionScope._CssFile }/css/module.css" type="text/css" media="all" />
<script language="javascript">
var toogle = parent.document.getElementById("Toogle");
var rows_str1=toogle.parentNode.rows;
function down()
{
	var rows_str=toogle.parentNode.rows;
	if(rows_str!=rows_str1)
	{
		toogle.parentNode.rows=rows_str1;
		document.getElementById("Arr").src="${sessionScope._CssFile }/img/change_down.gif";
		document.getElementById("Arr1").style.display = "";
	}else
	{
		var count = 0;
		var toogleNextSibling = toogle.nextSibling;
		while(toogleNextSibling)
		{
			count+=1;
			toogleNextSibling = toogleNextSibling.nextSibling;
		}
		var rows_arr=rows_str.split(",");
		var new_rows="";
		for(var i=0;i<rows_arr.length-count;i++)
		{
			new_rows += new_rows.length>0 ? ","+rows_arr[i]:rows_arr[i];
		}
		var new_arr=new_rows.split(",");
		new_arr[new_arr.length-2] = "*";
		toogle.parentNode.rows=new_arr;
		document.getElementById("Arr").src="${sessionScope._CssFile }/img/change_up.gif";
		document.getElementById("Arr1").style.display = "none";
	}
}
function doup()
{
	var rows_str=toogle.parentNode.rows;
	if(rows_str!=rows_str1)
	{
		toogle.parentNode.rows=rows_str1;
		document.getElementById("Arr1").src="${sessionScope._CssFile }/img/change_up.gif";
		document.getElementById("Arr").style.display = "";
	}else
	{
		var count = 0;
		var tooglePreviousSibling = toogle.previousSibling;
		while(tooglePreviousSibling)
		{
			count+=1;
			tooglePreviousSibling = tooglePreviousSibling.previousSibling;
		}
		var rows_arr=rows_str.split(",");
		var new_rows="";
		for(var i=0;i<rows_arr.length;i++)
		{
			if(i==0)
				new_rows = rows_arr[i];
			if(i!=0 && i < count)
				new_rows += new_rows.length>0 ? ",0" : "0";
		}
		new_rows = new_rows + ",10,25,*";
		toogle.parentNode.rows=new_rows;
		document.getElementById("Arr1").src="${sessionScope._CssFile }/img/change_down.gif";
		document.getElementById("Arr").style.display = "none";
	}
}




window.onload=function(){
	  objDiv = document.getElementById('drag');
	  drag(objDiv);
	};

	function drag(dv){
		  dv.onmousedown=function(e){
		      var d=document;
		      e = e || window.event;
		      
		      var x= e.layerX || e.offsetX;
		      var y= e.layerY || e.offsetY;
		      //设置捕获范围
		      if(dv.setCapture){
		          dv.setCapture();
		      }else if(window.captureEvents){
		          window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
		      }
		      

		      d.onmousemove=function(e){
		           e= e || window.event;
		           if(!e.pageX)e.pageX=e.clientX;
		           if(!e.pageY)e.pageY=e.clientY;
		           var tx=e.pageX-x;
		           var ty=e.pageY-y;
		          var h=$(toogle).offset().top+ty;
		          var TwoFrame = parent.document.getElementById("TwoFrame");
		          var FourFrame = parent.document.getElementById("FourFrame");
		          var hh=$(FourFrame).height();
		         $(toogle).offset({top:h,left:tx});
		         var rows_str=toogle.parentNode.rows;
		      	//toogle.parentNode.rows=rows_str.substring(0,rows_str.lastIndexOf(","))+","+(hh-e.clientY);
		      	var rowslength=rows_str.split(",").length;
		      	if(rowslength==4)
		      		toogle.parentNode.rows=rows_str.substring(0,rows_str.lastIndexOf(","))+","+(hh-e.clientY);
		      	else if(rowslength==5)
		       		toogle.parentNode.rows=rows_str.split(",")[0]+",*,1,26,"+(hh-e.clientY);
		    	else if(rowslength==6)
		      		toogle.parentNode.rows=rows_str.split(",")[0]+","+(rows_str.split(",")[1])+",*,1,26,"+(hh-e.clientY);
		      	else if(rowslength==7)
		      		toogle.parentNode.rows=rows_str.split(",")[0]+","+(rows_str.split(",")[1])+",*,1,26,"+(hh-e.clientY)+",40";
		       	
		      };

		      d.onmouseup=function(){
		           //取消捕获范围
		           if(dv.releaseCapture){
		              dv.releaseCapture();
		           }else if(window.captureEvents){
		              window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
		           }
		           
		          //清除事件
		          d.onmousemove=null;
		          d.onmouseup=null;
		      };
		   };
		}
</script>
</head>
<body  style="margin: 0 5px;padding: 0">
<div id="drag" style="margin:0; float:left;z-index:2; width:100%;height:1;text-align:center;border-top: 3 solid #efefef;cursor:s-resize;">
<%-- 	<img style="cursor:hand;border:0px;" id="Arr" src="${sessionScope._CssFile }/img/change_down.gif" onClick="down()">
	<img style="cursor: hand;border:0px;" id="Arr1" src="${sessionScope._CssFile }/img/change_up.gif" onClick="doup()"> --%>
</div>
</body>
</html>