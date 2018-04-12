<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
      function AddRecord(){
    	   var swbsTypeId = '${param.swbsTypeId}';
    	   var currFrameId = '${param.currFrameId}';
    	  	var modeObj = document.getElementById("mode1");
			var mode;
			if (modeObj.checked)
			{
			   mode = modeObj.value;
			}
			else
			{
			   mode = document.getElementById("mode2").value;
			}
			var filepath = document.getElementById("uploadfile").value;
			if (filepath == "")
			{     
				$.messager.alert('错误','请选则要上传的文件!');
			}
			else
			{ 
		               var res = $('#fileForm').SubmitForm("${ctx}/sys/swbs/impSwbsModule.action?swbsTypeId="+swbsTypeId+"&mode="+mode);
					 if(res[0].result=="success"){
						  GetIndexFrame(currFrameId).TwoFrame.location.reload(); 
						 parent.close_win('addWindow');
					 }	
			}
     }
  </script>
  <body style="background:  #ffffff;">
 <form id="fileForm" name="fileForm" action="" method="post" enctype="multipart/form-data">
		<table height="*" width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;margin-top: 10px;line-height:50px;">
			<tr>
				<TD width="19%" align="center" class="tdlab">源文件:</TD>
				<TD width="81%"><input type="file" id="uploadfile" name="uploadfile" style="width: 80%; border: #999999 1px solid"></TD>
			</tr>
			<tr  style="display: none;">
				<TD align="center" class="tdlab">导入方式:</TD>
				<TD class="td1">
					<INPUT TYPE="radio" id="mode1" NAME="mode" value="tihuan" checked="true">替换&nbsp;&nbsp;
					<INPUT TYPE="radio" id="mode2" NAME="mode" value="jiaru" >加入
				</TD>
			</tr>
			<tr>
				<TD align="center" colspan="2">
				 <a href="${ctx}/doc/swbsModule.xls" style="text-decoration:underline"><font color="blue">模板下载</font></a> 
				</TD>
			</tr>
		</table>
	</form>
				<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addEpsWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
