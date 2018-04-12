<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>

<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script type="text/javascript" src="js/mouseoverandout.js"></script>
<script>
// function look(obj){
		
//     createSimpleWindow("lookWindow","服务文本查看","${ctx}/prosale/tprosale/findById.action?cid="+obj, 1000,600);
// }
		
         $(function(){
        	 
        	 var lid='${param.lid}';        	 
        	 var isdel = '${param.isdel}';   
        	
        	 
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
				url: "${ctx}/housepic/thousepic/findAllHousepic.action?isdel="+isdel+"&hid="+lid,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'id',checkbox:true},
					
					{field:'pic',title:'图片',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	return "<img width='100px'id='imghead' heigth='130px' src ='${ctx}"+value+"' />";
		                   	}
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