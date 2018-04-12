<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
 
               createSimpleWindow("addWindow","增加联系单位","${ctx}/web/SYS/VNMT/AddVnmt.jsp?currFrameId="+currFrameId, 650,450);
    
          
          }
          
        function del(){
        	    var wids="";
				var rows = parent.TwoFrame.$('#SysVnmtTable').datagrid('getChecked');
				if(rows.length==0){
					parent.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           wids = isStringNull(wids) ? wids +","+rows[i].VNMT_ID : rows[i].VNMT_ID;	    		         
					}
				}
				 
				  parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/vnmt/DelVnmt.action?vnmtIds="+wids);
	    		if(res[0].result=="success"){
	    			 parent.TwoFrame.location.reload(); 
	    			 
	    		}
	    		}});
              
			}
         
  
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
             <epmis:button id="SearchRecord" imageCss="icon-search" value="查找" action="SearchPage('TwoFrame')"  ></epmis:button>
             <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()"  ></epmis:button>
             <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
