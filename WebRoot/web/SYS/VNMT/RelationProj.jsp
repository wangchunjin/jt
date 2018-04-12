<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加分类码</title>
			
		<script>
       $(function(){ 
    	    var vnmtId = "${param.VNMT_ID}";
        	$('#CmProjWorkTree').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,  
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/ds/contact/relationProj.action?vnmtId='+vnmtId,
				frozenColumns:[[
				    {field:'PROJ_ID',checkbox:true}
				]],
				columns:[[
					{field:'PROJ_SHORT_NAME',title:'代码',width:0.15,align:'left' ,sortable:true,
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
					{field:'PROJ_NAME',title:'名称',width:0.2,align:'left' ,sortable:true,
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
					},{field:'SUPERVISOR',title:'监理项目负责人',width:0.08,align:'left' ,sortable:true,
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
					},{field:'SUPERVISOR_PHONE',title:'监理项目负责人电话',width:0.1,align:'left' ,sortable:true,
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
					{field:'PROJ_STAGE',title:'项目类别',width:0.07,align:'center' ,sortable:true,
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
					},{field:'PROJ_CMPT_PCT',title:'形象进度',width:0.15,align:'center' ,sortable:true,
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
					},{field:'DESCRIPTION',title:'工作内容',width:0.45,align:'left' ,sortable:true,
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

			 		});
			 	}
		 	});
	 	});
        </script>
	</head>
	<body> 
        <div   class="page_content" style="position: absolute;width: 100%;bottom:10px; top: 0px;">
		<table id="CmProjWorkTree" class="TreeClass"></table>
		</div>
		<%-- <div   class="" style="position: absolute;width: 100%;height: 30px;bottom: 0px;">
		<table>
			<tr>
				<td style="width: 10px;"></td>
				<td style=""><epmis:button id="addrecord" imageCss="icon-add" value="增加" action="addrecord()" datactrCode="SYS_PROJ.projuser.edit"  ></epmis:button></td>
				<td style=""><epmis:button id="del" imageCss="icon-remove" value="删除" action="del()" datactrCode="SYS_PROJ.projuser.edit"  ></epmis:button></td>
 			</tr>
		</table>
		</div> --%>
	</body>

</html>