<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
     var node = parent.TwoFrame.$('#SysUserTable').datagrid('getSelected');
    if(!isNull(node)){
    	$.messager.alert("错误","请选中要修改对应的行！");
    	return;
    }
    var user_name=document.getElementById("cmUsers.userName").value;
		if(isStringNull(user_name)){
		  var res =  CheckUserName(user_name);
		  if(res==false){
		  document.getElementById("cmUsers.userName").value="";
		  document.getElementById("cmUsers.userName").focus(); 	  
		     return;
		  }

		}
    var res=  $("#labelForm").SubmitForm("UpdateUser.action");
    if(res[0].result=="success"){
    	var index = parent.TwoFrame.$("#SysUserTable").datagrid('getRowIndex',node);
					parent.TwoFrame.$('#SysUserTable').datagrid('updateRow',{
						index: index,
						row: {
							USER_NAME: $("#cmUsers\\.userName").val(),
							ACTUAL_NAME: $("#cmUsers\\.actualName").val()
						}
					});
					window.location.reload();
     }else{
  	   $.messager.alert("错误",res[0].result)
     }
  }
  
   function CheckUserName(value) {
            var Regx = /^[a-z0-9_-]*$/;
            if (Regx.test(value)) {
            
                return true;
            }
            else {
            $.messager.alert("错误","用户名只能输入小写字母、数字、下划线、中划线！")
                return false;
            }
        }
       function ChangePassword(obj){
    	var currFrameId = parent.frameElement.id; 
    	createSimpleWindow("addWindow","密码修改","${ctx}/web/SYS/USER/changePassword.jsp?currFrameId="+currFrameId+"&type=label", 350, 200);
    }  
       
       
       $(function(){
    	   $("#aaa").hide();
    	   
       })
  </script>
  <body class="LabelWinClass">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table style="width: 96%;">
     	       <tr class="">
					<th width="12%">打√禁用该用户</th>
					<td width="38%" class=""><input type="checkbox" id="cmUsers.disableFlag" name="cmUsers.disableFlag" value="${CmUserInfo.DISABLE_FLAG}" ${CmUserInfo.DISABLE_FLAG eq '1' ? 'checked' :''} onclick="SetValue(this)" ></td>
					<th width="12%"> </td>
					<td width="38%" class=""> </td>
				</tr>
				<tr class="">
					<th width="12%">用户名</th>
					<td width="38%" class=""><input class="label_text"   name="cmUsers.userName" id="cmUsers.userName" value="${CmUserInfo.USER_NAME}"></td>
					<th width="12%">姓名</th>
					<td width="38%"><input class="label_text"   name="cmUsers.actualName" id="cmUsers.actualName" value="${CmUserInfo.ACTUAL_NAME}"></td>
				</tr>
				<tr class="">
					<th width="12%">性别</th>
					<td width="38%" class=""><s:radio name="cmUsers.sex" list="#{'0':'男','1':'女'}"  onchange="showLabelBottomDiv()"  value="#request.CmUserInfo.SEX" theme="simple"></s:radio></td>
<!--                     <th width="12%">分数范围</th> -->
                    <th width="12%"></th>
					<td width="38%" id="aaa" nowrap="true"  class="">
					<table style="table-layout: fixed" border="0" cellSpacing="0" cellPadding="0" style='width:100%'>
						<td width="30%" >				
						    <select class="select"  onchange ="javacript:$('#labelBottomDiv').css('display','')" name="cmUsers.lowFraction" id="cmUsers.lowFraction"  >
								  <%  
								     for(int i=1 ;i<=10;i++){
								    	   
								    	 if( request.getAttribute("CmUserInfo") != null &&  Integer.valueOf(((Map) request.getAttribute("CmUserInfo")).get("LOW_FRACTION").toString()) ==  i*10 ){
									    	 out.print("<option value='"+i*10+"' selected>"+i*10+"</option>");				    		 
								    	 }else{
									    	 out.print("<option value='"+i*10+"'>"+i*10+"</option>");			    		 
								    	 }
				
								     }
								  %>
								</select>
 						<td width="25%" class="">---------</td>
						<td width="45%" class="" align="center">
					        	<select class="select"  onchange ="javacript:$('#labelBottomDiv').css('display','')" name="cmUsers.highFraction" id="cmUsers.highFraction"  >
								  <%  
								     for(int i=1 ;i<=10;i++){
								    	   
								    	 if( request.getAttribute("CmUserInfo") != null &&  Integer.valueOf(((Map) request.getAttribute("CmUserInfo")).get("HIGH_FRACTION").toString()) ==  i*10 ){
									    	 out.print("<option value='"+i*10+"' selected>"+i*10+"</option>");				    		 
								    	 }else{
									    	 out.print("<option value='"+i*10+"'>"+i*10+"</option>");			    		 
								    	 }
				
								     }
								  %>
								</select>
					  </td>
					</table>
					</td>
				</tr>
				<tr class="">		
					<th width="12%">出生年月</th>
					<td width="38%" ><input class="label_text"    name="cmUsers.birthday" id="cmUsers.birthday"   value="${CmUserInfo.BIRTHDAY}"  	onfocus="DataEdit('yyyy-MM-dd','','')" ></td>	 
					<th width="12%">密码</th>
					<td width="38%" class=""> <input type="password" class="label_text"   name="cmUsers.password" id="cmUsers.password" value="${CmUserInfo.PASSWORD}" readonly="readonly"  onclick="ChangePassword(this)" ></td>
				</tr>
       </table>	
       <input type="hidden" id="cmUsers.userId" name="cmUsers.userId" value="${CmUserInfo.USER_ID}">							
       </form>
     </div> 						
  </body>
</html>
