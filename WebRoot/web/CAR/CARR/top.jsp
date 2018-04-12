<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	
  	
  	function dchu(){
  		parent.window.location.href="${ctx}/export/texport/FWQKexport.action";
  	}
  	
	  	function add(){
	     	var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","增加车型","${ctx}/web/CAR/CARR/addCar.jsp?currFrameId="+currFrameId, 700,600);
	    }
	    function update(){
	    
	    var currFrameId =  parent.frameElement.id;
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要修改的行！");
				return;
			}
			createSimpleWindow("eidtWindow","修改配置","${ctx}/car/tcar/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,700,600);
			
			
			
			
	    }
	    //复制选中的车型
	    function copy(){
		    
		    var currFrameId =  parent.frameElement.id;
		    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');
		    	
		    	
		    	if(rows.length==0){
		    		
					parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要复制的行！");
					return;
				}
		    	if(rows[0].isdel==1){
		    		parent.LeftFrame.TwoFrame.$.messager.alert("错误","被删除的数据不能复制！");
					return;
		    	}
		    	
				createSimpleWindow("eidtWindow","修改配置","${ctx}/car/tcar/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId+"&fuzhi=19491001",700,600);
				
				
				
				
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
					var res = IntoActionByUrl("${ctx}/car/tcar/delete.action?id="+ids);
					
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","删除成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload();
	    			}
				}
			});
		 }
	  	
	  	//彻底删除
function dele(){
	  		
			var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
			var ids="";
			for(var i=0;i<rows.length;i++){
				if(i==0)
				ids=ids+rows[i].id;
				else
				ids=ids+","+rows[i].id;
			}
					
			if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请慎重选中要彻底删除的行！");
				return;
			}
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要彻底删除?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/car/tcar/deletez.action?id="+ids);
					
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","已彻底删除成功！");	
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
          <epmis:button id="NewRecord" imageCss="icon-update" value="导出" action="dchu()" datactrCode="" ></epmis:button>
          	<epmis:button id="NewRecord" imageCss="icon-update" value="复制" action="copy()" datactrCode="car_fuzhi.edit" ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="car_add.edit" ></epmis:button>
          	 <epmis:button  id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode="car_delete.edit"  ></epmis:button>
          	 <epmis:button  id="DeleteRecord" imageCss="icon-remove" value="彻底删除" action="dele()" datactrCode="car_delete.edit"  ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-edit" value="修改" action="update()" datactrCode="car_update.edit" ></epmis:button>
          	 
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
