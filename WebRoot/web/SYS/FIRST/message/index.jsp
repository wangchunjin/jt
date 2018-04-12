<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="${ctx}/web/CO/CO_NEW/INDEX/center.css" type="text/css"></link>

 </head>
 <script type="text/javascript">
 function SelectUsers(){
	 var currFrameId =  parent.frameElement.id;
     createSimpleWindow("addWindow","选择用户","${ctx}/web/SYS/FIRST/message/choose/roleUserTree.jsp?currFrameId="+currFrameId, 550, 600);
 }
 
 function SendMessage(){
	 var toUsers = $("#to_user_ids").val();
	 var title = $("#title").val();
	 var content = $("#content").val();
	 var userId = '${UserInfo.getUserId()}';
	 if(!isStringNull(toUsers) || !isStringNull(title) || !isStringNull(content) ){
		 $.messager.alert("错误","收信人、标题、内容任一不能为空");
	 }else{         
	  var res=  IntoActionByUrl("${ctx}/sys_ent/appMsg/SendMsg.action?toUsers="+toUsers+"&title="+title+"&content="+content+"&userId="+userId);
       if(res[0].result=="success"){
    	   $.messager.alert("成功","发送成功");
       }else{
    	   $.messager.alert("失败",res[0].result);
       }
	 }
 }
 
 function ShowHistory(){
	 var currFrameId =  parent.frameElement.id;
     createSimpleWindow("addWindow","历史消息","${ctx}/web/SYS/FIRST/message/history/selType.jsp?currFrameId="+currFrameId, 750, 600);

 }
  function cleanUsers(){
     $("#to_user_names").val("");
	 $("#to_user_ids").val("");
  }
 
 </script>
<%
	Date nowDate=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
	request.setAttribute("nowDate", sdf.format(nowDate));
	
%>
<body style="height:400px;">
<div class="center_02" style="position: relative;width:100%;border:none;" >
<div class="center_000_01">
	<div><b><span style="font-size: 12px;font-family: 宋体;font-weight: normal;display:none;" > &nbsp;☆ 沟通消息</span><span style="font-size: 12px;float: right;" ><a href="#" onclick="ShowHistory();" class="mytest" style="color: black;font-weight: normal;">点击浏览历史消息</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></b></div>
</div>
<div class="center_000_02">
<table border="0" style="margin-top: 6px;" cellpadding="0" cellspacing="0" width="100%" height="95%">
  <tr>
      <td width="10%" align="center">标题</td>
      <td width="70%" colspan="2"><input type="text" class="form_text" id="title" style="width: 95%;" ></td>
 </tr> 
  <tr>
      <td width="10%" align="center">收信人</td>
      <td width="70%"><textarea id="to_user_names" style="width: 100%;height: 50px;" readonly="readonly"></textarea></td>
      <td align="center"  ><input type="button" value="选择" onclick="SelectUsers()" >&nbsp;&nbsp;<input type="button" value="清空" onclick="cleanUsers()" ></td>
  </tr>
  <tr>
      <td align="center">内容</td>
      <td colspan="2"><textarea id="content" style="width: 95%;height: 220px;" ></textarea></td>
  </tr>
  <tr>
      <td colspan="3" style="padding-right:5%; text-align: right;"><a href="###" class="btn_01" onclick="SendMessage();">发送<b></b></a></td>
  </tr><input type="hidden" id="to_user_ids">
</table>
</div>
</div>
</body>
</html>