<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
      function changeContent(obj){
    	  if(obj.value=="0"){
    		  parent.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PUBDOC/FS.jsp";
    	  }else if(obj.value=="2"){
    		  parent.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/FS.jsp";
    	  }else if(obj.value=="3"){
    		  parent.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/LANGUAGE/FS.jsp";
    	  }    	  
      }
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="290px" style="padding-left: 30px;"><input type="radio" name="docType" value="0"   onchange="changeContent(this)" >公共文档&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="docType" value="2"  checked    onchange="changeContent(this)" > 项目文档&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="docType" value="3"     onchange="changeContent(this)" > 专业术语</td>
          <td width="*" align="right">
             <epmis:button id="export" imageCss="icon-expand" value="全部展开" action="parent.TreeFrame.exportAll('ProjDocTree')" ></epmis:button>
             <epmis:button id="close" imageCss="icon-close" value="全部折叠" action="parent.TreeFrame.closeAll('ProjDocTree')"></epmis:button> 
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
