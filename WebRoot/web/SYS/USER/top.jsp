<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
             //var result = IntoActionByUrl("${ctx}/sys/user/CheckUserNum.action");
	        //if(!isStringNull(result[0].result)){
	              createSimpleWindow("addWindow","增加用户","${ctx}/web/SYS/USER/ADD/FS.jsp?currFrameId="+currFrameId,  850, 500);
	         //  }else{
		    //    alert(result[0].result);
		   // }
          
          }
          
        function del(){
        	
        	
// 			     var node = parent.TwoFrame.$('#SysUserTable').datagrid('getSelected');
        	var rows = parent.TwoFrame.$('#SysUserTable').datagrid('getChecked');	
        	
			var ids="";
			for(var i=0;i<rows.length;i++){
				if(i==0)
				ids=ids+rows[i].USER_ID;
				else
				ids=ids+","+rows[i].USER_ID;
				
			}
			    if(rows.length==0){
			    	 parent.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
			    	return;
			    }
				  parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/user/DelUser.action?USER_ID="+ids);
// 				var res = IntoActionByUrl("${ctx}/sys/user/DelUser.action?USER_ID="+node.USER_ID);
	    		if(res[0].result=="success"){
	    			   SetChange();	 
	    		}
	    		}});
              
			}
        
			
         
/*   function sel(obj){
	var where ="";
	if(obj.value=="1"){
		where = "1";
	}else if(obj.value=="0"){
		where = "0";
	}
	$("#key").val(""); 
	var src = "${ctx}/web/SYS/USER/content.jsp?where="+where;
 	parent.TwoFrame.location.href =src;
} */
  
  function UrlTo(){
	  parent.location.href = "${ctx}/web/SYS/USER/VIEW/FS.jsp";
  }
	 function SetChange(){ 
		 var typ = $("#typ").val();  
    	 var whereSql="";
    	 var key = $("#key").val(); 
    	 if(typ!="2"){
    		 whereSql = typ;
    	 }
    	   
    	 parent.TwoFrame.$('#SysUserTable').datagrid('clearSelections');
    	 parent.TwoFrame.$('#SysUserTable').datagrid('load',{ 
    		   where: whereSql,
    		   key:key
    		});
     }
  </script>
  <body>
  <table width="100%">
       <tr>
           <td width="200px;" align="center"><select id="typ" name="typ" onchange="SetChange()" >
			    <option value="0" selected="selected">显示启用的用户</option>
			    <option value="1">显示禁用的用户</option>
			    <option value="2">显示全部用户</option>
			    </select>
		  </td> 
		   <td>
		       关键字搜索：<input type="text" id="key" onchange="SetChange()" >
		  </td>
          <td width="*" align="right">
<!--              <epmis:button id="UrlTo" imageCss="icon-redo" value="按机构权限显示用户" action="UrlTo()"  ></epmis:button> -->
             <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()"  ></epmis:button>
             <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
