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
function nextstep(num,userId){
	 $("#S"+num).attr("class","style2");
	 num = num - (-1);
	 $("#S"+num).attr("class","style1");
	 if(num=="2"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/USER/ADD/AddRole.jsp?USER_ID="+userId;
	 }else if(num=="3"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/USER/ADD/AddModule.jsp?USER_ID="+userId;
	 }else if(num=="4"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/USER/ADD/AddModuleList.jsp?USER_ID="+userId;
	 }else if(num=="5"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/USER/ADD/AddProj.jsp?USER_ID="+userId;
	 }else if(num=="6"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/USER/ADD/AddProjfile.jsp?USER_ID="+userId;
	 }else if(num=="7"){
		 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/USER/ADD/AddPortlet.jsp?USER_ID="+userId;
	 }
}
</script>
 </head>
<body style="margin-top:45%;margin-left:5px;background-color: #f4f4f4;">
<input id="currFrameId" type="hidden" value="${param.currFrameId}"></input> 
<table width="200" border="0" cellspacing="0" cellpadding="0" style="position: absolute;top: 10px;left:15px;">
			<tr>
				<td><span ><strong>向导步骤:</strong></span></td>
			</tr>
			<tr>
				<td><span id="S1" class="style1" onclick="">1.用户基本信息</span></td>
			</tr>
			<tr>
				<td><span id="S2" class="style2" onclick="">2.分配角色</span></td>
			</tr>
			<tr>
				<td><span id="S3" class="style2" onclick="">3.分配组件</span></td>
			</tr>
			<tr>
				<td><span id="S4" class="style2" onclick="">4.分配模块</span></td>
			</tr>
			<tr>
				<td><span id="S5" class="style2" onclick="">5.分配项目</span></td>
			</tr>
			<tr>
				<td><span id="S6" class="style2" onclick="">6.分配全局安全配置</span></td>
			</tr>
	        <tr>
				<td><span id="S7" class="style2" onclick="">7.分配PROTLET</span></td>
			</tr>
			
		 
		</table>
</div>
</body>
</html>