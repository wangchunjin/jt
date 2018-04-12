<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
		 var moduleCode = parent.frameElement.id.replace("iframe_","");
 
        var  Level = 1;
         $(function(){ 
         
        	$('#regionTree').treegrid({
		        url:"${ctx}/sys/sys_region/regionTree.action?parentId=0",
				idField:'WID',
				treeField:'AREA_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
// 				    {field:'AREA_EN',title:'英文名',width:0.2,align:'left'},
					{field:'AREA_NAME',title:'名称',width:0.3},
					
				    {field:'AREA_TYPE',title:'类型',width:0.5,align:'center',
				    	formatter: function(value,row,index){
							if(row.AREA_TYPE == "0"){
							   return "国家";
							}else if(row.AREA_TYPE == "1"){
							   return "省份";
							}else if(row.AREA_TYPE == "2"){
							   return "城市";
							}else if(row.AREA_TYPE == "3"){
							   return "区县";
							}else if(row.AREA_TYPE == "4"){
							   return "街道";
							}
						}
				    },
				    {field:'aroundname',title:'所属周边',width:0.5,
// 				    	formatter: function(value,row,index){
// 							if(row.around == "0"){
// 							   return "";
// 							}
// 						}	
				    }
				]],
				 
	 			onBeforeExpand:function(node){
			 		$('#regionTree').treegrid('options').url = "${ctx}/sys/sys_region/regionTree.action?parentId=" + node.WID;
				},
				onLoadSuccess:function(row, data){
			       
				},
				onClickRow:function(node){
// 					var where = node.WID;
// 					parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				},
				onDblClickRow:function(row){
				}
				
			});
        	
		  });
			
			  
		 
		 

        </script>
	</head>
	<body>
		<div class="page_content">
		<table id="regionTree" class="TreeClass"></table>
		</div>
	</body>

</html>