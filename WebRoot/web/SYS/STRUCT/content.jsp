<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
       function openDoc(url,id){
    	   url = "${ctx}"+url;
    	  	 var width = "1000";
             if($(parent.window).width()<1000){
            	 width = $(parent.window).width()-100;
             }
    		 createSimpleWindow("viewDocWindow","查看文档","${ctx}/web/SYS/STRUCT/edit.jsp?url="+url+"&id="+id, width, $(parent.window).height());
 
       }
         $(function(){ 
        	 
       	$('#StructTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true, 
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/struct/StructTable.action',
				frozenColumns:[[
				    {field:'OFFICETPL_ID',checkbox:true}
				]],
				columns:[[
					 {field:'IMG',title:'操作',width:0.05,align:'center' ,sortable:true,
						formatter:function(value,row,index){
						 return " <img style=' cursor: pointer;' onclick=\"openDoc('"+row.OFFICETPL_URL+"','"+row.OFFICETPL_ID+"')\" src='${ctx}/img/button/bg_fileview.gif'>";
					 }
					},
					{field:'OFFICETPL_NAME',title:'名称',width:0.2,align:'left' ,sortable:true,
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
					{field:'OFFICETPL_URL',title:'office模板',width:0.3,align:'left' ,sortable:true,
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
					{field:'OFFICETPL_REMARK',title:'备注',width:0.3,align:'left' ,sortable:true,
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
					{field:'SEQ_NUM',title:'排序',width:0.1,align:'right' ,sortable:true} ,
					{field:'OFFICETPL_FLAG',title:'OFFICETPL_FLAG',width:0.05,align:'center' ,hidden:true} 
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
               onClickRow:function(rowIndex, node){
				var where = node.OFFICETPL_ID+";"+node.OFFICETPL_FLAG;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
				
			});
        	 
		  	});
			
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="StructTable" class="TreeClass" ></table>
		</div>
   </body>

</html>