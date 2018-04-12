<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
 a:LINK {
	text-decoration: none;
	color: #0066cc;
		}
a:HOVER {
	text-decoration: underline;
}
.tab-ziti {
	font-family: "宋体";
	font-size: 12px;
	line-height: 21px;
	color: #000000;
}
a:VISITED {
	color: black;
}
.tab-bottom-bg {
	height: 5px;
    background: white;
}
a,area { blr:expression(this.onFocus=this.blur()) } 
</style>
<script type="text/javascript" src="${ctx}/webResources/js/easyui/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/webResources/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/webResources/js/easyui/locale/easyui-lang-zh_CN.js"></script>

<script language="javascript">


var toogle = parent.document.getElementById("Toogle");

//选中的列
var oldMenu = null; 
var myModule = ''; 
function changeStyle(menu)
{
	try{
		if(menu != oldMenu)
		{  
			var moduleCode = menu.id;
			 
			if(isNull(oldMenu))
			{
				$(oldMenu).attr("isdefault","0")
			}
		
			$("td").filter(".tab-ziti").each(function()
					{
							this.style.background="url(${ctx}/img/tab/tab-unselect-bg.jpg)";
							 $(this).find("#Title").css("font-weight","normal");
 					});
 					 
			var selectTable=$("#"+menu.id+">table");
	 
			$(selectTable).find("td").each(function()
					{
						if(this.id=="td_img_2")
						{
							this.style.background="url(${ctx}/img/tab/tab-select-bg.jpg)";
							$(this).css({"background-position":"top center"});
							
						}
						
					});
 			try{ 
 				$(menu).find("#Title").css("font-weight","bold");
 				} catch(e){}
			$(menu).attr("isdefault","1")
			oldMenu = menu;
		}
		var where = $(labelInfo).attr("where");
		var frame = window.frameElement;
		if(frame && frame.nextSibling)
		{
			 
			var filter = $(menu).attr("filter");
		    p = /\{.*?\}/g;
		    if(isStringNull(filter)){
			    filter = filter.replace(p, '');
			}
			if(isStringNull(where))
			{
				$(frame).next().attr("src",getFilterURL(menu,where));
			}else
			{
				$(frame).next().attr("src",$(menu).attr("url")+filter);
			}
		}
	} catch(e) {}
	return false;
}

//得到选中label的URL
function getLabelUrl(where)
{
	try{
		var label = labelInfo || document.getElementById("labelInfo");
		labelInfo = label;
		$(label).attr("where",where);
		$("#labelInfo").attr("where",where);
		var selectCell = getSelectLabel(label);
		var newUrl = getFilterURL(selectCell,where);
		return newUrl;
	}catch (e){}
}

//设置打开指定的label
function setLabelUrl(where,index)
{
	try{
		var label = labelInfo || document.getElementById("labelInfo");
		labelInfo = label;
		$(label).attr("where",where);
		var cells = label.cells;
		var selectCell = null;
		if(index)
		{
			index = index || 0;
			for(var i=0; i < cells.length ; i++)
			{
				if(index == i)
				{
					cells[i].isdefault = "1";
					selectCell = cells[i];
				}else
				{
					cells[i].isdefault = "0";
				}
			}
		}
		selectCell = getSelectLabel(label);
		changeStyle(selectCell);
	}catch (e){}
}
//得到选中的Label
function getSelectLabel(label)
{
	try{
		label = label || document.getElementById("labelInfo");
		labelInfo = label;
		var cell = $("#labelInfo").find("td[isdefault='1']")[0];
		if(!isNull(cell)){
			cell = $("#labelInfo").find("td[isdefault='0']")[0];
		}
		return cell;
	}catch (e){}
}
//导向黙认的列
var labelInfo = null;
function beforeLoad()
{
	try{
		labelInfo = document.getElementById("labelInfo");
		divbut = document.getElementById("LabelDiv");
		labelBut = document.getElementById("LabelBut");
		td_img_11 = document.getElementById("td_img_11"); 
		td_img_12 = document.getElementById("td_img_12");
		td_img_13 = document.getElementById("td_img_13");
		td_img_14 = document.getElementById("td_img_14");
		oldMenu = getSelectLabel();
		var where = $(labelInfo).attr("where");
		var frame = window.frameElement;
		if(frame && frame.nextSibling)
		{
			if(where !='')
			{
				//$(frame).next()[0].src = "${ctx}/web/SYS/XMJS/content.jsp";
			}else
			{
				//$(frame).next()[0].src = "${ctx}/web/SYS/XMJS/content.jsp";
			}
		}
		dispalyLabelDiv();//初始化是否显示左右移动按钮
		IntBug();
		//初始化显示
	}catch (e){}
}
  
