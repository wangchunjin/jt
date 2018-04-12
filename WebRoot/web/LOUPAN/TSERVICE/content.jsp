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
		
    createSimpleWindow("lookWindow","服务文本查看","${ctx}/tservice/ttservice/findById.action?cid="+obj, 1000,600);
}
		
         $(function(){ 
//         	 $("#proBody").css("height",$(window).height()-40);

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
				url: "${ctx}/tservice/ttservice/findAllTservice.action?isdel="+isdel,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'id',checkbox:true},
					{field:'pic',title:'图片',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	return "<img width='100px'id='imghead' heigth='130px' src ='${ctx}"+value+"' />";
		                   	}
					},
					{field:'statusname',title:'服务名称',width:0.2,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
// 		                   	if(value==1)
// 		                   		return "服务理念";
// 		                   	if(value==2)
// 		                   		return "服务文化";
// 		                   	if(value==3)
// 		                   		return "专车看房";
// 		                   	if(value==4)
// 		                   		return "金牌顾问";
// 		                   	if(value==5)
// 		                   		return "楼盘分析";
// 		                   	}
					},
					{field:'type',title:'状态',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==2)
		                   		return "服务链接";
		                   	if(value==1)
		                   		return "服务文本";
		                   	}
					},
					{field:'contentandurl',title:'内容',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(row.type==2)
		                   		return "<a href='#' onclick=\"showbaidu('"+value+"')\">"+value+"</a>";
		                   	if(row.type==1)
		                   		return "<a onclick='look("+row.id+")'>服务文本查看</a>";
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