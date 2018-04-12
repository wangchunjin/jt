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
    var res=  $("#labelForm").SubmitForm("UpdateVnmt.action");
    if(res[0].result=="success"){
    	 if(parent.TwoFrame.$('#SysVnmtTable').length>0){
    		 var default_linkman="";
    		 if(isStringNull($("#cmVnmt\\.defaultLinkman").val())){
    			 default_linkman=$("#cmVnmt\\.defaultLinkman").find("option:selected").text();
    		 }
     		 var roleId="";
    		 if(isStringNull($("#cmVnmt\\.roleId").val())){
    			 roleId=$("#cmVnmt\\.roleId").find("option:selected").text();
    		 }
    	           var index = parent.TwoFrame.$("#SysVnmtTable").datagrid('getRowIndex',parent.TwoFrame.row);
					parent.TwoFrame.$('#SysVnmtTable').datagrid('updateRow',{
						index: index,
						row: {
							COMPANY_ABBREV: $("#cmVnmt\\.companyAbbrev").val(),
							COMPANY_NAME: $("#cmVnmt\\.companyName").val(),
							ROLE_ID:roleId,
					        DEFAULT_LINKMAN:default_linkman
						}
		             });
			}             
			window.location.reload();
     }else{
  	   $.messager.alert("错误",res[0].result)
     }
  }
  </script>
  <body class="LabelWinClass">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table style="width: 96%;">
     	         <tr class="">
					<th width="12%">单位名称:</th>
					<td width="38%" class=""><input class="label_text"   name="cmVnmt.companyName" id="cmVnmt.companyName" value="${CmVnmtInfo.COMPANY_NAME}"> </td>
					<th width="12%">项目角色:</th>
					<td width="38%" class=""><epmis:select id="cmVnmt.roleId" define="SELECT CODE_SHORT_NAME,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='PROJ_ROLE'"  value="${CmVnmtInfo.ROLE_ID}"></epmis:select></td>
				</tr>
				<tr class="">
					<th width="12%">单位缩写:</th>
					<td width="38%" class=""><input class="label_text"   name="cmVnmt.companyAbbrev" id="cmVnmt.companyAbbrev" value="${CmVnmtInfo.COMPANY_ABBREV}"></td>
					<th width="12%">主要联系人:</th>
					<td width="38%" class=""><epmis:select id="cmVnmt.defaultLinkman" define="SELECT VNDT_ID,NAME FROM CM_VNDT WHERE VNMT_ID='${CmVnmtInfo.VNMT_ID}'"  value="${CmVnmtInfo.DEFAULT_LINKMAN}"></epmis:select></td>
				</tr>
				<tr class="">
					<th width="12%">关联甲方:</th>
					<td width="50%"><epmis:select id="cmVnmt.otherVnmtId" define="SELECT VNMT_ID,COMPANY_NAME FROM CM_VNMT WHERE VNMT_ID IN (SELECT VNMT_ID FROM CM_PROJ_JOIN WHERE PROJ_ID = '${UserInfo.getProjId() }')  ORDER BY COMPANY_NAME"  value="${CmVnmtInfo.OTHER_VNMT_ID}"></epmis:select></td>
					<th width="12%">默认的联系单位:</th>
					<td width="38%" class=""><input type="checkbox" id="cmVnmt.isdefault" name="cmVnmt.isdefault" value="${CmVnmtInfo.ISDEFAULT}" ${CmVnmtInfo.ISDEFAULT eq '1' ? 'checked' :''} onclick="SetValue(this)" ></td>
				</tr>
				<tr class="">
					<th width="12%">纳税人识别号</th>
					<td width="38%" class="" ><input class="label_text"   name="dsCmVnmtSupp.taxpayerNumber" id="dsCmVnmtSupp.taxpayerNumber" value="${CmVnmtInfo.TAXPAYER_NUMBER}"></td>
					<th width="12%">纳税人名称</th>
					<td width="38%" class=""><input class="label_text"   name="dsCmVnmtSupp.taxpayerName" id="dsCmVnmtSupp.taxpayerName" value="${CmVnmtInfo.TAXPAYER_NAME}" ></td>	
				</tr>
				<tr class="">
					<th width="12%">开户银行</th>
					<td width="38%" class="" ><input class="label_text"   name="dsCmVnmtSupp.bankAccount" id="dsCmVnmtSupp.bankAccount" value="${CmVnmtInfo.BANK_ACCOUNT}"></td>
					<th width="12%">银行帐号</th>
					<td width="38%" class=""><input class="label_text"   name="dsCmVnmtSupp.bankNumber" id="dsCmVnmtSupp.bankNumber" value="${CmVnmtInfo.BANK_NUMBER}"></td>	
				</tr>
				<tr class="">
					<th width="12%">备注:</th>
					<td width="38%" class="" height="50px" colspan="3"><textarea class="label_textarea"  id="cmVnmt.remark"  name="cmVnmt.remark" >${CmVnmtInfo.REMARK}</textarea></td>
				</tr>
		     	      
       </table>	
       <input type="hidden" id="cmVnmt.vnmtId" name="cmVnmt.vnmtId" value="${CmVnmtInfo.VNMT_ID}">							
       </form>
     </div> 						
  </body>
</html>
