<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title></title>
<script>
function single(){
		//<hxl
		var logon_value='${sessionScope.dlogin}';//时效
		 var userid_hxl='${sessionScope.UserInfo.userId}';//用户id
		 var r_hxl = GetXmlBySql("select logon_value from cm_users where USER_ID='"+userid_hxl+"'");
		 //判断登录用户session 时效
		 if(r_hxl[0].logon_value!=logon_value){
			 alert("你被挤下线了,请保护好你的密码！");
			 parent.parent.parent.location='<c:url value="/logout.action"/>';
		 }
		 //>hxl
	}
function open_win(url, title, w, h) {
	single();
    var iWidth = w;
    var iHeight = h;
    var iTop = (window.screen.availHeight-30-iHeight)/2;          //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
    myWindow  = window.open(url, title, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
 } 
 function showbaidu(url){ 
	 single();
	 open_win(url,"链接","1000","600");
 }
function look(obj){		
	single();
    createSimpleWindow("lookWindow","公告文本查看","${ctx}/adv/tadv/findById.action?cid="+obj, 1000,600);
}
		
         $(function(){   
        	 single();
        	 
        	 
        	 var isdel = '${param.isdel}';       	 
         	$('#CarTable').datagrid({
         	remoteSort:false,
                fitColumns:true,
                striped:true,
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
                autoRowHeight:true,
                remoteSort:false,                
                rownumbers:false,
                pagination: true,
                pageSize: 10,
                pageList: [10,20,30],//?username="+encodeURI(title)+"&usertype="+isdel+"&personsource="+status+"&islisted="+xin
				url: "${ctx}/adv/tadv/findAllAdv.action?isdel="+isdel,        
				frozenColumns:[[
				]],
				
				columns:[[		
					{field:'id',checkbox:true},
// 					{field:'pic',title:'图片',width:0.2,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
// 		                   	return "<img id='imghead' height='70px' src ='${ctx}"+value+"' />";
// 		                   	}
// 					},
					{field:'title',title:'标题',width:0.2,align:'center' ,sortable:true,
						
					},
// 					{field:'type',title:'状态',width:0.2,align:'center' ,sortable:true,
// 						formatter:function(value,row,index){
// 		                   	if(value==2)
// 		                   		return "公告链接";
// 		                   	if(value==1)
// 		                   		return "公告文本";
		                   
// 		                   	}
					
// 					},
					{field:'contentandurl',title:'内容',width:0.2,align:'center' ,sortable:true,
						formatter:function(value,row,index){
		                   	if(row.type==2)
		                   		return "<a href='#' onclick=\"showbaidu('"+value+"')\">"+value+"</a>";
		                   	if(row.type==1)
		                   		return "<a onclick='look("+row.id+")'>"+row.content+"</a>";
		                   	if(row.type==3)
		                   		return "--";
		                   	if(row.type==4)
		                   		return "--";
		                   	}
					},
					{field:'createtime',title:'创建时间',width:0.2,align:'center' ,sortable:true,
						
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