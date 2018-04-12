<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>模板类型</title>
			
		<script>
		 
 
        
        $(function(){ 
        	var module_name = $("#module_name").val();
        	$('#SwbsTypeTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                singleSelect:true,
				url: '${ctx}/sys/swbs/ShowSwbsTypeTable.action?module_name='+encodeURI(module_name),
				columns:[[
				    {field:'SWBS_TYPE_ID',title:'代码',width:0.1,align:'left' ,hidden:true,sortable:true},
					{field:'SWBS_TYPE_NAME',title:'名称',width:0.2,align:'left' ,sortable:true,editor:{type:'text'}},
					{field:'DESCRIPTION',title:'描述',width:0.3,align:'left' ,sortable:true,editor:{type:'text'}}

					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
				 onDblClickCell:function(rowIndex, field, value){ 
					 var SWBS_TYPE_ID = $('#SwbsTypeTable').datagrid('getSelected').SWBS_TYPE_ID;
	               $('#SwbsTypeTable').datagrid('beginEdit', rowIndex); 
	             var ed = $('#SwbsTypeTable').datagrid('getEditor', { index: rowIndex, field: field });
	                  $(ed.target).focus().bind('blur', function () {
                         var result = GetXmlBySql("update  SM_SWBSTYPE set "+field+" = '"+$(ed.target).val()+"' where SWBS_TYPE_ID ='"+SWBS_TYPE_ID+"' ");
                   $('#SwbsTypeTable').datagrid('endEdit', rowIndex);
                      });
				 }
				
			});
        	 
		  	});
			
			  function AddRecord(){
				  var module_name = $("#module_name").val();
				   var currFrameId = "${param.currFrameId}";
				  var id = Page_GetGUID();

				 var result = GetXmlBySql("insert into SM_SWBSTYPE(wid,swbs_type_id,swbs_type_name,description,modlename)values('"+Page_GetGUID()+"','"+id+"','请填写名称','请填写描述','"+module_name+"')");
			     if(result[0].result=="success"){
			    		 $('#SwbsTypeTable').datagrid('appendRow',{
						SWBS_TYPE_ID: id,
						SWBS_TYPE_NAME: '请填写名称',
						DESCRIPTION: '请填写描述'
					});
			    		 GetIndexFrame(currFrameId).location.reload(); 
			     }		     
			  }
			function DelRecord(){
				 var currFrameId = "${param.currFrameId}";
				 var row = $('#SwbsTypeTable').datagrid('getSelected');
              if(isNull(row)){
            	  $.messager.confirm("提醒","确定要删除?",function(res){
            		  if(res){
          		         var result =   IntoActionByUrl("${ctx}/sys/swbs/DelSwbsType.action?swbs_type_id="+row.SWBS_TYPE_ID);
		                 if(result[0].result=="success"){
		            	  var rowIndex = $('#SwbsTypeTable').datagrid('getRowIndex', row);
		                 $('#SwbsTypeTable').datagrid('deleteRow', rowIndex);  
		                  GetIndexFrame(currFrameId).location.reload();   
		                 } 
          		         }
            	  });
       	  
              }else{
            	  $.messager.alert("错误","请选中要删除的行！")
              }
			}
		 

        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table id="SwbsTypeTable" class="TreeClass" ></table>
		</div>
					<div class="BottomDiv">
					<a href="###" class="btn_01" onclick="AddRecord();">增加<b></b></a>
				<a href="###" class="btn_01" onclick="DelRecord();">删除<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addEpsWindow')">关闭<b></b></a>				
			</div>
		<input type="hidden" id="module_name" value="${param.module_name}"/>
	</body>

</html>