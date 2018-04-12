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
	var params = new Array();
	 res = IntoActionByUrl("${ctx}/GetRegister.action");
	   if(res!=""){
		   window.open(res[0].result);
		 }
	   
}
function  AddRecord(){
 
            var filepath = document.getElementById("uploadfile").value;
			if (filepath == "")
			{     
				alert('请选择许可文件!');
			}else{
				 var res =  $('#addForm').SubmitForm("${ctx}/Register.action");
				 location.reload();
			}
             
						     
 
         }
</script>
</head>
<body>
 <form method="post" action="" id="addForm" name="addForm">
	<center>
	<div style="height: 460px; width: 660px;  background-image: url(${ctx}/img/intro.jpg); background-repeat: no-repeat;   padding-left: 43px; padding-top: 64px; text-align: left;">
	<div class=steps>导入注册信息&nbsp;<font style="color: blue;" >${ _info}</font><font style="color: red;">${_error}</font></div>
	<div class=contents ><br>
	<div style="height: 220px; width: 572px; overflow: auto; ">
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
			<td><input type="text" id="name" Class="input1"   /></td>
		</tr>
		<tr>
			<td width=80 height=24>联系方式</td>
			<td><textarea  id="name" style="background-color: white;" ></textarea></td>
		</tr>
		<tr>
			<td width=80 height=24>开始时间</td>
			<td><input type="text" id="startTime" Class="input1" disabled value="${StartDate}"/></td>
		</tr>
		<tr>
			<td width=80 height=24>结束日期</td>
			<td><input type="text" id="endTime" Class="input1" disabled value="${EndDate}"/></td>
		</tr>
		<tr style="display: ${not empty _info ? 'none' : ''};">
			<td width=80 height=24>导入许可文件</td>
			<td><input type="file" id="uploadfile" name="uploadfile" class="input1"> </td>
		</tr>	
		<tr>
			<td width=80 height=24></td>
			<td><a href="#" onclick="downRegister()"><font style="color: blue;" >(获取版本的注册信息)</font></a>	</td>
		</tr>
		<input type="hidden" id="version" name ="version" value="1.0"  />
	</table>
	</div>
	<a style="display: ${not empty _info ? '' : 'none'};" href="${ctx}/login.jsp">转向主页</a>
	</div>
	<div class=foots>
	<div style="width: 572px; text-align: right"><input id="submit" type="submit"  class="btnbg60"  value="确 定" onclick="AddRecord()" style="display: ${not empty _info ? 'none' : ''};"/>&nbsp;&nbsp;&nbsp;<input id="submit" type="button"  class="btnbg60"  value="关 闭" onclick="window.close();"/></div>
	</div>
	</center>
</form>
</body>
</html:html>