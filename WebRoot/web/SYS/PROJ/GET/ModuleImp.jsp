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
 if(module_name=="进度管理"){
	 $("#jd").css("display","");
	 SetSelect("SELECT MODULE_ID,MODULE_NAME FROM PLAN_MODULE ORDER BY MODULE_NAME","PlanType","",new Array("=--请选择模板类型--"))
	 
 }
     SetSelect("SELECT SWBS_TYPE_ID ID,SWBS_TYPE_NAME NAME FROM SM_SWBSTYPE WHERE MODLENAME='"+module_name+"' ORDER BY SWBS_TYPE_NAME","swbsTypeName","",new Array("=--请选择模板类型--"))
  var tableName=$("#tableName").val();
      if(tableName=="sm_test"){
    	  $(".btn_01").html("完成<b></b>");
      } 
}
var onclickNum=0;
function ImpModule()
{   
	var swbs_type_id=$("#swbsTypeName").val();
	  var module_name =  $("#module_name").val() ;
	if(!isStringNull(swbs_type_id)){
		$.messager.alert("提醒","请选择类型");
		return;
	}
	if(onclickNum==0){
		onclickNum =1;
	var tableName=$("#tableName").val();
	var projId=$("#projId").val();
	IntoActionByUrl("${ctx}/sys/proj/ModuleImport.action?swbs_type_id="+swbs_type_id+"&tableName="+tableName+"&projId="+projId)
     if(module_name=="进度管理"){
    	 var PlanType=$("#PlanType").val();
    		IntoActionByUrl("${ctx}/jd/jd_plan/ModuleImport.action?module_id="+PlanType)
    }
	var num=$("#num").val();
 
	  var projRole = "${param.projRole}";
	  if(isStringNull(projRole)){
	    sql = "UPDATE  SM_PLANUSER SET USER_ID  = '"+projRole+"' WHERE PROJ_ID = '"+projId+"' AND USER_ID !='"+projRole+"' ";
	    res=GetXmlBySql(sql);
	  }	
    if(tableName=="sm_test"){
        
      parent.parent.close_win('addProjWindow');
    }else{
    	parent.OneFrame.nextstep(num,projId);
     }
	}
    onclickNum =0;
     
}
 
</script>
</head>
<body onload="initModeType()">
 
<table width="100%" border="0" style="margin-top: 60px;">
   <tr height="25" >
      <td width="30%" height="25" align="right">&nbsp;模板类型：&nbsp;</td>
      <td width="70%">
  	  <select   name="swbsTypeName" id="swbsTypeName"   style="width:170px" />
 
      </td>
 
 
    </tr>
       <tr height="25"  id="jd" style="display: none;">
      <td width="30%" height="25" align="right">&nbsp;计划编制载入：&nbsp;</td>
      <td width="70%">
  	  <select   name="PlanType" id="PlanType"   style="width:170px" />
 
      </td>
 
 
    </tr>
</table>
 					<div class="BottomDiv">
					<a href="###" class="btn_01" onclick="ImpModule();">下一步<b></b></a> 		
			</div>
</body>
<input type="hidden" id="module_name" value="${param.module_name}">
<input type="hidden" id="projId" value="${param.PROJ_ID}"> 
<input type="hidden" id="tableName" value="${param.tableName}"/>
<input type="hidden" id="num" value="${param.num}"/>
</html>