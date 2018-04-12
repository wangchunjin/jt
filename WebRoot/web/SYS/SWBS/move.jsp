<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
		 
        var  Level = 1;
        $(function(){
        	var currFrameId = "${param.currFrameId}";
        	   var node = GetIndexFrame(currFrameId).TwoFrame.$('#SwbsTree').treegrid('getSelected');           
        	   var  orginId = node.SWBS_ID; 
        	$('#SwbsTree').treegrid({
        
				url: '${ctx}/sys/swbs/ShowSwbsMoveTree.action?parentId=0&swbs_type_id=${param.swbs_type_id}&orginId='+orginId,
				idField:'SWBS_ID',
				treeField:'SWBS_SHORT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'SWBS_SHORT_NAME',title:'代码',width:0.1,align:'left'},
					{field:'SWBS_NAME',title:'名称',width:0.3},
					{field:'NODE_TYPE',title:'节点类型',width:120,align:'center',hidden:true},
					{field:'DOC_ID',title:'DOC_ID',width:120,align:'center',hidden:true},
					{field:'PARENT_SWBS_ID',title:'PARENT_SWBS_ID',width:120,align:'center',hidden:true}

					]],
				 
	 			onBeforeExpand:function(node){
					$('#SwbsTree').treegrid('options').url = "${ctx}/sys/swbs/ShowSwbsMoveTree.action?swbs_type_id=${param.swbs_type_id}&parentId=" + node.SWBS_ID+"&orginId="+orginId;
				} 		
				 
				
			});
		  	});
					 
   function  AddRecord(){
		   var orginId = ""; 
		   var parentId = ""
		   var currFrameId = "${param.currFrameId}";
		   var node = GetIndexFrame(currFrameId).TwoFrame.$('#SwbsTree').treegrid('getSelected');
	       if(isNull(node)){                  
		 	   orginId = node.SWBS_ID;                
	       }else{
	    	  $.messager.alert("错误","请选择要移动的行数据");
	    	   return;
	       }
	      var parentNode = $('#SwbsTree').treegrid('getSelected');
		   if(isNull(parentNode)){
			   parentId = parentNode.SWBS_ID;
		   }else{
			   parentId = "0";
		   }
           var res =  IntoActionByUrl("${ctx}/sys/swbs/MoveSwbs.action?orginId="+orginId+"&parentId="+parentId+"&swbs_type_id=${param.swbs_type_id}")
          if(res[0].result=="success"){
        	 GetIndexFrame(currFrameId).TwoFrame.$('#SwbsTree').treegrid('remove', orginId);  
             GetIndexFrame(currFrameId).TwoFrame.$('#SwbsTree').treegrid('reloadFooter');
        	  if(parentId=="0"){
        		  GetIndexFrame(currFrameId).TwoFrame.location.reload();
        	  }else{
        		GetIndexFrame(currFrameId).TwoFrame.TreeGrid_RefreshNode(parentId,"SwbsTree");  
        	  }
        	 parent.close_win('addWindow'); 
          }else{
        	  $.messager.alert("错误",res[0].result);
          }
           
 
         }
       </script>
	</head>
	<body>
		<div class="page_content" style="bottom: 40px;">
		<table id="SwbsTree" class="TreeClass"></table>
		</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
				<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
		<input type="hidden" value="${param.swbs_type_id}" id="swbs_type_id" >
	</body>

</html>