//移到第一位
var divbut = null; //移动的层
var labelBut = null;//label按钮显示层
var lindex = 0; //初始化值
var td_img_11 = null;
var td_img_12 = null;
var td_img_13 = null;
var td_img_14 = null;
var isbef = false;//是否可以向前
var isaft = true;//是否可以向后
function buton1()
{
	lindex = 0;
	if(isNull(divbut))
		divbut.scrollLeft = 0;	
	//td_img_12.style.display = "none";
	//td_img_13.style.display = "";
	isbef = false;
	isaft = true;
}
//向前移动
function buton2()
{
	if(!isbef)
		return;
	lindex--;
	var width = 0;
	for(var i=0; i<lindex; i++)
		width += labelInfo.cells[i].offsetWidth;
	divbut.scrollLeft = width;
	if(divbut.scrollLeft <= 0)
		isbef = false;
	isaft = true;
		//td_img_12.style.display = "none";
	//td_img_13.style.display = "";
}
//向后多动
function buton3()
{ 
	if(!isaft)
		return;
	lindex++;
	var width = 0;
	for(var i=0; i<lindex; i++)
		width += labelInfo.cells[i].offsetWidth;
	divbut.scrollLeft = width;
	var leftWidth = divbut.scrollLeft + divbut.offsetWidth;
	if(leftWidth >= divbut.scrollWidth)
		isaft = false;
	isbef = true;
		//td_img_13.style.display = "none";
	//td_img_12.style.display = "";
}
//移到最后一位
function buton4()
{
	var leftWidth = divbut.scrollWidth - divbut.offsetWidth; 
	divbut.scrollLeft = leftWidth;
	var width = 0;
	for(var i=0; i<labelInfo.cells.length; i++)
	{
		width += labelInfo.cells[i].offsetWidth;
		if(width >= leftWidth)
		{
			lindex = i+1;
			return;
		}
	}
	
	isbef = true;
	isaft = false;
}
//移到最后一位
function dispalyLabelDiv()
{
	try{
		if(isNull(divbut) && isNull(labelBut))
		{
			if(divbut.scrollLeft > 0)
				labelBut.style.display = "";
			else if(divbut.scrollLeft == 0);
			{
				divbut.scrollLeft = 1;
				if(divbut.scrollLeft > 0)
					labelBut.style.display = "";
				else
					labelBut.style.display = "none";
			}
		}
	}catch(e){}
}
 $(function()
		{
				 var threeframe = parent.document.getElementById("ThreeFrame");
				 var rows_str1=threeframe.parentNode.rows;
			$("#upordown").find("img").bind("click",function()
					{
				
					var rows_str=threeframe.parentNode.rows;
					var pageheight=$(threeframe.parentNode).height();
							if(this.id=="up")
							{   
								if(isNull(parent.document.getElementById("ButtonFrameZ"))){
									threeframe.parentNode.rows = "26,0,0,10,25,*"
								}else if(rows_str.substring(rows_str.lastIndexOf(",")+1)==-30)
								{
									threeframe.parentNode.rows=rows_str1;
								}
								else
								{
									var rowslength=rows_str.split(",").length;
							      	if(rowslength==5)
							       		toogle.parentNode.rows=rows_str.split(",")[0]+",*,1,26,"+(pageheight-60);
							      	else if(rowslength==6)
							      		toogle.parentNode.rows=rows_str.split(",")[0]+","+rows_str1.split(",")[1]+",*,1,26,"+(pageheight-60);
							      	else if(rowslength==7)
							      		toogle.parentNode.rows=rows_str.split(",")[0]+","+rows_str1.split(",")[1]+",*,1,26,"+(pageheight-60)+",40";
									//this.style.display="none";
								}
								
								//$("#down").show();
								
							}
							
							else
							{
                                if(isNull(parent.document.getElementById("ButtonFrameZ"))){
                                	threeframe.parentNode.rows = "38,*,30,10,25,0"
								}else if(rows_str1!=rows_str)
								{
									threeframe.parentNode.rows=rows_str1;
								}
									
								else
								{
									var rowslength=rows_str.split(",").length;
									if(rowslength==5)
										threeframe.parentNode.rows=rows_str.substring(0,rows_str.indexOf(","))+",*,1,26,-30";
									else if(rowslength==6)
										toogle.parentNode.rows=rows_str.split(",")[0]+","+rows_str1.split(",")[1]+",*,1,26,-30";
									else if(rowslength==7)
										toogle.parentNode.rows=rows_str.split(",")[0]+","+rows_str1.split(",")[1]+",*,1,26,-30";
									//this.style.display="none";
								}
									
								//$("#up").show();
								
							}
								
								 
					});
		
			
		}); 
		
		




	
</script>
</head>
<body onload="beforeLoad()" style="margin:0 5px;padding: 0; font-family:Microsoft YaHei;" onselectstart="">
<form>
	<div class="rollBox" id ="rollBox" style="width: 100%;float: left;border: 1px solid #dedede;height:26;margin: 0;padding: 0;background:url('${ctx}/img/tab/tab-unselect-bg.jpg');">
    <div style="float: left;width: 5px;height:25;">
    <table id="upordown" height="" border="0" cellspacing="0" cellpadding="0">
    <tr>
	      <td style="border-bottom: 1 solid #dedede;width: 5px;height:12px;white-space: nowrap;background-color: #ffffff;" id="upordown"><span><img alt="" id="up" onclick="" src="${ctx }/img/tab/btn_up.png" style="cursor:pointer;h" title="展开标签"></span>
	</tr>
	<tr>
	      <td style="height:12px"><span><img alt=""  id="down"  onclick="" src="${ctx }/img/tab/btn_dn.png" style="cursor:pointer;" title="收缩标签"></span></td>
	</tr>
	</table>
	</div>
	
 <div id="LabelDiv" style="width: 90%;margin-left:5px; overflow-x: hidden; float: left;" onpropertychange="dispalyLabelDiv()">
	<table id="LabelTb" border="0" cellspacing="0" cellpadding="0" width=100% class="" height="26" >
		<tr class='' height="26">
			<td background="" style="white-space: nowrap;height:0px;">
			<table border="0" cellspacing="0" cellpadding="0" height="20" id="labelInfo" where="" style="cursor: pointer;">
				<tr height="0">
					
						<td style="white-space: nowrap; " onclick="changeStyle(this)" id="A7226136131C4AC18B37CCC0A8D96A44" isdefault="1" url="${ctx }/sys/proj/label.action" ordernum="1" filter="?PROJ_ID={0}" index="0">
						</td>
					
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
	</div>
 
</form>
</body>

</html>