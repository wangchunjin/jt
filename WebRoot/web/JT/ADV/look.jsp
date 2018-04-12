<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>

<script type="text/javascript">
	function MCEInit() {
		$("#CONTENTT_ifr").contents().find("#tinymce").html();
	};
	
	 $(function(){
		 $("#CONTENTT").html($("#content").val());
		 
	  });
	  
	 
</script>

</head>
<body class="NewWinClass">
	 
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			
			
			<tr id="hhh">			
				<td>
					<textarea rows="20"  >${adv[0].content }</textarea> 					   	  
				</td>
			</tr>
       </table>
<!--        <input class="form_text" type="hidden"  name="country.content" id="content"  value="">	 -->
</form>

	<div class="BottomDiv">
		 <a
			href="###" class="btn_01" onclick="parent.close_win('lookWindow')">关闭<b></b></a>
	</div>
</body>
</html>
