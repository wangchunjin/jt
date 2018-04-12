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
			 var r3=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
			 
	        var url=$("#url").val();
	        var status=$("#status").val();
	       
	        if(status==2){
	        	if(r3.test(url)==false){
	        		$.messager.alert("错误","这网址不是以http://https://开头，或者不是网址！");
	 	        	return;
	        	}
	        }
 	        
			
	        var res = $('#addForm').SubmitForm("${ctx}/glodjs/tglodjs/save.action");
	       
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
	     };
	    
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
			
			<tr>
				<th width="20%" align="right"><span style="color: red;">*</span>金牌咨询内容状态：</th>
				<td width="20%">
			
					<select class="form_text" name="glodjs.status" id="status" onchange="check()" style="width: 100%;">
						<option value="2">金牌咨询介绍链接</option>
						<option value="1">金牌咨询介绍文本</option>
						
					</select>
				</td>
			</tr>
			
			<tr id="ttt"  >
				<th width="20%" align="right"><span style="color: red;">*</span>金牌咨询内容(链接)：</th>
				<td width="20%"><input class="form_text"  name="glodjs.url" id="url"  value=""></td>
			</tr>
			
			
			<tr id="hhh" style="display:none;">
			<th width="20%" align="right"><span style="color: red;">*</span>金牌咨询内容(文本)：</th>
			
				<td>
					<textarea id="CONTENTT" name="CONTENTT" class="label_textarea"  style="height: 400px;width: 880px;" ></textarea> 					   	  
				</td>
			</tr>
			
			
       </table>
       <input class="form_text" type="hidden"  name="glodjs.content" id="content"  value="">	
</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
