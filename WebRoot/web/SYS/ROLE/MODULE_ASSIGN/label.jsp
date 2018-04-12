<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>模块树</title>
			
		<script>
  
        $(function(){ 
        	var roleId  = '${param.ROLE_ID}';
        	$('#ModuleAssignRoleTree').treegrid({
         
			 	url: '${ctx}/sys/role/ModuleAssignRoleTree.action?parentId=0&ROLE_ID='+roleId,				                                       
				idField:'MODULE_CODE',
				treeField:'MODULE_CODE',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'MODULE_CODE',title:'组件代码',width:0.1,align:'left'},
					{field:'MODULE_NAME',title:'组件名称',width:0.1,align:'left'},
					{field:'PARENT',title:'PARENT',width:0.4,align:'left',hidden:true},
					{field:'ROLE_FLAG',title:'ROLE_FLAG',width:0.4,align:'left',hidden:true},
					{field:'NODE_TYPE',title:'NODE_TYPE',width:0.4,align:'left',hidden:true},
					{field:'SELECT',title:'选择',width:0.6,align:'left',
				    	  formatter:function(value,rowData,index){								
						  var checked ="";
				    	    if(rowData.ROLE_FLAG!="0"){
				    		  checked ="checked";
				    	   }
				    	    if(rowData.NODE_TYPE=="module"){
				    	           return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' id='"+rowData.MODULE_CODE+"' parent='"+rowData.PARENT+"' name='moduleselectbox' "+checked+"  onclick='SetAll(this)'   module_code ='"+rowData.MODULE_CODE+"' node_type='"+rowData.NODE_TYPE+"' >"; 				    	    				    	    
				    	    }else{
				            	   return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' parent='"+rowData.PARENT+"' name='selectbox' "+checked+"  onclick='SetBox(this)'   module_code ='"+rowData.MODULE_CODE+"' node_type='"+rowData.NODE_TYPE+"' >"; 				    	    				    	    
				    	    }
				    	}
				     }
					
				]]
			});
		  	});
			
			function SetAll(obj){
				$('#labelBottomDiv').css('display','');
				if(obj.checked){
					$("input[parent='"+obj.id+"']").not(':disabled').attr("checked",true);
				}else{
					$("input[parent='"+obj.id+"']").not(':disabled').attr("checked",false);
				}
			}  
			
			function SetBox(obj){
				$('#labelBottomDiv').css('display','');
				if(!obj.checked){
				 	$("input[id='"+$(obj).attr("parent")+"']").attr("checked",false);
				}
			}
           function SaveRecord(){
        	    var roleId  = '${param.ROLE_ID}';
			     var node = parent.TwoFrame.$('#SysRoleTree').datagrid('getSelected');
			    if(!isNull(node)){
			    	$.messager.alert("错误","请选择角色！");
			    	return;
			    }
			    var datas="";
                $("input[name='selectbox']:checked").not(':disabled').each(function(){    
				      datas = isStringNull(datas)? datas+","+$(this).attr("module_code") : $(this).attr("module_code");
				  }); 
			    var res=  IntoActionByUrl("UpdateModuleAssignRole.action?ROLE_ID="+roleId+"&MODULE_CODE="+datas);
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
		       <table id="ModuleAssignRoleTree" class="TreeClass" ></table>
		   </div>
		</div>
	</body>

</html>