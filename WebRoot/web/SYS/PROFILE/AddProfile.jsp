<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/sys/profile/AddProfile.action");
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
			<tr style="line-height: 30px;">
					<th width="25%">安全配置名称</th>
					<td width="75%"><input class="label_text"   name="cmProfile.profileName" id="cmProfile.profileName" ></td>
				</tr>
				<tr>
					<th width="25%">备注</th>
					<td width="75%"><textarea class="label_textarea"   name="cmProfile.remark" id="cmProfile.remark" ></textarea></td>
				</tr>
       </table>
	       <input type="hidden" id="cmProfile.profType" name="cmProfile.profType" value="${param.proftype}">		
	       						
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
