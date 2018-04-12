<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>词典</title>
			
		<script>
		 
       function openDoc(url,id){
    	   url = "${ctx}"+url;
    	  	 var width = "1000";
             if($(parent.window).width()<1000){
            	 width = $(parent.window).width()-100;
             }
    		 createSimpleWindow("viewDocWindow","查看文档","${ctx}/web/SYS/STRUCT/view.jsp?url="+url+"&id="+id, width, $(parent.window).height());
 
       }
         $(function(){ 
        	 var swbs_id = '${param.swbs_id}';
       	$('#AddLinkStructTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true, 
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,             
				url: '${ctx}/sys/struct/AddLinkStructTable.action?swbs_id='+swbs_id,
				frozenColumns:[[
				    {field:'OFFICETPL_ID',checkbox:true}
				]],
				columns:[[
					 {field:'IMG',title:'操作',width:0.05,align:'center' ,sortable:true,
						formatter:function(value,row,index){
						 return " <img style=' cursor: pointer;' onclick=\"openDoc('"+row.OFFICETPL_URL+"','"+row.OFFICETPL_ID+"')\" src='${ctx}/img/button/bg_fileview.gif'>";
					 },hidden:true
					},
					{field:'OFFICETPL_NAME',title:'名称',width:0.1,align:'left' ,sortable:true,
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
					{field:'OFFICETPL_URL',title:'office模板',width:0.1,align:'left' ,sortable:true,hidden:true},
					{field:'OFFICETPL_REMARK',title:'备注',width:0.1,align:'left' ,sortable:true,
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
					{field:'SEQ_NUM',title:'排序',width:0.1,align:'right' ,sortable:true,hidden:true} ,
					{field:'OFFICETPL_FLAG',title:'OFFICETPL_FLAG',width:0.05,align:'center' ,hidden:true} 
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })				
				 } 
				
			});
        	 
		  	});
			
         function AddRecord(){
        	   var currFrameId = "${param.currFrameId}";
        	  var swbs_id = '${param.swbs_id}';
        	    var Wids="";
				var rows = $('#AddLinkStructTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选择数据！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           Wids = isStringNull(Wids) ? Wids +","+rows[i].OFFICETPL_FLAG : rows[i].OFFICETPL_FLAG;	    		         
					}
					 var res = IntoActionByUrl("${ctx}/sys/struct/AddLinkStruct.action?officetpl_flags="+Wids+"&swbs_id="+swbs_id);
		    		if(res[0].result=="success"){
		    			 GetIndexFrame(currFrameId).FourFrame.location.reload();   
 				         parent.close_win('addWindow');
		    		}
				}
         }
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table id="AddLinkStructTable" class="TreeClass" ></table>
		</div>
		<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
   </body>

</html>