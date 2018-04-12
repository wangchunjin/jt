<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>文档列表</title>
			
		<script>
		 function openDoc(DocId){
			 
	            
	            var width = "900"; //窗口宽度
	        	var height = "500"; //窗口高度
	        	var wtop=(window.screen.height-height)/2;
	        	var wleft=(window.screen.width-width)/2;
	        	var urlTo = "${ctx}/km/km_upload/viewDoc.action?docId="+DocId+"&type=";
	        	window.open(urlTo,"","height=" + height + ",width=" + width + ", resizable=no,status=no,center=yes,top=" + wtop + ",left=" + wleft);	
	        		
	   //		 createSimpleWindow("viewDocWindow","查看文档",ProjName+"/km/km_upload/viewDoc.action?docId="+DocId+"&type=", width, $(parent.window).height());

	 }
        
        $(function(){ 
        	var folder_id = '${param.FOLDER_ID}';
        	$('#PubDocTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true, 
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
                url: '${ctx}/km/km_pubdoc/ShowDocTable.action?folderId='+folder_id+'&docGrade=0',
				frozenColumns:[[
				    {field:'DOC_ID',checkbox:true}
				]],
			    columns:[[
					{field:'IMG',title:'类型',width:0.1,align:'center' ,sortable:true,						
						formatter:function(value,row,index){
						if(isStringNull(row.FILE_NAME)){
		                  var className = getFileImage(row.FILE_NAME);
						   return "<img style=' cursor: pointer;' onclick=\"openDoc('"+row.DOC_ID+"')\" src='${ctx}/img/button/bg_fileview.gif'><span class='tree-icon tree-file "+className+"' ></span>";

					    
						}
					 }	
					},
				    {field:'DOC_NUMBER',title:'编号',width:0.3,align:'left' ,sortable:true},
					{field:'TITLE',title:'文档标题',width:0.4,align:'left' ,sortable:true,
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
					{field:'STATUS',title:'审批状态',width:0.1,align:'center' ,sortable:true,
					 formatter:function(value,row,index){
						if(value=="INIT"){
							return "新建";
						}else{
							return "已批准";
						}
					 }					
					},
					{field:'EDITION',title:'版本',width:0.05,align:'center' ,sortable:true},
					{field:'FRACTION',title:'分数',width:0.05,align:'center' ,sortable:true},
					{field:'CREATED_DATE',title:'创建日期',width:0.1,align:'center' ,sortable:true},
					{field:'EDITED_DATE',title:'最后修订日期',width:0.1,align:'center' ,sortable:true}

					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 },
				 onDblClickCell:function(rowIndex, field, value){ 
                     
				 },
				onClickRow:function(rowIndex, node){
				var where = node.DOC_ID;
				parent.document.all('FourFrame').src = parent.ThreeFrame.getLabelUrl(where);
				}
			});
        	 
		  	});
			
			  
		 
 function  getFileImage(fileName)
	  {
	    var doc_type = fileName.substring(fileName.lastIndexOf('.') + 1);
         doc_type = doc_type.toLowerCase();
	    var str2 = "icon-others";
  
	      if ( doc_type=="doc"  || doc_type=="docx")
	        str2 = "icon-word";
	      else if (doc_type=="xls" || doc_type=="xlsx")
	        str2 = "icon-excel";
	      else if (doc_type=="ppt")
	        str2 = "icon-ppt";
	      else if (doc_type=="txt")
	        str2 = "icon-txt";
	      else if (doc_type=="dwg")
	        str2 = "icon-dwg";
	      else if (doc_type=="gif" || doc_type=="jpg" || doc_type=="png" || doc_type=="bmp")
	        str2 = "icon-gif";
	      else if (doc_type=="pdf")
	        str2 = "icon-pdf";
	      else if (doc_type=="zip" || doc_type=="rar")
	        str2 = "icon-zip";
	    
	    return str2;
	  }
        </script>
	</head>
	<body>
		<div class="page_content"  >
		<table id="PubDocTable" class="TreeClass" ></table>
		</div>			 
	</body>

</html>