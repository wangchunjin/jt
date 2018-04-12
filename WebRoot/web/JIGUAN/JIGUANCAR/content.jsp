<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>
	

         $(function(){
        	 var title = '${param.title}';
        	 var isdel = '${param.isdel}';
        	 
        	 
        	 
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
				url: "${ctx}/sysm/tsysm/findAllSysmcar.action?title="+encodeURI(title)+"&isdel="+isdel,        
				frozenColumns:[[
				]],
				
				columns:[[
					//{field:'id',hidden:true},
// 					{field:'id',checkbox:true},
					{field:'title',title:'推送标题',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'content',title:'推送内容',width:0.2,align:'center' ,sortable:true,
						
					}
					,
					{field:'oid',title:'推送车型',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'c_time',title:'推送时间',width:0.2,align:'center' ,sortable:true,
						
					}
					
// 					,
// 					{field:'helps',title:'次问题',width:0.2,align:'center' ,
// 						formatter:function(value,row,index){
// 		                   	return "<input type='button' onclick='helps("+row.id+")'  value='所属次问题' />";
// 		                   	}
// 					}
					
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