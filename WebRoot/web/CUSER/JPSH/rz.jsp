<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
 <script type="text/javascript">
 function UpdateRecord(){
     var currFrameId = "${param.currFrameId}";
    
     
     var res = $('#addForm').SubmitForm("${ctx}/user/tuser/updaterz.action");
     
	  	if(res[0].result=="success"){
		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
		parent.close_win('eidtWindow');
		}
		else{ 				
			parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
	 	}

  }
 </script>
 
 </head>
  <body class="NewWinClass">
  <div style="height:400px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >		
			
			
		<tr>
				<th width="20%"><span style="color: red;"></span>身份证  正面照：</th>
				<td width="30%">				
				<div class="lf salebd"><a href="#"  id="preview">
				<c:choose>
				   <c:when test="${user[0].IdPiczheng=='' }">  
				      无 
				   </c:when>
				   <c:otherwise> 
				    <c:if test="${user[0].IdPiczheng.substring(0,7)=='/upload' }">
						<img id="imghead"  src="${ctx }/${user[0].IdPiczheng }" width="NAN" height="200" />		
					</c:if>
					<c:if test="${user[0].IdPiczheng.substring(0,5)=='/file' }">
						<img id="imghead"  src="http://120.27.215.48:8082/duifang/${user[0].IdPiczheng }" width="NAN" height="200" />	
					</c:if>
					<c:if test="${user[0].IdPiczheng.substring(0,4)=='http' }">
						<img id="imghead"  src="${user[0].IdPiczheng }" width="NAN" height="200" />	
					</c:if>
				   </c:otherwise>
				</c:choose>
				
				
				
					
				
				</a></div>
				</td>
				
			</tr> 
			<tr>
				<th width="20%"><span style="color: red;"></span>身份证  反面照：</th>
				<td width="30%">				
				<div class="lf salebd"><a href="#"  id="preview">
				<c:choose>
				   <c:when test="${user[0].IdPicfan=='' }">  
				      无 
				   </c:when>
				   <c:otherwise> 
				    <c:if test="${user[0].IdPicfan.substring(0,7)=='/upload' }">
				<img id="imghead"  src="${ctx }/${user[0].IdPicfan }" width="NAN" height="200" />
				</c:if>
					<c:if test="${user[0].IdPicfan.substring(0,5)=='/file' }">
				<img id="imghead"  src="http://120.27.215.48:8082/duifang/${user[0].IdPicfan }" width="NAN" height="200" />
				</c:if>
				<c:if test="${user[0].IdPicfan.substring(0,4)=='http' }">
				<img id="imghead"  src="${user[0].IdPicfan }" width="NAN" height="200" />
				</c:if>
				   </c:otherwise>
				</c:choose>
				
				
			
				
				</a></div>
				</td>
				
			</tr> 
			<tr>
				<th width="20%"><span style="color: red;"></span>手持身份证照：</th>
				<td width="30%">				
				<div class="lf salebd"><a href="#"  id="preview">
				<c:choose>
				   <c:when test="${user[0].shouchiId=='' }">  
				      无 
				   </c:when>
				   <c:otherwise>
				  <c:if test="${user[0].shouchiId.substring(0,7)=='/upload' }">
				<img id="imghead"  src="${ctx }/${user[0].shouchiId }" width="NAN" height="200" />
				</c:if>
					<c:if test="${user[0].shouchiId.substring(0,5)=='/file' }">
				<img id="imghead"  src="http://120.27.215.48:8082/duifang/${user[0].shouchiId }" width="NAN" height="200" />
				</c:if>
				<c:if test="${user[0].shouchiId.substring(0,4)=='http' }">
				<img id="imghead"  src="${user[0].shouchiId }" width="NAN" height="200" />
				</c:if>
				   </c:otherwise>
				</c:choose>
				
				</a></div>
				</td>
				
			</tr> 
		
			<tr>
				<th width="20%"><span style="color: red;"></span>真实姓名：</th>
				<td width="20%">
				
				${user[0].realName	 }
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;"></span>手机号码：</th>
				<td width="20%">				
				${user[0].telephone	 }
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;"></span>身份证号：</th>
				<td width="20%">
				
				${user[0].IdNo	 }
				</td>
			</tr>
			
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>用户注册时间：</th> -->
<!-- 				<td width="20%"> -->				
<!-- 				${user[0].createtime	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
		<tr>
				<th width="20%" ><span style="color: red;"></span>金牌成员类别：</th>			
				<td width="20%">
										
					<select id="neibu" name="user.neibu"  class="form_text">
						<option value="0" <c:if test="${param.neibu==0 }">selected="selected"</c:if>>外部金牌顾问</option>
						<option value="1" <c:if test="${param.neibu==1 }">selected="selected"</c:if>>内部金牌顾问</option>
					</select>					
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;"></span>认证：</th>
				<td width="20%">
				<input name="user.id" id="id" value="${user[0].id }" type="hidden">
					<select class="form_text" name="user.isrenzhen" id="isrenzhen">
						<option value="1" <c:if test="${user[0].isrenzhen==1 }">selected="selected"</c:if>>申请认证</option>
						<option value="2" <c:if test="${user[0].isrenzhen==2 }">selected="selected"</c:if>>认证失败</option>
						<option value="3" <c:if test="${user[0].isrenzhen==3 }">selected="selected"</c:if>>认证成功</option>
					</select>				
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
