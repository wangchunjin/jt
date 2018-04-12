<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>安全管理</title>
<script type="text/javascript">
function AddRecord(){
    var title = document.getElementById("title").value;
     var filepath = document.getElementById("pic").value;
     var headpath = document.getElementById("headpic").value;
			if (filepath != "")
			{     
 
				  var mime = filepath.toLowerCase().substr(filepath.lastIndexOf(".")); 
				   if(mime !='.png'&&mime !='.jpg')
				   {
						 $.messager.alert('错误','文件类型不正确!图片格式必须是png、jpg文件');
						 return ;
				   }
			}
			if (headpath != "")
			{     
 
				  var mime = headpath.toLowerCase().substr(headpath.lastIndexOf(".")); 
				   if(mime !='.png'&&mime !='.jpg')
				   {
						 $.messager.alert('错误','文件类型不正确!图片格式必须是png、jpg文件');
						 return ;
				   }
			}
             var res = $('#addForm').SubmitForm("${ctx}/saveLoginPic.action");
	    		if(res[0].result=="success"){				 
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
   				<tr class="">
					<th width="20%">登陆页标题</th>
					<td width="80%" class=""><input class="label_text" type="text"  name="title" id="title" ></td>
				</tr>
				<tr class="">
					<th width="20%">登陆页图片</th>
					<td width="80%"><input type="file" id="pic" name="pic" style="width: 100%; border: #999999 1px solid" ></td>
				</tr>
				<tr class="">
					<th width="20%">首页logo图片</th>
					<td width="80%"><input type="file" id="headpic" name="headpic" style="width: 100%; border: #999999 1px solid" ></td>
				</tr>
</table>
 </form>
 			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
</body>
<input type="hidden" name="show" value="${param.show}">
</html>