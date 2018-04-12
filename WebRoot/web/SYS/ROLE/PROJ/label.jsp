<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>模块树</title>
			
		<script>
  
        $(function(){ 
        	var roleId  = '${param.ROLE_ID}';
        	$('#ProjRoleTree').treegrid({
         
			 	url: '${ctx}/sys/role/ProjRoleTree.action?parentId=0&ROLE_ID='+roleId,				                                       
				idField:'PROJ_ID',
				treeField:'PROJ_SHORT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'PROJ_SHORT_NAME',title:'代码',width:0.1,align:'left'},
					{field:'PROJ_NAME',title:'名称',width:0.1,align:'left'},
					{field:'PARENT_PROJ_ID',title:'PARENT_PROJ_ID',width:0.4,align:'left',hidden:true},
					{field:'ROLE_FLAG',title:'ROLE_FLAG',width:0.4,align:'left',hidden:true},
					{field:'PROJ_NODE_FLAG',title:'PROJ_NODE_FLAG',width:0.05,align:'center',hidden:true},
					{field:'SELECT',title:'选择',width:0.4,align:'left',
				    	  formatter:function(value,rowData,index){								
						  var checked ="";
				    	    if(rowData.ROLE_FLAG!="0"){
				    		  checked ="checked";
				    	   }
                    return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' id='"+rowData.PROJ_ID+"' parent='"+rowData.PARENT_PROJ_ID+"' name='selectbox' "+checked+"  onclick='SetAll(this)'     proj_node_flag='"+rowData.PROJ_NODE_FLAG+"' >"; 				    	    				    	    
				    	}
				     }
					
				]]  
			});
		  	});
			
			function SetAll(obj){
				$('#labelBottomDiv').css('display','');
				SetBox(obj.checked,obj.id)
				if(!obj.checked){
			 	   SetParentBox(obj.checked,$(obj).attr("parent"))
				}
			}  
			
			function SetBox(checked,id){				
				$("input[parent='"+id+"']").not(':disabled').each(function(){
					$(this).attr("checked",checked);
					SetBox(checked,$(this).attr("id"));
				});			 			 
			}
			function SetParentBox(checked,parentId){
				$("input[id='"+parentId+"']").not(':disabled').attr("checked",false);
				parentId = $("input[id='"+parentId+"']").attr("parent")
				if(parentId !="0"&&$("input[id='"+parentId+"']").length>0){
				   SetParentBox(checked,$("input[id='"+parentId+"']").attr("id"));
			 	}
			}
			
           function SaveRecord(){
 
        	    var roleId  = '${param.ROLE_ID}';
			    var node = parent.TwoFrame.$('#SysRoleTree').treegrid('getSelected');
			    if(!isNull(node)){
			    	$.messager.alert("错误","请选择角色！");
			    	return;
			    }
			    var flag="";
			    var res=  IntoActionByUrl("DeleteProjRole.action?ROLE_ID="+roleId);
			    flag = res[0].result;
			    var datas="";
			    var i=0;
			    var length= $("input[name='selectbox']:checked").not(':disabled').length;
                $("input[name='selectbox']:checked").not(':disabled').each(function(){
                	 i = i+1;
                 	 datas = isStringNull(datas) ? datas +","+$(this).attr("id") : $(this).attr("id");
                	 if(i==length){
                		  res =   IntoActionByUrl("InsertProjRole.action?ROLE_ID="+roleId+"&PROJ_IDS="+datas);
                	 }else if(i%50==0){
                		 res =   IntoActionByUrl("InsertProjRole.action?ROLE_ID="+roleId+"&PROJ_IDS="+datas);
                		 datas="";
                	 }
				    
				  });
        	     flag = res[0].result;
			     if(flag=="success"){
			        window.location.reload();
			     }else{
			  	   $.messager.alert("错误",res[0].result)
			     }
			  }
  
        </script>
	</head>
	<body>
	    <div class="label_div">
		   <div class="page_content" style="bottom: 30px;">
		       <table id="ProjRoleTree" class="TreeClass" ></table>
		   </div>
		   		   <div class="LabelBottomDiv" style="width: 60%">
             <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="exportAll('ProjRoleTree')"></epmis:button>
             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="closeAll('ProjRoleTree')"></epmis:button> 	
			</div>
		</div>
	</body>

</html>