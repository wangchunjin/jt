<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">

        function add(){       	 
        	 var currFrameId =  parent.frameElement.id; 
        	 var parentId ="0";
        	 var node = parent.TwoFrame.$('#LanguageTree').treegrid('getSelected'); 
             if(isNull(node)){
             	parentId = node.ID;
             }
             createSimpleWindow("addWindow","增加","${ctx}/web/SYS/STRUCT/LANGUAGE/AddRecord.jsp?parentId="+parentId+"&currFrameId="+currFrameId, 500, 250);          
          }
          
          function del(){
        	 
          var node = parent.TwoFrame.$('#LanguageTree').treegrid('getSelected');
          if(isNull(node)){
				   parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/struct/deleteLanguage.action?id="+node.ID);
	    		if(res[0].result=="success"){
	    			   parent.TwoFrame.$('#LanguageTree').treegrid('remove', node.ID);  
                       parent.TwoFrame.$('#LanguageTree').treegrid('reloadFooter');    			 
	    		}
	    		}});
         }else{
           parent.TwoFrame.$.messager.alert("错误","请选择要删除的行数据");
         }
         
          }
         
          function changeContent(obj){
          	  parent.location.href= "${ctx}/web/SYS/STRUCT/FS.jsp";
           }
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="290px" style="padding-left: 50px;"><input type="radio" name="docType" value="0"    onchange="changeContent(this)" >office模板&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="docType" value="1"  checked  onchange="changeContent(this)" >常用术语</td>      
          <td width="*" align="right">      
             <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="parent.TwoFrame.exportAll('LanguageTree')"></epmis:button>
             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="parent.TwoFrame.closeAll('LanguageTree')"></epmis:button> 
             <epmis:button id="addwbs" imageCss="icon-add" value="增加" action="add()"></epmis:button>
             <epmis:button id="deleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
