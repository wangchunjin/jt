<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
	  	function add(){	  		
	  		var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","新增","${ctx}/web/JT/CLIENT/INFO/addPic.jsp?currFrameId="+currFrameId, 400,300);
	    }
	    
	    
	    function update(){
// 	    	createSimpleWindow("eidtWindow","用户信息展示","${ctx}/findById.action?id="+rows[0].client_id+"&currFrameId="+currFrameId, 1200,600);
			
			
			
	   		
	   		
	    	var currFrameId =  parent.frameElement.id;
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的用户！");
				return;
			}
			createSimpleWindow("eidtWindow","用户信息展示","${ctx}/clientinfo/tclientinfo/findById.action?id="+rows[0].client_id+"&currFrameId="+currFrameId, 1200,600);
			
	    }
	    
			
			
			
			
	    
	  	function del(){
	  		
	  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
	  		//获取主键ID 对经销商下设备表处理
			var ids="";
			for(var i=0;i<rows.length;i++){
				if(i==0)
				ids=ids+rows[i].id;
				else
				ids=ids+","+rows[i].id;
			}	
			
			
			
			
			if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要删除的服务类别！");
				return;
			}
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
			
	        	if(res){
	        		
					var res = IntoActionByUrl("${ctx}/clientinfo/tclientinfo/delete.action?id="+ids);
// 					alert(res[0].result);
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
          	 <epmis:button id="NewRecord" imageCss="icon-edit" value="查看" action="update()" datactrCode="" ></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
