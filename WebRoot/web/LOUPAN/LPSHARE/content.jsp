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
        	
        	 var oid='${param.uid}';
        	 var realname='${param.realname}';
        	 var telephone='${param.telephone}';
        	 
        	 
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
                pageSize: 15,
                pageList: [15,30,45],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/share/tshare/findAllShare.action?isdel="+isdel+"&oid="+oid+"&status=1&realname="+realname+"&telephone="+telephone,        
				frozenColumns:[[
				]],
				
				columns:[[	
					{field:'id',checkbox:true},
					{field:'uid',title:'用户id',width:0.2,align:'center' ,sortable:true,
						
						
					},					
					{field:'realName',title:'用户姓名',width:0.2,align:'center' ,sortable:true,
						
					},					
					{field:'telephone',title:'手机号',width:0.2,align:'center' ,sortable:true,
						
		                   
					},					
					{field:'createtime',title:'生成时间',width:0.2,align:'center' ,sortable:true,
						    
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