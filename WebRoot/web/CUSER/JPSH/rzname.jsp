<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
 <script type="text/javascript">
 function UpdateRecord(){
     var currFrameId = "${param.currFrameId}";
	
     
     
     var realName=$("#realName").val();
     if(realName=="" || realName==null){
    	 $.messager.alert("错误","真实姓名未填写不能认证！");
      	return;
     }
	
     var IdNo=$("#IdNo").val();
     if(IdNo=="" || IdNo==null){
    	 $.messager.alert("错误","身份证号未填写不能认证！");
      	return;
     }
     var NaAuthentified=$("#NaAuthentified").val();
     
     var rznameool=$("#rznameool").val();
     if(rznameool==0){
    	 
    	 if(NaAuthentified=="" || NaAuthentified==null){
        	 $.messager.alert("错误","真实姓名请认证！");
          	return;
         }
     }
     
     if(NaAuthentified==1){
    	 $("#rznameool").val(1);
     }
     
     var IdAuthentified=$("#IdAuthentified").val();
     var rzyhkhool=$("#rzyhkhool").val();
     if(rzyhkhool==0){
    	 if(IdAuthentified=="" || IdAuthentified==null){
        	 $.messager.alert("错误","身份证号请认证！");
          	return;
         }
     }
     
     if(IdAuthentified==1){
    	 $("#rzyhkhool").val(1);
     }
     
     
     
     
     
     
     
      

     var res = $('#addForm').SubmitForm("${ctx}/user/tuser/updateName.action");
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
			<input type="hidden" class="form_text"  name="" id="realName"  value="${user[0].realName }">
			<input type="hidden" class="form_text"  name="" id="IdNo"  value="${user[0].IdNo }">
			
			<input type="hidden" class="form_text"  name="user.NaAuthentified" id="rznameool"  value="${user[0].NaAuthentified }">
			<input type="hidden" class="form_text"  name="user.IdAuthentified" id="rzyhkhool"  value="${user[0].IdAuthentified }">
			
				<th width="20%"><span style="color: red;"></span>真实姓名：</th>
				<td width="20%">
				<c:if test="${user[0].realName==null}">未填写</c:if>
				<c:if test="${user[0].realName!=null}">${user[0].realName	 }</c:if>
				
				</td>
			</tr>
			
			<tr>
				<th width="20%"><span style="color: red;"></span>姓名认证：</th>
				<td width="20%">
				<!-- 首先判断用户是否填写姓名 -->
				<c:if test="${user[0].realName==null}">不能认证</c:if>
				<c:if test="${user[0].realName!=null}">
					<!-- 判断用户是姓名是否通过认证 -->
					<c:if test="${user[0].NaAuthentified==0 }">
					<select  id="NaAuthentified" class="form_text">
			       		<option value="" >--请认证--</option>
			       		<option value="1" <c:if test="${user[0].NaAuthentified==1 }">selected="selected"</c:if>>认证成功</option>
			       		<option value="0" >认证失败</option>       		
			       	</select>
			       	</c:if>
						<c:if test="${user[0].NaAuthentified==1 }">已认证</c:if>
					
				
				</c:if>
				</td>
			</tr>	
			
			<tr>
				<th width="20%"><span style="color: red;"></span>身份证号：</th>
				<td width="20%">
				<c:if test="${user[0].IdNo==null	 }">未填写</c:if>
				<c:if test="${user[0].IdNo!=null	 }">${user[0].IdNo	 }</c:if>
				
				</td>
			</tr>
			
			<tr>
				<th width="20%"><span style="color: red;"></span>身份证认证：</th>
				<td width="20%">
				<!-- 首先判断用户身份证号是否填写 -->
				<c:if test="${user[0].IdNo==null	 }">不能认证</c:if>
				<c:if test="${user[0].IdNo!=null	 }">
					<!-- 判断用户身份证号是否通过认证 -->
					<c:if test="${user[0].IdAuthentified==0 }">
					<select  id="IdAuthentified" class="form_text" >
			       		<option value="" >--请认证--</option>
			       		<option value="1" <c:if test="${user[0].IdAuthentified==1 }">selected="selected"</c:if>>认证成功</option>
			       		<option value="0" >认证失败</option>   		
			       	</select>
			       	</c:if>					
					<c:if test="${user[0].IdAuthentified==1 }">已认证</c:if>
					
					
				</c:if>
				</td>
			</tr>
			
			
			
			<tr>
				<th width="20%"><span style="color: red;"></span>身份证  正面照：</th>
				<td width="30%">				
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="http://www.1mche.com/qiche/${user[0].IdPiczheng }" width="280" height="200" /></a></div>
				</td>
				
			</tr> 
			<tr>
				<th width="20%"><span style="color: red;"></span>身份证  反面照：</th>
				<td width="30%">				
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="http://www.1mche.com/qiche/${user[0].IdPicfan }" width="280" height="200" /></a></div>
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
