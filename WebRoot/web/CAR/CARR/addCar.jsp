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
	        var r2=/^[0-9]*$/;//判断是否是整数
	        var carbid = $("#car\\.bid").val();
	        if(carbid==null||carbid==""){
	        	$.messager.alert("错误","请选择品牌！");
	        	return;
	        }
	        var carsysid = $("#car\\.carsysid").val();
	        if(carsysid==null||carsysid==""){
	        	$.messager.alert("错误","请选择车系！");
	        	return;
	        }
	        var carlid = $("#car\\.lid").val();
	        if(carlid==null||carlid==""){
	        	$.messager.alert("错误","请选择车款！");
	        	return;
	        }
	       
	        
	        var carjid = $("#car\\.jid").val();
	        if(carjid==null||carjid==""){
	        	$.messager.alert("错误","请选择级别！");
	        	return;
	        }
 	        var tiltle = $("#title").val();
 	        if(tiltle==null||tiltle==""){
 	        	$.messager.alert("错误","车型名称不能为空！");
 	        	return;
 	        }
 	       var productimg1 = $("#productimg1").val();
	        if(productimg1==null||productimg1==""){
	        	$.messager.alert("错误","请选择车型主图片！");
	        	return;
	        }
	        var productimg2 = $("#productimg2").val();
	        if(productimg2==null||productimg2==""){
	        	$.messager.alert("错误","请选择车型小图片！");
	        	return;
	        }
 	        
 	       
 	       var official_price = $("#official_price").val();
	        if(official_price==null||official_price==""){
	        	$.messager.alert("错误","官方指导价不能为空！");
	        	return;
	        }
	        
	        if(r1.test(official_price)==false){
	        	$.messager.alert("错误","官方指导价只能是整数或小数！");
	        	return;
	        }
	        
	        
	        var official_unit = $("#official_unit").val();
 	        if(official_unit==null||official_unit==""){
 	        	$.messager.alert("错误","官方指导价单位不能为空！");
 	        	return;
 	        }
 	       var service_charge = $("#service_charge").val();
	        if(service_charge==null||service_charge==""){
	        	$.messager.alert("错误","履约保证金不能为空！");
	        	return;
	        }
	        if(r1.test(service_charge)==false){
	        	$.messager.alert("错误","履约保证金只能是整数或小数！");
	        	return;
	        }
	        
	        var service_charge_unit = $("#service_charge_unit").val();
	        if(service_charge_unit==null||service_charge_unit==""){
	        	$.messager.alert("错误","请填写履约保证金单位！");
	        	return;
	        }
 	      
 	        
 	       var monthly_payments = $("#monthly_payments").val();
	        if(monthly_payments==null||monthly_payments==""){
	        	$.messager.alert("错误","月供价格不能为空！");
	        	return;
	        }
	        if(r1.test(monthly_payments)==false){
	        	$.messager.alert("错误","月供价格只能是整数或小数！");
	        	return;
	        }
	        
	        
	        var monthly_unit = $("#monthly_unit").val();
 	        if(monthly_unit==null||monthly_unit==""){
 	        	$.messager.alert("错误","月供单位不能为空！");
 	        	return;
 	        }
 	       var down_payment = $("#down_payment").val();
	        if(down_payment==null||down_payment==""){
	        	$.messager.alert("错误","首付价格不能为空！");
	        	return;
	        }
	        if(r1.test(down_payment)==false){
	        	$.messager.alert("错误","首付价格只能是整数或小数！");
	        	return;
	        }
	        
	        var down_unit = $("#down_unit").val();
	        if(down_unit==null||down_unit==""){
	        	$.messager.alert("错误","首付单位不能为空！");
	        	return;
	        }
 	       var cash_deposit = $("#cash_deposit").val();
	        if(cash_deposit==null||cash_deposit==""){
	        	$.messager.alert("错误","保证金不能为空！");
	        	return;
	        }
	        if(r1.test(cash_deposit)==false){
	        	$.messager.alert("错误","保证金只能是整数或小数！");
	        	return;
	        }
	        
	        var purchase_tax = $("#purchase_tax").val();
 	        if(purchase_tax==null||purchase_tax==""){
 	        	$.messager.alert("错误","购置税不能为空！");
 	        	return;
 	        }
 	       if(r1.test(purchase_tax)==false){
	        	$.messager.alert("错误","购置税只能是整数或小数！");
	        	return;
	        }
 	       var insurance = $("#insurance").val();
	        if(insurance==null||insurance==""){
	        	$.messager.alert("错误","保险不能为空！");
	        	return;
	        }
	        if(r1.test(insurance)==false){
	        	$.messager.alert("错误","保险只能是整数或小数！");
	        	return;
	        }
	        var car_size = $("#car_size").val();
 	        if(car_size==null||car_size==""){
 	        	$.messager.alert("错误","车身尺寸不能为空！");
 	        	return;
 	        }
 	       var car_construction = $("#car_construction").val();
	        if(car_construction==null||car_construction==""){
	        	$.messager.alert("错误","车结构不能为空！");
	        	return;
	        }
	        var engine = $("#engine").val();
 	        if(engine==null||engine==""){
 	        	$.messager.alert("错误","发动机不能为空！");
 	        	return;
 	        }
 	       
