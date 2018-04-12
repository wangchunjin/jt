<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>按项目类别分类</title>
			
		<script>
		 
        var  Level = 1;
        $(function(){ 
       	var catg_type_id = '${param.CATG_TYPE_ID}';
        	$('#SysVnmtTree').treegrid({
         
				url: '${ctx}/sys/vnmt/SysVnmtTree.action?parentId=0&catg_type_id='+catg_type_id,
				idField:'VNMT_ID',
				treeField:'COMPANY_ABBREV',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'COMPANY_ABBREV',title:'公司缩写',width:0.2,align:'left'},
					{field:'COMPANY_NAME',title:'公司名称',width:0.3},
					{field:'ROLE_ID',title:'项目角色',width:0.1,align:'center' },
					{field:'DEFAULT_LINKMAN',title:'主要联系人',width:0.1,align:'center'}
					]],
				 
	 			onBeforeExpand:function(node){
					$('#SysVnmtTree').treegrid('options').url = "${ctx}/sys/vnmt/SysVnmtTree.action?&parentId=" + node.VNMT_ID+"&catg_type_id="+catg_type_id;
				},
				onClickRow:function(node){
				 var vnmt_id = node.VNMT_ID;
				 var where = vnmt_id;	 
				 parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);	
 			 
				} 
			});
		  	});
			
			  
			
		 

        </script>
	</head>
	<body>
		<div class="page_content">
		<table id="SysVnmtTree" class="TreeClass"></table>
		</div>
	</body>

</html>