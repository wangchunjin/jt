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
        	 var type='${param.type}';
        	 var qu='${param.qu}';
        	 var price='${param.price}';
        	 var tese='${param.tese}';
        	 var mdid='${param.mdid}';
        	 var hid='${param.hid}';
        	 var area='${param.area}';
        	 var aid='${param.aid}';
        	 
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
				url: "${ctx}/findhouse/tfindhouse/findAllFindhouse.action?isdel="+isdel+"&uid="+uid+"&type="+type+"&qu="+qu+"&price="+price+"&tese="+tese+"&mdid="+mdid+"&hid="+hid+"&area="+area+"&aid="+aid,        
				frozenColumns:[[
				]],
				
				columns:[[	
					{field:'id',checkbox:true},
					{field:'userid',title:'用户编号',width:0.2,align:'center' ,sortable:true,
						 
					},	
					{field:'type',title:'行为类别',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(value==1)
	                   			return "区域 ";
	                   		if(value==2)
	                   			return "价格 ";
	                   		if(value==3)
	                   			return "类型";
	                   		if(value==4)
	                   			return "目的";
	                   		if(value==5)
	                   			return "户型";
	                   		if(value==6)
	                   			return "面积";
	                   		if(value==7)
	                   			return "空闲时间";
	                   		if(value==8)
	                   			return "周边";
	                   		
	                   	}
						
					},					
					{field:'qid',title:'区域',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return value;
	                   		if(row.type==2)
	                   			return "-- ";
	                   		if(row.type==3)
	                   			return "--";
	                   		if(row.type==4)
	                   			return "--";
	                   		if(row.type==5)
	                   			return "--";
	                   		if(row.type==6)
	                   			return "--";
	                   		if(row.type==7)
	                   			return "--";
	                   		if(row.type==8)
	                   			return "--";
	                   		
	                   	}
						 
					},					
					{field:'minprice',title:'最小价格',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return "--";
	                   		if(row.type==2)
	                   			return value;
	                   		if(row.type==3)
	                   			return "--";
	                   		if(row.type==4)
	                   			return "--";
	                   		if(row.type==5)
	                   			return "--";
	                   		if(row.type==6)
	                   			return "--";
	                   		if(row.type==7)
	                   			return "--";
	                   		if(row.type==8)
	                   			return "--";
	                   		
	                   	}
		                   
					},		
					{field:'maxprice',title:'最大价格',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return "--";
	                   		if(row.type==2)
	                   			return value;
	                   		if(row.type==3)
	                   			return "--";
	                   		if(row.type==4)
	                   			return "--";
	                   		if(row.type==5)
	                   			return "--";
	                   		if(row.type==6)
	                   			return "--";
	                   		if(row.type==7)
	                   			return "--";
	                   		if(row.type==8)
	                   			return "--";
	                   		
	                   	}
		                   
					},
					{field:'tid',title:'类型	',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return "--";
	                   		if(row.type==2)
	                   			return "--";
	                   		if(row.type==3)
	                   			return value;
	                   		if(row.type==4)
	                   			return "--";
	                   		if(row.type==5)
	                   			return "--";
	                   		if(row.type==6)
	                   			return "--";
	                   		if(row.type==7)
	                   			return "--";
	                   		if(row.type==8)
	                   			return "--";
	                   		
	                   	}
		                   
					},
					{field:'mdid',title:'目的',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return "--";
	                   		if(row.type==2)
	                   			return "--";
	                   		if(row.type==3)
	                   			return "--";
	                   		if(row.type==4)
	                   			return value;
	                   		if(row.type==5)
	                   			return "--";
	                   		if(row.type==6)
	                   			return "--";
	                   		if(row.type==7)
	                   			return "--";
	                   		if(row.type==8)
	                   			return "--";
	                   		
	                   	}
		                   
					},
					{field:'hid',title:'户型',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return "--";
	                   		if(row.type==2)
	                   			return "--";
	                   		if(row.type==3)
	                   			return "--";
	                   		if(row.type==4)
	                   			return "--";
	                   		if(row.type==5)
	                   			return value;
	                   		if(row.type==6)
	                   			return "--";
	                   		if(row.type==7)
	                   			return "--";
	                   		if(row.type==8)
	                   			return "--";
	                   		
	                   	}
		                   
					},
					{field:'minarea',title:'最小面积',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return "--";
	                   		if(row.type==2)
	                   			return "--";
	                   		if(row.type==3)
	                   			return "--";
	                   		if(row.type==4)
	                   			return "--";
	                   		if(row.type==5)
	                   			return "--";
	                   		if(row.type==6)
	                   			return value;
	                   		if(row.type==7)
	                   			return "--";
	                   		if(row.type==8)
	                   			return "--";
	                   		
	                   	}
		                   
					},
					{field:'maxarea',title:'最大面积',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return "--";
	                   		if(row.type==2)
	                   			return "--";
	                   		if(row.type==3)
	                   			return "--";
	                   		if(row.type==4)
	                   			return "--";
	                   		if(row.type==5)
	                   			return "--";
	                   		if(row.type==6)
	                   			return value;
	                   		if(row.type==7)
	                   			return "--";
	                   		if(row.type==8)
	                   			return "--";
	                   		
	                   	}
		                   
					},{field:'aid',title:'周边',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
	                   		if(row.type==1)
	                   			return "--";
	                   		if(row.type==2)
	                   			return "--";
	                   		if(row.type==3)
	                   			return "--";
	                   		if(row.type==4)
	                   			return "--";
	                   		if(row.type==5)
	                   			return "--";
	                   		if(row.type==6)
	                   			return "--";
	                   		if(row.type==7)
	                   			return "--";
	                   		if(row.type==8)
	                   			return value;
	                   		
	                   	}
		                   
					},					
					{field:'createtime',title:'生成时间',width:0.2,align:'center' ,sortable:true,
						    
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