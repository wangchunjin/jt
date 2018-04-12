<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
 
         $(function(){ 
        	  var type = parent.$('input:radio[name=msgType]:checked').val();   
        	  var beginDate =parent.$("#begin").val();
        	  var endDate =parent.$("#end").val();
        	  var beginDate ='${param.beginDate}';
        	  var userId ='${UserInfo.getUserId()}';
        	   
         	$('#msgTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,      
                singleSelect:true,
			    pagination:true,//分页控件 
                rownumbers:true,//行号 
                pageSize: 20,//每页显示的记录条数，默认为10 
		        pageList: [20,30,40],//可以设置每页记录条数的列表 
		        url: "${ctx}/sys_ent/appMsg/GetHistory.action?type="+type+"&userId="+userId+"&beginDate="+beginDate+"&endDate="+endDate,		        
				frozenColumns:[[
				    {field:'WID',checkbox:false,hidden:true}
				]],
				columns:[[
					{field:'TITLE',title:'标题',width:0.1,align:'left' ,sortable:true,
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
					{field:'CONTENT',title:'内容 ',width:0.3,align:'left' ,sortable:true,
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
					{field:'CREATED_TIME',title:'创建日期',width:0.1,align:'center' ,sortable:true}
					     
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 }, 
				
			});
        	 
		  	});
			
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="msgTable" class="TreeClass" ></table>
		</div>
   </body>

</html>