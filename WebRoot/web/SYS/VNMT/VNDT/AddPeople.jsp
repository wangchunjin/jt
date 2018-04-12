<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/sys/vndt/AddVndt.action");
			 if(res[0].result=="success"){
				  GetIndexFrame(currFrameId).FourFrame.location.reload(); 
				 parent.close_win('addWindow');
			 }			 
 
						     
 
         }
    //    INSERT INTO CM_PROJPART(WID,ID,ACTUAL_NAME,SEX,TEL,DEPT_NO,JOB_TITLE,PROJ_ID,SEQ_NUM) VALUES (SYS_GUID(),SYS_GUID(),'1','0','1','90D9529FDB2B4B09B15C63D5681CEF23','0365E3C70D274FAC860A77678A2C2ABE','098511993C254A00A881DD61A15AF1BE',420)
 	function changeState(obj){
	 
			SetSelect("  SELECT ID,CODE_NAME  FROM CM_CODE WHERE CODE_SHORT_NAME='"+obj.value+"' AND CODE_TYPE='STATE'  ORDER BY CODE_NAME","cmVndt\\.state","",new Array("=--请选择--")) 		   		   
	 
	}
 </script>

  </head>
 
  <body class="NewWinClass" >
   <form method="post" action="" id="addForm" name="addForm">
		<table style="width: 96%;">
			<tr class="">
				<th width="12%">联系人姓名</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.name" id="cmVndt.name" value=""></td>
				<th width="12%">性别</th>
				<td width="38%" class=""><s:radio name="cmVndt.gender" list="#{'Y':'男','N':'女'}"  value="'Y'" theme="simple"></s:radio></td>
			</tr>
			<tr class="">
				<th width="12%">联系人简称</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.initials" id="cmVndt.initials" value=""></td>
				<th width="12%">办公室电话</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.officePhone" id="cmVndt.officePhone" value=""></td>
			</tr>
			<tr class="">
				<th width="12%">地点</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.location" id="cmVndt.location" value=""></td>
				<th width="12%">手机</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.mobilePhone" id="cmVndt.mobilePhone" value=""></td>
			</tr>
	
			<tr class="">
				<th width="12%">国家</th>
				<td width="38%" class=""><epmis:select id="cmVndt.country" define=" SELECT code_short_name,CODE_NAME FROM CM_CODE T WHERE CODE_TYPE='COUNTRY'" onChange="changeState(this)" ></epmis:select></td>
				<th width="12%">E-MAIL</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.mailAddress" id="cmVndt.mailAddress" value=""></td>
			</tr>
			<tr class="">
				<th width="12%">省份</th>
				<td width="38%" class=""><epmis:select id="cmVndt.state"  onChange="changestage(this)"  ></epmis:select></td>
				<th width="12%">城市</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.city" id="cmVndt.city" value=""></td>
			</tr>
			<tr class="">
				<th width="12%">说明</th>
				<td width="38%" class="" colspan="3"><textarea class="form_textarea"  id="cmVndt.description" name="cmVndt.description"></textarea></td>
			</tr>		 
       </table>
	       <input type="hidden" id="cmVndt.vnmtId" name="cmVndt.vnmtId" value="${param.vnmtId}">							
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
