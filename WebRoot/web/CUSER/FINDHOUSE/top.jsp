<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
	  	function add(){
	  		var uid='${param.uid}';
	  		
	     	var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","新增","${ctx}/web/CUSER/FINDHOUSE/addPic.jsp?currFrameId="+currFrameId+"&uid="+uid, 600,400);
	    }
	  	
	  	function ck(){
	  		
	  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');
	     	var currFrameId =  parent.frameElement.id; 	     	
	     	if(rows.length==0){
	     		createSimpleWindow("addWindow","查看","${ctx}/web/CUSER/FINDHOUSE/GROUPLIST/FS.jsp?currFrameId="+currFrameId, 1000,550);
			}else{
				createSimpleWindow("addWindow","查看","${ctx}/web/CUSER/FINDHOUSE/GROUPLIST/FS.jsp?currFrameId="+currFrameId+"&uid="+rows[0].userid, 1000,500);
			}
	        
	    }
	  	
	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id;
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要修改的行！");
				return;
			}
	    	
			createSimpleWindow("eidtWindow","修改配置","${ctx}/findhouse/tfindhouse/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,300,150);
			
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
					var res = IntoActionByUrl("${ctx}/findhouse/tfindhouse/delete.action?id="+ids);
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
<!--           	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="" ></epmis:button> -->
<!--           	 <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode=""  ></epmis:button> -->
<!--           	 <epmis:button id="NewRecord" imageCss="icon-edit" value="修改" action="update()" datactrCode="" ></epmis:button> -->
			<epmis:button id="NewRecord" imageCss="icon-add" value="查看" action="ck()" datactrCode="" ></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
