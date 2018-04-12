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
	        
	        
	        var overdue_payment_rel=$("#overdue_payment_rel").val();
	       
	        
	        if(overdue_payment_rel==null|| trim(overdue_payment_rel)==""){
	        	$.messager.alert("错误","实际滞纳金自己不能为空！");
	        	return;
	        }
	        var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
	        if(r1.test(overdue_payment_rel)==false){
	        	$.messager.alert("错误","滞纳金只能为整数或小数！");
	        	return;
	        }
	        var cuid=$("#cuid").val();
	        var res = $('#addForm').SubmitForm("${ctx}/orderinfo/torderinfo/late.action?late_fee="+${param.late }+"&cuid="+cuid);
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
				<th width="20%;" align="right"><span style="color: red;">*</span>实际滞纳金：</th>
				<td colspan="3" width="30%">
				 <input type="hidden" id="cuid" value="${sessionScope.UserInfo.userId}">
				<input type="hidden" name="orderinfo.order_id" value="${param.order_id }" />
				<input type="hidden" name="orderinfo.interest" value="${param.interest }" />
				<input type="hidden" name="orderinfo.amount" value="${param.amount }" />
					<input class="form_text" name="orderinfo.overdue_payment_rel" id="overdue_payment_rel" value="${param.late }" >				
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
