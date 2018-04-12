<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*"%>
    <%@include file="/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.*"%>
<title>项目图片</title>
<link rel="stylesheet" href="${ctx}/web/CO/CO_NEW/INDEX/center.css" type="text/css"></link>
<style type="text/css">
#banner {position:absolute; top:0px;bottom:0px;left:0px;right:0px; overflow:hidden;}
#banner_list img {width: 100%;height: 100%;}
#banner_bg {position:absolute; bottom:5px;background-color:#000;height:30px;filter: Alpha(Opacity=30);opacity:0.3;z-index:1000;cursor:pointer; width:100%; }
#banner_info{position:absolute; bottom:10px; left:5px;height:22px;color:#fff;z-index:1001;cursor:pointer}
#banner_text {position:absolute;width:120px;z-index:1002; right:3px; bottom:3px;}
#banner ul {position:absolute;list-style-type:none;filter: Alpha(Opacity=80);opacity:0.8; border:1px solid #fff;z-index:1002;
			margin:0; padding:0; bottom:10px; right:5px;}
#banner ul li { padding:0px 8px;float:left;display:block;color:#FFF;border:#e5eaff 1px solid;background:#6f4f67;cursor:pointer}
#banner ul li.on { background:#900}
#banner_list a{position:absolute;width: 100%;height: 100%;} <!-- 让四张图片都可以重叠在一起-->
A.mytest:link {   
 FONT-SIZE: 12px; COLOR: #5b5b5b; TEXT-DECORATION: none   
}   
A.mytest:visited {   
 FONT-SIZE: 12px; COLOR: #969696; TEXT-DECORATION: none   
}   
A.mytest:active {   
 FONT-SIZE: 12px; COLOR: #0099ff; TEXT-DECORATION: none   
}   
A.mytest:hover {   
 FONT-SIZE: 12px; COLOR: #ff6102; TEXT-DECORATION: none   
}   


</style>
<script type="text/javascript" src="jquery-1.2.6.pack.js"></script>
<script type="text/javascript">
	var t = n = 0, count;
	$(document).ready(function(){	
		count=$("#banner_list a").length;
		$("#banner_list a:not(:first-child)").hide();
		$("#banner_info").html($("#banner_list a:first-child").find("img").attr('alt'));
		//$("#banner_info").click(function(){window.open($("#banner_list a:first-child").attr('href'), "_blank")});
		$("#banner li").click(function() {
        			var i = $(this).text() - 1;//获取Li元素内的值，即1，2，3，4
			n = i;
			if (i >= count) return;
			$("#banner_info").html($("#banner_list a").eq(i).find("img").attr('alt'));
			$("#banner_info").unbind().click(function(){window.open($("#banner_list a").eq(i).attr('href'), "_blank")})
			$("#banner_list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000);
			document.getElementById("banner").style.background="";
			$(this).toggleClass("on");
			$(this).siblings().removeAttr("class");
		});
	 	t = setInterval("showAuto()", 2000);
	 	$("#banner").hover(function(){clearInterval(t)}, function(){t = setInterval("showAuto()", 2000);});
	})
	 	
	function showAuto()
	{
		n = n >=(count - 1) ? 0 : ++n;
			var i = $("#banner li").eq(n).text() - 1;//获取Li元素内的值，即1，2，3，4
			n = i;
			if (i >= count) return;
			$("#banner_info").html($("#banner_list a").eq(i).find("img").attr('alt'));
			$("#banner_list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000);
			document.getElementById("banner").style.background="";
			$("#banner li").eq(n).toggleClass("on");
			$("#banner li").eq(n).siblings().removeAttr("class");
	}
	function clinkNotify()
	{
    	parent.parent.addTab('DS_FX_PICTABLE','形象进度','/ds/pic_table/show.action');

	}
    function OpenPic(path){
    	createSimpleWindow("ShowPic","显示原图","${ctx}/web/SYS/FIRST/pic/ShowPic.jsp?path="+path, 950, 570);
    }
</script>

</head>

<body style="height:410px;">
<div class="center_02" style="position: relative;width:100%;border:none;" >
<!-- <div class="center_000_01"> -->
<!-- 	<div><b><span style="font-size: 12px;font-family: 宋体;font-weight: normal;" > &nbsp;☆ 进展图片</span><span style="font-size: 12px;float: right;" ><a href="#" onclick="clinkNotify();" class="mytest" style="color: black;font-weight: normal;">点击浏览当前项目下的所有照片</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></b></div> -->
<!-- </div> -->
<div id="banner" >	
<!-- 	<div id="banner_bg"></div>  标题背景 -->
	<div id="banner_info"></div> <!--标题-->

    <ul>
    	<c:forEach var="item" items="${PhotoList}" varStatus="status" end="4">
    	<c:if test="${status.count<=10}">
    	<c:if test="${status.count==1}">
        <li class="on">${status.count}</li>
        </c:if>
      <c:if test="${status.count!=1}">
         <li>${status.count}</li>
         </c:if>
       </c:if>
        </c:forEach>
        
    </ul>
   
   <div id="banner_list"   >
   <c:if test="${empty PhotoList}">
    <a href="#" target="_blank"><img id="img" src="${ctx}/webResources/pic/nopic.gif" title="暂无图片，请上传！" alt="${UserInfo.getProjName()}" height="100%" width="100%"/></a>
   </c:if>
   <c:if test="${not empty PhotoList}">
  		<c:forEach var="item" items="${PhotoList}" varStatus="status">
  		 <a href="#"  onclick="OpenPic('${item.PATH}');" ><img id="img" src="${item.PATH_THUMBNAIL}" title="${item.IMG_NAME}" alt="${UserInfo.getProjName()}" /></a>
        </c:forEach>
        </c:if>

      </div>
</div>
</div>
</body>
</html>