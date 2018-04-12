<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/jsLib.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/webResources/style/css/default/login.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/webResources/style/css/default/mui.min.css" />
	    <script src="${ctx}/webResources/style/js/mui.min.js"></script>
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
			  	ff.action="${ctx}/userLogin.action";
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
			<div class="logo"><font  style="color: darkslateblue;font-size:19pt;font-family: 微软雅黑,tahoma;WIDTH: 100%; " >我享借后台管理平台</font>
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
	        <%-- <img src="${pic}" width="100%" height="100%"> --%>
	         <div id="slider" class="mui-slider" >
		        <div class="mui-slider-group mui-slider-loop">
		            <!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
<!-- 		            <div class="mui-slider-item mui-slider-item-duplicate"> -->
<!-- 		                <a href="#"> -->
<!-- 		                    <img src="${ctx }/KM/LOGIN/13.png"> -->
<!-- 		                </a> -->
<!-- 		            </div> -->
		           <div class="mui-slider-item">
		                <a href="#">
		                    <img src="${ctx }/KM/LOGIN/11.png">
		                </a>
<!-- 		            </div><div class="mui-slider-item"> -->
<!-- 		                <a href="#"> -->
<!-- 		                    <img src="${ctx }/KM/LOGIN/12.png"> -->
<!-- 		                </a> -->
<!-- 		            </div> -->
<!-- 		            <div class="mui-slider-item"> -->
<!-- 		                <a href="#"> -->
<!-- 		                    <img src="${ctx }/KM/LOGIN/13.png"> -->
<!-- 		                </a> -->
<!-- 		            </div> -->
		            <!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
<!-- 		            <div class="mui-slider-item mui-slider-item-duplicate"> -->
<!-- 		                <a href="#"> -->
<!-- 		                    <img src="${ctx }/KM/LOGIN/11.png"> -->
<!-- 		                </a> -->
<!-- 		            </div> -->	
		        </div>
<!-- 		        <div class="mui-slider-indicator"> -->
<!-- 	            <div class="mui-indicator mui-active"></div> -->
<!-- 	            <div class="mui-indicator"></div> -->
<!-- 	             <div class="mui-indicator"></div> -->
            </div>
            </div>
	        </div>
			<form method="post" action="" id="loginForm" name="loginForm">
				<input type="hidden" id="validateFlag" name="validateFlag" />
	        <script>
		        mui.init({
		            swipeBack: false
		        });
		        mui('#slider').slider({
		            interval:5000
		        });
	        </script>
				<div class="login_frame" style="z-index: 1;background-color:transparent">
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
							<input name="cmUsers.password" style="height:33px;" id="cmUsers.password" type="password" onkeydown="suball()" />
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
		</div>
	--%>
	</body>
</html>