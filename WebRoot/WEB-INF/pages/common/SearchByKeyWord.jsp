<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
             <input class="form_text"  name="KeyWord" id="KeyWord" >
             <epmis:button id="SearchRecord" imageCss="icon-search" value="查找" action="SearchByKeyWord('${param.currFrameId}','${param.frameName}')"  ></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
