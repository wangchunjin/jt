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

		function tg(){
			
			var currFrameId =  parent.frameElement.id;  
			var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');		
			if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中推广的用户！");
				return;
			}
	        createSimpleWindow("addWindow","推广信息","${ctx}/web/CUSER/TG/FS.jsp?currFrameId="+currFrameId+"&id="+rows[0].id, 600,400);
		}

	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id;  
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的行！");
				return;
			}
			createSimpleWindow("eidtWindow","查看用户","${ctx}/user/tuser/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,600,400);
			
	    }
	    
	    //姓名认证
	    function rzname(){
	    	var currFrameId =  parent.frameElement.id;  
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要认证的行！");
				return;
			}
			createSimpleWindow("eidtWindow","姓名认证","${ctx}/user/tuser/findByIdName.action?id="+rows[0].id+"&currFrameId="+currFrameId,800,600);
			
	    }
	    
			
		//银行卡号认证
		function rzyhkh(){
			var currFrameId =  parent.frameElement.id;  
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要认证的行！");
				return;
			}
			createSimpleWindow("eidtWindow","银行卡号认证","${ctx}/user/tuser/findByIdYhkh.action?id="+rows[0].id+"&currFrameId="+currFrameId,400,250);
			
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
<!--           	 <epmis:button id="NewRecord" imageCss="icon-edit" value="姓名认证" action="rzname()" datactrCode="" ></epmis:button> -->
<!--           	 <epmis:button id="NewRecord" imageCss="icon-edit" value="银行卡号认证" action="rzyhkh()" datactrCode="" ></epmis:button> -->
<!--           	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="" ></epmis:button> -->
<!--           	 <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode=""  ></epmis:button> -->
			 <epmis:button id="NewRecord" imageCss="icon-edit" value="推广" action="tg()" datactrCode="tuser_tg.edit" ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-edit" value="查看" action="update()" datactrCode="tuser_update.edit" ></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>