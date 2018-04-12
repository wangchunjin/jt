<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
  		  
          function addSubscrit(){
        	  var currFrameId =  parent.frameElement.id; 
        	  var node = parent.TwoFrame.$('#NewUserTable').datagrid('getSelected');
        	  if(!isNull(node)){
			    	 parent.TwoFrame.$.messager.alert("错误","请选中要新增预约单的新用户！");
			    	return;
			  }
        	  var realName = node.realName;
        	  var telephone = node.telephone;
        	  if(node.issub!=0){
        		  parent.TwoFrame.$.messager.alert("错误","该新用户已生成预约单");
			    	return;
        	  }
        	  createSimpleWindow("addWindow","新用户新增预约单","${ctx}/web/JP/SUB/XZC/ADD/FS.jsp?currFrameId="+currFrameId+"&realName="+realName+"&telephone="+telephone,  600, 600);
        	 
          }
        
	 function SetChange(){
    	 parent.TwoFrame.$('#NewUserTable').datagrid('clearSelections');
    	 parent.TwoFrame.$('#NewUserTable').datagrid('load',{ 
    		 name:$("#name").val(),
    		 phone:$("#phone").val()
    		});
     }
	 
			 var i =10;//5秒
			function timer(){
	  				i--;
	 			if(i==0){
		 			var res = IntoActionByUrl("${ctx}/jp/sub/tuisongwb.action");
		 			}
	 			setTimeout(timer,1000);
	 		}
  </script>
  <body>
  <table width="100%">
       <tr>
		   <td>
		   		&nbsp;&nbsp;手机号：<input class="label_text" id="phone" style="width:130px;" />&nbsp;&nbsp;
		   		姓名：<input class="label_text" id="name" style="width:130px;" />&nbsp;&nbsp;
		       
		       <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="SetChange()"  ></epmis:button>
		       
		  </td>
          <td width="*" align="right">
             <epmis:button id="add" imageCss="icon-add" value="编辑预约单" action="addSubscrit()" datactrCode="tuser_newupdate.edit"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
