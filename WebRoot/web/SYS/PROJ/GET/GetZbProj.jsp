<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
 
         $(function(){ 
        	 var ress = GetXmlBySql( "SELECT MAX(ID) ID FROM CM_CODE WHERE CODE_TYPE='TBJG_STATUS'   AND  code_name  = '中标'");
         	 var where="AND (LINK_PROJ NOT IN (SELECT PROJ_ID FROM CM_PROJ)  OR  LINK_PROJ IS NULL OR LINK_PROJ=@@@@)";
        	 var status = ress[0].ID;
         	$('#tbjgtable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,      
                singleSelect:true, 
			    pagination:true,//分页控件 
			    queryParams:{where:where},
                rownumbers:true,//行号 
                pageSize: 200,//每页显示的记录条数，默认为10 
		        pageList: [200,500,100000],//可以设置每页记录条数的列表 
		        url: "${ctx}/jygl/jygl_tbjg/tbjgtable.action?status="+status,
				frozenColumns:[[
				    {field:'TBJG_ID',checkbox:true,hidden:true}
				]],
				columns:[[
					{field:'PROJ_NAME',title:'项目名称',width:0.3,align:'left' ,sortable:true,
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
					{field:'COMPANY',title:'业主名称',width:0.2,align:'left' ,sortable:true,
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
					{field:'GROSS_INVEST',title:'项目总投资(万元)',width:0.1,align:'center' ,sortable:true,
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
					{field:'KB_DATE',title:'开/投标时间',width:0.1,align:'center' ,sortable:true,
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
					{field:'ZB_TYPE',title:'招标方式',width:0.1,align:'center' ,sortable:true,
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
					,
					{field:'TB_DEPT',title:'投标部门',width:0.1,align:'left' ,sortable:true,
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
					{field:'GZ_ID',title:'跟踪ID',width:0.1,align:'center' ,sortable:true,hidden:true},
					{field:'TB_ID',title:'拟派人员ID',width:0.1,align:'center' ,sortable:true,hidden:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 } 			
			});
        	 
		  	});
			
         
         function AddRecord(){
			 
			 
	     	 var node =  $('#tbjgtable').datagrid("getSelected");
	     	  if(!isNull(node)){
	     	    	$.messager.alert("错误","请选中行！");
	     	    	return;
	     	    }
	     	   
                parent.OneFrame.nextstep('1',node.TBJG_ID+","+node.PROJ_NAME);
			  }
         
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom:40px;" >
		<table id="tbjgtable" class="TreeClass" ></table>
		</div>
					<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">下一步<b></b></a> 			
			</div>
   </body>

</html>