// 	        var caryid = $("#car\\.yid").val();
// 	        if(caryid==null||caryid==""){
// 	        	$.messager.alert("错误","请选择最低月供！");
// 	        	return;
// 	        }
	        
// 	        var cargid = $("#car\\.gid").val();
// 	        if(cargid==null||cargid==""){
// 	        	$.messager.alert("错误","请选择最低官方指导价！");
// 	        	return;
// 	        }
	       
// 	        var carsid = $("#car\\.sid").val();
// 	        if(carsid==null||carsid==""){
// 	        	$.messager.alert("错误","请选择最低首付价！");
// 	        	return;
// 	        }
	       
	        
	        var naked_price = $("#naked_price").val();
	        if(naked_price==null||naked_price==""){
	        	$.messager.alert("错误","裸车价格不能为空！");
	        	return;
	        }
	        if(r1.test(naked_price)==false){
	        	$.messager.alert("错误","裸车价格只能是整数或小数！");
	        	return;
	        }
	        var naked_price_unit = $("#naked_price_unit").val();
	        if(naked_price_unit==null||naked_price_unit==""){
	        	$.messager.alert("错误","裸车单位不能为空！");
	        	return;
	        }
// 	        var car_title = $("#car_title").val();
// 	        if(car_title==null||car_title==""){
// 	        	$.messager.alert("错误","汽车车型视频标题不能为空！");
// 	        	return;
// 	        }
// 	        var productimg3 = $("#productimg3").val();
// 	        if(productimg3==null||productimg3==""){
// 	        	$.messager.alert("错误","请上传汽车车型视频！");
// 	        	return;
// 	        }
	        
	        
// 	        var buy_car_flow = $("#buy_car_flow").val();
// 	        if(buy_car_flow==null||buy_car_flow==""){
// 	        	$.messager.alert("错误","购车流程不能为空！");
// 	        	return;
// 	        }
// 	        var car_long = $("#car_long").val();
// 	        if(car_long==null||car_long==""){
// 	        	$.messager.alert("错误","车长不能为空！");
// 	        	return;
// 	        }
// 	        if(r2.test(car_long)==false){
// 	        	$.messager.alert("错误","车长只能是整数！");
// 	        	return;
// 	        }
// 	        var car_wide = $("#car_wide").val();
// 	        if(car_wide==null||car_wide==""){
// 	        	$.messager.alert("错误","车宽不能为空！");
// 	        	return;
// 	        }
// 	        if(r2.test(car_wide)==false){
// 	        	$.messager.alert("错误","车宽只能是整数！");
// 	        	return;
// 	        }
// 	        var car_hight = $("#car_hight").val();
// 	        if(car_hight==null||car_hight==""){
// 	        	$.messager.alert("错误","车高不能为空！");
// 	        	return;
// 	        }
// 	        if(r2.test(car_hight)==false){
// 	        	$.messager.alert("错误","车高只能是整数！");
// 	        	return;
// 	        }

	       
// 	        var car_min_space = $("#car_min_space").val();
// 	        if(car_min_space==null||car_min_space==""){
// 	        	$.messager.alert("错误","最小离地距离不能为空！");
// 	        	return;
// 	        }
// 	        if(r2.test(car_min_space)==false){
// 	        	$.messager.alert("错误","最小离地距离只能是整数！");
// 	        	return;
// 	        }
// 	        var wheel_base = $("#wheel_base").val();
// 	        if(wheel_base==null||wheel_base==""){
// 	        	$.messager.alert("错误","轴距不能为空！");
// 	        	return;
// 	        }
// 	        if(r2.test(wheel_base)==false){
// 	        	$.messager.alert("错误","轴距只能是整数！");
// 	        	return;
// 	        }
// 	        var warranty_policy = $("#warranty_policy").val();
// 	        if(warranty_policy==null||warranty_policy==""){
// 	        	$.messager.alert("错误","保修政策不能为空！");
// 	        	return;
// 	        }
// 	        var displacement = $("#displacement").val();
// 	        if(displacement==null||displacement==""){
// 	        	$.messager.alert("错误","排量不能为空！");
// 	        	return;
// 	        }
// 	        var power = $("#power").val();
// 	        if(power==null||power==""){
// 	        	$.messager.alert("错误","功率不能为空！");
// 	        	return;
// 	        }
// 	        var Ministry_industry = $("#Ministry_industry").val();
// 	        if(Ministry_industry==null||Ministry_industry==""){
// 	        	$.messager.alert("错误","工信部不能为空！");
// 	        	return;
// 	        }
// 	        var official = $("#official").val();
// 	        if(official==null||official==""){
// 	        	$.messager.alert("错误","官方不能为空！");
// 	        	return;
// 	        }
	        
	        var sales_volume = $("#sales_volume").val();
