<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>模板类型</title>
			
		<script>
		 
 
        
        $(function(){ 
        	var WHICH_CATVAL_TYPE = $("#WHICH_CATVAL_TYPE").val();
        	$('#CatvalTypeTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                singleSelect:true,
				url: '${ctx}/sys/catvalType/ShowCatvalTypeTable.action?WHICH_CATVAL_TYPE='+WHICH_CATVAL_TYPE,
				columns:[[
				    {field:'CATG_TYPE_ID',title:'代码',width:0.1,align:'left' ,hidden:true,sortable:true},
					{field:'CATG_TYPE',title:'分类码类型',width:0.2,align:'left' ,sortable:true,editor:{type:'text'}}

					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
				 onDblClickCell:function(rowIndex, field, value){ 
					 var CATG_TYPE_ID = $('#CatvalTypeTable').datagrid('getSelected').CATG_TYPE_ID;
	               $('#CatvalTypeTable').datagrid('beginEdit', rowIndex); 
	             var ed = $('#CatvalTypeTable').datagrid('getEditor', { index: rowIndex, field: field });
	                  $(ed.target).focus().bind('blur', function () {
                         var result = GetXmlBySql("update  cm_cattype set "+field+" = '"+$(ed.target).val()+"' where CATG_TYPE_ID ='"+CATG_TYPE_ID+"' ");
                   $('#CatvalTypeTable').datagrid('endEdit', rowIndex);
                      });
				 }
				
			});
        	 
		  	});
			
			  function AddRecord(){
				  var WHICH_CATVAL_TYPE = $("#WHICH_CATVAL_TYPE").val();
				  var id = Page_GetGUID();

				 var result = GetXmlBySql("insert into cm_cattype(wid, catg_type_id, catg_type, which_catval_type)values('"+Page_GetGUID()+"','"+id+"','请填写类型','"+WHICH_CATVAL_TYPE+"')");
			     if(result[0].result=="success"){
			    		 $('#CatvalTypeTable').datagrid('appendRow',{
						CATG_TYPE_ID: id,
						CATG_TYPE: '请填写类型'
					});
			    		 GetIndexFrame(currFrameId).location.reload();
			     }
			  }
			function DelRecord(){
				 var currFrameId = "${param.currFrameId}";
				 var row = $('#CatvalTypeTable').datagrid('getSelected');
              if(isNull(row)){
            	  $.messager.confirm("提醒","确定要删除?",function(res){
            		  if(res){
          		         var result =   IntoActionByUrl("${ctx}/sys/catvalType/DelCatvalType.action?CATG_TYPE_ID="+row.CATG_TYPE_ID)
		                 if(result[0].result=="success"){
		            	  var rowIndex = $('#CatvalTypeTable').datagrid('getRowIndex', row);
		                 $('#CatvalTypeTable').datagrid('deleteRow', rowIndex);  
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
		<table id="CatvalTypeTable" class="TreeClass" ></table>
		</div>
					<div class="BottomDiv">
					<a href="###" class="btn_01" onclick="AddRecord();">增加<b></b></a>
				<a href="###" class="btn_01" onclick="DelRecord();">删除<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
		<input type="hidden" id="WHICH_CATVAL_TYPE" value="${param.WHICH_CATVAL_TYPE}"/>
	</body>

</html>