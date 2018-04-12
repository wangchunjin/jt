<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
		 
        var  Level = 1;
        $(function(){ 
        	$('#SwbsTree').treegrid({
         
				url: '${ctx}/sys/swbs/ShowSwbsTree.action?parentId=0&swbs_type_id=${param.swbs_type_id}&level=2',
				idField:'SWBS_ID',
				treeField:'SWBS_SHORT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'SWBS_SHORT_NAME',title:'代码',width:0.1,align:'left'},
					{field:'SWBS_NAME',title:'名称',width:0.3},
					{field:'IS_MAJOR',title:'关注',width:0.05,align:'center',
						formatter: function(value,row,index){
						if(row.NODE_TYPE=="TASK"){
							if (value==1){
								return "<input id='selectId' name='selectName' checked  type='checkbox' value='"+ row.SWBS_ID +"'   onclick='updateMajor(this)'>";
							} else {
								return "<input id='selectId' name='selectName'  type='checkbox' value='"+ row.SWBS_ID +"'   onclick='updateMajor(this)'>";
							}
						  }
						}},
					{field:'NODE_TYPE',title:'节点类型',width:120,align:'center',hidden:true},
					{field:'DOC_ID',title:'DOC_ID',width:120,align:'center',hidden:true},
					{field:'PARENT_SWBS_ID',title:'PARENT_SWBS_ID',width:120,align:'center',hidden:true}

					]],
				 
	 			onBeforeExpand:function(node){
					$('#SwbsTree').treegrid('options').url = "${ctx}/sys/swbs/ShowSwbsTree.action?swbs_type_id=${param.swbs_type_id}&parentId=" + node.SWBS_ID;
				},
				onClickRow:function(node){
				var swbsTypeId="${param.swbs_type_id}";
				 var doc_id = node.DOC_ID;
				 var node_type=node.NODE_TYPE;
				 var swbs_id = node.SWBS_ID;
				 var parentId = node.PARENT_SWBS_ID;
				 var where = swbs_id+";"+node_type+";"+swbsTypeId+";"+parentId+";"+doc_id;
				 if(node_type=='SWBS')
				 { 
					  parent.OneFrame.document.getElementById('addwbs').style.display='';
					  parent.OneFrame.document.getElementById('newRecord').style.display='';					 
                       parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=SWBS_WBS&Where="+where;		
				 }else
				 {
					 parent.OneFrame.document.getElementById('addwbs').style.display='none';
					 parent.OneFrame.document.getElementById('newRecord').style.display='none';
				 //	 parent.OneFrame.proj_node_flag='N';
				 	 parent.document.all('ThreeFrame').src="${ctx}/common/label.jsp?Module=SWBS_TASK&Where="+where;
				 }		
				 
				}
			});
		  	});
			
			  
			function updateMajor(obj){
				var major ="0";
				if(obj.checked){
					major = "1";
				}
				GetXmlBySql("UPDATE SM_SWBS SET IS_MAJOR = '"+major+"' where SWBS_ID = '"+obj.value+"'");
			}
		 

        </script>
	</head>
	<body>
		<div class="page_content">
		<table id="SwbsTree" class="TreeClass"></table>
		</div>
		<input type="hidden" value="${param.swbs_type_id}" id="swbs_type_id" >
	</body>

</html>