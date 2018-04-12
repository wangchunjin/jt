<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Portal - jQuery EasyUI</title>
	<link rel="stylesheet" type="text/css" href="${ctx }/webResources/js/easyui/blue/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/webResources/js/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/webResources/portal/portal.css">
	<style type="text/css">
		.title{
			font-size:16px;
			font-weight:bold;
			padding:20px 10px;
			background:#eee;
			overflow:hidden;
			border-bottom:1px solid #ccc;
		}
		.t-list{
			padding:5px;
		}
		.panel-tool a{
			
			heigth:18px
		}
	</style>
	<script type="text/javascript" src="${ctx }/webResources/portal/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="${ctx }/webResources/portal/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx }/webResources/portal/jquery.portal.js"></script>
	<script type="text/javascript" src="${ctx }/webResources/GooFlow/json2.js"></script>
	
	<script>
		var p_title = "";//用户选择的类型 三列/两列
		var p_width = "";//用户设置的宽度
		var user_id = '${sessionScope.UserInfo_Ent.userId}';
		$(function(){
			var portalLetList = "";
			var portalLetCount = 0;
			$.ajax({
				url : "${ctx}/cc/portal/queryUserLayout.action",
				data : {'p_type':'ENT','user_id':user_id},
				async : false,
				type : 'POST',
				success : function(returnData){
					
					for(var i in returnData){
						p_title = returnData[i].P_TITLE;
						p_width = returnData[i].P_WIDTH;
						portalLetList = returnData[i].portalLetList;
						portalLetCount = returnData[i].portalLetCount;
					}
					
				}
			});
			
			var columnType = "三列";
			
			var widths = p_width.split(",");
			if(p_title == "三列"){
				var contentHtml = '<div style="width:'+widths[0]+';"></div><div style="width:'+widths[1]+';"></div><div style="width:'+widths[2]+';"></div>';
				$("#pp").html(contentHtml);
				columnType = "三列";
			}else if(p_title == "两列"){
				var contentHtml = '<div style="width:'+widths[0]+';overflow: hidden;"></div><div style="width:'+widths[1]+';overflow: hidden;">';
				$("#pp").html(contentHtml);
				columnType = "两列";
			}else{//默认为三列
				var contentHtml = '<div style="width:'+widths[0]+';"></div><div style="width:'+widths[1]+';"></div><div style="width:'+widths[2]+';"></div>';
				$("#pp").html(contentHtml);
				columnType = "三列";
			}
			
			//初始化portal参数
			$('#pp').portal({
				border:false,
				fit:true,
				onStateChange:function(){//当用户拖放面板（panel）时触发。
                    var rows = "";
                    var cols = "";
                    var ids = "";
	                for (var i = 0; i < portalLetCount; i++) {
	                    var pl = $('#pp').portal("getPanels", i);
	                    for (var j = 0; j < pl.length; j++) {
	                    	rows += rows != "" ? ","+j : j;
	                    	cols += cols != "" ? ","+i : i;
	                    	ids += ids != "" ? ","+pl[j].attr("id") : pl[j].attr("id");
	                    }
	                }
                    //alert(rows+"-----"+cols+"---"+ids);
                    $.ajax({
        				url : "${ctx}/cc/portal/updateUserPos.action?rows="+rows+"&cols="+cols+"&ids="+ids,
        				data : {'p_type':'ENT','user_id':user_id},
        				async : false,
        				type : 'POST',
        				success : function(returnData){
        					
        				}
        			});
                    
				}
			});
			
			//add();
			addPortalLet(columnType,portalLetList,portalLetCount);
			
			
		});

		function addPortalLet(portalColumns,portalLetList,portalLetCount){
			if(portalColumns == "三列"){
				//alert(portalLetCount);
				var num = 0;
				for(var i=0; i<portalLetCount; i++){
					if(num >2)
						num = 0;
					var p = $('<div/>').appendTo('body');
					p.panel({
						//href:'http://www.baidu.com',
						id:portalLetList[i].P_ID,
						title:portalLetList[i].P_TITLE,
						content:'<iframe id="'+portalLetList[i].P_CODE+'frame" name="'+portalLetList[i].P_CODE+'frame" src="${ctx}'+portalLetList[i].P_URL+'" style="width:100%;height: '+portalLetList[i].P_HEIGHT+' " frameborder="0" ></iframe>',
						height:portalLetList[i].P_HEIGHT,
						closable:true,
						collapsible:true,
						
					});
					var colNum = portalLetList[i].COL;
					if(colNum>2){
						colNum = num;
					}
					$('#pp').portal('add', {
						panel:p,
						columnIndex:colNum //所在列数，当该列已经存在数据时，将会在下一行的该列创建数据
					});
					
					num++;
					
					
				}
				$('#pp').portal('resize');
			}else if(portalColumns == "两列"){
				var num = 0;
				for(var i=0; i<portalLetCount; i++){
					if(num >1)
						num = 0;
					var p = $('<div/>').appendTo('body');
					p.panel({
						id:portalLetList[i].P_ID,
						title:portalLetList[i].P_TITLE,
						content:'<iframe id="'+portalLetList[i].P_CODE+'frame" name="'+portalLetList[i].P_CODE+'frame" src="${ctx}'+portalLetList[i].P_URL+'" style="width:100%;height: '+portalLetList[i].P_HEIGHT+' " frameborder="0" ></iframe>',
						height:	portalLetList[i].P_HEIGHT,
						closable:true,
						collapsible:true
					});
					var colNum = portalLetList[i].COL;
					if(colNum>1){
						colNum = num;
					}
						
					$('#pp').portal('add', {
						panel:p,
						columnIndex:colNum //所在列数，当该列已经存在数据时，将会在下一行的该列创建数据
					});
					num ++;
				}
				$('#pp').portal('resize');
			}else if(portalColumns == "一列"){
				var num = 0;
				for(var i=0; i<portalLetCount; i++){
					var p = $('<div/>').appendTo('body');
					p.panel({
						id:portalLetList[i].P_ID,
						title:portalLetList[i].P_TITLE,
						content:'<iframe id="'+portalLetList[i].P_CODE+'frame" name="'+portalLetList[i].P_CODE+'frame" src="${ctx}'+portalLetList[i].P_URL+'" style="width:100%;height: '+portalLetList[i].P_HEIGHT+' " frameborder="0" ></iframe>',
						height:	portalLetList[i].P_HEIGHT,
						closable:true,
						collapsible:true
					});
					
						
					$('#pp').portal('add', {
						panel:p,
						columnIndex:i //所在列数，当该列已经存在数据时，将会在下一行的该列创建数据
					});
				}
				$('#pp').portal('resize');
			}
		}
		
		function remove(){
			$('#pp').portal('remove',$('#pgrid'));
			$('#pp').portal('resize');
		}
		
		
	</script>
	<script type="text/javascript">
		$(function(){
			$('#cc').combobox({
			    valueField:'value',
			    textField:'text',
			    groupField:'group',
			    data: [
					{
						value: '50%,50%',
						text: '50%,50%',
						group:'两列'
					},{
						value: '20%,80%',
						text: '20%,80%',
						group:'两列'
					},{
						value: '40%,60%',
						text: '40%,60%',
						group:'两列'
					},
					{
						value: '20%,30%,50%',
						text: '20%,30%,50%',
						group:'三列'
					},
					{
						value: '40%,30%,30%',
						text: '40%,30%,30%',
						group:'三列'
					}
					
				],
				onSelect:function(record){
					//alert(record.value);
					$.ajax({
						url : "${ctx}/cc/portal/updateUserWidths.action",
						data : {'p_widths':record.value,'selType':record.group,'p_type':'ENT','user_id':user_id},
						async : false,
						type : 'POST',
						success : function(returnData){
							window.location.reload();
						}
					});
				}
			});
			
			$('#cc').combobox("setValue",p_width);//设置默认值
		})
		$(function(){
			var ppHeight = $(document.body).height()-30;
			$("#pp").css("height",ppHeight);
			
			$(".panel-tool-close").bind("click",function(){
				alert();
				var portaletId = $(this).parent().parent().next().attr("id");
				var res=  IntoActionByUrl("${ctx}/cc/user/userClosePortalet.action?USER_ID="+user_id+"&pId="+portaletId);
			    if(res[0].result=="success"){
			        //window.location.reload();
			     }else{
			  	   $.messager.alert("错误",res[0].result)
			     }
			})
		});
		
		function showPortalet(){
			var currFrameId =  window.frameElement.id;
			createSimpleWindow("selPortaletWindow","选择Portalet","${ctx}/cc/portal/selPortaletEnt.action?currFrameId="+currFrameId+"&USER_ID="+user_id+"&p_type=ENT", 850, 500);
		}
	</script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="overflow: hidden;">
		<div style="margin-top:10px;text-align:right;">
			<span style="margin-right:15px;"><a href="javaScript:showPortalet()" style="text-decoration: underline;">分配</a></span>
			<input id="cc" name="dept" value="三列">
		</div>
		
		<div id="pp" style="position:relative;">
		</div>
	</div>
</body>
</html>
