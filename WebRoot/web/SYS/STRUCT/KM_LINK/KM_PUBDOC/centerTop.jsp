<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
function AddRecord(){
	var start  =  parent.parent.opener.getStart();
 	if( start==0){
		  alert("请在word文档中鼠标选择一个插入点！");
		  return;
	}
	          var fileNames="";
              var docIds="";
				var rows = parent.TwoFrame.$('#PubDocTable').datagrid('getChecked');
				if(rows.length==0){
					parent.TwoFrame.$.messager.alert("错误","请选中行！");
					return;
				}else{
					for(var i=0;i<rows.length;i++){
 							docIds = isStringNull(docIds) ? docIds+","+rows[i].DOC_ID : rows[i].DOC_ID;
							fileNames =  isStringNull(fileNames) ? fileNames+","+rows[i].TITLE+rows[i].FILE_NAME.substring(rows[i].FILE_NAME.lastIndexOf('.'))  : rows[i].TITLE+rows[i].FILE_NAME.substring(rows[i].FILE_NAME.lastIndexOf('.')) ;		 
					}
				}
				var httpHost=window.document.location.host;  
				if(httpHost.indexOf("http")< 0){
					httpHost = "http://"+httpHost;
				}
	        //    var iconUrl=httpHost+"${ctx}/web/SYS/STRUCT/ICON/";
			 var res = IntoActionByUrl("${ctx}/km/km_pubdoc/getUrlListByDocIds.action","docIds="+docIds+"&fileNames="+fileNames);
			 if(res.length>0){
				 for(var i=0;i<res.length;i++){
					 parent.parent.opener.InsertFile(httpHost+res[i].url,res[i].fileName,"",parseInt(start)+i*30);
				 }
			 }  
				 
}
  </script>
  <body>
  <table width="100%">
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
