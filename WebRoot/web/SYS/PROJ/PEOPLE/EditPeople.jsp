<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  SaveRecord(){
            var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/sys/proj/SavePeople.action");
			 if(res[0].result=="success"){
				  GetIndexFrame(currFrameId).FourFrame.location.reload(); 
				 parent.close_win('addWindow');
			 }			 
 
						     
 
         }
    //    INSERT INTO CM_PROJPART(WID,ID,ACTUAL_NAME,SEX,TEL,DEPT_NO,JOB_TITLE,PROJ_ID,SEQ_NUM) VALUES (SYS_GUID(),SYS_GUID(),'1','0','1','90D9529FDB2B4B09B15C63D5681CEF23','0365E3C70D274FAC860A77678A2C2ABE','098511993C254A00A881DD61A15AF1BE',420)
 
 </script>

  </head>
 
  <body class="NewWinClass" >
   <form method="post" action="" id="addForm" name="addForm">
		<table style="width: 96%;">
			<tr >
				<th width="12%">姓名</th>
				<td width="38%"  ><input class="form_text"  name="cmProjpart.actualName" id="cmProjpart.actualName" value="${PeopleInfo.ACTUAL_NAME}"></td>
				<th width="12%">性别</th>
				<td width="38%"  ><s:radio name="cmProjpart.sex" list="#{'0':'男','1':'女'}"  value="#request.PeopleInfo.SEX"    theme="simple"></s:radio> </td>
			</tr>
			<tr >
				<th width="12%">联系方式</th>
				<td width="38%"  ><input class="form_text"  name="cmProjpart.tel" id="cmProjpart.tel" value="${PeopleInfo.TEL}"></td>
				<th width="12%">所属机构</th>
				<td width="38%"  ><epmis:select id="cmProjpart.deptNo" define=" SELECT DEPT_NO,DEPT_NAME FROM CM_DEPART WHERE PARENT_DEPT_NO='0' AND DEPT_TYPE='SYS_DEPART' ORDER BY SEQ_NUM" value="${PeopleInfo.DEPT_NO}"  ></epmis:select></td>
			</tr>
			<tr >
			<th width="12%">职务</th>	
			<td width="38%"  ><epmis:select id="cmProjpart.jobTitle" define="  SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='JOB_TITLE'" value="${PeopleInfo.JOB_TITLE}" ></epmis:select></td>
			<th width="12%">排序</th>
			<td width="38%"  ><input class="form_text"  id="cmProjpart.seqNum" value="${PeopleInfo.SEQ_NUM}"></td>	
			</tr>	 
       </table> 
	       <input type="hidden" id="cmProjpart.id" name="cmProjpart.id" value="${PeopleInfo.ID}">							
 </form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="SaveRecord();">保存<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
