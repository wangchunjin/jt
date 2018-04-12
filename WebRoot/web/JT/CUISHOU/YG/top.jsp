<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	/**
  	*测试11
  	**/
  	function cscs11(){
  	   
    	var currFrameId =  parent.frameElement.id;
    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');	
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要逾期订单！");
			return;
		}
    	
    
    	createSimpleWindow("CuishouWindow","hhh","${ctx}/web/JT/CUISHOU/YG/show.jsp?currFrameId="+currFrameId+"&id="+rows[0].order_id+"&cid="+rows[0].cid, 1200,600);
    	
		
    }
  	/**
  	*催收记录
  	**/
  	function cs(){
  	   
    	var currFrameId =  parent.frameElement.id;
    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');	
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要逾期订单！");
			return;
		}
    	
    
    	createSimpleWindow("CuishouWindow","催收记录","${ctx}/web/JT/CONNECT/RECORD/FS.jsp?id="+rows[0].order_id, 800,600);
    	
		
    }
  	
  	/**
  	*测试
  	**/
  	function cscs(){
  	   
    	var currFrameId =  parent.frameElement.id;
    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');	
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要逾期订单！");
			return;
		}    
    	
    	
    	createSimpleWindow("CuishouWindow","催收记录","${ctx}/web/JT/CUISHOU/YG/cs.jsp?id="+rows[0].order_id+"&cid="+rows[0].cid, 900,600);
    	
		
    }
  	
  	
  	
  	function lookt(){
 	   
    	var currFrameId =  parent.frameElement.id;
    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');	
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要逾期订单！");
			return;
		}
    	
    
    	
    	
		createSimpleWindow("eidtWindow","紧急联系人及通讯录信息","${ctx}/orderinfo/torderinfo/findById_lookt.action?id="+rows[0].cid+"&currFrameId="+currFrameId, 650,500);
		
    }
  	
	
  	
  		
	  	
	  	
	  	function add(){	
	  		
	  		var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","新增","${ctx}/web/JT/CUISHOU/YG/addPic.jsp?currFrameId="+currFrameId, 400,300);
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
<!--           <epmis:button id="NewRecord" imageCss="icon-edit" value="测试11" action="cscs11()" datactrCode="" ></epmis:button> -->
<!--           <epmis:button id="NewRecord" imageCss="icon-edit" value="测试" action="cscs()" datactrCode="" ></epmis:button> -->
<!--          		<epmis:button id="NewRecord" imageCss="icon-save" value="催收记录" action="cs()" datactrCode="" ></epmis:button> -->
<!--          		<epmis:button id="NewRecord" imageCss="icon-save" value="查看通讯录" action="lookt()" datactrCode="" ></epmis:button> -->
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
