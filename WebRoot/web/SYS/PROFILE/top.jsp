<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
        	  
             var proftype =  parent.LeftFrame.$("input[name='type']:checked").attr("id");
               proftype=proftype=="type1" ?"0":"1";
             if(isStringNull(proftype)){
               createSimpleWindow("addWindow","增加安全配置名称","${ctx}/web/SYS/PROFILE/AddProfile.jsp?currFrameId="+currFrameId+"&proftype=0", 500, 250);
             }else{
            	 parent.TwoFrame.$.messager.alert('错误','请选择类型！');
             }
          
          }
          
        function del(){
        	     
				var row = parent.TwoFrame.$('#SysProfileTable').datagrid('getSelected');
				if(!isNull(row)){
					parent.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
					return;
				} 
				  parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/profile/DelProfile.action?PROFILE_ID="+row.PROFILE_ID+"&PROF_TYPE="+row.PROF_TYPE);
	    		if(res[0].result=="success"){
	    			 parent.TwoFrame.location.reload(); 
	    			 
	    		}
	    		}});
              
			}
         
    function copy(){
    	var row = parent.TwoFrame.$('#SysProfileTable').datagrid('getSelected');
				if(!isNull(row)){
					parent.TwoFrame.$.messager.alert("错误","请选择要复制的安全配置！");					 
				}else{
					 parent.TwoFrame.$.messager.confirm("提醒","确定要复制?",function(res){
            	if(res){
					var res = IntoActionByUrl("${ctx}/sys/profile/CopyProfile.action?PROFILE_ID="+row.PROFILE_ID+"&PROF_TYPE="+row.PROF_TYPE);
		    		if(res[0].result=="success"){
		    			 parent.TwoFrame.location.reload(); 
		    			 
		    		}
		    		}});
				}
    }
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
             <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()"  ></epmis:button>
             <epmis:button id="Cope" imageCss="icon-copy" value="复制" action="copy()"  ></epmis:button>
             <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
