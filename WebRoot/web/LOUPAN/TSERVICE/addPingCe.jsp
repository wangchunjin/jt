<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
 <script type="text/javascript">
 //文本编辑
 function MCEInit(){ 
     $("#CONTENTT_ifr").contents().find("#tinymce").html(""); 
};  

 function AddRecord(){
	        var currFrameId = "${param.currFrameId}";
	        //文本编辑
	        var content =  tinyMCE.get('CONTENTT').getContent().replace(/"/g, "\'"); 
			 $("#content").val(content);

	        
 	        
 	       	
 	        
 	     //判断网址http://https://开头，或者不是网址！
// 			 var r3=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
			 
// 	        var url=$("#url").val();
// 	        var type=$("#type").val();
	       
// 	        if(type==2){
// 	        	if(r3.test(url)==false){
// 	        		$.messager.alert("错误","这网址不是以http://https://开头，或者不是网址！");
// 	 	        	return;
// 	        	}
// 	        }
 	        
			
	        var res = $('#addForm').SubmitForm("${ctx}/tservice/ttservice/save.action");
	       
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
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
				<th width="20%"><span style="color: red;"></span>服务图片：</th>
				<td width="30%">
				
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="../../../images/ic_pic_loading.png" width="NAN" height="100" /></a></div><br>
				</td>
			</tr>
			<tr id="bbb" >
			<th width="20%"><span style="color: red;"></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="previewImage(this)" />
				
				
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;">*</span>服务类型：</th>
				<td width="20%">
				<epmis:select  id="tservice.status" define="select id,name from t_service_type where isdel='0'  " attr="style='width: 100%;'"  ></epmis:select>
<!-- 					<select class="form_text" name="tservice.status" id="status"  style="width: 100%;"> -->
<!-- 						<option value="1">服务理念</option> -->
<!-- 						<option value="2">服务文化</option>	 -->
<!-- 						<option value="3">专车看房</option>	 -->
<!-- 						<option value="4">金牌顾问</option>	 -->
<!-- 						<option value="5">楼盘分析</option>	 -->
<!-- 					</select> -->
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;">*</span>服务内容状态：</th>
				<td width="20%">
			
					<select class="form_text" name="tservice.type" id="type" onchange="check()" style="width: 100%;">
						<option value="2">服务链接</option>
						<option value="1">服务文本</option>	
					</select>
				</td>
			</tr>
			
			<tr id="ttt"  >
				<th width="20%"><span style="color: red;">*</span>服务内容(链接)：</th>
				<td width="20%"><input class="form_text"  name="tservice.url" id="url"  value=""></td>
			</tr>
			
			
			<tr id="hhh" style="display:none;">
			<th width="20%"><span style="color: red;">*</span>服务内容(文本)：</th>
			
				<td>
					<textarea id="CONTENTT" name="CONTENTT" class="label_textarea"  style="height: 400px;width: 880px;" ></textarea> 					   	  
				</td>
			</tr>
			
       </table>
       <input class="form_text" type="hidden"  name="tservice.content" id="content"  value="">	
</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
