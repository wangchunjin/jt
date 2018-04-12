<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>组件</title>
			
		<script>
		 
 
         $(function(){ 
        	 var roleId  = '${param.ROLE_ID}';
        	 $('#ModuleLicenseRoleTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,  
                singleSelect:true,
				url: '${ctx}/sys/role/ModuleLicenseRoleTable.action?ROLE_ID='+roleId,
				columns:[[
					{field:'MODULE_CODE',title:'组件代码',width:0.1,align:'center'},	 
					{field:'MODULE_NAME',title:'组件名称',width:0.1,align:'center',
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
					{field:'ROLE_FLAG',title:'ROLE_FLAG',width:0.4,align:'left',hidden:true},
					{field:'SELECT',title:'选择',width:0.6,align:'left',
				    	formatter:function(value,rowData,index){		
						
						  var checked ="";
				    	    if(rowData.ROLE_FLAG!="0"){
				    		  checked ="checked";
				    	   }
				    	     return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox'  name='selectbox' "+checked+"   onclick=javascript:$('#labelBottomDiv').css('display','')   module_code ='"+rowData.MODULE_CODE+"' >"; 
				    	    				    	    
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
			
           function SaveRecord(){
        	    var roleId  = '${param.ROLE_ID}';
			     var node = parent.TwoFrame.$('#SysRoleTree').datagrid('getSelected');
			    if(!isNull(node)){
			    	$.messager.alert("错误","请选择角色！");
			    	return;
			    }
			    var datas="";
                $('input[name="selectbox"]:checked').each(function(){    
				      datas = isStringNull(datas)? datas+","+$(this).attr("module_code") : $(this).attr("module_code");
				  }); 
			    var res=  IntoActionByUrl("UpdateModuleLicenseRole.action?ROLE_ID="+roleId+"&MODULE_CODE="+datas);
			    if(res[0].result=="success"){
			        window.location.reload();
			     }else{
			  	   $.messager.alert("错误",res[0].result)
			     }
			  }
         
        </script>
	</head>
	<body>
	 <div class="label_div">
		<div class="page_content">
		<table id="ModuleLicenseRoleTable" class="TreeClass" ></table>
		</div>	
	</div> 
	</body>
</html>