<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript" src="js/UUID.js"></script>     
  <script type="text/javascript">
   
         
  function SetProFile(obj){
	  
	  
	  var uuidId1 = new UUID();  
	  var uuidId2 = new UUID();  
	  
	  //选中的用户id
	  var userId ='${param.USER_ID}';
	  //选择角色的id
	  var roleId=$("#profile_select").val();
	  
	  if(roleId==null || roleId==""){
		//获取该用户原有的权限角色id
		  var result1 = GetXmlBySql("select role_id from cm_rluser where user_id='${param.USER_ID}'");
		  
		 
		  //获取rluser_id 准备删除
		  var result2=GetXmlBySql("select rluser_id from cm_rluser where role_id='"+result1[0].role_id+"' and user_id ='"+userId+"'");
		 
		  //删除数据
		  GetXmlBySql("delete from cm_rluser where rluser_id in ('"+result2[0].rluser_id+"')");
	  }else{
		  
		  //获取选中用户的角色id
		  var result6 = GetXmlBySql("select count(role_id) a from cm_rluser where user_id='${param.USER_ID}'");
		  if(result6[0].a=="0"){ 
			//新增数据
			  GetXmlBySql("insert into cm_rluser(wid,rluser_id,user_id,role_id ,ar_flag)values('"+uuidId1+"','"+uuidId2+"','"+userId+"','"+roleId+"','N')");
			 
		  }else{
				//获取该用户原有的权限角色id
			  var result1 = GetXmlBySql("select role_id from cm_rluser where user_id='${param.USER_ID}'");
			  
			
			 
			  //获取rluser_id 准备删除
			  var result2=GetXmlBySql("select rluser_id from cm_rluser where role_id='"+result1[0].role_id+"' and user_id ='"+userId+"'");
			 
			  //删除数据
			  GetXmlBySql("delete from cm_rluser where rluser_id in ('"+result2[0].rluser_id+"')");
			//新增数据
			  GetXmlBySql("insert into cm_rluser(wid,rluser_id,user_id,role_id ,ar_flag)values('"+uuidId1+"','"+uuidId2+"','"+userId+"','"+roleId+"','N')");
			  

		  }
		  
		  
		  }
	  
	  
	  //获取该用户原有的权限角色id
// 	  var result1 = GetXmlBySql("select role_id from cm_rluser where user_id='${param.USER_ID}'");
	  
	  //获取rluser_id 准备删除
// 	  var result2=GetXmlBySql("select rluser_id from cm_rluser where role_id='"+roleId+"' and user_id ='"+userId+"'");
	  //删除数据
	  
// 	  GetXmlBySql("delete from cm_rluser where rluser_id in ('"+result2[0].rluser_id+"')");
	  
	  //查看cm_rluser 是否存在对应数据
// 	  var result =GetXmlBySql("select count(wid) as a from cm_rluser where role_id='"+roleId+"' and user_id ='"+userId+"' ");
	  
	  
	  //新增数据
// 	  GetXmlBySql("insert into cm_rluser(wid,rluser_id,user_id,role_id ,ar_flag)values('"+uuidId1+"','"+uuidId2+"','"+userId+"','"+roleId+"','N')");
	  
	  
	  
	  
	  
	  
	  
	  
	 
     
         var node = parent.parent.TwoFrame.$('#SysUserTable').datagrid('getSelected');
        
     	var index = parent.parent.TwoFrame.$("#SysUserTable").datagrid('getRowIndex',node);
					parent.parent.TwoFrame.$('#SysUserTable').datagrid('updateRow',{
						index: index,
						row: {
							ROLE_NAME: $("#profile_select  option:selected").text(),
							PROFILE_ID: $("#profile_select").val() 
						}
					});
					
	var src = "${ctx}/web/SYS/USER/PROJFILE/content.jsp?USER_ID="+userId+"&PROFILE_ID="+obj.value;
 	parent.TwoFrame.location.href =src;
}
  
  $(function(){
	  
	 
	  //获取选中用户的角色id
	  var result = GetXmlBySql("select count(role_id) a from cm_rluser where user_id='${param.USER_ID}'");
	 
 	if(result[0].a=="0"){ 
 		
 		 SetSelect("select role_id,role_name from cm_role where remark in(select role_name from cm_role where role_type='1' and  role_id in(select role_id from cm_rluser where user_id='${sessionScope.UserInfo.userId}'))","profile_select","",new Array("=--请选择--")); 
 		  
 	 }else{
 		
 		 var result5 = GetXmlBySql("select role_id from cm_rluser where user_id='${param.USER_ID}'");
 		 SetSelect("select role_id,role_name from cm_role where remark in(select role_name from cm_role where role_type='1' and  role_id in(select role_id from cm_rluser where user_id='${sessionScope.UserInfo.userId}'))","profile_select",result5[0].role_id,new Array("=--请选择--")); 
 		  
 	 }
 	
//  	 result[0].role_id;
	 
  })
  
  </script>
  <body>
  <table width="100%">
       <tr>
           <td width="150px;" align="center" >部分权限角色分配： </td>
           <td width="200px;">
           <epmis:select id="profile_select" onChange="SetProFile(this)" attr="style='width: 100%;'" define="" ></epmis:select>
		  </td>
          <td>&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
