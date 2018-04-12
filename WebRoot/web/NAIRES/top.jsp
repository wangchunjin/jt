<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	
  	
    //设置首个问题
  	function typeupdate(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
    	
    	if(rows.length==0){
    		
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要设置初始问题的问题！");
			return;
		}
		if(rows[0].type==0){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定设置为初始为题吗?",function(res){
	        	if(res){
					var res = IntoActionByUrl("${ctx}/naire/tnaire/typeupdate.action?id="+rows[0].id+"&shen="+rows[0].shens+"&shi="+rows[0].shis+"&type=1");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
		    		
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","设置成功！");
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}else{
	    				
		    			
	    				parent.LeftFrame.TwoFrame.$.messager.alert("提醒",res[0].result);
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		if(rows[0].type==1){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要取消初始问题吗?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/naire/tnaire/typeupdate.action?id="+rows[0].id+"&shen="+rows[0].shens+"&shi="+rows[0].shis+"&type=0");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
		    			
		    			
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","取消成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}else{
	    				
	    				
	    				parent.LeftFrame.TwoFrame.$.messager.alert("提醒",res[0].result);
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		}
  	
  	
  	
  	
  	
  	
  	
  	
  	function look(){
 	   
    	var currFrameId =  parent.frameElement.id;
    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的反馈！");
			return;
		}
		createSimpleWindow("eidtWindow","查看反馈","${ctx}/web/NAIRES/FS.jsp?tid="+rows[0].id+"&currFrameId="+currFrameId,800,600);
		
    }
  	
  	function option(){
  	   
    	var currFrameId =  parent.frameElement.id;
    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看选项的问题！");
			return;
		}
		createSimpleWindow("eidtWindow","查看选项","${ctx}/web/NAIRES/OPTIONS/FS.jsp?nid="+rows[0].id+"&currFrameId="+currFrameId,800,600);
		
    }
    	
  	
  	
  	
  	
	  	function add(){	  		
	  		var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","新增","${ctx}/web/NAIRES/addPic.jsp?currFrameId="+currFrameId, 400,300);
	    }
	    
	    
	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id;
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要修改的行！");
				return;
			}
			createSimpleWindow("eidtWindow","修改配置","${ctx}/naire/tnaire/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,400,300);
			
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
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
				return;
			}
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
			
	        	if(res){
	        		
					var res = IntoActionByUrl("${ctx}/naire/tnaire/delete.action?id="+ids);
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
<!--           <epmis:button id="NewRecord" imageCss="icon-add" value="查看" action="look()" datactrCode="" ></epmis:button> -->
			 <epmis:button id="NewRecord" imageCss="icon-add" value="选项" action="option()" datactrCode="naire_option.edit" ></epmis:button>
			 <epmis:button id="NewRecord" imageCss="icon-add" value="设置首个问题" action="typeupdate()" datactrCode="naire_typeone.edit" ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="naire_add.edit" ></epmis:button>
          	 <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode="naire_delete.edit"  ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-edit" value="修改" action="update()" datactrCode="naire_update.edit" ></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
