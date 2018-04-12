<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>责任人</title>
			
		<script>
		 
 
         $(function(){ 
        	 var proj_id ='${param.PROJ_ID}';
        
        	$('#ProjCompanyTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,  
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/proj/ProjCompanyTable.action?proj_id='+proj_id,
				frozenColumns:[[
				    {field:'WID',checkbox:true}
				]],
				columns:[[
					{field:'VNMT',title:'VNMT',width:0.1,align:'left',hidden:true},
					{field:'COMPANY_ABBREV',title:'公司缩写',width:0.1,align:'left' ,sortable:true,
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
					{field:'COMPANY_NAME',title:'公司名称',width:0.1,align:'left' ,sortable:true,
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
					{field:'ROLE_ID',title:'项目角色',width:0.1,align:'left' ,sortable:true,
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
            createSimpleWindow("addWindow","增加单位","${ctx}/web/SYS/PROJ/COMPANY/AddCompany.jsp?currFrameId="+currFrameId+"&projId="+projId, 800, 500);
      
          
          }
          
          function del(){
        	    var Wids="";
				var rows = $('#ProjCompanyTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           Wids = isStringNull(Wids) ? Wids +","+rows[i].WID : rows[i].WID;	    		         
					}
				}
				 
				  $.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/proj/delVnmt.action?Wids="+Wids);
	    		if(res[0].result=="success"){
	    			 location.reload(); 
	    			 
	    		}
	    		}});
              
			}
          
          function edit(){
        	    var node = parent.TwoFrame.$('#ProjTree').treegrid('getSelected');
        	    if(isNull(node)){
        	    	parent.TwoFrame.OpenProj(node);
        	    	parent.parent.addTab('DS_GTWORKS','参建单位','web/DS/DS_VNMT/FS.jsp');
        	    }else{
        	    	$.messager.alert("错误","请选项目记录！");
        	    }
          }
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table id="ProjCompanyTable" class="TreeClass" ></table>
		</div>
			<div class="LabelBottomDiv">
 	
             <epmis:button id="addtask" imageCss="icon-add" value="增加" action="add()" datactrCode="SYS_PROJ.cjdw.edit"></epmis:button>
             <epmis:button id="Delete" imageCss="icon-remove" value="删除" action="del()" datactrCode="SYS_PROJ.cjdw.edit"></epmis:button>		
            <epmis:button id="editRecord" imageCss="icon-edit" value="维护" action="edit()" datactrCode="SYS_PROJ.cjdw.edit"></epmis:button>		
			</div>
	 
	</body>

</html>