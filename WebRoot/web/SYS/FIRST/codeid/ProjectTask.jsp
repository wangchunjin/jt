<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>关注点</title>
  <script type="text/javascript" src="${ctx}/webResources/js/easyui1.5/jquery.easyui.min.js"></script>  
   <link rel="stylesheet" type="text/css" href="${ctx}/webResources/js/easyui1.5/easyui.css">
			
		<script>
		 var Startdate = "${param.Startdate}";
    	 var Enddate = "${param.Enddate}";
    	 var Key = "${param.Key}";
    	 var CodeId = "${param.CodeId}";
         $(function(){         	 
        	 $("#showcombobox").combobox({

				  onChange: function (n,o) { 
		 		  if($('#showcombobox').combobox('getValue')!=$('#showcombobox').combobox('getText') || !isStringNull($('#showcombobox').combobox('getValue'))){
				       
		 			 $("#CodeId").val($('#showcombobox').combobox('getValue'));
				  }
		  
				  }
				 });
        	 
        	$('#CodeIdTable').treegrid({
        		url: "${ctx}/ds/ds_ldzl/ProjPlanTaskCodeId.action?parentId=0&Startdate="+Startdate+"&Enddate="+Enddate+"&CodeId="+CodeId+"&Key="+Key+"&NodeType=PROJ",
 
				
        	    idField:'ID',
				treeField:'PROJ_NAME',
				fitColumns:true,
				striped: true,  
			     pagination:true,//分页控件 
          //      rownumbers:true,//行号 
                pageSize: 20,//每页显示的记录条数，默认为10 
		        pageList: [20,100,100000],//可以设置每页记录条数的列表 
				columns:[[
					{field:'PROJ_NAME',title:'项目代码--名称',width:200,align:'left' ,
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
					{field:'CODE_NAME',title:'业务代码-名称',width:200,align:'left' ,
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
					{field:'PLAN_NAME',title:'作业代码--名称',width:200,align:'left' ,
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
					{field:'DOC_COUNT',title:'文档 个数',width:200,align:'center' ,
					  formatter: function(value,row,index){
						   	return "<a href='#' style='color:blue;' onclick=OpenDocDetail('"+row.ID+"','"+row.NODE_TYPE+"')>"+value+"</a>";					    
					    }
					},
					{field:'NODE_TYPE',title:'节点类型',width:120,align:'center',hidden:true},
					
					]],
					onBeforeExpand:function(node){ 
						$('#CodeIdTable').treegrid('options').url = "${ctx}/ds/ds_ldzl/ProjPlanTaskCodeIdChild.action?parentId="+node.ID+"&Startdate="+Startdate+"&Enddate="+Enddate+"&CodeId="+CodeId+"&Key="+Key+"&NodeType="+node.NODE_TYPE;
					}				
			});       	
		  	});
			 function OpenDocDetail(id,nodeType){				  
				 createSimpleWindow("ShowPic","文档列表","${ctx}/web/SYS/FIRST/codeid/DocDetail.jsp?Id="+id+"&NodeType="+nodeType+"&Startdate="+Startdate+"&Enddate="+Enddate+"&CodeId="+CodeId, 950, 570);				 
			 }
			 
			function getContentByDate(){
				var Startdate  = $("#Startdate").val();
				var Enddate = $("#Enddate").val();
				var CodeId =  $("#CodeId").val();
				var Key = $("#Key").val(); 
				var src="${ctx}/web/SYS/FIRST/codeid/ProjectTask.jsp?Startdate="+Startdate+"&Enddate="+Enddate+"&CodeId="+CodeId+"&Key="+encodeURI(Key); 
				window.location.href=src;
			}
			function SetDateToChar(day){
			    var Year  = day.getFullYear();
			    var Month = day.getMonth()+1;
			    var Day   = day.getDate();
			      var CurrentDate = "";
			      CurrentDate += Year + "-";
			    if (Month >= 10 )
			    {
			        CurrentDate += Month + "-";
			    }
			    else
			    {
			       CurrentDate += "0" + Month + "-";
			    }
			    if (Day >= 10 )
			    {
			        CurrentDate += Day ;
			    }
			    else
			    {
			        CurrentDate += "0" + Day ;
			    }
			    return CurrentDate;
			    
			  }
			  function getCurrentMonthFirst(){
				 var date=new Date();
				 date.setDate(1);
				 return   SetDateToChar(date) ;
			}
			  
			  function getCurrentMonthLast(){
				 var date=new Date();
				 var currentMonth=date.getMonth();
				 var nextMonth=++currentMonth;
				 var nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
				 var oneDay=1000*60*60*24;
				 var d = new Date(nextMonthFirstDay-oneDay);
				 return   SetDateToChar(d) ;
			}
		  function getContentByQuickDate(type){
			  if(type=="day"){
				 var d = new Date();
				  $("#Startdate").val(SetDateToChar(d));
				  $("#Enddate").val(SetDateToChar(d));
			  }else if(type=="month"){
				  $("#Startdate").val(getCurrentMonthFirst());
				  $("#Enddate").val(getCurrentMonthLast());

			  }else if(type=="year"){
				  var d = new Date();
				  $("#Startdate").val(d.getFullYear()+"-01-01");
				  $("#Enddate").val(d.getFullYear()+"-12-31");
			  } 
			  getContentByDate();
		  }
        </script>
	</head>
	<body>
		<div style="">
			<table style="width: 100%; height:100%;border-spacing: 0;" cellspacing="0" border="0"  cellpadding="0">
		       <tr>
			       <td height="30px;" style="padding-top:2px;" width="232px" >
			       &nbsp;&nbsp; 项目名称:<input type="text" id="Key" style="width: 90px;" value="${param.Key}">
			        &nbsp;&nbsp;&nbsp;&nbsp; 业务代码:
			        </td>
			        <td height="30px;" style="padding-top:2px;" width="*" > <!--  -->
							 <div class="easyui-panel" style="width:100%;position: absolute;left: 235px;top: 5px;width: 150px;">
					 
					              <input class="easyui-combobox" id="showcombobox" style="width:100%;" data-options="
					                    url:'${ctx}/sys/code/GetCodeJson.action?CodeType=PLAN_CODE,PLAN_CODE_HZ&Value=ID&ShowTest=NAME&Test=CODE_SHORT_NAME,CODE_NAME&Belongto=',
					                    method:'get',
					                    valueField:'ID',
					                    textField:'NAME',
					                    panelHeight:'200',
					                    iconWidth:22, 					                    
					                    height:'20', 
					                    selectOnNavigation:true,
					                    value:'${param.CodeId}',
					                    labelPosition: 'top'
					                    ">
					        </div>
			       </td>
		       </tr>
		        <tr>		        
		            <td height="30px;" colspan="2"  style="padding-top:2px;" >
		            
		            &nbsp;&nbsp;&nbsp;<input type="button" value="当天" style="width: 80" onclick="getContentByQuickDate('day')">&nbsp;<input type="button" value="当月" style="width: 60" onclick="getContentByQuickDate('month')">&nbsp;<input type="button" value="当年" style="width: 60" onclick="getContentByQuickDate('year')">&nbsp;&nbsp;&nbsp;	
		                                         时间段：<input  name="Startdate" id="Startdate" style="width: 90px;"
		                        	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'Enddate\')}'})" value="${param.Startdate}">-
		                        	<input  name="Enddate" id="Enddate"  style="width: 90px;"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'Startdate\')}'})" value="${param.Enddate}">
			                  <input type="button" value="查询" style="width: 60" onclick="getContentByDate()">	             	            
		            </td>
		        </tr>
	         </table>
	         <input type="hidden" id="CodeId" value="${param.CodeId}">
		</div>
		<div   class="page_content"  style="left: 0;right: 0;margin-top: 70px;">
			<table id="CodeIdTable" class="TreeClass" ></table> 
		</div>
		
   </body>

</html>
