<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/sys/catvalType/AddCatvalCode.action");
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
				<th width="15%">码值</th>
				<td width="85%"><input class="form_text"  name="cmCatval.catgShortName" id="cmCatval.catgShortName" ></td>
			</tr>
 			<tr>
				<th width="15%">说明</th>
				<td width="85%"><input class="form_text"  name="cmCatval.catgName" id="cmCatval.catgName" ></td>
			</tr>
			<tr>
				<th width="15%">排序</th>
				<td width="85%"><epmis:text id="cmCatval.seqNum" attr="style='text-align:right;'" define="select ifnull(max(seq_num),0)+1 from cm_catval where catg_type_id = '${param.catg_type_id}'" ></epmis:text></td>
			</tr>
       </table>
	       <input type="hidden" id="cmCatval.catgName" name="cmCatval.catgTypeId" value="${param.catg_type_id}">
	       <input type="hidden" id="cmCatval.parentCatgId" name="cmCatval.parentCatgId" value="${param.parentId}">									
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
