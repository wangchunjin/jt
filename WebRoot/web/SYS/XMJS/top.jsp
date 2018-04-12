<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript">
		var rows = [];
		var result = null;
		function searchHtByInfo(){
			parent.document.all('FourFrame').src="${ctx}/web/SYS/XMJS/content.jsp";
		}
		function expEmployee(){
			var data = [];   
	        data = rows;
	        var coumlt = result;
	        var listRes;
	        $.ajax({
               	url : "${ctx}/xmjs/proj/ExpEmployeeList.action",
               	type: 'post',
                   dataType: 'json',
                   data: "data="+JSON.stringify(data)+"&coumlt="+JSON.stringify(coumlt),
                   async : false,
                   error: function(){
                   	parent.TwoFrame.$.messager.alert('错误','操作出错！');
                   },
                   success: function(data){
                       listRes =  data;
                   }
	        });
	        if(listRes[0].result=="Y"){
	    	    window.open(listRes[0].url);
	        }else{
	        	parent.TwoFrame.$.messager.alert("错误",listRes[0].url);
	        }
		}
		
	</script>
  </head>
  
  <body>
  <table width="100%" >
       <tr>
          <td width="*" align="right">
             <epmis:button id="publish" imageCss="icon-search" value="开始检索" action="searchHtByInfo();" datactrSql=""  datactrCode="" ></epmis:button>
             <epmis:button id="addNew" imageCss="icon-export" value="导出" action="expEmployee()" datactrSql=""  datactrCode="" ></epmis:button>
             <epmis:button id="cancelPublish" imageCss="icon-reload" value="重置" action="parent.window.location.reload();" datactrSql=""  datactrCode="" ></epmis:button>

          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
