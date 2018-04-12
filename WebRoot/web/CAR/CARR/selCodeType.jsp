<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){
	  var r2=/^[0-9]*$/;//判断是否是整数
	  var title= $("#title").val();	  
	  var csid= $("#csid").val();	
	  if(r2.test(csid)==false){
		  $.messager.alert("错误","搜索汽车ID只能是整数！");
		  return;
	  }
	  var isdel = $("#isdel").val();
	  var status = $("#status").val();
	  var xin = "";
	  var jid=$("#car\\.jid").val();
	  var bid=$("#car\\.bid").val();
	  var gid=$("#car\\.gid").val();
	  var carsysid=$("#car\\.carsysid").val();
	  var type=$("#type").val();
	  
	  if(jid==0){
		  jid="";
	  }
	  if(bid==0){
		  bid="";
	  }
	  if(gid==0){
		  gid="";
	  }
	  if(carsysid==0){
		  carsysid="";
	  }
	  if(isdel==1){
	  	  TwoFrame.location.href= "${ctx}/web/CAR/CARR/content.jsp?title="+encodeURI(title)+"&isdel="+isdel+"&status="+status+"&xin="+xin+"&bid="+bid+"&jid="+jid+"&gid="+gid+"&carsysid="+carsysid+"&type="+type+"&csid="+csid;
	  }else{
		  TwoFrame.location.href= "${ctx}/web/CAR/CARR/contentTwo.jsp?title="+encodeURI(title)+"&isdel="+isdel+"&status="+status+"&xin="+xin+"&bid="+bid+"&jid="+jid+"&gid="+gid+"&carsysid="+carsysid+"&type="+type+"&csid="+csid;
	  }
  }
  function changeValue(){
   	var obj=$("#car\\.bid").val();
   		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID="+obj,"car\\.carsysid","",new Array("=--请选择--")); 
   		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID=0","car\\.lid","",new Array("=--请选择--"));
   		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID=0","car\\.cartypeid","",new Array("=--请选择--")); 
   	
   		
	}
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 80px;">

<table width="100%" border="0">
   <tr height="25" >
      <td width="75" height="25" align="right">汽车标题：</td>
      <td width="100">
      	<input class="form_text" style="background-color: white;" name="title" id="title" >
      </td>
       <td width="80" height="25" align="right">汽车ID：</td>
      <td width="80">
       	<input class="form_text" style="background-color: white;" name="csid" id="csid" >
      </td> 
      
      <td width="50" height="25" align="right">品牌：</td>
      <td width="80">
       	<epmis:select id="car.bid" define="select id,title from t_brand where isdel='0' ORDER BY CONVERT( title USING gbk ) COLLATE gbk_chinese_ci ASC " attr="style='width: 100%;'" onChange="changeValue()" ></epmis:select>
      </td>
      <td width="50" height="25" align="right">车系：</td>
      <td width="80">
       	<epmis:select id="car.carsysid" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select>
      </td>
      
      <td width="50" height="25" align="right">级别：</td>
      <td width="80">
       	<epmis:select id="car.jid" define="SELECT ID,TITLE FROM T_LEVEL where isdel='0' " attr="style='width: 100%;'" ></epmis:select>
      </td>
      <td width="100" height="25" align="right">官方指导价(万)：</td>
      <td width="80">
       	<epmis:select id="car.gid" define="SELECT ID, CASE WHEN MINPRICE is null THEN CONCAT(maxprice,'万以下') WHEN maxprice is null THEN CONCAT(MINPRICE,'万以上') WHEN MINPRICE is not null and maxprice is not null THEN CONCAT(MINPRICE,'-',maxprice,'万') END AS price FROM T_OFFICIALPRICE where  isdel='0' and TYPE='1' ORDER BY id desc" attr="style='width: 100%;'"  ></epmis:select>
      </td>
      <td width="75" height="25" align="right">&nbsp;汽车状态：&nbsp;</td>
      <td width="80">
       	<select id="status" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">正常</option>
<!--        		<option value="1">热卖(热卖)</option> -->
       		<option value="2">新品</option>
<!--        		<option value="3">热门推荐</option> -->
       	</select>
      </td>
     
      
<!--       <td width="50" height="25" align="right">首付：</td> -->
<!--       <td width="100"> -->
<!--        	<epmis:select id="car.sid" define="SELECT ID,CONCAT(MINPRICE,'-',maxprice)MINPRICE  FROM T_OFFICIALPRICE WHERE isdel='0' and TYPE='2'" attr="style='width: 100%;'" ></epmis:select> -->
<!--       </td> -->
<!--       <td width="50" height="25" align="right">月供：</td> -->
<!--       <td width="100"> -->
<!--        	<epmis:select id="car.yid" define="SELECT ID,CONCAT(MINPRICE,'-',maxprice)MINPRICE  FROM T_OFFICIALPRICE WHERE isdel='0' and TYPE='3'" attr="style='width: 100%;'" ></epmis:select> -->
<!--       </td> -->
      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
         </td>
         
 
    </tr>
    <tr height="25" >
    	<td width="80" height="20" align="right"> 显示/隐藏：</td>
    	<td width="80">
       	<select id="type" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">显示</option>
       		<option value="1">隐藏</option>
       	</select>
      </td> 
      <td width="80" height="25" align="right">是否删除：</td>
      <td width="80">
       	<select id="isdel" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">未删除</option>
       		<option value="1">删除</option>
       	</select>
      </td> 
   </tr>
</table> 
</div>
<div style="position: absolute;top: 120px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/CAR/CARR/contentTwo.jsp" frameborder="0" scrolling="no"></iframe>
</div>
</body>
</html>