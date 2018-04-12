<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>关注点</title>
			
		<script>
		 
 
         $(function(){ 
        	 var Startdate = "${param.Startdate}";
        	 var Enddate = "${param.Enddate}";
        	$('#MajorTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,   
                singleSelect:true,           
				url: "${ctx}/sys/first/GetMajor.action?Startdate="+Startdate+"&Enddate="+Enddate,
				columns:[[
					{field:'PLAN_SHORT_NAME',title:'作业代码',width:0.2,align:'left' ,sortable:true,
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
					{field:'PLAN_NAME',title:'作业名称',width:0.4,align:'left' ,sortable:true,
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
					{field:'DOC_COUNT',title:'文档个数 ',width:0.1,align:'center' ,sortable:true,
					  formatter: function(value,row,index){
 					    	return "<a href='#' style='color:blue;' onclick=OpenDocDetail('"+row.PLAN_ID+"','"+row.MODULE_CODE+"')>"+value+"</a>";
					    
					    }
					}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
               onClickRow:function(rowIndex, node){
				var where = node.PROFILE_ID+";"+node.PROF_TYPE;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
				
			});
        	 
		  	});
			 function OpenDocDetail(planId,moduleCode){
				 if(moduleCode=="DS_PLAN"){
					  parent.parent.addTab('DS_GTORDER','建设程序','web/DS/DS_PLAN/FS.jsp?openId='+planId);

				 }else if(moduleCode=="SM_PLAN"){
					  parent.parent.addTab('SM_SWBS','质量安全分解','web/SM/SM_PLAN/FS.jsp?openId='+planId);
		
				 }else if(moduleCode=="SM_TEST"){
					  parent.parent.addTab('CC_CLAIM','合同造价工作分解','web/CC/SM_TEST/FS.jsp?openId='+planId);

				 }else{
					  parent.parent.addTab('QC_QWBS','进度工作分解','web/JD/SYS_PLAN/FS.jsp?openId='+planId);

				 }
			 }
			 
			function getContentByDate(){
				var Startdate  = $("#Startdate").val();
				var Enddate = $("#Enddate").val();
				var src="${ctx}/web/SYS/FIRST/major/ProjectTask.jsp?Startdate="+Startdate+"&Enddate="+Enddate;
				window.location.href=src;
				//document.all('frame1').src=src;
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
		            <td height="30px;" style="padding-top:2px;" >&nbsp;&nbsp;&nbsp;<input type="button" value="当天" style="width: 80" onclick="getContentByQuickDate('day')">&nbsp;<input type="button" value="当月" style="width: 60" onclick="getContentByQuickDate('month')">&nbsp;<input type="button" value="当年" style="width: 60" onclick="getContentByQuickDate('year')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
		                                         时间段：<input  name="Startdate" id="Startdate" style="width: 90px;"
		                        	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'Enddate\')}'})" value="${param.Startdate}">-
		                        	<input  name="Enddate" id="Enddate"  style="width: 90px;"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'Startdate\')}'})" value="${param.Enddate}">
			                  <input type="button" value="查询" style="width: 60" onclick="getContentByDate()">	             	            
		            </td>
		        </tr>
	         </table>
		</div>
		<div   class="page_content"  style="left: 0;right: 0;margin-top: 35px;">
			<table id="MajorTable" class="TreeClass" ></table>
		</div>
		
   </body>

</html>
