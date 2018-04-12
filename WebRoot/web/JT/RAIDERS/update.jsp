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
		
// 		var title=$("#title").val();
// 		 if(title==null || trim(title)==""){
// 			 $.messager.alert("错误","公告标题不能为空！");
// 			 return;
// 		 }
// 		var type=$("#type").val();
		 
// 		 if(type==3){
// 			 var lp=$("#lp").val();
// 			 $("#oid").val(lp);
// 		 }
// 		 if(type==4){
// 			 var house=$("#house").val();
// 			 $("#oid").val(house);
// 		 }
	        
	        
	      //判断网址http://https://开头，或者不是网址！
// 			 var r3=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
	      var r3=/^(http:\/\/|https:\/\/).*$/;
			 
	        var url=$("#url").val();
	        var status=$("#type").val();
	       
	        if(status==2){
	        	if(r3.test(url)==false){
	        		$.messager.alert("错误","这网址不是以http://https://开头，或者不是网址！");
	 	        	return;
	        	}
	        }
	        
		var res = $('#addForm').SubmitForm("${ctx}/raiders/traiders/update.action");
		if (res[0].result == "success") {
			GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
			parent.close_win('eidtWindow');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}

	}
	 $(function(){
		 $("#CONTENTT").html($("#content").val());
		 var status=$("#type").val();
		if(status==2){	    		
    		 
    		 $("#hhh").hide();
    		 $("#ttt").show();
//     		 $("#shens").hide();
//     		 $("#shis").hide();
//     		 $("#qus").hide();
//     		 $("#aaaa").hide();
//     		 $("#bbbb").hide();
    	 }
    		  if(status==1){
    			  
 	    		 $("#ttt").hide();
	    		 $("#hhh").show();
// 	    		 $("#shens").hide();
// 	    		 $("#shis").hide();
// 	    		 $("#qus").hide();
// 	    		 $("#aaaa").hide();
// 	    		 $("#bbbb").hide();
    		  }  
//     		  if(status==3){	    			  
// 	 	    		 $("#ttt").hide();
// 		    		 $("#hhh").hide();
// 		    		 $("#bbbb").hide();
// 		    		 $("#shens").show();
// 		    		 $("#shis").show();
// 		    		 $("#qus").show();
// 		    		 $("#aaaa").show();
		    		 
// 		    		 获取当前轮播楼盘的id		    		 
// 		    		 var oid=${banner[0].oid};
// 		    		 var result = GetXmlBySql("SELECT shen,shi,qu FROM t_prosale WHERE id="+oid);
// 		    		 var shen=result[0].shen;
// 		    		 var shi=result[0].shi;
// 		    		 var qu=result[0].qu;		    		 
// 		    		 区域该省的wid
// 		    		 var result1 = GetXmlBySql("SELECT wid FROM cm_region WHERE area_type='1' and ssqid="+shen);
		    		 
// 		    		区域该市的wid
// 		    		 var result2 = GetXmlBySql("SELECT wid FROM cm_region WHERE area_type='2' and ssqid="+shi);
// 		    		区域该县的wid
// 		    		 var result3 = GetXmlBySql("SELECT wid FROM cm_region WHERE area_type='3' and ssqid="+qu);
// 		    		 省
// 			     	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='1' and parent_id='0'","shen",result1[0].wid,new Array("=--请选择--")); 
			    	   		
// 		    		 市
// 		    	   	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='2' and parent_id='"+result1[0].wid+"'","shi",result2[0].wid,new Array("=--请选择--")); 
		    	   		
// 		    		 区
// 		    	   	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='3' and parent_id='"+result2[0].wid+"'","qu",result3[0].wid,new Array("=--请选择--"));
		    		 
// 		    		 楼盘
// 		    	   	SetSelect("SELECT id,title FROM t_prosale WHERE isdel='0' and qu='"+qu+"'","lp",oid,new Array("=--请选择--")); 
		    		 
		    		 
		    		 
		    		 
		    		 
// 	    		  }  
//     		  if(status==4){	    			  
// 	 	    		 $("#ttt").hide();
// 		    		 $("#hhh").hide();
// 		    		 $("#bbbb").show();
// 		    		 $("#shens").show();
// 		    		 $("#shis").show();
// 		    		 $("#qus").show();
// 		    		 $("#aaaa").show();
		    		 
// 		    		获取当前轮播户型的id		    		 
// 		    		 var oid=${banner[0].oid};
// 		    		 var result4 = GetXmlBySql("SELECT l_id FROM t_house WHERE id="+oid);
// 		    		 var result = GetXmlBySql("SELECT shen,shi,qu FROM t_prosale WHERE id="+result4[0].l_id);
// 		    		 var shen=result[0].shen;
// 		    		 var shi=result[0].shi;
// 		    		 var qu=result[0].qu;
		    		 
// 		    		 区域该省的wid
// 		    		 var result1 = GetXmlBySql("SELECT wid FROM cm_region WHERE area_type='1' and ssqid="+shen);
		    		 
// 		    		区域该市的wid
// 		    		 var result2 = GetXmlBySql("SELECT wid FROM cm_region WHERE area_type='2' and ssqid="+shi);
// 		    		区域该县的wid
// 		    		 var result3 = GetXmlBySql("SELECT wid FROM cm_region WHERE area_type='3' and ssqid="+qu);
		    		
// 		    		 省
// 			     	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='1' and parent_id='0'","shen",result1[0].wid,new Array("=--请选择--")); 
			    	   		
// 		    		 市
// 		    	   	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='2' and parent_id='"+result1[0].wid+"'","shi",result2[0].wid,new Array("=--请选择--")); 
		    	   		
// 		    		 区
// 		    	   	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='3' and parent_id='"+result2[0].wid+"'","qu",result3[0].wid,new Array("=--请选择--"));
		    		 
// 		    		 楼盘
// 		    	   	SetSelect("SELECT id,title FROM t_prosale WHERE isdel='0' and qu='"+qu+"'","lp",result4[0].l_id,new Array("=--请选择--")); 
		    		 
// 		    	  	户型
// 		    	   	SetSelect("SELECT id,name FROM t_house WHERE isdel='0' and l_id='"+result4[0].l_id+"'","house",oid,new Array("=--请选择--"));
		    		 
		    		 
// 	    		  }  
	  });
	  
	 function check(){
    	 var status=$("#type").val();
    	 
    	 if(status==2){	    		
    		 
    		 $("#hhh").hide();
    		 $("#ttt").show();
//     		 $("#shens").hide();
//     		 $("#shis").hide();
//     		 $("#qus").hide();
//     		 $("#aaaa").hide();
//     		 $("#bbbb").hide();
    	 }
    		  if(status==1){
    			  
 	    		 $("#ttt").hide();
	    		 $("#hhh").show();
// 	    		 $("#shens").hide();
// 	    		 $("#shis").hide();
// 	    		 $("#qus").hide();
// 	    		 $("#aaaa").hide();
// 	    		 $("#bbbb").hide();
    		  }  
//     		  if(status==3){	    			  
// 	 	    		 $("#ttt").hide();
// 		    		 $("#hhh").hide();
// 		    		 $("#bbbb").hide();
// 		    		 $("#shens").show();
// 		    		 $("#shis").show();
// 		    		 $("#qus").show();
// 		    		 $("#aaaa").show();
		    		 
// 	    		  }  
//     		  if(status==4){	    			  
// 	 	    		 $("#ttt").hide();
// 		    		 $("#hhh").hide();
// 		    		 $("#bbbb").show();
// 		    		 $("#shens").show();
// 		    		 $("#shis").show();
// 		    		 $("#qus").show();
// 		    		 $("#aaaa").show();
		    		 
// 	    		  }  
    	 
    	 
     }
     
