<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>责任人</title>
			
		<script>
		 
 
         $(function(){ 
        	 var swbs_id ='${param.swbs_id}';
        	 var doc_id ='${param.doc_id}';
             var swbs_type_id = '${param.swbs_type_id}';
      
        	$('#PlanRefDataTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,    
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/swbs/PlanRefDataTable.action?swbs_id='+swbs_id+'&doc_id='+doc_id+'&swbs_type_id='+swbs_type_id,
				frozenColumns:[[
				    {field:'DOC_ID',checkbox:true}
				]],
				columns:[[
					{field:'DOC_NUMBER',title:'编号',width:0.1,align:'left' ,sortable:true,
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
					{field:'TITLE',title:'文档标题',width:0.1,align:'left' ,sortable:true,
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
					{field:'DATE_LINKED',title:'日期',width:0.1,align:'center' ,sortable:true},
					{field:'WID',title:'',width:0.3,align:'left' ,sortable:true,hidden:true},
					{field:'RES',title:'',width:0.3,align:'left' ,sortable:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
 
				
			});
        	 
		  	});
			
       function add(){
    	  var node = parent.TwoFrame.$('#SwbsTree').treegrid('getSelected');
    	  if(isNull(node)){
    		    var base_master_key = '${param.swbs_id}';
    		    var plan_wid ='${param.doc_id}';
    		    var base_item_type = 'SM_PLAN_DATA';
    		     var swbs_type_id = '${param.swbs_type_id}';
    	  	    var currFrameId =  parent.frameElement.id; 
                createSimpleWindow("addWindow","选择文档关联","${ctx}/web/KM/KM_PUBDOC/DOCLINK/FS.jsp?currFrameId="+currFrameId+"&base_master_key="+base_master_key+"&plan_wid="+plan_wid+"&base_item_type="+base_item_type+"&swbs_type_id="+swbs_type_id, 900, 670);
    	  }else{
    		  $.messager.alert("错误","请选择作业节点！");
    	  }
       }
       
function del(){
        	    var Wids="";
				var rows = $('#PlanRefDataTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           Wids = isStringNull(Wids) ? Wids +","+rows[i].WID : rows[i].WID;	    		         
					}
				}
				 
				  $.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/swbs/delDocLink.action?Wids="+Wids);
	    		if(res[0].result=="success"){
	    			 location.reload(); 
	    			 
	    		}
	    		}});
              
			}
function down(){
	var row = $('#PlanRefDataTable').datagrid('getSelected');
	if(isNull(row)){
		var res = IntoActionByUrl("${ctx}/km/km_upload/downDoc.action?docId="+row.DOC_ID);
		 window.open(res[0].result)
	}else{
		$.messager.alert("错误","请选中要下载的文档！");
	}
}
 function openw(){
	var row = $('#PlanRefDataTable').datagrid('getSelected');
	if(isNull(row)){
	  		  var width = "1000";
             if($(parent.window).width()<1000){
            	 width = $(parent.window).width()-100;
             }
    		 createSimpleWindow("viewDocWindow","查看文档","${ctx}/km/km_upload/viewDoc.action?docId="+row.DOC_ID+"&type=", width, $(parent.window).height());
	}else{
		$.messager.alert("错误","请选中要查看的文档！");
	}
}
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table id="PlanRefDataTable" class="TreeClass" ></table>
		</div>
			<div class="LabelBottomDiv">
             <epmis:button id="Delete" imageCss="icon-add" value="选择" action="add()" datactrCode=""></epmis:button>					
             <epmis:button id="addtask" imageCss="icon-remove" value="取消" action="del()" datactrCode=""></epmis:button>
             <epmis:button id="view" imageCss="icon-view" value="查看" action="openw()" datactrCode=""></epmis:button>	
             <epmis:button id="Delete" imageCss="icon-download" value="下载" action="down()" datactrCode=""></epmis:button>	
			</div>
	 
	</body>

</html>