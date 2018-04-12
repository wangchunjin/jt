<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
  <%
	String telephone = request.getParameter("telephone");
	request.setAttribute("telephone", telephone);
%>
<c:set var="telephone" value="${requestScope.telephone }"></c:set>
		<title>词典</title>
			
		<script>
		 function formattime(val){
			var year=parseInt(val.year)+1900;
			var month=(parseInt(val.month)+1);
			month=month>9?month:('0'+month);
			var date=parseInt(val.date);
			date=date>9?date:('0'+date);
			var hours=parseInt(val.hours);
			hours=hours>9?hours:('0'+hours);
			var minutes=parseInt(val.minutes);
			minutes=minutes>9?minutes:('0'+minutes);
			var seconds=parseInt(val.seconds);
			seconds=seconds>9?seconds:('0'+seconds);
			var time=year+'-'+month+'-'+date+' '+hours+':'+minutes+':'+seconds;
			return time;
		}
		 //获取当前时间
		 Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
          
         $(function(){
        	$('#NewUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,     
                singleSelect:true,
                autoRowHeight:true,
                selectOnCheck:false,	
                checkOnSelect:true,
                rownumbers:false,
                pagination: true,
                pageSize: 20,
                pageList: [10,20,30],
				url: '${ctx}/user/tuser/showNewUsers.action',
				/* onSelect:function(index,row){  
					alert(1);
				},   */
				columns:[[
					{field:'id',title:"编号",width:0.1,align:'center',sortable:true,hidden:true},
					{field:'realName',title:'姓名',width:0.2,align:'center' ,sortable:true,
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
					{field:'telephone',title:'手机号',width:0.1,align:'center' ,sortable:true,
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
					{field:'type',title:'类型',width:0.2,align:'center' ,sortable:true,
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
						},formatter:function(val){
							if(val==0||(val==""||val==null)){
								return "普通用户";
							}else{
								return "金牌顾问";
							}
						}
					},
					
					{field:'sex',title:'性别',width:0.2,align:'center' ,sortable:true,
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
					
					{field:'IdNo',title:'身份证号',width:0.2,align:'center' ,sortable:true,
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
					{field:'createtime',title:'注册时间',width:0.2,align:'center' ,sortable:true,
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
							return formattime(val);
						}
					},
					{field:'brithdate',title:'生日',width:0.2,align:'center' ,sortable:true,
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
					{field:'issub',title:'是否已生成预约单',width:0.2,align:'center' ,sortable:true,
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
								return "<c style='color:red;'>未生成</c>";
							else
								return "<c style='color:green;'>已生成</c>";
						}
					}
					]]
			});
		  	});
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="NewUserTable" class="TreeClass" ></table>
		</div>
   </body>

</html>