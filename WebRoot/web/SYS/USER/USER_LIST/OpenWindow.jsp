<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>部门树</title>
			
		<script>
  
        $(function(){ 
        	$('#SysUserTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,     
                singleSelect:true,
				url: '${ctx}/sys/user/SysUserTable.action?where=0',
				columns:[[
					{field:'USER_NAME',title:'用户名',width:0.2,align:'left' ,sortable:true},
					{field:'ACTUAL_NAME',title:'姓名 ',width:0.3,align:'left' ,sortable:true},
					{field:'USER_ID',title:'USER_ID',width:0.3,align:'left' ,sortable:true ,hidden:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
               onClickRow:function(rowIndex, node){
				var where = node.USER_ID+";"+node.PROFILE_ID;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
				
			});
		  	});
			
		function AddRecord(){
			var column_id = "${param.COLUMN_ID}";
			var node = $('#SysUserTable').datagrid('getSelected');
			if(isNull(node)){
					opener.document.getElementById(column_id).value =    node.USER_ID;  
	                opener.document.getElementById(column_id+"_NAME").value = node.ACTUAL_NAME;
	                 opener.$("#labelBottomDiv").css("display","");
	                window.close();
			}else{
				alert("请选择节点!")
			}
		}	  
			
		 function SetChange(){
			 var typ ="0";  
	    	 var whereSql="";
	    	 var key = $("#key").val(); 
	    	 if(typ!="2"){
	    		 whereSql = typ;
	    	 }
	    	   
	    	$('#SysUserTable').datagrid('clearSelections');
	    	$('#SysUserTable').datagrid('load',{ 
	    		   where: whereSql,
	    		   key:key
	    		});
		 }

        </script>
	</head>
	<body>
		<div style="margin-top: 3px;padding-left: 20px;">工号或姓名关键字：<input style="width: 200px;color: #999" type="text" id="key" onfocus="infocus(this);" value=""/>
            <input type="button" id="search" onclick="SetChange()" value="搜索"/> </div>
		<div class="page_content" style="top:30px; bottom: 40px;">
		<table id="SysUserTable" class="TreeClass" ></table>
		</div>
		<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
	</body>

</html>