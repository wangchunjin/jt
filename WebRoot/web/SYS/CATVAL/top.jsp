<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
          function add(){
        	 var currFrameId =  parent.frameElement.id; 
             var parentId="0"
             var node = parent.TwoFrame.$('#CatvalTree').treegrid('getSelected');
             if(isNull(node)){
            parentId = node.CATG_ID;
             }
              var catg_type_id  = parent.LeftFrame.$("#catg_type_id").val();             
             if(isStringNull(catg_type_id)){
               createSimpleWindow("addWindow","增加分类码","${ctx}/web/SYS/CATVAL/AddCatval.jsp?currFrameId="+currFrameId+"&parentId="+parentId+"&catg_type_id="+catg_type_id, 500, 250);
             }else{
            	 parent.TwoFrame.$.messager.alert('错误','请选择类型！');
             }
          
          }
          
      function del(){
        	 
          var node = parent.TwoFrame.$('#CatvalTree').treegrid('getSelected');
         if(isNull(node)){
				   parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/catvalType/DelCatvalCode.action?catgId="+node.CATG_ID);
	    		if(res[0].result=="success"){
	    			   parent.TwoFrame.$('#CatvalTree').treegrid('remove', node.CATG_ID);  
                        parent.TwoFrame.$('#CatvalTree').treegrid('reloadFooter');
	    			 
	    		}
	    		}});
         }else{
           parent.TwoFrame.$.messager.alert("错误","请选择要删除的行数据");
         }
         
          }
         
  
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
             <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="parent.TwoFrame.exportAll('CatvalTree')"></epmis:button>
             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="parent.TwoFrame.closeAll('CatvalTree')"></epmis:button> 
             <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()"  ></epmis:button>
             <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
