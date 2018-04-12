<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
 <script type="text/javascript">
	    
 </script>
 
 </head>
  <body class="NewWinClass">
  <div style="height:330px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >		
			<tr>
				<th width="20%"><span style="color: red;"></span>头像：</th>
				<td width="30%">				
				<div class="lf salebd">
					<c:if test="${user[0].type==1 }">
						<a href="#"  id="preview"><img id="imghead"  src="http://120.27.215.48:8082/duifang/${user[0].ico }" width="100" height="80" /></a>
					</c:if>
					<c:if test="${user[0].type!=1 }">
						<a href="#"  id="preview"><img id="imghead"  src="http://120.27.215.48:8081/df/${user[0].ico }" width="100" height="80" /></a>
					</c:if>
					
				
				
				</div>
				</td>
			</tr>
			<tr style="display: none;">
				<th width="20%"><span style="color: red;"></span>qq头像：</th>
				<td width="30%">				
				<div class="lf salebd">
					<c:if test="${user[0].type==1 }">
						<a href="#"  id="preview"><img id="imghead"  src="http://120.27.215.48:8082/duifang/${user[0].qqico }" width="100" height="80" /></a>
					</c:if>
					
					<c:if test="${user[0].type!=1 }">
						<a href="#"  id="preview"><img id="imghead"  src="http://120.27.215.48:8081/df/${user[0].qqico }" width="100" height="80" /></a>
					</c:if>
				</div>
				</td>
				
			</tr> 
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>微信头像：</th> -->
<!-- 				<td width="30%">				 -->
<!-- 				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="http://120.27.215.48:8081/df/${user[0].wxico }" width="100" height="80" /></a></div> -->
<!-- 				</td> -->
				
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>微博头像：</th> -->
<!-- 				<td width="30%">				 -->
<!-- 				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="http://120.27.215.48:8081/df/${user[0].sinaico }" width="100" height="80" /></a></div> -->
<!-- 				</td> -->
				
<!-- 			</tr>  -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>支付宝头像：</th> -->
<!-- 				<td width="30%">				 -->
<!-- 				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="http://120.27.215.48:8081/df/${user[0].alipayico }" width="100" height="80" /></a></div> -->
<!-- 				</td> -->
				
