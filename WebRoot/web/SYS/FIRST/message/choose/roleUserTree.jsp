<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加人员</title>			
		<script>
       $(function(){     	  
        	$('#SysRoleTree').treegrid({        
				url: '${ctx}/co/co_notify/RoleUserTree.action?parentId=0&roleType=0',
				idField:'ID',
				treeField:'NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'NAME',title:'名称',width:0.6,align:'left'},
				    {field:'PARENT_ROLE_ID',title:'PARENT_ROLE_ID',width:0.2,align:'left',hidden:true},
				    {field:'ROLE_TYPE',title:'ROLE_TYPE',width:0.2,align:'left',hidden:true},
				    {field:'SELECT',title:'选择',width:0.1,align:'left',
				    	  formatter:function(value,rowData,index){	
						  var checked ="";
				    	    if(rowData.NUM=="1"){
				    		  checked ="checked";
				    	   }
				    	   if($("input[id='"+rowData.PARENT_ROLE_ID+"']").attr("checked")){
				    	   		checked ="checked";
				    	   }
		                  return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' id='"+rowData.ID+"' nodetype='"+rowData.ROLE_TYPE+"' username='"+rowData.NAME+"' parent='"+rowData.PARENT_ROLE_ID+"' name='selectbox' "+checked+"  onclick='SetBox(this);IfSetParentBox(this)' >"; 				    	    				    	    
				    	}
				     }
					]],
					onBeforeExpand:function(node){
						$('#SysRoleTree').treegrid('options').url = "${ctx}/co/co_notify/RoleUserTree.action?parentId="+node.ID+"&roleType="+node.ROLE_TYPE+"&key=${param.key}";
					}
			});
		  	});
       
       			
			function SetBox(obj){ 
				$("input[parent='"+$(obj).attr("id")+"']").each(function(){
					$(this).attr("checked",obj.checked);
					SetBox(this);
				});
			}
			
	 
			function SetParentBox(obj){
				var parentId = $(obj).attr("parent");
				if($("input[id='"+parentId+"']").length>0){
				  $("input[id='"+parentId+"']").attr("checked",false);
					var parentObj = $("input[id='"+parentId+"']")[0];
				   SetParentBox(parentObj);
			 	}
			}
			function IfSetParentBox(obj){
				if(!obj.checked){
					SetParentBox(obj);
				}
			}
			function AddRecord(){
				var ids="";
				var names="";
				var xmlby = "";
				$("input[name='selectbox'][nodetype='2']:checked").each(function(){
					ids = isStringNull(ids) ? ids +","+$(this).attr("id") : $(this).attr("id");
					names = isStringNull(names) ? names +","+$(this).attr("username") : $(this).attr("username");
				});
				
				$("input[name='selectbox'][nodetype='1']:checked").each(function(){
							var idValue= $(this).attr("id");
							xmlby += isStringNull(xmlby)?","+$(this).attr("id"):$(this).attr("id");
					});
					var sql = " SELECT USER_ID ID ,ACTUAL_NAME NAME FROM CM_USERS C WHERE USER_ID IN ( SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID IN ('"+xmlby.replaceAll(",","','")+"')) AND  DISABLE_FLAG !='1' ";
					if(isStringNull(ids)){
						sql += " AND USER_ID NOT IN ('"+ids.replaceAll(",","','")+"')";
					}
					var res=GetXmlBySql(sql);
					if(res.length>0){
						for(var i = 0;i<res.length;i++){
							ids += isStringNull(ids)?","+res[i].ID:res[i].ID;
							names += isStringNull(names)?","+res[i].NAME:res[i].NAME;
						}
				 	}
				if(!isStringNull(ids)){
					$.messager.alert("错误","请选择用户");
					return;
				}
				  var currFrameId = "${param.currFrameId}";
				  GetIndexFrame(currFrameId).gtxxframe.$("#to_user_names").val(names);
				  GetIndexFrame(currFrameId).gtxxframe.$("#to_user_ids").val(ids);
				  parent.close_win('addWindow');
			}
        </script>
	</head>
	<body>
		<div   class="page_content"  style="bottom: 40px;">
		<table id="SysRoleTree" class="TreeClass"></table>
		</div>
 			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
	</body>

</html>