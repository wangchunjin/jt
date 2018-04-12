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
	        
	      
			var sheng=$("#car\\.bid").val();
			if(sheng==null || trim(sheng)==""){
				$.messager.alert("错误","请选择省份！");
				return;
			}
			
			var shi=$("#region\\.around").val();
			if(shi==null || trim(shi)==""){
				$.messager.alert("错误","请选择城市！");
				return;
			}
			
	        var res = $('#addForm').SubmitForm("${ctx}/sys/sys_region/updatezb.action");
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).location.reload();
	 		parent.close_win('eidtWindow');
			}
 			else{
 				parent.$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
	     }
	     function changeValue(){
		     	var obj=$("#car\\.bid").val();
		     	
		     		SetSelect("SELECT wid,area_name FROM cm_region WHERE area_type='2' and parent_id='"+obj+"'","region\\.around","",new Array("=--请选择--")); 
		     		 
		     		
			}
	     $(function(){			
	    	 //获取所属周边城市的上级省份的wid=下级城市的parent_id
			var result = GetXmlBySql("SELECT parent_id FROM cm_region WHERE wid="+${region[0].around});	    	 
			    SetSelect("SELECT wid,area_name FROM cm_region where area_type='2' and parent_id="+result[0].parent_id,"region\\.around",value="${region[0].around }","",new Array("=--请选择--")); 
			    SetSelect("SELECT wid,area_name FROM cm_region where area_type='1' ","car\\.bid",value=""+result[0].parent_id+"","",new Array("=--请选择--")); 
			   
		 });
	     
		  
 </script>
 <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
  <div style="height:330px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 50px;width: 96%;" >
			<tr>
				<th width="20%"><span style="color: red;">*</span>省份：</th>
				<td width="20%" align="left">
				<input type="hidden" name="region.wid" id="wid" value="${region[0].WID }"/>
				<epmis:select id="car.bid" define="select wid,area_name from cm_region where area_type='1'" attr="style='width: 100%;'"  onChange="changeValue()"></epmis:select>
				</td>
				<th width="20%"><span style="color: red;">*</span>城市：</th>
				<td width="20%" align="left">
					<epmis:select id="region.around" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select>
				</td>
			</tr>
			
       </table>
<!--       <input class="form_text" type="hidden"  name="supp.content" id="content"  value="${supp[0].content }">	 -->
</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
