<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加建设单位</title>
			
		<script>
		 
 
        
        $(function(){ 
        	var profile_id =  '${param.PROFILE_ID}';
        	$('#SeeProFileTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                
				url: '${ctx}/sys/user/SeeProFileTable.action?PROFILE_ID='+profile_id,
				columns:[[
					{field:'SUB_NAME',title:'所属组件 ',width:0.2,align:'left' ,sortable:true,
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
					{field:'MNAME',title:'所属模块 ',width:0.2,align:'left' ,sortable:true,
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
					{field:'SELECT',title:'选择',width:0.05,align:'center',
				    	  formatter:function(value,rowData,index){								
						  var checked ="";
						  var disabled ="disabled";
			    	     if(rowData.ENABLED_FLAG!="0"){
			    		  checked ="checked";				    
			    	     }
				    	
				    	    return "<input type='checkbox'    name='moduleselectbox' "+checked+" "+disabled+"   >"; 				    	    				    	    
				    	}
				     },
					{field:'DATACTR_NAME',title:'权限名称 ',width:0.3,align:'left' ,sortable:true,
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
					{field:'REMARK',title:'说明 ',width:0.3,align:'left' ,sortable:true,
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
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 }
 
				
			});
        	 
		  	});
			
		 
		 

        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="SeeProFileTable" class="TreeClass" ></table>
	 
	</body>

</html>