<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
 
	<head>
		<title>责任人</title>
			
		<script>
		 
 
         $(function(){ 
 
        	$('#ProjTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                singleSelect:true,
				url: '${ctx}/sys/proj/ProjTable.action',
				columns:[[
					{field:'PROJ_ID',title:'PROJ_ID',width:0.1,align:'left' ,sortable:true ,hidden : true},
					{field:'PROJ_SHORT_NAME',title:'代码',width:0.2,align:'left' ,sortable:true},
					{field:'PROJ_NAME',title:'名称',width:0.3,align:'left' ,sortable:true},
					{field:'DEPT_NAME',title:'所属部门',width:0.1,align:'center',sortable:true},
					{field:'STAGE_NAME',title:'项目类别',width:0.1,align:'center',sortable:true},
					{field:'PROJ_CMPT_PCT',title:'形象进度',width:0.15,align:'center',sortable:true},
					{field:'PROCEED_DATE',title:'开工日期',width:0.1,align:'center',sortable:true},
					{field:'COMPLETION_DATE',title:'完工日期',width:0.1,align:'center',sortable:true},
					{field:'CONTRACT_TITLE',title:'项目状态',width:0.1,align:'center' ,sortable:true}			 
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	              })

				
				 },
				 onClickRow:function(rowIndex, rowData){
				parent.OneFrame.location.reload();
 
				 var proj_id = rowData.PROJ_ID;
 
				 var where = proj_id+";false";
			 
					   parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=SYS_PROJ&Where="+where;		
				 	
				 
				}
 
				
			});
        	 
		  	});
			
       
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="ProjTable" class="TreeClass" ></table>
		</div>
 
	 
	</body>

</html>