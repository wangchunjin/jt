<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>

         $(function(){
         
        	
        	 var isdel = '${param.isdel}';
        	 var shen = '${param.shen}';
        	 var shi = '${param.shi}';
        	
        	 
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
                pageSize: 15,
                pageList: [15,30,45],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/naire/tnaire/findAllNaire.action?isdel="+isdel+"&shen="+shen+"&shi="+shi,        
				frozenColumns:[[
				]],
				
				columns:[[
					{field:'id',checkbox:true},
					{field:'xxx',title:'编号',width:0.04,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   return row.id;
		                   	}
					},
					{field:'question',title:'问题',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'shen',title:'省',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'shi',title:'市',width:0.1,align:'center' ,sortable:true,
											
					},
					{field:'type',title:'初始问题',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(row.type==0)
		                   		return "<span style='color:green'>正常</span>";
		                   	if(row.type==1)
		                   		return "<span style='color:red'>初始问题</span>";
		                   	}
					},
					{field:'createtime',title:'创建时间',width:0.1,align:'center' ,sortable:true,
						
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