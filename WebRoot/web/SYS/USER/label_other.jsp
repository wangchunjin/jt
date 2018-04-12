<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
     var node = parent.TwoFrame.$('#SysUserTable').datagrid('getSelected');
    if(!isNull(node)){
    	$.messager.alert("错误","请选中要修改对应的行！");
    	return;
    }
    var res=  $("#labelForm").SubmitForm("UpdateUserOther.action");
    if(res[0].result=="success"){
        window.location.reload();
     }else{
  	   $.messager.alert("错误",res[0].result)
     }
  }
  </script>
  <body class="LabelWinClass">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table style="width: 96%;"> 
     	        <tr class="">
					<th width="12%">办公室电话</th>
					<td width="38%" class=""><input class="label_text"   name="cmUsers.telNoDept" id="cmUsers.telNoDept" value="${CmUserInfo.TEL_NO_DEPT}"></td>
					<th width="12%">住宅电话</th>
					<td width="38%" class=""><input class="label_text"   name="cmUsers.telNoHome" id="cmUsers.telNoHome" value="${CmUserInfo.TEL_NO_HOME}"></td>
				</tr>
				<tr class="">
					<th width="12%">办公传真</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.faxNoDept" id="cmUsers.faxNoDept" value="${CmUserInfo.FAX_NO_DEPT}"></td>
					<th width="12%">住宅地址</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.addHome" id="cmUsers.addHome" value="${CmUserInfo.ADD_HOME}"></td>
				</tr>
				<tr class="">
					<th width="12%">手机</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.mobilNo" id="cmUsers.mobilNo" value="${CmUserInfo.MOBIL_NO}"></td>
					<th width="12%">住宅邮编</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.postNoHome" id="cmUsers.postNoHome" value="${CmUserInfo.POST_NO_HOME}"></td>
				</tr>
				<tr class="">
					<th width="12%">电子邮件</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.email" id="cmUsers.email" value="${CmUserInfo.EMAIL}"></td>
					<th width="12%">微信号</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.oicqNo" id="cmUsers.oicqNo" value="${CmUserInfo.OICQ_NO}"></td>
				</tr>
				<tr class="" style="display: none;">
					<th width="12%">邮件密码</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.emailPassword" id="cmUsers.emailPassword" value="${CmUserInfo.EMAIL_PASSWORD}"></td>
					<th width="12%">MSN号码</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.msn" id="cmUsers.msn" value="${CmUserInfo.MSN}"></td>
				</tr>
				<tr class="" style="display: none;">
					<th width="12%">RTX用户名</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.rtxName" id="cmUsers.rtxName" value="${CmUserInfo.RTX_NAME}"></td>
				</tr>
       </table>	
       <input type="hidden" id="cmUsers.userId" name="cmUsers.userId" value="${CmUserInfo.USER_ID}">							
       </form>
     </div> 						
  </body>
</html>
