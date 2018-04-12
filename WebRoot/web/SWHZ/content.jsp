<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>

         $(function(){       	
        	 var isdel = '${param.isdel}';
        	 var type='${param.type}';
        	 var status='${param.status}';
        	 
        	 
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
				url: "${ctx}/swhz/tswhz/findAllSwhz.action?isdel="+isdel+"&type="+type+"&status="+status,        
				frozenColumns:[[
				]],
				
				columns:[[
					{field:'id',checkbox:true},	
					{field:'userid',title:'用户编号',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'realName',title:'用户姓名',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'phone',title:'手机号码',width:0.1,align:'center' ,sortable:true,
						
					},	
					{field:'type',title:'合作类别',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(row.type==1)
		                   		return "项目合作";
		                   	if(row.type==2)
		                   		return "城市合伙人";
		                   	if(row.type==3)
		                   		return "金牌咨询加盟";
		                   	if(row.type==4)
		                   		return "广告投放";
		                   	}
						
					},	
					{field:'status',title:'处理状态',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(row.status==0)
		                   		return "<span style='color:green;'>未处理</span>";
		                   	if(row.status==1)
		                   		return "<span style='color:red;'>已处理</span>";
		                   	
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