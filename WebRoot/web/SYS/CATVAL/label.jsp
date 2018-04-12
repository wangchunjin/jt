<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
     var node = parent.TwoFrame.$('#CatvalTree').treegrid('getSelected');
    if(!isNull(node)){
    	$.messager.alert("错误","请选中要修改对应的行！");
    	return;
    }
    var res=  $("#labelForm").SubmitForm("UpdateCatvalCode.action");
    if(res[0].result=="success"){
	    	parent.TwoFrame.$('#CatvalTree').treegrid('update',{
				id: node.CATG_ID,
				row: {
					CATG_SHORT_NAME: $("#cmCatval\\.catgShortName").val(),
					CATG_NAME: $("#cmCatval\\.catgName").val()
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
					<th width="15%">码值</th>
					<td width="85%"><input class="label_text"   name="cmCatval.catgShortName" id="cmCatval.catgShortName" value="${CmCatvalInfo.CATG_SHORT_NAME}"></td>
				</tr>
				<tr>
					<th width="15%">说明</th>
					<td width="85%"><input class="label_text"   name="cmCatval.catgName" id="cmCatval.catgName" value="${CmCatvalInfo.CATG_NAME}"></td>
				</tr>
				<tr>
					<th width="15%">排序</th>
					<td width="85%"><input class="label_text"   name="cmCatval.seqNum" id="cmCatval.seqNum" value="${CmCatvalInfo.SEQ_NUM}"></td>
				</tr>
       </table>	
       <input type="hidden" id="cmCatval.catgId" name="cmCatval.catgId" value="${CmCatvalInfo.CATG_ID}">							
       </form>
     </div> 						
  </body>
</html>
