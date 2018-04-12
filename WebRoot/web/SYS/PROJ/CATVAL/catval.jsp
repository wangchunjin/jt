<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>分类吗</title>
			
		<script>
		 
 
         $(function(){ 
        	 var base_master_key  = '${param.BASE_MASTER_KEY}';
        	 var which_catval_type ='${param.WHICH_CATVAL_TYPE}';

        	$('#ProjCatvalTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
                striped:true,               
				url: '${ctx}/sys/catval/CatvalTable.action?base_master_key='+base_master_key+'&which_catval_type='+which_catval_type,
				frozenColumns:[[
				    {field:'WID',checkbox:true}
				]],
				columns:[[
					{field:'CATG_TYPE_ID',title:'CATG_TYPE_ID',width:0.1,align:'left',hidden:true},
					{field:'CATG_TYPE',title:'分类码类型',width:0.1,align:'left' ,sortable:true,
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
					{field:'CATG_SHORT_NAME',title:'码值',width:0.1,align:'left' ,sortable:true,
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
					{field:'CATG_NAME',title:'说明',width:0.2,align:'left' ,sortable:true,
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
	             })

				
				 },
 
				
			});
        	 
		  	});
			
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
         	 var base_master_key  = '${param.BASE_MASTER_KEY}';
        	 var which_catval_type ='${param.WHICH_CATVAL_TYPE}';
             if(isStringNull(base_master_key)){
                 createSimpleWindow("addWindow","增加分类码","${ctx}/web/SYS/PROJ/CATVAL/AddCatval.jsp?currFrameId="+currFrameId+"&base_master_key="+base_master_key+"&which_catval_type="+which_catval_type, 460, 480);
             }else{
            	 $.messager.alert("错误","请选中要添加分类码的的数据！");
             }
          
          }
          
          function del(){
        	    var Wids="";
				var rows = $('#ProjCatvalTable').datagrid('getChecked');
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
				var res = IntoActionByUrl("${ctx}/sys/catval/DelCatval.action?Wids="+Wids);
	    		if(res[0].result=="success"){
	    			 location.reload(); 
	    			 
	    		}
	    		}});
              
			}
          
          function addAll(){
        	  var currFrameId =  parent.frameElement.id; 
         	 var base_master_key  = '${param.BASE_MASTER_KEY}';
        	 var which_catval_type ='${param.WHICH_CATVAL_TYPE}';
        
              if(isStringNull(base_master_key)){
            createSimpleWindow("addWindow","批量增加分类码","${ctx}/web/SYS/PROJ/CATVAL/AddCatvalAll.jsp?currFrameId="+currFrameId+"&base_master_key="+base_master_key+"&which_catval_type="+which_catval_type, 460, 480);
               }else{
            	 $.messager.alert("错误","请选中要添加分类码的的数据！");
             }
          }
          
        </script>
	</head>
	<body>
		<div class="page_content" style="bottom: 40px;">
		<table id="ProjCatvalTable" class="TreeClass" ></table>
		</div>
			<div class="LabelBottomDiv">
             <epmis:button id="addtask" imageCss="icon-add" value="增加" action="add()" datactrCode="SYS_PROJ.cat.edit" ></epmis:button>
             <epmis:button id="addtaskAll" imageCss="icon-add" value="批量增加" action="addAll()" datactrCode="SYS_PROJ.cat.edit" ></epmis:button>
             <epmis:button id="Delete" imageCss="icon-remove" value="删除" action="del()"  datactrCode="SYS_PROJ.cat.edit" ></epmis:button>			
			</div>
	 
	</body>

</html>