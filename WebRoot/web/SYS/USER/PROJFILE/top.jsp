<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
         
         
  function SetProFile(obj){
	  var userId = '${param.USER_ID}';
       GetXmlBySql("update cm_users set profile_id='"+obj.value+"' where user_id = '"+userId+"'");
        
         var node = parent.parent.TwoFrame.$('#SysUserTable').datagrid('getSelected');
  
     	var index = parent.parent.TwoFrame.$("#SysUserTable").datagrid('getRowIndex',node);
					parent.parent.TwoFrame.$('#SysUserTable').datagrid('updateRow',{
						index: index,
						row: {
							PROFILE_NAME: $("#profile_select  option:selected").text(),
							PROFILE_ID: $("#profile_select").val() 
						}
					});
	var src = "${ctx}/web/SYS/USER/PROJFILE/content.jsp?USER_ID="+userId+"&PROFILE_ID="+obj.value;
 	parent.TwoFrame.location.href =src;
}
  </script>
  <body>
  <table width="100%">
       <tr>
           <td width="100px;" align="center" >全局安全配置： </td>
           <td width="200px;">
           <epmis:select id="profile_select" onChange="SetProFile(this)" define=" SELECT PROFILE_ID,PROFILE_NAME FROM CM_PROFILE WHERE PROF_TYPE='0'" value="${param.PROFILE_ID}"></epmis:select>
		  </td> 
          <td>&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
