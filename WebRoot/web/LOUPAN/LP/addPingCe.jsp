<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=z47a5PpxBagrTYGdUsYqpHQS"></script>
 <script type="text/javascript">
 //文本编辑
//  function MCEInit(){ 
//      $("#CONTENTT_ifr").contents().find("#tinymce").html(""); 
// };  

 function AddRecord(){
	        var currFrameId = "${param.currFrameId}";
	        var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
	        //文本编辑
// 	        var content =  tinyMCE.get('CONTENTT').getContent().replace(/"/g, "\'"); 
// 			 $("#content").val(content);


			var ccs="";

	        var input = document.getElementById("productimg2");  
	        if(input.files){
	                        //读取图片数据  
	          var f = input.files[0];  
	          var reader = new FileReader();  
	          reader.onload = function (e) {
	              var data = e.target.result;  
	              //加载图片获取图片真实宽度和高度  
	              var image = new Image(); 
	              image.onload=function(){
	                  var width = image.width;
	                  var height = image.height;
	                  if( height/width!=0.75){	
	                	  
// 	                	  $.messager.alert("错误",""+height/width+"图片高宽比要3:4");
	                	  ccs=1;
	                	  
	                	  
	                  }
	                  $("#wide").val(width);
                	  $("#high").val(height);
// 	                  alert(width+'======'+height+"====="+f.size);  
	              };  
	              image.src= data;  
	              
	          };  
	              reader.readAsDataURL(f);  
	          }else{  
	              var image = new Image();   
	              image.onload =function(){  
	                  var width = image.width;  
	                  var height = image.height;  
	                  var fileSize = image.fileSize;  
// 	                  alert(width+'======'+height+"====="+fileSize);  
	              };
	              image.src = input.value;  
	          }  
	        
	        setTimeout(function(){
	        
	        if(ccs==1){
	        	$.messager.alert("错误","楼栋图片高宽比要3:4");
	        	return;
	        }
	        
	       
	        
	       
	 
	     
	        var chk_value =[]; 
	        $('input[name="building"]:checked').each(function(){ 
	        chk_value.push($(this).val()); 
	        });
// 	        alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 
	        $("#bq_house").val(chk_value);
	        
	        var chk_value1 =[]; 
	        $('input[name="tesese"]:checked').each(function(){ 
	        	chk_value1.push($(this).val()); 
	        });
// 	        alert(chk_value1.length==0 ?'你还没有选择任何内容！':chk_value1);
	        $("#bul_type").val(chk_value1);
	        
	        var chk_value2 =[]; 
	        $('input[name="objectss"]:checked').each(function(){ 
	        	chk_value2.push($(this).val()); 
	        });
// 	        alert(chk_value2.length==0 ?'你还没有选择任何内容！':chk_value2);
	        $("#objective").val(chk_value2); 	
	        
	        
	        
	        
	        
	        
	        var title=$("#title").val();
	        if(title==null || trim(title)==""){
	        	$.messager.alert("错误","楼盘标题不能为空！");
	        	return;
	        }
	      
	        var unit_price=$("#unit_price").val();
	        if(unit_price==null || trim(unit_price)==""){
	        	$.messager.alert("错误","楼盘单价不能为空！");
	        	return;
	        }
	        if(r1.test(unit_price)==false){
	        	$.messager.alert("错误","楼盘单价只能是整数或小数！");
	        	return;
	        }
	        
	        var selling_price=$("#selling_price").val();
	        if(selling_price==null || trim(selling_price)==""){
	        	$.messager.alert("错误","起售价单价不能为空！");
	        	return;
	        }
	        if(r1.test(selling_price)==false){
	        	$.messager.alert("错误","起售价只能是整数或小数！");
	        	return;
	        }
	        
	        var avg_price=$("#avg_price").val();
	        if(avg_price==null || trim(avg_price)==""){
	        	$.messager.alert("错误","均价不能为空！");
	        	return;
	        }
	        if(r1.test(avg_price)==false){
	        	$.messager.alert("错误","均价只能是整数或小数！");
	        	return;
	        }
	        
	        var start_time=$("#start_time").val();
	        if(start_time==null || trim(start_time)==""){
	        	$.messager.alert("错误","开盘时间不能为空！");
	        	return;
	        }
	        var free_start_time=$("#free_start_time").val();
	        if(free_start_time==null || trim(free_start_time)==""){
	        	$.messager.alert("错误","空闲开始时间不能为空！");
	        	return;
	        }
	        var free_end_time=$("#free_end_time").val();
	        if(free_end_time==null || trim(free_end_time)==""){
	        	$.messager.alert("错误","空闲结束时间不能为空！");
	        	return;
	        }
	        var address=$("#address").val();
	        if(address==null || trim(address)==""){
	        	$.messager.alert("错误","地址不能为空！");
	        	return;
	        }
	        
	        var Lng=$("#Lng").val();
	        if(Lng==null || trim(Lng)==""){
	        	$.messager.alert("错误","经度不能为空！");
	        	return;
	        }
	       
	        var Lat=$("#Lat").val();
	        if(Lat==null || trim(Lat)==""){
	        	$.messager.alert("错误","纬度不能为空！");
	        	return;
	        }
	       
	       
	        
	        
	        
	        
	        
	        
	        
	        
	        
			
	        var res = $('#addForm').SubmitForm("${ctx}/prosale/tprosale/save.action");
	       
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	        },200);
	     };
	    
	     function check(){
	    	 var status=$("#type").val();
	    	 
	    	 if(status==2){	    		
	    		 
	    		 $("#hhh").hide();
	    		 $("#ttt").show();
	    	 }
	    		  if(status==1){
	    			  
	 	    		 $("#ttt").hide();
		    		 $("#hhh").show();
	    		  }  
	    	 
	    	 
	     }
	     function getUrl(node,imgId){   
			    var file = null;  
			    if(node.files && node.files[0] ){  
			        file = node.files[0]; 
			    }else if(node.files && node.files.item(0)) {                                  
			        file = node.files.item(0);     
			    }
//			    alert(getObjectURL(file));
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
			
			function changeValue1(){
		     	var obj=$("#prosale\\.shen").val();
		     		SetSelect("SELECT cityId,cityName FROM T_CITY WHERE provinceId="+obj,"prosale\\.shi","",new Array("=--请选择--")); 
		     		SetSelect("SELECT id,name FROM t_pro_label WHERE id=0","prosale\\.qu","",new Array("=--请选择--")); 
		     	
		     		
			}
			function changeValue2(){
		     	var obj=$("#prosale\\.shi").val();
		     		SetSelect("SELECT districtId,districtName FROM T_district WHERE cityId="+obj,"prosale\\.qu","",new Array("=--请选择--")); 
		     				     		
			}
			
			$(function(){
				setMap("南京市","星火E方");				
				var result = GetXmlBySql("SELECT id,name FROM t_building_type WHERE isdel='0'");
				var str="";
				for(var i=0;i<result.length;i++){
					str=str+"<input  type='checkbox' name='building'  value='"+result[i].id+"'>"+result[i].name+" ";
				}
				$("#ksksk").html(str);
				
				var result2 = GetXmlBySql("SELECT id,name FROM t_pro_label WHERE isdel='0'");
				var str1="";
				for(var i=0;i<result2.length;i++){
					str1=str1+"<input  type='checkbox' name='tesese'  value='"+result2[i].id+"'>"+result2[i].name+" ";
				}
				$("#tese").html(str1);
				
				
				var result3 = GetXmlBySql("SELECT id,name FROM t_objective WHERE isdel='0'");
				var str2="";
				for(var i=0;i<result3.length;i++){
					str2=str2+"<input  type='checkbox' name='objectss'  value='"+result3[i].id+"'>"+result3[i].name+" ";
				}
				$("#objectiv").html(str2);
				
			});
			


			function setMap(cityName,currentAddr){
			        var map = new BMap.Map("container");
			        map.centerAndZoom(cityName,12);                   // 初始化地图,设置城市和地图级别。
			        map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
			        map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}));  //右上角，仅包含平移和缩放按钮
			        map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT, type: BMAP_NAVIGATION_CONTROL_PAN}));  //左下角，仅包含平移按钮
			        map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, type: BMAP_NAVIGATION_CONTROL_ZOOM}));  //右下角，仅包含缩放按钮
			        
			        //根据输入的地址定位
			        if(currentAddr==""||currentAddr==null){
			                currentAddr="南京市";
			        }
			        var local = new BMap.LocalSearch(map, {
			                renderOptions:{map: map}
			        });
			        local.search(currentAddr);
			        
			        //鼠标点击获取坐标
			        map.enableScrollWheelZoom();                            //启用滚轮放大缩小
			        map.addEventListener("click", function(e){
			          //document.getElementById("r-result1").innerHTML = e.point.lng + ", " + e.point.lat;
			                $("#Lng").val(e.point.lng);
			                $("#Lat").val(e.point.lat);
							$("#cityname").val(e.target.$g);
			        });        
			        
			        
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
			
			<tr align="right">
			<input class="form_text" type="hidden"  name="prosale.wide" id="wide"  value="">	
			<input class="form_text" type="hidden"  name="prosale.high" id="high"  value="">	
					<input class="form_text" type="hidden"  name="prosale.bq_house" id="bq_house"  value="">	
					<input class="form_text" type="hidden" name="prosale.bul_type" id="bul_type"  value="">	
					<input class="form_text" type="hidden" name="prosale.objective" id="objective"  value="">	
				<th width="20%"><span style="color: red;">*</span>楼盘图片：</th>
				<th><div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="../../../images/ic_pic_loading.png" width="100" height="80" /></a></div><br></th>
				<td width="20%"><input name="file" type="file"  id="productimg1"  class="offset10 lf" onchange="getUrl(this,'imghead')" />
				<span style="color:red;"></span>
				</td>
			</tr>
			
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>楼栋图片：</th>		
				<th><div class="lf salebd"><a href="#"  id="preview"><img id="imghead1"  src="../../../images/ic_pic_loading.png" width="100" height="80" /></a></div><br></th>
				<td width="20%"><input name="file1" type="file" id="productimg2"  class="offset10 lf" onchange="getUrl(this,'imghead1')" />
				<span style="color:red;"></span>
				</td>
				
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>标题：</th>
				<td width="20%">
				<input class="form_text"  name="prosale.title" id="title"  value="" ></td>
				<th width="10%"><span style="color: red;">*</span>单价(元)：</th>
				<td width="20%"><input class="form_text"  name="prosale.unit_price" id="unit_price"  value=""></td>						
				
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>起售价(万)：</th>
				<td width="20%">
				<input class="form_text"  name="prosale.selling_price" id="selling_price"  value="" ></td>
				<th width="10%"><span style="color: red;">*</span>均价(元)：</th>
				<td width="20%"><input class="form_text"  name="prosale.avg_price" id="avg_price"  value=""></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;">*</span>开盘时间：</th>
				<td width="20%">	
				<textarea  name="prosale.start_time" id="start_time" rows="3" cols="15"></textarea>			
				</td>
				<th width="10%"><span style="color: red;">*</span>装修状态：</th>
				<td width="20%">
					<select class="form_text"  name="prosale.zx" id="zx">
						<option value="1">精装 </option>
						<option value="2">毛坯 </option>
						<option value="3">简装 </option>
					</select>				
				</td>			
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;">*</span>空闲开始时间：</th>
				<td width="20%">				
						<input class="form_text"  name="prosale.free_start_time" id="free_start_time" readonly="readonly"  value="" onfocus="DataEdit('HH:mm','','')">		
				</td>
				<th width="10%"><span style="color: red;">*</span>空闲结束时间：</th>
				<td width="20%"><input class="form_text"  name="prosale.free_end_time" id="free_end_time" readonly="readonly"  value="" onfocus="DataEdit('HH:mm','','')"></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;">*</span>售卖状态：</th>
				<td width="20%">	
					<select class="form_text"  name="prosale.status" id="status">
						<option value="1">待售 </option>
						<option value="2">在售 </option>
						<option value="3">售罄 </option>
					</select>			
				</td>
									
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;">*</span>楼盘类型：</th>
				<td width="20%" colspan="3" id="ksksk" align="left">	
						
				</td>
			</tr>
			<tr align="right">	
				<th width="10%"><span style="color: red;">*</span>楼盘房屋(特色)：</th>
				<td width="20%" colspan="3" id="tese" align="left">	
				    
				</td>
			</tr>
			<tr align="right">	
				<th width="10%"><span style="color: red;">*</span>楼盘目的：</th>
				<td width="20%" colspan="3" id="objectiv" align="left">		
					
				</td>
			</tr>			
			<tr align="right">			
				<th ><span style="color: red;">*</span>省份：</th>
				<td >
					<epmis:select id="prosale.shen" define="SELECT provinceId,provinceName FROM T_province" attr="style='width: 100%;'" onChange="changeValue1()" ></epmis:select>
				</td>
				<th ><span style="color: red;">*</span>市：</th>
				<td >
					<epmis:select id="prosale.shi" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select>
				</td>
			</tr>
			<tr align="right">			
				<th ><span style="color: red;">*</span>区县：</th>
				<td >
					<epmis:select id="prosale.qu" define="" attr="style='width: 100%;'"  ></epmis:select>
				</td>
				
			</tr>
			
			
			<tr align="right">
				<th ><span style="color: red;">*</span>地址：</th>
				<td colspan="3">
				<div class="formControls col-xs-8 col-sm-9">
				<input type="text" style="width: 100%;" class="form_text" value="" placeholder="请输入楼盘地址" name="prosale.address" id="address" onkeyup="setMap('南京市',value)">
				</td>
			</tr>
			
			
			<tr align="right">
				<th><span style="color: red;">*</span>经度：</th>
				<td>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" style="width: 100%;" readonly="readonly" class="form_text" name="prosale.longitude" id="Lng"  onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
					</div>
					
				</td>
				<th><span style="color: red;">*</span>纬度：</th>
				<td>
					<div class="formControls col-xs-8 col-sm-9">
				<input type="text" style="width: 100%;" readonly="readonly" class="form_text" name="prosale.latitude" id="Lat" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
			</div>
				</td>
				
			</tr>
       </table>
       
       
       
		<div class="row cl" >
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2" id="container" style="height:360px;">
				
			</div>
		</div>
		
<!--        <input class="form_text" type="hidden"  name="tservice.content" id="content"  value="">	 -->
</form>

</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
