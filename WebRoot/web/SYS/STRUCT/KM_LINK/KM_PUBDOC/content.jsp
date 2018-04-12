<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
 
 
        $(function(){ 
        	$('#PubDocTree').treegrid({
         
			 	url: '${ctx}/km/km_pubdoc/ShowDocTree.action?parentId=0&docGrade=0',				                                       
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
					$('#PubDocTree').treegrid('options').url = "${ctx}/km/km_pubdoc/ShowDocTree.action?docGrade=0&parentId=" + node.FOLDER_ID;
				    
			 	},
				onClickRow:function(node){
			 		parent.TwoFrame.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PUBDOC/PUBcontent.jsp?FOLDER_ID="+node.FOLDER_ID;
			 		if(isNull(parent.ToogBar))
			 		{
			 		parent.ToogBar.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PUBDOC/centerTop.jsp?FOLDER_ID="+node.FOLDER_ID;
			 		}
				
									
				}
			});
		  	});
      </script>
	</head>
	<body>
		<div class="page_content">
		<table id="PubDocTree" class="TreeClass" ></table>
		</div>
	</body>

</html>