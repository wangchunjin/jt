<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>
function jilu(obj){
	
    createSimpleWindow("CountWindow","购车记录","${ctx}/web/CUSER/MYCAR/FS.jsp?uid="+obj,1000,600);
}
function insurance(obj){
	
    createSimpleWindow("InsuranceWindow","我的车险","${ctx}/web/CUSER/INSURANCE/FS.jsp?uid="+obj,1000,600);
}
function score(obj){
	
    createSimpleWindow("scoreWindow","积分明细","${ctx}/web/CUSER/SCORE/FS.jsp?uid="+obj,1000,600);
}
         $(function(){
        	 var title = '${param.title}';
        	 var isdel = '${param.isdel}';
        	 var status = '${param.status}';
        	 var uid='${param.uid}';
        	 var realname='${param.realname}';
        	 
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
                pageSize: 10,
                pageList: [10,20,30],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/user/tuser/findAllUser.action?title="+encodeURI(title)+"&isdel="+isdel+"&status="+status+"&uid="+uid+"&type=0"+"&realname="+realname,        
		frozenColumns:[[
				]],
				
				columns:[[
					//{field:'id',hidden:true},
					{field:'id',checkbox:true},
// 					{field:'nickname',title:'用户昵称',width:0.2,align:'center' ,sortable:true,
						
// 					},
					{field:'uuid',title:'用户编号',width:0.05,align:'center' ,sortable:true,
						
					},
					{field:'realName',title:'用户姓名',width:0.05,align:'center' ,sortable:true,
						
					},
					{field:'ico',title:'头像',width:0.15,align:'center' ,sortable:true,
					formatter:function(value,row,index){
		                   	return "<img  style='height:60px;'  src ='http://120.27.215.48:8081/df/"+value+"' />";
		                   	}
					},
					{field:'telephone',title:'手机号',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'sex',title:'性别',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							if(value==null)
								return "--";
							else
								return value;
							
						}
					},
					{field:'brithdate',title:'生日',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							if(value==null)
								return "--";
							else
								return value;
							
						}
					}
					,

// 					{field:'signcount',title:'签到天数',width:0.1,align:'center' ,

// 					},
					{field:'scorecount',title:'积分(点击查看明细)',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							if(value==null){
								return "<a onclick='score("+row.id+")'>"+0+"</a>";
							}else{
								return "<a onclick='score("+row.id+")'>"+row.scorecount+"</a>";
							}
		                   	
							
							
		                   	}
					},
					{field:'createtime',title:'创建时间',width:0.1,align:'center' ,

	 					}
// 					,
// 					{field:'jl',title:'购车记录',width:0.1,align:'center' ,
// 						formatter:function(value,row,index){
// 		                   	return "<input type='button' onclick='jilu("+row.id+")'  value='购车记录' />";
// 		                   	}
// 					}
// 					,
// 					{field:'cx',title:'我的车险',width:0.1,align:'center' ,
// 						formatter:function(value,row,index){
// 		                   	return "<input type='button' onclick='insurance("+row.id+")'  value='我的车险' />";
// 		                   	}
// 					}
					
					
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