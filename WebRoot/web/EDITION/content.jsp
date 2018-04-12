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
        	 
        	 
         	$('#CarTable').datagrid({
         		remoteSort:false,
//                 fitColumns:true,  //设置easyui的展示宽度，是否自动适配
                striped:true,
               
                selectOnCheck:false,
                autoRowHeight:true,
                checkOnSelect:true,
                singleSelect:true,
                pagination: true,
                pageSize: 10,
                pageList: [10,20,30],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/edition/tedition/findAllEdition.action?isdel="+isdel,        
				frozenColumns:[[
				]],
				columns:[[
					{field:'id',checkbox:true},
					{field:'android',title:'安卓版本号',width:80,align:'center' ,sortable:true,
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
					{field:'ios',title:'ios版本号',width:80,align:'center' ,sortable:true,
						
					},
					{field:'androname',title:'安卓版本名称',width:120,align:'center' ,sortable:true,
						
					},
					{field:'iosname',title:'ios版本名称',width:120,align:'center' ,sortable:true,
						
					},
					{field:'androidcontent',title:'安卓新版本介绍',width:390,align:'center' ,sortable:true,
					
					},
					{field:'ioscontent',title:'ios新版本介绍',width:400,align:'center' ,sortable:true,
						
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