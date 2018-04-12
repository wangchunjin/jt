<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>模块树</title>
			
		<script>
  
        $(function(){ 
        	var userId  = '${param.USER_ID}';
        	$('#ModuleAssignUserTree').treegrid({
         
			 	url: '${ctx}/sys/user/ModuleAssignUserTree.action?parentId=0&USER_ID='+userId,				                                       
				idField:'MODULE_CODE',
				treeField:'MODULE_CODE',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'MODULE_CODE',title:'组件代码',width:0.1,align:'left'},
					{field:'MODULE_NAME',title:'组件名称',width:0.1,align:'left'},
					{field:'PARENT',title:'PARENT',width:0.4,align:'left',hidden:true},
					{field:'USRE_FLAG',title:'USRE_FLAG',width:0.4,align:'left',hidden:true},
					{field:'ROLE_FLAG',title:'ROLE_FLAG',width:0.4,align:'left',hidden:true},
					{field:'NODE_TYPE',title:'NODE_TYPE',width:0.4,align:'left',hidden:true},
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
				    	    if(rowData.NODE_TYPE=="module"){
				    	           return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' id='"+rowData.MODULE_CODE+"' parent='"+rowData.PARENT+"' name='moduleselectbox' "+checked+" "+disabled+" onclick='SetAll(this)'   module_code ='"+rowData.MODULE_CODE+"' node_type='"+rowData.NODE_TYPE+"' >"; 				    	    				    	    
				    	    }else{
				            	   return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' parent='"+rowData.PARENT+"' name='selectbox' "+checked+" "+disabled+" onclick='SetBox(this)'   module_code ='"+rowData.MODULE_CODE+"' node_type='"+rowData.NODE_TYPE+"' >"; 				    	    				    	    
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
           function AddRecord(){
        	    var userId  = '${param.USER_ID}';
	 
			    var datas="";
                $("input[name='selectbox']:checked").not(':disabled').each(function(){    
				      datas = isStringNull(datas)? datas+","+$(this).attr("module_code") : $(this).attr("module_code");
				  }); 
			    var res=  IntoActionByUrl("${ctx}/sys/user/UpdateModuleAssignUser.action?USER_ID="+userId+"&MODULE_CODE="+datas);
			    if(res[0].result=="success"){
			        parent.OneFrame.nextstep('4',userId);
			     }else{
			  	   $.messager.alert("错误",res[0].result)
			     }
			  }
        </script>
	</head>
	<body>
	   
		   <div class="page_content" style="bottom: 40px;">
		       <table id="ModuleAssignUserTree" class="TreeClass" ></table>
		   </div>
	 
		    <div class="BottomDiv">

					<a href="###" class="btn_01" onclick="AddRecord()">下一步<b></b></a>				
			</div>
	</body>

</html>