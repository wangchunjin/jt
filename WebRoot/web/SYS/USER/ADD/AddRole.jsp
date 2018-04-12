<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加角色</title>
			
		<script>
       $(function(){ 
          	var userId = '${param.USER_ID}';
        	$('#SysRoleUserTree').treegrid({        
				url: '${ctx}/sys/user/AddRoleUserTree.action?parentId=0&userId='+userId,
				idField:'ROLE_ID',
				treeField:'ROLE_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'ROLE_NAME',title:'角色名称',width:0.2,align:'left'},
				    {field:'ROLE_TYPE',title:'ROLE_TYPE',width:0.2,align:'left',hidden:true},
					{field:'SELECT',title:'选择',width:0.05,align:'center',
				    	formatter:function(value,rowData,index){
				    	if(rowData.ROLE_TYPE=="1"){
					    	   if(value=="1"){
					    		  return "<input type='checkbox' checked name='selectbox'   role_id ='"+rowData.ROLE_ID+"' >"; 
					    	   }
					    	   if(value=="0"){
					    		    return "<input type='checkbox'  name='selectbox'   role_id ='"+rowData.ROLE_ID+"' >"; 
					    	   }
				    	   }
				    	}
				     }
					]],
				 
	 			onBeforeExpand:function(node){
					$('#SysRoleUserTree').treegrid('options').url = "${ctx}/sys/user/AddRoleUserTree.action?parentId=" + node.ROLE_ID+'&userId='+userId;
				} 
			});
		  	});
		 
		 function AddRecord(){
 
			 var userId = '${param.USER_ID}';
			 
				  var datas="";
                $('input[name="selectbox"]:checked').each(function(){    
				      datas = isStringNull(datas)? datas+","+$(this).attr("role_id") : $(this).attr("role_id");
				  }); 				 
				if(isStringNull(datas)){					 
	        	var res = IntoActionByUrl("${ctx}/sys/user/AddRoleUser.action?userId="+userId+"&datas="+datas);
		    		if(res[0].result=="success"){
		    		  parent.OneFrame.nextstep('2',userId);
		    		}else{
		    			$.messager.alert("错误",res[0].result);
		    		}
		    			
		         }else{
		        	 $.messager.alert("错误","请选择角色！");
		         }
		  }
 
        </script>
	</head>
	<body>
		<table width="100%">
	      <tr>
	         <td width="50%">&nbsp;</td>
	         <td width="50%" align="center"> 
		         <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="exportAll('SysRoleUserTree')"></epmis:button>
	             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="closeAll('SysRoleUserTree')"></epmis:button> 
             </td>
	      </tr>
	   </table>
		<div   class="page_content" style="bottom: 40px;top: 30px;">
		<table id="SysRoleUserTree" class="TreeClass"></table>
		</div>
		    <div class="BottomDiv">

					<a href="###" class="btn_01" onclick="AddRecord()">下一步<b></b></a>				
			</div>
	</body>

</html>