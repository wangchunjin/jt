<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
 
         $(function(){ 
        	 var proj_stage ='${param.proj_stage}'; 
        
        	$('#SysColumnTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,               
				url: '${ctx}/sys/proj/ShowProjColumn.action?proj_stage='+proj_stage,
	            singleSelect:true,
				columns:[[
					{field:'COLUMN_CODE',title:'字段代码 ',width:0.1,align:'left' ,sortable:true,
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
					{field:'COLUMN_NAME',title:'字段名称 ',width:0.1,align:'left' ,sortable:true,editor:{type:'text'},
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
					{field:'ENABLED',title:'是否启用',width:0.1,align:'center' ,sortable:true,
					    formatter: function(value,row,index){
							if (value==1){
								return "<input id='selectId' name='selectName' checked  type='checkbox' value='"+ row.WID +"'     onclick='updateAbled(this)'>";
							} else {
								return "<input id='selectId' name='selectName'  type='checkbox' value='"+ row.WID +"'    onclick='updateAbled(this)'>";
							}
						}},
					{field:'SEQ_NUM',title:'排序 ',width:0.3,align:'left' ,sortable:true,editor:{type:'text'}},
					{field:'WID',title:'WID ',width:0.3,align:'left' ,sortable:true,hidden:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
				  onDblClickCell:function(rowIndex, field, value){ 
					 var WID = $('#SysColumnTable').datagrid('getSelected').WID;
	               $('#SysColumnTable').datagrid('beginEdit', rowIndex); 
	             var ed = $('#SysColumnTable').datagrid('getEditor', { index: rowIndex, field: field });
	                  $(ed.target).focus().bind('blur', function () {
                         var result = GetXmlBySql("update  CM_COLUMN set "+field+" = '"+$(ed.target).val()+"' where WID ='"+WID+"' ");
                   $('#SysColumnTable').datagrid('endEdit', rowIndex);
                      });
				 }
			});
        	 
		  	});
			
         function updateAbled(obj){
        	 var checkValue = "0";
        	 if(obj.checked){
        		 checkValue = "1";
        	 }
        	var result = GetXmlBySql("update  CM_COLUMN set ENABLED = '"+checkValue+"' where WID ='"+obj.value+"' ");
         }
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="SysColumnTable" class="TreeClass" ></table>
		</div>
   </body>

</html>