<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 var onselectrow;
 
         $(function(){ 
        	 var CodeType ='${param.CodeType}';
        	  var Belongto ='${param.Belongto}';
        
        	$('#SysCodeTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,     
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/code/SysCodeTable.action?CodeType='+CodeType+'&Belongto='+Belongto,
				frozenColumns:[[
				    {field:'WID',checkbox:true}
				]],
				columns:[[
					{field:'CODE_SHORT_NAME',title:'代码',width:0.1,align:'left' ,sortable:true,
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
					{field:'CODE_NAME',title:'名称 ',width:0.1,align:'left' ,sortable:true,
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
					{field:'MODULE_NAME',title:'所属组件',width:0.1,align:'left' ,sortable:true,
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
					{field:'SEQ_NUM',title:'排序',width:0.3,align:'left' ,sortable:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
               onClickRow:function(rowIndex, node){
				var where = node.WID;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				onselectrow = node;
				}
				
			});
        	 
		  	});
			
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="SysCodeTable" class="TreeClass" ></table>
		</div>
   </body>

</html>