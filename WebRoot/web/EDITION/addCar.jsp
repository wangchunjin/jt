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
	        var r2=/^[0-9]*$/;//判断是否是整数
	        var android=$("#android").val();
	        if(android==null||android==""){
	        	$.messager.alert("错误","安卓版本号不能为空！");
	        	return;
	        }
	        if(r2.test(android)==false){
	        	$.messager.alert("错误","安卓版本号只能是整数！");
	        	return;
	        }
	        var androname=$("#androname").val();
	        if(androname==null||androname==""){
	        	$.messager.alert("错误","安卓版本名称不能为空！");
	        	return;
	        }
	        var androidcontent=$("#androidcontent").val();
	        if(androidcontent==null||androidcontent==""){
	        	$.messager.alert("错误","安卓版本介绍不能为空！");
	        	return;
	        }
	        
	        
	        var productimg1=$("#productimg1").val();
	        if(productimg1==null||productimg1==""){
	        	$.messager.alert("错误","请选择安卓apk文件上传！");
	        	return;
	        }
	        var FileExt=productimg1.replace(/.+\./,"");   //正则表达式获取后缀
	        if(FileExt!="apk"){
	        	
	        	$.messager.alert("错误","安卓apk上传文件格式不正确（apk）！");
 	        	return;
	        }
	       
	        var ios=$("#ios").val();
	        if(ios==null||ios==""){
	        	$.messager.alert("错误","ios版本号不能为空！");
	        	return;
	        }
	        if(r2.test(ios)==false){
	        	$.messager.alert("错误","ios版本号只能是整数！");
	        	return;
	        }
	        
	        var iosname=$("#iosname").val();
	        if(iosname==null||iosname==""){
	        	$.messager.alert("错误","ios版本名称不能为空！");
	        	return;
	        }
	        var ioscontent=$("#ioscontent").val();
	        if(ioscontent==null||ioscontent==""){
	        	$.messager.alert("错误","ios版本介绍不能为空！");
	        	return;
	        }
	        var iosurl=$("#iosurl").val();
	        if(iosurl==null||iosurl==""){
	        	$.messager.alert("错误","ios地址介绍不能为空！");
	        	return;
	        }
	        var bbid=0;
// 	        var productimg2=$("#productimg2").val();
// 	        if(productimg2==null||productimg2==""){
// 	        	$.messager.alert("错误","请选择ios文件上传！");
// 	        	return;
// 	        }
// 	        var FileExt=productimg2.replace(/.+\./,"");   //正则表达式获取后缀
// 	        if(FileExt!="apk"){	        	
// 	        	$.messager.alert("错误","ios apk上传文件格式不正确（apk）！");
//  	        	return;
// 	        }
	        
	        $("#b").show();
       		$("#a").hide();
       		
       		setTimeout(function(){
	        var res = $('#addForm').SubmitForm("<%=basePath%>/servlet/BBUpload?android="+android+"&androname="+androname+"&androidcontent="+androidcontent+"&ios="+ios+"&iosname="+iosname+"&ioscontent="+ioscontent+"&bbid="+bbid+"&iosurl="+iosurl);
// 	 	  	console.log(res[0].result+"----");
	         if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
	 		$("#a").show();
		 	   
			$("#b").hide();
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 				$("#a").show();
 		 	   
				$("#b").hide();
 		 	} 
       		},200);
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
			
			
			
			
			

 </script>
 <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
  <div style="height:500px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
   <div id="a">
		<table style="margin-top: 15px;width: 96%;" >
		
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>安卓版本号：</th>
				<td width="20%"><input class="form_text"  name="edition.android" id="android"  value=""></td>
			</tr>
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>安卓版本名称：</th>
				<td width="20%"><input class="form_text"  name="edition.androname" id="androname"  value=""></td>
			</tr>
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>安卓新功能介绍：</th>
				<td width="20%">
				<textarea  rows="3" cols="20"  name="edition.androidcontent" id="androidcontent"  value=""></textarea>
				</td>
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>安卓apk：</th>
<!-- 				<th><div class="lf salebd"><a href="#"  id="preview"></a></div><br></th> -->
				<td width="20%" align="left"><input name="file" type="file" id="productimg1"  class="offset10 lf" onchange="getUrl(this,'imghead')" /></td>
			</tr>
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>ios版本号：</th>
				<td width="20%"><input class="form_text"  name="edition.ios" id="ios"  value=""></td>
			</tr>
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>ios版本名称：</th>
				<td width="20%"><input class="form_text"  name="edition.iosname" id="iosname"  value=""></td>
			</tr>
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>ios新功能介绍：</th>
				<td width="20%">
				<textarea  rows="3" cols="20"  name="edition.ioscontent" id="ioscontent"  value="">${edition[0].ioscontent }</textarea></td>
			</tr>
			
<!-- 			<tr align="right"> -->
<!-- 				<th width="10%"><span style="color: red;">*</span>ios apk：</th>		 -->
<!-- 				<th><div class="lf salebd"><a href="#"  id="preview"></a></div><br></th> -->
<!-- 				<td width="20%"  align="left"><input name="file1" type="file" id="productimg2"  class="offset10 lf" onchange="getUrl(this,'imghead1')" /></td> -->
				
<!-- 			</tr> -->
			
			<tr align="right">
				<th width="20%"><span style="color: red;">*</span>iosURL：</th>
				<td width="20%"><input class="form_text"  name="edition.iosURl" id="iosurl"  value=""></td>
			</tr>
			
			
			
			
			
			
			
       </table>
       </div>
       <div id="b" style="display:none; ">

   
		<table style="margin-top: 100px;margin-right:150px  ;width: 96%;" >
		
		
			<tr align="right">
				<th width="10%"><span style="color: red;"></span></th>
				<td width="20%" align="left">
				
				
				<h6 style="color:red;"><h6 style="color:red;font-size: 20px;">版本正在传输中，等待中请勿操作......</h6></h6>	
				</td>
				
				
				
				
				

			</tr>
			
			
			
			
       </table>

</div>
</form>

</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
