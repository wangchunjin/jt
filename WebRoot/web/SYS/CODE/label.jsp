<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
     var node = parent.TwoFrame.onselectrow;
    if(!isNull(node)){
    	$.messager.alert("错误","请选中要修改对应的行！");
    	return;
    }
    var res=  $("#labelForm").SubmitForm("UpdateCode.action");
    if(res[0].result=="success"){
    	var index = parent.TwoFrame.$("#SysCodeTable").datagrid('getRowIndex',node);
					parent.TwoFrame.$('#SysCodeTable').datagrid('updateRow',{
						index: index,
						row: {
							CODE_SHORT_NAME: $("#cmCode\\.codeShortName").val(),
							CODE_NAME: $("#cmCode\\.codeName").val(),
							SEQ_NUM:$("#cmCode\\.seqNum").val()
						}
					});
					window.location.reload();
     }else{
  	   $.messager.alert("错误",res[0].result)
     }
  }
  </script>
  <body class="LabelWinClass">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table style="width: 96%;">
     	                          
				<tr style="line-height: 30px;">
					<th width="15%">代码</th>
					<td width="85%"><input class="label_text"   name="cmCode.codeShortName" id="cmCode.codeShortName" value="${CmCodeInfo.CODE_SHORT_NAME}"></td>
				</tr>
				<tr>
					<th width="15%">名称</th>
					<td width="85%"><input class="label_text"   name="cmCode.codeName" id="cmCode.codeName" value="${CmCodeInfo.CODE_NAME}"></td>
				</tr>
				<tr>
					<th width="15%">说明</th>
					<td width="85%"><input class="label_text"   name="cmCode.remark" id="cmCode.remark" value="${CmCodeInfo.REMARK}"></td>
				</tr>
				<tr>
					<th width="15%">排序</th>
					<td width="85%"><input class="label_text"   name="cmCode.seqNum" id="cmCode.seqNum" value="${CmCodeInfo.SEQ_NUM}"></td>
				</tr>
       </table>	
       <input type="hidden" id="cmCode.wid" name="cmCode.wid" value="${CmCodeInfo.WID}">							
       </form>
     </div> 						
  </body>
</html>
