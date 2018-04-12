<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript" src="js/imagePreview.js"></script>

<script type="text/javascript">

	function UpdateRecord() {
		var currFrameId = "${param.currFrameId}";
		
	        
// 	        var tid=$("#pic\\.tid").val();
// 	        if(tid==null || trim(tid)==""){
// 	        	$.messager.alert("错误","请选择楼盘图片类别！");
// 	        	return;
// 	        }
	      
	       
	        

		var res = $('#addForm').SubmitForm("${ctx}/housepic/thousepic/update.action");
		if (res[0].result == "success") {
			GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
			parent.close_win('eidtWindow');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}
        

	}

	
	
	function getObjectURL(file) {
        var url = null;
        if (window.createObjcectURL != undefined) {
            url = window.createOjcectURL(file);
        } else if (window.URL != undefined) {
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) {
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }

    
	
	function getUrl(node){
		   
	    var file = null;  
	    if(node.files && node.files[0] ){  
	        file = node.files[0]; 
	    }else if(node.files && node.files.item(0)) {                                  
	        file = node.files.item(0);     
	    }
	     document.getElementById("imghead").src = getObjectURL(file);
	}
	
	
	
	
</script>
<style type="text/css">
#preview {
	width: 100px;
	height: 130px;
	overflow: hidden;
}

#imghead {
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
}
</style>
</head>
<body class="NewWinClass">
	 <div style="height:500px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			<tr>
				<th width="20%"><span style="color: red;">*</span>户型图片：</th>
				<td width="30%">
				<input type="hidden" class="form_text"  name="housepic.hid" id="hid"  value="${housepic[0].hid }">
				<input type="hidden" class="form_text"  name="housepic.id" id="id"  value="${housepic[0].id }">
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="${ctx}${housepic[0].pic}" width="NAN" height="100" /></a></div><br>
					
				</td>
			
			</tr>
			<tr>
			<th width="20%"><span style="color: red;"></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="getUrl(this)" /></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;">*</span>楼盘图片类别：</th> -->
<!-- 				<td width="30%"> -->
<!-- 				<epmis:select  id="pic.tid" define="select id,name from t_pro_pictype where isdel='0'  " attr="style='width: 100%;'"  value="${pic[0].tid }" ></epmis:select> 	 -->
<!-- 				</td> -->
			
<!-- 			</tr> -->
			
			
       </table>
       
       
       
	
</form>
</div>
	<div class="BottomDiv">
		<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> <a
			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>
	</div>
</body>
</html>
