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
			 
			 var type=$("#type").val();
			 
			 

	        
 	        
 	       	
 	        
 	     //判断网址http://https://开头，或者不是网址！
// 			 var r3=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
			 
 	     var r3=/^(http:\/\/|https:\/\/).*$/;
 	     
	        var url=$("#url").val();
	        var type=$("#type").val();
	       
	        if(type==2){
	        	if(r3.test(url)==false){
	        		$.messager.alert("错误","这网址不是以http://https://开头，或者不是网址！");
	 	        	return;
	        	}
	        }
 	        
			
	        var res = $('#addForm').SubmitForm("${ctx}/banner/tbanner/save.action");
	       
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
// 	    		 $("#shens").hide();
// 	    		 $("#shis").hide();
// 	    		 $("#qus").hide();
// 	    		 $("#aaaa").hide();
// 	    		 $("#bbbb").hide();
	    	 }
	    		  if(status==1){
	    			  
	 	    		 $("#ttt").hide();
		    		 $("#hhh").show();
// 		    		 $("#shens").hide();
// 		    		 $("#shis").hide();
// 		    		 $("#qus").hide();
// 		    		 $("#aaaa").hide();
// 		    		 $("#bbbb").hide();
	    		  }  
// 	    		  if(status==3){	    			  
// 		 	    		 $("#ttt").hide();
// 			    		 $("#hhh").hide();
// 			    		 $("#bbbb").hide();
// 			    		 $("#shens").show();
// 			    		 $("#shis").show();
// 			    		 $("#qus").show();
// 			    		 $("#aaaa").show();
			    		 
// 		    		  }  
// 	    		  if(status==4){	    			  
// 		 	    		 $("#ttt").hide();
// 			    		 $("#hhh").hide();
// 			    		 $("#bbbb").show();
// 			    		 $("#shens").show();
// 			    		 $("#shis").show();
// 			    		 $("#qus").show();
// 			    		 $("#aaaa").show();
			    		 
// 		    		  }  
	    	 
	    	 
	     }
	     
// 	     function changeValue(){
// 	    	   	var obj=$("#shen").val();
// 	    	   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='2' and parent_id='"+obj+"'","shi","",new Array("=--请选择--")); 
// 	    	   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","qu","",new Array("=--请选择--")); 
// 	    	   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","lp","",new Array("=--请选择--")); 
// 	    	   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","house","",new Array("=--请选择--")); 
	    	   		
// 	    		}
// 	     function changeValue2(){
//  		   	var obj=$("#shi").val();
//  		   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='3' and parent_id='"+obj+"'","qu","",new Array("=--请选择--")); 
//  		   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","lp","",new Array("=--请选择--"));
//  		   	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","house","",new Array("=--请选择--")); 
 		   		
//  			}
// 	     function changeValue3(){
//  		   	var obj=$("#qu").val();
//  		   var result = GetXmlBySql("SELECT ssqid FROM cm_region WHERE wid="+obj);
// 	    	 qu=result[0].ssqid;
//  		   		SetSelect("SELECT id,title FROM t_prosale WHERE isdel='0' and qu='"+qu+"'","lp","",new Array("=--请选择--")); 
//  		   	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","house","",new Array("=--请选择--")); 
 		   		 
 		   		
//  			}
// 	     function changeValue4(){
// 	 		   	var obj=$("#lp").val();
	 		   
// 	 		   		SetSelect("SELECT id,name FROM t_house WHERE isdel='0' and l_id='"+obj+"'","house","",new Array("=--请选择--")); 
	 		   		 
	 		   		
// 	 			}
	     
	     
	     
	     
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
				<th width="20%" align="right"><span style="color: red;"></span>首页轮播图片：</th>
				<td width="30%">
<!-- 				<input class="form_text" type="hidden"  name="banner.oid" id="oid"  value="0"/> -->
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="../../../images/ic_pic_loading.png" width="NAN" height="100" /></a></div><br>
				</td>
			</tr>
			<tr id="bbb" >
			<th width="20%"><span style="color: red;"></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="previewImage(this)" />
				
				
				</td>
			</tr>			
			<tr>
				<th width="20%" align="right"><span style="color: red;">*</span>首页轮播内容状态：</th>
				<td width="20%">
			
					<select class="form_text" name="banner.type" id="type" onchange="check()" style="width: 100%;">
						<option value="2">首页轮播链接</option>
						<option value="1">首页轮播文本</option>
						
					</select>
				</td>
			</tr>
			
			<tr id="ttt"  >
				<th width="20%" align="right"><span style="color: red;">*</span>首页轮播内容(链接)：</th>
				<td width="20%"><input class="form_text"  name="banner.url" id="url"  value=""></td>
			</tr>
			
			
			<tr id="hhh" style="display:none;">
			<th width="20%" align="right"><span style="color: red;">*</span>首页轮播内容(文本)：</th>
			
				<td>
					<textarea id="CONTENTT" name="CONTENTT" class="label_textarea"  style="height: 400px;width: 880px;" ></textarea> 					   	  
				</td>
			</tr>
			
<!-- 		<tr id="shens" style="display:none;"> -->
<!-- 				<th width="35" height="25" align="right">省份:</th> -->
<!-- 		      	<td width="100"> <epmis:select  id="shen" define="select wid,area_name from cm_region where area_type='1'"   onChange="changeValue()"></epmis:select> -->
<!-- 		       </td> -->
		      
		       
<!--        </tr> -->
<!--        <tr id="shis" style="display:none;"> -->
				
<!-- 		       <th width="35" height="25" align="right">城市:</th> -->
<!-- 		      	<td width="100"> -->
<!-- 		      	<epmis:select id="shi" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select> -->
		      
<!-- 		       </td> -->
		      
<!--        </tr> -->
       
<!--        <tr id="qus" style="display:none;">				 -->
<!-- 		       <th width="35" height="25" align="right">区县:</th> -->
<!-- 		      	<td width="100">  -->
<!-- 		      	<epmis:select id="qu" define="" attr="style='width: 100%;'" onChange="changeValue3()" ></epmis:select>		       -->
<!-- 		       </td> -->
<!--        </tr> -->
<!--        <tr id="aaaa" style="display:none;">				 -->
<!-- 		       <th width="35" height="25" align="right">楼盘:</th> -->
<!-- 		      	<td width="100">  -->
<!-- 		      	<epmis:select id="lp" define="" attr="style='width: 100%;'" onChange="changeValue4()"></epmis:select> -->
		      
<!-- 		       </td> -->
<!--        </tr> -->
<!--        <tr id="bbbb" style="display:none;">				 -->
<!-- 		       <th width="35" height="25" align="right">户型:</th> -->
<!-- 		      	<td width="100">  -->
<!-- 		      	<epmis:select id="house" define="" attr="style='width: 100%;'" ></epmis:select> -->
		      
<!-- 		       </td> -->
<!--        </tr> -->
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
       </table>
       <input class="form_text" type="hidden"  name="banner.content" id="content"  value="">	
</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
