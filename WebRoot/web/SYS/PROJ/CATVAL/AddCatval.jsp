<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加分类码</title>
			
		<script>
 
		 
		 function AddRecord(){
			 var currFrameId = '${param.currFrameId}';
			 var base_master_key = '${param.base_master_key}';
			 var which_catval_type = '${param.which_catval_type}';
			 var catg_type_id = $("#catg_type_id").val();
 
			var row =  document.all.catcalIframe.contentWindow.$('#AddCatvalTree').treegrid('getSelected');
			if(isNull(row)){
				var catg_id = row.CATG_ID;
        	var res = IntoActionByUrl("${ctx}/sys/catval/AddCatval.action?catg_type_id="+catg_type_id+"&base_master_key="+base_master_key+"&catg_id="+catg_id);
	    		if(res[0].result=="success"){
	    			 GetIndexFrame(currFrameId).FourFrame.location.reload(); 
	    			 parent.close_win('addWindow');
	    		}else{
	    			$.messager.alert("错误",res[0].result);
	    		}
	    			
	         }else{
	        	 $.messager.alert("错误","请选中要添加的行！");
	         }
		}
 function SetView(obj){
	var catg_type_id=  obj.value
	 document.all.catcalIframe.src = "${ctx}/web/SYS/PROJ/CATVAL/AddCatvalTree.jsp?catg_type_id="+catg_type_id;
	 
 }
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table width="100%">
		      <tr>
		         <td width="16%">分类码：</td>
		         <td width="44%">
                     <epmis:select id="catg_type_id" onChange="SetView(this)" define="SELECT CATG_TYPE_ID,CATG_TYPE FROM CM_CATTYPE WHERE  WHICH_CATVAL_TYPE='${param.which_catval_type}' ORDER BY CATG_TYPE_ID DESC"></epmis:select>
		         </td>
		         <td width="40%"> <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="catcalIframe.contentWindow.exportAll('AddCatvalTree')"></epmis:button>
             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="catcalIframe.contentWindow.closeAll('AddCatvalTree')"></epmis:button> </td>
		      </tr>
		      <tr>
		         <td colspan="3">
		            <iframe id="catcalIframe" src="${ctx}/web/SYS/PROJ/CATVAL/AddCatvalTree.jsp?catg_type_id=" height="370px" frameborder="0" width="425px" > </iframe>
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