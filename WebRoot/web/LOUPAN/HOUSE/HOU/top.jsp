<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	
  //楼盘户型推荐或取消推荐
  	function tjorqx(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');	
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑的户型！");
			return;
		}//这里的lid为户型id
//   		createSimpleWindow("HousepicWindow","户型图片类别","${ctx}/web/LOUPAN/HOUSE/PIC/FS.jsp?lid="+rows[0].id, 1000,600);
		if(rows[0].istuijian==0){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要推荐吗?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/house/thouse/tj.action?id="+rows[0].id+"&istj=1");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","推荐成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		
		if(rows[0].istuijian==1){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要取消推荐吗?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/house/thouse/tj.action?id="+rows[0].id+"&istj=0");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","取消推荐成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		
    	
		
		
    }
  	
  	
  	
  	
  	
  //楼盘户型图片类别
  	function pic(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');	
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑图片的户型！");
			return;
		}//这里的lid为户型id
  		createSimpleWindow("HousepicWindow","户型图片类别","${ctx}/web/LOUPAN/HOUSE/PIC/FS.jsp?lid="+rows[0].id, 1000,600);
    }
	  	function add(){
	  		var lid='${param.lid}';
	     	var currFrameId =  parent.frameElement.id;
	        createSimpleWindow("addWindow","新增","${ctx}/web/LOUPAN/HOUSE/HOU/addPingCe.jsp?currFrameId="+currFrameId+"&lid="+lid, 700,600);
	    }
	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id; 	   
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要修改的行！");
				return;
			}
			createSimpleWindow("eidtWindow","修改配置","${ctx}/house/thouse/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,700,600);
			
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
					var res = IntoActionByUrl("${ctx}/house/thouse/delete.action?id="+ids);
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
          <epmis:button id="NewRecord" imageCss="icon-add" value="推荐/取消" action="tjorqx()" datactrCode="loupan_houtjorqx.edit" ></epmis:button>
            <epmis:button id="NewRecord" imageCss="icon-add" value="图片" action="pic()" datactrCode="loupan_houpic.edit" ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="loupan_houadd.edit" ></epmis:button>
          	 <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode="loupan_houdelete.edit"  ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-edit" value="修改" action="update()" datactrCode="loupan_houupdate.edit" ></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
