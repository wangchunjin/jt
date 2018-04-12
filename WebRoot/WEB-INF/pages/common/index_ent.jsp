<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/jsLib.jsp"%>
		<title>GPMIS监理项目管理信息系统</title>
		<script>
			function startTime() {
			    var today=new Date();
				var years=today.getFullYear();
				var months=today.getMonth();
				var d=today.getDate();
				var h=today.getHours();
				var m=today.getMinutes();
				var s=today.getSeconds();
				// add a zero in front of numbers<10
				months=months+1;
				months=checkTime(months);
				d=checkTime(d);
				m=checkTime(m);
				s=checkTime(s);
				var weekday=new Array(7);
				weekday[0]="星期日";
				weekday[1]="星期一";
				weekday[2]="星期二";
				weekday[3]="星期三";
				weekday[4]="星期四";
				weekday[5]="星期五";
				weekday[6]="星期六";
				var w=weekday[today.getDay()];
				document.getElementById('ShowTime').innerHTML=years+"年"+months+"月"+d+"日 "+"&nbsp;&nbsp;"+w+"&nbsp;&nbsp;"+h+":"+m;
				t=setTimeout('startTime()',500);
			}
			function checkTime(i) {
			    if (i < 10) {
			        i = "0" + i;
			    }
			    return i;
			}
			function logout() {
		    	if(confirm("确认注销吗？"))
		    	{
		    		parent.parent.location='<c:url value="/sys_ent/logout.action"/>';
		    	}
		    }
			$(document).ready(function() {
				$('#rollBtn').click(hideLeftMenu);
				$('#headBtn').click(hideHeadMenu);
				$('#tabdiv').tabs({
					width:$('.pageFrame').width(),
					height:$('.pageFrame').height(),
					tools:[{
						iconCls:'icon-clean',
						handler: function(){
							var selectedtab = $('#tabdiv').tabs('getSelected');
							var tabs = $('#tabdiv').tabs('tabs');
							$(tabs).each(function (i,dom){
								if($(dom).is(":hidden")&&$(dom).panel('options').title!='首页'){
									var index = $('#tabdiv').tabs('getTabIndex',dom);
									$('#tabdiv').tabs('close',index);
								}
							 });
						}
					}],
					onSelect:function(title){
						close_win();
						checkProj(title);
					}
				});
				load_default();
			    $(".sub_01").bind("click",
					     function(event) {
					if(!$(event.target).parents(".sub_00").hasClass("opened")){
						$(".opened").removeClass("opened");
					}
					
					$(event.target).parents(".sub_00").toggleClass("opened");
				});
			
				indexbody=$('body');
				$("body").mousedown(function(e){
					if($(e.target).hasClass("panel window")||$(e.target).hasClass("panel-title"))
						addWaterfall();
				});
				$("body").mouseup(function(){
					removeWaterfall();
				});
				function addWaterfall(){
					$("<div id='waterfall'></div>").appendTo(indexbody);
					$("#waterfall").css( {   
						"height" : "100%",   
						"width" : "100%",   
						"filter" : "alpha(opacity = 0)",   
						"-moz-opacity" : "0.1",   
						"opacity" : "0",   
						"background-color" : "#fff",   
						"position" : "absolute",   
						"left" : "0px",   
						"top" : "0px",
						"z-index":8000
					});
				}
				function removeWaterfall(){
					$("#waterfall").remove();
				}
				$(".page_left_bottom .rolldown").click(function(){
					var top_offset = $(".nav").offset().top;
					var top_offset2 = $(".nav li.sub_00:last").offset().top;
					var top_offset3 = $(".page_left_bottom").offset().top;
					if(top_offset2+45>top_offset3)
					$(".nav").offset({top:top_offset-45});
				});
				$(".page_left_bottom .rollup").click(function(){
					var top_offset = $(".nav").offset().top;
					if(top_offset<64)
					$(".nav").offset({top:top_offset+45});
				});
				$(".sub_02 .rolldown").click(function(){
					var top_offset = $(this).parent().prev().offset().top;
					var top_offset2 =$(this).parent().prev().find(".sub_03:last").offset().top;
					var top_offset3 = $(this).parent().offset().top;
					if(top_offset2+45>top_offset3)
					$(this).parent().prev().offset({top:top_offset-45});
				});
				$(".sub_02 .rollup").click(function(){
					var top_offset = $(this).parent().prev().offset().top;
					if(top_offset<64)
					$(this).parent().prev().offset({top:top_offset+45});
				});
				
			 });
			function checkProj(title){
				 var OldProjId = $("iframe[existname='"+title+"']").attr("projId");
				 var CurrProjId =  $("#CurrProjId").val();
				 if(OldProjId!=CurrProjId && title!='企业项目'){
					 $("iframe[existname='"+title+"']").attr("src", $("iframe[existname='"+title+"']").attr("src"));
					 $("iframe[existname='"+title+"']").attr("projId",CurrProjId);
				 }
			}
			function getCurrTab(){
				var tab = $('#tabdiv').tabs('getSelected');
				return tab;
			}
			function tabdivResize(){	
			 	var width=$('.pageFrame').width();
				var height=$('.pageFrame').height();
				var pp = $('#tabdiv').tabs('getSelected');
				var tabtitle = pp.panel('options').title;
				$('#tabdiv').tabs({
					width:width,
					height:height});
				$('#tabdiv').tabs("select",tabtitle);
			 }
			function load_default(){
			//	var url = "web/report.jsp";
		    //	var url = "web/SYS/PROJ/MAP/AllInfo.jsp";
		    	var url = "web/SYS/FIRST_ENT/index.jsp";
			     addTab("tab_index","首页",url);
			}
			function addTab(id,text,url){
				$(".opened").removeClass("opened");
				$("#drawer").hide();
				//$(".nav li:first").unbind("mouseenter").unbind("mouseleave");
				
				if(url=="")return;
				var width = $(".pageFrame").width() -4;
				var height = $(".pageFrame").height()-34;
				var existname="";
				var existid="";
				$("div[class='panel'] iframe").each(function (i,dom){
			    	if(text==$(dom).attr("existname")){
			    		existname=$(dom).attr("existname");
				    	existid=$(dom).attr("id");
			    	}
				});
				url="${ctx}/"+url;
				if(url.indexOf("?")==-1)
					url += "?";
				else
					url += "&";
				var randomnumber=Math.floor(Math.random()*100000);
				if(existname==""){
					
					var parentModuleCode = "";
					var parentModuleName = "";
					var svt = window.event;
					
	                if(isNull(svt)){
	                var obj = svt.srcElement;
	                parentModuleCode = $(obj).attr("parentModuleCode");
	                parentModuleName = $(obj).attr("parentModuleName");
	               }
	                var projId =  $("#CurrProjId").val() ;
					$('#tabdiv').tabs('add',{
						id:'tab_'+id,  
						title:text,
						width:width,
						height:height,
						closable:true,
						tools:[{   
					        iconCls:'icon-mini-refresh',   
					        handler:function(){ 
					        	var tab = $('#tabdiv').tabs('getSelected');  // get selected panel
								tab.panel('refresh');
					        }   
					    }],
						content:'<iframe name="iframe_'+id+'" existname="'+text+'" projId="'+projId+'" id="iframe_'+id+'" width="100%" height="100%" MODULE_CODE="'+parentModuleCode+'" MODULE_NAME="'+parentModuleName+'" name="iframe_'+id+'" frameborder="0"  src="'+url+"random="+randomnumber+'"></iframe>'  
					});
		 	  	}else{
		 	  		$('#tabdiv').tabs('select',existname);
		     			//重载要访问的地址
		      		document.getElementById(existid).src = url + "random=" + randomnumber;
		  		}     
			}
			function close_win(windowId){
				if(!windowId || windowId=="" || windowId == "null"){  
					$("#win").attr("src", "<c:url value='/blank.html'/>");
					$("#newWindow").window("close", true);
				}else{
					$("#"+windowId+"_frm").attr("src", "<c:url value='/blank.html'/>");
					$("#"+windowId).window("close", true);
					$("#"+windowId).remove();
				}
			}
			function hideLeftMenu(){
				if($("#rollBtn").hasClass("roll_01")){
					$(".page_left").hide();
					$("#rollBtn").removeClass("roll_01");
					$("#rollBtn").addClass("roll_02");
					$("#page_right").removeClass("page_right");
					$("#drawer_content").removeClass("drawer_content");
					$("#page_right").addClass("page_right_shrinked");
					$("#drawer_content").addClass("drawer_content_shrinked");
				}else{
					$(".page_left").show();
					$("#rollBtn").addClass("roll_01");
					$("#rollBtn").removeClass("roll_02");
					$("#page_right").addClass("page_right");
					$("#drawer_content").addClass("drawer_content");
					
					$("#page_right").removeClass("page_right_shrinked");
					$("#drawer_content").removeClass("drawer_content_shrinked");
					
				}
				tabdivResize();
			}
			$(window).resize(function() {
				tabdivResize();
			});
			function hideHeadMenu(){
				var userId = '${UserInfo.getUserId()}';
				if($("#headBtn").hasClass("header_hover")){
					$("#headBtn").removeClass("header_hover");
				}else{
					$("#headBtn").addClass("header_hover");
		            var sql = "SELECT MODULE_CODE,MODULE_NAME,URLTO FROM ( SELECT MODULE_CODE, MODULE_NAME, URLTO,SEQ_NUM,(SELECT C.SEQ_NUM FROM CM_MODULE C WHERE C.MODULE_CODE = M.PARENT_MODULE_CODE ) PARENT_SEQ_NUM, (SELECT G.SEQ_NUM FROM CM_MODULE_ENT G WHERE G.MODULE_CODE = (SELECT C.PARENT_MODULE_CODE FROM CM_MODULE_ENT C WHERE C.MODULE_CODE = M.PARENT_MODULE_CODE)) GRADEPARENT_SEQ_NUM FROM CM_MODULE_ENT M WHERE MODULE_CODE  IN (SELECT MODULE_CODE FROM CM_USERLINK WHERE USER_ID = '"+userId+"') ORDER BY MODULE_NAME) TT ORDER BY GRADEPARENT_SEQ_NUM ASC,PARENT_SEQ_NUM ASC ,SEQ_NUM ASC";	 		           
					var res=GetXmlBySql(sql);
		             if(res.length>0){
		            	 var temp="";
		            	 for(var i=0;i< res.length;i++){
		            	 	 temp =temp + "<a href=\"javaScript:addTab('"+res[i].MODULE_CODE+"','"+res[i].MODULE_NAME+"','"+res[i].URLTO+"')\" title='"+res[i].MODULE_NAME+"' ><img src=\"<c:url value='/webResources/style/images/default/header_center_icon_04.png'/>\" width='48' height='48' />"+res[i].MODULE_NAME+"</a>";
		            	 }
		            	 $("#linkContent").html(temp);
		             }else{
		            	 $("#linkContent").html("");
		             }
					
				}
			}
			
			var typeFlag="";
			function InitWin(){
  
			 var user_id ='${UserInfo.getUserId()}';
			 var proj_id  ='${UserInfo.getProjId()}';
 
 	 
     
	  var res = IntoActionByUrl("${ctx}/ds/ds_ldzl/CheckUser.action?UserId="+user_id);
	  var flag = res[0].result;
		var Layer3 =document.getElementById("Layer3");
		var ly = document.getElementById("ly");
		var btn_return = document.getElementById("btn_return");
		if(flag=="1"){
			ly.style.display="block";
			Layer3.style.display="";
	 
			$("#btn_return > img").click(function(){
			Layer3.innerHTML = "<div style='width:100%;height:100%;'><iframe  id='kjContent' align='top' frameborder='0' src='${ctx}/web/DS/CM_INDEX/ZJLR/index.jsp?UserId="+user_id+"&proj_id=" + proj_id + "' width='100%' height='100%'  style='padding-top: 10px;'> </iframe></div>";
			btn_return.style.display="none";
		     });
	 	    Layer3.innerHTML = "<div style='width:100%;height:100%;'><iframe  id='kjContent' align='top' frameborder='0' src='${ctx}/web/DS/CM_INDEX/ZJLR/index.jsp?UserId="+user_id+"&proj_id=" + proj_id + "' width='100%' height='100%'  style='padding-top: 10px;'> </iframe></div>";
 
		}else if(flag=="2"){
			 
			ly.style.display="block";
			Layer3.style.display="";
		//	btn_wait.style.display="";
			$("#btn_return > img").click(function(){
		     Layer3.innerHTML = "<div style='width:100%;height:100%;'><iframe id='kjContent' align='top' frameborder='0' src='${ctx}/ds/ds_ldzl/DsLdzlIndexFirst.action?UserId="+user_id+"' width='100%' height='100%'  style='padding-top: 10px;'> </iframe></div>";
		     btn_return.style.display="none";
		  //   btn_wait.style.display="";
		     });
	      	 Layer3.innerHTML = "<div style='width:100%;height:100%;'><iframe id='kjContent' align='top' frameborder='0' src='${ctx}/ds/ds_ldzl/DsLdzlIndexFirst.action?UserId="+user_id+"' width='100%' height='100%'  style='padding-top: 10px;'> </iframe></div>";
 
		}
		typeFlag = flag;

	}
	function closeFarme(type){
		if(typeFlag=="1"&&type=="1"){
	       if(!confirm("确定已经完成全部，并且退出？")){
	           return;
		}
		}
		if(typeFlag=="2"){
		       if(!confirm("确定已全部查阅，并且退出？")){
		           return;
			}else{
				var userId = '${UserInfo.getUserId()}';
			    var group_id = '${sessionScope.GROUP_ID}';
 				   var sql = "insert into ds_log_hz(wid,LOG_ID,user_id,module_code,type,proj_id,group_id,created_time)values('"+Page_GetGUID()+"','"+Page_GetGUID()+"','"+userId+"','ALL','IGNORE_ALL','ALL','"+group_id+"',now())";	 
				   var res=GetXmlBySql(sql);
			   $("#btn_wait").css("display","none");
			}
		}
			$("#ly").css("display","none");
		$("#Layer3").css("display","none");
		$("#Layer3").html("");
		$("#btn_close").css("display","none");
		$("#btn_return").css("display","none");
		
	}
	
	function shortlink(obj){
		var userId = '${UserInfo.getUserId()}';
		var module_code  = $(obj).attr("id");
		var orgClass = $(obj).attr("class");
		if(orgClass=="shortlinkDel"){
		   $(obj).attr("class","shortlinkAdd");
		   $(obj).attr("title","取消收藏");
    	   var sql = "INSERT INTO CM_USERLINK(WID,LINK_ID,USER_ID,MODULE_CODE)values('"+Page_GetGUID()+"','"+Page_GetGUID()+"','"+userId+"','"+module_code+"')";	 
		   var res=GetXmlBySql(sql);
		}else{
		   $(obj).attr("class","shortlinkDel");
		   $(obj).attr("title","收藏");
		   var sql = "DELETE FROM CM_USERLINK WHERE USER_ID ='"+userId+"' AND  MODULE_CODE ='"+module_code+"'";	 
		   var res=GetXmlBySql(sql);
		}
		
	}
 	</script>
	</head>
	<body onLoad="startTime()">
		<!--收缩按钮-->
		<a href="#" id="rollBtn" class="roll_01"></a>
		<!--全局左边-->
		<div class="page_left">

			<!--用户中心-->
			<div class="header" id="headBtn" title="快捷菜单">
				<div class="header_ico"></div>
				<div class="header_name">
					${UserInfo.getActualName()}
				</div>
				<div class="header_center">
					<div class="header_center_area">
						<div class="header_caption header_caption_01">
							${UserInfo_Ent.getActualName()}
						</div>
						<div class="header_content">
							<a href="javaScript:logout()"><img
									src="<c:url value='/webResources/style/images/default/header_center_icon_01.png'/>"
									width="48" height="48" />退出</a>
							<a
								href="javaScript:addTab('password','资料修改','ChangeUserInfo.action?type=UserInfo_Ent')"><img
									src="<c:url value='/webResources/style/images/default/header_center_icon_02.png'/>"
									width="48" height="48" />我的资料</a> 
							<a href="javaScript:addTab('PlugDown','插件下载','/web/KM/KM_PLUS/PlugDown.jsp')"><img
									src="<c:url value='/webResources/style/images/default/header_center_icon_27.png'/>"
									width="48" height="48" />插件下载</a>
						 </div>
						<div class="header_content_blank">
						</div>
						<div class="header_caption header_caption_02">
							快捷方式
						</div>
						<div class="header_content" id="linkContent">
							<%--<c:forEach items="${items}" var="quicklyStart">
								<a
									href="javaScript:addTab('','','')"><img
										src="<c:url value='/webResources/style/images/default/header_center_icon_04.png'/>"
										width="48" height="48" />项目文档目录</a>
							</c:forEach>
						--%></div>
						<div class="header_content_blank">
						</div>
					</div>
				</div>
			</div>

			<!--导航-->
			<ul class="nav">
				<li class="sub_00">
					<a class="sub_01" href="#" onclick="load_default()"><b><i>1</i>
					</b><span>首页</span> </a>
				</li>
				<c:forEach items="${items_ent}" var="moduleitem" varStatus="status1">
					<li class="sub_00">
						<c:if test="${fn:length(moduleitem.children)>0}">
							<a class="sub_01" href="#"><b><i>${status1.index+2}</i> </b><span>${moduleitem.text}</span>
								<h1></h1> </a>
							<ul class="sub_02">
								<div class="level_t_l2">
									<b>${status1.index+2}</b>${moduleitem.text}
								</div>
								<div class="level_m">
									<c:forEach items="${moduleitem.children}" var="child"
										varStatus="status2">
										<li>
											<c:if test="${fn:length(child.children)>0}">
												<a class="sub_03" href="#"><b><i>${status2.index+1}</i>
												</b><span>${child.text}</span>
													<h1></h1> </a>

												<ul>
													<div class="level_t_l3">
														<b>${status2.index+1}</b>${child.text}
													</div>
													<div class="level_m">
														<c:forEach items="${child.children}" var="child2"
															varStatus="status3">
															<li>
																<a>
																	href="#" onclick="addTab('${child2.id}','${child2.text}','${child2.attributes}')"  parentModuleCode="${moduleitem.id}" parentModuleName="${moduleitem.text}" ><b><i>${status3.index+1}</i>
																</b>${child2.text}</a>
															</li>
														</c:forEach>
													</div>
												</ul>
											</c:if>
											<c:if test="${fn:length(child.children)==0}">
												<a class="sub_03">
													href="javaScript:addTab('${child.id}','${child.text}','${child.attributes}')"><b><i>${status2.index+1}</i>
												</b><span>${child.text}</span> </a>
											</c:if>
										</li>
									</c:forEach>
								</div>
								<div class="level_b">
									<a href="#" class="rollup"></a>
									<a href="#" class="rolldown"></a>
								</div>
							</ul>
						</c:if>
						<c:if test="${fn:length(moduleitem.children)==0}">
							<a class="sub_01"
								href="javaScript:addTab('${moduleitem.id}','${moduleitem.text}','${moduleitem.attributes}')"><b><i>${status1.index+2}</i>
							</b><span>${moduleitem.text}</span> </a>
						</c:if>
					</li>
				</c:forEach>
			</ul>
			<div class="page_left_bottom">
				<div class="level_b">
					<a href="#" class="rollup"></a><a href="#" class="rolldown"></a>
				</div>
			</div>
		</div>




		<!--全局右边-->
		<div class="page_right" id="page_right">
			<div class="top">
				<div class="logo">
					 <img src="${ctx}/KM/LOGIN/head.png" width="100%" height="100%">
				</div>
				<div class="user_info">

					<div class="drawer">
						<a href="#" onClick="drawer.style.display='block';" title="功能地图"></a>
					</div>
					<div class="exit">
						<a href="#" onclick="logout()" title="注销"></a>
					</div>
				</div>
			</div>
			<div class="pageFrame">
				<div id="tabdiv">
				</div>
			</div>
			<div class="bottom">
				<div id="tabdiv">
				<div  style="float: left;margin-left: 30px;margin-top: 3px;"  id="ShowTime">
					 </div>
				<div style="float: right;margin-right: 30px;margin-top: 3px;"><input type="hidden" id="CurrProjId" value ="" >
				<span style="display: none">当前项目：</span><span id="CurrProj"></span>
				</div>
				</div>
			</div>
		</div>
		<div id="drawer" style="display: none">
			<div class="drawer_content" id="drawer_content">
				<a href="javascript:;" onClick="drawer.style.display='none';"
					class="drawer_close" title="回到登录页面。"></a>
				<div class="drawer_frame">
					<c:forEach items="${items_ent}" var="moduleitem" varStatus="status1">
						<div class="drawer_sub">
							<div class="drawer_caption">
								<c:if test="${fn:length(moduleitem.children)>0}">
									<a href="#">${moduleitem.text}</a>
								</c:if>
								<c:if test="${fn:length(moduleitem.children)==0}">
									<a
										href="javaScript:addTab('${moduleitem.id}','${moduleitem.text}','${moduleitem.attributes}')">
										${moduleitem.text} </a>
								</c:if>
							</div>
							<div class="drawer_map_sub">
								<ul>
									<c:forEach items="${moduleitem.children}" var="child"
										varStatus="status2">
										<li>
											<c:if test="${fn:length(child.children)>0}">
												<a href="#">${child.text}<b></b> </a>
												<div>
													<c:forEach items="${child.children}" var="child2"
														varStatus="status3">
														<a
															href="javaScript:addTab('${child2.id}','${child2.text}','${child2.attributes}')"><i id="${child2.id}" class="${child2.className}" title="${child2.title}" onclick="shortlink(this);return false;"></i>${child2.text}</a>
													</c:forEach>
												</div>
											</c:if>
											<c:if test="${fn:length(child.children)==0}">
												<a>
													href="javaScript:addTab('${child.id}','${child.text}','${child.attributes}')">${child.text}</a>
											</c:if>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>


			<div id="newWindow" class="easyui-window" closed="true"
				style="width: 300px; height: 100px">
				<iframe id="win" scrolling="no" style="width: 100%; height: 100%"></iframe>
			</div>
			
				     <div id="ly" style="position: absolute; top: 0px; filter: alpha(opacity=60); background-color: #777;      
		        z-index: 101; left: 0px;WIDTH: 100%;HEIGHT:100%;display: none;">
		    </div>     
		    <!--         浮层框架开始         -->     
		    <DIV ID="Layer3" STYLE="Z-INDEX: 103;WIDTH: 90%;HEIGHT:98%;border: 1px solid #000;position: absolute;left:5%;top:10px;display: none;">
		    
		    </DIV>
		     <div id="btn_return" style="Z-INDEX: 104;position: absolute;width:34px;height:34px;left:90%;top:0px;cursor: pointer;display:none ;"><img src="${ctx}/img/button/return.png" style="height: 25px;width: 25px;"/></div>		  
		    <div id="btn_wait" style="Z-INDEX: 106;position: absolute;width:300px;height:54px;left:45%;top:30%;display: none;">
		    <table bordercolor="white">
			    <tr>
		        	<td style="background-color:white;">
		        	   <img style="width: 300px;height: 14px;"  src="${ctx}/img/page_wait.gif" ><br>&nbsp;&nbsp;&nbsp;正在压缩请等待...
		            </td>
			    </tr>
		    </table>
		   </div>
	</body>
</html>