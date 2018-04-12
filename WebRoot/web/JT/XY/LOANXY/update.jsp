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
		
	
		
		
	
	        
	        
	      //判断网址http://https://开头，或者不是网址！
// 			 var r3=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
			 
// 	        var url=$("#url").val();
// 	        if(r3.test(url)==false){
//         		$.messager.alert("错误","这网址不是以http://https://开头，或者不是网址！");
//  	        	return;
//         	}
	       
		var res = $('#addForm').SubmitForm("${ctx}/agree/tagree/updateLoan.action");
		if (res[0].result == "success") {
			location.reload();
			parent.close_win('eidtWindow');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}

	}
	 $(function(){
		 $("#CONTENTT").html($("#content").val());
// 		 var status=$("#type").val();
// 		 if(status==2){	    		
    		 
//     		 $("#hhh").hide();
//     		 $("#ttt").show();
//     	 }
//     		  if(status==1){
    			  
//  	    		 $("#ttt").hide();
// 	    		 $("#hhh").show();
//     		  }  
	  });
	  
// 	  function check(){
// 	    	 var status=$("#type").val();
	    	 
// 	    	 if(status==2){	    		 
// 	    		 $("#hhh").hide();
// 	    		 $("#ttt").show();
// 	    	 }
// 	    		  if(status==1){
	    			  
// 	 	    		 $("#ttt").hide();
// 		    		 $("#hhh").show();
// 	    		  } 
// 	     }
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
<!-- 			<tr > -->
<!-- 				<th width="10%" align="right"><span style="color: red;">*</span>图片：</th> -->
<!-- 				<td width="30%" align="left"> -->
				<input type="hidden" class="form_text"  name="agree.id" id="id"  value="${agree[0].id }">
<!-- 				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="${ctx}${about[0].pic}" width="NAN" height="100" /></a></div><br> -->
				
<!-- 				</td> -->
			
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 			<th width="10%"><span style="color: red;"></th> -->
<!-- 				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="getUrl(this)" /></td> -->
<!-- 			</tr> -->
			
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;">*</span>首页轮播内容状态：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 					<select class="form_text" name="banner.type" id="type" onchange="check()" style="width: 100%;"> -->
					
<!-- 						<option value="2" <c:if test="${banner[0].type==2 }">selected="selected"</c:if>>首页轮播链接</option> -->
<!-- 						<option value="1" <c:if test="${banner[0].type==1 }">selected="selected"</c:if>>首页轮播文本</option> -->
						
<!-- 					</select> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 				<tr id="ttt" > -->
<!-- 					<th width="10%" align="right"><span style="color: red;">*</span>客服电话：</th> -->
<!-- 					<td width="20%"><input class="form_text"  name="about.servicephone" id="servicephone"  value="${about[0].servicephone }"></td> -->
<!-- 				</tr> -->
<!-- 				<tr id="ddd" > -->
<!-- 					<th width="10%" align="right"><span style="color: red;">*</span>微信公众号名称：</th> -->
<!-- 					<td width="20%"><input class="form_text"  name="about.weixinname" id="weixinname"  value="${about[0].weixinname }"></td> -->
<!-- 				</tr> -->
<!-- 				<tr id="ttt" > -->
<!-- 					<th width="10%" align="right"><span style="color: red;">*</span>公司介绍：</th> -->
<!-- 					<td width="20%"> -->
<!-- 						<textarea name="about.content" rows="8" cols="">${about[0].content }</textarea> -->
<!-- 					</td> -->
<!-- 				</tr> -->			
			<tr id="hhh">
			<th width="10%" align="right"><span style="color: red;">*</span>借款协议：</th>
			
				<td>
					<textarea id="CONTENTT" name="CONTENTT" class="label_textarea"  style="height: 400px;width: 880px;" >${agree[0].loan_content }</textarea> 					   	  
				</td>
			</tr>
       </table>
       <input class="form_text" type="hidden"  name="agree.loan_content" id="content"  value="${agree[0].loan_content }">	
</form>
</div>
	<div class="BottomDiv">
		<epmis:button id="NewRecord" imageCss="icon-edit" value="确定" action="UpdateRecord()" datactrCode="xy_loan.edit" ></epmis:button> 
<!-- 		<a -->
<!-- 			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a> -->
	</div>
</body>
</html>
