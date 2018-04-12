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
		 var fp_zg = $("#fp_zg").val();	
		 var zgtime = $("#zgtime").val();	
		 var jgtime = $("#jgtime").val();
		 var mobile =$("#mobile").val();
		 var jg=$("#jg").val();
		 var cy=$("#cy").val();
		 
	  
	  TwoFrame.location.href= "${ctx}/web/JT/CUISHOU/ZG/content.jsp?approve_status=1"+"&transfer_status=3"+"&fp_zg="+fp_zg+"&zgtime="+zgtime+"&jgtime="+jgtime+"&mobile="+mobile+"&jg="+jg+"&cy="+cy;
  }
  $(function(){
	  var obj='${sessionScope.UserInfo.userId}';
	   	
 		SetSelect("select user_id,actual_name from cm_users where subsidiary='"+obj+"'","jg","",new Array("=--请选择--")); 
//  		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID=0","car\\.lid","",new Array("=--请选择--"));
//  		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID=0","car\\.cartypeid","",new Array("=--请选择--")); 
 	
  });
  function changeValue(){
   	var obj=$("#jg").val();
   		SetSelect("select user_id,actual_name from cm_users where subsidiary='"+obj+"'","cy","",new Array("=--请选择--")); 
   		
   		
	}

</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
      
      	<input type="hidden" class="form_text" style="background-color: white;" name="title" id="title" >
      
      <td width="75" height="25" align="right">&nbsp;分配状态：&nbsp;</td>
      <td width="50">
       	<select id="fp_zg" class="form_text">
       		
       		<option value="0" selected="selected">未分配</option>
       		<option value="1">已分配</option>
       		
       	</select>
      </td>
      <td width="55" height="25" align="right">手机号：</td>
      <td width="70">       	
       		<input class="form_text" name="mobile" id="mobile"/>
       		
       
      </td>
      <td width="75" height="25" align="right">所属机构：</td>
             	
       	<td width="70">
       		<epmis:select id="jg" define="" attr="style='width: 100%;'" onChange="changeValue()" ></epmis:select>
     	</td>     		
       
      <td width="75" height="25" align="right">所属成员：</td>
             	
       	<td width="70">
       		<epmis:select id="cy" define="" attr="style='width: 100%;'" ></epmis:select>
     	</td>
      <td width="100" height="25" align="right">主管分配时间：</td>
      <td width="70">       	
       		<input class="form_text" onchange="findbycoi()" id="zgtime" type="text" readonly="readonly" onclick="WdatePicker({el:'zgtime'})" />
       		
       
      </td>
	  <td width="100" height="25" align="right">机构分配时间：</td>
      <td width="70">
       <input class="form_text" onchange="findbycoi()" id="jgtime" type="text" readonly="readonly" onclick="WdatePicker({el:'jgtime'})" />
      </td>
      
      
    

      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
         </td>
 
    </tr>
</table> 
</div>
<div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/JT/CUISHOU/ZG/content.jsp?fp_zg=0" frameborder="0" scrolling="no"></iframe>
</div>

</body>
</html>