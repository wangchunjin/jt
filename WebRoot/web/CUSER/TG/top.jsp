<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
// 	  	function add(){
// 	     	var currFrameId =  parent.frameElement.id; 	     	
// 	        createSimpleWindow("addWindow","新增","${ctx}/web/CUSER/LEVEL/addBrand.jsp?currFrameId="+currFrameId, 600,400);
// 	    }

	

	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id;  
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的行！");
				return;
			}
			createSimpleWindow("eidtWindow","查看用户","${ctx}/user/tuser/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,600,400);
			
	    }
	    
	  
			
	    
	  	function del(){
	  		
			var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
			var ids="";
			for(var i=0;i<rows.length;i++){
				if(i==0)
				ids=ids+rows[i].id;
				else
				ids=ids+","+rows[i].id;
			}			
			if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
				return;
			}
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/user/tuser/delete.action?id="+ids);
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","删除成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		 }
	  	
	  	
  	</script>
  </head>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">

<!--           	 <epmis:button id="NewRecord" imageCss="icon-edit" value="查看" action="update()" datactrCode="" ></epmis:button> -->
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
