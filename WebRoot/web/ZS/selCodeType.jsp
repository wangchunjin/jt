<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){  		 
	 	 
	var month=$("#month").val();
	var pinyin=$("#pinyin").find("option:selected").text();
	var quyu=$("#quyu").find("option:selected").text();
	
	  TwoFrame.location.href= "${ctx}/web/ZS/content.jsp?month="+month+"&pinyin="+pinyin+"&quyu="+quyu;
  }
  
  
  
  function sr(){
	  var result = GetXmlBySql("SELECT citypinyin FROM t_dfcity WHERE cityname='"+$("#cname").val()+"'");
 	 
 	 $("#pinyin").val(result[0].citypinyin);
 	SetSelect("SELECT ids,quyuname FROM t_dfcityqy WHERE city='"+result[0].citypinyin+"'","quyu","",new Array("=--请选择--")); 
  }
  
  function check(){
	  var pinyin=$("#pinyin").val();
	  SetSelect("SELECT ids,quyuname FROM t_dfcityqy WHERE city='"+pinyin+"'","quyu","",new Array("=--请选择--")); 
  }
  
  
  
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
   <td width="75" height="25" align="right">&nbsp;月份：&nbsp;</td>
       <td width="100">      	 
      	  <input class="form_text"  name="month" id="month" readonly="readonly"  value="" onfocus="DataEdit('yyyy-MM','','')">		
       </td>
       
       <td width="75" height="25" align="right">城市名：</td>
       <td width="100">      	 
      	  <input class="form_text"  name="cname" id="cname"   value="" onkeyup="sr();" >		
       </td>
       
       
       <td width="75" height="25" align="right">城市：</td>
      	 <td width="100"> 	 
      	<epmis:select  id="pinyin" define="select citypinyin,cityname from t_dfcity  " attr="style='width: 100%;'"  onChange="check()"  value=""></epmis:select>
       </td>
       
       
       <td width="75" height="25" align="right">区县：</td>
      	 <td width="100"> 	 
      	<epmis:select  id="quyu" define="" attr="style='width: 100%;'"    value=""></epmis:select>
       </td>
      </td>
      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
      </td> 
    </tr>
</table> 
</div>
<div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/ZS/content.jsp" frameborder="0" scrolling="no"></iframe>
</div>
</body>
</html>