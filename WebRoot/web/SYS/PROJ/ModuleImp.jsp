<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>安全管理</title>
<script type="text/javascript">
function initModeType(){
   
   var module_name =  $("#module_name").val() ;
 
     SetSelect("SELECT SWBS_TYPE_ID ID,SWBS_TYPE_NAME NAME FROM SM_SWBSTYPE WHERE MODLENAME='"+module_name+"' ORDER BY SWBS_TYPE_NAME","swbsTypeName","",new Array("=--请选择模板类型--"))
   
}
function ImpModule()
{   
	var swbs_type_id=$("#swbsTypeName").val();
	if(!isStringNull(swbs_type_id)){
		$.messager.alert("提醒","请选择类型");
		return;
	}
	var tableName=$("#tableName").val();
	var projId=$("#projId").val();
	IntoActionByUrl("${ctx}/sys/proj/ModuleImport.action?swbs_type_id="+swbs_type_id+"&tableName="+tableName+"&projId="+projId)
	var tableName=$("#tableName").val();
	var currFrameId=$("#currFrameId").val();
	var nextModuleName="";
	var nextTableName="";
	var nextMsgName="";
	if(tableName=="ds_gcgk"){
		nextMsgName = "载入建设程序模板";
		nextModuleName = "项目信息";
		nextTableName = "ds_plan";
		
	}else if(tableName=="ds_plan"){
		nextMsgName = "载入质量安全模板";
		nextModuleName = "质量安全";
		nextTableName = "sm_plan";
		
	}else if(tableName=="sm_plan"){
		nextMsgName = "载入进度管理模板";
		nextModuleName = "进度管理";
		nextTableName = "sys_plan";
		
	}else if(tableName=="sys_plan"){
		nextMsgName = "载入合同造价模板";
		nextModuleName = "合同造价";
		nextTableName = "sm_test";
	}
    if(tableName=="sm_test"){
       
      GetIndexFrame(currFrameId).TwoFrame.$.messager.alert("信息","载入成功");
      parent.close_win('addModuleWindow');
    }else{
      GetIndexFrame(currFrameId).OneFrame.createSimpleWindow("addModuleWindow",nextMsgName,"${ctx}/web/SYS/PROJ/ModuleImp.jsp?module_name="+nextModuleName+"&tableName="+nextTableName+"&projId="+projId+"&currFrameId="+currFrameId, 350, 200);      
    }
      
     
}
 
</script>
</head>
<body onload="initModeType()">
 
<table width="100%" border="0" style="margin-top: 60px;">
   <tr height="25" >
      <td width="70" height="25">&nbsp;模板类型：&nbsp;</td>
      <td width="100">
  	  <select   name="swbsTypeName" id="swbsTypeName"   style="width:170px" />
 
      </td>
      <td>   
&nbsp;&nbsp;&nbsp;&nbsp;
             <epmis:button id="btn1" imageCss="icon-add" value="导入" action="ImpModule()"></epmis:button>
 
         </td>
 
    </tr>
</table>
 
</body>
<input type="hidden" id="module_name" value="${param.module_name}">
<input type="hidden" id="projId" value="${param.projId}">
<input type="hidden" id="currFrameId" value="${param.currFrameId}"/>
<input type="hidden" id="tableName" value="${param.tableName}"/>
</html>