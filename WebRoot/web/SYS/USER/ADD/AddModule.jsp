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
				    	     return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox'  name='selectbox' "+checked+" "+disabled+"  module_code ='"+rowData.MODULE_CODE+"' >"; 
				    	    				    	    
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
			
           function AddRecord(){
        	    var userId  = '${param.USER_ID}';
		 
			    var datas="";
                $('input[name="selectbox"]:checked').each(function(){    
				      datas = isStringNull(datas)? datas+","+$(this).attr("module_code") : $(this).attr("module_code");
				  }); 
                 
			    var res=  IntoActionByUrl("${ctx}/sys/user/UpdateModuleLicenseUser.action?USER_ID="+userId+"&MODULE_CODE="+datas);
			    if(res[0].result=="success"){
			       parent.OneFrame.nextstep('3',userId);
			     }else{
			  	   $.messager.alert("错误",res[0].result)
			     }
			  }
         
        </script>
	</head>
	<body>
	 
		<div class="page_content" style="bottom: 40px;">
		<table id="ModuleLicenseUserTable" class="TreeClass" ></table>
		</div>	
	 
	 <div class="BottomDiv">

					<a href="###" class="btn_01" onclick="AddRecord()">下一步<b></b></a>				
			</div>
	</body>
</html>