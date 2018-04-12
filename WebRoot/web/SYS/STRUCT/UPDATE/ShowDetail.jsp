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

		
		var height ="100";
	var width ="400";
	resizable="No";
		var servicechecks = document.getElementsByName("service_docid");
		var serviceIds;
		for ( var i = 0; i < servicechecks.length; i++)
		{
			if (servicechecks[i].checked)
			{
				if (isStringNull(serviceIds))
				{
					serviceIds = serviceIds + "," + servicechecks[i].value;

				} else
				{
					serviceIds = servicechecks[i].value;
				}
			}
		}
	  if(isStringNull(serviceIds))
	  {
		document.getElementById("percentShow").style.display = "";
		document.getElementById("percent").style.display = "";
		  var  res;
		 var  Ids = serviceIds.split(",");
		 var totalNum  = Ids.length;
		 var hasNum = 0;
		  var sh;
		   	sh = setInterval(function(){
		   		$.ajax({
	                	url : "${ctx}/sys/service/GetPercent.action",
	                	type: 'post',
	                    dataType: 'json',
	                    async : true,
	                    error: function(){
	                    	$.messager.alert('错误','操作出错！');
	                    },
	                    success: function(result){
	                    	//alert(result[0].totalSize)
	                        $("#percent").html("(正在下载："+result[0].hasSize+"/"+result[0].totalSize+" ,已下载"+hasNum+"/"+totalNum+")");
	                        document.getElementById("div1").style.width = hasNum*800/totalNum+"px";
	                    }
	                });
		          if(hasNum==totalNum){
                     clearInterval(sh);
                     parent.close_win('addWindow')
                  }
		   	},1);
		 	  for(var i=0;i<Ids.length;i++){
		 		        $.ajax({
			                	url : "${ctx}/sys/service/StructUpdate.action?serviceId="+Ids[i],
			                	type: 'post',
			                    dataType: 'json',
			                    async : true,
			                    error: function(){
			                    	$.messager.alert('错误','操作出错！');
			                    },
			                    success: function(result){
		                               hasNum++;	                   
		                         }
			                });
		 		  }
			
 		    	
	  }else
	  {
	   		alert("请选择更新文档！");
	  }
	}

</script>

</head>
<body onload="" class="nei-2-body-bg">
<div id="userOrgDiv1177" style="height: 85%; position: absolute; width: 90%; top:5%; left:5%; padding: 5px; overflow: auto; float: left;">
		<table border="1" width="100%">
			<tr>
				<td width="25%" align="center">office模版名称:</td>
				<td width="45%" align="center">描述:</td>
				<td width="10%" align="center">
					<input id="selectAllOrNull" name="selectAllOrNull" type="checkbox" onclick="selectAll(this)" style="margin-left: 4px"/>
				</td>
			</tr>
			<c:forEach var="dateclient" items="${MapList}">
			<tr>
				<td align="left">${dateclient.OFFICETPL_NAME}</td>
				<td align="left">${dateclient.OFFICETPL_REMARK}</td>
			<td align="center" style="text-align: center">
					<input id="service_docid2" name="service_docid" type="checkbox" value="${dateclient.OFFICETPL_ID}"   onclick="selectbox(this)" style="margin-left: 5px"/>
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
		<div style="height: 5%; position: absolute; width: 100%; top: 90%; right: 0px;">
		
		<table width="100%">
			<tr><td id="percentShow" colspan="2" width="90%" style="padding-top:0px;display: none;" align="center">
				<div style="background-color: #94EF18;height: 11px;width: 700px;font-size:1px;overflow:hidden">
				<div id = "div1" title ="title" style="position:absolute;z-index:2;left:0px;top:2px;background-color: #000FFF;height: 7px;font-size:1px;overflow:hidden"></div>
				</div>
				</td>
				</tr>
		         <tr>
				 	<td id="percent" width="85%" style="padding-left:250px;display:none; ">(正在下载：100Kb/128kb ,已下载1/8)</td>
				    <td align="right" style="padding-right:30px ">
                         <input type="button" id="con" value="确定更新"  style="cursor: pointer;"  onclick="commit()" />
				         <input type="button" id="con" value="取消更新"  style="cursor: pointer;"  onclick="parent.close_win('addWindow')" />
				    </td>
				</tr>
		</table>
	</div>
</body>
</html>
