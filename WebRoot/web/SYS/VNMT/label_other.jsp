<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
	  if(parent.TwoFrame.$('#SysVnmtTable').length>0){
	     var node = parent.TwoFrame.$('#SysVnmtTable').datagrid('getSelected');
	    if(!isNull(node)){
	    	$.messager.alert("错误","请选中要修改对应的行！");
	    	return;
	    }
    }
    var res=  $("#labelForm").SubmitForm("UpdateVnmtOther.action");
    if(res[0].result=="success"){             
			window.location.reload();
     }else{
  	   $.messager.alert("错误",res[0].result)
     }
  }
  </script>
  <body class="LabelWinClass" onload="SetDatactr('DS_VNMT.edit')">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table style="width: 96%;">
	     	<tr class="">
				<th width="12%">注册资金:</th>
				<td width="38%" class=""><input class="label_text"   name="cmVnmt.registerFund" id="cmVnmt.registerFund" value="${CmVnmtInfo.REGISTER_FUND}"></td>
				<th width="12%">行业:</th>
				<td width="38%" class=""><epmis:select id="cmVnmt.trade" define=" SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='TRADE'"  value="${CmVnmtInfo.TRADE}"></epmis:select></td>	
			</tr>
			<tr class="">
				<th width="12%">资质等级:</th>
				<td width="38%" class=""><epmis:select id="cmVnmt.intgGrade" define=" SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='INTG_GRADE'"  value="${CmVnmtInfo.INTG_GRADE}"></epmis:select></td>
				<th width="12%">评价等级:</th>
				<td width="38%" class=""><epmis:select id="cmVnmt.estmGrade" define=" SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='ESTM_GRADE'"  value="${CmVnmtInfo.ESTM_GRADE}"></epmis:select></td>	
			</tr>
			<tr class="">
				<th width="12%">办公室电话:</th>
				<td width="38%" class=""><input class="label_text"   name="cmVnmt.officePhone" id="cmVnmt.officePhone" value="${CmVnmtInfo.OFFICE_PHONE}"></td>
				<th width="12%">传真:</th>
				<td width="38%" class=""><input class="label_text"   name="cmVnmt.fax" id="cmVnmt.fax" value="${CmVnmtInfo.FAX}"></td>	
			</tr>
			<tr class="">
				<th width="12%">地址:</th>
				<td width="38%" class=""><input class="label_text"   name="cmVnmt.defaultLocation" id="cmVnmt.defaultLocation" value="${CmVnmtInfo.DEFAULT_LOCATION}"></td>
			</tr> 
       </table>	
       <input type="hidden" id="cmVnmt.vnmtId" name="cmVnmt.vnmtId" value="${CmVnmtInfo.VNMT_ID}">							
       </form>
     </div> 						
  </body>
</html>
