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
	 
     parent.TwoFrame.$('#SwbsTree').treegrid('update',{
			id: node.SWBS_ID,
			row: {
				SWBS_SHORT_NAME: $("#smSwbs\\.swbsShortName").val(),
				SWBS_NAME: $("#smSwbs\\.swbsName").val()
			}
		});

                var ff = document.labelForm;
               
                ff.action="UpdateSwbs.action?type=info";
			   	ff.submit();
  }
  </script>
  <body  >
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table width="96%">
     	                          
				<tr style="line-height: 30px;">
					<th width="15%">代码</th>
					<td width="85%"><input class="label_text"   name="smSwbs.swbsShortName" id="smSwbs.swbsShortName" value="${smSwbs.swbsShortName}"></td>
				</tr>
				<tr>
					<th width="15%">名称</th>
					<td width="85%"><input class="label_text"   name="smSwbs.swbsName" id="smSwbs.swbsName" value="${smSwbs.swbsName}"></td>
				</tr>
				<tr>
					<th width="15%">排序</th>
					<td width="85%"><input class="label_text"   name="smSwbs.seqNum" id="smSwbs.seqNum" value="${smSwbs.seqNum}"></td>
				</tr>
       </table>	
       <input type="hidden" id="smSwbs.swbsId" name="smSwbs.swbsId" value="${smSwbs.swbsId}">							
       </form>
     </div> 						
  </body>
</html>
