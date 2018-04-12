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
        	 
        	 var isdel = '${param.isdel}';
        	 var oid = '${param.oid}';
        	 
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
				url: "${ctx}/crecord/tcrecord/findAllCrecord.action?isdel="+isdel+"&oid="+oid,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'id',checkbox:true},
					{field:'rep_willingness',title:'还款意愿',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'comm_results',title:'沟通结果',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'contact_book_type',title:'通讯录联系结果',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==1)
		                   		return "没有联系通讯录";
		                   	if(value==2)
		                   		return "只联系紧急联系人";
		                   	if(value==3)
		                   		return "只联系其它联系人";
		                   	if(value==4)
		                   		return "紧急联系人和其它联系人都联系";
		                   	}
					},
					
					{field:'createtime',title:'催收时间',width:0.2,align:'center' ,sortable:true,
						
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