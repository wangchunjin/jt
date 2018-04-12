<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
              var res=  $("#labelForm").SubmitForm("${ctx}/sys/struct/updateLanguage.action");
		    if(res[0].result=="success"){
                var node = parent.TwoFrame.$('#LanguageTree').treegrid('getSelected');
            var ispub = 'N';
            if($("#structLanguage\\.isPublic").attr("checked")=="checked"){
            	ispub='Y';
            }
				parent.TwoFrame.$('#LanguageTree').treegrid('update',{
					id: node.ID,
					row: {
						NAME: $("#structLanguage\\.name").val(),
						SEQ_NUM: $("#structLanguage\\.seqNum").val(),
						IS_PUBLIC: ispub
					}
				});
		    	window.location.reload();
		    }else{
		    	$.messager.alert("错误","保存出错！")
		    }
  }
  function init(){
	  var type = "${languageInfo.TYPE}";
	  if(type=="WBS"){
		  $("#tit1").css({"display":"none"});
		  $("#tit2").css({"display":"none"});
	  }
  }
  </script>
  <body class="LabelWinClass"  onload="SetReadOnly(${param.ISEDIT});init()" >
    <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table width="96%">
            <tr>
				<th width="12%">名称</th>
				<td width="38%" colspan="3"><input class="label_text"  name="structLanguage.name" id="structLanguage.name" value="${languageInfo.NAME} "></td>
 			</tr> 			
			<tr >
				<th width="12%">节点类型</th>
				<td width="38%"><s:radio name="type" list="#{'WBS':'分类','TASK':'语言'}" disabled="true"  value="#request.languageInfo.TYPE" theme="simple"></s:radio></td> 		      			
				<th width="12%"  id="tit1">是否共享</th>
				<td width="38%"  id="tit2"><input type="checkbox" id="structLanguage.isPublic" name="structLanguage.isPublic" value="${languageInfo.IS_PUBLIC}"   ${languageInfo.IS_PUBLIC eq 'Y' ? 'checked' :''} onclick="SetValue(this)" ></td>
			</tr>
			<tr >
				<th width="12%">排序编号</th>
				<td width="38%"><input class="label_text"  id="structLanguage.seqNum" name="structLanguage.seqNum"  value="${languageInfo.SEQ_NUM}"  ></td>				 			
				<th width="12%">创建来源</th>
				<td width="38%" ><input class="label_text"   style="background-color:#eeeeee "  disabled="disabled"    name="proj" id="proj"  value ="${languageInfo.PROJ_NAME}"></td></td>
			</tr>
			<tr >
				<th width="12%">创建人</th>
				<td width="38%" ><input class="label_text"  style="background-color:#eeeeee " disabled="disabled"    name="createdName" id="createdName"  value ="${languageInfo.CREATED_NAME}"></td>
				<th width="12%">创建时间</th>
				<td width="38%" ><input class="label_text"   style="background-color:#eeeeee "  disabled="disabled"   name="createdTime" id="createdTime"  value ="${languageInfo.CREATED_TIME}"></td>
			</tr>
			 <input type="hidden" id="structLanguage.id" name="structLanguage.id" value="${languageInfo.ID}">	
       </table>	
       <input type="hidden" id="kmFdmd.folderId" name="kmFdmd.folderId" value="${FdmdInfo.FOLDER_ID} ">							
       </form>
     </div> 						
  </body>
</html>
