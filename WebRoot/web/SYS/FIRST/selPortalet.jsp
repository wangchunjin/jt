<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
 		 var userId = "${param.USER_ID}";	
         $(function(){ 
        	$('#SysPortalTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                queryParams:{USER_ID:userId,p_type:'PRO'},
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
                idField:'P_ID',  
				url: '${ctx}/cc/portal/userPortalet.action',
				columns:[[
					{field:'P_CODE',title:'代码',width:0.2,align:'center' ,sortable:true,
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
					{field:'P_TITLE',title:'名称',width:0.2,align:'center' ,sortable:true,
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
					{field:'SELECT',title:'选择',width:0.1,align:'center',
				    	formatter:function(value,rowData,index){
				    		
							var checked ="";
							if(rowData.IS_SHOW == "0"){
								checked ="checked";
							}

				    	    return "<input type='checkbox' name='selectbox' "+checked+" value='"+rowData.P_ID+"' >"; 
				    	}
					},
					{field:'kz',title:'',width:0.5}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
               onClickRow:function(rowIndex, node){
				//var where = node.USER_ID+";"+node.PROFILE_ID;
				//parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
				
			});
        	 
		  	});
			
         	function userSelPortalet(obj){
         		var userId = "${param.USER_ID}";
         		
         		var pIds = "";
         		$("input:checkbox[name='selectbox']:checked").each(function(i){
         			pIds += isStringNull(pIds)?","+$(this).val():$(this).val();
         		});
         		var currFrameId = "${param.currFrameId}";
         		var res=  IntoActionByUrl("${ctx}/cc/portal/userSelPortalet.action?USER_ID="+userId+"&pIds="+pIds+"&p_type=PRO");
			    if(res[0].result=="success"){
			    	GetIndexFrame(currFrameId).window.location.reload();
			    	parent.close_win('selPortaletWindow');
			     }else{
			  	   $.messager.alert("错误",res[0].result)
			     }
         	}
        </script>
	</head>
	<body>
		<div class="page_content" style="bottom:40px;">
			<table id="SysPortalTable" class="TreeClass" ></table>
		</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="userSelPortalet();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('selPortaletWindow');">取消<b></b></a> 	 			
		</div>
   </body>

</html>