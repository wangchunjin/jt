<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ page import="java.util.List"%>
<html>
<head>
<title>KM_OPTION维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript" src="/super/app/js/calendar/calendar.js"
	type="text/javascript" charset="UTF-8"></script>
<script type="text/javascript">
function selectAll(obj){
	var servicechecks = document.getElementsByName("service_docid");
	for ( var i = 0; i < servicechecks.length; i++)
	{
		if(obj.checked==true){
		servicechecks[i].checked= true;
		}else{
			servicechecks[i].checked= false;
		}
	}
	
}

function selectbox(obj){
 
	if(obj.checked==false){
	 
	 var a = document.getElementsByName("selectAllOrNull");
		 a[0].checked=false;
	}
}
function commit()
{ 
	var servicechecks = document.getElementsByName("service_docid");
	var serviceIds;
	var num = 0;
	for ( var i = 0; i < servicechecks.length; i++)
	{
		if (servicechecks[i].checked)
		{ 
			if (isStringNull(serviceIds))
			{
				serviceIds = serviceIds + "~" + servicechecks[i].value+"@"+$(servicechecks[i]).attr("orginvalue");

			} else
			{
				serviceIds = servicechecks[i].value+"@"+$(servicechecks[i]).attr("orginvalue");
			}
			num++;
		}
	}  
	  if(isStringNull(serviceIds))
	  {
		  var modle_name = '${param.module_name}';
 		 var res = IntoActionByUrl("${ctx}/sys/service/SwbsUpdate.action?serviceIds="+encodeURI(serviceIds)+"&modle_name="+encodeURI(modle_name));
 
  	  }

	 parent.close_win('addWindow'); 
}
</script>

</head>
<body onload="" class="nei-2-body-bg">
<div id="userOrgDiv1177" style="height: 85%; position: absolute; width: 90%; top:5%; left:5%; padding: 5px; overflow: auto; float: left;">
		<table border="1" width="100%">
			<tr>
				<td width="25%" align="center">模版名称:</td>
				<td width="45%" align="center">描述:</td>
				<td width="10%" align="center">
					<input id="selectAllOrNull" name="selectAllOrNull" type="checkbox" onclick="selectAll(this)" style="margin-left: 4px"/>
				</td>
			</tr>
			<c:forEach var="dateclient" items="${MapList}">
			<tr>
				<td align="left">${dateclient.swbs_type_name}</td>
				<td align="left">${dateclient.description}</td>
			<td align="center" style="text-align: center">
					<input id="service_docid2" name="service_docid" type="checkbox" value="${dateclient.swbs_type_name}" orginvalue = "${dateclient.description}"  onclick="selectbox(this)" style="margin-left: 5px"/>
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
		<div style="height: 5%; position: absolute; width: 100%; top: 90%; right: 0px;">
		<table width="100%">
			<tr>
				<td align="right" height="20" style="padding-right: 30px;">
				         <input type="button" id="con"  style="cursor: pointer;"  value="确定更新" onclick="commit()" />
				         <input type="button" id="con"  style="cursor: pointer;"  value="取消更新" onclick="parent.close_win('addWindow')" />
			
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
