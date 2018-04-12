<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>
function a(){
	//<hxl
	var logon_value='${sessionScope.dlogin}';//时效
	 var userid_hxl='${sessionScope.UserInfo.userId}';//用户id
	 var r_hxl = GetXmlBySql("select logon_value from cm_users where USER_ID='"+userid_hxl+"'");
	 //判断登录用户session 时效
	 if(r_hxl[0].logon_value!=logon_value){
		 parent.parent.parent.location='<c:url value="/logout.action"/>';
	 }
	 //>hxl
	 
	var currFrameId =  parent.frameElement.id;
	var rows =$('#CarTable').datagrid('getSelections');			
	
	if(rows.length==0){
		parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要查看的用户！");
		return;
	}
	createSimpleWindow("eidtWindow","用户信息展示","${ctx}/clientinfo/tclientinfo/findById.action?id="+rows[0].client_id+"&currFrameId="+currFrameId, 1200,600);
	
}
         $(function(){
        	 
        	//<hxl
        		var logon_value='${sessionScope.dlogin}';//时效
        		 var userid_hxl='${sessionScope.UserInfo.userId}';//用户id
        		 var r_hxl = GetXmlBySql("select logon_value from cm_users where USER_ID='"+userid_hxl+"'");
        		 //判断登录用户session 时效
        		 if(r_hxl[0].logon_value!=logon_value){
        			 parent.parent.parent.location='<c:url value="/logout.action"/>';
        		 }
        		 //>hxl
        	 
        	
        	 
        	 var isdel = '${param.isdel}';
        	 var mobile='${param.mobile}';
        	
        	 
         	$('#CarTable').datagrid({
         	remoteSort:false,
                fitColumns:true,
                striped:true,                 
                selectOnCheck:false,
                autoRowHeight:true,
                checkOnSelect:true,
                singleSelect:true,
                remoteSort:false,                
                rownumbers:false,
                pagination: true,
                pageSize: 15,
                pageList: [15,30,45],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/clientinfo/tclientinfo/findAllClientinfo.action?isdel="+isdel+"&mobile="+mobile,        
				onDblClickRow:function() {
// 					双击某行触发查看事件
                	a();
            	}, 
				
				columns:[[
					{field:'client_id',checkbox:true},
					
					{field:'name',title:'姓名',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'mobile',title:'手机号',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'idcard',title:'身份证号',width:0.1,align:'center' ,sortable:true,
						
					},
					{field:'gender',title:'性别',width:0.1,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(value==0)
		                   		return "女";
		                   	if(value==1)
		                   		return "男";
		                   	
		                   	}
						
					},
					{field:'createtime',title:'创建时间',width:0.05,align:'center' ,sortable:true,
						
					}
					
					
     				]],
						 onLoadSuccess: function(data){
	 			 	   		$(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                		$(this).attr("title",$(this).html());
			                });
						 },
						 onClickRow:function(rowIndex, node){
		                	var where = node.USERID;
							parent.parent.document.all('FourFrame').src = parent.parent.ThreeFrame.getLabelUrl(where);
		                }
// 						 , rowStyler:function(index,row){   //修改easyui 行的样式
// 		                    if (row.value=="15"){   
// 		                        return 'background-color:pink;';   
// 		                    }  
// 		                    if (row.value=="14"){   
// 		                        return 'background-color:yellow;';   
// 		                    }  
// 		                }   
				});
		  	});
        </script>
</head>
<body>
	<div class="page_content">
		<table id="CarTable" class="TreeClass"></table>
	</div>
</body>
</html>