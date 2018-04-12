<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
    
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
			 
			 var chk_value =[]; 
		        $('input[name="building"]:checked').each(function(){ 
		        chk_value.push($(this).val()); 
		        });
//	 	        alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 
		        $("#lable").val(chk_value);
		        
			 
	        var name=$("#name").val();
	        if(name==null || trim(name)==""){
	        	$.messager.alert("错误","户型名称不能为空！");
	        	return;
	        }
	        var price=$("#price").val();
	        if(price==null || trim(price)==""){
	        	$.messager.alert("错误","户型总价不能为空！");
	        	return;
	        }
	        var area=$("#area").val();
	        if(area==null || trim(area)==""){
	        	$.messager.alert("错误","户型面积不能为空！");
	        	return;
	        }
	        var typeid=$("#house\\.typeid").val();
	        if(typeid==null || trim(typeid)==""){
	        	$.messager.alert("错误","请选择户型！");
	        	return;
	        }
	       
	        var res = $('#addForm').SubmitForm("${ctx}/house/thouse/save.action");
	       
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	       
	     };
	    
	     $(function(){
	    	 var result = GetXmlBySql("SELECT id,name FROM t_house_label WHERE isdel='0'");
				var str="";
				for(var i=0;i<result.length;i++){
					str=str+"<input  type='checkbox' name='building'  value='"+result[i].id+"'>"+result[i].name+" ";
				}
				$("#ksksk").html(str);
	    	 
	     })

			
			
			
			
			
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
		<tr id="aaa" >
				<th width="20%"><span style="color: red;"></span>户型大图：</th>
				<td width="20%">
				<input class="form_text" type="hidden"  name="house.lable" id="lable"  value="">	
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="../../../images/ic_pic_loading.png" width="NAN" height="100" /></a></div><br>
				</td>
			</tr>
			<tr id="bbb" >
			<th width="20%"><span style="color: red;"></th>
				<td width="20%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="previewImage(this)" />
				
				
				</td>
			</tr>			
			<tr align="right">	
			<input class="form_text" type="hidden"  name="house.l_id" id="lid"  value="${param.lid }" >		
				<th width="20%"><span style="color: red;">*</span>名称：</th>
				<td width="20%">				
				<input class="form_text"  name="house.name" id="name"  value="" ></td>
				<th width="20%"><span style="color: red;">*</span>室/厅/卫：</th>
				<td width="26%" align="left">
				<select  name="house.shi" id="shi" >
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
				</select>室
				<select  name="house.ting" id="ting" >
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
				</select>厅
				<select name="house.wei" id="wei" >
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
				</select>卫
				
				
				</td>				
			</tr>
			<tr align="right">	
				
				<th width="20%"><span style="color: red;">*</span>总价(万元/套)：</th>
				<td width="20%">				
				<input class="form_text"  name="house.price" id="price"  value="" ></td>
				<th width="20%"><span style="color: red;">*</span>面积：</th>
				<td width="20%"><input class="form_text"  name="house.area" id="area"  value=""></td>				
			</tr>
			<tr align="right">	
				
				<th width="20%"><span style="color: red;">*</span>户型：</th>
				<td width="20%">				
					<epmis:select  id="house.typeid" define="select id,name from t_housetype where isdel='0'  " attr="style='width: 100%;'" onChange="check()" ></epmis:select>
				</td>
							
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>户型类别标签：</th>
				<td width="20%" colspan="3" id="ksksk" align="left">	
						
				</td>
			</tr>
			<tr >
			<th width="20%"><span style="color: red;">*</span>户型详情：</th>
			
				<td colspan="3">
					<textarea name="house.content" id="content" class="label_textarea"  rows="10"  ></textarea> 					   	  
				</td>
			</tr>
			
        </table>   
<!--         <input class="form_text" type="hidden"  name="house.content" id="content"  value=""> -->
       
		
</form>

</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
