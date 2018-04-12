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

function collent(obj){
	
    createSimpleWindow("scoreWindow","楼盘收藏明细","${ctx}/web/LOUPAN/LPCOLLENT/FS.jsp?uid="+obj,1000,600);
}
function dingyue(obj){
	
    createSimpleWindow("scoreWindow","楼盘订阅明细","${ctx}/web/LOUPAN/LPDINGYUE/FS.jsp?uid="+obj,1000,600);
}
function jilu(obj){
	
    createSimpleWindow("scoreWindow","楼盘点赞明细","${ctx}/web/LOUPAN/LPJILU/FS.jsp?uid="+obj,1000,600);
}
function share(obj){
	
    createSimpleWindow("scoreWindow","楼盘分享明细","${ctx}/web/LOUPAN/LPSHARE/FS.jsp?uid="+obj,1000,600);
}
function foot(obj){
	
    createSimpleWindow("scoreWindow","楼盘点击明细","${ctx}/web/LOUPAN/LPFOOT/FS.jsp?uid="+obj,1000,600);
}
		
         $(function(){
        	 
        	 var isdel = '${param.isdel}';      
        	 var title = '${param.title}';
        	 var type = '${param.type}';
        	 var shen='${param.shen}';
        	 var shi='${param.shi}';
        	 var qu='${param.qu}';
        	 var zdorqx='${param.zdorqx}';        	 
        	 
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
				url: "${ctx}/prosale/tprosale/findAllProsale.action?isdel="+isdel+"&title="+title+"&type="+type+"&shen="+shen+"&shi="+shi+"&qu="+qu+"&zdorqx="+zdorqx,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'id',checkbox:true},
					
					{field:'title',title:'标题',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'unit_price',title:'每平方米价格(元)',width:0.2,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
// 		                   	if(row.type==2)
// 		                   		return "<a href='"+row.contentandurl+"'>"+row.contentandurl+"</a>";
// 		                   	if(row.type==1)
// 		                   		return "<a onclick='look("+row.id+")'>服务文本查看</a>";
// 		                   	}
					},
					{field:'selling_price',title:'起售价(万)',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'avg_price',title:'均价(元)',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'shen',title:'省',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'shi',title:'市',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'qu',title:'区',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'zx',title:'装修',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==1)
		                   		return "精装";
		                   	if(value==2)
		                   		return "毛坯";
		                   	if(value==3)
		                   		return "简装";
		                   	}
					},
					{field:'type',title:'推荐状态',width:0.15,align:'center' ,sortable:true,
						formatter:function(value,row,index){
// 							return "<select class='form_text'  >"
// 							+"<option value='0'<c:if test='"+row.type+"==0'>selected='selected'</c:if>>正常 </option>"
// 							+"<option value='1'<c:if test='"+row.type+"==1'>selected='selected'</c:if>>首页推荐</option>"							
// 							+" </select>";
		                   	if(row.type==0)
		                   		return "<span style='color:green'>正常</span>";
		                   	if(row.type==1)
		                   		return "<span style='color:red'>首页推荐</span>";
		                   	}
					},
					
					{field:'status',title:'楼盘状态',width:0.15,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==1)
		                   		return "<span style='color:#FF8000'>待售</span>";
		                   	if(value==2)
		                   		return "<span style='color:green;'>在售</span>";
		                   	if(value==3)
		                   		return "<span style='color:red;'>售罄</span>";
		                   	}
					},
					{field:'isgift',title:'转发',width:0.15,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==0)
		                   		return "<span style='color:red;'>无礼物</span>";
		                   	if(value==1)
		                   		return "<span style='color:green;'>有礼物</span>";
						}
		                   	
					},
					{field:'shouchangnum',title:'收藏量',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							
								return "<a style='cursor:pointer;color:#CD5C5C;' onclick='collent("+row.id+")'>"+value+"</a>";
						
		                   	}
					},
// 					{field:'dy',title:'订阅',width:0.1,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
							
// 							return "<a style='cursor:pointer;color:#CD5C5C;' onclick='dingyue("+row.id+")'>"+value+"</a>";
					
						
// 	                   	}
// 					},
					{field:'clicks',title:'点击量',width:0.1,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){							
// 							return "<a style='cursor:pointer;color:#CD5C5C;' onclick='foot("+row.id+")'>"+value+"</a>";					
						
// 	                   	}
	
					},
					{field:'forwards',title:'转发量',width:0.1,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
							
// 							return "<a style='cursor:pointer;color:#CD5C5C;' onclick='share("+row.id+")'>"+value+"</a>";					
						
// 	                   	}
					},
					{field:'point',title:'点赞量',width:0.1,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
							
// 							return "<a style='cursor:pointer;color:#CD5C5C;' onclick='jilu("+row.id+")'>"+value+"</a>";					
						
// 	                   	}
					},
					{field:'ordertime',title:'置顶时间',width:0.30,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value=='1990-01-01 00:00:00')
		                   		return "<span style='color:black;'>--</span>";
		                   	else
		                   		return "<span style='color:red;'>"+value+"</span>";
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