<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>项目存取</title>
			
		<script>
		 
 
         $(function(){ 
        	 var proj_id ='${param.PROJ_ID}';
        
        	$('#ProjUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,          
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/proj/ProjUserTable.action?projId='+proj_id,
				frozenColumns:[[
				    {field:'USER_ID',checkbox:true}
				]],
				columns:[[
					{field:'USER_NAME',title:'用户名',width:0.1,align:'left' ,sortable:true,
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
					{field:'ACTUAL_NAME',title:'姓名 ',width:0.1,align:'left' ,sortable:true,
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
					{field:'PROFILE_NAME',title:'项目安全配置',width:0.1,align:'center' ,sortable:true,
					/* 	editor:{type:'combobox',options:{
						valueField: 'PROFILE_ID',
				        textField: 'PROFILE_NAME',
				        panelHeight: '100'
					}}, */
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
					{field:'OF_ROLE',title:'角色继承',width:0.1,align:'center' ,sortable:true},
			         {field:'PROFILE_ID',title:'',width:0.3,align:'center' ,sortable:true,hidden:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
 				 onDblClickCell:function(rowIndex, field, value){ 
					 var user_id = $('#ProjUserTable').datagrid('getSelected').USER_ID;
				 
	               $('#ProjUserTable').datagrid('beginEdit', rowIndex); 
	              // synchCategory()
	              
	             var ed = $('#ProjUserTable').datagrid('getEditor', { index: rowIndex, field: field });
	              var jqData =IntoActionByUrl("${ctx}/sys/proj/GetProfileType.action?value="+value);
	              
	               $(ed.target).combobox('loadData' , jqData);
	                 $(ed.target).combobox({
 
                  onSelect:function(record){
                          IntoActionByUrl("${ctx}/sys/proj/SetProfileType.action?id="+record.PROFILE_ID+"&proj_id="+proj_id+"&user_id="+user_id);
                            location.reload();           
                            }
	                 });
	 
				 }
				
			});
        	 
		  	});
 
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
 
        
             var projId = '${param.PROJ_ID}';
            createSimpleWindow("addWindow","增加用户","${ctx}/web/SYS/PROJ/USER/AddUser.jsp?currFrameId="+currFrameId+"&projId="+projId, 700, 600);
      
          
          }
         function addAll(){
        	  var currFrameId =  parent.frameElement.id; 
                var ids="";
				var rows = $('#ProjUserTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选中要设置的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){
						  
							 ids = isStringNull(ids) ? ids +","+rows[i].USER_ID : rows[i].USER_ID; 
						 	   			          	    		         
					}
				}
        
             var projId = '${param.PROJ_ID}';
            createSimpleWindow("addWindow","批量设置安全配置","${ctx}/web/SYS/PROJ/USER/SetUser.jsp?currFrameId="+currFrameId+"&projId="+projId+"&userIds="+ids, 400, 400);
      
          
          }
          function del(){
        	   var projId = '${param.PROJ_ID}';
        	    var ids="";
				var rows = $('#ProjUserTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){
						 if(!isStringNull(rows[i].OF_ROLE)){
							 ids = isStringNull(ids) ? ids +","+rows[i].USER_ID : rows[i].USER_ID; 
						 }	   			          	    		         
					}
				}
				if(!isStringNull(ids)){
					$.messager.alert("错误","系统禁止删除角色继承的用户！");
					return;
				}
				  $.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/proj/delUser.action?userIds="+ids+"&projId="+projId);
	    		if(res[0].result=="success"){
	    			 location.reload(); 
	    			 
	    		}
	    		}});
              
			}
       function edit(){
  	       
      	  var currFrameId =  parent.frameElement.id; 
         var row = $('#ProjUserTable').datagrid('getSelected');
          if(isNull(row)){
        	var profile_id = $('#ProjUserTable').datagrid('getSelected').PROFILE_ID;
          createSimpleWindow("addWindow","查看安全配置","${ctx}/web/SYS/PROJ/USER/seeAssign.jsp?currFrameId="+currFrameId+"&profile_id="+profile_id, 800, 600);
 
          }else{
        	  $.messager.alert("错误","请选中要查看的用户！");
          }
        
     
          
          }
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table id="ProjUserTable" class="TreeClass" ></table>
		</div>
			<div class="LabelBottomDiv">
             <epmis:button id="addtask" imageCss="icon-add" value="增加" action="add()" datactrCode="SYS_PROJ.user.edit"></epmis:button>
             <epmis:button id="Delete" imageCss="icon-remove" value="删除" action="del()" datactrCode="SYS_PROJ.user.edit"></epmis:button>	
             <epmis:button id="addtaskAll" imageCss="icon-remove" value="设置安全配置" action="addAll()" datactrCode="SYS_PROJ.user.edit"></epmis:button>	
            <epmis:button id="editRecord" imageCss="icon-edit" value="查看安全配置" action="edit()" datactrCode="SYS_PROJ.user.edit"></epmis:button>		
			</div>
	 
	</body>

</html>