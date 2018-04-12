<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/sys/vnmt/AddVnmt.action");
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
			<tr class="">
				<th width="12%">单位名称</th>
				<td width="38%" class=""  ><input class="label_text"   name="cmVnmt.companyName" id="cmVnmt.companyName" ></td>
				<th width="12%">单位缩写</th>
				<td width="38%" class="" colspan=""><input class="label_text"   name="cmVnmt.companyAbbrev" id="cmVnmt.companyAbbrev" ></td>				
			</tr>
			<tr class="">
				<th width="12%">项目角色</th>
				<td width="38%" class=""><epmis:select id="cmVnmt.roleId" define="SELECT CODE_SHORT_NAME,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='PROJ_ROLE'"></epmis:select></td>
				<th width="12%">办公室电话</th>
				<td width="38%" class=""><input class="label_text"   name="cmVnmt.officePhone" id="cmVnmt.officePhone" ></td>
			</tr>
			<tr class="">
				<th width="12%">资质等级</th>
				<td width="38%" class=""><epmis:select id="cmVnmt.intgGrade" define=" SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='INTG_GRADE'"   ></epmis:select></td>
				<th width="12%">行业</th>
				<td width="38%" class=""><epmis:select id="cmVnmt.trade" define=" SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='TRADE'"  ></epmis:select></td>	
			</tr>
			<tr class="">
				<th width="12%">评价等级</th>
				<td width="38%" class=""><epmis:select id="cmVnmt.estmGrade" define=" SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='ESTM_GRADE'"  ></epmis:select></td>
				<th width="12%">传真</th>
				<td width="38%" class=""><input class="label_text"   name="cmVnmt.fax" id="cmVnmt.fax" ></td>
			</tr>
			<tr class="">
				<th width="12%">地点</th>
				<td width="88%" class="" colspan="3"><input class="label_text"   name="cmVnmt.defaultLocation" id="cmVnmt.defaultLocation" ></td>	
			</tr>
			<tr class="">
				<th width="12%">纳税人识别号</th>
				<td width="38%" class="" ><input class="label_text"   name="dsCmVnmtSupp.taxpayerNumber" id="dsCmVnmtSupp.taxpayerNumber" ></td>
				<th width="12%">纳税人名称</th>
				<td width="38%" class=""><input class="label_text"   name="dsCmVnmtSupp.taxpayerName" id="dsCmVnmtSupp.taxpayerName" ></td>	
			</tr>
			<tr class="">
				<th width="12%">开户银行</th>
				<td width="38%" class="" ><input class="label_text"   name="dsCmVnmtSupp.bankAccount" id="dsCmVnmtSupp.bankAccount" ></td>
				<th width="12%">银行帐号</th>
				<td width="38%" class=""><input class="label_text"   name="dsCmVnmtSupp.bankNumber" id="dsCmVnmtSupp.bankNumber" ></td>	
			</tr>
			<tr class="">
				<th width="12%">工作内容</th>
				<td width="88%" class="" colspan="3"><textarea class="label_textarea"  id="cmVnmt.remark"  name="cmVnmt.remark" ></textarea></td>
			</tr>
       </table>
	  									
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
