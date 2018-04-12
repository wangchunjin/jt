<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
		 
        var  Level = 1;
        $(function(){ 
        	$('#EpsTree').treegrid({
         
			 	url: '${ctx}/xmjs/proj/ShowEpsTree.action?parentId=0&level=2',				                                       
				idField:'id',
				treeField:'proj_short_name',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'proj_short_name',title:'代码',width:0.25,align:'left'},
					{field:'proj_name',title:'名称',width:0.3},
					{field:'IS_ZJLR_FLAG',title:'',width:0.05,align:'center',
						formatter: function(value,row,index){
							if (value==1){
								return '<input type="checkbox" name="checkId"  value="'+row.id+'" id="'+row.proj_name+'" parent="'+row.PARENT_PROJ_ID+'" onclick="updateZjlr(this)">';
							} else {
								return '<input type="checkbox" name="checkId"  value="'+row.id+'" id="'+row.proj_name+'" parent="'+row.PARENT_PROJ_ID+'" onclick="updateZjlr(this)">';
							}	
						},
					},
					{field:'id',title:'名称',width:0.3,hidden:true}
				]],
	 			onBeforeExpand:function(node){
					$('#EpsTree').treegrid('options').url = "${ctx}/xmjs/proj/ShowEpsTree.action?parentId=" + node.id;
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
					     /* if(obj.checked){
			        		var sql = "update CM_PROJ  set  IS_ZJLR_FLAG ='1'  where  INSTR(PARENT_PATH,'"+obj.value+"')>0 ";
			                var res=GetXmlBySql(sql);
			            }else{
			            	var sql = "update CM_PROJ  set  IS_ZJLR_FLAG ='0'  where  INSTR(PARENT_PATH,'"+obj.value+"')>0 ";
			                var res=GetXmlBySql(sql);
			            } */
					}
					 SetBox(obj.checked,obj.value)
			 }
			 function SetBox(checked,id){				
				$("input[parent='"+id+"']").not(':disabled').each(function(){
					$(this).attr("checked",checked);
					SetBox(checked,$(this).attr("value"));
				});			 			 
			}
		  	
			function check(obj){
				var sql = "SELECT proj_id id ,proj_short_name,proj_name,seq_num  FROM cm_proj WHERE proj_node_flag = 'N' AND parent_proj_id = '"+obj.value+"' ORDER BY seq_num ";
				var  res=GetXmlBySql(sql);
				if(res.length>0){
					if(obj.checked == true){
		 				$("input[name='checkId']").attr("checked",true);
			 		}else{
			 			$("input[name='checkId']").attr("checked",false);
			 		}
				}
	 		}
			 	

			 function selProj(){
			 		var checkId = "";
			 		var checkName = "";
		   			$("input[name='checkId']:checkbox").each(function(){
			                if($(this).attr("checked")){
			                    checkId += isStringNull(checkId)?","+$(this).val():$(this).val();
			                    checkName += isStringNull(checkName)?","+$(this).attr('id'):$(this).attr('id');
			                }
		            });
			 		if(checkId==""){
			 			$.messager.alert("提示","请选择EPS！");
						return false;
			 		}
					var currFrameId = "${param.currFrameId}";
					GetIndexFrame(currFrameId).TwoFrame.$("#projInfo_NAME").val(checkName);
					GetIndexFrame(currFrameId).TwoFrame.$("#projInfo").val(checkId);
					parent.close_win('selectProj');
			}
        </script>
	</head>
	<body>
		<div class="page_content" style="bottom: 40px;">
			<table id="EpsTree" class="TreeClass"></table>
		</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="selProj()">确认<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('selectProj')">关闭<b></b></a> 				
		</div>
	</body>

</html>