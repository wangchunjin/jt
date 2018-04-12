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
	      
			 

			 
			 
			 var title=$("#title").val();
			 if(title==null || trim(title)==""){
				 $.messager.alert("错误","公告标题不能为空！");
				 return;
			 }
	        
 	        
 	       	
 	        
	 
 	     var r3=/^(http:\/\/|https:\/\/).*$/;
 	     
	        var url=$("#url").val();
	        var type=$("#type").val();
	       
	        if(type==2){
	        	if(r3.test(url)==false){
	        		$.messager.alert("错误","这网址不是以http://https://开头，或者不是网址！");
	 	        	return;
	        	}
	        }
 	        
			
	        var res = $('#addForm').SubmitForm("${ctx}/adv/tadv/save.action");
	       
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
			<tr id="aaa" style="display: none;">
				<th  align="right"><span style="color: red;"></span>公告图片：</th>
				<td>
<!-- 				<input class="form_text" type="hidden"  name="banner.oid" id="oid"  value="0"/> -->
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="../../../images/ic_pic_loading.png" width="NAN" height="100" /></a></div><br>
				</td>
			</tr>
			<tr id="bbb" style="display: none;">
			<th ><span style="color: red;"></span></th>
				<td ><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="previewImage(this)" />
				
				
				</td>
			</tr>	
			<tr   >
				<th width="15%"  align="right"><span style="color: red;">*</span>公告标题：</th>
				<td><input class="form_text"  name="adv.title" id="title"  value=""></td>
			</tr>		
			<tr style="display: none;">
				<th  align="right"><span style="color: red;">*</span>公告内容状态：</th>
				<td >
			
					<select class="form_text" name="adv.type" id="type" onchange="check()" style="width: 100%;">
						<option value="2">公告链接</option>
						<option value="1" selected="selected">公告文本</option>
						
					</select>
				</td>
			</tr>
			
			<tr id="ttt"  style="display: none;">
				<th align="right"><span style="color: red;">*</span>公告内容(链接)：</th>
				<td ><input class="form_text"  name="adv.url" id="url"  value=""></td>
			</tr>
			
			
			<tr id="hhh" >
			<th align="right"><span style="color: red;">*</span>公告内容(文本)：</th>
			
				<td>
					<textarea id="content" name="adv.content"   rows="20" cols="" ></textarea> 					   	  
				</td>
			</tr>
			

			
			
			
       </table>

</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
