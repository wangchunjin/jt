<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加用户</title>
			
		<script>
		 
 
        
        $(function(){ 
        	var profile_id  = '${param.PROFILE_ID}';
        	$('#AddUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/profile/AddUserTable.action?PROFILE_ID='+profile_id,
				frozenColumns:[[
				    {field:'USER_ID',checkbox:true}
				]],
				columns:[[
					{field:'USER_NAME',title:'用户名 ',width:0.2,align:'left' ,sortable:true,
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
					{field:'ACTUAL_NAME',title:'姓名 ',width:0.2,align:'left' ,sortable:true,
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
			
		 
		 function AddRecord(){
			 var currFrameId = '${param.currFrameId}';
			 var profile_id  = '${param.PROFILE_ID}';
    	 var objs = $('#AddUserTable').datagrid("getChecked");
    	 if(objs.length==0){
    		 $.messager.alert("提醒","请选择记录！");
    	 }else{
    		 var UserIds  = "";
    		 for(var i=0;i<objs.length;i++){
    			   UserIds = isStringNull(UserIds) ? UserIds +","+objs[i].USER_ID : objs[i].USER_ID ;
	    		   }
    		  
    		 	var res = IntoActionByUrl("${ctx}/sys/profile/AddProfileUser.action?PROFILE_ID="+profile_id+"&USER_IDS="+UserIds);
	    		if(res[0].result=="success"){
	    			var node = GetIndexFrame(currFrameId).TwoFrame.$('#SysProfileTable').datagrid('getSelected');
	    			var index = GetIndexFrame(currFrameId).TwoFrame.$("#SysProfileTable").datagrid('getRowIndex',node);
					GetIndexFrame(currFrameId).TwoFrame.$('#SysProfileTable').datagrid('updateRow',{
						index: index,
						row: {
							USER_NAME: res[0].USER_NAME
						}
					});
					
	    			
	    			 GetIndexFrame(currFrameId).FourFrame.location.reload(); 
	    			 parent.close_win('addWindow');
	    		}
    	 }
         
		}

        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table id="AddUserTable" class="TreeClass" ></table>
		</div>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
		 
	</body>

</html>