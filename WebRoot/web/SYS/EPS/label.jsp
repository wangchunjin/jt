<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
     var node = parent.TwoFrame.$('#EpsTree').treegrid('getSelected');
   
		parent.TwoFrame.$('#EpsTree').treegrid('update',{
			id: node.id,
			row: {
				proj_short_name: $("#cmProj\\.projShortName").val(),
				proj_name: $("#cmProj\\.projName").val()
			}
		});

                var ff = document.labelForm;
               
                ff.action="UpdateEps.action";
			   	ff.submit();
  }
  function init(show){
	  if(show=="false"){
	    $("#labelForm").find("input").attr("readOnly",true);
	  }
  }
  </script>
  <body  onload="init('${param.SHOW}')">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table style="width: 96%;">
     	                          
				<tr style="line-height: 30px;">
					<th width="15%">代码</th>
					<td width="85%"><input class="label_text"   name="cmProj.projShortName" id="cmProj.projShortName" value="${cmProj.projShortName}"></td>
				</tr>
				<tr>
					<th width="15%">名称</th>
					<td width="85%"><input class="label_text"   name="cmProj.projName" id="cmProj.projName" value="${cmProj.projName}"></td>
				</tr>
				<tr>
					<th width="15%">排序</th>
					<td width="85%"><input class="label_text"   name="cmProj.seqNum" id="cmProj.seqNum" value="${cmProj.seqNum}"></td>
				</tr>
       </table>	
       <input type="hidden" id="cmProj.projId" name="cmProj.projId" value="${cmProj.projId}">							
       </form>
     </div> 						
  </body>
</html>
