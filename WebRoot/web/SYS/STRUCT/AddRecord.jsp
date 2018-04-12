<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
            var filepath = document.getElementById("uploadfile").value;
			if (filepath == "")
			{     
				$.messager.alert('错误','请选则要上传的模板文件!');
				return;
			}else{
				  var mime = filepath.toLowerCase().substr(filepath.lastIndexOf(".")); 
				   if(mime !='.doc')
				   {
						 $.messager.alert('错误','文件类型不正确，请选择.doc文件');
						 return ;
				   }
			}
             var res = $('#addForm').SubmitForm("${ctx}/sys/struct/AddStruct.action");
	    		if(res[0].result=="success"){				 
                    GetIndexFrame(currFrameId).TwoFrame.location.reload();   
 				    parent.close_win('addWindow');
	    		}else{
	    			$.messager.alert("错误",res[0].result);
	    		}
						     
 
         }
       
 </script>

  </head>
  <jsp:useBean id="now" class="java.util.Date" /> 
  <body  class="NewWinClass" onload="init()">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			<tr>
				<th width="12%">office模板:</th>
				<td width="38%" colspan="3"><input type="file" id="uploadfile" name="uploadfile" style="width: 100%; border: #999999 1px solid"></td>
			</tr>
			<tr>
				<th width="12%">名称</th>
				<td width="38%" colspan="3"><input class="form_text"  name="structOfficetpl.officetplName" id="structOfficetpl.officetplName" ></td>
			</tr>
			<tr>
				<th width="12%">备注</th>
                <td width="38%" colspan="3"><textarea class="form_textarea"  name="structOfficetpl.officetplRemark" id="structOfficetpl.officetplRemark" ></textarea></td>				
			</tr>
			 
			<tr>
				<th width="12%">排序</th>
				<td width="38%" colspan="3"><epmis:text id="structOfficetpl.seqNum" define=" select ifnull(max(seq_num),0)+1 from STRUCT_OFFICETPL "></epmis:text></td>
			</tr>
       </table>		          
        <input type="hidden" id="structOfficetpl.officetplFlag" name="structOfficetpl.officetplFlag" value="${param.OFFICETPL_FLAG}">					
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
