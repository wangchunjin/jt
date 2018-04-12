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
	        
// 	        var aa=GetIndexFrame("CuishouWindow_frm").LeftFrame.TwoFrame.location;

// 	        aa="http://localhost:8080/jietiao/web/JT/CONNECT/RECORD/content.jsp?oid=5";

// 	        return;
			 
			 var rep_willingness=$("#crecord\\.rep_willingness").val();
			 if(rep_willingness==null || trim(rep_willingness)==""){
				 $.messager.alert("错误","请选择还款意愿!");
				 return;
			 }
			 var comm_results=$("#crecord\\.comm_results").val();
			 if(comm_results==null || trim(comm_results)==""){
				 $.messager.alert("错误","请选择沟通结果!");
				 return;
			 }
			 var contact_book_type=$("#contact_book_type").val();
			 if(contact_book_type==null || trim(contact_book_type)==""){
				 $.messager.alert("错误","请选择通讯录联系结果!");
				 return;
			 }

// 			 var contact_book_type=$('input[name="crecord\\.contact_book_type"]:checked').val();
// 	    	 alert(contact_book_type);
			
			
	        var res = $('#addForm').SubmitForm("${ctx}/crecord/tcrecord/save.action");
	       
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame("CuishouWindow_frm").LeftFrame.TwoFrame.location.reload();
	 		
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
	     };
	     
// 	     function gai(){

// 	    	 var contact_book_type=$('input[name="crecord\\.contact_book_type"]:checked').val();
// 	    	 if(contact_book_type==0){
// 	    		 $(".aaa").show();
	    		 
// 	    	 }else{
// 	    		 $(".aaa").hide();
	    		 
	    		 
// 	    	 }
// 	     }
	     
	    
 </script>
<style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
    <div style="height:400px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
		<input class="form_text" type="hidden" name="crecord.cuid" id="cuid"  value="${sessionScope.UserInfo.userId}">
		<input class="form_text" type="hidden" name="crecord.order_id" id="order_id"  value="${param.oid}">
			<tr   >
				<th width="20%" align="right"><span style="color: red;">*</span>还款意愿：</th>
				<td width="30%" align="left">
				<epmis:select id="crecord.rep_willingness" define="select id,name from rep_willingness where isdel='0' " attr="style='width: 100%;'" onChange="" ></epmis:select>
				</td>
			</tr>
			
			<tr>
				<th width="20%" align="right"><span style="color: red;">*</span>沟通结果：</th>
				<td width="20%" align="left">			
					<epmis:select id="crecord.comm_results" define="select id,name from comm_results where isdel='0' " attr="style='width: 100%;'" onChange="" ></epmis:select>
				</td>
			</tr>
			<tr>
				<th width="20%" align="right"><span style="color: red;">*</span>通讯录联系结果：</th>
				<td width="20%" align="left">			
					<select class="form_text" name="crecord.contact_book_type" id="contact_book_type">
						<option value="">--请选择--</option>
						<option value="1">没有联系通讯录</option>
						<option value="2">只联系紧急联系人</option>
						<option value="3">只联系其它联系人</option>
						<option value="4">紧急联系人和其它联系人都联系</option>
					</select>
				</td>
			</tr>
			<tr style="display: none;">
				<th width="20%" align="right"><span style="color: red;">*</span>是否联系紧急人：</th>
				<td width="20%" align="left">			
					<s:radio id="emergency_contact" name="crecord.emergency_contact"  list="#{'0':'是','1':'否'}"   value="1" theme="simple" onchange="gai();"  ></s:radio>
				</td>
			</tr>
			<tr style="display: none;">
				<th width="20%" align="right"><span style="color: red;">*</span>是否联系其它联系人：</th>
				<td width="20%" align="left">			
					<s:radio id="contact_other_contacts" name="crecord.contact_other_contacts"  list="#{'0':'是','1':'否'}"   value="1" theme="simple" onchange="gai();"  ></s:radio>
				</td>
			</tr>
			<tr style="display: none;">
				<th width="20%" align="right"><span style="color: red;">*</span>通讯录联系情况：</th>
				<td width="20%">
					<textarea name="crecord.cus_beh" id="cus_beh" rows="5" cols=""></textarea>
				</td>
			</tr>
			
			
			
			<tr style="display: none;">
				<th width="20%" align="right"><span style="color: red;">*</span>客户行为：</th>
				<td width="20%">
					<textarea name="crecord.cus_beh" id="cus_beh" rows="5" cols=""></textarea>
				</td>
			</tr>
			<tr>
				<th width="20%" align="right"><span style="color: red;">*</span>备注：</th>
				<td width="20%">
					<textarea name="crecord.content" id="content" rows="5" cols=""></textarea>
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
