<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=z47a5PpxBagrTYGdUsYqpHQS"></script>
 <script type="text/javascript">


 function AddRecord(){
	        var currFrameId = "${param.currFrameId}";
	        var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数


	        
	        
	        
	        var title=$("#title").val();
	        if(title==null || trim(title)==""){
	        	$.messager.alert("错误","楼盘标题不能为空！");
	        	return;
	        }   
	       
	        var address=$("#address").val();
	        if(address==null || trim(address)==""){
	        	$.messager.alert("错误","地址不能为空！");
	        	return;
	        }
	        
	        var Lng=$("#Lng").val();
	        if(Lng==null || trim(Lng)==""){
	        	$.messager.alert("错误","经度不能为空！");
	        	return;
	        }
	       
	        var Lat=$("#Lat").val();
	        if(Lat==null || trim(Lat)==""){
	        	$.messager.alert("错误","纬度不能为空！");
	        	return;
	        }
	       
	       
	        
	        
	        
	        
	        
	        
	        
	        
	        
			
	        var res = $('#addForm').SubmitForm("${ctx}/proaround/tproaround/save.action");
	       
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	       
	     };
	    
// 	     function check(){
// 	    	 var status=$("#type").val();
	    	 
// 	    	 if(status==2){	    		
	    		 
// 	    		 $("#hhh").hide();
// 	    		 $("#ttt").show();
// 	    	 }
// 	    		  if(status==1){
	    			  
// 	 	    		 $("#ttt").hide();
// 		    		 $("#hhh").show();
// 	    		  }  
	    	 
	    	 
// 	     }
// 	     function getUrl(node,imgId){   
// 			    var file = null;  
// 			    if(node.files && node.files[0] ){  
// 			        file = node.files[0]; 
// 			    }else if(node.files && node.files.item(0)) {                                  
// 			        file = node.files.item(0);     
// 			    }
// 			    alert(getObjectURL(file));
// 			    $("#"+imgId).attr("src",getObjectURL(file));  
			    
// 			      $(node).parent().find('img')[0].attr("src",getObjectURL(file));  
// 			}
// 			function getObjectURL(file) {
// 			    var url = null;
// 			    if (window.createObjectURL != undefined) { // basic
// 			        url = window.createObjectURL(file);
// 			    } else if (window.URL != undefined) { // mozilla(firefox)
// 			        url = window.URL.createObjectURL(file);//实现本地浏览
// 			    } else if (window.webkitURL != undefined) { // webkit or chrome
// 			        url = window.webkitURL.createObjectURL(file);
// 			    }
// 			    return url;
// 			}
			
			
			
			$(function(){
				
				setMap("南京市","星火E方");		
				
				
			});
			


			function setMap(cityName,currentAddr){
			        var map = new BMap.Map("container");
			        map.centerAndZoom(cityName,12);                   // 初始化地图,设置城市和地图级别。
			        map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
			        map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}));  //右上角，仅包含平移和缩放按钮
			        map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT, type: BMAP_NAVIGATION_CONTROL_PAN}));  //左下角，仅包含平移按钮
			        map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, type: BMAP_NAVIGATION_CONTROL_ZOOM}));  //右下角，仅包含缩放按钮
			        
			        //根据输入的地址定位
			        if(currentAddr==""||currentAddr==null){
			                currentAddr="南京市";
			        }
			        var local = new BMap.LocalSearch(map, {
			                renderOptions:{map: map}
			        });
			        local.search(currentAddr);
			        
			        //鼠标点击获取坐标
			        map.enableScrollWheelZoom();                            //启用滚轮放大缩小
			        map.addEventListener("click", function(e){
			          //document.getElementById("r-result1").innerHTML = e.point.lng + ", " + e.point.lat;
			                $("#Lng").val(e.point.lng);
			                $("#Lat").val(e.point.lat);
							$("#cityname").val(e.target.$g);
			        });        
			        
			        
			}
			
			
			
			
			
 </script>
<style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
 
    <div style="height:500px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >			
			<tr align="right">	
			<input class="form_text" type="hidden"  name="proaround.l_id" id="lid"  value="${param.lid }" >		
				<th width="10%"><span style="color: red;">*</span>标题：</th>
				<td width="20%">				
				<input class="form_text"  name="proaround.title" id="title"  value="" ></td>
				<th width="10%"><span style="color: red;">*</span>名称：</th>
				<td width="20%"><input class="form_text"  name="proaround.name" id="name"  value=""></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;">*</span>设施类型：</th>
				<td width="20%">				
					<select class="form_text" name="proaround.type" id="type">
						<option value="1">交通</option>
						<option value="2">学校</option>
						<option value="3">医疗</option>
						<option value="4">购物</option>
						
					</select>
				
				</td>
										
				
			</tr>
			
			
			
			<tr align="right">
				<th ><span style="color: red;">*</span>地址：</th>
				<td colspan="3">			
				<div class="formControls col-xs-8 col-sm-9">
				<input type="text" style="width: 100%;" class="form_text" value="" placeholder="请输入楼盘地址" name="proaround.address" id="address" onkeyup="setMap('南京市',value)">
				</td>
			</tr>
			
			
			<tr align="right">
				<th><span style="color: red;">*</span>经度：</th>
				<td>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" style="width: 100%;" readonly="readonly" class="form_text" name="proaround.longitude" id="Lng"  onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
					</div>
					
				</td>
				<th><span style="color: red;">*</span>纬度：</th>
				<td>
					<div class="formControls col-xs-8 col-sm-9">
				<input type="text" style="width: 100%;" readonly="readonly" class="form_text" name="proaround.latitude" id="Lat" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
			</div>
				</td>
				
			</tr>
       </table>      
       
       
		<div class="row cl" >
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2" id="container" style="height:360px;">
				
			</div>
		</div>
		
<!--        <input class="form_text" type="hidden"  name="tservice.content" id="content"  value="">	 -->
</form>

</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>