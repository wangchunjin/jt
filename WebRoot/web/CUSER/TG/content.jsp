<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>

         $(function(){
        	 
        	 var id='${param.id}';
//         	 alert(id);
        	
        	 
         	$('#CarTable').datagrid({
         		remoteSort:false,
                fitColumns:true,
                striped:true,                 
                selectOnCheck:false,
                autoRowHeight:true,
                checkOnSelect:true,
                singleSelect:true,
                remoteSort:false,   
                rownumbers:false,
                pagination: true,
                pageSize: 10,
                pageList: [10,20,30],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/user/tuser/findAllUser.action?uuid="+id,        
		frozenColumns:[[
				]],
				
				columns:[[
					//{field:'id',hidden:true},
					{field:'id',checkbox:true},

					{field:'uuid',title:'用户编号',width:0.1,align:'center' ,sortable:true,
						
					},
					
					{field:'telephone',title:'手机号',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'sex',title:'性别',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							if(value==null)
								return "--";
							else
								return value;
							
						}
					},
					{field:'brithdate',title:'生日',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							if(value==null)
								return "--";
							else
								return value;
							
						}
					}

					
					
     				]],
						 onLoadSuccess: function(data){
	 			 	   		$(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                		$(this).attr("title",$(this).html());
			                });
						 },
						 onClickRow:function(rowIndex, node){
		                	var where = node.USERID;
							parent.parent.document.all('FourFrame').src = parent.parent.ThreeFrame.getLabelUrl(where);
		                }
				});
		  	});
        </script>
</head>
<body>
	<div class="page_content">
		<table id="CarTable" class="TreeClass"></table>
	</div>
</body>
</html>