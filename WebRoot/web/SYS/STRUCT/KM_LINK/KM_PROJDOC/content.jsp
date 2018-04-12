<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
 
 
        $(function(){ 
        	$('#ProjDocTree').treegrid({
         
			 	url: '${ctx}/km/km_pubdoc/ShowDocTree.action?parentId=2B44E7C053F64B24877F6E79775F4AFD&docGrade=2',				                                       
				idField:'FOLDER_ID',
				treeField:'TITLE',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'TITLE',title:'名称',width:0.1,align:'left',	    	
				    formatter: function(value,row,index){
							if (row.NUM!="0"){
								return value+"<span style='color: #0000ff;font-weight:bold;'>("+row.NUM+") </span>";
							} else {
								return value;
							}
						}
				    },
					{field:'NUM',title:'名称',width:0.1,align:'left',hidden:true}
				]],
	 			onBeforeExpand:function(node){
					$('#ProjDocTree').treegrid('options').url = "${ctx}/km/km_pubdoc/ShowDocTree.action?docGrade=2&parentId=" + node.FOLDER_ID;
				    
			 	},
				onClickRow:function(node){
			 		if(isNull(parent.ToogBar))
			 		{			 		 
			     		parent.ToogBar.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/centerTop.jsp?FOLDER_ID="+node.FOLDER_ID;
					}
					parent.TwoFrame.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/PUBcontent.jsp?FOLDER_ID="+node.FOLDER_ID;				
				}
			});
		  	});
      </script>
	</head>
	<body>
		<div class="page_content">
		<table id="ProjDocTree" class="TreeClass" ></table>
		</div>
	</body>

</html>