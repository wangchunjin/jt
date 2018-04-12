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
			 $.messager.alert("错误","资讯标题不能为空！");
			 return;
		 }
		 var tid=$("#cons\\.typeid").val();
		 if(tid==null || trim(tid)==""){
			 $.messager.alert("错误","请选择咨询类别！");
			 return;
		 }

	
	        
	        
	      //判断网址http://https://开头，或者不是网址！
			 var r3=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
			 
	        var url=$("#url").val();
	        var status=$("#type").val();
	       
	        if(status==2){
	        	if(r3.test(url)==false){
	        		$.messager.alert("错误","这网址不是以http://https://开头，或者不是网址！");
	 	        	return;
	        	}
	        }
		var res = $('#addForm').SubmitForm("${ctx}/cons/tcons/update.action");
		if (res[0].result == "success") {
			GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
			parent.close_win('eidtWindow');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}

	}
	 $(function(){
		 $("#CONTENTT").html($("#content").val());
		 var status=$("#status").val();
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
	  function getUrl(node,imgId){   
		    var file = null;  
		    if(node.files && node.files[0] ){  
		        file = node.files[0]; 
		    }else if(node.files && node.files.item(0)) {                                  
		        file = node.files.item(0);     
		    }
//		    alert(getObjectURL(file));
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
		<tr align="right">
				<th width="10%"><span style="color: red;">*</span>图片1：</th>
				<th align="left"><div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="${ctx }${cons[0].pic1}" width="NAN" height="80" /></a></div><br></th>
<!-- 				<td width="20%" colspan="2" align="left"><input name="file" type="file"  id="productimg1"  class="offset10 lf" onchange="getUrl(this,'imghead')" /> -->
				
<!-- 				</td> -->
			
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;"></span></th>
				<td width="20%"  align="left">
				<input name="cons.id" type="hidden"  id="id"  value="${cons[0].id }" />
				<input name="file" type="file"  id="productimg1"  class="offset10 lf" onchange="getUrl(this,'imghead')" />
				
				</td>
			
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>图片2：</th>		
				<th align="left"><div class="lf salebd"><a href="#"  id="preview"><img id="imghead1"  src="${ctx }${cons[0].pic2}" width="NAN" height="80" /></a></div><br></th>
<!-- 				<td width="20%" colspan="2" align="left"><input name="file1" type="file" id="productimg2"  class="offset10 lf" onchange="getUrl(this,'imghead1')" /> -->
				
<!-- 				</td> -->
				
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;"></span></th>		
<!-- 				<th align="left"><div class="lf salebd"><a href="#"  id="preview"><img id="imghead1"  src="../../../images/ic_pic_loading.png" width="100" height="80" /></a></div><br></th> -->
				<td width="20%" colspan="2" align="left"><input name="file1" type="file" id="productimg2"  class="offset10 lf" onchange="getUrl(this,'imghead1')" />
				
				</td>
				
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;"></span>图片3：</th>
				<th align="left"><div class="lf salebd"><a href="#"  id="preview"><img id="imghead3"  src="${ctx }${cons[0].pic3}" width="NAN" height="80" /></a></div><br></th>
<!-- 				<td width="20%" colspan="2" align="left"><input name="file3" type="file" id="productimg3"  class="offset10 lf" onchange="getUrl(this,'imghead3')" /></td> -->
			</tr>	
			<tr align="right">
				<th width="10%"><span style="color: red;"></span></th>
<!-- 				<th align="left"><div class="lf salebd"><a href="#"  id="preview"><img id="imghead3"  src="../../../images/ic_pic_loading.png" width="100" height="80" /></a></div><br></th> -->
				<td width="20%"  align="left"><input name="file3" type="file" id="productimg3"  class="offset10 lf" onchange="getUrl(this,'imghead3')" /></td>
			</tr>		
			<tr id=""  align="right">
				<th width="10%"><span style="color: red;">*</span>标题：</th>
				<td width="20%"><input class="form_text"  name="cons.title" id="title"  value="${cons[0].title }"></td>
			</tr>
			<tr id=""  align="right">
				<th width="10%"><span style="color: red;">*</span>资讯类别：</th>
				<td width="20%">
				
				<epmis:select  id="cons.typeid" define="select id,name from t_cons_type where isdel='0'  " value="${cons[0].typeid }" attr="style='width: 100%;'"  ></epmis:select>
				</td>
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>资讯内容状态：</th>
				<td width="20%">
			
					<select class="form_text" name="cons.status" id="status" onchange="check()" style="width: 100%;">
						<option value="2" <c:if test="${cons[0].status==2 }">selected="selected"</c:if>>链接</option>
						<option value="1" <c:if test="${cons[0].status==1 }">selected="selected"</c:if>>文本</option>	
					</select>
				</td>
			</tr>
			
			<tr id="ttt"  align="right">
				<th width="10%"><span style="color: red;">*</span>内容(链接)：</th>
				<td width="20%"><input class="form_text"  name="cons.url" id="url"  value="${cons[0].url }"></td>
			</tr>
			
			
			<tr id="hhh" style="display:none;" align="right">
			<th width="10%"><span style="color: red;">*</span>内容(文本)：</th>
			
				<td>
					<textarea id="CONTENTT" name="CONTENTT" class="label_textarea"  style="height: 400px;width: 880px;" ></textarea> 					   	  
				</td>
			</tr>
       </table>
       <input class="form_text" type="hidden"  name="cons.content" id="content"  value="${cons[0].content }">	
</form>
</div>
	<div class="BottomDiv">
		<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> <a
			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>
	</div>
</body>
</html>
