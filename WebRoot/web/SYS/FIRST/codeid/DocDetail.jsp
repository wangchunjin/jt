<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>文档列表</title>
			
		<script>
		 
        
        $(function(){ 
        	 var Startdate = "${param.Startdate}";
        	 var Enddate = "${param.Enddate}";
        	 var NodeType = "${param.NodeType}";
        	 var CodeId = "${param.CodeId}";
        	 var Id = "${param.Id}"; 
		//	+ "&proj_id=${param.proj_id}";
        	$('#ShowDocDetail').datagrid({
                remoteSort:false,
                
                fitColumns:true,
                singleSelect:true,
                striped:true,    
                idField:'DOC_ID',
                url: "${ctx}/ds/ds_ldzl/ShowDocDetail.action?Id="+Id+"&NodeType="+NodeType+"&Startdate="+Startdate+"&Enddate="+Enddate+"&CodeId="+CodeId,
			    columns:[[
					{field:'IMG',title:'操作',width:0.09,align:'center' ,sortable:true,
						formatter:function(value,row,index){
						 return row.IMG+"<span style='width:17px;cursor: pointer;' onclick=\"openDoc('"+row.DOC_ID+"')\"   class='icon-view' >&nbsp;&nbsp;&nbsp;&nbsp;</span>  <span style='width:17px;cursor: pointer;' onclick=\"downDoc('"+row.DOC_ID+"')\"   class='icon-download' >&nbsp;&nbsp;&nbsp;&nbsp;</span>";
					 }
					},
				    {field:'DOC_NUMBER',title:'编号',width:0.2,align:'left' ,sortable:true,
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
					{field:'TITLE',title:'文档标题',width:0.3,align:'left' ,sortable:true,
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
					{field:'CREATED_DATE',title:'创建日期',width:0.1,align:'center' ,sortable:true}, 
					{field:'CREATED_BY',title:'创建人',width:0.1,align:'center' ,sortable:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
				 onDblClickCell:function(rowIndex, field, value){ 
                     
				 } 
				
			});
        	 
		  	});
			
			  
		 
function downDoc(docId){
	var res = IntoActionByUrl("${ctx}/km/km_upload/downDoc.action?docId="+docId+"&type=");
 
	 window.open(res[0].result)
}
        </script>
	</head>
	<body>
		<div class="page_content"  >
		<table id="ShowDocDetail" class="TreeClass" ></table>
		</div>			 
	</body>

</html>