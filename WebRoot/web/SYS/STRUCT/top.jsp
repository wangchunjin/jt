<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
          function add(){
        	  var currFrameId =  parent.frameElement.id; 
               createSimpleWindow("addWindow","增加","${ctx}/web/SYS/STRUCT/AddRecord.jsp?currFrameId="+currFrameId, 600, 250);         
          }
         function update(){
        	  var currFrameId =  parent.frameElement.id; 
        	   var node = parent.TwoFrame.$('#StructTable').datagrid('getSelected');
        	   if(isNull(node)){
                   createSimpleWindow("addWindow","增加","${ctx}/web/SYS/STRUCT/AddRecord.jsp?currFrameId="+currFrameId+"&OFFICETPL_FLAG="+node.OFFICETPL_FLAG, 600, 250); 
               }else{
            	   parent.TwoFrame.$.messager.alert("错误","请选中要更新的行！");
               }
          }
        function del(){
        	    var wids="";
				var rows = parent.TwoFrame.$('#StructTable').datagrid('getChecked');
				if(rows.length==0){
					parent.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           wids = isStringNull(wids) ? wids +","+rows[i].OFFICETPL_ID : rows[i].OFFICETPL_ID;	    		         
					}
				}
				 
				  parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/struct/DelStruct.action?OFFICETPL_IDS="+wids);
	    		if(res[0].result=="success"){
	    			 parent.TwoFrame.location.reload(); 
	    			 
	    		}
	    		}});
              
			}
         
        function changeContent(obj){
      	  parent.location.href= "${ctx}/web/SYS/STRUCT/LANGUAGE/FS.jsp";
       }
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="290px" style="padding-left: 50px;"><input type="radio" name="docType" value="0"  checked  onchange="changeContent(this)" >office模板&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="docType" value="1"   onchange="changeContent(this)" >常用术语</td>
          <td width="*" align="right">
             <epmis:button id="NewRecord" imageCss="icon-redo" value="更新版本" action="update()"  ></epmis:button>
             <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()"  ></epmis:button>
             <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
