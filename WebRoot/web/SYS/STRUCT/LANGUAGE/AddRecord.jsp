<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
			 function  AddRecord(){
			     var currFrameId = "${param.currFrameId}";
			    var res = $("#addForm").SubmitForm("${ctx}/sys/struct/addLanguage.action");
			     if(res[0].result=="success"){
			         var node = GetIndexFrame(currFrameId).TwoFrame.$('#LanguageTree').treegrid('getSelected');
			         if(isNull(node)){                  
			   	 	   GetIndexFrame(currFrameId).TwoFrame.TreeGrid_RefreshNode(node.ID,"LanguageTree");                   
			          }else{
			       	   GetIndexFrame(currFrameId).TwoFrame.location.reload();   
			          }		 
			         parent.close_win('addWindow');
					    
					 }else{
					    $.messager.alert("错误",res[0].result);
					 }
			  }       
 </script>

  </head>
  <body  class="NewWinClass" onload="init()">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			<tr>
				<th width="12%">节点类型</th>
				<td width="38%" colspan="3"><input type="radio" name="structLanguage.type" value="WBS" checked >分类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="structLanguage.type" value="TASK">语言</td>
			</tr>
			<tr>
				<th width="12%">名称</th>
				<td width="38%" colspan="3"><input class="form_text"  name="structLanguage.name" id="structLanguage.name" ></td>
			</tr>			 
			<tr>
				<th width="12%">排序</th>
				<td width="38%" colspan="3"><epmis:text id="structLanguage.seqNum" define=" select ifnull(max(seq_num),0)+1 from STRUCT_LANGUAGE WHERE parent_id='${param.parentId}'"></epmis:text></td>
			</tr>
       </table>		          
        <input type="hidden" id="structLanguage.parentId" name="structLanguage.parentId" value="${param.parentId}">	
        <input type="hidden" id="structLanguage.isPublic" name="structLanguage.isPublic" value="Y">	
        <input type="hidden" id="structLanguage.createdBy" name="structLanguage.createdBy" value="${sessionScope.UserInfo.userId}">					
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
