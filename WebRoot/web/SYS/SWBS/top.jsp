<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">

         function add(node_type){
       	 
        	 var currFrameId =  parent.frameElement.id; 
        	  var swbs_type_id = parent.TwoFrame.$('#swbs_type_id').val();
                 if(!isStringNull(swbs_type_id)){
                	parent.TwoFrame.$.messager.alert("错误","请选择模板");
                	return;
                }
             var parentId="0"
             var node = parent.TwoFrame.$('#SwbsTree').treegrid('getSelected'); 
             var shortName="";
             if(isNull(node)){
            parentId = node.SWBS_ID;
            shortName = node.SWBS_SHORT_NAME; 
             }else{
            	 if(node_type=="TASK"){
            		parent.TwoFrame.$.messager.alert('错误','请选择父节点！');
                    return ;
            	 }
             }
             var sql= "select SWBS_SHORT_NAME from SM_SWBS WHERE SWBS_TYPE_ID = '"+swbs_type_id+"' AND PARENT_SWBS_ID = '"+parentId+"' order by seq_num desc limit 1";
             var wbsArray = GetXmlBySql(sql);
   
             var maxName="";
			var newShortName="";
			if(wbsArray.length > 0)
			{
				maxName=wbsArray[0].SWBS_SHORT_NAME;
			}
			if(shortName=="")
			{
				newShortName="";
			}else{
				var index=maxName.lastIndexOf(".");
				var val="";
			
				if(index>=0)
				{
					val=maxName.substring(index+1,maxName.length);
					newShortName=shortName+"."+(parseInt(val)+1);
				}else{
					val=shortName;
					newShortName=val+".1";
				}			
			}
		
           createSimpleWindow("addWindow","增加"+node_type,"${ctx}/web/SYS/SWBS/AddNew.jsp?parentId="+parentId+"&node_type="+node_type+"&swbs_type_id="+swbs_type_id+"&currFrameId="+currFrameId+"&newShortName="+newShortName, 500, 250);
             
          
          }
          
          function del(){
        	 
          var node = parent.TwoFrame.$('#SwbsTree').treegrid('getSelected');
         if(isNull(node)){
				   parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/swbs/delSwbs.action?swbsId="+node.SWBS_ID);
	    		if(res[0].result=="success"){
	    			   parent.TwoFrame.$('#SwbsTree').treegrid('remove', node.SWBS_ID);  
                        parent.TwoFrame.$('#SwbsTree').treegrid('reloadFooter');
	    			 
	    		}
	    		}});
         }else{
           parent.TwoFrame.$.messager.alert("错误","请选择要删除的行数据");
         }
         
          }
         
    function impModel(){
          var parentId="0"
          var currFrameId =  parent.frameElement.id; 
          var swbsTypeId = parent.LeftFrame.$('#swbsTypeName').val();
          if(isStringNull(swbsTypeId)){
           createSimpleWindow("addWindow","增加EPS","${ctx}/web/SYS/SWBS/ImpSwbs.jsp?swbsTypeId="+swbsTypeId+"&currFrameId="+currFrameId, 500, 250);
         } else{
         	parent.TwoFrame.$.messager.alert("错误","请选择模板类型");
         }
    }
    function delmodule(){
    	  parent.TwoFrame.$.messager.confirm("提醒","确定要删除模板?",function(res){
            	if(res){
            		var swbsTypeId = parent.LeftFrame.$('#swbsTypeName').val();
            		if(isStringNull(swbsTypeId)){
            			 GetXmlBySql("delete from  SM_SWBS WHERE SWBS_TYPE_ID = '"+swbsTypeId+"'");
            			  parent.TwoFrame.location.reload();
            		}
            		
            		
            	}});
    }
    
                    
        function move(){ 
        	 var swbs_type_id = parent.TwoFrame.$('#swbs_type_id').val();
                 if(!isStringNull(swbs_type_id)){
                	parent.TwoFrame.$.messager.alert("错误","请选择模板");
                	return;
                }
        	 var currFrameId =  parent.frameElement.id; 
        	 var node = parent.TwoFrame.$('#SwbsTree').treegrid('getSelected');
        	 if(isNull(node)){      
        			createSimpleWindow("addWindow","移动WBS","${ctx}/web/SYS/SWBS/move.jsp?currFrameId="+currFrameId+"&swbs_type_id="+swbs_type_id, 500, 550);	 
        	 }else{
                parent.TwoFrame.$.messager.alert("错误","请选择要移动的行数据");
             }
           }
        
            function copy(){ 
        	 var swbs_type_id = parent.TwoFrame.$('#swbs_type_id').val();
                 if(!isStringNull(swbs_type_id)){
                	parent.TwoFrame.$.messager.alert("错误","请选择模板");
                	return;
             }
        	 var currFrameId =  parent.frameElement.id; 
        	 var node = parent.TwoFrame.$('#SwbsTree').treegrid('getSelected');
        	 if(isNull(node)){ 
                createSimpleWindow("addWindow","复制WBS","${ctx}/web/SYS/SWBS/copy.jsp?currFrameId="+currFrameId+"&swbs_type_id="+swbs_type_id, 500, 550);
         	 }else{
                parent.TwoFrame.$.messager.alert("错误","请选择要复制的行数据");
             }
         }
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right"><%--
             <epmis:button id="btn2" imageCss="icon-update" value="模板同步更新" action="UpdateModel()"></epmis:button>
             --%><epmis:button id="btn3" imageCss="icon-import" value="导入模板" action="impModel()"></epmis:button>           
             <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="parent.TwoFrame.exportAll('SwbsTree')"></epmis:button>
             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="parent.TwoFrame.closeAll('SwbsTree')"></epmis:button> 
             <epmis:button id="addwbs" imageCss="icon-add" value="增加" action="add('SWBS')"></epmis:button>
             <epmis:button id="newRecord" imageCss="icon-add" value="增加作业" action="add('TASK')"></epmis:button><%--
             <epmis:button id="deletemodule" imageCss="icon-remove" value="删除模板" action="delmodule()"></epmis:button>
             --%>
             <epmis:button id="copy" imageCss="icon-copy" value="复制" action="copy()" datactrCode="" ></epmis:button>     
             <epmis:button id="move" imageCss="icon-cut" value="移动" action="move()" datactrCode="" ></epmis:button>               
             <epmis:button id="deleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>

             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