<!-- 			</tr>   -->
			<tr>
				<th width="20%"><span style="color: red;"></span>用户昵称：</th>
				<td width="20%">
				
				${user[0].nickname }
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;"></span>qq昵称：</th>
				<td width="20%">
				
				${user[0].qqnickname }
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;"></span>微信昵称：</th>
				<td width="20%">
				
				${user[0].wxnickname }
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>微博昵称：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].sinanickname } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>支付宝昵称：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].alipaynickname } -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<th width="20%"><span style="color: red;"></span>手机号：</th>
				<td width="20%">
				
				${user[0].telephone }
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>密码：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].password } -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<th width="20%"><span style="color: red;"></span>性别：</th>
				<td width="20%">
				
				${user[0].sex }
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>qq性别：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].qqsex } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>微信性别：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].wxsex } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>微博性别：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].sinasex } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>支付宝性别：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].alipaysex } -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<th width="20%"><span style="color: red;"></span>生日：</th>
				<td width="20%">
				
				${user[0].brithdate	 }
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>qq生日：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].qqbrithdate	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>微信生日：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].wxbrithdate	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>微博生日：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].sianbrithdate	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>支付宝生日：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].alipaybrithdate	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>微信OpenId：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].wxOpenId	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>qqOpenId：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].qqOpenId	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>支付宝OpenId：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].alipayOpenId	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>微博OpenId：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].sinaOpenId	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>环信账号：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].huanxinAccount	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>环信密码：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].huanxinPassword	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>邀请码：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].invitationCode	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<th width="20%"><span style="color: red;"></span>身份证  正面照：</th>
				<td width="30%">				
				<div class="lf salebd"><a href="#"  id="preview">
				<c:choose>
				   <c:when test="${user[0].IdPiczheng=='' }">  
				      无 
				   </c:when>
				   <c:otherwise> 
				    <c:if test="${user[0].IdPiczheng.substring(0,7)=='/upload' }">
						<img id="imghead"  src="${ctx }/${user[0].IdPiczheng }" width="NAN" height="200" />		
					</c:if>
					<c:if test="${user[0].IdPiczheng.substring(0,5)=='/file' }">
						<img id="imghead"  src="http://120.27.215.48:8082/duifang/${user[0].IdPiczheng }" width="NAN" height="200" />	
					</c:if>
					<c:if test="${user[0].IdPiczheng.substring(0,4)=='http' }">
						<img id="imghead"  src="${user[0].IdPiczheng }" width="NAN" height="200" />	
					</c:if>
				   </c:otherwise>
				</c:choose>
				
				
				
					
				
				</a></div>
				</td>
				
			</tr> 
			<tr>
				<th width="20%"><span style="color: red;"></span>身份证  反面照：</th>
				<td width="30%">				
				<div class="lf salebd"><a href="#"  id="preview">
				<c:choose>
				   <c:when test="${user[0].IdPicfan=='' }">  
				      无 
				   </c:when>
				   <c:otherwise> 
				    <c:if test="${user[0].IdPicfan.substring(0,7)=='/upload' }">
				<img id="imghead"  src="${ctx }/${user[0].IdPicfan }" width="NAN" height="200" />
				</c:if>
					<c:if test="${user[0].IdPicfan.substring(0,5)=='/file' }">
				<img id="imghead"  src="http://120.27.215.48:8082/duifang/${user[0].IdPicfan }" width="NAN" height="200" />
				</c:if>
				<c:if test="${user[0].IdPicfan.substring(0,4)=='http' }">
				<img id="imghead"  src="${user[0].IdPicfan }" width="NAN" height="200" />
				</c:if>
				   </c:otherwise>
				</c:choose>
				
				
			
				
				</a></div>
				</td>
				
			</tr> 
			<tr>
				<th width="20%"><span style="color: red;"></span>手持身份证照：</th>
				<td width="30%">				
				<div class="lf salebd"><a href="#"  id="preview">
				<c:choose>
				   <c:when test="${user[0].shouchiId=='' }">  
				      无 
				   </c:when>
				   <c:otherwise> 
				  <c:if test="${user[0].shouchiId.substring(0,7)=='/upload' }">
				<img id="imghead"  src="${ctx }/${user[0].shouchiId }" width="NAN" height="200" />
				</c:if>
					<c:if test="${user[0].shouchiId.substring(0,5)=='/file' }">
				<img id="imghead"  src="http://120.27.215.48:8082/duifang/${user[0].shouchiId }" width="NAN" height="200" />
				</c:if>
				<c:if test="${user[0].shouchiId.substring(0,4)=='http' }">
				<img id="imghead"  src="${user[0].shouchiId }" width="NAN" height="200" />
				</c:if>
				   </c:otherwise>
				</c:choose>
				
				</a></div>
				</td>
				
			</tr> 
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>身份证认证：</th> -->
<!-- 				<td width="20%"> -->
<!-- 				<c:if test="${user[0].IdAuthentified==0 }">未认证</c:if> -->
<!-- 				<c:if test="${user[0].IdAuthentified==1 }">已认证</c:if> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>姓名认证：</th> -->
<!-- 				<td width="20%"> -->
<!-- 				<c:if test="${user[0].NaAuthentified==0 }">未认证</c:if> -->
<!-- 				<c:if test="${user[0].NaAuthentified==1 }">已认证</c:if> -->
<!-- 				</td> -->
<!-- 			</tr>	 -->
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>银行卡认证：</th> -->
<!-- 				<td width="20%"> -->
<!-- 				<c:if test="${user[0].BankAuthentified=='0' }">未认证</c:if> -->
<!-- 				<c:if test="${user[0].BankAuthentified=='1' }">已认证</c:if> -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<th width="20%"><span style="color: red;"></span>真实姓名：</th>
				<td width="20%">
				
				${user[0].realName	 }
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;"></span>身份证号：</th>
				<td width="20%">
				
				${user[0].IdNo	 }
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<th width="20%"><span style="color: red;"></span>银行卡号：</th> -->
<!-- 				<td width="20%"> -->
				
<!-- 				${user[0].bankcardNo	 } -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<th width="20%"><span style="color: red;"></span>用户注册时间：</th>
				<td width="20%">
				
				${user[0].createtime	 }
				</td>
			</tr>
			
			
       </table>
       
</form>
</div>
		<div class="BottomDiv" style="height: 10%;">
			
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
