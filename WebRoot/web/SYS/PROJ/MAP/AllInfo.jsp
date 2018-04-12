<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=910kh9jL9SYh93KxwhmyXqIV"></script>
<script type="text/javascript">
 var map ;    // 创建Map实例
	function myFun(result){
		var cityName = result.name;
	 	map.centerAndZoom(cityName,7);
	}
   function  LoadMap(){ 
    map = new BMap.Map("allmap"); 
	 var myCity = new BMap.LocalCity();
     myCity.get(myFun);
 
	   	// 百度地图API功能
  // map.centerAndZoom(new BMap.Point(r.point.lng, r.point.lat), 7);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));
	map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}));
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	var res = IntoActionByUrl("${ctx}/sys/proj/ShowProjEntByMap.action");
	for(var i=0;i<res.length;i++){ 
		var sContent =
	"<div><h4 style='margin:0 0 5px 0;padding:0.2em 0'>"+res[i].PROJ_NAME+"</h4>" + 
	//"<img style='float:right;margin:4px' id='imgDemo' src='http://app.baidu.com/map/images/tiananmen.jpg' width='139' height='104' title='"+res[i].PROJ_NAME+"'/>" + 
	//"<p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>天安门坐落在中国北京市中心,故宫的南侧,与天安门广场隔长安街相望,是清朝皇城的大门...</p>" + 
	"</div>"; 
	 
		var markCode = res[i].MARK;
		 var point = new BMap.Point(markCode.split(",")[0],markCode.split(",")[1]);
		 var marker = new BMap.Marker(point);
		 map.addOverlay(marker);  
		 addClickHandler(sContent,marker);
 
	}
	function addClickHandler(content,marker){
		marker.addEventListener("click",function(e){
			openInfo(content,e)}
		);
	}
	function openInfo(content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
  	var opts = {
				width : 250,     // 信息窗口宽度
				height: 80,     // 信息窗口高度
				title : "" , // 信息窗口标题
				enableMessage:true//设置允许信息窗发送短息
			   };
   }
    
</script>
<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
  </head>
  
  <body class="NewWinClass" onload="LoadMap()">
        <div id="allmap"></div>
  </body>
</html>

