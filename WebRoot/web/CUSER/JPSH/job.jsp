<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript" src="js/imagePreview.js"></script>
<script type="text/javascript">

	function UpdateRecord() {
		var currFrameId = "${param.currFrameId}";
		var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
		
		

			
			
		var job_yname=$("#job_yname").val();
			
			
			
			
		
		var res = $('#addForm').SubmitForm("${ctx}/user/tuser/gw.action?job_yname="+job_yname);
		if (res[0].result == "success") {
			GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
			parent.close_win('eidtWindows');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}

	}
	
	

	
	
	$(function(){
// 		var type =${param.neibu};
// 		var job_num=${param.job_num};
		
// 		if(type==0){
//      		SetSelect("SELECT id,jnum FROM t_jnum WHERE type='0' and status='0' and id<>'"+job_num+"'","prize\\.tid",job_num,new Array("=--请选择--")); 
     		
//      	}else if(type==1){
//      		SetSelect("SELECT id,jnum FROM t_jnum WHERE type='0' and status='1' and id<>'"+job_num+"' ","prize\\.tid",job_num,new Array("=--请选择--")); 
     		
//      	}
		
		
		
	});
	
	
	function check(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	  
</script>
<style type="text/css">
#preview {
	width: 100px;
	height: 130px;
	overflow: hidden;
}

#imghead {
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
}
</style>
</head>
<body class="NewWinClass">
	 <div style="height:500px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
		
		<tr id="hhh">
			<th width="20%" align="right"><span style="color: red;">*</span>金牌成员类别：</th>
			
				<td>
					<input type="hidden" class="form_text"  name="user.id" id="id"  value="${param.id}">
					
					<select id="neibu" name="user.neibu" onchange="check();" class="form_text">
						<option value="0" <c:if test="${param.neibu==0 }">selected="selected"</c:if>>外部金牌顾问</option>
						<option value="1" <c:if test="${param.neibu==1 }">selected="selected"</c:if>>内部金牌顾问</option>
					</select>					
				</td>
			</tr>
			<tr id="aaa"  >
				<th width="20%" align="right"><span style="color: red;">*</span>金牌成员工号：</th>
				<td width="20%">
					<input name="user.job_num" id="job_num" value="${param.job_name }" class="form_text" >
					<input type="hidden"  id="job_yname" value="${param.job_name }" class="form_text" >
					
<!-- 				<epmis:select  id="prize.tid" define=" "  attr="style='width: 100%;'"   ></epmis:select> -->
				
				</td>
			</tr>
			
			
			
			

			
			
			
			
			
			
				
       </table>
       	
</form>
</div>
	<div class="BottomDiv">
		<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> <a
			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>
	</div>
</body>
</html>
