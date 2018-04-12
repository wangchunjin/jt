<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>责任人</title>
			
		<script>
		 
 
         $(function(){ 
        	 var proj_id ='${param.PROJ_ID}';
        
        	$('#ProjPeopleTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true, 
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/proj/ProjPeopleTable.action?projId='+proj_id,
				frozenColumns:[[
				    {field:'ID',checkbox:true}
				]],
				columns:[[
					{field:'ACTUAL_NAME',title:'用户名称',width:0.1,align:'left' ,sortable:true,
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
					{field:'SEX',title:'性别 ',width:0.1,align:'left' ,sortable:true},
					{field:'JOB_TITLE',title:'职务',width:0.1,align:'left' ,sortable:true,
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
					{field:'TEL',title:'联系电话',width:0.1,align:'left' ,sortable:true},
			        {field:'DEPT_NO',title:'部门',width:0.1,align:'left' ,sortable:true,
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

				
				 },
 
				
			});
        	 
		  	});
			
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
 
        
             var projId = '${param.PROJ_ID}';
            createSimpleWindow("addWindow","增加人员","${ctx}/web/SYS/PROJ/PEOPLE/AddPeople.jsp?currFrameId="+currFrameId+"&projId="+projId, 700, 300);
      
          
          }
          
          function del(){
        	    var ids="";
				var rows = $('#ProjPeopleTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           ids = isStringNull(ids) ? ids +","+rows[i].ID : rows[i].ID;	    		         
					}
				}
				 
				  $.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/proj/delPeople.action?ids="+ids);
	    		if(res[0].result=="success"){
	    			 location.reload(); 
	    			 
	    		}
	    		}});
              
			}
       function edit(){
        	  var currFrameId =  parent.frameElement.id; 
         var row = $('#ProjPeopleTable').datagrid('getSelected');
          if(isNull(row)){
          createSimpleWindow("addWindow","修改人员","${ctx}/sys/proj/EditPeople.action?currFrameId="+currFrameId+"&id="+row.ID, 700, 300);
 
          }else{
        	  $.messager.alert("错误","请选中要修改的行！");
          }
        
     
          
          }
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table id="ProjPeopleTable" class="TreeClass" ></table>
		</div>
			<div class="LabelBottomDiv">
 	
             <epmis:button id="addtask" imageCss="icon-add" value="增加" action="add()" datactrCode="SYS_PROJ.projuser.edit"></epmis:button>
             <epmis:button id="Delete" imageCss="icon-remove" value="删除" action="del()" datactrCode="SYS_PROJ.projuser.edit"></epmis:button>		
            <epmis:button id="editRecord" imageCss="icon-edit" value="修改" action="edit()" datactrCode="SYS_PROJ.projuser.edit"></epmis:button>		
			</div>
	 
	</body>

</html>