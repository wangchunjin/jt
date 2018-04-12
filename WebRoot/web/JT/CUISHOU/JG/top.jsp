<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	
  	//催收排行 机构
  	function rank(){
  		
  	}
  	
	function dchu(){
		//获取当前操作人员
		var cuid=$("#cuid").val();
		var result4=GetXmlBySql("SELECT batch_status FROM batch_transfer  ORDER BY batch_id desc");
		if(result4[0].batch_status==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","上批订单还未处理。");
			return;
		}
		
		
		var result2=GetXmlBySql("select count(num)nums from (select count(lend_id)num,lend_id,(select name from lender l where l.lender_id=o.lend_id)lender_name from order_info o where approve_status='1' and transfer_status='0' and fun_change='1' and bd is NULL group BY lend_id ) c ORDER BY num desc limit 1");
		var nums=result2[0].nums;
		if(nums==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","订单正在打款或没有新的订单。");
			return;
		}else{
			//获取batch_transfer id 最大值加1
			var result3=GetXmlBySql("select max(batch_id) b from batch_transfer");
			var batch_id=result3[0].b+1;
			
			
			var result=GetXmlBySql("select num,lend_id,lender_name from (select count(lend_id)num,lend_id,(select name from lender l where l.lender_id=o.lend_id)lender_name from order_info o where approve_status='1' and transfer_status='0' and fun_change='1' and bd is NULL group BY lend_id ) c ORDER BY num desc limit 1");
			//对应时段订单中匹配最多的出借人出借人
			
			var lend_id=result[0].lend_id;
			
			//出借人姓名
			var lender_name=result[0].lender_name;
			
			parent.window.location.href="${ctx}/export/texport/FWQKexport.action?cuid="+cuid+"&lend_id="+lend_id+"&lender_name="+lender_name+"&batch_id="+batch_id;
		}
		
  	}
  	
  	
  		//分配
	  	function fp(){	  	
	  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
	  		if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要分配的订单！");
				return;
			}
	  		if(rows[0].fp_jg==1){
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
	        createSimpleWindow("addWindow","逾期订单分配","${ctx}/web/JT/CUISHOU/JG/addPic.jsp?currFrameId="+currFrameId+"&ids="+ids, 400,300);
	    }
	  	//收回
	  	function sh(){	  	
	  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
	  		if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要收回的订单！");
				return;
			}
	  		if(rows[0].fp_jg==0){
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
	        		
					var res = IntoActionByUrl("${ctx}/orderinfo/torderinfo/sh_jg.action?ids="+ids+"&cuid="+cuid);

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
	    
			
	    function daoru(){	  		
	  		var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","批量转账导入","${ctx}/web/JT/ORDER/INFO/update.jsp?currFrameId="+currFrameId, 500,400);
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
          <epmis:button id="NewRecord" imageCss="icon-add" value="分配" action="fp()" datactrCode="jg_fp.edit" ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-remove" value="收回" action="sh()" datactrCode="jg_sh.edit" ></epmis:button>
<!--           <epmis:button id="NewRecord" imageCss="icon-update" value="导入" action="daoru()" datactrCode="" ></epmis:button> -->
<!--           <epmis:button id="NewRecord" imageCss="icon-update" value="导出" action="dchu()" datactrCode="" ></epmis:button> -->
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
