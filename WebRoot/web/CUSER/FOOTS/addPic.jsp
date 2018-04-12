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
	        
	       	
 	        
			
	        var res = $('#addForm').SubmitForm("${ctx}/store/tstore/save.action");
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
	     }
	     
	     function initialize() {
	         var mp = new BMap.Map('map');
	         mp.centerAndZoom(new BMap.Point(121.2134, 31.0576), 15);
	 }

	 function loadScript() {
	         var script = document.createElement("script");
	         script.src = "http://api.map.baidu.com/api?v=1.4&callback=initialize";
	         document.body.appendChild(script);
	 }

	 window.onload = loadScript;
 </script>
 <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			
			
			
			<tr>
				<th width="20%"><span style="color: red;">*</span>地址：</th>
				<td width="20%">
				<input class="form_text" type="hidden"  name="store.isdel" id="isdel"  value="0">
				<input class="form_text" type="hidden"  name="store.rid" id="rid"  value="${param.rid }">
				
				<input class="form_text"   name="store.address" id="address"  value="">
				
				</td>
			</tr>
			<div id="map" style="width: 500px; height: 400px"></div>
			
       </table>
</form>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
