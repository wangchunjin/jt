<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加分类码</title>
			
		<script>
       $(function(){ 
        	$('#HrDepartTree').treegrid({        
				url: '${ctx}/hr/hr_depart/HrDepartTree.action?parentId=0',
				idField:'DEPT_ID',
				treeField:'DEPT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'DEPT_NAME',title:'名称',width:0.3,align:'left',
				       formatter: function(value,row,index){							
							return  row.DEPT_CODE+"&nbsp;&nbsp;&nbsp;"+value;						 
						}
				    },
				   	{field:'SELECT',title:'选择',width:0.05,align:'center',
						formatter: function(value,row,index){
                            return "<input  name='selectbox' id='"+row.DEPT_ID+"' node_type='"+row.NODE_TYPE+"' parent = '"+row.PARENT_DEPT_ID+"' type='checkbox'  onclick='onclickselectbox();'>";
						}
					},
				    {field:'DEPT_CODE',title:'名称',width:0.3,align:'left',hidden:true},
				    {field:'PARENT_DEPT_ID',title:'PARENT_DEPT_ID',width:0.2,align:'left',hidden:true},
				    {field:'DEPT_ID_PATH',title:'DEPT_ID_PATH',width:0.2,align:'left',hidden:true},
				    {field:'NODE_TYPE',title:'NODE_TYPE',width:0.2,align:'left',hidden:true}
					]],
				 
	 			onBeforeExpand:function(node){
					$('#HrDepartTree').treegrid('options').url = "${ctx}/hr/hr_depart/HrDepartTree.action?parentId=" + node.DEPT_ID;
				} ,
				onClickRow:function(node){
					if(onclickselectboxflag){
						$("#"+node.DEPT_ID).attr('checked',!$("#"+node.DEPT_ID).attr('checked'));
					}
					SetBox($("#"+node.DEPT_ID));
					IfSetParentBox($("#"+node.DEPT_ID));
					onclickselectboxflag = true;
 			       	
				}
			});
		  	});
       var onclickselectboxflag = true;
       function onclickselectbox(){
    	   onclickselectboxflag = false;
       }

          function SetBox(obj){
				var ichecked = $(obj).attr("checked");
				if (ichecked == 'checked') {
					ichecked = true;
				} else {
					ichecked = false;
				}
				$("input[parent='"+$(obj).attr("id")+"']").each(function(){
					$(this).attr("checked",ichecked);
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

				parent.TwoFrame.location.reload();
			}
        </script>
	</head>
	<body>
		<div   class="page_content"  >
		<table id="HrDepartTree" class="TreeClass"></table>
		</div>
	</body>

</html>