<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
 
         $(function(){ 
        	 var cuid='${sessionScope.UserInfo.userId}';
        	 var result = GetXmlBySql("SELECT issub FROM cm_users WHERE user_id='"+cuid+"'");
        	 var issub=result[0].issub;
        	 
        	 var where ='${param.where}';
        	 var key ='${param.key}';
        	$('#SysUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,     
                singleSelect:true,
                autoRowHeight:true,
                selectOnCheck:false,
                checkOnSelect:true,
                queryParams:{where:where,key:key,cuid:cuid,issub:issub},
//                 rownumbers:true,//行号 
                idField:'USER_ID', 
				url: '${ctx}/sys/user/SysUserTable.action',
				
				columns:[[
					{field:'USER_ID',checkbox:true},
					{field:'USER_NAME',title:'用户名',width:0.1,align:'left' ,sortable:true,
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
					{field:'USER_PIC',title:'头像 ',width:0.1,align:'left' ,sortable:true,
						formatter:function(value,row,index){
		                   	return "<img width='100px' heigth='130px' src ='${ctx}"+value+"' />";
		                   	}
					},
// 					{field:'ENSEMBLE',title:"客户端&nbsp;<input type='checkbox' name='selectAll' onchange='SetAllValue(this)'   >" ,width:0.1,align:'center' ,sortable:true,
// 						formatter: function(value,row,index){
// 							if(value == "Y"){
// 								 return "<input type='checkbox' name='appCheckbox' checked  onchange='SetValue(this)' value='"+row.USER_ID+"'>";
// 							}else{
// 								 return "<input type='checkbox' name='appCheckbox'  onchange='SetValue(this)' value='"+row.USER_ID+"'>";
// 							}
// 						}						
// 					},
					 {field:'PROFILE_NAME',title:'全局安全配置',width:0.1,align:'left' ,sortable:true,
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
// 					{field:'ROLE_NAME',title:'权限角色',width:0.2,align:'left' ,sortable:true,
// 						sorter:function(param1,param2){
// 							if(!isStringNull(param1)){
// 								param1 = "";
// 							}
// 							if(!isStringNull(param2)){
// 								param2 = "";
// 							}
// 							if(param1.localeCompare(param2)<0){
// 								return -1;
// 							}else{
// 								return 1;
// 							}
// 						}	
// 					},
// 					{field:'USER_ID',title:'USER_ID',width:0.3,align:'left' ,sortable:true ,hidden:true},
// 					{field:'PROFILE_ID',title:'PROFILE_ID',width:0.3,align:'left' ,sortable:true ,hidden:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
 		                 
	                 });
		                
		               if( $("input[type='checkbox'][name='appCheckbox']:checked").length == $("input[type='checkbox'][name='appCheckbox']").length){
		            	   $("input[type='checkbox'][name='selectAll']").attr("checked",true);
		               }				
				 }
				,
               onClickRow:function(rowIndex, node){
				var where = node.USER_ID+";"+node.PROFILE_ID;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
				
			});
        	 
		  	});
			
         function SetAllValue(obj){
        	 var where ='${param.where}';
        	 var ensemble = "N";
        	 if(obj.checked){
        		 ensemble="Y";
        	 }
        	 var res = IntoActionByUrl("${ctx}/sys/user/SetAppUser.action?userId=&where="+where+"&ensemble="+ensemble);
        	 if(res[0].result !="success"){
        		
       		 $.messager.alert("错误",res[0].result,'',function(){
        		 if(obj.checked){
        			 obj.checked = false;
        		 }else{
        			 obj.checked = true;
        		 }
       		 });  
          
        	        		
        	 }else{
        		 $.messager.alert("成功","批量设置成功",'',function(){
           			 window.location.reload();
           		 });
        	 }
         }
         
         function SetValue(obj){
        	 var ensemble = "N";
        	 if(obj.checked){
        		 ensemble="Y";
        	 }
        	 var res = IntoActionByUrl("${ctx}/sys/user/SetAppUser.action?userId="+obj.value+"&where=&ensemble="+ensemble);
        	 if(res[0].result !="success"){
        		 $.messager.alert("错误",res[0].result);
        		 if(obj.checked){
        			 obj.checked = false;
        		 }else{
        			 obj.checked = true;
        		 }
        	//	 window.location.reload();
        	 }else{
        		 $.messager.alert("成功","设置成功");
        	 }
         }
        </script>
	</head>
	<body>
		<div   class="page_content" >
		<table id="SysUserTable" class="TreeClass" ></table>
		</div>
   </body>

</html>