// 	        if(sales_volume==null||sales_volume==""){
// 	        	$.messager.alert("错误","新车销量默认为0！");
// 	        	return;
// 	        }
	        if(r2.test(sales_volume)==false){
	        	$.messager.alert("错误","新车销量只能是整数！");
	        	return;
	        }
	        
	        var stock = $("#stock").val();
// 	        if(stock==null||stock==""){
// 	        	$.messager.alert("错误","请填写新车库存！");
// 	        	return;
// 	        }
	        if(r2.test(stock)==false){
	        	$.messager.alert("错误","新车库存只能是整数！");
	        	return;
	        }
	        var jiangjia = $("#jiangjia").val();
	        if(jiangjia==null||jiangjia==""){
	        	$.messager.alert("错误","此车降价不能为空！");
	        	return;
	        }
	        
// 	        if(r1.test(jiangjia)==false){
// 	        	$.messager.alert("错误","此车降价只能是整数或小数！");
// 	        	return;
// 	        }
	        
	        var jiangjiaunit = $("#jiangjiaunit").val();
	        if(jiangjiaunit==null||jiangjiaunit==""){
	        	$.messager.alert("错误","请填写降价单位！");
	        	return;
	        }
	        
	        
	        
	        
	        
// 	        var tallest = $("#tallest").val();
// 	        if(tallest==null||tallest==""){
// 	        	$.messager.alert("错误","最高不能为空！");
// 	        	return;
// 	        }
	        
	        var xin = $("#xin").val();
	        if(xin==null||xin==""){
	        	$.messager.alert("错误","请选择星级！");
	        	return;
	        }
			

	        var res = $('#addForm').SubmitForm("${ctx}/car/tcar/save.action");
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
	     }
	     /* function getUrl(node,imgId){   
			    var file = null;  
			    if(node.files && node.files[0] ){  
			        file = node.files[0]; 
			    }else if(node.files && node.files.item(0)) {                                  
			        file = node.files.item(0);     
			    }
			      //$(node).parent().find('img')[0].src  = getObjectURL(file);  
			      
			} 
			function getObjectURL(file) {
			    var url = null;
			    if (window.createObjectURL != undefined) { // basic
			        url = window.createObjectURL(file);
			    } else if (window.URL != undefined) { // mozilla(firefox)
			        url = window.URL.createObjectURL(file);//实现本地浏览
			    } else if (window.webkitURL != undefined) { // webkit or chrome
			        url = window.webkitURL.createObjectURL(file);
			    }
			    return url;
			}*/
			function getUrl(node,imgId){   
			    var file = null;  
			    if(node.files && node.files[0] ){  
			        file = node.files[0]; 
			    }else if(node.files && node.files.item(0)) {                                  
			        file = node.files.item(0);     
			    }
// 			    alert(getObjectURL(file));
			    $("#"+imgId).attr("src",getObjectURL(file));  
			    
			      //$(node).parent().find('img')[0].attr("src",getObjectURL(file));  
			}
			function getObjectURL(file) {
			    var url = null;
			    if (window.createObjectURL != undefined) { // basic
			        url = window.createObjectURL(file);
			    } else if (window.URL != undefined) { // mozilla(firefox)
			        url = window.URL.createObjectURL(file);//实现本地浏览
			    } else if (window.webkitURL != undefined) { // webkit or chrome
			        url = window.webkitURL.createObjectURL(file);
			    }
			    return url;
			}
			
			
			
			function changeValue(){
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
		     	var obj=$("#car\\.lid").val();
		     		SetSelect("SELECT ID,NAME FROM T_CARTYPE WHERE isdel='0' and sid="+obj,"car\\.cartypeid","",new Array("=--请选择--")); 
		     	
		     		
			}
			
			function jisuan(){
				var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
				var price=$("#naked_price").val();
				if(r1.test(price)==false){
					$.messager.alert("错误","裸车价只能输入整数或小数！");
		        	return;
				}
// 				SetSelect("SELECT id,fuwuBaifenbi FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
// 				var sysid=$("#carbid").find("option:selected").text();	
// 				var k=sysid/100;				
				
// 				var p=accMul(price,k);				
// 				赋值
// 				var c=Math.floor(p * 10000) ;
// 				$("#service_charge").val(c);
				
				//获取保险的百分比
				SetSelect("SELECT id,bxBaifenbi FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var baoxian=$("#carbid").find("option:selected").text();
				var k1=baoxian/100;				
				
				var p1=accMul(price,k1);
				//获取保险的常量
				SetSelect("SELECT id,bxChangshu FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var cl=$("#carbid").find("option:selected").text();
				var shu=cl/10000;
				
				var pric=shu+p1;
				
				//赋值
				var cc=Math.floor(pric * 10000);
				$("#insurance").val(cc);
				
				//获取购置税计算 除的 常数
				SetSelect("SELECT id,gzsJishu FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var gshu=$("#carbid").find("option:selected").text();
				var k2=price/gshu;
				
				
				//获取购置税的百分比
				SetSelect("SELECT id,gzsBaifenbi FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var gbai=$("#carbid").find("option:selected").text();
				var gbai1=gbai/100;
				
				
				var p3=accMul(k2,gbai1);
				var ccc=Math.floor(p3 * 10000);
				
				//赋值
				$("#purchase_tax").val(ccc);
				
				
				//以下附加
				var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
				var official_price=$("#official_price").val();
				
				if(r1.test(official_price)==false){
// 					$.messager.alert("错误","官方指导价只能输入整数或小数！");
		        	return;
				}
				var naked_price=$("#naked_price").val();
				
				
				
				var k=(official_price/1+0.1)-(naked_price/1+0.1);
				
				var sss=Math.round(k * 100) / 100;
				$("#jiangjia").val(sss);
				
				SetSelect("SELECT id,fuwuBaifenbi FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var sysid=$("#carbid").find("option:selected").text();	
				var k=sysid/100;				
				
				var p=accMul(official_price,k);				
				//赋值
				var c=Math.floor(p * 10000) ;
				$("#service_charge").val(c);
				
			}
			function accMul(arg1,arg2) 
			{ 
				
				var m=0,s1=arg1.toString(),s2=arg2.toString(); 
				try{m+=s1.split(".")[1].length}catch(e){} 
				try{m+=s2.split(".")[1].length}catch(e){} 
				
				return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
			}
			
			function jiss(){
				
				var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
				var official_price=$("#official_price").val();
				
				if(r1.test(official_price)==false){
					$.messager.alert("错误","官方指导价只能输入整数或小数！");
		        	return;
				}
				var naked_price=$("#naked_price").val();
				
				var k=(official_price/1+0.1)-(naked_price/1+0.1);
				
				var sss=Math.round(k * 100) / 100;
				$("#jiangjia").val(sss);
				
				SetSelect("SELECT id,fuwuBaifenbi FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var sysid=$("#carbid").find("option:selected").text();	
				var k=sysid/100;				
				
				var p=accMul(official_price,k);			
				//赋值
				var c=Math.round(p * 10000) ;
				$("#service_charge").val(c);
				
				
				//以下附加
				var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
				var price=$("#naked_price").val();
				if(r1.test(price)==false){
					$.messager.alert("错误","裸车价只能输入整数或小数！");
		        	return;
				}
// 				SetSelect("SELECT id,fuwuBaifenbi FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
// 				var sysid=$("#carbid").find("option:selected").text();	
// 				var k=sysid/100;				
				
// 				var p=accMul(price,k);				
// 				赋值
// 				var c=Math.floor(p * 10000) ;
// 				$("#service_charge").val(c);
				
				//获取保险的百分比
				SetSelect("SELECT id,bxBaifenbi FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var baoxian=$("#carbid").find("option:selected").text();
				var k1=baoxian/100;				
				
				var p1=accMul(price,k1);
				//获取保险的常量
				SetSelect("SELECT id,bxChangshu FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var cl=$("#carbid").find("option:selected").text();
				var shu=cl/10000;
				
				var pric=shu+p1;
				
				//赋值
				var cc=Math.floor(pric * 10000);
				$("#insurance").val(cc);
				
				//获取购置税计算 除的 常数
				SetSelect("SELECT id,gzsJishu FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var gshu=$("#carbid").find("option:selected").text();
				var k2=price/gshu;
				
				
				//获取购置税的百分比
				SetSelect("SELECT id,gzsBaifenbi FROM T_gouchejisuan WHERE isdel='0' and id=1","carbid",1,new Array("=--请选择--")); 
				var gbai=$("#carbid").find("option:selected").text();
				var gbai1=gbai/100;
				
				
				var p3=accMul(k2,gbai1);
				var ccc=Math.floor(p3 * 10000);
				
				//赋值
				$("#purchase_tax").val(ccc);
				
				
				
			}
			

 </script>
 <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
</style>
 </head>
  <body class="NewWinClass">
  <div style="height:500px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
		<tr style="display: none;"><td><epmis:select  id="carbid" define=""  ></epmis:select></td> </tr>
		<tr align="right">
				<th width="15%"><span style="color: red;">*</span>品牌：</th>
				<td width="12%">				
					
					
					
					<epmis:select id="car.bid" define="select id,title from t_brand where isdel='0' ORDER BY CONVERT( title USING gbk ) COLLATE gbk_chinese_ci ASC " attr="style='width: 100%;'" onChange="changeValue()" ></epmis:select>
				</td>
				<th width="15%"><span style="color: red;">*</span>车系：</th>
				<td width="12%">						
					<epmis:select id="car.carsysid" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select>
				</td>
				
				
			</tr>
			<tr align="right">
				<th width="15%"><span style="color: red;">*</span>车款：</th>
				<td width="12%">						
					<epmis:select id="car.lid" define="" attr="style='width: 100%;'" onChange="changeValue3()" ></epmis:select>
				</td>
				
				<th width="15%"><span style="color: red;">*</span>级别：</th>
				<td width="20%">
				<epmis:select id="car.jid" define="SELECT ID,TITLE FROM T_LEVEL where isdel='0' " attr="style='width: 100%;'" ></epmis:select>
				
			</tr>
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>车型名称：</th>
				<td width="20%"><input class="form_text"  name="car.title" id="title"  value="" ></td>
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>车型主图片：</th>
				<th><div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="../../../images/ic_pic_loading.png" width="100" height="80" /></a></div><br></th>
				<td width="20%"><input name="file" type="file"  id="productimg1"  class="offset10 lf" onchange="getUrl(this,'imghead')" />
				<span style="color:red;">车型主图片建议宽高比3:2  图片大小100K左右</span>
				</td>
			
			</tr>
			
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>车型小图片：</th>		
				<th><div class="lf salebd"><a href="#"  id="preview"><img id="imghead1"  src="../../../images/ic_pic_loading.png" width="100" height="80" /></a></div><br></th>
				<td width="20%"><input name="file1" type="file" id="productimg2"  class="offset10 lf" onchange="getUrl(this,'imghead1')" />
				<span style="color:red;">车型小图片建议宽高比1:2  图片大小50K以内</span>
				</td>
				
			</tr>
			<tr align="right">
			
				<th width="10%"><span style="color: red;">*</span>裸车总价(万)：</th>
				<td width="10%">
				<input class="form_text" type="hidden" name="car.status" id="status"  value="0">
				<input class="form_text" type="hidden" name="car.isdel" id="isdel"  value="0">
				<input class="form_text"  name="car.naked_price" id="naked_price"  value="" onchange="jisuan();"></td>
				<th width="10%"><span style="color: red;">*</span>裸车单位：</th>
				<td width="20%"><input class="form_text"  name="car.naked_price_unit" id="naked_price_unit"  value="万"></td>	
					
				
			</tr>
			
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>官方指导价(万)：</th>
				<td width="10%"><input class="form_text"  name="car.official_price" id="official_price"  value="" onchange="jiss();"></td>
				<th width="10%"><span style="color: red;">*</span>官方指导价   单位：</th>
				<td width="20%">
				
				<input class="form_text"  name="car.official_unit" id="official_unit"  value="万">
				<input class="form_text" type="hidden"  name="car.gid" id="gid"  value="0">
				</td>
			</tr>
<!-- 			<tr align="right"> -->
<!-- 				<th width="20%"><span style="color: red;">*</span>官方指导价范围标签：</th> -->
<!-- 				<td width="5%"> -->
<!-- 				<epmis:select id="car.gid" value="0" define="SELECT ID, CASE WHEN MINPRICE is null THEN CONCAT(maxprice,'万以下') WHEN maxprice is null THEN CONCAT(MINPRICE,'万以上') WHEN MINPRICE is not null and maxprice is not null THEN CONCAT(MINPRICE,'-',maxprice,'万') END AS price FROM T_OFFICIALPRICE where  isdel='0' and TYPE='1' ORDER BY id desc" attr="style='width: 100%;'" ></epmis:select> -->
<!-- 				</td> -->
				
<!-- 			</tr> -->
			<tr align="right">
				<th width="5%"><span style="color: red;">*</span>履约保证金(元)：</th>
				<td width="10%"><input class="form_text"  name="car.service_charge" id="service_charge"  value=""></td>
				<th width="10%"><span style="color: red;">*</span>履约保证金单位：</th>
				<td width="10%"><input attr="style='width: 100%;'" class="form_text"   name="car.service_charge_unit" id="service_charge_unit"  value="元"></td>
			</tr>
			
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>月供(元)：</th>
				<td width="10%"><input class="form_text"  name="car.monthly_payments" id="monthly_payments"  value=""></td>
				<th width="10%"><span style="color: red;">*</span>月供 单位：</th>
				<td width="10%">
				<input class="form_text" type="hidden"  name="car.yid" id="yid"  value="0">
				<input class="form_text"  name="car.monthly_unit" id="monthly_unit"  value="元"></td>
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>月供描述：</th>
				<td width="10%">
				
				<input class="form_text"  name="car.month_ms" id="month_ms"   value="无">
				
<!-- 					<select name="car.down_ms" id="down_ms" style="width: 100%;" class="form_text"> -->
<!-- 						<option value="无">无</option> -->
<!-- 						<option value="包含保险">包含保险</option> -->
<!-- 						<option value="包含购置税">包含购置税</option> -->
<!-- 						<option value="包含保险和购置税">包含保险和购置税</option> -->
						
						
<!-- 					</select> -->
				
				</td>
				
			</tr>
<!-- 			<tr align="right"> -->
				
<!-- 				<th width="10%"><span style="color: red;">*</span>月供范围标签：</th> -->
<!-- 				<th width="20%"> -->
<!-- 				<epmis:select id="car.yid" value="0" define="SELECT ID, CASE WHEN MINPRICE is null THEN CONCAT(maxprice,'千以下') WHEN maxprice is null THEN CONCAT(MINPRICE,'千以上') WHEN MINPRICE is not null and maxprice is not null THEN CONCAT(MINPRICE,'-',maxprice,'千') END AS price FROM T_OFFICIALPRICE where  isdel='0' and TYPE='3' ORDER BY id desc" attr="style='width: 100%;'" ></epmis:select> -->
<!-- 				</th> -->
<!-- 			</tr> -->
			
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>首付(万)：</th>
				<td width="10%"><input class="form_text"  name="car.down_payment" id="down_payment"  value=""></td>
				<th width="10%"><span style="color: red;">*</span>首付单位：</th>
				<td width="20%">
				<input class="form_text" type="hidden"  name="car.sid" id="sid"  value="0">
				<input class="form_text"  name="car.down_unit" id="down_unit"   value="万"></td>
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>首付描述：</th>
				<td width="10%">
				
				<input class="form_text"  name="car.down_ms" id="down_ms"   value="无">
				
<!-- 					<select name="car.down_ms" id="down_ms" style="width: 100%;" class="form_text"> -->
<!-- 						<option value="无">无</option> -->
<!-- 						<option value="包含保险">包含保险</option> -->
<!-- 						<option value="包含购置税">包含购置税</option> -->
<!-- 						<option value="包含保险和购置税">包含保险和购置税</option> -->
						
						
<!-- 					</select> -->
				
				</td>
				
			</tr>
<!-- 			<tr align="right"> -->
			
				
<!-- 				<th width="20%"><span style="color: red;">*</span>首付范围标签：</th> -->
<!-- 				<td width="10%"> -->
<!-- 				<epmis:select id="car.sid" value="0" define="SELECT ID, CASE WHEN MINPRICE is null THEN CONCAT(maxprice,'万以下') WHEN maxprice is null THEN CONCAT(MINPRICE,'万以上') WHEN MINPRICE is not null and maxprice is not null THEN CONCAT(MINPRICE,'-',maxprice,'万') END AS price FROM T_OFFICIALPRICE where  isdel='0' and TYPE='2' ORDER BY id desc" attr="style='width: 100%;'" ></epmis:select> -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr align="right">
			<th width="10%"><span style="color: red;">*</span>购置税(元)：</th>
				<td width="10%"><input class="form_text"  name="car.purchase_tax" id="purchase_tax"  value="" ></td>
				<th width="20%"><span style="color: red;"></span></th>
				<td width="10%"><input class="form_text" type="hidden"  name="car.cash_deposit" id="cash_deposit"  value="0"></td>
				
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>商业保险(元)：</th>
				<td width="10%"><input class="form_text"  name="car.insurance" id="insurance"  value=""></td>
				<th width="10%"><span style="color: red;">*</span>星级：</th>
				<td width="20%">
					<select name="car.xin" id="xin" style="width: 100%;" class="form_text">
						<option value="">--请选择--</option>
						<option value="1">✰</option>
						<option value="2">✰✰</option>
						<option value="3">✰✰✰</option>
						<option value="4">✰✰✰✰</option>
						<option value="5">✰✰✰✰✰</option>
						
					</select>
				</td>
				<input class="form_text" type="hidden"  name="car.car_size" id="car_size"  value="0">
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;"></span>销量(辆)：</th>
				<td width="10%" align="left">
				0
				<input class="form_text" type="hidden"  name="car.sales_volume" id="sales_volume"  value="0"></td>
				<th width="10%"><span style="color: red;"></span>库存(辆)：</th>
				<td width="20%"><input class="form_text"  name="car.stock" id="stock"  value="0"></td>
			</tr>
			<tr align="right">
				<th width="5%"><span style="color: red;">*</span>降价/优惠(万)：</th>
				<td width="10%"><input class="form_text"  name="car.jiangjia" id="jiangjia"  value="" ></td>
				<th width="10%"><span style="color: red;">*</span>降价单位：</th>
				<td width="10%"><input attr="style='width: 100%;'" class="form_text"   name="car.jiangjiaunit" id="jiangjiaunit"  value="万"></td>
			</tr>
			<tr style="display: none;">
				<th width="20%"><span style="color: red;">*</span>车结构：</th>
				<td width="10%"><input class="form_text"  name="car.car_construction" id="car_construction"  value="-"></td>
				<th width="10%"><span style="color: red;">*</span>发动机：</th>
				<td width="10%"><input class="form_text"  name="car.engine" id="engine"  value="-"></td>
			</tr>
			
			
				
			
			
			
			
			<tr align="right">
				<th width="20%"><span style="color: red;"></span>汽车车型视频标题：</th>
				<td width="10%"><input class="form_text"  name="car.car_title" id="car_title"  value="-"></td>
<!-- 				<th width="10%"><span style="color: red;">*</span>车宽：</th> -->
<!-- 				<td width="10%"><input class="form_text"  name="car.car_wide" id="car_wide"  value=""> -->
<!-- 				</td> -->
				<th width="10%"><span style="color: red;">*</span>全景链接：</th>
				<td width="10%"><input class="form_text"  name="car.overall_view" id="panorama"  value="-">
				</td>
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;"></span>视频第一帧图片：</th>
				<th><div class="lf salebd"><a href="#"  id="preview"><img id="imghead3"  src="../../../images/ic_pic_loading.png" width="100" height="80" /></a></div><br></th>
				<td width="20%"><input name="file3" type="file" id="productimg3"  class="offset10 lf" onchange="getUrl(this,'imghead3')" /></td>
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;"></span>汽车车型视频：</th>
				<td width="10%">
				 <input name="file2"   type="file" id="productimg3"   style="width: 100%;" />
				</td>
				
<!-- 				<input class="form_text"  name="car.car_vedio" id="car_vedio"  value=""> -->
				
				
				
				
				<input class="form_text" type="hidden" name="car.buy_car_flow" id="buy_car_flow"  value="null">
			</tr>
			<tr style="display: none;">
				<th width="20%"><span style="color: red;">*</span>车长(mm)：</th>
				<td width="10%"><input class="form_text"  name="car.car_long" id="car_long"  value="0"></td>
				<th width="10%"><span style="color: red;">*</span>车宽(mm)：</th>
				<td width="10%"><input class="form_text"  name="car.car_wide" id="car_wide"  value="0"></td>
			</tr>
			<tr style="display: none;">
				<th width="10%"><span style="color: red;">*</span>车高(mm)：</th>
				<td width="10%"><input class="form_text"  name="car.car_hight" id="car_hight"  value="0"></td>
				<th width="10%"><span style="color: red;">*</span>最小离地间距：</th>
				<td width="20%"><input class="form_text"  name="car.car_min_space" id="car_min_space"  value="0"></td>
			</tr>
			<tr style="display: none;">
				<th width="20%"><span style="color: red;">*</span>轴距：</th>
				<td width="10%"><input class="form_text"  name="car.wheel_base" id="wheel_base"  value="0"></td>
				<th width="10%"><span style="color: red;">*</span>保修政策：</th>
				<td width="10%"><input class="form_text"  name="car.warranty_policy" id="warranty_policy"  value="0"></td>
			</tr>
			<tr style="display: none;">
				<th width="10%"><span style="color: red;">*</span>排量：</th>
				<td width="10%"><input class="form_text"  name="car.displacement" id="displacement"  value="0"></td>
				<th width="10%"><span style="color: red;">*</span>功率：</th>
				<td width="20%"><input class="form_text"  name="car.power" id="power"  value="-"></td>
			</tr>
			<tr style="display: none;">
				<th width="5%"><span style="color: red;"></span>工信部：</th>
				<td width="10%"><input class="form_text"  name="car.Ministry_industry" id="Ministry_industry"  value="0"></td>
				<th width="10%"><span style="color: red;"></span>官方：</th>
				<td width="10%"><input attr="style='width: 100%;'" class="form_text"   name="car.official" id="official"  value="0"></td>
			</tr>
			
			
			
			
			
			<tr align="right">
				<th width="10%"><span style="color: red;"></span></th>
				<td width="10%"><input class="form_text" type="hidden"  name="car.tallest" id="tallest"  value="0"></td>
				
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
