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
      
	var src = "${ctx}/web/SYS/USER/PROJFILE/content.jsp?USER_ID="+userId+"&PROFILE_ID="+obj.value;
 	document.all.contentFrame.src =src; 
}
  function AddRecord(){
	  var userId = '${param.USER_ID}';
	  parent.OneFrame.nextstep('6',userId);	  
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
  <iframe id="contentFrame" src="${ctx}/web/SYS/USER/PROJFILE/content.jsp?USER_ID=${param.USER_ID}&PROFILE_ID=${param.PROFILE_ID}" width="100%" height="400px;" frameborder="0"></iframe>
 	    <div class="BottomDiv">

					<a href="###" class="btn_01" onclick="AddRecord()">下一步<b></b></a>				
			</div>
  </body>
</html>
