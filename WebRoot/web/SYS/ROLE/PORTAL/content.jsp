<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
 		 var roleId = "${param.ROLE_ID}";	
         $(function(){ 
        	$('#SysPortalTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                queryParams:{ROLE_ID:roleId},
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
                idField:'P_ID',  
				url: '${ctx}/cc/role/portaletRoleTable.action',
				columns:[[
					{field:'P_CODE',title:'代码',width:0.2,align:'center' ,sortable:true,
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
					{field:'P_TITLE',title:'名称',width:0.2,align:'center' ,sortable:true,
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
					{field:'SELECT',title:'选择',width:0.1,align:'center',
				    	formatter:function(value,rowData,index){
				    		
							var checked ="";
							var disabled="";
							if(rowData.ROLE_FLAG!="0"){
								checked ="checked";
							}
	
				    	    return "<input type='checkbox' name='selectbox' onclick=updateUserPortalet(this) "+checked+" value='"+rowData.P_ID+"' >"; 
				    	}
					},
					{field:'kz',title:'',width:0.5}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
               onClickRow:function(rowIndex, node){
				//var where = node.USER_ID+";"+node.PROFILE_ID;
				//parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
				
			});
        	 
		  	});
			
         	function updateUserPortalet(obj){
         		var roleId = "${param.ROLE_ID}";
         		var checkFlag = "false";
         		
         		if(!isStringNull(roleId)){
         			$.messager.alert("错误","请选择角色");
         			return fasle;
         		}
         		
         		if(obj.checked){
         			checkFlag = "true";
         		}
         		
         		
         		var res=  IntoActionByUrl("${ctx}/cc/role/updateRolePortalet.action?ROLE_ID="+roleId+"&pId="+obj.value+"&checkFlag="+checkFlag);
			    if(res[0].result=="success"){
			        //window.location.reload();
			     }else{
			  	   $.messager.alert("错误",res[0].result)
			     }
         	}
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="SysPortalTable" class="TreeClass" ></table>
		</div>
   </body>

</html>