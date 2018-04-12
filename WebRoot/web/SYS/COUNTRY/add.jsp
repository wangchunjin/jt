<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
            
            var areaEn = $("#region\\.areaEn").val();
			if(areaEn.replace(/\s/g,'') == ""){
				$.messager.alert("提示","英文名/拼音不能为空!");
				return false;
			}
			
			var areaName = $("#region\\.areaName").val();
			if(areaName.replace(/\s/g,'') == ""){
				$.messager.alert("提示","名称不能为空!");
				return false;
			}
			
			var seqNum = $("#region\\.seqNum").val();
			if(seqNum.replace(/\s/g,'') == ""){
				$.messager.alert("提示","排序号不能为空!");
				return false;
			}
            var parentId = "${param.parentId }";
            var res = $('#addForm').SubmitForm("${ctx}/sys/sys_region/save.action");
    		if(res[0].result=="success"){				 
                GetIndexFrame(currFrameId).TwoFrame.location.reload();
				parent.close_win('addAreaWindow');
    		}else{
    			$.messager.alert("错误",res[0].result);
    		}
         }
           
 </script>

  </head>
  
  <body  class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
   		<input type="hidden" name="region.parentId" value="${param.parentId }">
   		<input type="hidden" name="region.areaType" value="${param.areaType }">
   		
		<table style="margin-top: 15px;width: 96%;" >
			<tr class="">
				<th width="12%">英文名/拼音</th>
				<td width="38%" class=""  ><input class="label_text"   name="region.areaEn" id="region.areaEn" ></td>
				<th width="12%">名称</th>
				<td width="38%" class="" colspan=""><input class="label_text" name="region.areaName" id="region.areaName" ></td>				
			</tr>
			<tr class="">
				<th width="12%">排序</th>
				<td width="38%" class=""  ><input class="label_text" name="region.seqNum" id="region.seqNum" onblur="value=value.replace(/[^\- \d]/g,'')"></td>
			</tr>
       </table>
	  									
	</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addAreaWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
