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
        	
        	 var uid='${param.uid}';
        	 
        	 
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
				url: "${ctx}/score/tscore/findAllScore.action?isdel="+isdel+"&uid="+uid,        
				frozenColumns:[[
				]],
				
				columns:[[	
					{field:'id',checkbox:true},
					{field:'status',title:'来源',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(value==1)
	                   			return "签到 ";
	                   		if(value==2)
	                   			return "阅读咨询 ";
	                   		if(value==3)
	                   			return "分享咨询";
	                   		if(value==4)
	                   			return "完成个人信息";
	                   		if(value==5)
	                   			return "分享楼盘";
	                   		if(value==6)
	                   			return "分享抽奖";
	                   		if(value==7)
	                   			return "分享限购查询";
	                   		if(value==8)
	                   			return "分享推广页面";
	                   		if(value==9)
	                   			return "分享问答";
	                   		if(value==10)
	                   			return "抽奖(消耗)";
	                   		if(value==11)
	                   			return "积分兑换";
	                   		if(value==12)
	                   			return "抽奖(奖品)";
	                   		if(value==13)
	                   			return "注册";
	                   		if(value==14)
	                   			return "签到红包";
	                   	}
						
					},					
					{field:'num',title:'积分数量',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return "<span style='color:green;'>+"+value+"</span>";
	                   		if(row.type==0)
	                   			return "<span style='color:red;'>-"+value+"</span>";
	                   	}
					},					
// 					{field:'phone',title:'会员手机号',width:0.2,align:'center' ,sortable:true,
						
		                   
// 					},					
					{field:'createtime',title:'生成时间',width:0.2,align:'center' ,sortable:true,
						    
					},	
					{field:'type',title:'积分状态',width:0.2,align:'center' ,sortable:true,
						
						formatter:function(value,row,index){
		                   		if(value==1)
		                   			return "获取";
		                   		if(value==0)
		                   			return "消费";
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