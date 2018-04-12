<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){
  		 
  		 
	 	 
		 var isdel = $("#isdel").val();
		 var title = $("#title").val();
		 var type = $("#type").val();
		 var shen=$("#shen").val();
		 var shi=$("#shi").val();
		 var qu=$("#qu").val();
		 var zdorqx=$("#zdorqx").val();
		 
		 if(shen==null || shen==""){
			 shen="";
		 }else{
			var result = GetXmlBySql("SELECT ssqid FROM cm_region WHERE wid="+shen);
	    	 
	    	 shen=result[0].ssqid;
		 }
		 if(shi==null || shi==""){
			 shi="";
		 }else{
				var result = GetXmlBySql("SELECT ssqid FROM cm_region WHERE wid="+shi);
	    	 
	    	 shi=result[0].ssqid;
		 }
		 if(qu==null || qu==""){
			 qu="";
		 }else{
			var result = GetXmlBySql("SELECT ssqid FROM cm_region WHERE wid="+qu);
	    	 
	    	 qu=result[0].ssqid;
		 }
		 
// 		 setTimeout( function(){ }, 1000);
	  
	  TwoFrame.location.href= "${ctx}/web/LOUPAN/LP/content.jsp?isdel="+isdel+"&title="+title+"&type="+type+"&shen="+shen+"&shi="+shi+"&qu="+qu+"&zdorqx="+zdorqx;
		
  }
  function changeValue(){
   	var obj=$("#shen").val();
   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='2' and parent_id='"+obj+"'","shi","",new Array("=--请选择--")); 
   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","qu","",new Array("=--请选择--")); 
   		
	}
  function changeValue2(){
	   	var obj=$("#shi").val();
	   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='3' and parent_id='"+obj+"'","qu","",new Array("=--请选择--")); 
	   		 
	   		
		}
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
       <td width="65" height="25" align="right">楼盘名称:</td>
      	 <td width="100"><input type="" class="form_text" style="background-color: white;" name="title" id="title" >
       </td>
       <td width="30" height="25" align="right">省份:</td>
      	<td width="80"> <epmis:select  id="shen" define="select wid,area_name from cm_region where area_type='1'"   onChange="changeValue()"></epmis:select>
       </td>
       <td width="30" height="25" align="right">城市:</td>
      	<td width="80"> 
      	<epmis:select id="shi" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select>
      
       </td>
       <td width="30" height="25" align="right">区县:</td>
      	<td width="80"> 
      	<epmis:select id="qu" define="" attr="style='width: 100%;'" ></epmis:select>
      
       </td>
       
      <td width="65" height="25" align="right">是否推荐：</td>
      <td width="80">
       	<select id="type" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">正常</option>
       		<option value="1">推荐首页</option>
       	</select>
      </td>
      <td width="45" height="25" align="right">置顶：</td>
      <td width="80">
       	<select id="zdorqx" class="form_text" >
       		<option value="">--请选择--</option>       		
       		<option value="1">置顶</option>
       	</select>
      </td>
      <td width="65" height="25" align="right">是否删除：</td>
      <td width="80">
       	<select id="isdel" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">未删除</option>
       		<option value="1">删除</option>
       	</select>
      </td>
      
      <input type="hidden" id="status" value=""/>
    
      
      </td>
      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
         </td>
 
    </tr>
</table> 
</div>
<div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/LOUPAN/LP/content.jsp" frameborder="0" scrolling="no"></iframe>
</div>
</body>
</html>