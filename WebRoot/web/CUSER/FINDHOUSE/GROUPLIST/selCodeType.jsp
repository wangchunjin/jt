<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){
  		 var uid = '${param.uid}';
  		 var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
	 	 
		 var isdel = $("#isdel").val();
		 var type=$("#type").val();
		 var qu="";
		 var price="";
		 var tese="";
		 var mdid="";
		 var hid="";
		 var area="";
		 var aid="";
		 if(type==1){
			 qu=$("#qu").val();			 
		 }
		 if(type==2){
			 price=$("#price").val();
			 if(price==null || trim(price)==""){
				
			 }else{
				 if(r1.test(price)==false){
					 $.messager.alert("错误","价格只能输入整数或小数");
					 return;
				 }
			 }
		 }
		 if(type==3){
			 tese=$("#tese").val();
		 }
		 if(type==4){
			 mdid=$("#mdid").val();
		 }
		 if(type==5){
			 hid=$("#hid").val();
		 }
		 if(type==6){
			 area=$("#area").val();
			 if(area==null || trim(area)==""){
				 
			 }else{
				 if(r1.test(area)==false){
					 $.messager.alert("错误","面积只能输入整数或小数");
					 return;
				 }
			 }
		 }
		 if(type==8){
			 aid=$("#aid").val();
		 }
	 	 
	  
	  TwoFrame.location.href= "${ctx}/web/CUSER/FINDHOUSE/GROUPLIST/content.jsp?isdel="+isdel+"&uid="+uid+"&type="+type+"&qu="+qu+"&price="+price+"&tese="+tese+"&mdid="+mdid+"&hid="+hid+"&area="+area+"&aid="+aid;
  }
  
  
  function changeValue1(){
	   	var obj=$("#shen").val();
	   		SetSelect("SELECT cityId,cityName FROM T_CITY WHERE provinceId="+obj,"shi","",new Array("=--请选择--")); 
	   		SetSelect("SELECT cityId,cityName FROM T_CITY WHERE provinceId=0","qu","",new Array("=--请选择--")); 
		}
  function changeValue2(){
	   	var obj=$("#shi").val();
	   		SetSelect("SELECT districtId,districtName FROM t_district WHERE cityId="+obj,"qu","",new Array("=--请选择--")); 
	   		
		}
  
  function check(){
	  
	  var type=$("#type").val();
	 
	  if(type==1){
		  $("#q1").show();
		  $("#q2").show();
		  $("#q3").show();
		  $("#q4").show();
		  $("#q5").show();
		  $("#q6").show();
		  $("#t1").hide();
		  $("#t2").hide();
		  $("#m1").hide();
		  $("#m2").hide();
		  $("#h1").hide();
		  $("#h2").hide();
		  $("#a1").hide();
		  $("#a2").hide();
		  $("#a3").hide();
		  $("#a4").hide();
		  $("#p1").hide();
		  $("#p2").hide();
	  }
	  if(type==2){
		  $("#q1").hide();
		  $("#q2").hide();
		  $("#q3").hide();
		  $("#q4").hide();
		  $("#q5").hide();
		  $("#q6").hide();
		  $("#t1").hide();
		  $("#t2").hide();
		  $("#m1").hide();
		  $("#m2").hide();
		  $("#h1").hide();
		  $("#h2").hide();
		  $("#a1").hide();
		  $("#a2").hide();
		  $("#a3").hide();
		  $("#a4").hide();
		  $("#p1").show();
		  $("#p2").show();
	  }
	  if(type==3){
		  $("#q1").hide();
		  $("#q2").hide();
		  $("#q3").hide();
		  $("#q4").hide();
		  $("#q5").hide();
		  $("#q6").hide();
		  $("#p1").hide();
		  $("#p2").hide();
		  $("#m1").hide();
		  $("#m2").hide();
		  $("#h1").hide();
		  $("#h2").hide();
		  $("#a1").hide();
		  $("#a2").hide();
		  $("#a3").hide();
		  $("#a4").hide();
		  $("#t1").show();
		  $("#t2").show();
		  
	  }
	  if(type==4){
		  $("#q1").hide();
		  $("#q2").hide();
		  $("#q3").hide();
		  $("#q4").hide();
		  $("#q5").hide();
		  $("#q6").hide();
		  $("#p1").hide();
		  $("#p2").hide();
		  $("#t1").hide();
		  $("#t2").hide();
		  $("#h1").hide();
		  $("#h2").hide();
		  $("#a1").hide();
		  $("#a2").hide();
		  $("#a3").hide();
		  $("#a4").hide();
		  $("#m1").show();
		  $("#m2").show();
		  
	  }
	  if(type==5){
		  $("#q1").hide();
		  $("#q2").hide();
		  $("#q3").hide();
		  $("#q4").hide();
		  $("#q5").hide();
		  $("#q6").hide();
		  $("#p1").hide();
		  $("#p2").hide();
		  $("#t1").hide();
		  $("#t2").hide();
		  $("#m1").hide();
		  $("#m2").hide();
		  $("#a1").hide();
		  $("#a2").hide();
		  $("#a3").hide();
		  $("#a4").hide();
		  $("#h1").show();
		  $("#h2").show();
		  
	  }
	  if(type==6){
		  $("#q1").hide();
		  $("#q2").hide();
		  $("#q3").hide();
		  $("#q4").hide();
		  $("#q5").hide();
		  $("#q6").hide();
		  $("#p1").hide();
		  $("#p2").hide();
		  $("#t1").hide();
		  $("#t2").hide();
		  $("#m1").hide();
		  $("#m2").hide();
		  $("#h1").hide();
		  $("#h2").hide();
		  $("#a3").hide();
		  $("#a4").hide();
		  $("#a1").show();
		  $("#a2").show();
		  
	  }
	  if(type==8){
		  $("#q1").hide();
		  $("#q2").hide();
		  $("#q3").hide();
		  $("#q4").hide();
		  $("#q5").hide();
		  $("#q6").hide();
		  $("#p1").hide();
		  $("#p2").hide();
		  $("#t1").hide();
		  $("#t2").hide();
		  $("#m1").hide();
		  $("#m2").hide();
		  $("#h1").hide();
		  $("#h2").hide();
		  $("#a1").hide();
		  $("#a2").hide();
		  $("#a3").show();
		  $("#a4").show();
		  
	  }
	  
  }
  
  
  
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
      
      	<input type="hidden" class="form_text" style="background-color: white;" name="title" id="title" >
      
      <td width="75" height="25" align="right">&nbsp;是否删除：&nbsp;</td>
      <td width="100">
       	<select id="isdel" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">未删除</option>
       		<option value="1">删除</option>
       	</select>
      </td>
      <td width="75" height="25" align="right">&nbsp;行为类别：&nbsp;</td>
      <td width="100">
       	<select id="type" class="form_text" onchange="check();">
       		<option value="">--请选择--</option>
       		<option value="1">区域</option>
       		<option value="2">价格</option>
       		<option value="3">类型</option>
       		<option value="4">目的</option>
       		<option value="5">户型</option>
       		<option value="6">面积</option>
       		<option value="8">周边</option>		
       	</select>
      </td>
      
      <td width="35" id="q1" height="25" align="right" style="display: none;">省：</td>
      <td width="100" id="q2" align="left" style="display: none;">
       <epmis:select id="shen" define="SELECT provinceId,provinceName FROM T_province" attr="style='width: 100%;'" onChange="changeValue1()" ></epmis:select>
      </td>      
      <td width="35" id="q3" height="25" align="right" style="display: none;">市：</td>
      <td width="100" id="q4" align="left" style="display: none;">
       <epmis:select id="shi" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select>
      </td>
      <td width="35" id="q5" height="25" align="right" style="display: none;">区：</td>
      <td width="100" id="q6" align="left" style="display: none;">
       <epmis:select id="qu" define="" attr="style='width: 100%;'" onChange="changeValue3()"></epmis:select>
      </td>
      
      <td width="50" id="p1" height="25" align="right" style="display: none;">价格：</td>
      <td width="100" id="p2" align="left" style="display: none;">
       	<input class="form_text" id="price" name="price" value="" />
      </td>
            
      <td width="120" id="t1" height="25" align="right" style="display: none;">楼盘类型标签：</td>
      <td width="100" id="t2" align="left" style="display: none;">
       	<epmis:select id="tese" define="SELECT id,name FROM t_building_type " attr="style='width: 100%;'" ></epmis:select>
      </td>
      
      <td width="120" id="m1" height="25" align="right" style="display: none;">目的标签：</td>
      <td width="100" id="m2" align="left" style="display: none;">
       	<epmis:select id="mdid" define="SELECT id,name FROM t_objective " attr="style='width: 100%;'" ></epmis:select>
      </td>
      
      <td width="120" id="h1" height="25" align="right" style="display: none;">户型标签：</td>
      <td width="100" id="h2" align="left" style="display: none;">
       	<epmis:select id="hid" define="SELECT id,name FROM t_housetype where isdel='0'" attr="style='width: 100%;'" ></epmis:select>
      </td>
       <td width="50" id="a1" height="25" align="right" style="display: none;">面积：</td>
      <td width="100" id="a2" align="left" style="display: none;">
       	<input class="form_text" id="area" name="area" value="" />
      </td>
      <td width="75" id="a3" height="25" align="right" style="display: none;">周边：</td>
      <td width="100" id="a4" style="display: none;">
       	<select id="aid" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="学校">学校</option>
       		<option value="医疗">医疗</option>
       		<option value="购物">购物</option>
       		<option value="交通">交通</option>
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
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/CUSER/FINDHOUSE/GROUPLIST/content.jsp?uid=${param.uid}" frameborder="0" scrolling="no"></iframe>
</div>

</body>
</html>