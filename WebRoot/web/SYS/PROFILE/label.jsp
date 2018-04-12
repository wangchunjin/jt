<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
	     
	  var type="${CmProfileInfo.PROF_TYPE}";  
     var node = parent.TwoFrame.$('#SysProfileTable').datagrid('getSelected');
    if(!isNull(node)){
    	$.messager.alert("错误","请选中要修改对应的行！");
    	return;
    }
    var codeId="";
    if(type=="1"){
    	codeId=$("#projRole").val(); 
    }
    var profileId="${CmProfileInfo.PROFILE_ID}";
    var res=  $("#labelForm").SubmitForm("UpdateProfile.action");
    if(res[0].result=="success"){
    	  var codeName;
    	 if(isStringNull(codeId)){
    		codeName = $("#projRole").find("option:selected").text(); 
	        var sql = "DELETE FROM CM_PROJ_ROLE WHERE PROFILE_ID ='"+profileId+"' OR CODE_ID='"+codeId+"'";
	    	var ress=GetXmlBySql(sql);
	    	sql = "INSERT INTO CM_PROJ_ROLE(WID,ID,CODE_ID,PROFILE_ID)VALUES('"+Page_GetGUID()+"','"+Page_GetGUID()+"','"+codeId+"','"+profileId+"')";
	        ress=GetXmlBySql(sql); 
	   	    parent.TwoFrame.$("td[field='CODE_ID']").find("div[title='"+codeName+"']").eq(0).html("");
    		parent.TwoFrame.$("td[field='CODE_ID']").find("div[title='"+codeName+"']").eq(0).attr("title",""); 
    	 }
    	var isdefault =$("#cmProfile\\.isdefault").val();
    	if(isStringNull(isdefault)&&isdefault=="1"){
    		parent.TwoFrame.$("div[title='√']").html("");
    		parent.TwoFrame.$("div[title='√']").attr("title","");   		
    		isdefault = "√";
    	}else{
    		isdefault="";
    	} 
    	var index = parent.TwoFrame.$("#SysProfileTable").datagrid('getRowIndex',node);
					parent.TwoFrame.$('#SysProfileTable').datagrid('updateRow',{
						index: index,
						row: {
							PROFILE_NAME: $("#cmProfile\\.profileName").val(),
							ISDEFAULT: isdefault,
							CODE_ID: codeName
						}
					});
				 parent.TwoFrame.$(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })
					window.location.reload();
     }else{
  	   $.messager.alert("错误",res[0].result);
     }
  }
  
  $(function(){
	 
	  var type="${CmProfileInfo.PROF_TYPE}";
	  if(type=="1"){
		  $("#roleTr").show();	
		     var codeId="";
		    var profileId="${CmProfileInfo.PROFILE_ID}";
	    	var sql = "SELECT CODE_ID FROM CM_PROJ_ROLE WHERE PROFILE_ID ='"+profileId+"'";
	    	var  res=GetXmlBySql(sql);
	    	if(res.length>0){
	    		codeId = res[0].CODE_ID;
	    	} 
		  SetSelect("SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='JOB_TITLE'   ORDER BY SEQ_NUM","projRole",codeId,new Array("=--请选择--")); 		   		   
	  }else{
		  $("#roleTr").hide();
	  }
  });
  
 
  </script>
  <body class="LabelWinClass">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table style="width: 96%;">
     	                          
				<tr style="line-height: 30px;">
					<th width="15%">安全配置名称</th>
					<td width="85%"><input class="label_text"   name="cmProfile.profileName" id="cmProfile.profileName" value="${CmProfileInfo.PROFILE_NAME}"></td>
				</tr>
				<tr style="display:none;">
					<th width="15%">安全配置类型</th>
					<td width="85%"><s:radio name="cmProfile.profType" id="cmProfile.profType" list="#{'0':'全局安全配置','1':'项目安全配置'}"  onclick="return false"  value="#request.CmProfileInfo.PROF_TYPE" theme="simple"   ></s:radio></td>
				</tr>
				<tr>
					<th width="15%">默认安全配置</th>
					<td width="85%"><input type="checkbox" id="cmProfile.isdefault" name="cmProfile.isdefault" value="${CmProfileInfo.ISDEFAULT}" ${CmProfileInfo.ISDEFAULT eq '1' ? 'checked' :''} onclick="SetValue(this)" ></td>
				</tr>
				<tr id="roleTr">
					<th width="15%">关联项目职务</th>
					<td width="85%"><epmis:select id="projRole"   attr="style='width:200px;'" ></epmis:select></td>
				</tr>
				<tr>
					<th width="15%">备注</th>
					<td width="85%"><input class="label_text"   name="cmProfile.remark" id="cmProfile.remark" value="${CmProfileInfo.REMARK}"></td>
				</tr>				
       </table>	
       <input type="hidden" id="cmProfile.profileId" name="cmProfile.profileId" value="${CmProfileInfo.PROFILE_ID}">					
       </form>
     </div> 						
  </body>
</html>
