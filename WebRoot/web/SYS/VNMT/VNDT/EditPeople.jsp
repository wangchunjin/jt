<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  SaveRecord(){
            var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/sys/vndt/UpdateVndt.action");
			 if(res[0].result=="success"){
				  GetIndexFrame(currFrameId).FourFrame.location.reload(); 
				 parent.close_win('addWindow');
			 }			 
 
						     
 
         }
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
				<td width="38%" class=""><input class="form_text"  name="cmVndt.name" id="cmVndt.name" value="${CmVndtInfo.NAME}"></td>
				<th width="12%">性别</th>
				<td width="38%" class=""><s:radio name="cmVndt.gender" list="#{'Y':'男','N':'女'}"   value="#request.CmVndtInfo.GENDER" theme="simple"></s:radio></td>
			</tr>
			<tr class="">
				<th width="12%">联系人简称</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.initials" id="cmVndt.initials" value="${CmVndtInfo.INITIALS}"></td>
				<th width="12%">办公室电话</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.officePhone" id="cmVndt.officePhone" value="${CmVndtInfo.OFFICE_PHONE}"></td>
			</tr>
			<tr class="">
				<th width="12%">地点</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.location" id="cmVndt.location" value="${CmVndtInfo.LOCATION}"></td>
				<th width="12%">手机</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.mobilePhone" id="cmVndt.mobilePhone" value="${CmVndtInfo.MOBILE_PHONE}"></td>
			</tr>
	
			<tr class="">
				<th width="12%">国家</th>
				<td width="38%" class=""><epmis:select id="cmVndt.country" define=" SELECT code_short_name,CODE_NAME FROM CM_CODE T WHERE CODE_TYPE='COUNTRY'" onChange="changeState(this)" value="${CmVndtInfo.COUNTRY}" ></epmis:select></td>
				<th width="12%">E-MAIL</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.mailAddress" id="cmVndt.mailAddress" value="${CmVndtInfo.MAIL_ADDRESS}"></td>
			</tr>
			<tr class="">
				<th width="12%">省份</th>
				<td width="38%" class=""><epmis:select id="cmVndt.state" define=" SELECT ID,CODE_NAME  FROM CM_CODE WHERE CODE_SHORT_NAME='${CmVndtInfo.COUNTRY}' AND CODE_TYPE='STATE'  ORDER BY CODE_NAME"   value="${CmVndtInfo.STATE}" ></epmis:select></td>
				<th width="12%">城市</th>
				<td width="38%" class=""><input class="form_text"  name="cmVndt.city" id="cmVndt.city" value="${CmVndtInfo.CITY}"></td>
			</tr>
			<tr class="">
				<th width="12%">说明</th>
				<td width="38%" class="" colspan="3"><textarea class="form_textarea"  id="cmVndt.description" name="cmVndt.description">${CmVndtInfo.DESCRIPTION}</textarea></td>
			</tr>	 
       </table> 
	       <input type="hidden" id="cmVndt.vndtId" name="cmVndt.vndtId" value="${param.vndtId}">						
 </form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="SaveRecord();">保存<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
