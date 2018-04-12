<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
			
		<script>
       $(function(){ 
        	$('#EpsTree').treegrid({        
				url: '${ctx}/ds/award/ShowAward.action?parentId=0',
				idField:'AWARD_ID',
				treeField:'AWARD_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'AWARD_NAME',title:'名称',width:0.2,align:'left'},
				    {field:'REMARK',title:'备注',width:0.3,align:'left'},
				    {field:'IS_ZJLR_FLAG',title:'',width:0.05,align:'center',
						formatter: function(value,row,index){
							return '<input type="checkbox" name="checkId" types="'+row.AWARD_TYPE+'" value="'+row.AWARD_ID+'" id="'+row.AWARD_NAME+'" parent="'+row.PARENT_AWARD_ID+'" onclick="updateZjlr(this)">';
						},
					},
				    {field:'PARENT_AWARD_ID',title:'PARENT_AWARD_ID',width:0.2,align:'left',hidden:true},
				    {field:'AWARD_TYPE',title:'AWARD_TYPE',width:0.2,align:'left',hidden:true}
					]],
	 			onBeforeExpand:function(node){
					$('#EpsTree').treegrid('options').url = "${ctx}/ds/award/ShowAward.action?parentId=" + node.AWARD_ID;
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
   			$("input[name='checkId']:checkbox").each(function(){
	                if($(this).attr("checked")){
	                	if($(this).attr('types')!='0'){
		                    checkId += isStringNull(checkId)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
		                    checkName += isStringNull(checkName)?","+$(this).attr('id'):$(this).attr('id');
	                	}
	                }
            });
            if(checkId==""){
	 			$.messager.alert("提示","请选择奖项！");
	 			return;
			}
			var currFrameId = "${param.currFrameId}";
			GetIndexFrame(currFrameId).TwoFrame.$("#docInfo_NAME").val(checkName);
			GetIndexFrame(currFrameId).TwoFrame.$("#docInfo").val(checkId);
			parent.close_win('selectProj');
		}
        </script>
	</head>
	<body>
		<div style="height:40px;margin-right: 30px;" align="right">
       		 <epmis:button id="export" imageCss="icon-expand" value="全部展开" action="exportAll('EpsTree')"   ></epmis:button>
             <epmis:button id="close" imageCss="icon-close" value="全部折叠" action="closeAll('EpsTree')"></epmis:button>          
		</div>
		<div class="page_content" style="bottom: 40px;margin-top:28px;">
			<table id="EpsTree" class="TreeClass"></table>
		</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="selProj()">确认<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('selectProj')">关闭<b></b></a> 				
		</div>
	</body>
</html>