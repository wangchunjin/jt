<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" src="${ctx}/webResources/ECharts/js/echarts.js"></script>
	</head>
	<body>
		<!-- 项目类别图表 -->
		<div id="income"></div>
		<script type="text/javascript">
		    var clickCount=1; 
			var wid = $(document).width();
			var hei = $(document).height();
			document.getElementById('income').style.width=wid+"px";
			document.getElementById('income').style.height=hei+"px";
			var res = IntoActionByUrl("${ctx}/chart/employee/ShowProjChart.action?contractTitle="+encodeURI("未完工"));
			var nameResult = IntoActionByUrl("${ctx}/chart/employee/selectName.action");
			var dom = document.getElementById("income");
			var myChart = echarts.init(dom);
			var labelRight = {
			    normal: {
			        position: 'right'
			    }
			};
			option = null;
			option = {
				backgroundColor: '#FFFFFF',
			    title: {
			        text: '项目类别图表',
			        x : 'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a}{b} : {c} ({d}%)"
    			},
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: nameResult
   			 	},
			    series : [
			        {
			            name:'',
			            type: 'pie',
            			radius : '55%',
            			center: ['50%', '50%'],
			            label: {
			                normal: {
			                    show: true,
			                    formatter: '{b}',
			                    position: 'bottom'
			                }
			            },
			            data:res
			        }
			    ]
			};
			if (option && typeof option === "object") {
				var startTime = +new Date();
				myChart.setOption(option, true);
				var endTime = +new Date();
				var updateTime = endTime - startTime;
				console.log("Time used:", updateTime);
			}
			myChart.on('click', function (params){
				var codeBelongto = option.series[0].data[params.dataIndex].id;
				var res = IntoActionByUrl("${ctx}/chart/employee/SeachCount.action?codeBelongto="+codeBelongto);
				if(res.length>0){
					parent.OneFrame.$("#showProj").hide();
					var showundo1 = parent.OneFrame.document.getElementById("showundo1");
					var showdo1   = parent.OneFrame.document.getElementById("showdo1");
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
					var resName = IntoActionByUrl("${ctx}/chart/employee/ShowStageName.action?codeBelongto="+codeBelongto);//名称
	   				var res = IntoActionByUrl("${ctx}/chart/employee/ShowProjStage1.action"+where+"&codeBelongto="+codeBelongto);//List
	   				var stageName  = resName[0].result.split(",");
	   				option.series[0].data = res;
   					option.legend.data= stageName;
   					myChart.hideLoading();
					myChart.setOption(option, true);
   				}else{
   					parent.OneFrame.$("#showProj").hide();
					var showundo1 = parent.OneFrame.document.getElementById("showundo1");
					var showdo1   = parent.OneFrame.document.getElementById("showdo1");
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
   					$('#income').attr('id','CoNewTable');
   					$('#CoNewTable').datagrid({
		                remoteSort:false,
		                fitColumns:true,
		                striped:true,      
		                singleSelect:true,
		                rownumbers:true,//行号 
						url: "${ctx}/chart/employee/ShowDatagrid.action"+where+"&stageName="+params.name+"&clickCount="+clickCount+"&codeBelongto="+codeBelongto,		        
						columns:[[
								{field:'PROJ_SHORT_NAME',title:'代码',width:0.2,align:'left' ,sortable:true,
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
										},formatter: function(value,row,index){
											return "<img src='${ctx}/img/treegrid/project.gif'/>"+value;
										}
								},
								{field:'PROJ_NAME',title:'名称',width:0.3,align:'left' ,sortable:true,
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
								{field:'PROJ_STAGE',title:'项目类别',width:0.1,align:'center' ,sortable:true,
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
								{field:'PROJ_STAGE1',title:'项目子类',width:0.1,align:'center' ,sortable:true,
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
								{field:'PROJ_CMPT_PCT',title:'形象进度',width:0.15,align:'center' ,sortable:true,
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
								{field:'PROCEED_DATE',title:'开工日期',width:0.1,align:'center' ,sortable:true,
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
								{field:'COMPLETION_DATE',title:'完工日期',width:0.1,align:'center' ,sortable:true,
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
								}
		     				]],
								 onLoadSuccess: function(data){
			 			 	   		$(".page_content").find("div[class^='datagrid-cell']").each(function(i){
				                		$(this).attr("title",$(this).html());
					                })
								 },
				                onClickRow:function(rowIndex, node){
								}
						});
   				}
				clickCount++;
			});
			function openw4(){
				var showundo1 = parent.OneFrame.document.getElementById("showundo1");
				var showdo1   = parent.OneFrame.document.getElementById("showdo1");
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
				var res = IntoActionByUrl("${ctx}/chart/employee/ShowType.action"+where);
				option.series[0].data = res;
	       		myChart.hideLoading();
				myChart.setOption(option, true);
			}
		</script>
	</body>
</html>
