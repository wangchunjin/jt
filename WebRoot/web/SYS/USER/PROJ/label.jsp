<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>模块树</title>
			
		<script>
  
        $(function(){ 
        	var userId  = '${param.USER_ID}';
        	$('#ProjUserTree').treegrid({
         
			 	url: '${ctx}/sys/user/ProjUserTree.action?parentId=0&USER_ID='+userId,				                                       
				idField:'PROJ_ID',
				treeField:'PROJ_SHORT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'PROJ_SHORT_NAME',title:'代码',width:0.1,align:'left'},
					{field:'PROJ_NAME',title:'名称',width:0.1,align:'left'},
					{field:'PARENT_PROJ_ID',title:'PARENT_PROJ_ID',width:0.4,align:'left',hidden:true},
					{field:'USRE_FLAG',title:'USRE_FLAG',width:0.4,align:'left',hidden:true},
					{field:'ROLE_FLAG',title:'ROLE_FLAG',width:0.4,align:'left',hidden:true},
					{field:'PROFILE_ID',title:'PROFILE_ID',width:0.4,align:'left',hidden:true},
					{field:'PROJ_NODE_FLAG',title:'PROJ_NODE_FLAG',width:0.05,align:'center',hidden:true},
					{field:'SELECT',title:'选择',width:0.05,align:'center',
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
                    return "<input type='checkbox' id='"+rowData.PROJ_ID+"' parent='"+rowData.PARENT_PROJ_ID+"' name='selectbox' "+checked+" "+disabled+" onclick='SetAll(this)'     proj_node_flag='"+rowData.PROJ_NODE_FLAG+"' >"; 				    	    				    	    
				    	}
				     },
					{field:'PROFILE_NAME',title:'项目安全配置(双击修改)',width:0.4,align:'left'}
					
				]] ,
				onLoadSuccess:function(row,data){
			  	    $("div[class='datagrid-body']").find("[field='PROFILE_NAME']").each(function() { 
					        $(this).dblclick(function() { 
					        var value =$(this).children().first().html();
					        var proj_node_flag = $('#ProjUserTree').treegrid('getSelected').PROJ_NODE_FLAG;
					        if(proj_node_flag=="N"){
					        	return;
					        }
                                var jqData =IntoActionByUrl("${ctx}/sys/proj/GetProfileType.action?value="+value);
                                var str ="";
                                for(var i =0;i<jqData.length;i++){
                                	var select ="";
                                	if(isNull(jqData[i].selected)&&jqData[i].selected==true){
                                		select = "selected"
                                	}
                                   str = str+"<option "+select+"  value='"+jqData[i].PROFILE_ID+"'>"+jqData[i].PROFILE_NAME+"</option>"
                                }
                                var proj_id = $('#ProjUserTree').treegrid('getSelected').PROJ_ID;
                                $(this).children().first().html("<select name='profile_name_select'  proj_id='"+proj_id+"' >"+str+"</option></select>"); 
                                 $("#labelBottomDiv").css("display","");
					    }); 
					
					}); 
				}
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
				$("input[parent='"+id+"']").each(function(){
					if(!$(this).prop("disabled")){											
					   $(this).attr("checked",checked);
					}
					SetBox(checked,$(this).attr("id"));
				});			 			 
			}
			function SetParentBox(checked,parentId){
				$("input[id='"+parentId+"']").not(':disabled').attr("checked",false);
				parentId = $("input[id='"+parentId+"']").attr("parent");
				if(parentId !="0"&&$("input[id='"+parentId+"']").length>0){
				   SetParentBox(checked,$("input[id='"+parentId+"']").attr("id"));
			 	}
			}
			
           function SaveRecord(){
 
        	    var userId  = '${param.USER_ID}';
			    var node = parent.TwoFrame.$('#SysUserTable').datagrid('getSelected');
			    if(!isNull(node)){
			    	$.messager.alert("错误","请选择用户！");
			    	return;
			    }
			    var flag="";
			    var res=  IntoActionByUrl("DeleteProjUser.action?USER_ID="+userId);
			    flag = res[0].result;
			    var datas="";
			    var i=0;
			    var length= $("input[name='selectbox']:checked").not(':disabled').length;
                $("input[name='selectbox']:checked").not(':disabled').each(function(){
                	 i = i+1;
                 	 datas = isStringNull(datas) ? datas +","+$(this).attr("id") : $(this).attr("id");
                	 if(i==length){
                		  res =   IntoActionByUrl("InsertProjUser.action?USER_ID="+userId+"&PROJ_IDS="+datas);
                	 }else if(i%50==0){
                		 res =   IntoActionByUrl("InsertProjUser.action?USER_ID="+userId+"&PROJ_IDS="+datas);
                		 datas="";
                	 }
				    
				  });
                flag = res[0].result;
        	     $("select[name='profile_name_select']").each(function(){
        	    	 res =  IntoActionByUrl("${ctx}/sys/proj/SetProfileType.action?id="+$(this).val()+"&proj_id="+$(this).attr("proj_id")+"&user_id="+userId);
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
		   <div class="page_content"  style="bottom: 30px;">
		       <table id="ProjUserTree" class="TreeClass" ></table>
		   </div>
		   <div class="LabelBottomDiv" style="width: 60%">
             <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="exportAll('ProjUserTree')"></epmis:button>
             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="closeAll('ProjUserTree')"></epmis:button> 	
			</div>
		</div>
	</body>

</html>