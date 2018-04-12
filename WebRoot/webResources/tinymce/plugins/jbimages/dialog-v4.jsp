<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	request.setAttribute("basePath",basePath);
%>
<script type="text/javascript" src="${ctx}/webResources/js/easyui/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/webResources/js/easyui/jquery-form.js"></script>
<script type="text/javascript" src="${ctx}/webResources/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/webResources/js/easyui/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${ctx}/webResources/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/style/css/default/base.css">
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
	<title>Upload an image</title>
	<script type="text/javascript" src="js/dialog-v4.js"></script>
	<script type="text/javascript">
 
         $(function(){ 
         	$('#PlusFileTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true, 
                singleSelect:true,
			    pagination:true,//分页控件 
                rownumbers:true,//行号 
                pageSize: 20,//每页显示的记录条数，默认为10 
		        pageList: [20,30,40],//可以设置每页记录条数的列表 
		        url: "${ctx}/km/plus/PlusFileTable.action",		        
				frozenColumns:[[
				    {field:'USERFILE_ID',checkbox:false,hidden:true}
				]],
				columns:[[
					{field:'FILENAME',title:'名称',width:0.3,align:'left' ,sortable:true},
					{field:'TYPE',title:'类型 ',width:0.1,align:'center' ,hidden:true},
					{field:'FILEPATH',title:'路径',width:0.1,align:'center' ,hidden:true},				 
					{field:'XX',title:'删除',width:0.05,align:'center' ,sortable:true,
					   	 formatter:function(value,row,index){
							 return "<img title='删除' src='./img/btn_del.gif' onclick=deleteFile('"+row.USERFILE_ID+"')  style='cursor: pointer;'>";
					     }
					
					},
					{field:'jj',title:'查看',width:0.05,align:'center' ,sortable:true,
					   	 formatter:function(value,row,index){
							 return "<img title='查看' src='./img/jbimages-bw.gif' onclick=lookFile('"+row.USERFILE_ID+"/"+row.FILENAME+"')  style='cursor: pointer;'>";
					     }
					
					}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })
				 },
                onDblClickRow:function(rowIndex, row){
                        jbImagesDialog.insert(row.TYPE,"${ctx}"+row.FILEPATH,row.FILENAME)			
                }
			});
        	 
		  	});
	function deleteFile(userfile_id){
		
		 var res = IntoActionByUrl("${ctx}/km/plus/DeletePlusFile.action?userfile_id="+userfile_id);
		 if(res[0].result =="success"){
			 var row = $('#PlusFileTable').datagrid('getSelected');
			 var rowIndex = $('#PlusFileTable').datagrid('getRowIndex', row);
		      $('#PlusFileTable').datagrid('deleteRow', rowIndex);  
		 }else{
			   $.messager.alert("错误",res[0].result);
		 }
		 
	}
	//查看图片
	function lookFile(userfile_id){	
		    var iWidth = 1000;
		    var iHeight = 600;
		    var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
		    var iLeft = (window.screen.availWidth-10-iWidth)/2;        //获得窗口的水平位置;
		    myWindow  = window.open("http://106.14.163.15:8088/jt/KM/PLUS/"+userfile_id, "图片展示", 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
		
		 
		 
		 
		 
	}
   function UploadFile(){
  	    var filepath = document.getElementById("uploadfile").value;
    	if (filepath == ""){     
			$.messager.alert("错误","请选择要上传的文件!");
		}else{
		    var  fileName=filepath.split("\\");//这里要将 \ 转义一下
	        fileName = fileName[fileName.length-1];
		    var mime = filepath.toLowerCase().substr(filepath.lastIndexOf("."));
		    var fileType="";
		    if(mime==".jpg"||mime==".jpeg"||mime==".gif"||mime==".png"||mime==".bmp"){
			   fileType ="IMG";
		    }else{
			   fileType="FILE";
		    }
		    $("#fileName").val(fileName);
		    $("#fileType").val(fileType);
		    var res = $('#upl').SubmitForm("${ctx}/km/plus/UploadPlusFile.action");
		    if(res[0].resultCode == "notAllow"){
			   $.messager.alert("错误","文件格式不允许上传！");
		   }else{
			   jbImagesDialog.inProgress();
			   jbImagesDialog.uploadFinish(res);
		  }
     }

	   
   }
	</script>
	<link href="css/dialog-v4.css" rel="stylesheet" type="text/css">
</head>
<body>
 
	<form class="form-inline" id="upl" name="upl"  method="post" enctype="multipart/form-data" >
	 	<input type="hidden" name="fileName" id="fileName"/>
	 	<input type="hidden" name="fileType" id="fileType"/>
		<div id="upload_in_progress" class="upload_infobar"><img src="img/spinner.gif" width="16" height="16" class="spinner">Upload in progress&hellip; <div id="upload_additional_info"></div></div>
		<div id="upload_infobar" class="upload_infobar"></div>	
		<div   class="page_content"  style="bottom:35px;">
		<table id="PlusFileTable" class="TreeClass" ></table>
		</div>
		<p id="upload_form_container">
			<input id="uploadfile" name="uploadfile" type="file" value="增加文件"   class="jbFileBox" style="width: 80%; border: #999999 1px solid;margin-top: 5px; margin-left: 30px;margin-bottom: 5px; " onChange="UploadFile();"><%--
			
		--%></p>

	</form>
</body>
</html>