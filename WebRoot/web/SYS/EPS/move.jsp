<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
 
        var  Level = 1;
        $(function(){ 
        	$('#EpsTree').treegrid({
         
			 	url: '${ctx}/sys/eps/ShowEpsTree.action?parentId=0&level=2',				                                       
				idField:'id',
				treeField:'proj_short_name',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'proj_short_name',title:'代码',width:0.1,align:'left'},
					{field:'proj_name',title:'名称',width:0.3}
					
				]],
	 			onBeforeExpand:function(node){
					$('#EpsTree').treegrid('options').url = "${ctx}/sys/eps/ShowEpsTree.action?parentId=" + node.id;
				} 
			});
		  	});
			
			  
			
		 
   function  AddRecord(){
		   var orginId = ""; 
		   var parentId = ""
		   var currFrameId = "${param.currFrameId}";
		   var node = GetIndexFrame(currFrameId).TwoFrame.$('#EpsTree').treegrid('getSelected');
	       if(isNull(node)){                  
		 	   orginId = node.id;                
	       }else{
	    	  $.messager.alert("错误","请选择要移动的行数据");
	    	  return;
	       }
	      var parentNode = $('#EpsTree').treegrid('getSelected');
		   if(isNull(parentNode)){
			   parentId = parentNode.id;
		   }else{
			   parentId= "0";
		   }
           var res =  IntoActionByUrl("${ctx}/sys/eps/MoveEps.action?orginId="+orginId+"&parentId="+parentId)
          if(res[0].result=="success"){
        	 GetIndexFrame(currFrameId).TwoFrame.$('#EpsTree').treegrid('remove', orginId);  
             GetIndexFrame(currFrameId).TwoFrame.$('#EpsTree').treegrid('reloadFooter');
        	  if(parentId=="0"){
        		  GetIndexFrame(currFrameId).TwoFrame.location.reload();
        	  }else{
        		GetIndexFrame(currFrameId).TwoFrame.TreeGrid_RefreshNode(parentId,"EpsTree");  
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
		<table id="EpsTree" class="TreeClass" ></table>
		</div>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
	</body>

</html>