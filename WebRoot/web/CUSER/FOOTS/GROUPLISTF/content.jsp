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
        	
        	 var uid='${param.uid}';
        	 var status='${param.status}';
        	 var name='${param.name}';
        	
        	 
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
				url: "${ctx}/foots/tfoots/findAllFoots.action?isdel="+isdel+"&uid="+uid+"&status="+status+"&name="+name,        
				frozenColumns:[[
				]],
				
				columns:[[	
					{field:'id',checkbox:true},
					{field:'uid',title:'用户编号',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(value==null)
	                   			return "--";
	                   		else
	                   			return value;
	                   		
	                   	}
					},	
					{field:'status',title:'行为类别',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(value==1)
	                   			return "楼盘";
	                   		if(value==2)
	                   			return "问答问题 ";
	                   		if(value==3)
	                   			return "资讯";
	                   		if(value==4)
	                   			return "户型";
	                   		
	                   	}
						
					},					
					
					{field:'oid',title:'各类别',width:0.2,align:'center' ,sortable:true,
					
		                   
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