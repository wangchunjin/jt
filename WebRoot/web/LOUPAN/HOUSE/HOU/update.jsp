<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript" src="js/imagePreview.js"></script>

<script type="text/javascript">
// function MCEInit() {
// 	$("#CONTENTT_ifr").contents().find("#tinymce").html();
// };
	function UpdateRecord() {
		var currFrameId = "${param.currFrameId}";
// 		var content = tinyMCE.get('CONTENTT').getContent().replace(/"/g, "\'");
// 		$("#content").val(content);
// 		var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
	        
		var chk_value =[]; 
        $('input[name="building"]:checked').each(function(){ 
        chk_value.push($(this).val()); 
        });
//	        alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 
        $("#lable").val(chk_value);
        
        var name=$("#name").val();
        if(name==null || trim(name)==""){
        	$.messager.alert("错误","户型名称不能为空！");
        	return;
        }
        var price=$("#price").val();
        if(price==null || trim(price)==""){
        	$.messager.alert("错误","户型总价不能为空！");
        	return;
        }
        var area=$("#area").val();
        if(area==null || trim(area)==""){
        	$.messager.alert("错误","户型面积不能为空！");
        	return;
        }
        var typeid=$("#house\\.typeid").val();
        if(typeid==null || trim(typeid)==""){
        	$.messager.alert("错误","请选择户型！");
        	return;
        }
        
        setTimeout(function(){
		var res = $('#addForm').SubmitForm("${ctx}/house/thouse/update.action");
		if (res[0].result == "success") {
			GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
			parent.close_win('eidtWindow');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}
        },200);
        

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
	
	$(function(){
// 		$("#CONTENTT").html($("#content").val());
		 //房屋标签
		var label="${house[0].lable }";		
		var label1=label.split(",");
		
		
		 
		
		
		
		
		var result = GetXmlBySql("SELECT id,name FROM t_house_label WHERE isdel='0'");
		var str="";
		var  dd="";
		for(var i=0;i<result.length;i++){
			
			for(var j=0;j<label1.length;j++){
				if(result[i].id==label1[j]){
					dd="checked='checked'";
					break;				
				}else
					dd="";
							

				}
			
			str=str+"<input  type='checkbox'  name='building' "+dd+"   value='"+result[i].id+"'>"+result[i].name+" ";
			dd="";
			
		}
		$("#ksksk").html(str);
	});
	


	
	
	
	
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
				<th width="20%"><span style="color: red;">*</span>户型大图：</th>
				<td width="30%">
				<input type="hidden" class="form_text"  name="house.lable" id="lable"  value="${house[0].lable }">
				<input type="hidden" class="form_text"  name="house.id" id="id"  value="${house[0].id }">
				<input type="hidden" class="form_text"  name="house.l_id" id="lid"  value="${house[0].l_id }">
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="${ctx}${house[0].pic}" width="NAN" height="100" /></a></div><br>
				
				</td>
			
			</tr>
			<tr>
			<th width="20%"><span style="color: red;"></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="getUrl(this)" /></td>
			</tr>
		<tr align="right">	
			<input class="form_text" type="hidden"  name="house.l_id" id="lid"  value="${param.lid }" >		
				<th width="20%"><span style="color: red;">*</span>名称：</th>
				<td width="20%">
				<input class="form_text"  name="house.name" id="name"  value="${house[0].name }" ></td>
				<th width="20%"><span style="color: red;">*</span>室/厅/卫：</th>
				<td width="28%" align="left">
				<select  name="house.shi" id="shi" >
					<option value="1" <c:if test="${house[0].shi==1 }">selected="selected"</c:if>>1</option>
					<option value="2" <c:if test="${house[0].shi==2 }">selected="selected"</c:if>>2</option>
					<option value="3" <c:if test="${house[0].shi==3 }">selected="selected"</c:if>>3</option>
					<option value="4" <c:if test="${house[0].shi==4 }">selected="selected"</c:if>>4</option>
					<option value="5" <c:if test="${house[0].shi==5 }">selected="selected"</c:if>>5</option>
					<option value="6" <c:if test="${house[0].shi==6 }">selected="selected"</c:if>>6</option>
					<option value="7" <c:if test="${house[0].shi==7 }">selected="selected"</c:if>>7</option>
					<option value="8" <c:if test="${house[0].shi==8 }">selected="selected"</c:if>>8</option>
					<option value="9" <c:if test="${house[0].shi==9 }">selected="selected"</c:if>>9</option>
					<option value="10" <c:if test="${house[0].shi==10 }">selected="selected"</c:if>>10</option>
					<option value="11" <c:if test="${house[0].shi==11 }">selected="selected"</c:if>>11</option>
				</select>室
				<select  name="house.ting" id="ting" >
					<option value="1" <c:if test="${house[0].ting==1 }">selected="selected"</c:if>>1</option>
					<option value="2" <c:if test="${house[0].ting==2 }">selected="selected"</c:if>>2</option>
					<option value="3" <c:if test="${house[0].ting==3 }">selected="selected"</c:if>>3</option>
					<option value="4" <c:if test="${house[0].ting==4 }">selected="selected"</c:if>>4</option>
					<option value="5" <c:if test="${house[0].ting==5 }">selected="selected"</c:if>>5</option>
					<option value="6" <c:if test="${house[0].ting==6 }">selected="selected"</c:if>>6</option>
					<option value="7" <c:if test="${house[0].ting==7 }">selected="selected"</c:if>>7</option>
					<option value="8" <c:if test="${house[0].ting==8 }">selected="selected"</c:if>>8</option>
					<option value="9" <c:if test="${house[0].ting==9 }">selected="selected"</c:if>>9</option>
					<option value="10" <c:if test="${house[0].ting==10 }">selected="selected"</c:if>>10</option>
					<option value="11" <c:if test="${house[0].ting==11 }">selected="selected"</c:if>>11</option>
				</select>厅
				<select name="house.wei" id="wei" >
					<option value="1" <c:if test="${house[0].wei==1 }">selected="selected"</c:if>>1</option>
					<option value="2" <c:if test="${house[0].wei==2 }">selected="selected"</c:if>>2</option>
					<option value="3" <c:if test="${house[0].wei==3 }">selected="selected"</c:if>>3</option>
					<option value="4" <c:if test="${house[0].wei==4 }">selected="selected"</c:if>>4</option>
					<option value="5" <c:if test="${house[0].wei==5 }">selected="selected"</c:if>>5</option>
					<option value="6" <c:if test="${house[0].wei==6 }">selected="selected"</c:if>>6</option>
					<option value="7" <c:if test="${house[0].wei==7 }">selected="selected"</c:if>>7</option>
					<option value="8" <c:if test="${house[0].wei==8 }">selected="selected"</c:if>>8</option>
					<option value="9" <c:if test="${house[0].wei==9 }">selected="selected"</c:if>>9</option>
					<option value="10" <c:if test="${house[0].wei==10 }">selected="selected"</c:if>>10</option>
					<option value="11" <c:if test="${house[0].wei==11 }">selected="selected"</c:if>>11</option>
				</select>卫
				
				
				</td>				
			</tr>
			<tr align="right">	
				
				<th width="20%"><span style="color: red;">*</span>总价(万元/套)：</th>
				<td width="20%">				
				<input class="form_text"  name="house.price" id="price"  value="${house[0].price }" ></td>
				<th width="20%"><span style="color: red;">*</span>面积：</th>
				<td width="20%"><input class="form_text"  name="house.area" id="area"  value="${house[0].area }"></td>		
			</tr>
			<tr align="right">	
				
				<th width="20%"><span style="color: red;">*</span>户型：</th>
				<td width="20%">				
					<epmis:select  id="house.typeid"  define="select id,name from t_housetype where isdel='0'  " attr="style='width: 100%;'" value="${house[0].typeid }" ></epmis:select>
				</td>
							
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;">*</span>户型类别标签：</th>
				<td width="20%" colspan="3" id="ksksk" align="left">	
						
				</td>
			</tr>
			<tr >
			<th width="20%"><span style="color: red;">*</span>户型详情：</th>
			
				<td colspan="3">
					<textarea name="house.content" id="content"   class="label_textarea"  rows="10" >${house[0].content }</textarea> 					   	  
				</td>
			</tr>
			
        </table>   
<!--         <input class="form_text" type="hidden"  name="house.content" id="content"  value="${house[0].content }"> -->
</form>
</div>
	<div class="BottomDiv">
		<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> <a
			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>
	</div>
</body>
</html>
