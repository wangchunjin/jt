<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>
function a(){
	   
	var currFrameId =  parent.frameElement.id;
	var rows = $('#CarTable').datagrid('getSelections');			
	
	if(rows.length==0){
		parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的用户！");
		return;
	}
	createSimpleWindow("eidtWindow","订单信息展示","${ctx}/orderinfo/torderinfo/findById.action?id="+rows[0].order_id+"&currFrameId="+currFrameId, 1200,600);
	
}

         $(function(){
         
        	
        	 var isdel = '${param.isdel}';
        	 var approve_status = '${param.approve_status}';
        	 var transfer_status = '${param.transfer_status}';
        	 var mobile='${param.mobile}';
        	
        	 
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
				url: "${ctx}/orderinfo/torderinfo/findAllOrderinfo.action?isdel="+isdel+"&approve_status="+approve_status+"&transfer_status="+transfer_status+"&mobile="+mobile,        
				onDblClickRow:function() {
// 					双击某行触发查看事件
                	a();
            	}, 
				
				columns:[[
					{field:'order_id',checkbox:true},
					{field:'orderid',title:'订单编号',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'client_id',title:'借款用户',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'mobile',title:'手机号',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'lend_id',title:'出借人',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'amount',title:'借款金额(元)',width:0.1,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
// 		                   	if(value==0)
// 		                   		return "女";
// 		                   	if(value==1)
// 		                   		return "男";
		                   	
// 		                   	}
						
					},
					{field:'period',title:'周期(天)',width:0.05,align:'center' ,sortable:true,
						
					},
					{field:'interest',title:'利息(元)',width:0.05,align:'center' ,sortable:true,
						
					},
					{field:'rel_money',title:'实际还款金额(元)',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'overdue_payment_the',title:'滞纳金(元)',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'overdue_payment_rel',title:'实际滞纳金(元)',width:0.1,align:'center' ,sortable:true,
						
					},
// 					{field:'real_amt',title:'实际到账金额(元)',width:0.1,align:'center' ,sortable:true,
						
// 					},
// 					{field:'pur_id',title:'借款用途',width:0.1,align:'center' ,sortable:true,
						
// 					},
					{field:'approve_status',title:'审核状态',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==0)
		                   		return "<span style='color:#EEAD0E;'>待审核</span>";
		                   	if(value==1)
		                   		return "<span style='color:green;'>通过</span>";
		                   	if(value==2)
		                   		return "<span style='color:red;'>失败</span>";
		                   	
		                   	}
					},
					{field:'transfer_status',title:'打款状态',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							if(row.approve_status==1){
								if(value==0)
			                   		return "<span style='color:#EEAD0E;'>待打款</span>";
			                   	if(value==1)
			                   		return "<span style='color:#8A2BE2;'>还款中</span>";
			                   	if(value==2)
			                   		return "<span style='color:green;'>正常还款</span>";
			                   	if(value==3)
			                   		return "<span style='color:red;'>逾期</span>";
			                   	if(value==4)
			                   		return "<span style='color:#436EEE;'>逾期还款</span>";
			                   	if(value==5)
			                   		return "<span style='color:#8B6969;'>异常订单</span>";
			                   	if(value==6)
			                   		return "<span style='color:#DA70D6;'>未确认</span>";
			                   	if(value==7)
			                   		return "<span style='color:#DA70D6;'>打款异常</span>";
                                if(value==8)
                                    return "<span style='color:#DA70D6;'>待财务确认</span>";
							}else{
								return "--";
							}
		                   	
		                   	
		                   	}
					},
					{field:'createtime',title:'创建时间',width:0.1,align:'center' ,sortable:true,
						
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