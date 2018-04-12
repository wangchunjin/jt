<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改密码</title>
		<script type="text/javascript" src="js/imagePreview.js"></script>
    <script type="text/javascript">
    function ChangePassword(obj){
    	var currFrameId = window.frameElement.id; 
    	createSimpleWindow("addWindow","密码修改","${ctx}/web/SYS/USER/changePassword.jsp?currFrameId="+currFrameId, 350, 200);
    }    
    function AddRecord(){
    	  var res=  $("#labelForm").SubmitForm("${ctx}/acmuser/tacmuser/SaveUserInfo.action");
    	  if(res[0].result=="success"){
    		  location.reload();
    	  }
    } 
    
    function init(){
    	var userId = $("#cmUsers\\.userId").val();
    	var sql = "SELECT EMPLOYEEID FROM HR_EMPLOYEE WHERE LINK_USER = '"+userId+"'";
    	var res=GetXmlBySql(sql); 
    	if(res.length>0){
	    	var where =res[0].EMPLOYEEID+";false"; 
			document.all('FourFrame').src = ThreeFrame.getLabelUrl(where);
    	}
    };
    function getObjectURL(file) {
        var url = null;
        if (window.createObjcectURL != undefined) {
            url = window.createOjcectURL(file);
        } else if (window.URL != undefined) {
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) {
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }

    

function getUrl(node){
	   
   var file = null;  
   if(node.files && node.files[0] ){  
       file = node.files[0]; 
   }else if(node.files && node.files.item(0)) {                                  
       file = node.files.item(0);     
   }
    document.getElementById("imghead").src = getObjectURL(file);
}
    </script>
    <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
	</head>
<!--   <body  class="NewWinClass" onload="init()"> --><!-- hxl -->
	<body  class="NewWinClass" >
    <div style="float: left;background:#f4f4f4; position: absolute;top: 5px;left:0;right: 0;height: 20px;border-top: 1px solid #ccc;border-bottom: 1px dashed #ccc;text-align: center;font-weight: bolder;">用户信息修改</div>
       <form method="post" action="" id="labelForm" name="labelForm" style="margin-top: 25px;">
     	<table style="width: 96%;">
     	     	 <tr class="" style="display: none;">
					<th width="12%">打√禁用该用户</th>
					<td width="38%" class=""><input type="checkbox" id="cmUsers.disableFlag" name="cmUsers.disableFlag" value="${CmUserInfo.DISABLE_FLAG}" ${CmUserInfo.DISABLE_FLAG eq '1' ? 'checked' :''} onclick="SetValue(this)" ></td>
<!-- 					<th width="12%">用户名</th> -->
<!-- 					<td width="38%" class=""><input class="form_text" readonly="readonly"  name="cmUsers.userName" id="cmUsers.userName" value="${CmUserInfo.USER_NAME}"></td> -->
				</tr>
				<tr class="">
					
					<th width="12%">用户名：</th>
					<td width="38%" class=""><input class="form_text"   name="cmUsers.userName" id="cmUsers.userName" value="${CmUserInfo.USER_NAME}"></td>
				</tr>
				<tr>
				<th width="12%"><span style="color: red;"></span>头像：</th>
				<td width="38%">
				
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="${ctx}${CmUserInfo.USER_PIC }" width="100" height="80" /></a></div><br>
				
				<input class="form_text" type="hidden" name="cmUsers.USER_PIC" id="USER_PIC"  value="${CmUserInfo.USER_PIC }"></td>
			
			</tr>
			<tr>
			<th width="12%"><span style="color: red;"></th>
				<td width="38%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="getUrl(this)" /></td>
			</tr>
				<tr class="">
					<th width="12%">姓名</th>
					<td width="38%"><input class="form_text"   name="cmUsers.actualName" id="cmUsers.actualName" value="${CmUserInfo.ACTUAL_NAME}"></td>
					<th width="12%">性别</th>
					<td width="38%" class=""><s:radio name="cmUsers.sex" list="#{'0':'男','1':'女'}"   value="#request.CmUserInfo.SEX" theme="simple"></s:radio></td>
				</tr>
				<tr class="" style="display: none;">
                    <th width="12%">分数范围</th>
					<td width="38%" nowrap="true" class="">
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
					<th width="12%">出生年月</th>
					<td width="38%" ><input class="form_text"    name="cmUsers.birthday" id="cmUsers.birthday"   value="${CmUserInfo.BIRTHDAY}"  	onfocus="DataEdit('yyyy-MM-dd','','')" ></td>	 
				</tr>
				<tr class="">
					<th width="12%">密码</th>
					<td width="38%" class=""><input type="password" class="form_text"   name="cmUsers.password" id="cmUsers.password" readonly="readonly" value="${CmUserInfo.PASSWORD}"  onclick="ChangePassword(this)" ></td>
					<th width="12%">办公室电话</th>
					<td width="38%" class=""><input class="form_text"   name="cmUsers.telNoDept" id="cmUsers.telNoDept" value="${CmUserInfo.TEL_NO_DEPT}"></td>
				</tr>
				<tr class="">
					<th width="12%">住宅电话</th>
					<td width="38%" class=""><input class="form_text"   name="cmUsers.telNoHome" id="cmUsers.telNoHome" value="${CmUserInfo.TEL_NO_HOME}"></td>
					<th width="12%">办公传真</th>
					<td width="38%" class=""> <input class="form_text"   name="cmUsers.faxNoDept" id="cmUsers.faxNoDept" value="${CmUserInfo.FAX_NO_DEPT}"></td>
				</tr>
				<tr class="">
					<th width="12%">住宅地址</th>
					<td width="38%" class="" colspan="3"> <input class="form_text"   name="cmUsers.addHome" id="cmUsers.addHome" value="${CmUserInfo.ADD_HOME}"></td>
 				</tr>
				<tr class="">
					<th width="12%">手机</th>
					<td width="38%" class=""> <input class="form_text"   name="cmUsers.mobilNo" id="cmUsers.mobilNo" value="${CmUserInfo.MOBIL_NO}"></td>
 					<th width="12%">住宅邮编</th>
					<td width="38%" class=""> <input class="form_text"   name="cmUsers.postNoHome" id="cmUsers.postNoHome" value="${CmUserInfo.POST_NO_HOME}"></td>
 				</tr>
				<tr class="" style="">
					<th width="12%">电子邮件</th>
					<td width="38%" class=""> <input class="form_text"   name="cmUsers.email" id="cmUsers.email" value="${CmUserInfo.EMAIL}"></td>
 					<th width="12%">微信号</th>
					<td width="38%" class=""> <input class="form_text"   name="cmUsers.oicqNo" id="cmUsers.oicqNo" value="${CmUserInfo.OICQ_NO}"></td>
 				</tr>
				<tr class="" style="display: none;">
					<th width="12%">邮件密码</th>
					<td width="38%" class=""> <input class="form_text"   name="cmUsers.emailPassword" id="cmUsers.emailPassword" value="${CmUserInfo.EMAIL_PASSWORD}"></td>
 					<th width="12%">MSN号码</th>
					<td width="38%" class=""> <input class="form_text"   name="cmUsers.msn" id="cmUsers.msn" value="${CmUserInfo.MSN}"></td>
 				</tr>
				<tr class="" style="display: none;">
					<th width="12%">RTX用户名</th>
					<td width="38%" class=""> <input class="form_text"   name="cmUsers.rtxName" id="cmUsers.rtxName" value="${CmUserInfo.RTX_NAME}"></td>
					<th width="12%"> </td>
					<td width="38%" class=""> </td>
				</tr>
				<tr class="" height="50">
					<td width="12%" colspan="4" class="" align="center">
						<a href="###" class="btn_01" onclick="AddRecord();">保存<b></b></a>
						<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">取消<b></b></a> 
					</td> 
				</tr>
       </table>	
       <input type="hidden" id="cmUsers.userId" name="cmUsers.userId" value="${CmUserInfo.USER_ID}">							
       </form> 	
<!--         <div style="float: left;background:#f4f4f4; position: absolute;top: 260px;left:0;right: 0;height: 20px;border-top: 1px solid #ccc;border-bottom: 1px dashed #ccc;text-align: center;font-weight: bolder;">个人档案查看</div> -->
<!--         <div style="float: left;position: absolute;top: 282px;left:0;right: 0;bottom:0;border: 0px solid red;background: #f4f4f4; "> -->
<!--         	<iframe name="ThreeFrame" id="ThreeFrame" noresize="noresize" src="${ctx}/common/label.jsp?Module=HR_EMPLOYEE_LABEL_READONLY"  frameborder="0" scrolling="no" style="width: 100%;height: 26px;margin-top:2px;"></iframe> -->
<!-- 			<iframe name="FourFrame" id="FourFrame" noresize="noresize" src=""  frameborder="0" scrolling="no" style=" width: 100%;height: 90%;"></iframe> -->
<!--         </div>	   -->
  </body>
  <script type="text/javascript">
  $("#upordown").hide();
  </script>
</html>

