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
        	 var type='${param.type}';
        	
        	 
         	$('#CarTable').datagrid({
         	remoteSort:false,
                fitColumns:true,
                striped:true,
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
                autoRowHeight:true,
                remoteSort:false,                
                rownumbers:false,
                pagination: true,
                pageSize: 10,
                pageList: [10,20,30],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/telephones/ttelephones/findAllTelephones.action?isdel="+isdel+"&type="+type,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'id',checkbox:true},
					
					{field:'type',title:'服务类别',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							if(value==1)
								return "客服电话";
							if(value==2)
								return "合作电话";
							if(value==3)
								return "售后电话";
		                   
		                   	}
					
					},
					{field:'telephone',title:'电话',width:0.2,align:'center' ,sortable:true,
						
					},
					
					{field:'createtime',title:'创建时间',width:0.2,align:'center' ,sortable:true,
						
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