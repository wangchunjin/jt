<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
     var node = parent.TwoFrame.$('#SysRoleTree').treegrid('getSelected');
    if(!isNull(node)){
    	$.messager.alert("错误","请选中要修改对应的行！");
    	return;
    }
    var res=  $("#labelForm").SubmitForm("UpdateRole.action");
    if(res[0].result=="success"){
		    	parent.TwoFrame.$('#SysRoleTree').treegrid('update',{
					id: node.ROLE_ID,
					row: {
						ROLE_NAME: $("#cmRole\\.roleName").val()
					}
				});
					window.location.reload();
     }else{
  	   $.messager.alert("错误",res[0].result);
     }
  }
  </script>
  <body class="LabelWinClass">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
       
     	<table style="width: 96%;">
				<tr class="">
					<th width="12%">名称</th>
					<td width="88%" class=""><input class="label_text"   name="cmRole.roleName" id="cmRole.roleName" value="${CmRoleInfo.ROLE_NAME}"></td>
				</tr>
	
				<tr class="">
					<th width="12%">类别</th>
					<td width="88%" class=""><s:radio name="cmRole.roleType" list="#{'0':'分类','1':'角色'}"   value="#request.CmRoleInfo.ROLE_TYPE" theme="simple" disabled="true" ></s:radio></td>
               </tr>
               	<tr>
					<th width="12%">排序</th>
					<td width="88%"><input class="label_text"   name="cmRole.seqNum" id="cmRole.seqNum" value="${CmRoleInfo.SEQ_NUM}"></td>
				</tr>
				<tr>
					<th width="12%">上一级角色名称</th>
					<td width="88%"><textarea  class="label_textarea"   name="cmRole.remark" id="cmRole.remark" >${CmRoleInfo.REMARK}</textarea></td>
				</tr>
       </table>	
       <input type="hidden" id="cmRole.roleId" name="cmRole.roleId" value="${CmRoleInfo.ROLE_ID}">					
       </form>
     </div> 						
  </body>
</html>
