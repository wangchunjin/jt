<%@ page language="java" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ page import="java.util.*" %>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <style>
     #uploader{
      position:relative;
     }
     #uploader_queue{
      position:absolute;
      width:600px;
      left:200px;
      top:0;
     }
    
     
    </style>
    <title>My JSP 'upLoad.jsp' starting page</title>
 <meta http-equiv="pragma" content="no-cache">
 <meta http-equiv="cache-control" content="no-cache">
 <meta http-equiv="expires" content="0">    
 <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
 <meta http-equiv="description" content="This is my page">
	<link type="text/css" rel="stylesheet" href="${ctx }/webResources/uploadify/uploadify.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/webResources/style/css/default/form.css">
	<script src="${ctx }/webResources/uploadifive/Sample/jquery.min.js" type="text/javascript"></script>
	<script src="${ctx }/webResources/uploadifive/Sample/jquery.uploadifive.min.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${ctx }/webResources/uploadifive/uploadifive.css">
	<style type="text/css">
	body{
		font: 13px Arial, Helvetica, Sans-serif;
	}
	.uploadifive-button {
		float: left;
		margin-right: 10px;
	}
	#queue {
		border: 1px solid #E5E5E5;
		height: 177px;
		overflow: auto;
		margin-bottom: 10px;
		padding: 0 3px 3px;
		width: 300px;
	}
	</style>
 	<script type="text/javascript">
	</script>
  </head>
  <body>
  
	<div id="uploader_msg"  style="font-weight:bold; color:#0099FF; text-align:left;font-size:14; position:absolute; float:left; top:0;left:0;right:0; height: 450px;overflow-y: auto;"></div>
	<div id="uploader_queue"style="width:99%; position: absolute;left: 0;right: 30px;top:20px;bottom:45px;overflow-y: auto;height:80px;"></div>
	<div style="float:left; height:80px; position: absolute;left: 0;right: 0;bottom: 0; border-top: 2px solid #1b4973;" >
		
		<table width="100%">
			<tr>
				<td style="padding-top: 7px;width: 10%;padding-left:20px;">
					<input  type="file" name="file_upload" id="file_upload" onChange="check()" />
				</td>
				
<!-- 				<th style="padding-bottom: 5px;"><span style="color: red;">*</span>楼盘图片类别：</th> -->
<!-- 				<td > -->
				<input class="form_text" type="hidden"  name="hid" id="hid"  value="${param.lid }">
				
<!-- 			<epmis:select  id="tid" define="select id,name from t_pro_pictype where isdel='0'  " attr="style='width: 100%;'" onChange="check()" ></epmis:select>  -->

				
<!-- 				</td> -->
			</tr>
		</table>
		
		</div>
		<div class="BottomDiv">
			<a href="javascript:$('#file_upload').uploadifive('upload')" class="btn_01"  style="text-decoration: none;" >上传<b></b></a>&nbsp;
					<a href="###" class="btn_01" style="text-decoration: none;" onclick="parent.close_win('addWindow')">关闭<b></b></a>			
		</div>
	
	
	<script type="text/javascript">
	
	var sta="";
	
	setTimeout(check,100); 
	
	
	
	function check(){
		
		
			
			var hid=$("#hid").val();			
			
// 			$('input[type=file]').removeAttr('disabled');
			
	$('#file_upload').uploadifive({
		
				'height' : 20,
				'width' : 110,
				'auto'  : false,
				'buttonText'	:'选择文件',   
				'queueID'       : 'uploader_msg',
				fileSizeLimit:1024000,//限制大小
				'uploadScript'  : "<%=basePath%>/servlet/HHUpload?hid="+hid,
				'onUploadComplete' : function(file, data) {
					//alert(status);
					console.log(data);
					var currFrameId = "${param.currFrameId}";
					GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
					//parent.close_win('addWindow');
				},
				'onError':function(file,fiType,data){			
					alert("上传图片不能大于1024000k！");
				}
			});	
			
			
		
	}
	
	
	
	
	</script>
  	
  </body>
</html>