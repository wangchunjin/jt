<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
 <script type="text/javascript">
	     function AddRecord(){
	        var currFrameId = "${param.currFrameId}";
	        var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
	        
// 	        var cartypeid=$("#car\\.cartypeid").val();	        
// 	        $("#car_id").val(cartypeid);	        
	        
	        
	        
// 	        	var carbid=$("#car\\.bid").val();
// 		        if(carbid==null || carbid==""){
// 		        	$.messager.alert("错误","请选择品牌！");
// 	 	        	return;
// 		        }
// 		        var carsysid=$("#car\\.carsysid").val();
// 		        if(carsysid==null || carsysid==""){
// 		        	$.messager.alert("错误","请选择车系！");
// 	 	        	return;
// 		        }
// 		        var lid=$("#car\\.lid").val();
// 		        if(lid==null || lid==""){
// 		        	$.messager.alert("错误","请选择车款！");
// 	 	        	return;
// 		        }
// 		        var cartypeida=$("#car\\.cartypeid").val();
// 		        if(cartypeida==null || cartypeida==""){
// 		        	$.messager.alert("错误","请选择车型！");
// 	 	        	return;
// 		        }	        
		        var insurance_money=$("#insurance_money").val();
		        if(insurance_money==null || insurance_money==""){
		        	$.messager.alert("错误","保险金额不能为空！");
	 	        	return;
		        }
		        if(r1.test(insurance_money)==false){
		        	$.messager.alert("错误","保险金额只能是整数或小数！");
		        	return;
		        }
		        var insurance_money_unit=$("#insurance_money").val();
		        if(insurance_money_unit==null || insurance_money_unit==""){
		        	$.messager.alert("错误","保险金额单位不能为空！");
	 	        	return;
		        }
		        var insurance_year=$("#insurance_year").val();
		        if(insurance_year==null || insurance_year==""){
		        	$.messager.alert("错误","保险年限不能为空！");
	 	        	return;
		        }
		        if(r1.test(insurance_year)==false){
		        	$.messager.alert("错误","保险年限只能是整数或小数！");
		        	return;
		        }
		        var insurance_year_unit=$("#insurance_year_unit").val();
		        if(insurance_year_unit==null || insurance_year_unit==""){
		        	$.messager.alert("错误","保险年限单位不能为空！");
	 	        	return;
		        }
		        
		        var buy_time=$("#buy_time").val();
		        if(buy_time==null || buy_time==""){
		        	$.messager.alert("错误","购险时间不能为空！");
	 	        	return;
		        }
		        
		        var deadline_time=$("#deadline_time").val();
		        if(deadline_time==null || deadline_time==""){
		        	$.messager.alert("错误","保险期限不能为空！");
	 	        	return;
		        }
		        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        var stime=$("#buy_time").val();
	        
	        
	        var etime=$("#deadline_time").val();
	        
	        
	        
	        
	        
	       
	        
	        
 	       
	        var res = $('#addForm').SubmitForm("${ctx}/carinsurance/tcarinsurance/save.action?stime="+stime+"&etime="+etime);
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("错误!",res[0].result);
 		 	}
	
	     }
	     
	     function changeValue1(){
	    	 
		     	var obj=$("#car\\.bid").val();
		     	
		     		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID="+obj,"car\\.carsysid","",new Array("=--请选择--")); 
		     		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID=0","car\\.lid","",new Array("=--请选择--"));
		     		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID=0","car\\.cartypeid","",new Array("=--请选择--")); 
		     	
		     		
			}
			function changeValue2(){
		     	var obj=$("#car\\.carsysid").val();
		     		SetSelect("SELECT ID,TITLE FROM T_SORT WHERE isdel='0' and sid="+obj,"car\\.lid","",new Array("=--请选择--")); 
		     		SetSelect("SELECT ID,NAME FROM T_CARTYPE WHERE isdel='0' and sid=0","car\\.cartypeid","",new Array("=--请选择--")); 
		     		
			}
			function changeValue3(){
		     	
		     	var carsysid=$("#car\\.carsysid").val();
		    	var carbid=$("#car\\.bid").val();
		    	var carlid=$("#car\\.lid").val();
		     	SetSelect("SELECT id,title FROM t_car where isdel='0' and bid="+carbid+" and carsysid="+carsysid+" and lid="+carlid,"car\\.cartypeid","",new Array("=--请选择--")); 
		     	
		     		
			}
