<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>

 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
 <script type="text/javascript">
	     function AddRecord(){	       
	        var currFrameId = "${param.currFrameId}";			

	        var res = $('#addForm').SubmitForm("${ctx}/car/tcar/upxl.action");
	 	  	if(res[0].result=="success"){
	 	  		setTimeout("javascript:location.href='${ctx}/web/CAR/CARR/FS.jsp'", 100); 
// 	 	  		window.location.href='${ctx}/web/CAR/CARR/FS.jsp';
// 	 	  		$('#addForm').SubmitForm("${ctx}/web/CAR/CARR/FS.jsp");
 	 	  		parent.GetIndexFrame("iframe_CARRR").location.reload();
	 	  		parent.close_win('XiaoLiang');
	 		GetIndexFrame(currFrameId ).LeftFrame.TwoFrame.location.reload();
	 		
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
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
		
			<tr align="left">
				<th width="5%"><span style="color: red;">*</span>车型名称：</th>
				<td width="50%">${xl[0].title }</td>
				<input type="hidden" value="${xl[0].id }" name="car.id" id="id" />
				
			</tr>
			<tr align="left">
				
				<th width="10%"><span style="color: red;">*</span>销量(辆)：</th>
				<td width="50%"><input attr="style='width: 100%;'" class="form_text"   name="car.sales_volume" id="sales_volume"  value="${xl[0].sales_volume }"></td>
			</tr>
			
			
			
			
			
			
			
			
			
       </table>
</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('XiaoLiang')">关闭<b></b></a>				
		</div>
  </body>
</html>
