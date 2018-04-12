<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加分类码</title>
			
		<script>
       $(function(){ 
        	$('#SysRoleTree').treegrid({        
				url: '${ctx}/sys/role/SysRoleTree.action?parentId=0',
				idField:'ROLE_ID',
				treeField:'ROLE_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'ROLE_NAME',title:'角色名称',width:0.2,align:'left'},
				    {field:'ACTUAL_NAME',title:'用户',width:0.6,align:'left'},
				    {field:'PARENT_ROLE_ID',title:'PARENT_ROLE_ID',width:0.2,align:'left',hidden:true},
				    {field:'ROLE_TYPE',title:'ROLE_TYPE',width:0.2,align:'left',hidden:true}
					]],
				 
	 			onBeforeExpand:function(node){
					$('#SysRoleTree').treegrid('options').url = "${ctx}/sys/role/SysRoleTree.action?parentId=" + node.ROLE_ID;
				},
				onClickRow:function(node){
				var where = node.ROLE_ID;
					if(node.ROLE_TYPE=="1"){
						parent.OneFrame.$('#NewRecord').css("display","none");
						parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=SYS_ROLE&Where="+where;	
					}else{
						parent.OneFrame.$('#NewRecord').css("display","");
						parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=SYS_OBS&Where="+where;	
					}	
				}
			});
		  	});
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="SysRoleTree" class="TreeClass"></table>
		</div>
 
	</body>

</html>