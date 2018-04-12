<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<html:html>
<html:base />
<head>
<title>Super导入向导</title>
<style>
body {
	background-color: #FFFFFF;
	margin-top: 20px;
}

div,td {
	font-size: 12px;
	font-family: Tahoma;
}

.steps {
	line-height: 30px;
	height: 35px;
}

.contents {
	height: 300px;
	line-height: 30px;
}

.input1 {
	font-size: 12px;
	font-family: Tahoma;
	border-left: 1px solid #808080;
	border-right: 1px solid #EEEEEE;
	border-top: 1px solid #808080;
	border-bottom: 1px solid #EEEEEE;
	height: 20px;
	width: 280px;
}
a{}
</style> 
<script language=javascript>
function downRegister(){
	var name = $("#name").val();
	var contact_way = $("#contact_way").val();
	var version = $("#version").val();
	if(isStringNull(name)&&isStringNull(contact_way)){
	var res = $('#addForm').SubmitForm("${ctx}/GetRegister.action"); 
	   if(res!=""){
		   window.open(res[0].result);
		 }
    }else{
	   $.messager.alert("错误","单位名称、联系方式任一不能为空！");
	}
}
function updateLicense(){
 
 
		
             var  flag=IntoActionByUrl("${ctx}/sys/service/UpdateLicense.action");
               if(flag[0].result=="Y"){
            	   $.messager.alert("成功","更新成功，请回首页重新登陆，若还不成功请联系软件公司！");
               }else{
            	   $.messager.alert("错误","连接云服务器失败，未注册或者未连接网络！");
               } 	  
}
</script>
</head>
<body>
 <form method="post" action="" id="addForm" name="addForm">
	<center>
	<div style="height: 460px; width: 660px;  background-image: url(${ctx}/img/intro.jpg); background-repeat: no-repeat;   padding-left: 43px; padding-top: 64px; text-align: left;">
	<div class=steps>&nbsp;<font style="color: red;" >${_error}</font></div>
	<div class=contents ><br>
	<div style="height: 220px; width: 572px; overflow: hidden; ">
	<table border=0 cellpadding=0 cellspacing=0>

<!--		<tr>-->
<!--			<td width=80 height=24>许可模块</td>-->
<!--			<td><input type="checkbox" id="moduleCodes" value="JDGL" disabled ${JDGL}/>进度管理<input type="checkbox" id="moduleCodes" value="CC" disabled ${CC}/>投资成本<input type="checkbox" id="moduleCodes" value="TM" disabled ${TM}/>招标管理<input type="checkbox" id="moduleCodes" value="QC" disabled ${QC}/>质量控制<input type="checkbox" id="moduleCodes" value="CO" disabled ${CO}/>沟通管理</td>-->
<!--		</tr>-->
<!--		<tr>-->
<!--			<td width=80 height=24>&nbsp;</td>-->
<!--			<td><input type="checkbox" id="moduleCodes" value="SM" disabled ${SM}/>安全管理<input type="checkbox" id="moduleCodes" value="KM" disabled ${KM}/>知识管理<input type="checkbox" id="moduleCodes" value="AM" disabled ${AM}/>物资管理<input type="checkbox" id="moduleCodes" value="GM" disabled ${GM}/>综合管理</td>-->
<!--		</tr>-->
<!--		<tr>-->
<!--			<td width=80 height=24>许可模块</td>-->
<!--			<td><input type="checkbox" id="moduleCodes" value="JDGL" disabled ${JDGL}/>项目信息<input type="checkbox" id="moduleCodes" value="CC" disabled ${CC}/>质量安全<input type="checkbox" id="moduleCodes" value="TM" disabled ${TM}/>合同造价<input type="checkbox" id="moduleCodes" value="QC" disabled ${QC}/>进度管理</td>-->
<!--		</tr>-->
<!--		<tr>-->
<!--			<td width=80 height=24>&nbsp;</td>-->
<!--			<td><input type="checkbox" id="moduleCodes" value="SM" disabled ${SM}/>知识管理<input type="checkbox" id="moduleCodes" value="KM" disabled ${KM}/>日常办公<input type="checkbox" id="moduleCodes" value="AM" disabled ${AM}/>职能部门<input type="checkbox" id="moduleCodes" value="GM" disabled ${GM}/>系统管理</td>-->
<!--		</tr>-->
		<tr>
			<td width=80 height=24>单位名称</td>
			<td><input type="text" id="name" name="name" Class="input1"  style="background-color: white;width:410px;" /></td>
		</tr>
		<tr>
			<td width=80 height=24>联系方式</td>
			<td><textarea  id="contact_way" name="contact_way" style="background-color: white;width:400px;" ></textarea></td>
		</tr>
		 
 		<tr>
			<td width=80 height=24>版本</td>
			<td><input type="text" id="version" name="version" Class="input1" readonly="readonly" value="1.0" style="background-color: white;width:410px;" /></td>
		</tr>
		<tr>
		    <td colspan="2"><font style="color: blue;">注：若程序首次运行，请填写您单位以上信息，导出注册信息文件后联系我司进行注册。若显示不在有效期、文件已损坏请在保证互联网通畅情况下点击“自动更新许可”，如若还不成功，请联系我司人工服务</font></td>
		</tr>
	</table>
	</div>
 	</div>
	<div class=foots>
	<div style="width: 572px; text-align: right"><input id="submit" type="button"  class="btnbg60"  value="导出注册信息" onclick="downRegister()"  />&nbsp;&nbsp;&nbsp;<input id="submit" type="button"  class="btnbg60"  value="自动更新许可" onclick="updateLicense();"/></div>
	</div>
	</center>
</form>
</body>
</html:html>