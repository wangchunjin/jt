<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
  var OpenProjId="";
  function InitProjId(id){
	  OpenProjId = id;
 
	  if( parent.TwoFrame.$('#ProjTree').length == 0){
		  	$('#openProj').css("display","none");
			$('#deleteRecord').css("display","none");
			$('#newRecord').css("display","none");
			$('#getnewRecord').css("display","none");			
			$('#move').css("display","none");
			return;
	  }
	  var selNodeObj= parent.TwoFrame.$('#ProjTree').treegrid('getSelected');
	  
	if(isNull(selNodeObj))
	{	
		var projNodeFlag = selNodeObj.PROJ_NODE_FLAG; 
		if(projNodeFlag=="Y"){
			$('#openProj').css("display","");
			$('#deleteRecord').css("display","");
			$('#newRecord').css("display","none");
			$('#getnewRecord').css("display","none");
			$('#move').css("display","");
		}else{
			$('#openProj').css("display","none");
			$('#deleteRecord').css("display","none");
			$('#newRecord').css("display","");
			$('#getnewRecord').css("display","");
			$('#move').css("display","none");
		}
	}else{
			$('#openProj').css("display","none");
			$('#deleteRecord').css("display","none");
			$('#newRecord').css("display","none");
			$('#getnewRecord').css("display","none");
			$('#move').css("display","none");
	}
  }
  function addProj(){
	  
    var selNodeObj= parent.TwoFrame.$('#ProjTree').treegrid('getSelected');	
	if(isNull(selNodeObj)&&selNodeObj.PROJ_NODE_FLAG=='N')
	{	
		var projNodeFlag = selNodeObj.PROJ_NODE_FLAG; 
		var id = "";
		if(projNodeFlag=='N' )
			id = selNodeObj.PROJ_ID;
		else
			id = selNodeObj.PARENT_PROJ_ID;
		if(isStringNull(id))
		{
		   var result = IntoActionByUrl("${ctx}/sys/proj/CheckProjNum.action");
	       if(!isStringNull(result[0].result)){
	    	    var currFrameId =  parent.frameElement.id; 
                createSimpleWindow("addProjWindow","增加项目","${ctx}/web/SYS/PROJ/ADD/FS.jsp?currFrameId="+currFrameId+"&ParentProjId="+id, 850, 500);
		    }else{
		        alert(result[0].result);
		    }
		}
	}else
	{
		parent.TwoFrame.$.messager.alert("错误","请选择项目要挂接的EPS!");
	}
  }
          
          function delProj(){
        	 
          var node = parent.TwoFrame.$('#EpsTree').treegrid('getSelected');
         if(isNull(node)){
                $.ajax({
	                	url : "${ctx}/sys/eps/DelEps.action",
	                	type: 'post',
	                    dataType: 'json',
	                    async : false,
	                    data:{ 
	                    	"proj_id" : node.id
	                    },
	                    error: function(){
	                    	parent.TwoFrame.$.messager.alert('错误','删除时出错！');
	                    },
	                    success: function(data){
	                        if(data[0].result=="success"){
	                                 
	                          parent.TwoFrame.$('#EpsTree').treegrid('remove', node.id);  
                              parent.TwoFrame.$('#EpsTree').treegrid('reloadFooter');
	                        }else{
	                        	parent.TwoFrame.$.messager.alert('错误','删除时出错！');
	                        }
	                    }
	                });	
         }else{
           parent.TwoFrame.$.messager.alert("错误","请选择要删除的行数据");
         }
         
          }
         
		 function openProj()
		{
			var type = parent.LeftFrame.$("#type").val();
	       if(type=="NO_CATTYE"){
	    	 //   var selRowObj= parent.TwoFrame.$('#ProjTable').datagrid('getSelected');
	       }else{
	    	    var selNodeObj= parent.TwoFrame.$('#ProjTree').treegrid('getSelected');
	    	    if(isNull(selNodeObj))
			    {
					var projId = selNodeObj.PROJ_ID;
					var projshortName = selNodeObj.PROJ_SHORT_NAME;
					var projName = selNodeObj.PROJ_NAME;
					if(projId != OpenProjId){	
					 
					   IntoActionByUrl("${ctx}/sys/proj/ChangeProj.action?projId="+projId);
				 	 parent.TwoFrame.$(".page_content").find("span").filter(".icon-openproj").addClass("icon-proj").removeClass("icon-openproj");					
				 	 parent.TwoFrame.$("tr[node-id='"+projId+"']").find("span").filter(".icon-proj").addClass("icon-openproj").removeClass("icon-proj");
	               
				 	  parent.parent.$("#CurrProj").html(projshortName+"--"+projName) ;
				 	   parent.parent.$("#CurrProjId").val(projId) ;
				 	 OpenProjId =  projId;
					  
					} 
										 
				}else{
					parent.TwoFrame.$.messager.alert("错误","请选择一个项目");
				} 
	       }
	 

		}
		 function delProj(){
			 	var selNodeObj= parent.TwoFrame.$('#ProjTree').treegrid('getSelected');
	    	    if(isNull(selNodeObj))
			    {
	    	    	var projId = selNodeObj.PROJ_ID;
	    	    	var proj_node_flag = selNodeObj.PROJ_NODE_FLAG;
	    	    	if(proj_node_flag !="Y"){
	    	    		parent.TwoFrame.$.messager.alert("系统禁止删除ESP!");
	    	    	}else{
	    	    				parent.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){
                              	if(res){
                              		  var result =IntoActionByUrl("${ctx}/sys/proj/DelProj.action?projId="+projId);   
                              		   parent.TwoFrame.$('#ProjTree').treegrid('remove', projId);  
                                       parent.TwoFrame.$('#ProjTree').treegrid('reloadFooter');
                              	   }
                              	});
                              	
	    	    	}
			    }else{
					parent.TwoFrame.$.messager.alert("错误","请选择一个项目");
				}
		 }
		 
		 function move(){ 
        	
        	 var currFrameId =  parent.frameElement.id; 
        	 var node = parent.TwoFrame.$('#ProjTree').treegrid('getSelected');
        	 if(isNull(node)){     
        			createSimpleWindow("addWindow","移动WBS","${ctx}/web/SYS/PROJ/move.jsp?currFrameId="+currFrameId, 500, 550);	 
        	 }else{
                parent.TwoFrame.$.messager.alert("错误","请选择要移动的项目");
             }
         }
		 function getProj(){
			  var selNodeObj= parent.TwoFrame.$('#ProjTree').treegrid('getSelected');	
				if(isNull(selNodeObj)&&selNodeObj.PROJ_NODE_FLAG=='N')
				{	
					var projNodeFlag = selNodeObj.PROJ_NODE_FLAG; 
					var id = "";
					if(projNodeFlag=='N' )
						id = selNodeObj.PROJ_ID;
					else
						id = selNodeObj.PARENT_PROJ_ID;
					if(isStringNull(id))
					{
					   var result = IntoActionByUrl("${ctx}/sys/proj/CheckProjNum.action");
				       if(!isStringNull(result[0].result)){
				    	    var currFrameId =  parent.frameElement.id; 
			                createSimpleWindow("addProjWindow","增加项目","${ctx}/web/SYS/PROJ/GET/FS.jsp?currFrameId="+currFrameId+"&ParentProjId="+id, 850, 500);
					    }else{
					        alert(result[0].result);
					    }
					}
				}else
				{
					parent.TwoFrame.$.messager.alert("错误","请选择项目要挂接的EPS!");
				}
		 }
  </script>
  <body onload="InitProjId('${UserInfo.getProjId()}')">
  <table width="100%">
       <tr>
          <td width="*" align="right">
             <epmis:button id="btn4" imageCss="icon-expand" value="全部展开" action="parent.TwoFrame.exportAll('ProjTree')"></epmis:button>
             <epmis:button id="btn5" imageCss="icon-close" value="全部折叠" action="parent.TwoFrame.closeAll('ProjTree')"></epmis:button> 
             <epmis:button id="openProj" imageCss="icon-open" value="打开" action="openProj()"></epmis:button>
             <epmis:button id="getnewRecord" imageCss="icon-add" value="获取" action="getProj()" datactrCode="SYS_PROJ.QYXM.add_del"></epmis:button>
             <epmis:button id="newRecord" imageCss="icon-add" value="增加" action="addProj()" datactrCode="SYS_PROJ.QYXM.add_del"></epmis:button>
             <epmis:button id="move" imageCss="icon-cut" value="移动" action="move()" datactrCode="SYS_PROJ.QYXM.add_del" ></epmis:button>     
             <epmis:button id="deleteRecord" imageCss="icon-remove" value="删除" action="delProj()" datactrCode="SYS_PROJ.QYXM.add_del"></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
