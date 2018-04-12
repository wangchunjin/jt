<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加建设单位</title>
			
		<script>
		 
 
        
        $(function(){ 
        	var projId = $("#projId").val();
        	$('#AddVnmtTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/cb/cb_details/showVnmtTable.action?projId='+projId+'&areaName=${param.areaName}',
				frozenColumns:[[
				    {field:'VNMT_ID',checkbox:true}
				]],
				columns:[[
					{field:'ROLE_ID',title:'项目角色 ',width:0.2,align:'left' ,sortable:true,
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
					{field:'COMPANY_ABBREV',title:'公司缩写 ',width:0.3,align:'left' ,sortable:true,
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
					{field:'COMPANY_NAME',title:'公司名称   ',width:0.3,align:'left' ,sortable:true,
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
	             })

				
				 }
 
				
			});
        	 
		  	});
			
		 
		 function AddRecord(){
			 var currFrameId = '${param.currFrameId}';
			 var projId = $("#projId").val();
    	 var objs = $('#AddVnmtTable').datagrid("getChecked");
    	 if(objs.length==0){
    		 $.messager.alert("提醒","请选择记录！");
    	 }else{
    		 var VnmtIds  = "";
    		 for(var i=0;i<objs.length;i++){
    			   VnmtIds = isStringNull(VnmtIds) ? VnmtIds +","+objs[i].VNMT_ID : objs[i].VNMT_ID ;
	    		   }
    		 	var res = IntoActionByUrl("${ctx}/sys/proj/AddVnmt.action?projId="+projId+"&VnmtIds="+VnmtIds);
	    		if(res[0].result=="success"){
	    			 GetIndexFrame(currFrameId).FourFrame.location.reload(); 
	    			 parent.close_win('addWindow');
	    		}
    	 }
         
		}
		function searchName(){
			var currFrameId = '${param.currFrameId}';
			var areaName = $("#areaName").val();
			window.location.href = "${ctx}/web/SYS/PROJ/COMPANY/AddCompany.jsp?areaName="+areaName+"&currFrameId="+currFrameId;
		}
        </script>
	</head>
	<body>
		<table style="width: 100%;border: 0px">
			<tr>
				<td style="width: 30px"></td>
				<td>
					公司缩写或名称关键字搜索：<input id="areaName" value="${param.areaName}" type="text" onchange="searchName()"/>
				</td>
			</tr>
		</table>
		<div   class="page_content" style="bottom: 40px;top: 30px">
			<table id="AddVnmtTable" class="TreeClass" ></table>
		</div>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
		<input type="hidden" id="projId" value="${param.projId}"/>
	</body>

</html>