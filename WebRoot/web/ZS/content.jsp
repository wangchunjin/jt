<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>
function look(obj){
		
    createSimpleWindow("lookWindow","资讯文本查看","${ctx}/cons/tcons/findById.action?cid="+obj, 1000,600);
}
		
         $(function(){         
        	 
        	 var month='${param.month}';
        	 var pinyin='${param.pinyin}';
        	 var quyu='${param.quyu}';
        	 
        	 
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
				url: "${ctx}/zs/tzs/findAllZs.action?month="+month+"&pinyin="+pinyin+"&quyu="+quyu,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'ids',checkbox:true},
					{field:'fangxing',title:'房型',width:0.2,align:'center' ,sortable:true,
						
					},
// 					{field:'pic',title:'图片',width:0.2,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
// 		                   	return "<img width='100px'id='imghead' heigth='130px' src ='${ctx}"+value+"' />";
// 		                   	}
// 					},
					{field:'mouth',title:'月份',width:0.2,align:'center' ,sortable:true,
						
					},

					{field:'price',title:'单价',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'pricestr',title:'单价单位',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'huanbi',title:'环比',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'tongbi',title:'同比',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'cityname',title:'城市名称',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'quyuname',title:'区域名称',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'didian',title:'地点',width:0.2,align:'center' ,sortable:true,
						
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