function changeValue4(){
		     	
		     	var suppid=$("#carinsurance\\.suppid").val();
		    	
		     	SetSelect("select sid,(select title from t_supp s where s.id=p.sid)title from t_supplier p where isdel='0' and ssid="+suppid,"carinsurance\\.sid","",new Array("=--请选择--")); 
		     	
		     		
			}
	     
	     
			$(function(){
				var mid='${param.uid }';//获取我的购车的id
				
				var result = GetXmlBySql("SELECT uid FROM t_mycar WHERE id="+mid+" and isdel='0' ");
		 		 $("#uid").val(result[0].uid);
		 		var result1 = GetXmlBySql("SELECT cid FROM t_mycar WHERE id="+mid+" and isdel='0' ");
		 		 $("#car_id").val(result1[0].cid);
		 		 
			})
			
			
			
	     
 </script>
 <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
    <div style="height:330px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
		<tr align="right">
<!-- 				<th width="20%"><span style="color: red;">*</span>图片：</th> -->
				<td width="10%">
				
<!-- 				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="../../../images/ic_pic_loading.png" width="50" height="40" /></a></div><br> -->
				<input class="form_text" type="hidden"  name="carinsurance.uid" id="uid"  value="">
				<input class="form_text" type="hidden"  name="carinsurance.car_id" id="car_id"  value="">
				<input class="form_text" type="hidden"  name="carinsurance.isdel" id="isdel"  value="0"></td>
			
			</tr>
			<tr style="display:none; " align="right">
			<th width="10%"><span style="color: red;"></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="previewImage(this)" /></td>
			</tr>
			<tr  id="ppp" align="right" style="display: none;">
				<th width="10%"><span style="color: red;">*</span>品牌：</th>
				<td width="12%">						
					<epmis:select id="car.bid" define="SELECT ID,TITLE FROM T_BRAND where isdel='0' " attr="style='width: 100%;'" onChange="changeValue1()" ></epmis:select>
				</td>
			</tr >
				
			<tr id="pppp" align="right" style="display: none;">
				<th width="10%"><span style="color: red;">*</span>车系：</th>
				<td width="12%">						
					<epmis:select id="car.carsysid" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select>
				</td>
			
			</tr>
			
			<tr id="ppppp" align="right" style="display: none;">
				<th width="10%"><span style="color: red;">*</span>车款：</th>
				<td width="12%">						
					<epmis:select id="car.lid" define="" attr="style='width: 100%;'" onChange="changeValue3()" ></epmis:select>
				</td>
				
				
			</tr>
			<tr id="pppppp" align="right" style="display: none;">
				<th width="10%"><span style="color: red;">*</span>车型：</th>
				<td width="12%">
					<epmis:select id="car.cartypeid" define="" attr="style='width: 100%;'" ></epmis:select>
				</td>
			</tr>
			
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>车险提供商：</th>
				<td width="12%">
					<epmis:select id="carinsurance.suppid" define="SELECT id,TITLE FROM T_supplier where isdel='0' and ssid='0' " attr="style='width: 100%;'" onChange="changeValue4()" ></epmis:select>
				</td>
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>车险：</th>
				<td width="12%">		
									
					<epmis:select id="carinsurance.sid" define="" attr="style='width: 100%;'"  ></epmis:select>
				</td>
			</tr>
			
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>保险金额：</th>
				<td width="20%"><input class="form_text"  name="carinsurance.insurance_money" id="insurance_money"  value=""></td>
			</tr >
	
			
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>保险金额单位：</th>
				<td width="20%"><input class="form_text"  name="carinsurance.insurance_money_unit" id="insurance_money_unit"  value="万"></td>
			</tr>
			
			
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>保险年限：</th>
				<td width="20%"><input class="form_text"  name="carinsurance.insurance_year" id="insurance_year"  value=""></td>
			</tr>
			
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>保险年限单位：</th>
				<td width="20%"><input class="form_text"  name="carinsurance.insurance_year_unit" id="insurance_year_unit"  value="年"></td>
			</tr>
			
			
			
			<tr align="right">
			 	<th width="10%"><span style="color: red;">*</span>购险时间：</th>
      			<td width="100">
      			<input class="form_text" onchange="findbycoi()" id="buy_time" type="text" onclick="WdatePicker({el:'buy_time'})" />
      			</td>
      		</tr>
			<tr align="right">
			 	<th width="10%"><span style="color: red;">*</span>保险期限：</th>
      			<td width="100">
      			<input class="form_text" onchange="findbycoi()" id="deadline_time" type="text" onclick="WdatePicker({el:'deadline_time'})" />
      			</td>
      		</tr>
			
			
			
			
			
			
			
			
			
			
			
			
       </table>
</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
