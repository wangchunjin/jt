<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>标记重命名</title>
			
		<script>
		 
 
        
        $(function(){ 
        	var id = $("#id").val();
        	$('#OfficetplKeyInfo').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                singleSelect:true,
				url: '${ctx}/sys/struct/OfficetplKeyInfo.action?ID='+id,
				columns:[[
				    {field:'ID',title:'ID',width:0.1,align:'left' ,hidden:true,sortable:true},
				    {field:'OFFICETPL_ID',title:'OFFICETPL_ID',width:0.1,align:'left' ,hidden:true,sortable:true},
					{field:'KEY_SHORT_NAME',title:'标签代码',width:0.1,align:'left' ,sortable:true},
					{field:'KEY_NAME',title:'标签名称',width:0.1,align:'left' ,sortable:true,
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
                    {field:'KEY_DISPLAY_NAME',title:'重命名',width:0.2,align:'left' ,sortable:true,editor:{type:'text'}}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
				 onDblClickCell:function(rowIndex, field, value){ 
					 var ID = $('#OfficetplKeyInfo').datagrid('getSelected').ID;
	               $('#OfficetplKeyInfo').datagrid('beginEdit', rowIndex); 
	             var ed = $('#OfficetplKeyInfo').datagrid('getEditor', { index: rowIndex, field: field });
	                  $(ed.target).focus().bind('blur', function () {
                         var result = GetXmlBySql("update  STRUCT_OFFICETPL_KEY set "+field+" = '"+$(ed.target).val()+"' where ID ='"+ID+"' ");
                   $('#OfficetplKeyInfo').datagrid('endEdit', rowIndex);
                      });
				 }
				
			});
        	 
		  	});
			
			 
        </script>
	</head>
	<body>
				<div  >
				 			<font style="color: red;">修改只需双击对应的‘重命名’列，修改完自动保存！</font>
			</div>
		<div   class="page_content" style="top: 30px;">
		<table id="OfficetplKeyInfo" class="TreeClass" ></table>
		</div>

		<input type="hidden" id="id" value="${param.id}"/>
	</body>

</html>