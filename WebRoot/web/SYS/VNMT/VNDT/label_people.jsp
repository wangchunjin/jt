<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<title>联系人</title>
			
		<script>
		 
 
         $(function(){ 
        	 var vnmtId ='${param.VNMT_ID}';
        	$('#VndtTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true, 
                selectOnCheck:false,
                checkOnSelect:true,
                singleSelect:true,
				url: '${ctx}/sys/vndt/VndtTable.action?vnmtId='+vnmtId,
				frozenColumns:[[
				    {field:'VNDT_ID',checkbox:true}
				]],
				columns:[[
					{field:'NAME',title:'姓名',width:0.1,align:'left' ,sortable:true,
						sorter:function(param1,param2){
							if(!isStringNull(param1)){
								param1 = "";
							}
							if(!isStringNull(param2)){
								param2 = "";
							}
							if(param1.localeCompare(param2)<0){
								return -1;
							}else{
								return 1;
							}
						}	
					},
					{field:'OFFICE_PHONE',title:'办公室电话',width:0.1,align:'left' ,sortable:true},
					{field:'MOBILE_PHONE',title:'手机',width:0.1,align:'left',sortable:true},
					{field:'MAIL_ADDRESS',title:'EMAIL地址',width:0.1,align:'left' ,sortable:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 }
 
				
			});
        	 
		  	});
			
          function add(){
        	 
        	  var currFrameId =  parent.frameElement.id; 
              var vnmtId = '${param.VNMT_ID}';
             if(isStringNull(vnmtId)){
            createSimpleWindow("addWindow","增加联系人","${ctx}/web/SYS/VNMT/VNDT/AddPeople.jsp?currFrameId="+currFrameId+"&vnmtId="+vnmtId, 700, 300);
              }else{
            	 $.messager.alert("错误","请选择要添加联系人的单位！");
             }
          
          }
          
          function del(){
        	    var ids="";
				var rows = $('#VndtTable').datagrid('getChecked');
				if(rows.length==0){
					$.messager.alert("错误","请选中要删除的行！");
					return;
				}else{
					 for(var i=0;i<rows.length;i++){			   		     
	   			           ids = isStringNull(ids) ? ids +","+rows[i].VNDT_ID : rows[i].VNDT_ID;	    		         
					}
				}
				 
				  $.messager.confirm("提醒","确定要删除?",function(res){
            	if(res){
				var res = IntoActionByUrl("${ctx}/sys/vndt/DelVndt.action?vndtIds="+ids);
	    		if(res[0].result=="success"){
	    			 location.reload(); 
	    			 
	    		}
	    		}});
              
			}
       function edit(){
        	  var currFrameId =  parent.frameElement.id; 
         var row = $('#VndtTable').datagrid('getSelected');
          if(isNull(row)){
          createSimpleWindow("addWindow","修改联系人","${ctx}/sys/vndt/EditVndt.action?currFrameId="+currFrameId+"&vndtId="+row.VNDT_ID, 700, 300);
 
          }else{
        	  $.messager.alert("错误","请选中要修改的行！");
          }
        
     
          
          }
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table id="VndtTable" class="TreeClass" ></table>
		</div>
			<div class="LabelBottomDiv">
 	
             <epmis:button id="addtask" imageCss="icon-add" value="增加" action="add()" datactrCode=""></epmis:button>
             <epmis:button id="Delete" imageCss="icon-remove" value="删除" action="del()" datactrCode=""></epmis:button>		
            <epmis:button id="editRecord" imageCss="icon-edit" value="修改" action="edit()" datactrCode=""></epmis:button>		
			</div>
	 
	</body>

</html>