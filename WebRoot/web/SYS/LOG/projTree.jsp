<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
		 
        var  Level = 1;
         $(function(){
        	$('#SysRoleTree').treegrid({        
				url: '${ctx}/sys/role/SysRoleTree.action?parentId=0',
				idField:'ROLE_ID',
				treeField:'ROLE_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'ROLE_NAME',title:'角色名称',width:0.5,align:'left'},
				    {field:'ACTUAL_NAME',title:'用户',width:0.6,align:'left'},
				    {field:'PARENT_ROLE_ID',title:'PARENT_ROLE_ID',width:0.2,align:'left',hidden:true},
				    {field:'ROLE_TYPE',title:'ROLE_TYPE',width:0.2,align:'left',hidden:true},
				    {field:'ROLE_ID',title:'ROLE_ID',width:0.2,align:'left',hidden:true}
					]],
				 
	 			onBeforeExpand:function(node){
					$('#SysRoleTree').treegrid('options').url = "${ctx}/sys/role/SysRoleTree.action?parentId=" + node.ROLE_ID;
				},
				onClickRow:function(node){
				}
			});
		  	});
		  	function updateZjlr(obj){
				    var datactrs =  '${UserInfo.getDatactrCode()}';
					if(datactrs.indexOf("SYS_PROJ.QYXM.window") ==-1){
						$.messager.alert("错误","你没有权限!");
						 if(obj.checked){
				             obj.checked = false;
						 }else{
				             obj.checked = true;
						 }
					}else{
					}
					 SetBox(obj.checked,obj.value)
			 }
			 function SetBox(checked,id){				
				$("input[parent='"+id+"']").not(':disabled').each(function(){
					$(this).attr("checked",checked);
					SetBox(checked,$(this).attr("value"));
				});			 			 
			}
			function selProj(){
			 		var checkId = "";
			 		var checkName = "";
			 		var rows = $('#SysRoleTree').treegrid('getSelections');
					if(rows.length==0){
						$.messager.alert("错误","请选择记录！");
						return;
					}
					checkId = rows[0].ROLE_ID;
					checkName = rows[0].ROLE_NAME;
		   			/* $("input[name='checkId']:checkbox").each(function(){
			                if($(this).attr("checked")){
			                    checkId += isStringNull(checkId)?","+$(this).val():$(this).val();
			                    checkName += isStringNull(checkName)?","+$(this).attr('id'):$(this).attr('id');
			                }
		            }); 
			 		if(checkId==""){
			 			$.messager.alert("提示","请选择EPS！");
						return false;
			 		}*/
					var currFrameId = "${param.currFrameId}";
					GetIndexFrame(currFrameId).TwoFrame.$("#projInfo_NAME").val(checkName);
					GetIndexFrame(currFrameId).TwoFrame.$("#projInfo").val(checkId);
					parent.close_win('selectProj');
					
					
			}
        </script>
	</head>
	<body>
		<div class="page_content" style="bottom: 40px;">
			<table id="SysRoleTree" class="TreeClass"></table>
		</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="selProj()">确认<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('selectProj')">关闭<b></b></a> 				
		</div>
	</body>

</html>