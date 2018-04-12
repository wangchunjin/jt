<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=910kh9jL9SYh93KxwhmyXqIV"></script>
<script type="text/javascript">
 var marker;
   function  LoadMap(){
	   var CurrCity = "${param.city}";
	   if(CurrCity=="--请选择--"){
		   CurrCity="南京";
	   } 
	   var mark = "${param.mark}"; 
	   	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	if(isStringNull(mark)){
	     map.centerAndZoom(new BMap.Point(mark.split(",")[0],mark.split(",")[1]), 13);  // 初始化地图,设置中心点坐标和地图级别
	        var point = new BMap.Point(mark.split(",")[0],mark.split(",")[1]);//默认  
             // 创建标注对象并添加到地图    
             marker = new BMap.Marker(point); 
             map.addOverlay(marker);     
	}else{
		map.centerAndZoom(CurrCity,13);    
	}	 
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	
	var size = new BMap.Size(10, 10);
	map.addControl(new BMap.CityListControl({
	    anchor: BMAP_ANCHOR_TOP_LEFT,
	    offset: size,
	    // 切换城市之间事件
	    // onChangeBefore: function(){
	    //    alert('before');
	    // },
	    // 切换城市之后事件
	    // onChangeAfter:function(){
	    //   alert('after');
	    // }
	}));
	
	  var geolocationControl = new BMap.GeolocationControl();
	  geolocationControl.addEventListener("locationSuccess", function(e){
		  if(isNull(marker)){
	          map.removeOverlay(marker);
	         }            
             var point = new BMap.Point(e.point.lng ,e.point.lat);//默认  
             // 创建标注对象并添加到地图    
             marker = new BMap.Marker(point);      
            // marker.setLabel(label)  
             map.addOverlay(marker);    
            $("#lat").html(e.point.lat);
            $("#lng").html(e.point.lng);
	  });
	  geolocationControl.addEventListener("locationError",function(e){
	    // 定位失败事件
	    alert(e.message);
	  });
	  map.addControl(geolocationControl);
	  
	 map.addEventListener("click", function(e){  
           
            //---------------------------------------------遮盖物---------------------------------------------  
	         if(isNull(marker)){
	          map.removeOverlay(marker);
	         }            
             var point = new BMap.Point(e.point.lng ,e.point.lat);//默认  
             // 创建标注对象并添加到地图    
             marker = new BMap.Marker(point);      
            // marker.setLabel(label)  
             map.addOverlay(marker);    
            $("#lat").html(e.point.lat);
            $("#lng").html(e.point.lng);
         });  
   }
   
   function AddRecord(){
	   var lng = $("#lng").html();
	   if(!isStringNull(lng)){
		    parent.close_win('addWindow');
		    return;
	   }
	   	var currFrameId = "${param.currFrameId}";
	     var json = IntoActionByUrl("${ctx}/sys/proj/GetLocationByMark.action?location="+ $("#lat").html()+","+$("#lng").html()+"&output=json&key=910kh9jL9SYh93KxwhmyXqIV")
	       var obj = eval(json);
	       var location = obj.result.formatted_address;
	       var city = obj.result.addressComponent.city;
	       var province = obj.result.addressComponent.province;
	       var district = obj.result.addressComponent.district;
	       GetIndexFrame(currFrameId).FourFrame.SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='CITY'  ORDER BY SEQ_NUM ASC,CODE_NAME ASC","city","",new Array("=--请选择--"))
           GetIndexFrame(currFrameId).FourFrame.SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='AREA'  ORDER BY SEQ_NUM ASC,CODE_NAME ASC","area","",new Array("=--请选择--"))

        var provinceObj = GetXmlBySql("SELECT MAX(ID) ID FROM CM_CODE WHERE CODE_TYPE ='STATE' AND  LOCATE(CODE_NAME,'"+encodeURI(province)+"')>0 ");
	    GetIndexFrame(currFrameId).FourFrame.$("#stage").val(provinceObj[0].ID);
	    
	    var cityObj = GetXmlBySql("SELECT MAX(ID) ID FROM CM_CODE WHERE CODE_TYPE ='CITY' AND CODE_BELONGTO = '"+provinceObj[0].ID+"' AND  LOCATE(CODE_NAME ,'"+encodeURI(city)+"')>0 ");
	    
	    GetIndexFrame(currFrameId).FourFrame.$("#city").val(cityObj[0].ID);
	    var districtObj = GetXmlBySql("SELECT MAX(ID) ID FROM CM_CODE WHERE CODE_TYPE ='AREA' AND CODE_BELONGTO = '"+cityObj[0].ID+"' AND  LOCATE(CODE_NAME,'"+encodeURI(district)+"' )>0 ");
	    GetIndexFrame(currFrameId).FourFrame.$("#area").val(districtObj[0].ID);
        GetIndexFrame(currFrameId).FourFrame.$("#cmProj\\.mark").val( $("#lng").html()+","+$("#lat").html());
        GetIndexFrame(currFrameId).FourFrame.$("#cmProj\\.siteLocation").val( location);      
        GetIndexFrame(currFrameId).FourFrame.SaveRecord();
	    parent.close_win('addWindow');
   }
</script>
<style type="text/css">
	body, html,#allmap {width: 100%;height: 97%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
  </head>
  
  <body class="NewWinClass" onload="LoadMap()">
        <div id="allmap"></div>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a> 			
			</div>
			<div id="lng" style="float:left;width:200px;display: none;"></div>  
			<div id="lat" style="float:left;width:200px;display: none;"></div>  
  </body>
</html>

