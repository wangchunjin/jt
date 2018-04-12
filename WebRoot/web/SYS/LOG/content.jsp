<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
 
         $(function(){ 
   
        	$('#SysLogTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,              
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/xmjs/proj/log.action?userName=${param.userName}&theName=${param.theName}&ipArea=${param.ipArea}&state=${param.state}&KSDate=${param.KSDate}&JSDate=${param.JSDate}',
			    pagination:true,//分页控件 
                rownumbers:true,//行号 
                pageSize: 30,//每页显示的记录条数，默认为10 
		        pageList: [30,100,100000],//可以设置每页记录条数的列表 
				frozenColumns:[[
				    {field:'WID',checkbox:true}
				]],
				columns:[[
					{field:'USER_NAME',title:'用户名',width:0.1,align:'left' ,sortable:true,
						sorter:function(param1,param2){
							if(!isStringNull(param1)){
								param1 = "";
							}
							if(!isStringNull(param2)){
								param2 = "";
							}
							if(param1.localeCompare(param2)<0){
								return -1;
							}else{
								return 1;
							}
						}	
					},
					{field:'ACTUAL_NAME',title:'姓名 ',width:0.1,align:'left' ,sortable:true,
						sorter:function(param1,param2){
							if(!isStringNull(param1)){
								param1 = "";
							}
							if(!isStringNull(param2)){
								param2 = "";
							}
							if(param1.localeCompare(param2)<0){
								return -1;
							}else{
								return 1;
							}
						}	
					},
					{field:'IP',title:'IP地址',width:0.1,align:'left' ,sortable:true},
					{field:'STATE',title:'状态',width:0.1,align:'left' ,sortable:true},
					{field:'LOGIN_TIME',title:'时间',width:0.1,align:'left' ,sortable:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })
				 },
               onClickRow:function(rowIndex, node){
				var where = node.WID;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
				
			});
		  	});
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
  			
  			function openWin(id){
			    var currFrameId = parent.frameElement.id;
				var wtop=(window.screen.height-530)/2;
				var wleft=(window.screen.width-550)/2;
		 		createSimpleWindow("selectProj","权限角色显示","${ctx}/web/SYS/LOG/projTree.jsp?currFrameId="+currFrameId, 600, 500);
			}
			$(function(){
				var currFrameId = parent.frameElement.id;
				$("#projInfo_NAME").val('${param.projinfoName}');
				$("#projInfo").val('${param.ipArea}');
			});
        </script>
	</head>
	<body>
		<div style="height:40px;margin-left: 10px;width: 100%;">
			<table>
				<tr>
					<td width="7%"><span style="float: right">角色名称：</span></td>
					<td width="8%"><span style="float: left"><epmis:text id="projInfo" attr="style='float: left'" displaytypeedit="1"  value="" define=""  ></epmis:text></span></td>
					<td>
						用户名：<input class="label_text" name="userName" id="userName" style="width:7%;" value="" >&nbsp;&nbsp;
			       		姓名：<input class="label_text" name="theName" id="theName" style="width:7%;" value="${param.theName }" >&nbsp;&nbsp;
			       		状态：<input class="label_text" name="state" id="state" style="width:7%;" value="${param.state }" >&nbsp;&nbsp;
			       		时间：<input class="label_text" name="KSDate" id="KSDate" style="width:8%;" value="${param.KSDate}"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})">-
			       		<input class="label_text" name="JSDate" id="JSDate" style="width:8%;" value="${param.JSDate}"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})">
			       		<epmis:button id="ShowSearch" imageCss="icon-search" value="检索" action="searchShow()" datactrSql=""  datactrCode="" ></epmis:button>
			       		<epmis:button id="ShowSearch" imageCss="icon-search" value="日志报表" action="searchUser()" datactrSql=""  datactrCode="" ></epmis:button>
					</td>
					<td width="*"></td>
				</tr>
			</table>
		</div> 
		<div   class="page_content" style="margin-top: 30px;">
		<table id="SysLogTable" class="TreeClass" ></table>
		</div>
   </body>

</html>