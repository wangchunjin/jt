<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
  function zb(){
		var currFrameId =  parent.frameElement.id;
		var row = parent.TwoFrame.$('#regionTree').treegrid('getSelected');	
// 		var level = parent.TwoFrame.$('#regionTree').treegrid('getLevel',row.WID);
		
		if(row==null){
			parent.TwoFrame.$.messager.alert("错误","请选择城市设置周边！");
			return;
		}
		
		if(row.AREA_TYPE==1 || row.AREA_TYPE==3){
			parent.TwoFrame.$.messager.alert("错误","所属周边只能设置在城市一级！");
			return;
		}
		
		createSimpleWindow("eidtWindow","修改配置","${ctx}/sys/sys_region/findById.action?wid="+row.WID+"&currFrameId="+currFrameId,400,200);
		
		
// 		if(isNull(row)){
// 			var level = parent.TwoFrame.$('#regionTree').treegrid('getLevel',row.WID);
			
// 			createSimpleWindow("addAreaWindow","增加区域","${ctx}/web/SYS/COUNTRY/add.jsp?currFrameId="+currFrameId+"&parentId="+row.WID+"&areaType="+level, 650,250);
// 		}else{
// 			createSimpleWindow("addAreaWindow","增加区域","${ctx}/web/SYS/COUNTRY/add.jsp?currFrameId="+currFrameId+"&parentId=0&areaType=0", 650,250);
// 		}
	}
  
  function delzb(){
	    var wid="";
		var row = parent.TwoFrame.$('#regionTree').treegrid('getSelected');
		if(!isNull(row)){
			parent.TwoFrame.$.messager.alert("错误","请选中要消除周边的城市！");
			return;
		}
		wid = row.WID;
		if(row.AREA_TYPE==1 || row.AREA_TYPE==3){
			parent.TwoFrame.$.messager.alert("错误","请选中城市一级消除周边！");
			return;
		}
		 
		parent.TwoFrame.$.messager.confirm("提醒","确定要消除周边的城市?",function(res){
      	if(res){
				var res = IntoActionByUrl("${ctx}/sys/sys_region/deletezb.action?wid="+wid);
	    		if(res[0].result=="success"){
	    			 parent.TwoFrame.location.reload();
	    		}
			}
      });
    
	}
  
  
  
  
  
  
  
		function add(){
			var currFrameId =  parent.frameElement.id;
			var row = parent.TwoFrame.$('#regionTree').treegrid('getSelected');
			if(isNull(row)){
				var level = parent.TwoFrame.$('#regionTree').treegrid('getLevel',row.WID);
				createSimpleWindow("addAreaWindow","增加区域","${ctx}/web/SYS/COUNTRY/add.jsp?currFrameId="+currFrameId+"&parentId="+row.WID+"&areaType="+level, 650,250);
			}else{
				createSimpleWindow("addAreaWindow","增加区域","${ctx}/web/SYS/COUNTRY/add.jsp?currFrameId="+currFrameId+"&parentId=0&areaType=0", 650,250);
			}
		}
          
        function del(){
        	    var wid="";
				var row = parent.TwoFrame.$('#regionTree').treegrid('getSelected');
				if(!isNull(row)){
					parent.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
					return;
				}
				wid = row.WID;
				 
				parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
	            	if(res){
						var res = IntoActionByUrl("${ctx}/sys/sys_region/delete.action?wid="+wid);
			    		if(res[0].result=="success"){
			    			 parent.TwoFrame.location.reload();
			    		}
	    			}
	            });
              
			}
         
  
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
          	 <epmis:button id="export" imageCss="icon-expand" value="全部展开" action="parent.TwoFrame.exportAll('regionTree')"></epmis:button>
             <epmis:button id="close" imageCss="icon-close" value="全部折叠" action=" parent.TwoFrame.closeAll('regionTree')"></epmis:button>
             <epmis:button id="NewRecord" imageCss="icon-add" value="周边" action="zb()"  ></epmis:button> 
             <epmis:button id="DeleteRecord" imageCss="icon-remove" value="消除周边" action="delzb()"></epmis:button>
<!--              <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()"  ></epmis:button> -->
<!--              <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button> -->
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
