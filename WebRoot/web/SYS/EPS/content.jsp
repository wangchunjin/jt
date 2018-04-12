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
				},
				onClickRow:function(node){
				var where = node.id;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
			});
		  	});
			
			  
			
		 

        </script>
	</head>
	<body>
		<div class="page_content">
		<table id="EpsTree" class="TreeClass" ></table>
		</div>
	</body>

</html>