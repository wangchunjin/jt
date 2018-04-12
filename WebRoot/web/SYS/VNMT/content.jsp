<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 var row;
 
         $(function(){ 
     	   $('#SysVnmtTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,     
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
                idField:'VNMT_ID', 
				url: '${ctx}/sys/vnmt/SysVnmtTable.action?',
				frozenColumns:[[
				    {field:'VNMT_ID',checkbox:true}
				]],
				columns:[[
					{field:'COMPANY_ABBREV',title:'公司缩写',width:0.2,align:'left' ,sortable:true,
						sorter:function(param1,param2){
							if(!isStringNull(param1)){
								param1 = "";
							}
							if(!isStringNull(param2)){
								param2 = "";
							}
							if(param1.localeCompare(param2)<0){
								return -1;
							}else{
								return 1;
							}
						}	
					},
					{field:'COMPANY_NAME',title:'公司名称',width:0.3,align:'left' ,sortable:true,
						sorter:function(param1,param2){
							if(!isStringNull(param1)){
								param1 = "";
							}
							if(!isStringNull(param2)){
								param2 = "";
							}
							if(param1.localeCompare(param2)<0){
								return -1;
							}else{
								return 1;
							}
						}	
					},
					{field:'ROLE_ID',title:'项目角色',width:0.1,align:'center' ,sortable:true,
						sorter:function(param1,param2){
							if(!isStringNull(param1)){
								param1 = "";
							}
							if(!isStringNull(param2)){
								param2 = "";
							}
							if(param1.localeCompare(param2)<0){
								return -1;
							}else{
								return 1;
							}
						}	
					},
					{field:'DEFAULT_LINKMAN',title:'主要联系人',width:0.1,align:'center' ,sortable:true,
						sorter:function(param1,param2){
							if(!isStringNull(param1)){
								param1 = "";
							}
							if(!isStringNull(param2)){
								param2 = "";
							}
							if(param1.localeCompare(param2)<0){
								return -1;
							}else{
								return 1;
							}
						}	
					}
					]],
				 onLoadSuccess: function(data){
				 		if("${param.vnmt_Id}"!=null&&"${param.vnmt_Id}"!=""){
				 			 $("#SysVnmtTable").datagrid("selectRecord", "${param.vnmt_Id}"); 
				 			 var where = "${param.vnmt_Id}";
				 			 parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
		                	 $('#SysVnmtTable').datagrid('getSelected');
				 		}
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })
				 },
               onClickRow:function(rowIndex, node){
				var where = node.VNMT_ID;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				row = node;
				}
				
			});
        	 
		  	});
			
         
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="SysVnmtTable" class="TreeClass" ></table>
		</div>
   </body>

</html>