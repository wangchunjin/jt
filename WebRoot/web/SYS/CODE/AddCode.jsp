<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/sys/code/AddCode.action");
	    		if(res[0].result=="success"){				 
                    GetIndexFrame(currFrameId).TwoFrame.location.reload();   
 				    parent.close_win('addWindow');
	    		}else{
	    			$.messager.alert("错误",res[0].result);
	    		}
						     
 
         }
           
 </script>

  </head>
  
  <body  class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			<tr>
				<th width="15%">代码</th>
				<td width="85%"><input class="form_text"  name="cmCode.codeShortName" id="cmCode.codeShortName" ></td>
			</tr>
			<tr>
				<th width="15%">名称</th>
				<td width="85%"><input class="form_text"  name="cmCode.codeName" id="cmCode.codeName" ></td>
			</tr>
 			<tr>
				<th width="15%">说明</th>
				<td width="85%"><input class="form_text"  name="cmCode.remark" id="cmCode.remark" ></td>
			</tr>
			<tr>
				<th width="15%">排序</th>
				<td width="85%"><epmis:text id="cmCode.seqNum" attr="style='text-align:right;'" define="select ifnull(max(seq_num),0)+1 from cm_code where code_type = '${param.CodeType}'" ></epmis:text></td>
			</tr>
       </table>
	       <input type="hidden" id="cmCode.codeType" name="cmCode.codeType" value="${param.CodeType}">
	       <input type="hidden" id="cmCode.codeBelongto" name="cmCode.codeBelongto" value="${param.BelongtoCodeName}">									
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
