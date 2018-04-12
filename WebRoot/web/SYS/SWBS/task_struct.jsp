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
       	$('#LinkStructTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,       
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/struct/LinkStructTable.action?swbs_id='+swbs_id,
				frozenColumns:[[
				    {field:'OFFICETPL_ID',checkbox:true}
				]],
				columns:[[
					 {field:'IMG',title:'操作',width:0.05,align:'center' ,sortable:true,
						formatter:function(value,row,index){
						 return " <img style=' cursor: pointer;' onclick=\"openDoc('"+row.OFFICETPL_URL+"','"+row.OFFICETPL_ID+"')\" src='${ctx}/img/button/bg_fileview.gif'>";
					 }
					},
					{field:'OFFICETPL_NAME',title:'名称',width:0.2,align:'left' ,sortable:true},
					{field:'OFFICETPL_URL',title:'office模板',width:0.3,align:'left' ,sortable:true},
					{field:'OFFICETPL_REMARK',title:'备注',width:0.3,align:'left' ,sortable:true},					
					{field:'SEQ_NUM',title:'排序',width:0.1,align:'right' ,sortable:true} ,
					{field:'OFFICETPL_FLAG',title:'OFFICETPL_FLAG',width:0.05,align:'center' ,hidden:true} 
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })				
				 } 
				
			});
        	 
		  	});
		function add(){
    	  var node = parent.TwoFrame.$('#SwbsTree').treegrid('getSelected');
    	  if(isNull(node)){
    		    var swbs_id = '${param.swbs_id}';
    	  	    var currFrameId =  parent.frameElement.id; 
                createSimpleWindow("addWindow","选择模板","${ctx}/web/SYS/STRUCT/LinkSwbs.jsp?currFrameId="+currFrameId+"&swbs_id="+swbs_id, 500, 500);
    	  }else{
    		  $.messager.alert("错误","请选择作业节点！");
    	  }
       }
       
        function del(){
        	    var Wids="";
        	    var swbs_id = '${param.swbs_id}';
				var rows = $('#LinkStructTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           Wids = isStringNull(Wids) ? Wids +","+rows[i].OFFICETPL_FLAG : rows[i].OFFICETPL_FLAG;	    		         
					}
				}
				 
				  $.messager.confirm("提醒","确定要删除?",function(ress){
            	if(ress){
				  var res = IntoActionByUrl("${ctx}/sys/struct/DelLinkStruct.action?officetpl_flags="+Wids+"&swbs_id="+swbs_id);
	    		if(res[0].result=="success"){
	    			 location.reload(); 
	    			 
	    		}
	    		}});
              
			}
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table id="LinkStructTable" class="TreeClass" ></table>
		</div>
					<div class="LabelBottomDiv">
             <epmis:button id="Delete" imageCss="icon-add" value="选择" action="add()" datactrCode=""></epmis:button>					
             <epmis:button id="addtask" imageCss="icon-remove" value="取消" action="del()" datactrCode=""></epmis:button> 
			</div>
   </body>

</html>