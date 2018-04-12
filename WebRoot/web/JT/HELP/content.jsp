<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>
function look(){
	   
	var currFrameId =  parent.frameElement.id;
	var rows = $('#CarTable').datagrid('getSelections');			
	
	if(rows.length==0){
		parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的帮助详情！");
		return;
	}
	createSimpleWindow("eidtWindow","帮助详情","${ctx}/help/thelp/findById.action?id="+rows[0].id+"&see=1"+"&currFrameId="+currFrameId,800,600);
	
}
         $(function(){
         
        	
        	 var isdel = '${param.isdel}';
        	
        	 
         	$('#CarTable').datagrid({
         	remoteSort:false,
                fitColumns:true,
                striped:true,
                selectOnCheck:false,
                autoRowHeight:true,
                checkOnSelect:true,
                singleSelect:true,
                remoteSort:false,
                rownumbers:false,
                pagination: true,
                pageSize: 15,
                pageList: [15,30,45],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/help/thelp/findAllHelp.action?isdel="+isdel,
				frozenColumns:[[
				]],
				onDblClickRow:function() {
// 					双击某行触发查看事件
                	look();
            	}, 
				columns:[[
					{field:'id',checkbox:true},
					{field:'orderflag',title:'排序数字',width:0.05,align:'center' ,sortable:true,
						
					},
					{field:'title',title:'帮助标题',width:0.1,align:'center' ,sortable:true,
						
					},				
					{field:'content',title:'内容',width:0.1,align:'center' ,sortable:true,
						
					},					
					{field:'createtime',title:'创建时间',width:0.05,align:'center' ,sortable:true,
						
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
// 						 , rowStyler:function(index,row){   //修改easyui 行的样式
// 		                    if (row.value=="15"){   
// 		                        return 'background-color:pink;';   
// 		                    }  
// 		                    if (row.value=="14"){   
// 		                        return 'background-color:yellow;';   
// 		                    }  
// 		                }   
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