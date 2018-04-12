<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
    
 <script type="text/javascript">
	     function UpdateRecord(){
	        var currFrameId = "${param.currFrameId}";
	        var res = $('#addForm').SubmitForm("${ctx}/naireoption/tnaireoption/update.action");
	        
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('eidtWindows');
			}
 			else{
 				parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	       
	     }
	     
	     $(function(){
		  		var nid='${naireoption[0].nid}';
				var result = GetXmlBySql("SELECT shen,shi FROM T_naire WHERE id='"+nid+"'");
				SetSelect("SELECT id,question FROM T_naire WHERE isdel='0' and shen='"+result[0].shen+"' and shi='"+result[0].shi+"'","naireoption\\.snid","${naireoption[0].snid}",new Array("=--请选择--")); 
		    	 
		  	});
	     

 </script>
<style type="text/css">
#preview {
	width: 100px;height: 130px;overflow: hidden;
}
#imghead {
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
}
</style>
  <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
   <div style="height:300px;overflow: auto;" >
		<table style="margin-top: 10px;width: 84%;" >
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>选项：</th>
				<td colspan="3" width="30%">
				<input type="hidden"  id="id" name="naireoption.id" value="${naireoption[0].id }"/>
				<input type="hidden"  id="nid" name="naireoption.nid" value="${naireoption[0].nid }"/>
				<input  id="xx" class="form_text" name="naireoption.xx" value="${naireoption[0].xx }"/>
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>内容：</th>
				<td colspan="3" width="30%">
				<input  id="options" class="form_text" name="naireoption.options" value="${naireoption[0].options }"/>
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>下个问题：</th>
				<td colspan="3" width="30%">
					<epmis:select id="naireoption.snid" define="" attr="style='width: 100%;'" onChange="changeValue1()" ></epmis:select>
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>理由：</th>
				<td colspan="3" width="30%">
				<textarea rows="3" cols="" name="naireoption.ly" id="ly">${naireoption[0].ly }</textarea>
				
				</td>
			</tr>
       </table>
       </div>
</form>

		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindows')">关闭<b></b></a>				
		</div>
  </body>
</html>
