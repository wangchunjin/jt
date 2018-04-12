<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>用户列表</title>
			
		<script>
		 
 
        
        $(function(){ 
        	 var projId =  $("#projId").val() ;
 
        	$('#UserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true, 
                singleSelect:true,
                url: '${ctx}/kh/kh_sfxm_public/UserList.action?opType=Add',
				columns:[[
                    {field:'USER_ID',title:'USER_ID',width:0.1,align:'left' ,sortable:true,hidden:true},
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
					{field:'ACTUAL_NAME',title:'姓名',width:0.1,align:'left' ,sortable:true,
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
	                    });
 				 }
  	 
		  	});
		 });
			  function AddRecord(){
			 
				 var projId =  $("#projId").val() ;
		     	 var obj =  $('#UserTable').datagrid("getSelected");
		     	 if(!isNull(obj)){
		     		 $.messager.alert("错误","请选择总监！");
		     		 return;
		     	 }
		     	  
 
    				var res =   IntoActionByUrl("${ctx}/hr/proj_work/AddWork.action","ids="+obj.USER_ID+"&projId="+projId);
    				if(res[0].result=="success"){	
    				var sql = "UPDATE CM_USERPROF SET PROFILE_ID=(select PROFILE_ID from cm_proj_role where CODE_ID='0365E3C70D274FAC860A77678A2C2ABE') WHERE USER_ID='"+obj.USER_ID+"' AND PROJ_ID='"+projId+"' ";
		    	    var  res=GetXmlBySql(sql);
   				    sql = "UPDATE CM_PROJ_WORK SET ROLE= (SELECT CODE_NAME FROM CM_CODE WHERE ID='0365E3C70D274FAC860A77678A2C2ABE')  WHERE USER_ID='"+obj.USER_ID+"' AND PROJ_ID='"+projId+"' ";
	    	        res=GetXmlBySql(sql);
	    	        parent.OneFrame.$("#projRole").val(obj.USER_ID);
                    }
                    parent.OneFrame.nextstep('2',projId);
 			  }
 
		 
				 function SetChange(){ 
			    	 var key = $("#key").val();  
			    	   
			    	$('#UserTable').datagrid('clearSelections');
			    	$('#UserTable').datagrid('load',{  
			    		   key:key
			    		});
				 }

        </script>
	</head>
	<body>
			<div style="margin-top: 3px;padding-left: 20px;">工号或姓名关键字：<input style="width: 200px;" type="text" id="key" onfocus="infocus(this);" value=""/>
            <input type="button" id="search" onclick="SetChange()" value="搜索"/> </div>
		<div   class="page_content" style="top:30px;bottom: 40px;">
		<table id="UserTable" class="TreeClass" ></table>
		</div>
					<div class="BottomDiv">
					<a href="###" class="btn_01" onclick="AddRecord();">下一步<b></b></a> 		
			</div>
		<input type="hidden" id="projId" value="${param.PROJ_ID}"/> 
	</body>

</html>