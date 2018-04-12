<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
 <script type="text/javascript">
	     function UpdateRecord(){
	    	 var cuid=$("#cuid").val();
	    	 
	    	 var istrue=false;
	    	 
	    	 var result1=GetXmlBySql("SELECT (select name from lender l where l.lender_id=b.lender_id)lender_id,batch_id,batch_status,bd FROM batch_transfer b  ORDER BY batch_id desc");
	    	if(result1[0].batch_status!=0){
	    		$.messager.alert("错误","没有未处理订单！");
 	        	return;
	    	}
	    	
	    	 var path=$("#path").val();
	    	 path="0";
	    	 
	    	 var type=$("#type").val();
		        
		        if(type==null|| type==""){
		        	path="1";
		        	$.messager.alert("错误","请选择导入类型！");
	 	        	return;
		        }
	       		
	        var file = $("#productimg").val();
		    if(file==null || file==""){
		    	path="2";
		    	$.messager.alert("错误","请选择导入的文件！");
 	        	return;
		    }
		    
		    var FileExt=file.replace(/.+\./,"");   //正则表达式获取后缀
	        if(FileExt!="xls"){
	        	path="3";
	        	$.messager.alert("错误","Excel导入文件格式不正确（xls）！");
 	        	return;
	        }
		    var aaa="0";
	        
			if(aaa!="0"){
	        	path="3";
	        	$.messager.alert("错误","Excel导入文件格式不正确（xls）！");
 	        	return;
	        }
		    
//  			var path=$("#path").val();
	        
// 	        if(path==null|| path==""){
// 	        	$.messager.alert("错误","Excel导入名不能为空！");
//  	        	return;
// 	        }	
				
if(!istrue){

        $("#b").show();
        $("#a").hide();
        istrue=true;
		
}else{

    $("#a").show();   
		$("#b").hide();
		istrue=false;
}
			
				

	        setTimeout(function(){
	        	var res = $('#addForm').SubmitForm("${ctx}/excel/texcel/edmt.action?path="+path+"&type="+type+"&bd="+result1[0].bd+"&batch_id="+result1[0].batch_id+"&lender_id="+result1[0].lender_id+"&cuid="+cuid);
		        
		        if(res==undefined){
		        	$("#a").show();		 	   
					$("#b").hide();
		        }
		 	  	if(res[0].result=="success"){
// 		 	  	GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
		 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
		 		parent.close_win('addWindow');
		 		istrue=false;
		 		$("#a").show();
		 	   
				$("#b").hide();
				}
	 			else{
	 				istrue=true;
	 				$.messager.alert("Execl导入!",res[0].result);
	 				$("#a").show();
	 		        
					$("#b").hide();
					GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
			 		parent.close_win('addWindow');
	 				
	 				
	 		 	}
	        },200);
	
	     }
	     
// 	     function getPhoto(){
// 	    		var file = $("#file").val();
		       
// 		        var strFileName=file.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1");  //正则表达式获取文件名，不带后缀
// 		        var FileExt=file.replace(/.+\./,"");   //正则表达式获取后缀
// 		        var name=strFileName+"."+FileExt;
		        
// 		        $("#path").val(name);
// 	     }
	   


	    	

	    
 </script>
  <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	
</style>
 </head>
 
 
  <body >
  



   <form method="post" action="" id="addForm" name="addForm" enctype="multipart/form-data">
   <div id="a" >
		<table style="margin-top: 100px;margin-right:150px  ;width: 96%;" >
		<input type="hidden" id="cuid" value="${sessionScope.UserInfo.userId}">
		<tr align="right">
				<th width="10%"><span style="color: red;">*</span>请选择导入类型：</th>
				<td width="20%">
					<select id="type" class="form_text" >
						<option value="">--请选择--</option>
						<option value="1">批量转账</option>
						
					</select>		
				</td>
			</tr>
			<tr align="right">	
<!-- 				<th width="10%"><span style="color: red;">*</span>请输入文件名(包含后缀)：</th> -->
				<td width="20%">
				
				<input class="form_text" type="hidden"  name="off.minprice" id="path"  value=""></td>
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;"></span></th>
				<td width="20%" align="left">
				<input name="file" type="file" id="productimg"  class="offset10 lf" onchange="previewImage(this)" />
				
<!-- 				<h6 style="color:red;">导入的Excel文件请放于‘D’盘的‘Excel导入管理’文件夹内,在进行操作</h6>	 -->
				</td>
				
				
				
				
				

			</tr>
			
			
			
			
			
       </table>
       </div>
       
       <div id="b" style="display:none; ">

   
		<table style="margin-top: 100px;margin-right:150px  ;width: 96%;" >
		
		
			<tr align="right">
				<th width="10%"><span style="color: red;"></span></th>
				<td width="20%" align="left">
				
				
				<h6 style="color:red;"><h6 style="color:red;font-size: 17px;">数据正在传输中，等待中请勿操作......</h6></h6>	
				</td>
				
				
				
				
				

			</tr>
			
			
			
			
       </table>

</div>
</form>


		<div class="BottomDiv">
		<epmis:button id="NewRecord" imageCss="icon-edit" value="确定" action="UpdateRecord()" datactrCode="" ></epmis:button>
<!-- 			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> -->
			</a>
		</div>
  </body>
</html>
