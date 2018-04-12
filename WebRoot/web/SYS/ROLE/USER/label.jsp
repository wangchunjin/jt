<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>分类吗</title>
			
		<script>
		 
 
         $(function(){ 
        	 var roleId  = '${param.ROLE_ID}';
        	 var role  = '${roleid}';
        	 
        	 $('#RoleUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
// 				url: '${ctx}/sys/role/RoleUserTable.action?ROLE_ID='+roleId,
				url: '${ctx}/sys/role/RoleUserTable.action?ROLE_ID='+role,
				frozenColumns:[[
				    {field:'USER_ID',checkbox:true}
				]],
				columns:[[
					{field:'USER_NAME',title:'用户名',width:0.1,align:'left',
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
					{field:'ACTUAL_NAME',title:'姓名',width:0.1,align:'left',
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
					{field:'RLUSER_ID',title:'RLUSER_ID',width:0.1,align:'left',hidden:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             });
				 }				
			});
        	 
		  	});
			
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
         	  var roleId  = '${param.ROLE_ID}';
             if(isStringNull(roleId)){
                 createSimpleWindow("addWindow","增加用户","${ctx}/web/SYS/ROLE/USER/AddUser.jsp?currFrameId="+currFrameId+"&roleId="+roleId, 460, 480);
             }else{
            	 $.messager.alert("错误","请选择角色！");
             }
          
          }
          
          function del(){
        	    var Wids="";
        	    var roleId  = '${param.ROLE_ID}';
				var rows = $('#RoleUserTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           Wids = isStringNull(Wids) ? Wids +","+rows[i].RLUSER_ID : rows[i].RLUSER_ID;	    		         
					}
				}
				 
				  $.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/role/DelRoleUser.action","rluserIds="+Wids+"&roleId="+roleId);
	    		if(res[0].result=="success"){
    			    var node = parent.TwoFrame.$('#SysRoleTree').treegrid('getSelected');
	    			parent.TwoFrame.$('#SysRoleTree').treegrid('update',{
					id: node.ROLE_ID,
					row: {
						ACTUAL_NAME: res[0].actualName
					}
				    });
	    			 location.reload(); 
	    			 
	    		}
	    		}});
              
			}
      function adds(){
     	 var currFrameId =  parent.frameElement.id; 
   	     var roleId  = '${param.ROLE_ID}';
        if(isStringNull(roleId)){
            createSimpleWindow("addWindow","增加用户","${ctx }/web/SYS/ROLE/USER/HR/FS.jsp?currFrameId="+currFrameId+"&roleId="+roleId, 800, 480);
        }else{
       	 $.messager.alert("错误","请选择角色！");
        }
       }
        </script>
	</head>
	<body>
		<div class="page_content" style="bottom: 40px;">
		<table id="RoleUserTable" class="TreeClass" ></table>
		</div>
			<div class="LabelBottomDiv">
<%-- 			 <epmis:button id="addpeople" imageCss="icon-add" value="获取" action="adds()" ></epmis:button> --%>
             <epmis:button id="addtask" imageCss="icon-add" value="分配" action="add()" ></epmis:button>
             <epmis:button id="Delete" imageCss="icon-remove" value="删除" action="del()" ></epmis:button>			
			</div>
	 
	</body>

</html>