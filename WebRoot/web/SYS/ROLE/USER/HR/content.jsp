<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 var onselectrow;
 		var newselectrow;
         $(function(){ 
        	    var dept_ids ="";
        	    if(isNull(parent.TreeFrame)){
        	    	if(isNull(parent.TreeFrame.$))
					parent.TreeFrame.$("input[name='selectbox'][node_type='0']:checked").each(function(){
						dept_ids = isStringNull(dept_ids)?dept_ids +"," + $(this).attr("id") :$(this).attr("id");
					});
        	    }
				var statustype ="3";
        	$('#HrEmployeeTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,                 
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
                queryParams:{dept_ids:dept_ids},
                idField:'EMPLOYEEID', 
				url: '${ctx}/hr/hr_employee/HrEmployeeUserTable.action',
				frozenColumns:[[
				    {field:'EMPLOYEEID',checkbox:true}
				]],
				columns:[[
					{field:'EMPLOYEENO',title:'员工工号',width:0.1,align:'left' ,sortable:true,
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
					{field:'EMPLOYEENAME',title:'员工姓名 ',width:0.1,align:'left' ,sortable:true,
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
           	 var currFrameId  = '${param.currFrameId}';
            
        	 var roleId  = '${param.roleId}';
     
        	 var ids="";
				var rows = parent.TwoFrame.$('#HrEmployeeTable').datagrid('getChecked');
				if(rows.length==0){
					parent.TwoFrame.$.messager.alert("错误","请选中要添加的员工！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           ids = isStringNull(ids) ? ids +","+rows[i].EMPLOYEEID : rows[i].EMPLOYEEID;	    		         
					}
				} 
				 
				var res = IntoActionByUrl("${ctx}/hr/hr_employee/AddRoleUsers.action","ids="+ids+"&roleId="+roleId);
	    		if(res[0].result=="success"){
	    	 	var wid = $("#"+currFrameId,parent.parent.document.body)[0].contentWindow;
	    	 	 
	    	 	wid.FourFrame.location.reload();
	    			 parent.parent.close_win('addWindow');		 
	    		} 
         }
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;" >
		    <table id="HrEmployeeTable" class="TreeClass" ></table>
		</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
   </body>

</html>