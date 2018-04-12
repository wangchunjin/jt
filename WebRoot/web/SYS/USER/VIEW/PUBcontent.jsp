<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
 
         $(function(){ 
        	 var role_id ='${param.ROLE_ID}';
        	$('#SysUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,     
                singleSelect:true,
				url: '${ctx}/sys/user/SysUserTableByRoleId.action?ROLE_ID='+role_id,
				columns:[[
					{field:'USER_NAME',title:'用户名',width:0.1,align:'left' ,sortable:true},
					{field:'ACTUAL_NAME',title:'姓名 ',width:0.1,align:'left' ,sortable:true},
					{field:'DISABLE_FLAG',title:'禁用 ',width:0.1,align:'center' ,sortable:true},
					{field:'PROFILE_NAME',title:'全局安全配置',width:0.4,align:'left' ,sortable:true},
					{field:'USER_ID',title:'USER_ID',width:0.3,align:'left' ,sortable:true ,hidden:true},
					{field:'PROFILE_ID',title:'PROFILE_ID',width:0.3,align:'left' ,sortable:true ,hidden:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })				
				 },
               onClickRow:function(rowIndex, node){
				var where = node.USER_ID+";"+node.PROFILE_ID;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
				
			});
        	 
		  	});
			
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="SysUserTable" class="TreeClass" ></table>
		</div>
   </body>

</html>