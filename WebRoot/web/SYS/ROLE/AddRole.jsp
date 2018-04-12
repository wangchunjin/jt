<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
 <script type="text/javascript">
         function  AddRecord(){
            var currFrameId = "${param.currFrameId}";
             var res = $('#addForm').SubmitForm("${ctx}/sys/role/AddRole.action");
	    		if(res[0].result=="success"){				 
                    var node = GetIndexFrame(currFrameId).TwoFrame.$('#SysRoleTree').treegrid('getSelected');
						 if(isNull(node)){                  
                    	 	   GetIndexFrame(currFrameId).TwoFrame.TreeGrid_RefreshNode(node.ROLE_ID,"SysRoleTree");                   
                           }else{
                        	   GetIndexFrame(currFrameId).TwoFrame.location.reload();   
                           }
 				    parent.close_win('addWindow');
	    		}else{
	    			$.messager.alert("错误",res[0].result);
	    		}
						     
 
         }
           
 </script>

  </head>
  
  <body  class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			<tr class="">
					<th width="12%">名称</th>
					<td width="88%" class=""><input class="label_text"   name="cmRole.roleName" id="cmRole.roleName" ></td>
				</tr>
	
				<tr class="">
					<th width="12%">类别</th>
					<td width="88%" class=""><s:radio name="cmRole.roleType" list="#{'0':'分类','1':'角色'}"   value="0" theme="simple" ></s:radio></td>
               </tr>
               	<tr>
					<th width="12%">排序</th>
					<td width="88%"><epmis:text id="cmRole.seqNum" define="select ifnull(max(seq_num),0)+1 from cm_role where parent_role_id ='${param.parentId}'"></epmis:text></td>
				</tr>
							<tr>
					<th width="12%">备注</th>
					<td width="88%"><textarea  class="label_textarea"   name="cmRole.remark" id="cmRole.remark" ></textarea></td>
				</tr>
       </table>
	       <input type="hidden" id="cmRole.parentRoleId" name="cmRole.parentRoleId" value="${param.parentId}">								
</form>
			<div class="BottomDiv">
				<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
