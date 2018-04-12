<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加建设单位</title>
			
		<script>
		 
 
        
        $(function(){ 
        	var profile_id =  '${param.PROFILE_ID}';
        	var prof_type =  '${param.PROF_TYPE}';
        	$('#DatactrListTable').datagrid({
                remoteSort:false,
                fitColumns:true,
                striped:true,
                
				url: '${ctx}/sys/profile/DatactrListTable.action?PROFILE_ID='+profile_id+"&PROF_TYPE="+prof_type,
				columns:[[
					{field:'SUB_NAME',title:'所属组件 ',width:0.2,align:'left' ,sortable:true},
					{field:'MNAME',title:'所属模块 ',width:0.2,align:'left' ,sortable:true,
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
					{field:'DATACTR_NAME',title:'权限名称 ',width:0.3,align:'left' ,sortable:true},
					{field:'SELECT',title: '<input   name="Allcheckbox" type="checkbox" onclick="SetAll(this)" >',width:0.1,align:'center' ,sortable:true,
	                    formatter: function (value, rec, rowIndex) {
						var checked="";
						 if(isStringNull(rec.ENABLED_FLAG)&&rec.ENABLED_FLAG=="1"){
							 checked = "checked";
						 }
	                        return "<input type=\"checkbox\"  name=\"checkboxs\" "+checked+" onclick=\"SetBox(this)\" id=\"" + rec.DATACTR_CODE + "\" >";
	                    }
					},
					{field:'REMARK',title:'说明 ',width:0.7,align:'left' ,sortable:true},
					{field:'ENABLED_FLAG',title:'ENABLED_FLAG',width:0.7,align:'left' ,sortable:true,hidden:true},
					{field:'DATACTR_CODE',title:'DATACTR_CODE',width:0.7,align:'left' ,sortable:true,hidden:true}
					]],
				 onLoadSuccess: function(data){
	 			 	   $(".page_content").find("div[class^='datagrid-cell']").each(function(i){
		                $(this).attr("title",$(this).html());
	             })

				
				 }
 
				
			});
        	 
		  	});
					function SetAll(obj){
				$('#labelBottomDiv').css('display','');
				if(obj.checked){
					$("input[name='checkboxs']").attr("checked",true);
				}else{
					$("input[name='checkboxs']").attr("checked",false);
				}
			}  
		 	function SetBox(obj){
				$('#labelBottomDiv').css('display','');				
				if(!obj.checked){
				 	$("input[id='Allcheckbox']").attr("checked",false);
				}
			}
		 
           function SaveRecord(){
 
        	    var profile_id =  '${param.PROFILE_ID}';
			    var node = parent.TwoFrame.$('#SysProfileTable').datagrid('getSelected');
			    if(!isNull(node)){
			    	$.messager.alert("错误","请选择安全配置名称！");
			    	return;
			    }
			    var flag="";
			    var res=  IntoActionByUrl("DeleteProfctr.action?PROFILE_ID="+profile_id);
			    flag = res[0].result;
			    var datas="";
			    var i=0;
			    var length= $("input[name='checkboxs']:checked").length;
                $("input[name='checkboxs']:checked").each(function(){
                	 i = i+1;
                 	 datas = isStringNull(datas) ? datas +","+$(this).attr("id") : $(this).attr("id");
                	 if(i==length){
                		  res =   IntoActionByUrl("InsertProfctr.action?PROFILE_ID="+profile_id+"&DATACTR_CODES="+datas);
                	 }else if(i%50==0){
                		 res =   IntoActionByUrl("InsertProfctr.action?PROFILE_ID="+profile_id+"&DATACTR_CODES="+datas);
                		 datas="";
                	 }
				    
				  });
                flag = res[0].result;
			     if(flag=="success"){
			        window.location.reload();
			     }else{
			  	   $.messager.alert("错误",res[0].result)
			     }
			  }
        </script>
	</head>
	<body>
	<div class="label_div">
		<div   class="page_content" >
		<table id="DatactrListTable" class="TreeClass" ></table>
	 </div>
	 </div>
	</body>

</html>