<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
		 
        var  Level = 1;
        $(function(){ 
        	var type = '${param.type}';
        	var key = '${param.key}';
        	$('#ProjTree').treegrid({
         
				url: '${ctx}/sys/proj/ShowProjTree.action?parentId=0&type='+type+'&key='+key,
				idField:'PROJ_ID',
				treeField:'PROJ_SHORT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'PROJ_SHORT_NAME',title:'代码',width:0.2,align:'left'},
					{field:'PROJ_NAME',title:'名称',width:0.3},
					{field:'PROJ_STAGE',title:'项目类别',width:0.1,align:'center'},
					{field:'PROJ_CMPT_PCT',title:'形象进度',width:0.15,align:'center'},
					{field:'PROCEED_DATE',title:'开工日期',width:0.1,align:'center'},
					{field:'COMPLETION_DATE',title:'完工日期',width:0.1,align:'center'},
					{field:'IS_ZJLR_FLAG',title:'是否弹窗',width:0.05,align:'center',
						formatter: function(value,row,index){
							if (value==1){
								return "<input id='selectId' name='selectName' checked  type='checkbox' value='"+ row.PROJ_ID +"' parent='"+row.PARENT_PROJ_ID+"'  node_type='"+row.PROJ_NODE_FLAG+"' onclick='updateZjlr(this)'>";
							} else {
								return "<input id='selectId' name='selectName'  type='checkbox' value='"+ row.PROJ_ID +"' parent='"+row.PARENT_PROJ_ID+"'  node_type='"+row.PROJ_NODE_FLAG+"' onclick='updateZjlr(this)'>";
							}
						}
					},
                    {field:'WID',title:'WID',width:120,align:'center',hidden:true},
                    {field:'PARENT_PROJ_ID',title:'PARENT_PROJ_ID',width:0.1,align:'center',hidden:true},
					{field:'PROJ_NODE_FLAG',title:'PROJ_NODE_FLAG',width:0.1,align:'center',hidden:true}
					]],
				 
	 			onBeforeExpand:function(node){
					$('#ProjTree').treegrid('options').url = "${ctx}/sys/proj/ShowProjTree.action?&parentId=" + node.PROJ_ID+"&type="+type+"&key="+key;
				},
				onClickRow:function(node){
					OnclickRow(node);
				 
				},
				onDblClickRow:function(node){
				 	OpenProj(node);
				 
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
					     if(obj.checked){
			        		var sql = "update CM_PROJ  set  IS_ZJLR_FLAG ='1'  where  INSTR(PARENT_PATH,'"+obj.value+"')>0 ";
			                var res=GetXmlBySql(sql);
			            }else{
			            	var sql = "update CM_PROJ  set  IS_ZJLR_FLAG ='0'  where  INSTR(PARENT_PATH,'"+obj.value+"')>0 ";
			                var res=GetXmlBySql(sql);
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
			function OnclickRow(node){
				parent.OneFrame.location.reload();
 
				 var proj_id = node.PROJ_ID;
				 var node_type=node.PROJ_NODE_FLAG;
 
				 var where = proj_id+";false";
				 if(node_type=='N')
				 {
                       parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=SYS_EPS&Where="+where;		
				 }else
				 {
					   parent.document.all('ThreeFrame').src = "${ctx}/common/label.jsp?Module=SYS_PROJ&Where="+where;		
				 }	
			}
		    function OpenProj(node){
		    	parent.OneFrame.location.reload();
 
				 var projId = node.PROJ_ID;
				 var node_type=node.PROJ_NODE_FLAG;
                var projshortName = node.PROJ_SHORT_NAME;
					var projName = node.PROJ_NAME;
				 
				 if(node_type=='Y')
				 {
                       var openProj = parent.OneFrame.OpenProjId;
         
                       	if(projId != openProj){	
					 
					   IntoActionByUrl("${ctx}/sys/proj/ChangeProj.action?projId="+projId);
				 	 $(".page_content").find("span").filter(".icon-openproj").addClass("icon-proj").removeClass("icon-openproj");					
				 	 $("tr[node-id='"+projId+"']").find("span").filter(".icon-proj").addClass("icon-openproj").removeClass("icon-proj");
	               
				 	  parent.parent.$("#CurrProj").html(projshortName+"--"+projName) 
				 	  parent.parent.$("#CurrProjId").val(projId) ;
				 	 parent.OneFrame.OpenProjId =  projId;
					  
					} 
				 }
		    }

        </script>
	</head>
	<body>
		<div class="page_content">
		<table id="ProjTree" class="TreeClass"></table>
		</div>
	</body>

</html>