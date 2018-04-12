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
	 <div style="height:400px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			<input class="form_text" type="hidden" name="crecord.cuid" id="cuid"  value="${sessionScope.UserInfo.userId}">
			<input class="form_text" type="hidden" name="crecord.order_id" id="order_id"  value="${crecord[0].order_id}">
			<input class="form_text" type="hidden" name="crecord.id" id="id"  value="${crecord[0].id}">
			<tr   >
				<th width="20%" align="right"><span style="color: red;">*</span>还款意愿：</th>
				<td width="30%" align="left">
				<epmis:select id="crecord.rep_willingness" define="select id,name from rep_willingness where isdel='0' " value="${crecord[0].rep_willingness}" attr="style='width: 100%;'" onChange="" ></epmis:select>
				</td>
			</tr>
			
			<tr>
				<th width="20%" align="right"><span style="color: red;">*</span>沟通结果：</th>
				<td width="20%" align="left">			
					<epmis:select id="crecord.comm_results" define="select id,name from comm_results where isdel='0' " value="${crecord[0].comm_results}"  attr="style='width: 100%;'" onChange="" ></epmis:select>
				</td>
			</tr>
			<tr>
				<th width="20%" align="right"><span style="color: red;">*</span>通讯录联系结果：</th>
				<td width="20%" align="left">			
					<select class="form_text" disabled="disabled" name="crecord.contact_book_type" id="contact_book_type">
						<option value="">--请选择--</option>
						<option value="1" <c:if test="${crecord[0].contact_book_type==1}">selected="selected"</c:if>>没有联系通讯录</option>
						<option value="2" <c:if test="${crecord[0].contact_book_type==2}">selected="selected"</c:if>>只联系紧急联系人</option>
						<option value="3" <c:if test="${crecord[0].contact_book_type==3}">selected="selected"</c:if>>只联系其它联系人</option>
						<option value="4" <c:if test="${crecord[0].contact_book_type==4}">selected="selected"</c:if>>紧急联系人和其它联系人都联系</option>
						
					</select>
				</td>
			</tr>
			
			<tr style="display: none;">
				<th width="20%" align="right"><span style="color: red;">*</span>通讯录联系情况：</th>
				<td width="20%">
					<textarea name="crecord.cus_beh" id="cus_beh" rows="5" cols="" readonly="readonly">${crecord[0].cus_beh}</textarea>
				</td>
			</tr>
			
			
			
			<tr style="display: none;">
				<th width="20%" align="right"><span style="color: red;" >*</span>客户行为：</th>
				<td width="20%">
					<textarea name="crecord.cus_beh" id="cus_beh" rows="5" cols="" readonly="readonly">${crecord[0].cus_beh}</textarea>
				</td>
			</tr>
			<tr>
				<th width="20%" align="right"><span style="color: red;">*</span>备注：</th>
				<td width="20%">
					<textarea name="crecord.content" id="content" rows="5" cols="" readonly="readonly">${crecord[0].content}</textarea>
				</td>
			</tr>
			
			
			
       </table>
       	
</form>
</div>
	<div class="BottomDiv">
<!-- 		<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a>  -->
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>
	</div>
</body>
</html>
