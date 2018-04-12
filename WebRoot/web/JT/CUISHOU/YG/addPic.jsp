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
	        //选中分配逾期订单的id
	        var ids="${param.ids}";
	        
	        var jg_name=$("#order\\.jg_name").val();
	       
	        
	        if(jg_name==null|| jg_name==""){
	        	$.messager.alert("错误","请选择催收员！");
	        	return;
	        }
	        
	        
	       
	        
	        var res = $('#addForm').SubmitForm("${ctx}/orderinfo/torderinfo/fp_jg.action?ids="+ids+"&jg_name="+jg_name);
	 	  	if(res[0].result=="success"){
	 	  		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 	  		parent.close_win('addWindow');
	 		
			}
 			else{
 				
 				parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	       
	     }
	     
	   
	     $(function(){		
	    	 var users_id='${sessionScope.UserInfo.userId}';

			    SetSelect("SELECT USER_ID,ACTUAL_NAME FROM cm_users where subsidiary='"+users_id+"'","order\\.jg_name",value="",new Array("=--请选择--")); 

			    
			 
		 });
	
		
	  	
      
	
	    
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
				<th width="20%;" align="right"><span style="color: red;">*</span>催收员：</th>
				<td colspan="3" width="30%">
					<epmis:select id="order.jg_name" define=""  attr="style='width: 100%;'" ></epmis:select>
				
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
