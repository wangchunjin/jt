<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加建设单位</title>
			
		<script>
		 
 
        
        $(function(){ 
        	var projId = $("#projId").val();
        	$('#AddUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/proj/AddUserTable.action?projId='+projId,
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
					},
					{field:'PROFILE_NAME',title:'职务   ',width:0.3,align:'left' ,sortable:true},
					{field:'PROFILE_ID',title:'职务   ',width:0.3,align:'left' ,sortable:true,hidden:true}
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
			 var projId = $("#projId").val();
    	 var objs = $('#AddUserTable').datagrid("getChecked");
    	 if(objs.length==0){
    		 $.messager.alert("提醒","请选择记录！");
    	 }else{
    		 var UserIds  = "";
    		 for(var i=0;i<objs.length;i++){
    			   UserIds = isStringNull(UserIds) ? UserIds +";"+objs[i].USER_ID : objs[i].USER_ID ;
	    		   }
    		 
    		 	var res = IntoActionByUrl("${ctx}/sys/proj/AddUser.action?projId="+projId+"&userIds="+UserIds);
	    		if(res[0].result=="success"){
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
		<input type="hidden" id="projId" value="${param.projId}"/>
	</body>

</html>