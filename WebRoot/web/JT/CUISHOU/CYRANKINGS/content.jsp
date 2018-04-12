<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>
function open_win(url, title, w, h) {
    var iWidth = w;
    var iHeight = h;
    var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
    myWindow  = window.open(url, title, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
 }
 
 function showbaidu(url){
	 open_win(url,"链接","1000","600");
 }
function look(obj){
		
    createSimpleWindow("lookWindow","广告文本查看","${ctx}/adv/tadv/findById.action?cid="+obj, 1000,600);
}

         $(function(){         
        	 var cuid='${param.cuid}';
        	 var start_time = '${param.start_time}';
        	 var end_time = '${param.end_time}';
        	 
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
				url: "${ctx}/orderinfo/torderinfo/Cyrankings.action?cuid="+cuid+"&start_time="+start_time+"&end_time="+end_time,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'user_id',checkbox:true},
					{field:'name',title:'机构名称',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'sum',title:'订单总数',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'daisum',title:'催收中订单数',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'success',title:'催收成功订单数',width:0.2,align:'center' ,sortable:true,
						
					},
					
					{field:'fail',title:'催收失败订单数',width:0.2,align:'center' ,sortable:true,
						
					},
					
					{field:'percent',title:'成功百分比',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'money',title:'催收总金额',width:0.2,align:'center' ,sortable:true,
						
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