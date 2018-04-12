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
// 	        var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数


	        
	        
	        
	        var name=$("#name").val();
	        if(name==null || trim(name)==""){
	        	$.messager.alert("错误","楼栋名称不能为空！");
	        	return;
	        }   
	        var unit_number=$("#unit_number").val();
	        if(unit_number==null || trim(unit_number)==""){
	        	$.messager.alert("错误","单元数不能为空！");
	        	return;
	        }  
	        var ladder=$("#ladder").val();
	        if(ladder==null || trim(ladder)==""){
	        	$.messager.alert("错误","梯户比不能为空！");
	        	return;
	        }
	        var households=$("#households").val();
	        if(households==null || trim(households)==""){
	        	$.messager.alert("错误","总户数不能为空！");
	        	return;
	        }
	        
	        var floor=$("#floor").val();
	        if(floor==null || trim(floor)==""){
	        	$.messager.alert("错误","楼层不能为空！");
	        	return;
	        }
	        var opentime=$("#opentime").val();
	        if(opentime==null || trim(opentime)==""){
	        	$.messager.alert("错误","开盘时间不能为空！");
	        	return;
	        }
	        var otherhousetime=$("#otherhousetime").val();
	        if(otherhousetime==null || trim(otherhousetime)==""){
	        	$.messager.alert("错误","交房时间不能为空！");
	        	return;
	        }
	        var x=$("#x").val();
	        if(x==null || trim(x)==""){
	        	$.messager.alert("错误","请选中图片相关区域！");
	        	return;
	        }
	        var y=$("#y").val();
	        if(y==null || trim(y)==""){
	        	$.messager.alert("错误","请选中图片相关区域！");
	        	return;
	        }
	        
	        
			
	        var res = $('#addForm').SubmitForm("${ctx}/ban/tban/save.action");
	       
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	       
	     };
	    

			$(function(){
				var result = GetXmlBySql("select ban_pic from t_prosale where id='"+${param.lid }+"'");	
// 				alert(result[0].ban_pic);
				document.getElementById('monitor_map').src = "${ctx}"+result[0].ban_pic;
// 				$("#monitor_map").src="${ctx}"+result[0].ban_pic;
				
			});
			
			
			function mapOnClick(e){
		        e = e || window.event;
		        var imgId ='#'+ $(e.target).attr('id');
		        var currentWidth = $(imgId).width();
		        var currentHeight = $(imgId).height();
		        var offsetX = e.pageX - $(imgId).offset().left;
		        var offsetY = e.pageY - $(imgId).offset().top;
		        var x = offsetX / currentWidth;
		        var y = offsetY / currentHeight;
		        $("#x").val(offsetX);
		        $("#y").val(offsetY);
// 		        alert(x + ':' + y);
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
		
		
		<img id="monitor_map" src="" class="col-md-12" style="margin:0;padding: 0"  onclick="mapOnClick(event)"/>		
			<tr align="right">	
			<input class="form_text" type="hidden"  name="ban.l_id" id="lid"  value="${param.lid }" >		
				<th width="10%"><span style="color: red;">*</span>名称：</th>
				<td width="20%">				
				<input class="form_text"  name="ban.name" id="name"  value="" ></td>
				<th width="10%"><span style="color: red;">*</span>单元数：</th>
				<td width="20%"><input class="form_text"  name="ban.unit_number" id="unit_number"  value=""></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;">*</span>楼栋状态：</th>
				<td width="20%">				
					<select class="form_text" name="ban.status" id="status">
						<option value="1">待售</option>
						<option value="2">在售</option>
						<option value="3">售罄</option>						
					</select>
				
				</td>
				<th width="10%"><span style="color: red;">*</span>梯户比：</th>
				<td width="20%"><input class="form_text"  name="ban.ladder" id="ladder"  value=""></td>						
				
										
				
			</tr>
			
			<tr align="right">
				<th><span style="color: red;">*</span>总户数：</th>
				<td>
					
						<input type="text" style="width: 100%;"  class="form_text" name="ban.households" id="households"  >
					
					
				</td>
				<th><span style="color: red;">*</span>楼层：</th>
				<td>
					
				<input type="text" style="width: 100%;"  class="form_text" name="ban.floor" id="floor" >
			
				</td>
				
			</tr>
			<tr align="right">
				<th><span style="color: red;">*</span>开盘时间：</th>
				<td>
					
						<input type="text" style="width: 100%;" onchange="findbycoi()" readonly="readonly" class="form_text" name="ban.opentime" id="opentime" onclick="WdatePicker({el:'opentime'})" >					
					
				</td>
				<th><span style="color: red;">*</span>交房时间：</th>
				<td>
					
				<input type="text" style="width: 100%;" readonly="readonly" onchange="findbycoi()" class="form_text" name="ban.otherhousetime" id="otherhousetime" onclick="WdatePicker({el:'otherhousetime'})">
			
				</td>
				
			</tr>
			<tr align="right">
				<th><span style="color: red;">*</span>x：</th>
				<td>
					
						<input type="text" style="width: 100%;" readonly="readonly" class="form_text" name="ban.x" id="x"  >
					
					
				</td>
				<th><span style="color: red;">*</span>y：</th>
				<td>
					
				<input type="text" style="width: 100%;" readonly="readonly" class="form_text" name="ban.y" id="y" >
			
				</td>
				
			</tr>
       </table>      
       
       
		
		

</form>

</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
