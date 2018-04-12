<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript" src="js/imagePreview.js"></script>
<script type="text/javascript">
	function MCEInit() {
		$("#CONTENTT_ifr").contents().find("#tinymce").html();
	};
	function UpdateRecord() {
		var currFrameId = "${param.currFrameId}";
		var content = tinyMCE.get('CONTENTT').getContent().replace(/"/g, "\'");
		$("#content").val(content);
		
		
		 var title=$("#title").val();
		 if(title==null || trim(title)==""){
			 $.messager.alert("错误","广告标题不能为空!");
			 return;
		 }
	        
	        
	      //判断网址http://https://开头，或者不是网址！
// 			 var r3=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
			 var r3=/^(http:\/\/|https:\/\/).*$/;
	        var url=$("#url").val();
	        var status=$("#type").val();
	       
// 	        if(status==2){
// 	        	if(r3.test(url)==false){
// 	        		$.messager.alert("错误","这网址不是以http://https://开头，或者不是网址！");
// 	 	        	return;
// 	        	}
// 	        }
		var res = $('#addForm').SubmitForm("${ctx}/adv/tadv/update.action");
		if (res[0].result == "success") {
			GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
			parent.close_win('eidtWindow');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}

	}
	 $(function(){
		 $("#CONTENTT").html($("#content").val());
		 var status='${adv[0].status}';
		 if(status==2){	    		
    		 
    		 $("#hhh").hide();
    		 $("#ttt").show();
    	 }
    		  if(status==1){
    			  
 	    		 $("#ttt").hide();
	    		 $("#hhh").show();
    		  }  
	  });
	  
	  function check(){
	    	 var status=$("#status").val();
	    	 
	    	 if(status==2){	    		 
	    		 $("#hhh").hide();
	    		 $("#ttt").show();
	    	 }
	    		  if(status==1){	    			  
	 	    		 $("#ttt").hide();
		    		 $("#hhh").show();
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
				<th width="20%"><span style="color: red;">*</span>个人中心广告图片：</th>
				<td width="30%">
				<input type="hidden" class="form_text"  name="adv.id" id="id"  value="${adv[0].id }">
				<input type="hidden" class="form_text"  name="adv.type" id="type"  value="${adv[0].type }">
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="${ctx}${adv[0].pic}" width="NAN" height="100" /></a></div><br>
				
				</td>		
			</tr>
			<tr>
			<th width="20%"><span style="color: red;"></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="getUrl(this)" /></td>
			</tr>
			<tr   >
				<th width="20%"><span style="color: red;">*</span>标题：</th>
				<td width="20%"><input class="form_text"  name="adv.title" id="title"  value="${adv[0].title }"></td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;">*</span>个人中心广告内容状态：</th>
				<td width="20%">
				
					<select class="form_text" name="adv.status" id="status" onchange="check()" style="width: 100%;">					
						<option value="2" <c:if test="${adv[0].status==2 }">selected="selected"</c:if>>广告链接</option>
						<option value="1" <c:if test="${adv[0].status==1 }">selected="selected"</c:if>>广告文本</option>						
					</select>
				</td>
			</tr>
			<tr id="ttt">
				<th width="20%"><span style="color: red;">*</span>个人中心广告内容(链接)：</th>
				<td width="20%"><input class="form_text"  name="adv.url" id="url"  value="${adv[0].url }"></td>
				</tr>
			
			<tr id="hhh">
			<th width="20%"><span style="color: red;">*</span>个人中心广告内容(文本)：</th>			
				<td>
					<textarea id="CONTENTT" name="CONTENTT" class="label_textarea"  style="height: 400px;width: 880px;" ></textarea> 					   	  
				</td>
			</tr>
       </table>
       <input class="form_text" type="hidden"  name="adv.content" id="content"  value="${adv[0].content }">	
</form>
</div>
	<div class="BottomDiv">
		<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> <a
			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>
	</div>
</body>
</html>
