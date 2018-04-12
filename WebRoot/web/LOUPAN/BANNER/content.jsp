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
    var iTop = (window.screen.availHeight-30-iHeight)/2;          //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
    myWindow  = window.open(url, title, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
 } 
 function showbaidu(url){ 
	 open_win(url,"链接","1000","600");
 }
function look(obj){		
    createSimpleWindow("lookWindow","轮播文本查看","${ctx}/banner/tbanner/findById.action?cid="+obj, 1000,600);
}
		
         $(function(){       	 
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
				url: "${ctx}/banner/tbanner/findAllBanner.action?isdel="+isdel,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'id',checkbox:true},
					{field:'pic',title:'图片',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	return "<img id='imghead' height='70px' src ='${ctx}"+value+"' />";
		                   	}
					},
					{field:'type',title:'状态',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==2)
		                   		return "首页轮播链接";
		                   	if(value==1)
		                   		return "首页轮播文本";
		                   	if(value==3)
		                   		return "楼盘";
		                   	if(value==4)
		                   		return "户型";
		                   	}
					
					},
					{field:'contentandurl',title:'内容',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(row.type==2)
		                   		return "<a href='#' onclick=\"showbaidu('"+value+"')\">"+value+"</a>";
		                   	if(row.type==1)
		                   		return "<a onclick='look("+row.id+")'>首页轮播文本查看</a>";
		                   	if(row.type==3)
		                   		return "--";
		                   	if(row.type==4)
		                   		return "--";
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