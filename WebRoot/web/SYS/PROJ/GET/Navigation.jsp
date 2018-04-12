<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>用户增加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.style1
{
color: red;
height:30px;
line-height:30px;
text-align: center;
}
.style2
{
text-align: center;
height:30px;
line-height:30px;
color: black;
}
</style>
<script type="text/javascript">
function nextstep(num,projId){
	 $("#S"+num).attr("class","style2");
	 num = num - (-1);
	 $("#S"+num).attr("class","style1");
	 var projRole = $("#projRole").val();
	 if(num=="2"){ 
		 var tbjgId = projId.substring(0,projId.indexOf(","));
		 var projName = projId.substring(projId.indexOf(",")+1);
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/PROJ/GET/AddProj.jsp?PROJ_NAME="+projName+"&TBJG_ID="+tbjgId;
	 } else if(num=="3"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/PROJ/GET/AddProjUser.jsp?PROJ_ID="+projId;
	 }else if(num=="4"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/PROJ/GET/ModuleImp.jsp?module_name="+encodeURI('项目信息')+"&tableName=ds_gcgk&PROJ_ID="+projId+"&num="+num+"&projRole="+projRole;
	 }else if(num=="5"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/PROJ/GET/ModuleImp.jsp?module_name="+encodeURI('项目信息')+"&tableName=ds_plan&PROJ_ID="+projId+"&num="+num+"&projRole="+projRole;
	 }else if(num=="6"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/PROJ/GET/ModuleImp.jsp?module_name="+encodeURI('质量安全')+"&tableName=sm_plan&PROJ_ID="+projId+"&num="+num+"&projRole="+projRole;
	 }else if(num=="7"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/PROJ/GET/ModuleImp.jsp?module_name="+encodeURI('进度管理')+"&tableName=sys_plan&PROJ_ID="+projId+"&num="+num+"&projRole="+projRole;
	 }else if(num=="8"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/PROJ/GET/ModuleImp.jsp?module_name="+encodeURI('合同造价')+"&tableName=sm_test&PROJ_ID="+projId+"&num="+num+"&projRole="+projRole;
	 }
}
</script>
 </head>
<body style="margin-top:45%;margin-left:5px;background-color: #f4f4f4;">
<input id="ParentProjId" type="hidden" value="${param.ParentProjId}"></input> 
<input id="currFrameId" type="hidden" value="${param.currFrameId}"></input> 
<input id="projRole" type="hidden" ></input> 
<table width="200" border="0" cellspacing="0" cellpadding="0" style="position: absolute;top: 10px;left:15px;">
			<tr>
				<td><span ><strong>向导步骤:</strong></span></td>
			</tr>
			<tr>
				<td><span id="S1" class="style1" onclick="">1.选择已中标项目</span></td>
			</tr>
			<tr>
				<td><span id="S2" class="style2" onclick="">2.项目基本信息</span></td>
			</tr>
			<tr>
				<td><span id="S3" class="style2" onclick="">3.分配总监</span></td>
			</tr>
			<tr>
				<td><span id="S4" class="style2" onclick="">4.载入工程概况模板</span></td>
			</tr>
			<tr>
				<td><span id="S5" class="style2" onclick="">5.载入建设程序模板</span></td>
			</tr>
			<tr>
				<td><span id="S6" class="style2" onclick="">6.载入质量安全模板</span></td>
			</tr>
			<tr>
				<td><span id="S7" class="style2" onclick="">7.载入进度管理模板</span></td>
			</tr>	 
			<tr>
				<td><span id="S8" class="style2" onclick="">8.载入合同造价模板</span></td>
			</tr>			
		 
		</table>
</div>
</body>
</html>