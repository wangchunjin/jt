<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript" src="${ctx}/webResources/js/easyui1.5/jquery.easyui.min.js"></script>  
  <link rel="stylesheet" type="text/css" href="${ctx}/webResources/js/easyui1.5//easyui.css">
  <script type="text/javascript">
  function SaveRecord(){
 
     var node = parent.TwoFrame.$('#SwbsTree').treegrid('getSelected');
	 
     parent.TwoFrame.$('#SwbsTree').treegrid('update',{
			id: node.SWBS_ID,
			row: {
				SWBS_SHORT_NAME: $("#smSwbs\\.swbsShortName").val(),
				SWBS_NAME: $("#smSwbs\\.swbsName").val()
			}
		});

                var ff = document.labelForm;
               
                ff.action="UpdateTask.action?type=info";
			   	ff.submit();
  }
  
  $(function(){
	  $("#showcombobox").combobox({

		  onChange: function (n,o) {
 		  if($('#showcombobox').combobox('getValue')!=$('#showcombobox').combobox('getText') || !isStringNull($('#showcombobox').combobox('getValue'))){
		       
		       $("#smSwbs\\.codeId").val($('#showcombobox').combobox('getValue'));
		       $("#labelBottomDiv").css("display","");
		  }
  
		  }
		  });
  })
  </script>
  <body class="LabelWinClass"  >
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table width="96%">
     	                          
				<tr style="line-height: 30px;">
					<th width="15%">作业代码</th>
					<td width="85%"><input class="label_text"   name="smSwbs.swbsShortName" id="smSwbs.swbsShortName" value="${smSwbs.swbsShortName}"></td>
				</tr>
				<tr>
					<th width="15%">作业名称</th>
					<td width="85%"><input class="label_text"   name="smSwbs.swbsName" id="smSwbs.swbsName" value="${smSwbs.swbsName}"></td>
				</tr>
				<tr>
					<th width="15%">排序</th>
					<td width="85%"><input class="label_text"   name="smSwbs.seqNum" id="smSwbs.seqNum" value="${smSwbs.seqNum}"></td>
				</tr>
				<tr>
					<th width="15%">业务编码</th>
					<td width="85%"><div class="easyui-panel" style="width:100%;">
 
              <input class="easyui-combobox" id="showcombobox" style="width:100%;" data-options="
                    url:'${ctx}/sys/code/GetCodeJson.action?CodeType=PLAN_CODE&Value=ID&ShowTest=NAME&Test=CODE_SHORT_NAME,CODE_NAME&Belongto=',
                    method:'get',
                    valueField:'ID',
                    textField:'NAME',
                    panelHeight:'70',
                    iconWidth:22, 
                    height:'20', 
                    selectOnNavigation:true,
                    value:'${smSwbs.codeId}',
                    labelPosition: 'top'
                    ">
        </div></td>
				</tr>
       </table>	
       <input type="hidden" id="smSwbs.swbsId" name="smSwbs.swbsId" value="${smSwbs.swbsId}">
        <input type="hidden" id="smSwbs.codeId" name="smSwbs.codeId" value="${smSwbs.codeId}">								
       </form>
     </div> 						
  </body>
</html>
