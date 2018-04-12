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
   <div style="height:550px;overflow: auto;" >
   <p style="color:gray;font-size: 20px;">基本信息</p>
		<table style="margin-top: 10px;width: 96%;" >
			<tr>
				<th  align="right">姓名：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].name }">
				
				</td>
				<th  align="right">性别：</th>
				<td  >
				<input class="form_text" readonly="readonly"  value="<c:if test="${clientinfo[0].gender==0}">女</c:if><c:if test="${clientinfo[0].gender==1}">男</c:if>">
				
				
				
				</td>
				<th  align="right">手机号：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].mobile}">
				
				</td>
				<th  align="right">身份证号：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].idcard}">
				
				</td>
			</tr>
			
			<tr>
				<th  align="right">银行预留电话：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].bank_phone}">
				
				</td>
				<th  align="right">出生日期：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].birth_date}">
				
				</td>
				<th  align="right">银行卡号：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].bank_card}">
				
				</td>
				<th  align="right">家庭地址：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].home_addr}">
				
				</td>
			</tr>
			
			<tr>
				<th align="right">公司名称：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].company_name}">
				
				</td>
				<th  align="right">公司地址：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].company_addr}">
				
				</td>
				<th align="right">qq号：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].qq}">
				
				</td>
				<th align="right">邮箱：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].email}">
				
				</td>				
			</tr>
			
			<tr>
				<th  align="right">职业：</th>
				<td >
				<input class="form_text" readonly="readonly" value="<c:if test="${clientinfo[0].occupation==0}">学生/待业</c:if><c:if test="${clientinfo[0].occupation==1}">餐饮/服务业</c:if><c:if test="${clientinfo[0].occupation==2}">快递物流行业</c:if><c:if test="${clientinfo[0].occupation==3}">生产制造业</c:if><c:if test="${clientinfo[0].occupation==4}">建筑业 </c:if><c:if test="${clientinfo[0].occupation==5}">农林渔牧业</c:if><c:if test="${clientinfo[0].occupation==6}">事业单位/公务员</c:if><c:if test="${clientinfo[0].occupation==7}">企业职员 </c:if><c:if test="${clientinfo[0].occupation==8}">个体户/私营企业主 </c:if><c:if test="${clientinfo[0].occupation==9}">其他</c:if>">
				
				</td>
				<th  align="right">月收入状态：</th>
				<td >
				<input class="form_text" readonly="readonly" value="<c:if test="${clientinfo[0].income==0}">0-2500元及以下 </c:if><c:if test="${clientinfo[0].income==1}">2500-5000元</c:if><c:if test="${clientinfo[0].income==2}">5000-7500元</c:if><c:if test="${clientinfo[0].income==3}">7500-10000元</c:if><c:if test="${clientinfo[0].income==4}">10000元及以上</c:if>">
				
				
				</td>	
				<th  align="right">车辆状况：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="<c:if test="${clientinfo[0].vechile_status==0}">无车</c:if><c:if test="${clientinfo[0].vechile_status==1}">有车</c:if>">
				
				
				</td>
				<th  align="right">教育状况：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="<c:if test="${clientinfo[0].education==0}">初中及以下</c:if><c:if test="${clientinfo[0].education==1}">高中/中专</c:if><c:if test="${clientinfo[0].education==2}">大专 </c:if><c:if test="${clientinfo[0].education==3}">本科 </c:if><c:if test="${clientinfo[0].education==4}">研究生及以上</c:if>">
				
		
				</td>							
			</tr>
			
			<tr>
				<th align="right">社保状况：</th>
				<td >
				<input class="form_text" readonly="readonly" value="<c:if test="${clientinfo[0].social_scurity==0}">未交社保 </c:if><c:if test="${clientinfo[0].social_scurity==1}">已交社保</c:if>">
				
				
				</td>
				<th  align="right">婚姻状况：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="<c:if test="${clientinfo[0].marital_status==0}">未婚</c:if><c:if test="${clientinfo[0].marital_status==1}">已婚未育</c:if><c:if test="${clientinfo[0].marital_status==2}">已婚已育 </c:if><c:if test="${clientinfo[0].marital_status==3}">离异</c:if>">
				
				
				</td>	
				<th  align="right">租房状况：</th>
				<td  >
				<input class="form_text" readonly="readonly" value="<c:if test="${clientinfo[0].accomodation==0}">租房/宿舍</c:if><c:if test="${clientinfo[0].accomodation==1}">自有住房无贷款</c:if><c:if test="${clientinfo[0].accomodation==2}">自有住房有贷款</c:if><c:if test="${clientinfo[0].accomodation==3}">与父母同住</c:if>">
				
				
				</td>			
			</tr>
			<tr>
			<th  align="left"><p style="color:gray;font-size: 15px;">第一联系人</p></th>
					
			</tr>
			<tr>
				<th  align="right">姓名：</th>
				<td>
				<input class="form_text" readonly="readonly" value="${clientinfo[0].contact1_name}">
				
				</td>
				<th align="right">关系：</th>
				<td >
				<input class="form_text" readonly="readonly" value="<c:if test="${clientinfo[0].contact1_relation==0}">配偶</c:if><c:if test="${clientinfo[0].contact1_relation==7}">母</c:if><c:if test="${clientinfo[0].contact1_relation==1}">父</c:if><c:if test="${clientinfo[0].contact1_relation==2}">兄弟姐妹</c:if><c:if test="${clientinfo[0].contact1_relation==3}">子女</c:if><c:if test="${clientinfo[0].contact1_relation==4}">同事</c:if><c:if test="${clientinfo[0].contact1_relation==5}">同学</c:if><c:if test="${clientinfo[0].contact1_relation==6}">朋友</c:if>">
						
				</td>
				<th  align="right">电话：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].contact1_mobile}">
				
				</td>
				<th  align="right">地址：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].contact1_addr}">
				
				</td>
			</tr>
			<tr>
			<th  align="left"><p style="color:gray;font-size: 15px;">第二联系人</p></th>
					
			</tr>
			<tr>
				<th align="right">姓名：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].contact2_name}">
				
				</td>
				<th  align="right">关系：</th>
				<td>
				<input class="form_text" readonly="readonly" value="<c:if test="${clientinfo[0].contact2_relation==0}">配偶</c:if><c:if test="${clientinfo[0].contact1_relation==7}">母</c:if><c:if test="${clientinfo[0].contact2_relation==1}">父</c:if><c:if test="${clientinfo[0].contact2_relation==2}">兄弟姐妹</c:if><c:if test="${clientinfo[0].contact2_relation==3}">子女</c:if><c:if test="${clientinfo[0].contact2_relation==4}">同事</c:if><c:if test="${clientinfo[0].contact2_relation==5}">同学</c:if><c:if test="${clientinfo[0].contact2_relation==6}">朋友</c:if>">
				
				
				</td>
				<th align="right">电话：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].contact2_mobile}">
				
				</td>
				<th  align="right">地址：</th>
				<td >
				<input class="form_text" readonly="readonly" value="${clientinfo[0].contact2_addr}">
				
				</td>
			</tr>
		</table>
		
   
</form>

<!-- 		<div class="BottomDiv"> -->
<!-- 			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> -->
<!-- 			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				 -->
<!-- 		</div> -->
<!-- <div > -->

<!-- 	<iframe name="TwoFrame" width="100%" height="30px" id="TwoFrame" noresize="noresize" src="${ctx}/web/JT/CONNECT/RECORD/top.jsp?id=${param.rid}" frameborder="0" scrolling="no"></iframe> -->
<!-- 	<iframe name="TwoFrame" width="100%" height="400px" id="TwoFrame" noresize="noresize" src="${ctx}/web/JT/CONNECT/RECORD/selCodeType.jsp?id=${param.rid}" frameborder="0" scrolling="no"></iframe> -->
<!-- </div> -->
</div>
  </body>
</html>
