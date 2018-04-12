<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>安全管理</title>
<script type="text/javascript">
function AddRecord(){
    var currFrameId = "${param.currFrameId}";
    var type = "${param.type}";
    var pwd1=$("#password").val();
	var pwd2=$("#password2").val();
	if(pwd1==""&pwd2=="")
	{
		$.messager.alert("错误","您设置的新密码为空,为保证系统安全请及时更改!"); 
		return;
	}
	if(pwd1 != pwd2)
	{
		$.messager.alert("错误","两次输入不一致");
	}
	else
	{
		var res = IntoActionByUrl("${ctx}/GetPassword.action?password="+pwd1);
   		if(res[0].result=="success"){
   			if(type=="label"){
   			 GetIndexFrame(currFrameId).FourFrame.$("#cmUsers\\.password").val(res[0].password) ;
   			 GetIndexFrame(currFrameId).FourFrame.$("#labelBottomDiv").css("display","");
   			}else{   				   			
   			 GetIndexFrame(currFrameId).$("#cmUsers\\.password").val(res[0].password)   ;
   			}

 			 parent.close_win('addWindow');
   		}
	}
} 
</script>
</head>
  <body  class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
   				<tr class="">
					<th width="20%">输入新密码</th>
					<td width="80%" class=""><input class="label_text" type="password"  name="password" id="password" ></td>
				</tr>
				<tr class="">
					<th width="20%">验证新密码</th>
					<td width="80%"><input class="label_text" type="password"   name="password2" id="password2" ></td>
				</tr>
</table>
 </form>
 			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
</body>
<input type="hidden" name="show" value="${param.show}">
</html>