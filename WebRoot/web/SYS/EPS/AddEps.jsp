<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
 
 
                      var ajax_option={
						 
						url:"${ctx}/sys/eps/AddEps.action",
						type:"post", 
						dataType: "json",
						error: function(){
	                    	$.messager.alert('错误','增加时出错！');
	                    },
						success:function(data){
						 if(data[0].result=="success"){
						 var node = GetIndexFrame(currFrameId).TwoFrame.$('#EpsTree').treegrid('getSelected');
						 if(isNull(node)){                  
                    	 	   GetIndexFrame(currFrameId).TwoFrame.TreeGrid_RefreshNode(node.id,"EpsTree");                   
                           }else{
                        	   GetIndexFrame(currFrameId).TwoFrame.location.reload();   
                           }
							 
						    parent.close_win('addEpsWindow');
						    
						 }else{
						    $.messager.alert("错误",data[0].result);
						 }
						}
						 }
						$('#addForm').ajaxSubmit(ajax_option);
						     
 
         }
           
 </script>

  </head>
  
  <body  style="background:  #ffffff;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;">
			<tr style="line-height: 40px;">
				<th width="15%">代码</th>
				<td width="85%"><input class="form_text" style="width: 300px;text-align:right; " name="cmProj.projShortName" id="cmProj.projShortName" value=" "></td>
			</tr>
			<tr>
				<th width="15%">名称</th>
				<td width="85%"><input class="form_text" style="width: 300px;text-align:right;" name="cmProj.projName" id="cmProj.projName" value=" "></td>
			</tr>
       </table>
	       <input type="hidden" id="cmProj.parentProjId" name="cmProj.parentProjId" value="${param.parentId}">							
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addEpsWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
