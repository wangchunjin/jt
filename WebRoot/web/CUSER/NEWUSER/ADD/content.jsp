<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
  <%
	String gwid = request.getParameter("gwid");
	request.setAttribute("gwid", gwid);
%>
<c:set var="gwid" value="${requestScope.gwid }"></c:set>
		<title>楼盘信息</title>
			
		<script>
		 function formattime(val) {  
		    var year=parseInt(val.year)+1900;  
		    var month=(parseInt(val.month)+1);  
		    month=month>9?month:('0'+month);  
		    var date=parseInt(val.date);  
		    date=date>9?date:('0'+date);  
		    var hours=parseInt(val.hours);  
		    hours=hours>9?hours:('0'+hours);  
		    var minutes=parseInt(val.minutes);  
		    minutes=minutes>9?minutes:('0'+minutes);  
		    var seconds=parseInt(val.seconds);  
		    seconds=seconds>9?seconds:('0'+seconds);  
		    var time=year+'-'+month+'-'+date+' '+hours+':'+minutes+':'+seconds;  
        	return time;
    	}  
          
         $(function(){
        	$('#PProsaleTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                singleSelect:true,
                autoRowHeight:true,
                selectOnCheck:false,
                checkOnSelect:true,
                rownumbers:true,
                pagination: true,
                pageSize: 12,
                pageList: [10,12,20,25,30],
				url: '${ctx}/jp/sub/showProsale.action',
				onDblClickRow: function (index, row) {  
					//alert(row.id);
					window.opener.document.getElementById("loupanid").value=row.id;
					window.opener.document.getElementById("lpname").value=row.title;
					//window.opener.document.getElementById("province").value
					//parent.parent.close_win("showWindow");
					window.close();
				},  
				columns:[[
					{field:'id',title:"编号",width:0.1,align:'center',sortable:true,hidden:true},
					{field:'title',title:'楼盘名称',width:0.3,align:'center' ,sortable:true,
						sorter:function(param1,param2){
							if(!isStringNull(param1)){
								param1 = "";
							}
							if(!isStringNull(param2)){
								param2 = "";
							}
							/*排序localeCompare()方法他的实现时按照区域特性来的，
							如果在英语体系中，他的实现可能是按照字符串升序，如果在汉语中，他的实现则是按照首字母的拼音  */
							if(param1.localeCompare(param2)<0){
								return -1;
							}else{
								return 1;
							}
						}	
					},
					{field:'unit_price',title:'单价',width:0.2,align:'center' ,sortable:true,
						sorter:function(param1,param2){
							if(!isStringNull(param1)){
								param1 = "";
							}
							if(!isStringNull(param2)){
								param2 = "";
							}
							/*排序localeCompare()方法他的实现时按照区域特性来的，
							如果在英语体系中，他的实现可能是按照字符串升序，如果在汉语中，他的实现则是按照首字母的拼音  */
							if(param1.localeCompare(param2)<0){
								return -1;
							}else{
								return 1;
							}
						}	
					},
					{field:'selling_price',title:'起售价',width:0.2,align:'center' ,sortable:true,
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
					{field:'avg_price',title:'均价',width:0.2,align:'center' ,sortable:true,
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
					{field:'objective',title:'购房目的',width:0.2,align:'center' ,sortable:true,hidden:true,
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
					{field:'pic',title:'楼盘图片',width:0.3,align:'center' ,sortable:true,
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
						},formatter:function(val){
							if(val==""||val==null)
								return "<img style='height:50px;' src='${ctx}/upload/jiazai4.png'>";
							else
								return "<img style='height:50px;' src='${ctx}"+val+"'>";
						}
					},
					{field:'longitude',title:'经度',width:0.2,align:'center' ,sortable:true,
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
					{field:'latitude',title:'纬度',width:0.2,align:'center',sortable:true,
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
					{field:'provinceName',title:'省',width:0.2,align:'center',sortable:true,
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
					{field:'cityName',title:'市',width:0.2,align:'center' ,sortable:true,
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
					},{field:'districtName',title:'区',width:0.2,align:'center' ,sortable:true,
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
					},{field:'address',title:'地址',width:0.2,align:'center' ,sortable:true,
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
					},{field:'shouchangnum',title:'收藏量',width:0.2,align:'center' ,sortable:true,
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
					},{field:'clicks',title:'点击量',width:0.2,align:'center' ,sortable:true,
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
					},{field:'forwards',title:'转发量',width:0.2,align:'center' ,sortable:true,
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
					},{field:'point',title:'点赞量',width:0.2,align:'center' ,sortable:true,
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
					},{field:'zx',title:'装修状态',width:0.2,align:'center' ,sortable:true,
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
						},formatter:function(val){
							if(val==1)
								return "精装";
							else if(val==2)
								return "毛坯";
							else if(val==3)
								return "简装";
						}
					},{field:'status',title:'楼盘状态',width:0.2,align:'center' ,sortable:true,
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
						},formatter:function(val){
							if(val==1)
								return "在售";
							else if(val==2)
								return "待售";
							else if(val==3)
								return "售罄";
						}
					},{field:'isgift',title:'是否有礼物',width:0.2,align:'center' ,sortable:true,
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
						},formatter:function(val){
							if(val==0)
								return "无礼物";
							else if(val==1)
								return "有礼物";
						}
					}
					]]
			});
        	//省市区三级联动
        	showProvince();
        	$("#shen").change(function(){
        		var pId = $(this).children("option:selected").val();
        		showCity(pId);
        	})
        	$("#shi").change(function(){
        		var cId = $(this).children("option:selected").val();
        		showDistrict(cId);
        	})
			});
        	
function showProvince(){
	$.ajax({
		type:"POST",
		url:"${ctx}/jp/sub/showProvince.action",
		dataType:"json",
		contentType:"application/json;charset=utf-8",
		success:function(result){
			$("#shen").text("");
			$("<option value=''>---请选择省---</option>").appendTo("#shen");
			for(var i = 0;i<result.rows.length;i++){
				$("<option value='"+result.rows[i].provinceId+"'>"+result.rows[i].provinceName+"</option>").appendTo("#shen");
			}
		}
	});
}
function showCity(pId){
	$.ajax({
		type:"POST",
		url:"${ctx}/jp/sub/showCity.action?pId="+pId,
		dataType:"json",
		contentType:"application/json;charset=utf-8",
		success:function(result){
			$("#shi").text("");
			$("<option value=''>---请选择市---</option>").appendTo("#shi");
			for(var i = 0;i<result.rows.length;i++){
				$("<option value='"+result.rows[i].cityId+"'>"+result.rows[i].cityName+"</option>").appendTo("#shi");
			}
		}
	});
}
function showDistrict(cId){
	$.ajax({
		type:"POST",
		url:"${ctx}/jp/sub/showDistrict.action?cId="+cId,
		dataType:"json",
		contentType:"application/json;charset=utf-8",
		success:function(result){
			$("#qu").text("");
			$("<option value=''>---请选择区---</option>").appendTo("#qu");
			for(var i = 0;i<result.rows.length;i++){
				$("<option value='"+result.rows[i].districtId+"'>"+result.rows[i].districtName+"</option>").appendTo("#qu");
			}
		}
	});
}

function SetChange(){ 
	 $('#PProsaleTable').datagrid('clearSelections');
	 $('#PProsaleTable').datagrid('load',{
		 title:$("#title").val(),
		 type:$("#type option:selected").val(),
		 minPrice:$("#minPrice").val(),
		 maxPrice:$("#maxPrice").val(),
		 zx:$("#zx option:selected").val(),
		 shen:$("#shen").val(),
		 shi:$("#shi").val(),
		 qu:$("#qu").val()
	 });
}
		  	
	
        </script>
	</head>
	<body>
		<div   class="page_content" >
		 <table width="100%">
	       <tr>
			   <td>
			   		&nbsp;&nbsp;楼盘名称：<input class="label_text" id="title" style="width:130px;" />&nbsp;&nbsp;
			       <select class="label_text" id="type" style="width:80px;" name="type">
			       		<option value="">--请选择--</option>
			       		<option value="1">单价</option>
			       		<option value="2">起售价</option>
			       		<option value="3">均价</option>
			       </select>&nbsp;
			       <input class="label_text" style="width:100px;" name="minPrice" id="minPrice">-<input class="label_text" style="width:100px;" name="maxPrice" id="maxPrice">&nbsp;&nbsp;
			       装修状态：<select class="label_text" id="zx" style="width:100px;" name="zx">
			       		<option value="">--请选择--</option>
			       		<option value="1">精装</option>
			       		<option value="2">毛坯</option>
			       		<option value="3">简装</option>
			       </select>&nbsp;&nbsp;
			      省：<select class="label_text" id="shen" style="width:130px;" name="shen">
			      </select>
			      &nbsp;&nbsp;
			      市：<select class="label_text" id="shi" style="width:130px;" name="shi">
			      </select>
			      &nbsp;&nbsp;
			      区：<select class="label_text" id="qu" style="width:130px;" name="qu">
			      </select>
			      &nbsp;&nbsp;
			       <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="SetChange()" ></epmis:button>
			       
			  </td>
	          <td width="*" align="right">
	             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
	          </td>
	          <td width="50">&nbsp;</td>
	       </tr>
	  </table>
		<table id="PProsaleTable" class="TreeClass" ></table>
		</div>
   </body>

</html>