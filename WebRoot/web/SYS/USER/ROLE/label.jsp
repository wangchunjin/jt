<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>分类吗</title>
			
		<script>
		 
 
         $(function(){ 
        	 var userId  = '${param.USER_ID}';
        	 
        	 $('#RoleUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,    
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/user/RoleUserTable.action?USER_ID='+userId,
				frozenColumns:[[
				    {field:'RLUSER_ID',checkbox:true}
				]],
				columns:[[
					{field:'ROLE_NAME',title:'角色名称',width:0.1,align:'left'}	 
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
         	  var userId  = '${param.USER_ID}';
             if(isStringNull(userId)){
                 createSimpleWindow("addWindow","增加角色","${ctx}/web/SYS/USER/ROLE/AddRole.jsp?currFrameId="+currFrameId+"&userId="+userId, 460, 480);
             }else{
            	 $.messager.alert("错误","请选择用户！");
             }
          
          }
          
          function del(){
        	    var Wids="";
        	    var userId  = '${param.USER_ID}';
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
				var res = IntoActionByUrl("${ctx}/sys/user/DelRoleUser.action?rluserIds="+Wids+"&userId="+userId);
	    		if(res[0].result=="success"){
    			    var node = parent.TwoFrame.$('#SysUserTable').datagrid('getSelected');
	    			var index =  parent.TwoFrame.$("#SysUserTable").datagrid('getRowIndex',node);
					 parent.TwoFrame.$('#SysUserTable').datagrid('updateRow',{
						index: index,
						row: {
							ROLE_NAME: res[0].roleName
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