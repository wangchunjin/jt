<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
          function add(){

        	  var parentId="0";
              var currFrameId =  parent.frameElement.id;
        	  var node = parent.TwoFrame.$('#SysRoleTree').treegrid('getSelected');
        	  if(isNull(node)){
        		  parentId = node.ROLE_ID;
        	  }
  
               createSimpleWindow("addWindow","增加","${ctx}/web/SYS/ROLE/AddRole.jsp?currFrameId="+currFrameId+"&parentId="+parentId, 500, 300);
 
          
          }
          
        function del(){
        	    var wids="";
				var node = parent.TwoFrame.$('#SysRoleTree').treegrid('getSelected');
				if(!isNull(node)){
					parent.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
					return;
				}
				 
				  parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/role/DelRole.action?ROLE_ID="+node.ROLE_ID+"&ROLE_TYPE="+node.ROLE_TYPE);
	    		if(res[0].result=="success"){
    				      parent.TwoFrame.$('#SysRoleTree').treegrid('remove', node.ROLE_ID);  
                          parent.TwoFrame.$('#SysRoleTree').treegrid('reloadFooter');
	    			 jgjh
	    		}else{
	    			parent.TwoFrame.$.messager.alert("错误",res[0].result);
	    		}
	    		}});
              
			}
         
 
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
              <epmis:button id="export" imageCss="icon-expand" value="全部展开" action="parent.TwoFrame.exportAll('SysRoleTree')"   ></epmis:button>
             <epmis:button id="close" imageCss="icon-close" value="全部折叠" action="parent.TwoFrame.closeAll('SysRoleTree')"></epmis:button>          
             <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()"  ></epmis:button>
             <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
