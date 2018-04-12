<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
 <script type="text/javascript">
     function  AddRecord(){
        var ROLE_ID = '${param.ROLE_ID}';
        var user_name=document.getElementById("cmUsers.userName").value;
		if(isStringNull(user_name)){
		  var res =  CheckUserName(user_name);
		  if(res==false){
		  document.getElementById("cmUsers.userName").value="";
		  document.getElementById("cmUsers.userName").focus(); 	  
		     return;
		  }

		}
           var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/acmuser/tacmuser/AddUser.action?ROLE_ID="+ROLE_ID);
	    		if(res[0].result=="success"){
	    			var objFrame = $("#"+currFrameId,parent.parent.document.body)[0].contentWindow;
                    objFrame.TwoFrame.location.reload();
                    parent.parent.close_win("addWindow");
                   // objFrame.TwoFrame.$("#SysUserTable").datagrid('selectRecord',res[0].userid);
 				   // parent.OneFrame.nextstep('1',res[0].userid);
	    		}else{
	    			$.messager.alert("错误",res[0].result);
	    		}
         }
       function CheckUserName(value) {
    	    var Regx = /^[a-z0-9_-]*$/;
            if (Regx.test(value)) {
            
                return true;
            }
            else {
            $.messager.alert("错误","用户名只能输入小写字母或数字用户名只能输入小写字母、数字、下划线、中划线！");
                return false;
            }
        }
 </script>
<style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
  </head>
  
  <body  class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			<tr class="">
			<input type="hidden" value="${sessionScope.UserInfo.userId}" name="cmUsers.subsidiary">
					<th width="12%">用户名</th>
					<td width="38%" class=""><input class="label_text"   name="cmUsers.userName" id="cmUsers.userName" value=""></td>
					<th width="12%">姓名</th>
					<td width="38%"><input class="label_text"   name="cmUsers.actualName" id="cmUsers.actualName" value=""></td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;"></span>头像：</th>
				<td width="30%">
				
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="../../../images/ic_pic_loading.png" width="50" height="40" /></a></div><br>
				
				</td>
			
			</tr>
			<tr>
			<th width="20%"><span style="color: red;"></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="previewImage(this)" /></td>
			</tr>
				<tr class="">
					<th width="12%">性别</th>
					<td width="38%" class=""><s:radio name="cmUsers.sex" list="#{'0':'男','1':'女'}"   value="0" theme="simple"></s:radio></td>
					<th width="12%">出生年月</th>
					<td width="38%" ><input class="label_text"    name="cmUsers.birthday" id="cmUsers.birthday"   value=""  	onfocus="DataEdit('yyyy-MM-dd','','')" ></td>	 
				</tr>
				<tr class="" style="display: none;">						
                    <th width="12%">分数范围</th>
					<td width="38%" nowrap="true" class="">
					<table style="table-layout: fixed" border="0" cellSpacing="0" cellPadding="0" style='width:100%'>
						<td width="30%" >				
						    <select class="select"  onchange ="javacript:$('#labelBottomDiv').css('display','')" name="cmUsers.lowFraction" id="cmUsers.lowFraction"  >
								  <%  
								     for(int i=1 ;i<=10;i++){								   
									    	 out.print("<option value='"+i*10+"'>"+i*10+"</option>");			    		 								    					
								     }
								  %>
								</select>
 						<td width="25%" class="">---------</td>
						<td width="45%" class="" align="center">
					        	<select class="select"  onchange ="javacript:$('#labelBottomDiv').css('display','')" name="cmUsers.highFraction" id="cmUsers.highFraction"  >
								  <%  
								     for(int i=1 ;i<=10;i++){
									    	 out.print("<option value='"+i*10+"'>"+i*10+"</option>");			    		 
								     }
								  %>
								</select>
					  </td>
					</table>
					</td>	
					<th width="12%">手机</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.mobilNo" id="cmUsers.mobilNo" value=""></td>
                 </tr>
                 <tr class="">
					<th width="12%">办公室电话</th>
					<td width="38%" class=""><input class="label_text"   name="cmUsers.telNoDept" id="cmUsers.telNoDept" value=""></td>
					<th width="12%">住宅电话</th>
					<td width="38%" class=""><input class="label_text"   name="cmUsers.telNoHome" id="cmUsers.telNoHome" value=""></td>
				</tr>
				<tr class="">
					<th width="12%">办公传真</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.faxNoDept" id="cmUsers.faxNoDept" value=""></td>
					<th width="12%">住宅地址</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.addHome" id="cmUsers.addHome" value=""></td>
				</tr>
				<tr class="">
					<th width="12%">电子邮件</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.email" id="cmUsers.email" value=""></td>
					<th width="12%">微信号</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.oicqNo" id="cmUsers.oicqNo" value=""></td>
				</tr>
				<tr class="" style="display: none;">
					<th width="12%">住宅邮编</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.postNoHome" id="cmUsers.postNoHome" value=""></td>				
					<th width="12%">MSN号码</th>
					<td width="38%" class=""> <input class="label_text"   name="cmUsers.msn" id="cmUsers.msn" value=""></td>
				</tr>
       </table>
       </form>
			<div class="BottomDiv">

					<a href="###" class="btn_01" onclick="AddRecord()">确定<b></b></a>				
			</div>
  </body>
</html>
