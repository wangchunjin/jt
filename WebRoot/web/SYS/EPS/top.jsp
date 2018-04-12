<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
             var parentId="0"
             var node = parent.TwoFrame.$('#EpsTree').treegrid('getSelected');
             if(isNull(node)){
            parentId = node.id;
             }
           createSimpleWindow("addEpsWindow","增加EPS","${ctx}/web/SYS/EPS/AddEps.jsp?currFrameId="+currFrameId+"&parentId="+parentId, 500, 250);
             
          
          }
          
          function del(){
        	 
          var node = parent.TwoFrame.$('#EpsTree').treegrid('getSelected');
         if(isNull(node)){
            parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
        	 $.ajax({
	                	url : "${ctx}/sys/eps/DelEps.action",
	                	type: 'post',
	                    dataType: 'json',
	                    async : false,
	                    data:{ 
	                    	"proj_id" : node.id
	                    },
	                    error: function(){
	                    	parent.TwoFrame.$.messager.alert('错误','删除时出错！');
	                    },
	                    success: function(data){
	                        if(data[0].result=="success"){
	                                 
	                          parent.TwoFrame.$('#EpsTree').treegrid('remove', node.id);  
                              parent.TwoFrame.$('#EpsTree').treegrid('reloadFooter');
	                        }else{
	                        	parent.TwoFrame.$.messager.alert('错误',data[0].result);
	                        }
	                    }
	                });	
        	 }
        	 });
         }else{
           parent.TwoFrame.$.messager.alert("错误","请选择要删除的行数据");
         }
         
          }
         function move(){
        	 var currFrameId =  parent.frameElement.id; 
        	 var node = parent.TwoFrame.$('#EpsTree').treegrid('getSelected');
        	 if(isNull(node)){
        		 createSimpleWindow("addWindow","移动EPS","${ctx}/web/SYS/EPS/move.jsp?currFrameId="+currFrameId, 500, 550);
 	 
        	 }else{
                parent.TwoFrame.$.messager.alert("错误","请选择要移动的行数据");
             }
         }
  
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
             <epmis:button id="export" imageCss="icon-expand" value="全部展开" action="parent.TwoFrame.exportAll('EpsTree')"   ></epmis:button>
             <epmis:button id="close" imageCss="icon-close" value="全部折叠" action="parent.TwoFrame.closeAll('EpsTree')"></epmis:button> 
             <epmis:button id="addNew" imageCss="icon-add" value="增加" action="add()"  ></epmis:button>
             <epmis:button id="move" imageCss="icon-cut" value="移动" action="move()"  ></epmis:button>
             <epmis:button id="Delete" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
