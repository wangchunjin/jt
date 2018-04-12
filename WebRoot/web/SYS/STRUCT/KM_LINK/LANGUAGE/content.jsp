<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>语言库</title>
			
		<script>
 
      
        $(function(){ 
        	var userId = '${sessionScope.UserInfo.getUserId()}';
        	$('#LanguageTree').treegrid({         
			 	url: '${ctx}/sys/struct/showOwnLanguageTree.action?parentId=0&userId='+userId,				                                       
				idField:'ID',
				treeField:'NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'NAME',title:'名称',width:0.3,align:'left'},
					{field:'CREATED_NAME',title:'创建人',width:0.08,align:'center'},
					{field:'IS_PUBLIC',title:'是否共享',width:0.08,align:'center',
						formatter: function(value,row,index){
							if(row.TYPE=="TASK"){
								if(value =='Y'){
									return "是";
								}else{
									return "<font style='color:red'>否</font>";
								}
							}else{
								return "";
							}
						}
					},
					{field:'SEQ_NUM',title:'排序',width:0.08,align:'right'}					
				]],
	 			onBeforeExpand:function(node){
					$('#LanguageTree').treegrid('options').url = "${ctx}/sys/struct/showOwnLanguageTree.action?parentId=" + node.ID+"&userId="+userId;
				},
				onClickRow:function(node){
				if(node.TYPE=='TASK'){
					parent.OneFrame.$("#addwbs").hide();
					parent.OneFrame.$("#insertWord").show();
					
				}else{
					parent.OneFrame.$("#addwbs").show();
					parent.OneFrame.$("#insertWord").hide();
				}
				var where = node.ID+";"+false;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
			});
		  	});
			
			  
			
		 

        </script>
	</head>
	<body>
		<div class="page_content">
		<table id="LanguageTree" class="TreeClass" ></table>
		</div>
	</body>

</html>