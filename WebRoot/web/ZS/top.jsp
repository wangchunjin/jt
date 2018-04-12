<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	
  	function tjorqx(){
  		
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
// 		var ids="";
// 		for(var i=0;i<rows.length;i++){
// 			if(i==0)
// 			ids=ids+rows[i].id;
// 			else
// 			ids=ids+","+rows[i].id;
// 		}			
		if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要操作的资讯！");
			return;
		}
		if(rows[0].istuijian==0){
			//针对资讯推荐达到两个后的操作
			var result = GetXmlBySql("SELECT count(id)num FROM t_cons WHERE isdel='0' and istuijian='1'");
	   	 	var sums=result[0].num;
			if(sums/1==2 || sums/1>2){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","已有2个资讯被推荐！");
				return;
			}
			
			
			
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要推荐吗?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/cons/tcons/tj.action?id="+rows[0].id+"&istuijian=1");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","推荐成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		if(rows[0].istuijian==1){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定取消推荐吗?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/cons/tcons/tj.action?id="+rows[0].id+"&istuijian=0");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","取消推荐成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		
	 }
  	
  	
  	
  	
  	
  //问题的一级评论
  	function yconscomment(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
    	
    	if(rows.length==0){
    		
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中资讯！");
			return;
		}		
		createSimpleWindow("YconscommentWindow","资讯一级评论列表","${ctx}/web/CONS/COMMENT/FS.jsp?cid="+rows[0].id, 1000,600);
	}
  	function wz(){
     	var currFrameId =  parent.frameElement.id;   
     	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要排序的首页轮播！");
			return;
		}
    	
        createSimpleWindow("WzWindow","位置展示","${ctx}/web/CONS/CONS/wz.jsp?id="+rows[0].id+"&currFrameId="+currFrameId, 400,150);
    }
	  	function add(){
	  		var cid='${param.cid}';	  		
	     	var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","新增","${ctx}/web/CONS/CONS/addPingCe.jsp?currFrameId="+currFrameId+"&cid="+cid, 800,600);
	    }
	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id; 	   
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要修改的行！");
				return;
			}
			createSimpleWindow("eidtWindow","修改配置","${ctx}/cons/tcons/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,800,600);
			
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
					var res = IntoActionByUrl("${ctx}/cons/tcons/delete.action?id="+ids);
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
<!--           <epmis:button id="NewRecord" imageCss="icon-add" value="一级评论" action="yconscomment()" datactrCode="cons_yconscomment.edit" ></epmis:button> -->
<!--              <epmis:button id="NewRecord" imageCss="icon-save" value="展示置位" action="wz()" datactrCode="" ></epmis:button> -->
<!-- 			 <epmis:button id="NewRecord" imageCss="icon-add" value="推荐/取消" action="tjorqx()" datactrCode="cons_tjqx.edit" ></epmis:button> -->
<!--           	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="cons_add.edit" ></epmis:button> -->
<!--           	 <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode="cons_delete.edit"  ></epmis:button> -->
<!--           	 <epmis:button id="NewRecord" imageCss="icon-edit" value="修改" action="update()" datactrCode="cons_update.edit" ></epmis:button> -->
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
