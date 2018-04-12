<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	//催收排行
  	function rank(){
  		var currFrameId =  parent.frameElement.id;
//     	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
    	
//     	if(rows.length==0){
// 			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的用户！");
// 			return;
// 		}
		var cuid=$("#cuid").val();
		createSimpleWindow("rankWindow","催收展示","${ctx}/orderinfo/torderinfo/rank.action?cuid="+cuid+"&currFrameId="+currFrameId, 650,500);
  		
  	}
  	
  	//对滞纳金进行消减
  	function late(){
  		var currFrameId =  parent.frameElement.id; 	   
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');	    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要消减滞纳金用户！");
			return;
		}    	
        createSimpleWindow("addWindow","滞纳金","${ctx}/web/JT/CUISHOU/ZG/late.jsp?currFrameId="+currFrameId+"&late="+rows[0].overdue_payment_rel+"&order_id="+rows[0].order_id+"&interest="+rows[0].interest+"&amount="+rows[0].amount, 400,200);
  	}
  	
  	  	
	
  	
  	
  		//分配
	  	function fp(){	  	
	  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
	  		if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要分配的订单！");
				return;
			}
	  		if(rows[0].fp_zg==1){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","选中订单已分配！");
				return;
			}
	  		//获取主键ID 
			var ids="";
			for(var i=0;i<rows.length;i++){
				if(i==0)
				ids=ids+rows[i].order_id;
				else
				ids=ids+","+rows[i].order_id;
			}
			
			
			
	  		var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","逾期订单分配","${ctx}/web/JT/CUISHOU/ZG/addPic.jsp?currFrameId="+currFrameId+"&ids="+ids, 400,300);
	    }
	  	//收回
	  	function sh(){	  	
	  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
	  		if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要收回的订单！");
				return;
			}
	  		if(rows[0].fp_zg==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","选中订单未分配，不能收回！");
				return;
			}
	  		//获取主键ID 
			var ids="";
			for(var i=0;i<rows.length;i++){
				if(i==0)
				ids=ids+rows[i].order_id;
				else
				ids=ids+","+rows[i].order_id;
			}
			
			if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要收回的订单！！");
				return;
			}
			var cuid=$("#cuid").val();
			
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要收回选中订单吗?",function(res){
				
	        	if(res){
	        		
					var res = IntoActionByUrl("${ctx}/orderinfo/torderinfo/sh.action?ids="+ids+"&cuid="+cuid);

		    		if(res[0].result=="success"){
// 						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","收回成功！");
						parent.LeftFrame.TwoFrame.location.reload();
							
			    		 
	    			}
				}
			});
			
			
			
			
// 	  		var currFrameId =  parent.frameElement.id; 	     	
// 	        createSimpleWindow("addWindow","逾期订单分配","${ctx}/web/JT/CUISHOU/ZG/addPic.jsp?currFrameId="+currFrameId+"&ids="+ids, 400,300);
	    }
	  	
	  	
	  	function add(){	  	
	  		
	  		var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","新增","${ctx}/web/JT/CUISHOU/ZG/addPic.jsp?currFrameId="+currFrameId, 400,300);
	    }
	    
	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id;
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的用户！");
				return;
			}
			createSimpleWindow("eidtWindow","订单信息展示","${ctx}/orderinfo/torderinfo/findById.action?id="+rows[0].order_id+"&currFrameId="+currFrameId, 650,500);
			
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
	        		
					var res = IntoActionByUrl("${ctx}/orderinfo/torderinfo/delete.action?id="+ids);
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
          <input type="hidden" id="cuid" value="${sessionScope.UserInfo.userId}">
<!--           <epmis:button id="NewRecord" imageCss="icon-view" value="催收排行" action="rank()" datactrCode="" ></epmis:button> -->
          <epmis:button id="NewRecord" imageCss="icon-remove" value="滞纳金" action="late()" datactrCode="zg_zhinajin.edit" ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-add" value="分配" action="fp()" datactrCode="zg_fp.edit" ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-remove" value="收回" action="sh()" datactrCode="zg_sh.edit" ></epmis:button>
<!--           	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="" ></epmis:button> -->
<!--           	 <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode=""  ></epmis:button> -->
<!--           	 <epmis:button id="NewRecord" imageCss="icon-edit" value="查看" action="update()" datactrCode="" ></epmis:button> -->
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
