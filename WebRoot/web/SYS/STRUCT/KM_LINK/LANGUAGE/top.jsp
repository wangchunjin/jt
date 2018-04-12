<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">

        function add(){       	 
     
        	 var parentId ="0";
        	 var node = parent.TwoFrame.$('#LanguageTree').treegrid('getSelected'); 
             if(isNull(node)){
             	parentId = node.ID;
             }
             
             var width = "500"; //窗口宽度D:\\WORDICON.EXE
         	var height = "250"; //窗口高度
         	var wtop=(window.screen.height-height)/2;
         	var wleft=(window.screen.width-width)/2;
         	var urlTo = "${ctx}/web/SYS/STRUCT/KM_LINK/LANGUAGE/AddRecord.jsp?parentId="+parentId;
         		window.open(urlTo,"","height=" + height + ",width=" + width + ", resizable=no,status=no,center=yes,top=" + wtop + ",left=" + wleft);		        	 
         }
          
          function del(){
           var node = parent.TwoFrame.$('#LanguageTree').treegrid('getSelected');        
          if(isNull(node)){
				   parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/struct/deleteOwnLanguage.action?id="+node.ID);
	    		if(res[0].result=="success"){
	    			   parent.TwoFrame.$('#LanguageTree').treegrid('remove', node.ID);  
                       parent.TwoFrame.$('#LanguageTree').treegrid('reloadFooter');    			 
	    		}else{
	    			 parent.TwoFrame.$.messager.alert("错误",res[0].result);
	    		}
	    		}});
         }else{
           parent.TwoFrame.$.messager.alert("错误","请选择要删除的行数据");
         }
         
          }
          function changeContent(obj){
        	  if(obj.value=="0"){
        		  parent.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PUBDOC/FS.jsp";
        	  }else if(obj.value=="2"){
        		  parent.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/FS.jsp";
        	  }else if(obj.value=="3"){
        		  parent.location.href="${ctx}/web/SYS/STRUCT/KM_LINK/LANGUAGE/FS.jsp";
        	  }    	  
          }
          
          function insert(){
	      		var start  =  parent.parent.opener.getStart();
	    	 	if( start==0){
	    			  alert("请在word文档中鼠标选择一个插入点！");
	    			  return;
	    		}
              var node = parent.TwoFrame.$('#LanguageTree').treegrid('getSelected');        
              if(isNull(node)){
            	  parent.parent.opener.InsertLanguage(node.NAME,start);
              }else{
            	  parent.TwoFrame.$.messager.alert("错误","请选择要插入的语句！");
              }

        	 	
        	 	
          }
  </script>
  <body>
  <table width="100%">
       <tr>
          <td width="290px" style="padding-left: 30px;"><input type="radio" name="docType" value="0"   onchange="changeContent(this)" >公共文档&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="docType" value="2"      onchange="changeContent(this)" > 项目文档&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="docType" value="3"  checked   onchange="changeContent(this)" > 专业术语</td>
          <td width="*" align="right">      
             <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="parent.TwoFrame.exportAll('LanguageTree')"></epmis:button>
             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="parent.TwoFrame.closeAll('LanguageTree')"></epmis:button> 
            <epmis:button id="addwbs" imageCss="icon-add" value="增加" action="add()"></epmis:button>
             <epmis:button id="deleteRecord" imageCss="icon-remove" value="删除" action="del()"></epmis:button>
             <epmis:button id="insertWord" imageCss="icon-add" value="插入到word" action="insert()"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
