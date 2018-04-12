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
	        var r5=/^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$/;  //判断颜色色值
	        
	        var name=$("#name").val();
	        if(name==null || trim(name)==""){
	        	$.messager.alert("错误","楼盘户型标签名称不能为空！");
	        	return;
	        }
	        var cname=$("#cname").val();
	        if(cname==null || trim(cname)==""){
	        	$.messager.alert("错误","楼盘户型标签颜色不能为空！");
	        	return;
	        }
	        var color=$("#color").val();
	        if(color==null || trim(color)==""){
	        	$.messager.alert("错误","楼盘户型标签色值不能为空！");
	        	return;
	        }
	        if(r5.test(color)==false){
	        	$.messager.alert("错误","色值格式不正确，#后为3位或6位值！");
	        	return;
	        }
	      
	        var res = $('#addForm').SubmitForm("${ctx}/houselabel/thouselabel/save.action");
	 	  	if(res[0].result=="success"){
	 	  		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 	  		parent.close_win('addWindow');
	 		
			}
 			else{
 				
 				parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	       
	     }
	     
	    
	
		
	  	
      
	
	    
 </script>
<style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
   <div style="height:300px;overflow: auto;" >
   	<table style="margin-top: 50px;width: 84%;" >
			
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>楼盘户型标签名称：</th>
				<td colspan="3" width="30%">
				<input  type="" class="form_text"  name="houselabel.name" id="name"   >
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>标签颜色名称：</th>
				<td colspan="3" width="30%">
				<input  type="" class="form_text"  name="houselabel.cname" id="cname"   >
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>标签色值：</th>
				<td colspan="3" width="30%">
				<input  type="" class="form_text"  name="houselabel.color" id="color" placeholder="色值如：#FF0000"  >
				</td>
			</tr>
			
		
		
       </table>	
       </div>
		
		
		
</form>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
