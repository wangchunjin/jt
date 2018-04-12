<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加分类码</title>
			
		<script>
 
		 
		 function AddRecord(){
			 var currFrameId = '${param.currFrameId}';
			 var base_master_key = '${param.base_master_key}';
			  var datas="";
                $('input[name="selectbox"]:checked').each(function(){    
				      datas = isStringNull(datas)? datas+";"+$(this).attr("catg_id")+","+$(this).attr("catg_type_id") : $(this).attr("catg_id")+","+$(this).attr("catg_type_id");
				  }); 
                if(isStringNull(datas)){
                	var res = IntoActionByUrl("${ctx}/sys/catval/AddCatvalAll.action?datas="+datas+"&base_master_key="+base_master_key);
		    		if(res[0].result=="success"){
		    			 GetIndexFrame(currFrameId).FourFrame.location.reload(); 
		    			 parent.close_win('addWindow');
		    		}else{
		    			$.messager.alert("错误",res[0].result);
		    		}
                }else{
                	 $.messager.alert("错误","请选择要添加的分类码！");
                }		
		}
    $(function(){ 
       		 var base_master_key = '${param.base_master_key}';
			 var which_catval_type = '${param.which_catval_type}';
        	$('#AddCatvalAllTree').treegrid({
         
				url: '${ctx}/sys/catval/AddCatvalAllTree.action?parentId=0&which_catval_type='+which_catval_type+'&base_master_key='+base_master_key,
				idField:'CATG_ID',
				treeField:'CATG_SHORT_NAME',
				fitColumns:true,
				striped: true,
				columns:[[
				    {field:'CATG_SHORT_NAME',title:'码值',width:0.2,align:'left'},
				     {field:'SELECT',title:'选择',width:0.05,align:'center',
				    	formatter:function(value,rowData,index){
				    	   if(value=="1"){
				    		  return "<input type='checkbox' checked name='selectbox' catg_type_id='"+rowData.CATG_TYPE_ID+"' catg_id ='"+rowData.CATG_ID+"' >"; 
				    	   }
				    	   if(value=="0"){
				    		    return "<input type='checkbox'  name='selectbox' catg_type_id='"+rowData.CATG_TYPE_ID+"' catg_id ='"+rowData.CATG_ID+"' >"; 
				    	   }
				    	}
				     },
					{field:'CATG_NAME',title:'说明',width:0.3},
				    {field:'CATG_TYPE_ID',title:'类型',width:0.3,hidden:true}
					]],
				 
	 			onBeforeExpand:function(node){
					$('#AddCatvalAllTree').treegrid('options').url = "${ctx}/sys/catval/AddCatvalAllTree.action?&parentId=" + node.CATG_ID+"&which_catval_type="+which_catval_type+"&base_master_key="+base_master_key;
				} 
			});
		  	});
        </script>
	</head>
	<body>
		<div    >
		<table width="100%"><%--
		      <tr>
		         <td width="16%">&nbsp;</td>
		         <td width="44%">&nbsp;
  		         </td>
		         <td width="40%"> <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="exportAll('AddCatvalAllTree')"></epmis:button>
             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="closeAll('AddCatvalAllTree')"></epmis:button> </td>
		      </tr>
		      --%><tr>
		         <td colspan="3">
		            <div style="height: 400px;">
		            <table id="AddCatvalAllTree" class="TreeClass"></table>
		            </div>
		         </td>
		      </tr>
		</table>
		</div>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
		<input type="hidden" id="projId" value="${param.projId}"/>
	</body>

</html>