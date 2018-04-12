<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/common/header.jsp"%>
<title>canvas与图片旋转</title>
<style type="text/css">
.l{float:left;}.r{float:right;}.fix{overflow:hidden; zoom:1;}
.image_box{text-align:center;}
.image_box img{margin-top:-150px;}
</style>
<!-[if lt IE 9]><script type="text/javascript" src="${ctx}/webResources/js/excanvas.js"></script><![endif]-> 
 
<script type="text/javascript">

 function AutoResizeImage(maxWidth,maxHeight,objImg){
 var img = new Image();
 img.src = objImg.src;
 var hRatio;
 var wRatio;
 var Ratio = 1;
 var w = img.width;
 var h = img.height;
 wRatio = maxWidth / w;
 hRatio = maxHeight / h;
 if (maxWidth ==0 && maxHeight==0){
 Ratio = 1;
 }else if (maxWidth==0){//
 if (hRatio<1) Ratio = hRatio;
 }else if (maxHeight==0){
 if (wRatio<1) Ratio = wRatio;
 }else if (wRatio<1 || hRatio<1){
 Ratio = (wRatio<=hRatio?wRatio:hRatio);
 }
 if (Ratio<1){
 w = w * Ratio;
 h = h * Ratio;
 }
 
 objImg.height = h;
 objImg.width = w;
 
 }
  function rotate(canvas,img,rot){  
		//获取图片的高宽
		var w = img.width;
		var h = img.height;
		if(w=="0"){
	 
			document.getElementById("rotImg").style.display ="block";
			document.getElementById("OpButton").style.display ="none";
			return;
		}
		//角度转为弧度
		if(!rot){
			rot = 0;	
		}
		var rotation = Math.PI * rot / 180;
		var c = Math.round(Math.cos(rotation) * 1000) / 1000;
		var s = Math.round(Math.sin(rotation) * 1000) / 1000;
		//旋转后canvas标签的大小
		canvas.height = Math.abs(c*h) + Math.abs(s*w);
		canvas.width = Math.abs(c*w) + Math.abs(s*h);
		//绘图开始
		var context = canvas.getContext("2d");
		context.save();
		//改变中心点
		if (rotation <= Math.PI/2) {
			context.translate(s*h,0);
		} else if (rotation <= Math.PI) {
			context.translate(canvas.width,-c*h);
		} else if (rotation <= 1.5*Math.PI) {
			context.translate(-c*w,canvas.height);
		} else {
			context.translate(0,-s*w);
		}
		//旋转90°
		context.rotate(rotation);
		//绘制
		context.drawImage(img, 0, 0, w, h);
		context.restore();
		img.style.display = "none";	
	}
  
/* window.onload = function(){

		var param = {
		right: document.getElementById("rotRight"),
		left: document.getElementById("rotLeft"),
		img: document.getElementById("rotImg"),
		cv: document.getElementById("canvas"),
		rot: 0
	};
	var fun = {
		right: function(){
			param.rot += 90;
			rotate(param.cv, param.img, param.rot);
			if(param.rot == 270){
				param.rot = -90;	
			}
			if(param.rot == 360||param.rot == -360){
				param.rot = 0;	
			}
			
		},
		left: function(){
			param.rot -= 90;
			if(param.rot == -90){
				param.rot = 270;	
			}
			if(param.rot == 360||param.rot == -360){
				param.rot = 0;	
			}
			rotate(param.cv, param.img, param.rot);			
		}
	};
	param.right.onclick = function(){
		fun.right();
		return false;
	};
	param.left.onclick = function(){
		fun.left();
		return false;
	};
	
	
	 
}; */
 
function showBig(){ 
 
	
//	return;
 
     $("#rotImg").attr("width",$("#rotImg").attr("width")*1.5);
	$("#rotImg").attr("height",$("#rotImg").attr("height")*1.5);
	
 	 cv.width=$("#rotImg").attr("width");
	 cv.height=$("#rotImg").attr("height");
	var context = cv.getContext("2d");
	// context.clearRect(0,0,cv.width,cv.height);
	
     context.clearRect(0,0,$("#rotImg").attr("width"),$("#rotImg").attr("height"));
      	var img= document.getElementById("rotImg");
 		var w = img.width;
		var h = img.height;
				context.drawImage(img, 0, 0, w, h);
			context.restore();
			if(rot == -90){
				rot = 270;	
			}
			rotate(cv, img, rot);
			if(rot == 270){
				rot = -90;	
			}
}

function showSmall(){ 
    $("#rotImg").attr("width",$("#rotImg").attr("width")/1.5);
	$("#rotImg").attr("height",$("#rotImg").attr("height")/1.5);
	 	 cv.width=$("#rotImg").attr("width");
	 cv.height=$("#rotImg").attr("height");
	var context = cv.getContext("2d");
        context.clearRect(0,0,cv.width,cv.height);
      	var img= document.getElementById("rotImg");
 		var w = img.width;
		var h = img.height;
		context.drawImage(img, 0, 0, w, h);
		context.restore();
		if(rot == -90){
			rot = 270;	
		}
		rotate(cv, img, rot);
		if(rot == 270){
		 	rot = -90;	
		}
} 
var img;
var cv;
var rot = 0;
function initImg(){
	img = document.getElementById("rotImg");
	cv = document.getElementById("canvas");
	rotate(cv, img,rot);
}; 
function showLeft(){
	rot -= 90;
	if(rot == -90){
		rot = 270;	
	}
	if(rot == 360||rot == -360){
		rot = 0;	
	}
	rotate(cv, img, rot);	
}
function showRight(){
	rot += 90;
	rotate(cv, img, rot);
	if(rot == 270){
		rot = -90;	
	}
	if(rot == 360||rot == -360){
		rot = 0;	
	}
}
</script>
</head>
<body onload="initImg();" >
<div style="position: absolute; top:0;bottom:5px;left:0;right:0;   overflow: auto; ">
    <div class="image_box">
        <canvas id="canvas"  ></canvas>
        <img id="rotImg" src="${param.path}" style="display:none;" onload="AutoResizeImage(970,0,this)"/>
    </div>
</div>
<div id="OpButton" style="z-index: 10;position: absolute;bottom: 30px; text-align: center; width: 100%;">
      <img src="${ctx}/img/big.png" title="放大"  width="34px" height="34px"  alt="" style="cursor: pointer;"  onclick="showBig()" /> &nbsp;&nbsp;
      <img src="${ctx}/img/small.png" title="缩小"  width="34px" height="34px"  alt="" style="cursor: pointer;" onclick="showSmall()" />&nbsp;&nbsp;
      <img id="rotLeft"  src="${ctx}/img/left.png" title="逆时针旋转"  width="34px" height="34px"  alt="" style="cursor: pointer;" onclick="showLeft()" />&nbsp;&nbsp;
      <img id="rotRight" src="${ctx}/img/right.png" title="顺时针旋转" width="34px" height="34px"  alt="" style="cursor: pointer;" onclick="showRight()" />&nbsp;&nbsp;
</div>
</body>
</html>