//      function changeValue(){
//     	   	var obj=$("#shen").val();
//     	   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='2' and parent_id='"+obj+"'","shi","",new Array("=--请选择--")); 
//     	   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","qu","",new Array("=--请选择--")); 
//     	   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","lp","",new Array("=--请选择--")); 
//     	   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","house","",new Array("=--请选择--")); 
    	   		
//     		}
//      function changeValue2(){
// 		   	var obj=$("#shi").val();
// 		   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='3' and parent_id='"+obj+"'","qu","",new Array("=--请选择--")); 
// 		   		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","lp","",new Array("=--请选择--"));
// 		   	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","house","",new Array("=--请选择--")); 
		   		
// 			}
//      function changeValue3(){
// 		   	var obj=$("#qu").val();
// 		   var result = GetXmlBySql("SELECT ssqid FROM cm_region WHERE wid="+obj);
//     	 qu=result[0].ssqid;
// 		   		SetSelect("SELECT id,title FROM t_prosale WHERE isdel='0' and qu='"+qu+"'","lp","",new Array("=--请选择--")); 
// 		   	SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='0' and parent_id='0'","house","",new Array("=--请选择--")); 
		   		 
		   		
// 			}
//      function changeValue4(){
//  		   	var obj=$("#lp").val();
 		   
