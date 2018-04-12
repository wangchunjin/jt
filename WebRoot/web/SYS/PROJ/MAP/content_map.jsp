<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=910kh9jL9SYh93KxwhmyXqIV"></script>
<script type="text/javascript">

	var map ;    // 创建Map实例
 
	var OpenMark; 
	function myFun(result){
		var cityName = result.name;
	 	map.centerAndZoom(cityName,11);
	}

   function  LoadMap(){ 
    map = new BMap.Map("allmap"); 
	 var myCity = new BMap.LocalCity();
     myCity.get(myFun);
 
	   	// 百度地图API功能
  	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));
	map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}));
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	
	
	var res = IntoActionByUrl("${ctx}/sys/proj/ShowProjByMap.action");
	for(var i=0;i<res.length;i++){ 
		var markCode = res[i].MARK;
		 var point = new BMap.Point(markCode.split(",")[0],markCode.split(",")[1]);
		  var myIcon ;
		   var fontColor=""; 
		 		var marker;
		 if("${sessionScope.UserInfo.getProjId()}" == res[i].PROJ_ID){
			fontColor = "color:red;";
		    myIcon = new BMap.Icon("${ctx}/img/map_mark.png", new BMap.Size(22, 25), {  
                        offset: new BMap.Size(10, 25), // 指定定位位置  
                        imageOffset: new BMap.Size(-42,-21) // 设置图片偏移  
              });  
		    marker=new BMap.Marker(point,{icon:myIcon}); 
		    OpenMark = marker;
		  }else{
			  myIcon = new BMap.Icon("${ctx}/img/map_mark.png", new BMap.Size(22, 25), {  
                        offset: new BMap.Size(10, 25), // 指定定位位置  
                        imageOffset: new BMap.Size(-20,-21) // 设置图片偏移  
              }); 
			   marker=new BMap.Marker(point,{icon:myIcon}); 
		  } 
		// var marker = new BMap.Marker(point);
		 map.addOverlay(marker); 

          var  ss ="<div name='projName' lng='"+markCode.split(",")[0]+"' lat='"+markCode.split(",")[1]+"' ondblclick=OpenProj('"+res[i].PROJ_ID+"','"+res[i].PROJ_NODE_FLAG+"','"+(res[i].PROJ_SHORT_NAME).replace(/(^\s*)|(\s*$)/g, "")+"','"+(res[i].PROJ_NAME).replace(/(^\s*)|(\s*$)/g, "")+"',this) style='color:blue;font-weight: bold;font-size: 16px;cursor: pointer;"+fontColor+"' onclick=OnclickRow('"+res[i].PROJ_ID+"','"+res[i].PROJ_NODE_FLAG+"')  > "+res[i].PROJ_NAME+"</div>";
          var label = new BMap.Label(ss,{offset:new BMap.Size(20,-10)});
	     marker.setLabel(label); 
		 label.setStyle({                                   //给label设置样式，任意的CSS都是可以的
		  //  color:"red",                   //颜色
		  //  fontSize:"14px",               //字号
		    border:"1px solid #aeaeae",                    //边
		  //  height:"120px",                //高度
		  //  width:"125px",                 //宽
		 //   textAlign:"center",            //文字水平居中显示
		//    lineHeight:"120px",            //行高，文字垂直居中显示
		 //   background:"url(http://cdn1.iconfinder.com/data/icons/CrystalClear/128x128/actions/gohome.png)",    //背景图片，这是房产标注的关键！
		 //  cursor:"pointer"
		});
 
	} 
   }
    
   function OnclickRow(proj_id,node_type){
	    
				 var where = proj_id+";false";
				 if(node_type=='N')
				 {
                       parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=SYS_EPS&Where="+where;		
				 }else
				 {
					   parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=SYS_PROJ&Where="+where;		
				 }	
			}
   
   function OpenProj(projId,node_type,projshortName,projName,obj){ 
	             
	   	   		   
				 if(node_type=='Y')
				 {
                    var openProj = parent.OneFrame.OpenProjId;         
                    if(projId != openProj){						 
					 IntoActionByUrl("${ctx}/sys/proj/ChangeProj.action?projId="+projId);
                     $("div[name='projName']").css("color","blue");
                     $(obj).css("color","red");                             
                     var myIcon = new BMap.Icon("${ctx}/img/map_mark.png", new BMap.Size(22, 25), {  
                        offset: new BMap.Size(10, 25), // 指定定位位置  
                        imageOffset: new BMap.Size(-20,-21) // 设置图片偏移  
                     });  
                     OpenMark.setIcon(myIcon)
                    var allOverlay = map.getOverlays();
					for (var i = 0; i < allOverlay.length-1; i++){ 
						if(allOverlay[i].getLabel().content.indexOf(projId)>0){
						      myIcon = new BMap.Icon("${ctx}/img/map_mark.png", new BMap.Size(22, 25), {  
		                        offset: new BMap.Size(10, 25), // 指定定位位置  
		                        imageOffset: new BMap.Size(-42,-21) // 设置图片偏移  
		                     }); 
						      allOverlay[i].setIcon(myIcon)
						      OpenMark= allOverlay[i];
						}
					}
						 
				 	 parent.parent.$("#CurrProj").html(projshortName+"--"+projName) 
				 	 parent.parent.$("#CurrProjId").val(projId) ;
				 	 parent.OneFrame.OpenProjId =  projId; 
						    
					} 
				 }
		    }
</script>
<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
  </head>
  
  <body class="NewWinClass" onload="LoadMap()">
        <div id="allmap" ></div>
  </body>
</html>

