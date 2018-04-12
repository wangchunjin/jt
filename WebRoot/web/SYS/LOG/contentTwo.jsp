<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
    <head>
        <title>Struts2+ JasperReports 使用示例</title>
    	<script type="text/javascript">
    		function searchUser(){
  				var userName = $("#userName").val();
		  		var theName = $("#theName").val();
		  		var ipArea = $("#projInfo").val();
		  		var projinfoName = $("#projInfo_NAME").val();
		  		var state = $("#state").val();
		  		var KSDate = $("#KSDate").val();
		  		var JSDate = $("#JSDate").val();
  				parent.document.getElementById("TwoFrame").src = "${ctx}/web/SYS/LOG/contentTwo.jsp?userName="+userName+"&theName="+theName+"&ipArea="+ipArea+"&state="+state+"&KSDate="+KSDate+"&JSDate="+JSDate+"&projinfoName="+projinfoName;
  			}
  			function searchShow(){
		  		var userName = $("#userName").val();
		  		var theName = $("#theName").val();
		  		var ipArea = $("#projInfo").val();
		  		var projinfoName = $("#projInfo_NAME").val();
		  		var state = $("#state").val();
		  		var KSDate = $("#KSDate").val();
		  		var JSDate = $("#JSDate").val();
		  		parent.document.getElementById("TwoFrame").src = "${ctx}/web/SYS/LOG/content.jsp?userName="+userName+"&theName="+theName+"&ipArea="+ipArea+"&state="+state+"&KSDate="+KSDate+"&JSDate="+JSDate+"&projinfoName="+projinfoName;
  			}
  			function openWin(id){
			    var currFrameId = parent.frameElement.id;
				var wtop=(window.screen.height-530)/2;
				var wleft=(window.screen.width-550)/2;
		 		createSimpleWindow("selectProj","权限角色显示","${ctx}/web/SYS/LOG/projTree.jsp?currFrameId="+currFrameId, 600, 500);
			}
			$(function(){
				$("#projInfo_NAME").val('${param.projinfoName}');
				$("#projInfo").val('${param.ipArea}');
			});
			
			function exportPDF(){
        		var userName = $("#userName").val();
		  		var theName = $("#theName").val();
		  		var ipArea = $("#projInfo").val();
		  		var projinfoName = $("#projInfo_NAME").val();
		  		var state = $("#state").val();
		  		var KSDate = $("#KSDate").val();
		  		var JSDate = $("#JSDate").val();
        		window.open("${ctx}/report/proj/searchUser_PDF.action?userName="+userName+"&theName="+theName+"&ipArea="+ipArea+"&state="+state+"&KSDate="+KSDate+"&JSDate="+JSDate+"&projinfoName="+projinfoName);
        	}
        	function exportExcel(){
        		var userName = $("#userName").val();
		  		var theName = $("#theName").val();
		  		var ipArea = $("#projInfo").val();
		  		var projinfoName = $("#projInfo_NAME").val();
		  		var state = $("#state").val();
		  		var KSDate = $("#KSDate").val();
		  		var JSDate = $("#JSDate").val();
	        	window.open("${ctx}/report/proj/searchUser_XLS.action?userName="+userName+"&theName="+theName+"&ipArea="+ipArea+"&state="+state+"&KSDate="+KSDate+"&JSDate="+JSDate+"&projinfoName="+projinfoName);
        	}
    	</script>
    </head>
    
    <body>
    <div style="height:60px;margin-left: 10px;width: 100%">
			<table>
				<tr>
					<td width="7%"><span style="float: right">角色名称：</span></td>
					<td width="8%"><span style="float: left"><epmis:text id="projInfo" attr="style='float: left'" displaytypeedit="1"  value="" define=""  ></epmis:text></span></td>
					<td>
						用户名：<input class="label_text" name="userName" id="userName" style="width:7%;" value="${param.userName}" >&nbsp;&nbsp;
			       		姓名：<input class="label_text" name="theName" id="theName" style="width:7%;" value="${param.theName}" >&nbsp;&nbsp;
			       		状态：<input class="label_text" name="state" id="state" style="width:7%;" value="${param.state}" >&nbsp;&nbsp;
			       		时间：<input class="label_text" name="KSDate" id="KSDate" style="width:8%;" value="${param.KSDate}"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})">-
			       		<input class="label_text" name="JSDate" id="JSDate" style="width:8%;" value="${param.JSDate}"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})">
			       		<epmis:button id="ShowSearch" imageCss="icon-search" value="检索" action="searchShow()" datactrSql=""  datactrCode="" ></epmis:button>
			       		<epmis:button id="ShowSearch" imageCss="icon-search" value="日志报表" action="searchUser()" datactrSql=""  datactrCode="" ></epmis:button>
					</td>
					<td width="*"></td>
				</tr>
				<tr>
	    			<td colspan="4">
	    				<epmis:button id="pdf" imageCss="icon-pdf" value="" action="exportPDF()"></epmis:button>
	         			<epmis:button id="xls" imageCss="icon-excel" value="" action="exportExcel()"></epmis:button>
	    			</td>
    			</tr>
			</table>
	 </div> 
     <div style="position: absolute;width: 100%;top:0;bottom: 0;overflow: auto;margin-top: 60px;">
        <iframe frameborder="0" id="report" height="100%" width="100%" src="${ctx}/report/proj/searchUser_HTML.action?userName=${param.userName}&theName=${param.theName}&ipArea=${param.ipArea}&state=${param.state}&KSDate=${param.KSDate}&JSDate=${param.JSDate}" ></iframe>
     </div>
    </body>
</html>
