<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>

<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script type="text/javascript" src="js/mouseoverandout.js"></script>
<script>
function look(obj){
		
    createSimpleWindow("lookWindow","服务文本查看","${ctx}/prosale/tprosale/findById.action?cid="+obj, 1000,600);
}
		
         $(function(){
        	 var lid='${param.lid}';
        	 
        	 var isdel = '${param.isdel}';     
//         	 var type= '${param.type}';   
        	 
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
				url: "${ctx}/house/thouse/findAllHouse.action?isdel="+isdel+"&lid="+lid,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'id',checkbox:true},
					
					{field:'name',title:'名称',width:0.2,align:'center' ,sortable:true,
						
					},					
					{field:'area',title:'面积(平方米)',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'price',title:'价格(万元/套)',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'istuijian',title:'推荐状态',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(row.istuijian==0)
		                   		return "<span style='color:green'>正常</span>";
		                   	if(row.istuijian==1)
		                   		return "<span style='color:red'>已推荐</span>";		                   
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