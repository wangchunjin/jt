<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>SWBS</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		function loginPic(obj){
    	createSimpleWindow("addWindow","登录图片","${ctx}/loginPic.action", 420, 240);
	}
		
		function update(){
 
	var flag=IntoActionByUrl("${ctx}/sys/service/TestConnect.action")
	if(flag[0].result=="Y"){
		createSimpleWindow("addWindow","深度同步","${ctx}/web/KM/KM_PUBDOC/UPDATE/KJ.jsp", 900, 600);
	}else{
	    alert("连接文档服务器失败！")
	}
	  
}
		function updateNew(){
 
			var flag=IntoActionByUrl("${ctx}/sys/service/TestConnect.action")
			if(flag[0].result=="Y"){
				createSimpleWindow("addWindow","快捷同步","${ctx}/sys/service/ShowNewDetail.action", 900, 600);
 			}else{
			    alert("连接文档服务器失败！")
			}
		}
		
		function updateModule(obj){ 
	var modle_name=obj.value;
	var flag=IntoActionByUrl("${ctx}/sys/service/TestConnect.action")
	if(flag[0].result=="Y"){
		createSimpleWindow("addWindow","快捷同步","${ctx}/sys/service/getSwbsTypeByModle.action?module_name="+encodeURI(modle_name), 800, 380);
		}else{
	    alert("连接文档服务器失败！")
	}
}
		function updateStruct(){
	        var flag=IntoActionByUrl("${ctx}/sys/service/TestConnect.action")
			if(flag[0].result=="Y"){
				createSimpleWindow("addWindow","同步","${ctx}/sys/service/GetStruct.action", 800, 380);
 			}else{
			    alert("连接文档服务器失败！")
			}
		}
		function updateLanguage(){
	        var flag=IntoActionByUrl("${ctx}/sys/service/TestConnect.action");
			if(flag[0].result=="Y"){
				var res =IntoActionByUrl("${ctx}/sys/service/LanguageUpdate.action");
				alert(res[0].result);
 			}else{
			    alert("连接文档服务器失败！")
			}
		}
	</script>
</head>
<body class="NewWinClass">
    <table width="100%" style="margin-top: 30px;margin-left: 15px;">
           <tr>
               <td style="font-weight: bold;">1，首页图片logo设置：&nbsp;&nbsp;<input type="button" id="logo" value="上传" onclick="loginPic()" style="cursor: pointer;" /></td>
           </tr>
           <tr>
               <td style="font-weight: bold;">2，公共文档云同步：&nbsp;&nbsp;<input type="button" id="fast" value="快捷同步" onclick="updateNew()" style="cursor: pointer;" />&nbsp;&nbsp;<input type="button" id="low" value="深度同步" onclick="update()" style="cursor: pointer;" /><font style="color: red;">快捷同步指从您上次更新开始服务器新增加的文档，速度较快。深度同步指逐个比对本地与服务器文件，包括已更新过的文件，速度较慢。推荐使用快捷同步</font></td>
           </tr>
           <tr>
               <td style="font-weight: bold;">3，WBS模板同步：&nbsp;&nbsp;<input type="button" id="xmxx" value="项目信息" onclick="updateModule(this)" style="cursor: pointer;" />&nbsp;&nbsp;<input type="button" id="zlaq" value="质量安全" onclick="updateModule(this)" style="cursor: pointer;" />&nbsp;&nbsp;<input type="button" id="htzj" value="合同造价" onclick="updateModule(this)" style="cursor: pointer;" />&nbsp;&nbsp;<input type="button" id="logo" value="进度管理" onclick="updateModule(this)" style="cursor: pointer;" /></td>
           </tr>
           <tr>
               <td style="font-weight: bold;">4，结构化同步：&nbsp;&nbsp;<input type="button" id="strut" value="word模板同步" onclick="updateStruct()" style="cursor: pointer;" />&nbsp;&nbsp;<input type="button" id="language" value="专业术语同步" onclick="updateLanguage()" style="cursor: pointer;" /></td>
           </tr>
           <tr>
               <td></td>
           </tr>   
    </table>
</body>

</HTML>
