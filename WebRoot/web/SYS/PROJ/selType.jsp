<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>安全管理</title>
<script type="text/javascript">
function initModeType(){
   	var options = new Array();
   	options[0] = "EPS_CATTYPE=EPS分类";
	options[1] = "PROJ_MAP=地图分布";
	options[2] = "PROJ_CATTYE=部门分类";
	options[3] = "PROJECT_CATTYE=项目类别 ";
	options[4] = "SUPERVISION_CATTYE=监理费用分类 ";
	options[5] = "COMPROJ_CATTYE=完成项目总投资分类 "
 
   var MODULE_NAME = $(parent.frameElement).attr("MODULE_NAME");
     SetSelect(" SELECT CATG_TYPE_ID,CONCAT(CATG_TYPE,'(分类码)') AS NAME FROM CM_CATTYPE WHERE  WHICH_CATVAL_TYPE='SYS_PROJCATG'  ORDER BY CATG_TYPE","type","EPS_CATTYPE",options)
   
}
function changeView(obj)
{
    var selView=obj.value;
	var isShow = document.all.isShow;

		if(selView=='PROJ_CATTYE')
		{
		    isShow.style.display="none";
		    parent.document.all.TwoFrame.src="${ctx}/web/SYS/PROJ/content_proj.jsp";	 
		}
		else if(selView=='EPS_CATTYPE')
		{
			isShow.style.display="";
 
			parent.document.all.TwoFrame.src="${ctx}/web/SYS/PROJ/content.jsp";
		}
		else if(selView=='PROJECT_CATTYE')
		{
			isShow.style.display="none";
			parent.document.all.TwoFrame.src="${ctx}/web/SYS/PROJ/content_project.jsp";
		}
		else if(selView=='COMPROJ_CATTYE')
		{
			isShow.style.display="none";
			parent.document.all.TwoFrame.src="${ctx}/web/SYS/PROJ/content_comproj.jsp";
		}
		else if(selView=='SUPERVISION_CATTYE')
		{
			isShow.style.display="none";
			parent.document.all.TwoFrame.src="${ctx}/web/SYS/PROJ/content_supervision.jsp"; 
		}
		else if(selView=='PROJ_MAP')
		{
			isShow.style.display="none";
			parent.document.all.TwoFrame.src="${ctx}/web/SYS/PROJ/MAP/content_map.jsp"; 
		}
		else
		{
			isShow.style.display="none";
			parent.document.all.TwoFrame.src="${ctx}/web/SYS/PROJ/content_catvaltree.jsp?catg_type_id="+selView;
		}
 	 parent.OneFrame.location.reload();
}
function  openw4()
{
	var showundo1 = document.getElementById("showundo1");
	var showdo1   = document.getElementById("showdo1");
	var key   = document.getElementById("key").value;
	var where = "";
	if(!showundo1.checked && !showdo1.checked){
		where = "?type=NULL";
	}else if(!showundo1.checked && showdo1.checked){
		where = "?type=DO";
	}else if(showundo1.checked && !showdo1.checked){
		where ="?type=NOT";
	}else{
		where = "?type=ALL";
	}
	parent.document.all.TwoFrame.src="${ctx}/web/SYS/PROJ/content.jsp"+where+"&key="+encodeURI(key.trim());
}
</script>
</head>
<body onload="initModeType()">
 
<table width="100%" border="0">
   <tr height="25" >
      <td width="49" height="25" align="right">&nbsp;视图：&nbsp;</td>
      <td width="120">
		<select id="type" class="select" name="type" onchange="changeView(this)">
		</select> 
      </td>
      <td>   
      &nbsp;&nbsp;&nbsp;&nbsp;

 		<span id="isShow">
 		
 		              代码或名称关键字搜索：<input type="text" id="key" onchange="openw4()">
&nbsp;&nbsp;&nbsp;&nbsp; 
 		<input id="showundo1" type="checkbox" onclick="openw4();" checked/>显示未完工项目
		<input id="showdo1" type="checkbox" onclick="openw4();"  />显示已完工项目</span>
         </td>
 
    </tr>
</table>
 
</body>
<input type="hidden" name="show" value="${param.show}">
</html>