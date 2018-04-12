<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>
function hf(obj){
		


			
	
	$.messager.confirm("提醒","确定要恢复?",function(res){

    	if(res){
			var res = IntoActionByUrl("${ctx}/car/tcar/hf.action?id="+obj);
			
    		if(res[0].result=="success"){
    			
				$.messager.alert("提醒","恢复成功！");	
	    		location.reload();
			}
		}
	});
}
function color(obj){
 		
    createSimpleWindow("ColorWindow","颜色列表","${ctx}/web/CAR/CARCOLOR/FS.jsp?cid="+obj, 1000,600);
}
function param(obj){
		
    createSimpleWindow("ParamWindow","参数列表","${ctx}/web/CAR/PARAM/FS.jsp?cid="+obj, 1000,600);
}
function promot(obj){
	
    createSimpleWindow("PromotWindow","促销列表","${ctx}/web/CAR/PROMOTION/FS.jsp?cid="+obj, 800,400);
}
function ceping(obj){
		
    createSimpleWindow("PingceWindow","评测列表","${ctx}/web/CAR/PINGCE/FS.jsp?cid="+obj, 1000,600);
}
function kucun(obj){
	
    createSimpleWindow("KucunWindow","库存管理","${ctx}/web/CAR/KUCUN/FS.jsp?cid="+obj, 1000,600);
}



         $(function(){
        	 var title = '${param.title}';
        	 var isdel = '${param.isdel}';
        	 var status = '${param.status}';
        	 var xin = '${param.xin}';
        	 var bid = '${param.bid}';
        	 var jid = '${param.jid}';
        	 var gid = '${param.gid}';
        	 var carsysid='${param.carsysid}';
        	 var type='${param.type}';
        	 var csid='${param.csid}';
        	
        	 
         	$('#CarTable').datagrid({
         		remoteSort:false,
//                 fitColumns:true,
                striped:true,   
               
                selectOnCheck:false,
                autoRowHeight:true,
                checkOnSelect:true,
                singleSelect:true,
                pagination: true,
                pageSize: 10,
                pageList: [10,20,30],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/car/tcar/findAllCar.action?title="+encodeURI(title)+"&isdel="+isdel+"&status="+status+"&xin="+xin+"&bid="+bid+"&jid="+jid+"&gid="+gid+"&carsysid="+carsysid+"&type="+type+"&csid="+csid,        
				frozenColumns:[[
				]],
				columns:[[
					{field:'id',checkbox:true},
					{field:'title',title:'车型名称',width:280,align:'center' ,sortable:true,
						sorter:function(param1,param2){
								if(!isStringNull(param1)){			
									param1 = "";
								}
								if(!isStringNull(param2)){
									param2 = "";
								}
								if(param1.localeCompare(param2)<0){
									return -1;
								}else{
									return 1;
								}
							}
					},
					{field:'ids',title:'车型ID',width:200,align:'center' ,sortable:true,
						
						},
					{field:'pic',title:'主图片',width:200,align:'center' ,sortable:true,
					formatter:function(value,row,index){
		                   	return "<img width='100px' heigth='130px' src ='${ctx}"+value+"' />";
		                   }
					},
					{field:'ico',title:'小图片',width:200,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	return "<img width='100px' heigth='130px' src ='${ctx}"+value+"' />";
		                   	}
					},
					{field:'naked_price',title:'裸车价(万)',width:100,align:'center' ,sortable:true,
							formatter:function(value,row,index){
							
							if(value==null || value=="")
		                   	return "0";
							else
								return value;
							
							   
		                   	}
					},
					{field:'official_price',title:'官方指导价(万)',width:100,align:'center' ,sortable:true,
							formatter:function(value,row,index){
							
							if(value==null || value=="")
		                   	return "0";
							else
								return value;
							
							   
		                   	}
					},
// 					{field:'down_payment',title:'首付(万)',width:100,align:'center' ,sortable:true,
// 							formatter:function(value,row,index){
							
// 							if(value==null || value=="")
// 		                   	return "0";
// 							else
// 								return value;
							
							   
// 		                   	}
// 					},
// 					{field:'monthly_payments',title:'月供(元)',width:100,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
						
// 						if(value==null || value=="")
// 	                   	return "0";
// 						else
// 							return value;
						
						   
// 	                   	}
// 				},
// 				{field:'service_charge',title:'履约保证金(元)',width:100,align:'center' ,sortable:true,
// 					formatter:function(value,row,index){
					
// 					if(value==null || value=="")
//                    	return "0";
// 					else
// 						return value;
					
					   
//                    	}
// 			},
// 				{field:'purchase_tax',title:'购置税(元)',width:100,align:'center' ,sortable:true,
// 					formatter:function(value,row,index){
						
// 						if(value==null || value=="")
// 	                   	return "0";
// 						else
// 							return value;
						
						   
// 	                   	}
// 				},
// 				{field:'insurance',title:'商业保险(元)',width:100,align:'center' ,sortable:true,
// 					formatter:function(value,row,index){
						
// 						if(value==null || value=="")
// 	                   	return "0";
// 						else
// 							return value;
						
						   
// 	                   	}
// 				},
					{field:'jiangjia',title:'优惠(万)',width:70,align:'center' ,sortable:true,
						formatter:function(value,row,index){
							
							if(value==null || value=="")
		                   	return "0";
							else
								return value;
							
							   
		                   	}
					},
					{field:'sales_volume',title:'销量(辆)',width:70,align:'center' ,sortable:true,
						formatter:function(value,row,index){
						
						if(value==null || value=="")
	                   	return "0";
						else
							return value;
						
						   
	                   	}
				}
// 					,
// 				{field:'stock',title:'库存(辆)',width:100,align:'center' ,sortable:true,
// 					formatter:function(value,row,index){
					
// 					if(value==null || value=="")
//                    	return "0";
// 					else
// 						return value;
					
					   
//                    	}
// 			}
				
// 				,
// 					{field:'color',title:'颜色管理',width:100,align:'center' ,
// 						formatter:function(value,row,index){
// 		                   	return "<input type='button' onclick='color("+row.id+")' value='颜色管理' />";
							
// 							var	text = '<a href="javascript:void(0)"  id="edit" onclick=editw("'+row.id+'")>颜色管理</a>';
		      				
// 		      				return text;   
// 		                   	}
// 					},
// 					{field:'can',title:'参数管理',width:100,align:'center' ,
// 						formatter:function(value,row,index){
// 		                   	return "<input type='button' onclick='param("+row.id+")'  value='参数管理' />";
// 		                   	}
// 					},
// 					{field:'cxiao',title:'汽车促销',width:100,align:'center' ,
// 						formatter:function(value,row,index){
// 		                   	return "<input type='button' onclick='promot("+row.id+")'  value='促销管理' />";
// 		                   	}
// 					},
// 					{field:'cp',title:'汽车评测',width:100,align:'center' ,
// 						formatter:function(value,row,index){
// 		                   	return "<input type='button' onclick='ceping("+row.id+")'  value='开始评测' />";
// 		                   	}
// 					},
// 					{field:'kc',title:'库存管理',width:100,align:'center' ,
// 						formatter:function(value,row,index){
// 		                   	return "<input type='button' onclick='kucun("+row.id+")'  value='库存管理' />";
// 		                   	}
// 					}
					
					,
					{field:'huifu',title:'恢复删除',width:100,align:'center' ,
						formatter:function(value,row,index){
							if(row.isdel==1)
		                   	return "<input type='button' onclick='hf("+row.id+")'  value='恢复删除' style='color:white; width:50px; height:20px;  border:0; background:url(${ctx}/img/123.png) no-repeat left top' />";
		                   	
		                   	
		                   	
		                   	
		                   	
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
		<table id="CarTable" class="TreeClass" ></table>
	</div>
</body>
</html>