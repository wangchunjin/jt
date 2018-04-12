<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	function single(){
  		//<hxl
  		var logon_value='${sessionScope.dlogin}';//时效
  		 var userid_hxl='${sessionScope.UserInfo.userId}';//用户id
  		 var r_hxl = GetXmlBySql("select logon_value from cm_users where USER_ID='"+userid_hxl+"'");
  		 //判断登录用户session 时效
  		 if(r_hxl[0].logon_value!=logon_value){
  			alert("你被挤下线了,请保护好你的密码！");
  			 parent.parent.parent.location='<c:url value="/logout.action"/>';
  		 }
  		 //>hxl
  	}
  	function wz(){
  		single();
     	var currFrameId =  parent.frameElement.id;
     	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要排序的首页轮播！");
			return;
		}
        createSimpleWindow("WzWindow","位置展示","${ctx}/web/JT/ADV/wz.jsp?id="+rows[0].id+"&currFrameId="+currFrameId, 400,150);
    }
	  	function add(){
	  		single();
	  		var cid='${param.cid}';
	     	var currFrameId =  parent.frameElement.id;
	        createSimpleWindow("addWindow","新增","${ctx}/web/JT/ADV/addPingCe.jsp?currFrameId="+currFrameId+"&cid="+cid, 800,600);
	    }
	    function update(){
	    	single();
	    	var currFrameId =  parent.frameElement.id;
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要修改的行！");
				return;
			}
			createSimpleWindow("eidtWindow","修改配置","${ctx}/adv/tadv/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,800,600);
			
	    }
			
			
	    
	  	function del(){
	  		single();
	  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
			var ids="";
			for(var i=0;i<rows.length;i++){
				if(i==0)
				ids=ids+rows[i].id;
				else
				ids=ids+","+rows[i].id;
			}			
			if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要删除的行！");
				return;
			}
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要删除?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/adv/tadv/delete.action?id="+ids);
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","删除成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		 }
  	</script>
  </head>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
             <epmis:button id="NewRecord" imageCss="icon-save" value="展示置位" action="wz()" datactrCode="adv_wz.edit" ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="adv_add.edit" ></epmis:button>
          	 <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode="adv_delete.edit"  ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-edit" value="修改" action="update()" datactrCode="adv_update.edit" ></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
