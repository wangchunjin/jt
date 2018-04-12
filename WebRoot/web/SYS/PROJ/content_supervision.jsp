<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>按项目类别分类</title>
			
		<script>
		 
        var  Level = 1;
        $(function(){ 
 
        	$('#ProjTree').treegrid({
         
				url: '${ctx}/sys/proj/ProjTreeSupervisionCattye.action?parentId=0',
				idField:'PROJ_ID',
				treeField:'PROJ_SHORT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'PROJ_SHORT_NAME',title:'代码',width:0.2,align:'left'},
					{field:'PROJ_NAME',title:'名称',width:0.3},
					{field:'PROJ_STAGE',title:'项目类别',width:0.1,align:'center'},
					{field:'PROJ_CMPT_PCT',title:'形象进度',width:0.15,align:'center'},
					{field:'PROCEED_DATE',title:'开工日期',width:0.1,align:'center'},
					{field:'COMPLETION_DATE',title:'完工日期',width:0.1,align:'center'},
					{field:'CONTRACT_TITLE',title:'项目状态',width:0.1,align:'center' },
                    {field:'WID',title:'WID',width:120,align:'center',hidden:true},
					{field:'TYPE',title:'TYPE',width:0.1,align:'center',hidden:true}
					]],
				 
	 			onBeforeExpand:function(node){
					$('#ProjTree').treegrid('options').url = "${ctx}/sys/proj/ProjTreeSupervisionCattye.action?&parentId=" + node.PROJ_ID;
				},
				onClickRow:function(node){
				parent.OneFrame.location.reload();
 
				 var proj_id = node.PROJ_ID;
			 
 
				 var where = proj_id+";false";
	 
					   parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=SYS_PROJ&Where="+where;		
				 		
				 
				} 
			});
		  	});
			
			  
			
		 

        </script>
	</head>
	<body>
		<div class="page_content">
		<table id="ProjTree" class="TreeClass"></table>
		</div>
	</body>

</html>