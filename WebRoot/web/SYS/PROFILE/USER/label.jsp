<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>分类吗</title>
			
		<script>
		 
 
         $(function(){ 
        	 var PROFILE_ID  = '${param.PROFILE_ID}';
        	 $('#RoleUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,      
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/profile/ProfileUserTable.action?PROFILE_ID='+PROFILE_ID,
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
					}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })				
				 }				
			});
        	 
		  	});
			
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
         	 var profile_id  = '${param.PROFILE_ID}';
             if(isStringNull(profile_id)){
                 createSimpleWindow("addWindow","增加用户","${ctx}/web/SYS/PROFILE/USER/AddUser.jsp?currFrameId="+currFrameId+"&PROFILE_ID="+profile_id, 460, 480);
             }else{
            	 $.messager.alert("错误","请选择角色！");
             }
          
          }
          
          function del(){
        	    var userIds="";
        	     var profile_id  = '${param.PROFILE_ID}';
				var rows = $('#RoleUserTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           userIds = isStringNull(userIds) ? userIds +","+rows[i].USER_ID : rows[i].USER_ID;	    		         
					}
				}
				 
				  $.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/profile/DelProfileUser.action?USER_IDS="+userIds+"&PROFILE_ID="+profile_id);
	    		if(res[0].result=="success"){
	    			var node = parent.TwoFrame.$('#SysProfileTable').datagrid('getSelected');
	    			var index = parent.TwoFrame.$("#SysProfileTable").datagrid('getRowIndex',node);
					parent.TwoFrame.$('#SysProfileTable').datagrid('updateRow',{
						index: index,
						row: {
							USER_NAME: res[0].USER_NAME
						}
					});
					
	    			 location.reload(); 
	    			 
	    		}
	    		}});
              
			}
         
        </script>
	</head>
	<body>
		<div class="page_content" style="bottom: 40px;">
		<table id="RoleUserTable" class="TreeClass" ></table>
		</div>
			<div class="LabelBottomDiv">
             <epmis:button id="addtask" imageCss="icon-add" value="分配" action="add()" ></epmis:button>
             <epmis:button id="Delete" imageCss="icon-remove" value="删除" action="del()" ></epmis:button>			
			</div>
	 
	</body>

</html>