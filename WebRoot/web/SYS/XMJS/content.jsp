<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
  
			$(function(){
				var projStage = parent.TwoFrame.$('#projStageSel').combo('getValue');//项目类别
				var projStage1 = parent.TwoFrame.$('#projStageChaild').combo('getValue');//项目子类
				var vnmtId = parent.TwoFrame.$('#vnmtId').combo('getValue');//建设单位
				var stage = parent.TwoFrame.$('#stage').combo('getValue');//省
				var city = parent.TwoFrame.$('#city').combo('getValue');//市
				var area = parent.TwoFrame.$('#area').combo('getValue');//区
				var projCmptPct = parent.TwoFrame.$('#projCmptPct').combo('getValue');//形象进度
			  	var code = parent.TwoFrame.$("#code").val();//代码
				var name = parent.TwoFrame.$("#name").val();//名称
				var contractTitle = parent.TwoFrame.$("#contractTitle").val();//项目状态
				var proceedDate = parent.TwoFrame.$("#proceedDate").val();//开工日期（头）
				var proceedDate1 = parent.TwoFrame.$("#proceedDate1").val();//开工日期（尾）
				var completionDate = parent.TwoFrame.$("#completionDate").val();//完工日期（头）
				var completionDate1 = parent.TwoFrame.$("#completionDate1").val();//完工日期（尾）
				var siteLocation = parent.TwoFrame.$("#siteLocation").val();//详细地址
				var supervisor = parent.TwoFrame.$("#supervisor").val();//监理项目负责人
				var supervisorPhone = parent.TwoFrame.$("#supervisorPhone").val();//监理项目负责人电话
				var radius = parent.TwoFrame.$("#radius").val();//签到有效半径
				var PROJ_FORM = parent.TwoFrame.$("#PROJ_FORM").val();//工程结构
				var PROJ_QUALITY_DEST = parent.TwoFrame.$("#PROJ_QUALITY_DEST").val();//工程质量目标
				var GROSS_INVEST = parent.TwoFrame.$("#GROSS_INVEST").val();//总投资（万元）
				var SCALE = parent.TwoFrame.$("#SCALE").val();//规模
				var PART_PROJ_INVEST = parent.TwoFrame.$("#PART_PROJ_INVEST").val();//监理费用（万元）
				var CULTURE_SITE = parent.TwoFrame.$("#CULTURE_SITE").val();//安全文明工地目标
				var projInfo = parent.TwoFrame.$("#projInfo").val();//EPS
				var docInfo = parent.TwoFrame.$("#docInfo").val();//获奖情况
				var winnerDate = parent.TwoFrame.$("#winnerDate").val();//获奖时间（头）
				var winnerDate1 = parent.TwoFrame.$("#winnerDate1").val();//获奖时间（尾）
				var mapResult = {"code":code,"name":name,"projCmptPct":projCmptPct,"contractTitle":contractTitle,"proceedDate":proceedDate
			,"proceedDate1":proceedDate1,"completionDate":completionDate,"completionDate1":completionDate1,"stage":stage,"city":city
			,"area":area,"siteLocation":siteLocation,"supervisor":supervisor,"supervisorPhone":supervisorPhone,"radius":radius
			,"vnmtId":vnmtId,"projStage":projStage,"projStage1":projStage1,"PROJ_FORM":PROJ_FORM,"PROJ_QUALITY_DEST":PROJ_QUALITY_DEST
			,"GROSS_INVEST":GROSS_INVEST,"SCALE":SCALE,"PART_PROJ_INVEST":PART_PROJ_INVEST,"CULTURE_SITE":CULTURE_SITE,"projInfo":projInfo
			,"docInfo":docInfo,"winnerDate":winnerDate,"winnerDate1":winnerDate1};
			var array = [];
			var columns = [];
			array.push({field:'PROJ_SHORT_NAME',title:'代码',width:180,align:'left',sortable:true,
								formatter: function(value,row,index){
									return "<img src='${ctx}/img/treegrid/project.gif'/>"+value;
								},
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
							});
			array.push({field:'parentPathName',title:'EPS',width:300,align:'left',sortable:true,
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
							}});
			array.push({field:'PROJ_NAME',title:'名称',width:350,sortable:true,
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
							}});
			array.push({field:'SUPERVISOR',title:'监理项目负责人',width:100,align:'center',sortable:true,
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
							}});
			array.push({field:'SUPERVISOR_PHONE',title:'监理项目负责人电话',width:120,align:'center',sortable:true,
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
							}});
			array.push({field:'PROJ_STAGE',title:'项目类别',width:150,align:'center',sortable:true,
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
							}});
			array.push({field:'PROJ_STAGE1',title:'项目子类',width:150,align:'center',sortable:true,
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
							}});
			array.push({field:'PROJ_CMPT_PCT',title:'形象进度',width:150,align:'center',sortable:true,
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
							}});
			array.push({field:'CONTRACT_TITLE',title:'项目状态',width:100,align:'center',sortable:true,
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
							}});
			array.push({field:'PROCEED_DATE',title:'开工日期',width:100,align:'center',sortable:true,
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
							}});
			array.push({field:'COMPLETION_DATE',title:'完工日期',width:100,align:'center',sortable:true,
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
							}});
			array.push({field:'cityOne',title:'工程地点(省)',width:100,align:'center',sortable:true,
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
							}});
			array.push({field:'cityTwo',title:'工程地点(市)',width:100,align:'center',sortable:true,
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
							}});
			array.push({field:'cityThree',title:'工程地点(区)',width:100,align:'center',sortable:true,
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
							}});
			array.push({field:'SITE_LOCATION',title:'详细地址',width:350,align:'left',sortable:true,
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
							}});
			array.push({field:'WINNER',title:'奖项名称',width:200,align:'left',sortable:true,
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
							}});
			var  res=GetXmlBySql("SELECT * FROM CM_COLUMN WHERE ENABLED='1' ORDER BY SEQ_NUM");
	    	if(res.length>0){
	    		for(var i=0;i<res.length;i++){
	    			var columnCode = res[i].PROJ_STAGE+res[i].COLUMN_CODE;
	    			array.push({field:columnCode,title:res[i].COLUMN_NAME,width:180,align:'left',sortable:true,
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
							}});
	    		}
	    	}
			array.push({field:'WID',title:'WID',width:120,align:'center',hidden:true});
			array.push({field:'PARENT_PROJ_ID',title:'PARENT_PROJ_ID',width:80,align:'center',hidden:true});
			array.push({field:'PROJ_NODE_FLAG',title:'PROJ_NODE_FLAG',width:80,align:'center',hidden:true});
			columns.push(array);
			var last=JSON.stringify(mapResult);
	     	   $('#CcHtTable').datagrid({
	                remoteSort:false,
	                fitColumns:false,
	                striped:true,   
	                singleSelect:true,
	                pagination:true,//分页控件 
	                rownumbers:true,//行号 
	                pageSize: 20,//每页显示的记录条数，默认为10 
			        pageList: [20,100,10000],//可以设置每页记录条数的列表 
	                queryParams: {
	                	"mapResult":last
	            	},
	                url: '${ctx}/xmjs/proj/SearchForm.action',
					columns:columns,
					 onLoadSuccess: function(data){
		 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
			               	 $(this).attr("title",$(this).html());
		             		})
		             	parent.OneFrame.result = res;
		             	parent.OneFrame.rows = $("#CcHtTable").datagrid('getRows');
					 },
					 onDblClickRow:function(rowIndex, row){
						 toDbUrl(row);
					}
					
				});
	        	 
			 });
        </script>
	</head>
	<body>
		<div class="page_content" style="">
			<table id="CcHtTable" class="TreeClass"></table>
		</div>
	</body>

</html>