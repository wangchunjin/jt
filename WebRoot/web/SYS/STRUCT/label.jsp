<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
     var node = parent.TwoFrame.$('#StructTable').datagrid('getSelected');
    if(!isNull(node)){
    	$.messager.alert("错误","请选中要修改对应的行！");
    	return;
    }
    var res=  $("#labelForm").SubmitForm("UpdateStruct.action");
    if(res[0].result=="success"){
    	var index = parent.TwoFrame.$("#StructTable").datagrid('getRowIndex',node);
					parent.TwoFrame.$('#StructTable').datagrid('updateRow',{
						index: index,
						row: {
							OFFICETPL_NAME: $("#structOfficetpl\\.officetplName").val(),
							OFFICETPL_REMARK:  $("#structOfficetpl\\.officetplRemark").val(),
							SEQ_NUM:$("#structOfficetpl\\.seqNum").val()
						}
					});
					window.location.reload();
     }else{
  	   $.messager.alert("错误",res[0].result)
     }
  }
  
            function openWin(id){
        	    var currFrameId =  parent.frameElement.id;
        	    var module_code = currFrameId.replace("iframe_","");
        		var wtop=(window.screen.height-530)/2;
				var wleft=(window.screen.width-550)/2;
				if(id=="dsZdsg.director"){
		 		    window.open('${ctx}/web/SYS/USER/USER_LIST/OpenWindow.jsp?COLUMN_ID='+id,'','width=550 ,height=530,left='+wleft+',top='+wtop);
				}else{
		 		    window.open('${ctx}/web/DS/DS_FOLDER/OpenWindow.jsp?MODULE_CODE='+module_code+'&COLUMN_ID='+id+'&CODE_ID=','','width=550 ,height=530,left='+wleft+',top='+wtop);
		        }
         } 
      
  </script>
  <body class="LabelWinClass">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table style="width: 96%;">
         			<tr>
				<th width="12%">名称</th>
				<td width="38%" colspan="3"><input class="label_text"  name="structOfficetpl.officetplName" id="structOfficetpl.officetplName" value="${StructInfo.OFFICETPL_NAME}" ></td>
			</tr>
			<tr>
				<th width="12%">备注</th>
                <td width="38%" colspan="3"><textarea class="label_textarea"  name="structOfficetpl.officetplRemark" id="structOfficetpl.officetplRemark" >${StructInfo.OFFICETPL_REMARK}</textarea></td>				
			</tr>
			 
			<tr>
				<th width="12%">排序</th>
				<td width="38%" colspan="3"><input class="label_text"  name="structOfficetpl.seqNum" id="structOfficetpl.seqNum"  value="${StructInfo.SEQ_NUM}"  /></td>
			</tr>
       </table>	
       <input type="hidden" id="structOfficetpl.officetplId" name="structOfficetpl.officetplId" value="${StructInfo.OFFICETPL_ID}">							
       </form>
     </div> 						
  </body>
</html>
