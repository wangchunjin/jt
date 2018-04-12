<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
    
 <script type="text/javascript">
	     function UpdateRecord(){
	        
	        
// 	        var res = $('#addForm').SubmitForm("${ctx}/clientinfo/tclientinfo/update.action");
	        
// 	 	  	if(res[0].result=="success"){
// 	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
// 	 		parent.close_win('eidtWindow');
// 			}
//  			else{
//  				parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
//  		 	}
	       
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
  <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
   <div style="height:400px;overflow: auto;" >
		<table style="margin-top: 10px;width: 96%;" >
			<tr>
				<th  align="right">订单编号：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].id }">				
				</td>
				<th  align="right">借款用户：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].client_id }">			
				</td>
				<th align="right">出借人：</th>
				<td>
				<input class="form_text" readonly="readonly" value="${orderinfo[0].lend_id}">
				
				
				</td>
				
			</tr>
			<tr>
			<th  align="right">借款申请时间：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].borrow_time}">				
				</td>
				<th  align="right">借款金额(元)：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].amount}">				
				</td>
				<th align="right">借款周期(天)：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].period}">
				
				
				</td>
			</tr>
			
			
			<tr>
				<th align="right">利息(元)：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].interest}">				
				</td>
				<th align="right">滞纳金(元)：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].overdue_payment_the}">			
				</td>
				<th  align="right">实际滞纳金(元)：</th>
				<td>
				<input class="form_text" readonly="readonly" value="${orderinfo[0].overdue_payment_rel}">
				
				
				</td>
			</tr>
			
			<tr>
				<th  align="right">借款用途：</th>
				<td>
				<input class="form_text" readonly="readonly" value="${orderinfo[0].pur_id}">				
				</td>
				<th  align="right">实际到账金额(元)：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].real_amt}">				
				</td>
				<th  align="right">预收服务费(元)：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].commision}">				
				</td>
				
			</tr>			
			<tr>
				<th align="right">订单审核：</th>
				<td>
				<input class="form_text" readonly="readonly" value="<c:if test="${orderinfo[0].approve_status==0}">待审核</c:if><c:if test="${orderinfo[0].approve_status==1}">通过</c:if><c:if test="${orderinfo[0].approve_status==2}">失败</c:if>">	
				
				</td>
				<c:if test="${orderinfo[0].approve_status==1}">
					<th  align="right">打款状态：</th>
					<td >
					<input class="form_text" readonly="readonly" value="<c:if test="${orderinfo[0].transfer_status==0}">待打款</c:if><c:if test="${orderinfo[0].transfer_status==1}">还款中</c:if><c:if test="${orderinfo[0].transfer_status==2}">已还款</c:if><c:if test="${orderinfo[0].transfer_status==3}">逾期</c:if><c:if test="${orderinfo[0].transfer_status==4}">逾期还款</c:if><c:if test="${orderinfo[0].transfer_status==5}">异常订单</c:if><c:if test="${orderinfo[0].transfer_status==6}">未确认</c:if>">
					
					</td>	
				</c:if>	
					<th  align="right">审核时间：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].approve_time}">
				
				
				
				</td>			
			</tr>
			<tr>
			
				<th  align="right">逾期时间：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].overdue_time}">				
				</td>
				<th align="right">放款时间：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].transfer_time}">				
				</td>
				<th  align="right">应放款时间：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].repay_time}">		
				</td>			
			</tr>
			
			<tr>
				<th  align="right">应还款金额(元)：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].repay_money}">			
				</td>
				<th align="right">实际还款日期：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].rel_time}">				
				</td>	
				<th align="right">实际还款金额(元)：</th>
				<td>
				<input class="form_text" readonly="readonly" value="${orderinfo[0].rel_money}">				
				</td>			
			</tr>
			<tr>
				
				<th  align="right">上次借款最大额度(元)：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].last_loan_amount}">				
				</td>	
				<th  align="right">打款渠道：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].fun_change}">				
				</td>
				<th  align="right">通讯录人次：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${orderinfo[0].maile_num}">					
				</td>
			</tr>
			
			
       </table>
       </div>
</form>

		<div class="BottomDiv">
<!-- 			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> -->
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
