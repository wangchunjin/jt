<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){
//   		 var rid = '${param.rid}';
  		
	 	 
// 		 var approve_status = $("#approve_status").val();	 	
		 var collection = $("#collection").val();	
		 
		 
	  
	  TwoFrame.location.href= "${ctx}/web/JT/CUISHOU/YG/content.jsp?approve_status=1"+"&transfer_status=3&fp_jg=1"+"&collection="+collection;
  }
  
  
  function jb(){
	  TwoFrame.location.href= "${ctx}/orderinfo/torderinfo/findById_lookt1.action?id="+${param.cid };
  }
  function txl(){
	  TwoFrame.location.href= "${ctx}/orderinfo/torderinfo/findById_lookt2.action?id="+${param.cid };
  }
  function cs(){
	  TwoFrame.location.href= "${ctx}/web/JT/CONNECT/RECORD/FS.jsp?id="+${param.id };
  }
  function closes(){
	  parent.GetIndexFrame("iframe_CUISHOUYGG").LeftFrame.TwoFrame.location.reload();
	  parent.parent.close_win('CuishouWindow');
	
  }
  
  
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0" style="background-color:#E6E6FA">
   <tr height="25" >
      
      	<input type="hidden" class="form_text" style="background-color: white;" name="title" id="title" >
      
      <td width="75" height="25" style="display: none;" align="right">&nbsp;催收状态：&nbsp;</td>
      <td width="100" style="display: none;">
       	<select id="collection" class="form_text">
       		
       		<option value="0" selected="selected">未催收</option>
       		<option value="1">已催收</option>
       		
       	</select>
      </td>
      

      
      
    

      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-save" value="基本信息" action="jb()"  ></epmis:button>
<!--           <epmis:button id="NewRecord" imageCss="icon-search" value="联系人" action="searchByKey()"  ></epmis:button> -->
          <epmis:button id="NewRecord" imageCss="icon-save" value="通讯录" action="txl()"  ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-edit" value="催收记录" action="cs()"  ></epmis:button>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-close" value="关闭" action="closes()"  ></epmis:button>
<!--          &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button> -->
         
        </td>
 
    </tr>
</table> 
</div>
<div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/orderinfo/torderinfo/findById_lookt1.action?id=${param.cid}" frameborder="0" scrolling="no"></iframe>
</div>

</body>
</html>