<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>按项目类别分类</title>
			
		<script>
		 
        var  Level = 1;
        $(function(){ 
       	var catg_type_id = '${param.catg_type_id}';
        	$('#AddCatvalTree').treegrid({
         
				url: '${ctx}/sys/catval/AddCatvalTree.action?parentId=0&catg_type_id='+catg_type_id,
				idField:'CATG_ID',
				treeField:'CATG_SHORT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'CATG_SHORT_NAME',title:'码值',width:0.2,align:'left'},
					{field:'CATG_NAME',title:'说明',width:0.3}
					]],
				 
	 			onBeforeExpand:function(node){
					$('#AddCatvalTree').treegrid('options').url = "${ctx}/sys/catval/AddCatvalTree.action?&parentId=" + node.CATG_ID+"&catg_type_id="+catg_type_id;
				} 
			});
		  	});
			
 

        </script>
	</head>
	<body>
		<div class="page_content">
		<table id="AddCatvalTree" class="TreeClass"></table>
		</div>
	</body>

</html>