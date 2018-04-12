<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
  
        $(function(){ 
        	var userId = '${UserInfo.getUserId()}';
        	$('#ProjTree').treegrid({
         
				url: '${ctx}/sys/user/ProjUserTree.action?parentId=0&USER_ID='+userId,
				idField:'PROJ_ID',
				treeField:'PROJ_SHORT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'PROJ_SHORT_NAME',title:'代码',width:0.2,align:'left'},
					{field:'PROJ_NAME',title:'名称',width:0.3},
					{field:'SELECT_BOX',title:'选择',width:0.05,align:'center',
						formatter: function(value,row,index){
				               return "<input   name='selectbox'   type='checkbox' id='"+row.PROJ_ID+"' projName='"+row.PROJ_NAME+"'  parent='"+row.PARENT_PROJ_ID+"'  proj_node_flag='"+row.PROJ_NODE_FLAG+"' onclick='SetAll(this)'>";
							 
						}
					},
                    {field:'WID',title:'WID',width:120,align:'center',hidden:true},
                    {field:'PARENT_PROJ_ID',title:'PARENT_PROJ_ID',width:0.1,align:'center',hidden:true},
					{field:'PROJ_NODE_FLAG',title:'PROJ_NODE_FLAG',width:0.1,align:'center',hidden:true}
					]],
				 
 
				onClickRow:function(node){
					OnclickRow(node);
				 
				},
				onDblClickRow:function(node){
				 	OpenProj(node);
				 
				}
			});
		  	});
			
		function AddRecord(){
			var column_id = "${param.COLUMN_ID}";
	        var datas ="";
	        var names ="";
			 $("input[name='selectbox']:checked").not(':disabled').each(function(){
                	  
                 	 datas = isStringNull(datas) ? datas +","+$(this).attr("id") : $(this).attr("id");
                 	 names = isStringNull(names) ? names +","+$(this).attr("projName") : $(this).attr("projName");
                 	 });
                 	 
 
					opener.document.getElementById(column_id).value =    datas;  
	                opener.document.getElementById(column_id+"_NAME").value = names;
	                
	                window.close();
		 
		}	  
			function SetAll(obj){
				$('#labelBottomDiv').css('display','');
				SetBox(obj.checked,obj.id)
				if(!obj.checked){
			 	   SetParentBox(obj.checked,$(obj).attr("parent"))
				}
			}  
			
			function SetBox(checked,id){				
				$("input[parent='"+id+"']").not(':disabled').each(function(){
					$(this).attr("checked",checked);
					SetBox(checked,$(this).attr("id"));
				});			 			 
			}
			function SetParentBox(checked,parentId){
				$("input[id='"+parentId+"']").not(':disabled').attr("checked",false);
				parentId = $("input[id='"+parentId+"']").attr("parent")
				if(parentId !="0"&&$("input[id='"+parentId+"']").length>0){
				   SetParentBox(checked,$("input[id='"+parentId+"']").attr("id"));
			 	}
			}
		 

        </script>
	</head>
	<body>
		<div class="page_content" style="bottom: 40px;">
		<table id="ProjTree" class="TreeClass" ></table>
		</div>
		<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
	</body>

</html>