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
         $(function(){
         
        	
        	 var client_id = '${param.client_id}';
        	 var order_number = '${param.order_number}';
        	 var createtime = '${param.createtime}';
        	
        	 
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
				url: "${ctx}/contract/tcontract/findAllContract.action?client_id="+client_id+"&order_number="+order_number+"&createtime="+createtime,        
				frozenColumns:[[
				]],
				
				columns:[[
					{field:'id',checkbox:true},
						
					{field:'client_id',title:'用户编号',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'client_number',title:'法大大生成编号',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'order_number',title:'订单编号',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'contract_number',title:'法大大生成合同编号',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'download_url',title:'下载地址',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							return "<a href='"+value+"' \">"+value+"</a>";
		                   	}
					},
					{field:'viewpdf_url',title:'查看地址',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							return "<a href='#' onclick=\"showbaidu('"+value+"')\">"+value+"</a>";
		                   	}
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