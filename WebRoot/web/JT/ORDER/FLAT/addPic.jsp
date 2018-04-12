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
	        var order_id="${param.order_id}";
	        var cuid="${param.cuid}";
	        var ts="${param.o_transfer_status}";
	        var rt="${param.o_rel_time}";
	       
	       
	        var rel_time=$("#rel_time").val();
	        if(rel_time==null || trim(rel_time)==""){
	        	parent.parent.$.messager.alert("错误","还款时间不能为空！");
	        	return;
	        }
	        var transfer_status=$("#transfer_status").val();
	        if(transfer_status==null || trim(transfer_status)==""){
	        	$.messager.alert("错误","请选择还款类别！");
	        	return;
	        }
	       
	        var res = $('#addForm').SubmitForm("${ctx}/orderinfo/torderinfo/flat_3.action?order_id="+order_id+"&cuid="+cuid+"&rel_time="+rel_time+"&transfer_status="+transfer_status+"&o_transfer_status="+ts+"&o_rel_time="+rt);
	 	  	if(res[0].result=="success"){
	 	  		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 	  		parent.close_win('addWindow');
	 		
			}
 			else{ 				
 				parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	       
	     }
	     
	    
	
		
	  	
      
	
	    
 </script>
<style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
   <div style="height:300px;overflow: auto;" >
   	<table style="margin-top: 50px;width: 84%;" >
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>还款时间：</th>
				<td colspan="3" width="30%">
				<input class="form_text" onchange="findbycoi()" id="rel_time" type="text" readonly="readonly" onclick="WdatePicker({el:'rel_time'})" />
       		
				</td>
			</tr>
			
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>还款类别：</th>
				<td colspan="3" width="30%">
					<select class="form_text" name="transfer_status" id="transfer_status">
						<option value="">--请选择--</option>
						<option value="2">正常还款</option>
						<option value="4">逾期还款</option>
					</select>
				
				</td>
			</tr>
		
		
       </table>	
       </div>
		
		
		
</form>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
