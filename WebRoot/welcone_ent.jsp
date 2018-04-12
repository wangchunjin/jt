<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/jsLib.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/webResources/style/css/default/login.css" />
		
		<script type="text/javascript">
		 	$(document).ready(function(){
		 		var inf = "${sessionScope.result}";
		 		if(isStringNull(inf)){
		 			if(inf == "input"){
		 				$.messager.alert("错误","用户名或密码输入错误！");
		 			}
		 		}
		 		
				 getSavedUserName();
				if(window.navigator.userAgent.indexOf("Chrome")==-1 ){
					 var BrowserChecked = Cookies.get("BrowserChecked");
					 if(BrowserChecked !="true"){
						 $("#ly").css("display","");
				         $("#BrowserPage").css("display","");
				         
					 }
				 }
				});
		     function getSavedUserName(){
				var userName = Cookies.get("cmUsers.userName");
				var password = Cookies.get("cmUsers.password");
				if(userName!=null){
					$("#cmUsers\\.userName").val(userName);
					$("#cmUsers\\.password").val(password);
					$("#rem").attr("checked",true);
				}
			  }
			function addvalidateCode(){
				$(".login_frame_m").append( '<div class="sub_name">'+
												'<img src="${ctx}/webResources/style/images/default/login_icon_03.png" width="16" height="16" />验证码</div>'+
											'<div class="sub_input">'+
												'<input name="validateCode" id="validateCode" maxlength="4" onkeydown="suball()" />'+
												'<img src="${ctx}/captcha.action" />'+
											'</div>'
				);
			}
			function closeBrowser(){
				$("#ly").css("display","none");
				$("#BrowserPage").css("display","none");
				if(document.all.notice.checked){
					var expires = new Date();
					expires.setTime(expires.getTime() + 1000*60*60*24*30);
					Cookies.set("BrowserChecked",document.all.notice.checked,expires);
				}
			}
			function doLogin() {
			 	if(($("#cmUsers\\.userName").val()).indexOf("'")>=0){
			 		$.messager.alert("错误","用户名含有非法字符单引号");
			 		return;
			 	}
				if($(".login_frame_error").size()>0)
					$(".login_frame_error").removeClass("login_frame_error");
				ff = document.loginForm;
				if(ff.rem.checked){
					var expires = new Date();
					expires.setTime(expires.getTime() + 1000*60*60*24*30);
					Cookies.set("checked",ff.rem.checked,expires);
					Cookies.set("cmUsers.userName",$("#cmUsers\\.userName").val(),expires);
					Cookies.set("cmUsers.password",$("#cmUsers\\.password").val(),expires);
				}else{
					Cookies.clear("cmUsers.userName");
					Cookies.clear("cmUsers.password");
					Cookies.clear("checked");
				}
			  	ff.action="${ctx}/sys_ent/userLogin.action";
			   	ff.submit();
			}
		    function suball(){
				if(event.keyCode==13){
					 doLogin();
				}
			}
			  $(document).keydown(function(){
				  if(event.keyCode == 13)
					  doLogin();
				  });  
		</script>
	</head>
	<body>
		<div class="login_top">
			<div class="logo"><font  style="color: #2493ca;font-size:19pt;font-family: 微软雅黑,tahoma;font-weight:bold;   WIDTH: 100% " >${title_ent}</font>
				 <%--<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
					codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0"
					width="435" height="30">
					<param name="allowScriptAccess" value="sameDomain" />
					<param name="movie"
						value="<c:url value='/webResources/style/images/default/login_logo.swf'/>" />
					<param name="quality" value="high" />
					<param name="wmode" value="transparent" />
					<embed
						src="<c:url value='/webResources/style/images/default/login_logo.swf'/>"
						quality="high" bgcolor="#ffffff" width="435" height="30"
						name="mymovie" align="center" allowScriptAccess="sameDomain"
						type="application/x-shockwave-flash" wmode="transparent"
						pluginspage="http://www.macromedia.com/go/getflashplayer" />
				</object>
			 --%></div><%--
			<div class="login_nav">
				<a href="#">设为首页</a>
				<a href="#">加入收藏</a>
				<a href="#">客户端下载</a>
				<a href="#">更新日志</a>
			</div>
		--%></div>

		<div class="wallpaper">
	        <div style="position: absolute;top: 10%;right:8%;left: 8%;bottom: 15%;">
	        <img src="${pic_ent}" width="100%" height="100%">
	     
	        </div>
			<form method="post" action="" id="loginForm" name="loginForm">
				<input type="hidden" id="validateFlag" name="validateFlag" />
							<div style="width:40px;height:40px;  z-index:100; top:15%;right:10%;position: absolute;padding-top: 5px;-webkit-animation: login_load 1s forwards;cursor: pointer;  "  onmouseover="$('#downApp').show()"  onmouseout="$('#downApp').hide()">
			  <img alt="" src="${ctx }/webResources/style/images/default/down.png"  width="35" height="35">
			  <div id="downApp" style=" background-color:#f4f4f4; z-index:100;width: 206px;height: 125px;border: 0px solid red;position: relative;top: -165px;left: -174px;display: none;" >
			      <table>
				    <tr>
				        <td><img alt="" src="${ctx }/webResources/style/images/default/Android.png"  width="100" height="100"></td>
				        <td><img alt="" src="${ctx }/webResources/style/images/default/Ios.png"  width="100" height="100"></td>
			        </tr>
			        <tr>
				        <td style="text-align: center;"  >安卓客户端</td>
				        <td  style="text-align: center;" >苹果客户端</td>
			        </tr>			      
			      </table>
			  </div>
			
			</div>
	
				<div class="login_frame" style="z-index: 1;">
					<div class="login_frame_t"></div>
					<div class="login_frame_m">
						<div class="sub_name">
							<img src="${ctx}/webResources/style/images/default/login_icon_01.png" width="16" height="16" />用户名
							<%--<a href="${ctx}/website/user/register.jsp" tabIndex=-1>注册</a>
						--%></div>
						<div class="sub_input">
							<input name="cmUsers.userName" id="cmUsers.userName" />
						</div>
						<div class="sub_name">
							<img src="${ctx}/webResources/style/images/default/login_icon_02.png" width="16" height="16" />密码
							<%--<a href="#" tabIndex=-1>忘记密码?</a>
						--%></div>
						<div class="sub_input">
							<input name="cmUsers.password" id="cmUsers.password" type="password" onkeydown="suball()" />
						</div>
						<div class="login_tips" id="loginMessage">
							${LOGIN_MESSAGE}
						</div>
					</div>
					<div class="login_frame_b">
						<div class="savemyacc">
							<input name="rem" id="rem" type="checkbox" />记住密码
						</div>
						<a href="#" class="login_btn" onclick="doLogin()"></a>

					</div>
				</div>
			</form>
		</div>
			<div id="ly" style="position: absolute; top: 0px; opacity:0.8; background-color: #777;      
		        z-index: 2; left: 0px;WIDTH: 100%;HEIGHT:100%;display:none ;">
		    </div>     
		    <!--         浮层框架开始         -->     
        <div id="BrowserPage" style=" z-index: 3;border: 1px solid #000;width:400px;height:70px;position: absolute;top:150px;left: 400px;background-color: white;-webkit-animation:login_load 0.6s forwards;display: none;" >
             <div style="font-size:11pt;margin-top: 10px; padding-left: 40px;">
                                                        推荐您使用谷歌chrome浏览器，请<a style="font-size:11pt; color: blue;" href="http://www.gpmis.com:12345/plugin/chrome.rar">点击下载</a>。
             </div>                     
               <div style="font-size:11pt;width: 100px;float: left;margin-top: 13px;padding-left: 40px;">
                      <a style="font-size:11pt; color: blue;" href="#" onclick="closeBrowser()">忽略</a>
               </div>
               <div style="font-size:11pt;width: 170px;float: right;margin-top: 13px;">
                 <input type="checkbox" id="notice" name="notice">下次不再提醒    
               </div>
        
        </div>
		<%--<div class="footer">
			<div class="footer_left">
				<span>苏XXXXX</span><span>http://www.gpmis.com</span>
			</div>
			<div class="footer_right">
				<span>南京建晓信息科技有限公司版权所有</span><span>CopyRights©2013</span>
				<span><a href="#">DEMO下载</a></span>
			</div>
		</div
	--%></body>
</html>