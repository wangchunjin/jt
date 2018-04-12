<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
    
 <script type="text/javascript">
	     function UpdateRecord(){
	        var currFrameId = "${param.currFrameId}";
	        
	       
	        var res = $('#addForm').SubmitForm("${ctx}/swhz/tswhz/update.action");
	        
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('eidtWindow');
			}
 			else{
 				parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	       
	     }
	     
	     $(function(){
	    	 var status=${swhz[0].status};
	    	 if(status==0){
	    		 $("#aaa").show();
	    		 $("#bbb").hide();
	    	 }
	    	 if(status==1){
	    		 $("#aaa").hide();
	    		 $("#bbb").show();	    		 
	    	 }
	    	 
	    	 
	     })

 </script>
<style type="text/css">
#preview {
	width: 100px;
	height: 130px;
	overflow: hidden;
}

#imghead {
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
}
</style>
  <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
   <div style="height:300px;overflow: auto;" >
		<table style="margin-top: 10px;width: 84%;" >
		<tr>
			<td   width="" ><span style="color: red;"></span>合作详情</td>
			<td>
			
				<textarea disabled="disabled" rows="3" cols="">${swhz[0].content }</textarea>
			
			</td>
			
		</tr>
		<tr id="aaa">
			<td   width="" align="center" colspan="2"><span style="color: red;">该合作为未处理状态，是否确定合作已被处理？</span></td>			
			
		</tr>
		<tr id="bbb">
			<td   width="" align="center" colspan="2"><span style="color: red;">该合作已被处理。</span></td>			
			
		</tr>
		
<!-- 				<th   width="100%;" align="center"><span style="color: red;">*</span>是否确定合作已被处理？</th> -->
				
				<input  type="hidden" class="form_text"   name="swhz.id" id="id"  value="${swhz[0].id }" >
				<input  type="hidden" class="form_text"   name="swhz.status" id="status"  value="1" >
				
	
				
		
       </table>
       
       </div>
</form>

		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
