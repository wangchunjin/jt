<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
     function add(){
    	 var node = parent.TreeFrame.$('#SysRoleTree').treegrid('getSelected');
    	 var ROLE_ID = node.ROLE_ID;
        	  var currFrameId =  parent.frameElement.id; 
  
               createSimpleWindow("addWindow","增加用户","${ctx}/web/SYS/USER/ADD/FS.jsp?currFrameId="+currFrameId+"&ROLE_ID="+ROLE_ID, 800, 550);
 
          
          }
          
        function del(){
			     var node = parent.TwoFrame.$('#SysUserTable').datagrid('getSelected');
			    if(!isNull(node)){
			    	 parent.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
			    	return;
			    }
				  parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/user/DelUser.action?USER_ID="+node.USER_ID);
	    		if(res[0].result=="success"){
	    			 parent.TwoFrame.location.reload(); 
	    			 
	    		}
	    		}});
              
			}
         
      function UrlTo(){
	     parent.location.href = "${ctx}/web/SYS/USER/FS.jsp";
      }
 
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
             <epmis:button id="UrlTo" imageCss="icon-redo" value="按列表显示用户" action="UrlTo()"  ></epmis:button>
             <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()"  ></epmis:button>
             <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
