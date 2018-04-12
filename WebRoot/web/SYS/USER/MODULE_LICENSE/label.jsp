<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>组件</title>
			
		<script>
		 
 
         $(function(){ 
        	 var userId  = '${param.USER_ID}';
        	 $('#ModuleLicenseUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,  
                singleSelect:true,
				url: '${ctx}/sys/user/ModuleLicenseUserTable.action?USER_ID='+userId,
				columns:[[
					{field:'MODULE_CODE',title:'组件代码',width:0.1,align:'center'},	 
					{field:'MODULE_NAME',title:'组件名称',width:0.1,align:'center'},
					{field:'USRE_FLAG',title:'USRE_FLAG',width:0.4,align:'left',hidden:true},
					{field:'ROLE_FLAG',title:'ROLE_FLAG',width:0.4,align:'left',hidden:true},
					{field:'SELECT',title:'选择',width:0.6,align:'left',
				    	formatter:function(value,rowData,index){		
						
						  var checked ="";
						  var disabled="";
				    	   if(rowData.USRE_FLAG!="0"){
				    		  checked ="checked";
				    	   }
				    	    if(rowData.ROLE_FLAG!="0"){
				    		  checked ="checked";
				    		  disabled ="disabled";
				    	   }
				    	     return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox'  name='selectbox' "+checked+" "+disabled+" onclick=javascript:$('#labelBottomDiv').css('display','')   module_code ='"+rowData.MODULE_CODE+"' >"; 
				    	    				    	    
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
        	    var userId  = '${param.USER_ID}';
			     var node = parent.TwoFrame.$('#SysUserTable').datagrid('getSelected');
			    if(!isNull(node)){
			    	$.messager.alert("错误","请选择用户！");
			    	return;
			    }
			    var datas="";
                $('input[name="selectbox"]:checked').each(function(){    
				      datas = isStringNull(datas)? datas+","+$(this).attr("module_code") : $(this).attr("module_code");
				  }); 
			    var res=  IntoActionByUrl("UpdateModuleLicenseUser.action?USER_ID="+userId+"&MODULE_CODE="+datas);
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
		<table id="ModuleLicenseUserTable" class="TreeClass" ></table>
		</div>	
	</div> 
	</body>
</html>