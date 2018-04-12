<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
     var node = parent.TwoFrame.$('#SwbsTree').treegrid('getSelected');
   	  var content =  tinyMCE.get('CONTENTT').getContent().replace(/"/g, "\'");
	  $("#smSwbs\\.info").val(content)
   
 

                var ff = document.labelForm;
               
                ff.action="UpdateSwbs.action?type=remark";
			   	ff.submit();
  }
  	  	function MCEInit()
	{
		var content=$("#smSwbs\\.info").val();
	 	$("#CONTENTT_ifr").contents().find("#tinymce").html(content); 	 
	}
  </script>
   <body  onload="MCEInit()">
      <div class="label_div">
			 <table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
			<tr class="" >
				<td width="99%" class="" id="content">
					<textarea id="CONTENTT" name="CONTENTT" class="label_textarea"  ></textarea>
				</td>
			</tr>
			</table>
     </div>
     <form method="post" action="" id="labelForm" name="labelForm">
       <input type="hidden" id="smSwbs.swbsId" name="smSwbs.swbsId" value="${smSwbs.swbsId}">	
        <input type="hidden" id="smSwbs.info" name="smSwbs.info" value="${smSwbs.info}">						
       </form>
     </div> 						
  </body>
</html>
