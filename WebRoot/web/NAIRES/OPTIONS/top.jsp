<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	function sa(){
 	   

  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
    	
    	if(rows.length==0){    		
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑的选项！");
			return;
		}
		
		
		
		
		
		if(rows[0].type==0){
			
			
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要设为答案吗?",function(res){
	        	if(res){
					var res = IntoActionByUrl("${ctx}/naireoption/tnaireoption/sa.action?id="+rows[0].id+"&type=1");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","设置成功！");
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		if(rows[0].type==1){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要取消吗?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/naireoption/tnaireoption/sa.action?id="+rows[0].id+"&type=0");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","取消成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}

    }
  	
  
  	
  	
  	
  	
	  	function add(){	  		
	  		var currFrameId =  parent.frameElement.id; 	
	  		var nid='${param.nid}';
	        createSimpleWindow("addWindow","新增","${ctx}/web/NAIRES/OPTIONS/addPic.jsp?currFrameId="+currFrameId+"&nid="+nid, 500,300);
	    }
	    
	    
	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id;
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要修改的行！");
				return;
			}
			createSimpleWindow("eidtWindows","修改配置","${ctx}/naireoption/tnaireoption/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,500,300);
			
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
	        		
					var res = IntoActionByUrl("${ctx}/naireoption/tnaireoption/delete.action?id="+ids);
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
			 <epmis:button id="NewRecord" imageCss="icon-add" value="设为/取消答案" action="sa()" datactrCode="naire_optionsa.edit" ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="naire_optionadd.edit" ></epmis:button>
          	 <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode="naire_optiondelete.edit"  ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-edit" value="修改" action="update()" datactrCode="naire_optionupdate.edit" ></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
