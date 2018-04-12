<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>

         $(function(){
         
        	 var title = '${param.title}';
        	 var isdel = '${param.isdel}';
        	 var status = '${param.status}';
        	 var mid='${param.uid}';
        	 
        	 var result = GetXmlBySql("SELECT uid FROM t_mycar WHERE id="+mid+" and isdel='0' ");
	 		 var uid=result[0].uid;
	 		var result1 = GetXmlBySql("SELECT cid FROM t_mycar WHERE id="+mid+" and isdel='0' ");
	 		 var cid=result1[0].cid;
        	 
        	 
        	 
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
				url: "${ctx}/carinsurance/tcarinsurance/findAllCarinsurance.action?title="+encodeURI(title)+"&isdel="+isdel+"&status="+status+"&uid="+uid+"&cid="+cid,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'id',checkbox:true},
					{field:'car_id',title:'车型名称',width:0.4,align:'center' ,sortable:true,
						
					},
// 					{field:'ico',title:'图片',width:0.2,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
// 			                   	return "<img width='100px' heigth='130px' src ='${ctx}"+value+"' />";
// 			                   	}
// 						},
					{field:'sid',title:'车险名称',width:0.2,align:'center' ,sortable:true,
					
					},
					{field:'buy_time',title:'购险时间',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'deadline_time',title:'保险期限',width:0.2,align:'center' ,sortable:true,
					
					},
					{field:'suppid',title:'车险供货商',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'insurance_money',title:'保险金额(万)',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'insurance_year',title:'保险年限',width:0.2,align:'center' ,sortable:true,
						
					},
					{field:'insurance_year_unit',title:'保险年限单位',width:0.2,align:'center' ,sortable:true,
						
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