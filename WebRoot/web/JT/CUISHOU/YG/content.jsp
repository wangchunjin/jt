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
	
// 	if(rows.length==0){
// 		parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要逾期订单！");
// 		return;
// 	}
	

	createSimpleWindow("CuishouWindow","","${ctx}/web/JT/CUISHOU/YG/show.jsp?currFrameId="+currFrameId+"&id="+rows[0].order_id+"&cid="+rows[0].cid, 1200,600);
	
};
         $(function(){
         
        	
        	 var isdel = '${param.isdel}';
//         	 var approve_status = '${param.approve_status}';
        	 var collection = '${param.collection}';
        	var yg_name='${sessionScope.UserInfo.userId}';
        	var zgtime='${param.zgtime}';
        	var jgtime='${param.jgtime}';
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
                pageList: [5,10,15,30,45],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/orderinfo/torderinfo/findAllOrderinfo_yg.action?isdel="+isdel+"&approve_status=1&transfer_status=3"+"&fp_jg=1&yg_name="+yg_name+"&collection="+collection+"&zgtime="+zgtime+"&jgtime="+jgtime+"&mobile="+mobile,        
				
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
					{field:'amount',title:'借款金额(元)',width:0.1,align:'center' ,sortable:true,

						
					},
					{field:'period',title:'周期(天)',width:0.05,align:'center' ,sortable:true,
						
					},
					{field:'interest',title:'利息(元)',width:0.05,align:'center' ,sortable:true,
						
					},
// 					{field:'real_amt',title:'实际到账金额(元)',width:0.1,align:'center' ,sortable:true,
						
// 					},
// 					{field:'rel_money',title:'实际还款金额(元)',width:0.1,align:'center' ,sortable:true,						
// 					},
					{field:'repay_money',title:'应还款金额(元)',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'overdue_payment_the',title:'滞纳金(元)',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'overdue_payment_rel',title:'实际滞纳金(元)',width:0.1,align:'center' ,sortable:true,
						
					},

					
					{field:'collection',title:'催收状态',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==0)
		                   		return "<span style='color:#EEAD0E;'>未催收</span>";
		                   	if(value==1)
		                   		return "<span style='color:green;'>已催收</span>";	                   	
		                   	}
					},
					{field:'zg_time',title:'主管分配时间',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==null || value=="")
		                   		return "--";
		                   	else 
		                   		return value;	                   	
		                   	}
					},
					{field:'jg_time',title:'机构分配时间',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==null || value=="")
		                   		return "--";
		                   	else 
		                   		return value;
		                   	
		                   	
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
		                },
		                onClickRow:function(node){
		    				var where = node;
// 							alert(where);
// 								parent.OneFrame.$('#NewRecord').css("display","none");
								parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=CSYG&Where="+where;	
							
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