<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
 <script type="text/javascript">
 function UpdateRecord(){
     var currFrameId = "${param.currFrameId}";
	
     
     
     var bankcardNo=$("#bankcardNo").val();
     if(bankcardNo=="" || bankcardNo==null){
    	 $.messager.alert("错误","银行卡号未填写不能认证！");
      	return;
     }
	
     
     var BankAuthentifiedool=$("#BankAuthentifiedool").val();
     
     var BankAuthentified=$("#BankAuthentified").val();
     if(BankAuthentifiedool==0){
	     if(BankAuthentified=="" || BankAuthentified==null){
	    	 $.messager.alert("错误","真实姓名请认证！");
	      	return;
	     }
     }
     if(BankAuthentified==1){
    	 $("#BankAuthentifiedool").val(1);
     }
     
  
     
     
     
      

     var res = $('#addForm').SubmitForm("${ctx}/user/tuser/updateYhkh.action");
	  	if(res[0].result=="success"){
		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
		parent.close_win('eidtWindow');
		}
		else{
			$.messager.alert("输入符号已存在!",res[0].result);
	 	}

  }
 </script>
 
 </head>
  <body class="NewWinClass">
  <div style="height:500px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >		
			<tr>
			<input type="hidden" class="form_text"  name="user.id" id="id"  value="${user[0].id }">
			<input type="hidden" class="form_text"  name="" id="bankcardNo"  value="${user[0].bankcardNo }">
			<input type="hidden" class="form_text"  name="user.BankAuthentified" id="BankAuthentifiedool"  value="${user[0].BankAuthentified }">
			
			
				<th width="20%"><span style="color: red;"></span>银行卡号：</th>
				<td width="20%">
				<c:if test="${user[0].bankcardNo==null	 }">未填写</c:if>
				<c:if test="${user[0].bankcardNo!=null	 }">${user[0].bankcardNo	 }</c:if>
				
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;"></span>银行卡号认证：</th>
				<td width="20%">
				<!-- 首先判断用户是否填写姓名 -->
				<c:if test="${user[0].bankcardNo==null	 }">不能认证</c:if>
				<c:if test="${user[0].bankcardNo!=null	 }">
					<!-- 判断用户是姓名是否通过认证 -->
					<c:if test="${user[0].BankAuthentified==0 }">
					<select  id="BankAuthentified" class="form_text">
			       		<option value="" >--请认证--</option>
			       		<option value="1" >认证成功</option>
			       		<option value="0" >认证失败</option>       		
			       	</select>
			       	</c:if>
						<c:if test="${user[0].BankAuthentified==1 }">已认证</c:if>
					
				
				</c:if>
				</td>
			</tr>	
			
			
			
			
			
			
			
			
			
       </table>
       
</form>
</div>
		<div class="BottomDiv" style="height: 10%;">
			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