//  		   		SetSelect("SELECT id,name FROM t_house WHERE isdel='0' and l_id='"+obj+"'","house","",new Array("=--请选择--")); 
 		   		 
 		   		
//  			}
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
				<th width="20%"><span style="color: red;">*</span>攻略图片：</th>
				<td width="30%">
				<input type="hidden" class="form_text"  name="raiders.id" id="id"  value="${raiders[0].id }">
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="${ctx}${raiders[0].pic}" width="NAN" height="100" /></a></div><br>
				
				</td>	
			</tr>
			<tr>
			<th width="20%"><span style="color: red;"></span></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="getUrl(this)" /></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<th width="20%" ><span style="color: red;">*</span>攻略标题：</th> -->
<!-- 				<td width="20%"><input class="form_text"  name="raiders.title" id="title"  value="${raiders[0].title}"></td> -->
<!-- 			</tr>	 -->
			<tr>
				<th width="20%"><span style="color: red;">*</span>攻略内容状态：</th>
				<td width="20%">				
					<select class="form_text" name="raiders.type" id="type" onchange="check()" style="width: 100%;">
					
						<option value="2" <c:if test="${raiders[0].type==2 }">selected="selected"</c:if>>攻略链接</option>
						<option value="1" <c:if test="${raiders[0].type==1 }">selected="selected"</c:if>>攻略文本</option>
<!-- 						<option value="3" <c:if test="${banner[0].type==3 }">selected="selected"</c:if>>楼盘</option> -->
<!-- 						<option value="4" <c:if test="${banner[0].type==4 }">selected="selected"</c:if>>户型</option> -->
						
					</select>
				</td>
			</tr>
			<tr id="ttt">
				<th width="20%"><span style="color: red;">*</span>攻略内容(链接)：</th>
				<td width="20%"><input class="form_text"  name="raiders.url" id="url"  value="${raiders[0].url }"></td>
			</tr>			
			<tr id="hhh">
			<th width="20%"><span style="color: red;">*</span>攻略内容(文本)：</th>			
				<td>
					<textarea id="CONTENTT" name="CONTENTT" class="label_textarea"  style="height: 400px;width: 880px;" ></textarea> 					   	  
				</td>
			</tr>
			
			
			
<!-- 			<tr id="shens" style="display:none;"> -->
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
       <input class="form_text" type="hidden"  name="raiders.content" id="content"  value="${raiders[0].content }">	
</form>
</div>
	<div class="BottomDiv">
		<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> <a
			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>
	</div>
</body>
</html>
