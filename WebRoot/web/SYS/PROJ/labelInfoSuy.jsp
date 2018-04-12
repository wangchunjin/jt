<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
	    var prames =$(".label_textarea").attr("id")+"="+$(".label_textarea").val()
	    var proj_id= $("#PROJ_ID").val();
        $(".label_text").each(function(i){
         	 prames =  prames+"@@@"+this.id+"="+this.value ;
        });  	  

         var result = IntoActionByUrl("${ctx}/sys/proj/updateProjSuy.action?proj_id="+proj_id+"&prames="+encodeURIComponent (prames));
        if(result[0].result=="success"){
        	window.location.reload();
        }else{
        	$.messager.alter("错误","保存错误!");
        }
			   	
  }
   
  </script>
  <body class="LabelWinClass" onload="SetDatactrs('SYS_PROJ.QYXM.edit','DS_GCGK.edit')" >
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table height="" style="width: 96%;">
     	      <c:forEach items="${ColumnInfo}" var="ColumnInfo" varStatus="status">			      
			     <c:if test="${status.index%2==0}">
			         <tr>
			     </c:if> 			             
		            <th width="12%">${ColumnInfo.COLUMN_NAME}</th>
					<td width="38%"><input class="label_text"   name="${ColumnInfo.COLUMN_CODE}" id="${ColumnInfo.COLUMN_CODE}" value="${ColumnInfo.COLUMN_VALUE}"></td>
				  <c:if test="${status.index%2==1}">
			         </tr>
			     </c:if> 
			   </c:forEach>
     	        <tr>
					<th width="12%">工程结构</th>
					<td width="38%"><input class="label_text"   name="PROJ_FORM" id="PROJ_FORM" value="${cmProjSurvey.projForm}"></td>
					<th width="12%">工程质量目标</th>
					<td width="38%" ><input class="label_text"   name="PROJ_QUALITY_DEST" id="PROJ_QUALITY_DEST" value="${cmProjSurvey.projQualityDest}"></td>
				</tr>
				<tr>
					<th width="12%">总投资（万元）</th>
					<td width="38%"><input class="label_text"   name="GROSS_INVEST" id="GROSS_INVEST" value="${cmProjSurvey.grossInvest}"></td>
					<th width="12%">规模</th>
					<td width="38%" rowspan="3" ><textarea class="label_textarea"  name="SCALE" id="SCALE"  >${cmProjSurvey.scale}</textarea></td>
				</tr>
				<tr>
					<th width="12%">监理费用（万元）</th>
					<td width="38%"><input class="label_text"   name="PART_PROJ_INVEST" id="PART_PROJ_INVEST" value="${cmProjSurvey.partProjInvest}"></td>
				</tr>
				<tr>
					<th width="12%">安全文明工地目标</th>
					<td width="38%" ><input class="label_text"   name="CULTURE_SITE" id="CULTURE_SITE" value="${cmProjSurvey.cultureSite}"></td>
				</tr>
				<input type="hidden" class="label_text" id="PROJ_ID" name="PROJ_ID" value="${cmProjSurvey.projId}">	
       </table>				
       </form>
     </div> 						
  </body>
</html>
