<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
 
         $(function(){ 
        	$('#SysProfileTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,   
                singleSelect:true,           
				url: '${ctx}/sys/profile/SysProfileTable.action?PROF_TYPE=0',
				columns:[[
					{field:'PROFILE_NAME',title:'名称',width:0.1,align:'left' ,sortable:true,
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
					{field:'USER_NAME',title:'用户 ',width:0.3,align:'left' ,sortable:true,
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
					{field:'PROFILE_ID',title:'PROFILE_ID',width:0.1,align:'left' ,sortable:true,hidden:true},
					{field:'PROF_TYPE',title:'PROF_TYPE',width:0.1,align:'left' ,sortable:true,hidden:true},
					{field:'ISDEFAULT',title:'默认',width:0.2,align:'center' ,sortable:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
               onClickRow:function(rowIndex, node){
				var where = node.PROFILE_ID+";"+node.PROF_TYPE;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
				
			});
        	 
		  	});
			
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="SysProfileTable" class="TreeClass" ></table>
		</div>
   </body>

</html>