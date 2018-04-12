<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	
  	
  //线下还款
	function flat(){
		var currFrameId =  parent.frameElement.id;   
		var cuid=$("#cuid").val();
		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');		
    	if(rows.length==0){
    		parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要操作的订单！");
			return;
		}			
    	

    	
	  		if(rows[0].transfer_status==2 || rows[0].transfer_status==4){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","该订单为已还款订单！");
			return;
		}	
	  		
	  	
        createSimpleWindow("addWindow","线下还款","${ctx}/web/JT/ORDER/FLAT/addPic.jsp?currFrameId="+currFrameId+"&order_id="+rows[0].order_id+"&cuid="+cuid+"&o_transfer_status="+rows[0].transfer_status+"&o_rel_time="+rows[0].rel_time, 400,300);
    
  	}
  	
  	
  	
  	
  	//导出已完成的订单
	function dchu(){
		var currFrameId =  parent.frameElement.id;
		//注意路径了   有所不同
        createSimpleWindow("addWindow","导出订单","${ctx}/web/JT/ORDER/INFO/addPic.jsp?currFrameId="+currFrameId, 400,300);
    
		//获取当前操作人员
// 		var cuid=$("#cuid").val();
// 		parent.window.location.href="${ctx}/export/texport/FWQKexport.action?cuid="+cuid+"&lend_id="+lend_id+"&lender_name="+lender_name+"&batch_id="+batch_id;
		
  	}
  	
  	
  	
  	
	  	function add(){	  		
	  		var currFrameId =  parent.frameElement.id;   	
	        createSimpleWindow("addWindow","新增","${ctx}/web/JT/ORDER/INFO/addPic.jsp?currFrameId="+currFrameId, 400,300);
	    }
	    
	    
	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id;
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的用户！");
				return;
			}
			createSimpleWindow("eidtWindow","订单信息展示","${ctx}/orderinfo/torderinfo/findById.action?id="+rows[0].order_id+"&currFrameId="+currFrameId, 1200,600);
			
	    }
	    
			
// 	    function daoru(){	  		
// 	  		var currFrameId =  parent.frameElement.id; 	     	
// 	        createSimpleWindow("addWindow","批量转账导入","${ctx}/web/JT/ORDER/INFO/update.jsp?currFrameId="+currFrameId, 500,400);
// 	    }	
			
			
	    
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
					var res = IntoActionByUrl("${ctx}/orderinfo/torderinfo/delete.action?id="+ids);
// 					alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","删除成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		 }
	  	
	  	
// function flat(){	  		
// 	  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
// 	  		if(rows.length==0){
// 				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要平帐的订单！");
// 				return;
// 			}
// 	  		if(rows[0].transfer_status==2 || rows[0].transfer_status==4){
// 	  			parent.LeftFrame.TwoFrame.$.messager.alert("错误","订单已平！");
// 				return;
// 			}
// 	  		获取主键ID 准备对订单进行平帐
// 			var ids="";
// 			for(var i=0;i<rows.length;i++){
// 				if(i==0)
// 				ids=ids+rows[i].order_id;
// 				else
// 				ids=ids+","+rows[i].order_id;
// 			}	
			
// 			var cuid=$("#cuid").val();
			
			
			
// 			if(rows[0].transfer_status==1){
// 				parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要平帐吗?",function(res){
					
// 		        	if(res){	        		
// 						var res = IntoActionByUrl("${ctx}/orderinfo/torderinfo/flat_1.action?id="+ids+"&cuid="+cuid);
	 					////alert(res[0].result);
// 			    		if(res[0].result=="success"){
// 							parent.LeftFrame.TwoFrame.$.messager.alert("提醒","删除成功！");	
// 				    		parent.LeftFrame.TwoFrame.location.reload(); 
// 		    			}
// 					}
// 				});
// 			}
// 			if(rows[0].transfer_status==3){
// 				parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要平帐吗?",function(res){					
// 		        	if(res){	        		
// 						var res = IntoActionByUrl("${ctx}/orderinfo/torderinfo/flat_2.action?id="+ids+"&cuid="+cuid);
	 					////alert(res[0].result);
// 			    		if(res[0].result=="success"){
// 							parent.LeftFrame.TwoFrame.$.messager.alert("提醒","删除成功！");	
// 				    		parent.LeftFrame.TwoFrame.location.reload(); 
// 		    			}
// 					}
// 				});
// 			}
			
			
			
// 		 }
	  	
	  	
	  	
	  	
  	</script>
  </head>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
          <input type="hidden" id="cuid" value="${sessionScope.UserInfo.userId}">
          <epmis:button id="NewRecord" imageCss="icon-update" value="导出" action="dchu()" datactrCode="orderinfo_pdaochu.edit" ></epmis:button>
         <epmis:button id="NewRecord" imageCss="icon-edit" value="线下还款" action="flat()" datactrCode="orderinfo_flat.edit" ></epmis:button>
<!--           	 <epmis:button id="NewRecord" imageCss="icon-edit" value="查看" action="update()" datactrCode="" ></epmis:button> -->
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
