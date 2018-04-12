<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/sys/swbs/AddSwbs.action");
	    		if(res[0].result=="success"){
					 var node = GetIndexFrame(currFrameId).TwoFrame.$('#SwbsTree').treegrid('getSelected');
					 if(isNull(node)){                  
                 	 	   GetIndexFrame(currFrameId).TwoFrame.TreeGrid_RefreshNode(node.SWBS_ID,"SwbsTree");                   
                        }else{
                     	   GetIndexFrame(currFrameId).TwoFrame.location.reload();   
                        }
					 
				    parent.close_win('addWindow');
	    		}else{
	    			$.messager.alert("错误",res[0].result);
	    		}
						     
 
         }
           
 </script>

  </head>
  
  <body class="NewWinClass" >
   <form method="post" action="" id="addForm" name="addForm">
		<table style="width: 96%;">
				<tr>
					<th width="15%">代码</th>
					<td width="85%"><input class="form_text"   name="smSwbs.swbsShortName" id="smSwbs.swbsShortName" value="${param.newShortName}" ></td>
				</tr>
				<tr>
					<th width="15%">名称</th>
					<td width="85%"><input class="form_text"   name="smSwbs.swbsName" id="smSwbs.swbsName" ></td>
				</tr>

			<tr>
				<th width="15%">备注</th>
				<td width="85%"><textarea class="form_textarea" style="width:95%;height: 50px;" name="smSwbs.remark" id="smSwbs.remark" ></textarea></td>
			</tr>
				<tr>
					<th width="15%">排序</th>
					<td width="85%">
					<epmis:text id="smSwbs.seqNum"  define="SELECT ifnull(MAX(SEQ_NUM),0)+10 NUM FROM SM_SWBS WHERE PARENT_SWBS_ID='${param.parentId}' AND SWBS_TYPE_ID = '${param.swbs_type_id }'"  ></epmis:text>
				 
				</tr>
       </table>
	       <input type="hidden" id="smSwbs.parentSwbsId" name="smSwbs.parentSwbsId" value="${param.parentId}">	
	        <input type="hidden" id="smSwbs.nodeType" name="smSwbs.nodeType" value="${param.node_type}">
	        <input type="hidden" id="smSwbs.swbsTypeId" name="smSwbs.swbsTypeId" value="${param.swbs_type_id}">
	        								
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
