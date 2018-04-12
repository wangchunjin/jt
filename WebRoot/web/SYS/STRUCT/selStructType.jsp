<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>安全管理</title>
<script type="text/javascript">

function AddRecord(obj)
{	
	var currFrameId = "${param.currFrameId}";
	var base_master_key = "${param.base_master_key}";
	 var base_link_id ='${param.base_link_id}';
	var folder_id = "${param.folder_id}";
	var officetplId = $("#StructType").val();
	var officetplName = $("#StructType").find("option:selected").text()
    var width = "1000"; 
    if($(parent.window).width()<1000){
   	 width = $(parent.window).width()-100;
    }   
	if(isStringNull(officetplId)){ 
		var  url = "${ctx}/KM/STRUCT/"+officetplId+".doc";
	    createSimpleWindow("AddDocWindow","结构化数据增加","${ctx}/web/SYS/STRUCT/load.jsp?currFrameId="+currFrameId+"&doc_grade=2&officetplId="+officetplId+"&base_master_key="+base_master_key+"&folder_id="+folder_id+"&url="+url+"&officetplName="+encodeURI(officetplName)+"&base_link_id="+base_link_id, width, $(parent.window).height()-200);

	}else{
		$.messager.alert("错误","请选择模板");
	}
}

function DownLoadType(){
	var officetplId = $("#StructType").val();
	if(isStringNull(officetplId)){
		window.open("${ctx}/KM/STRUCT/"+officetplId+".doc")
	}else{
		$.messager.alert("错误","请选择模板");
	}
}
</script>
</head>
<body  class="NewWinClass" >
<table width="100%" border="0">
   <tr height="25" >
      <td width="99" height="25">&nbsp;模板类型：&nbsp;</td>
      <td width="100"> 
          <epmis:select id="StructType" attr="style='width:170px'" define="SELECT OFFICETPL_ID ,OFFICETPL_NAME  FROM STRUCT_OFFICETPL T WHERE NOT EXISTS (SELECT S.WID  FROM STRUCT_OFFICETPL S WHERE S.OFFICETPL_FLAG = T.OFFICETPL_FLAG AND S.CREATED_DATE >T.CREATED_DATE) AND T.OFFICETPL_FLAG IN (SELECT OFFICETPL_FLAG FROM STRUCT_OFFICETPL_SWBS WHERE SWBS_ID=(SELECT SWBS_ID FROM DS_PLAN WHERE PLAN_ID='${param.base_master_key}' UNION SELECT SWBS_ID FROM SM_PLAN WHERE PLAN_ID='${param.base_master_key}' UNION SELECT SWBS_ID FROM SM_TEST WHERE PLAN_ID='${param.base_master_key}' UNION SELECT SWBS_ID FROM SYS_PLAN WHERE PLAN_ID='${param.base_master_key}'))  ORDER BY SEQ_NUM"></epmis:select>
      </td>
      <td>   
          &nbsp;&nbsp;&nbsp;&nbsp;
          <epmis:button id="btn1" imageCss="icon-download" value="下载" action="DownLoadType()"></epmis:button>
      </td>
    </tr>
</table>
<div class="BottomDiv">
    <a href="###" class="btn_01" onclick="AddRecord()">增加<b></b></a>
	<%--<a href="###" class="btn_01" onclick="ImpRecord()">导入<b></b></a>	
	--%><a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>					
</div>
</body>
<input type="hidden" name="show" value="${param.show}">
</html>