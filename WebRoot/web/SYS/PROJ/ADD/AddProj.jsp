<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
             var currFrameId = parent.OneFrame.$("#currFrameId").val();
 
                      var ajax_option={
						 
						url:"${ctx}/sys/proj/AddProj.action",
						type:"post", 
						dataType: "json",
						error: function(){
	                    	$.messager.alert('错误','增加时出错！');
	                    },
						success:function(data){
						 if(data[0].result=="success"){
							 var objFrame = $("#"+currFrameId,parent.parent.document.body)[0].contentWindow;
						 var node = objFrame.TwoFrame.$('#ProjTree').treegrid('getSelected');
						 if(isNull(node)){                  
                    	 	   objFrame.TwoFrame.TreeGrid_RefreshNode(node.PROJ_ID,"ProjTree");
                    	  }
	                	      var intt=self.setInterval(function(){
	                	 	  var newRow=objFrame.TwoFrame.$('#ProjTree').treegrid('find',data[0].projId);
					          if(isNull(newRow)){
					        	  window.clearInterval(intt);
					        	  objFrame.TwoFrame.$('#ProjTree').treegrid('select',data[0].projId);
					        	   
					        	  objFrame.TwoFrame.OnclickRow(newRow);
					        	  objFrame.TwoFrame.OpenProj(newRow);
				 				 parent.OneFrame.nextstep('1',data[0].projId);
								                
									}
								},500);
		 
                            						    
						 }else{
						    $.messager.alert("错误",data[0].result);
						 }
						}
						 }
						$('#addForm').ajaxSubmit(ajax_option);     
 
         }
           
 </script>

  </head>
  
  <body class="NewWinClass" >
   <form method="post" action="" id="addForm" name="addForm">
		<table style="width: 96%;">
			<tr >
				<th width="12%">项目代码</th>
				<td width="88%" colspan="3" ><input class="form_text"  name="cmProj.projShortName" id="cmProj.projShortName" value=" "></td>
			</tr>
			<tr>
				<th width="12%">项目名称</th>
				<td width="88%" colspan="3" ><input class="form_text"   name="cmProj.projName" id="cmProj.projName" value=" "></td>
			</tr>
			<tr>
				<th width="12%">开工日期</th>
				<td width="38%">
                           <input  class="form_text"    name="cmProj.proceedDate" id="cmProj.proceedDate"
                           	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'cmProj.completionDate\')}'})"></td>
				<th width="12%">完工日期</th>
				<td width="38%">					
				          <input   class="form_text"    name="cmProj.completionDate" id="cmProj.completionDate"  
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'cmProj.proceedDate\')}'})">
                 </td>
			</tr>
		    <tr style="display: none;">
				<th width="12%">工程地点</th>
				<td width="88%" colspan="3" ><input class="form_text"   name="cmProj.siteLocation" id="cmProj.siteLocation" value=" "></td>
			</tr>
			<tr>
				<th width="12%">说明</th>
				<td width="88%" colspan="3" ><textarea    style="height: 80px;" name="cmProj.description" id="cmProj.description" value=" "></textarea> </td>
			</tr>
       </table>
	       <input type="hidden" id="cmProj.parentProjId" name="cmProj.parentProjId" value="${param.ParentProjId}">							
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">下一步<b></b></a> 			
			</div>
  </body>
</html>
