<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  	
	//楼盘详情
  	function xq(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');
  		if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中查看详情的楼盘！");
			return;
		}
    	
  		createSimpleWindow("AroundWindow","楼盘详情","${ctx}/prosalexqq/tprosalexqq/findById.action?lid="+rows[0].id, 650,600);
    }
	
	//楼盘详情
  	function xqq(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');
  		if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中查看详情的楼盘！");
			return;
		}
    	
  		createSimpleWindow("AroundWindow","楼盘详情","${ctx}/proxq/tproqq/findById.action?lid="+rows[0].id, 650,600);
    }
  	
  	
  //金牌顾问点评楼盘
  	function comment(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要点评的楼盘！");
			return;
		}
  		createSimpleWindow("AroundWindow","楼盘点评信息","${ctx}/web/LOUPAN/COMMENT/FS.jsp?lid="+rows[0].id, 1000,600);
    }
  	
  	
  //楼盘广告
  	function adv(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑广告的楼盘！");
			return;
		}
  		createSimpleWindow("AroundWindow","楼盘广告信息","${ctx}/web/ADV/LPADV/FS.jsp?lid="+rows[0].id, 1000,600);
    }
  	
  //楼盘置顶or取消
  	function zdorqx(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
    	
    	if(rows.length==0){
    		
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑的楼盘！");
			return;
		}//这里的lid为户型id
//   		createSimpleWindow("HousepicWindow","户型图片类别","${ctx}/web/LOUPAN/HOUSE/PIC/FS.jsp?lid="+rows[0].id, 1000,600);
		if(rows[0].ordertime=="1990-01-01 00:00:00"){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定设置为置顶楼盘?",function(res){
	        	if(res){
					var res = IntoActionByUrl("${ctx}/prosale/tprosale/zdorqx.action?id="+rows[0].id+"&zx=1");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","置顶成功！");
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}else{
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要取消置顶吗?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/prosale/tprosale/zdorqx.action?id="+rows[0].id+"&zx=0");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","取消置顶成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		}
  	
  	
  	
  	
  //楼盘户型有礼或无礼
  	function yl(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
    	
    	if(rows.length==0){
    		
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑的楼盘！");
			return;
		}//这里的lid为户型id
//   		createSimpleWindow("HousepicWindow","户型图片类别","${ctx}/web/LOUPAN/HOUSE/PIC/FS.jsp?lid="+rows[0].id, 1000,600);
		if(rows[0].isgift==0){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定设置为转发有礼吗?",function(res){
	        	if(res){
					var res = IntoActionByUrl("${ctx}/prosale/tprosale/yl.action?id="+rows[0].id+"&yl=1");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","推荐成功！");
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		if(rows[0].isgift==1){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要取消转发有礼吗?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/prosale/tprosale/yl.action?id="+rows[0].id+"&yl=0");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","取消推荐成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		}
  	
  	
  	
  	
  	
  	
  	
  	
  	
  //楼盘户型推荐或取消推荐
  	function tjorqx(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
    	
    	if(rows.length==0){    		
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑的楼盘！");
			return;
		}//这里的lid为户型id
//   		createSimpleWindow("HousepicWindow","户型图片类别","${ctx}/web/LOUPAN/HOUSE/PIC/FS.jsp?lid="+rows[0].id, 1000,600);
		
   	 	
		
		
		
		
		
		if(rows[0].type==0){
			//针对楼盘超过5个推荐进行判断
			var result = GetXmlBySql("SELECT count(id)num FROM t_prosale WHERE isdel='0' and type='1'");
	   	 	var sums=result[0].num;
			if(sums/1==5 || sums/1>5){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","已有5个楼盘被推荐！");
				return;
			}
			
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要推荐吗?",function(res){
	        	if(res){
					var res = IntoActionByUrl("${ctx}/prosale/tprosale/tj.action?id="+rows[0].id+"&istj=1");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","推荐成功！");
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		if(rows[0].type==1){
			parent.LeftFrame.TwoFrame.$.messager.confirm("提醒","确定要取消推荐吗?",function(res){

	        	if(res){
					var res = IntoActionByUrl("${ctx}/prosale/tprosale/tj.action?id="+rows[0].id+"&istj=0");
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","取消推荐成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		}
		}
		
		
  	
  	
  	
  //楼盘户型
  	function house(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑户型的楼盘！");
			return;
		}
  		createSimpleWindow("HouseWindow","楼盘户型列表","${ctx}/web/LOUPAN/HOUSE/HOU/FS.jsp?lid="+rows[0].id, 1000,600);
    }
  //楼盘楼栋
  	function ban(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑楼栋的楼盘！");
			return;
		}
  		createSimpleWindow("AroundWindow","楼盘楼栋","${ctx}/web/LOUPAN/BAN/FS.jsp?lid="+rows[0].id, 1000,600);
    }
  	
  	
  	
  //楼盘图片类别
  	function pic(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');	
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑图片的楼盘！");
			return;
		}
  		createSimpleWindow("AroundWindow","楼盘图片类别","${ctx}/web/LOUPAN/PIC/FS.jsp?lid="+rows[0].id, 1000,600);
    }
  	
  	
  	
  	//楼盘动态
  	function dt(){
  		var currFrameId =  parent.frameElement.id;
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑动态楼盘！");
			return;
		}
  		createSimpleWindow("DtWindow","楼盘动态","${ctx}/web/LOUPAN/LPDT/FS.jsp?lid="+rows[0].id+"&currFrameId="+currFrameId,800,600);
    }
  	//楼盘周边
  	function around(){
  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
    	
    	if(rows.length==0){
			parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要编辑周边的楼盘！");
			return;
		}
  		createSimpleWindow("AroundWindow","楼盘周边列表","${ctx}/web/LOUPAN/LPAROUNN/FS.jsp?lid="+rows[0].id, 1000,600);
    }
	  	function add(){
	  		var cid='${param.cid}';	  		
	     	var currFrameId =  parent.frameElement.id; 	     	
	        createSimpleWindow("addWindow","新增","${ctx}/web/LOUPAN/LP/addPingCe.jsp?currFrameId="+currFrameId+"&cid="+cid, 700,600);
	    }
	    function update(){
	   
	    	var currFrameId =  parent.frameElement.id; 	   
	    	var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getSelections');			
	    	
	    	if(rows.length==0){
				parent.LeftFrame.TwoFrame.$.messager.alert("错误","请选中要修改的楼盘！");
				return;
			}
			createSimpleWindow("eidtWindow","修改配置","${ctx}/prosale/tprosale/findById.action?id="+rows[0].id+"&currFrameId="+currFrameId,700,600);
			
	    }
			
	    
	  	function del(){
	  		
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
					var res = IntoActionByUrl("${ctx}/prosale/tprosale/delete.action?id="+ids);
					//alert(res[0].result);
		    		if(res[0].result=="success"){
						parent.LeftFrame.TwoFrame.$.messager.alert("提醒","删除成功！");	
			    		parent.LeftFrame.TwoFrame.location.reload(); 
	    			}
				}
			});
		 }
	  	
	  	function sc(){
	  		var rows = parent.LeftFrame.TwoFrame.$('#CarTable').datagrid('getChecked');	
	  		 createSimpleWindow("scoreWindow","楼盘收藏明细","${ctx}/web/LOUPAN/LPCOLLENT/FS.jsp?uid="+rows[0].id,1000,600);
	  	}
  	</script>
  </head>
  <body>
  <table width="100%">
       <tr>
          <td width="*" align="right">
           <epmis:button id="NewRecord" imageCss="icon-add" value="收藏" action="sc()" datactrCode="" ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-add" value="置顶/取消" action="zdorqx()" datactrCode="" ></epmis:button>
		  <epmis:button id="NewRecord" imageCss="icon-add" value="旧详情" action="xqq()" datactrCode="loupan_xq.edit" ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-add" value="详情" action="xq()" datactrCode="loupan_xq.edit" ></epmis:button>
<!--           <epmis:button id="NewRecord" imageCss="icon-add" value="点评" action="comment()" datactrCode="loupan_comment.edit" ></epmis:button> -->
          <epmis:button id="NewRecord" imageCss="icon-add" value="广告" action="adv()" datactrCode="loupan_adv.edit" ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-add" value="户型" action="house()" datactrCode="loupan_house.edit" ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-add" value="楼栋" action="ban()" datactrCode="loupan_ban.edit" ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-add" value="图片" action="pic()" datactrCode="loupan_pic.edit" ></epmis:button>
          <epmis:button id="NewRecord" imageCss="icon-add" value="动态" action="dt()" datactrCode="loupan_dt.edit" ></epmis:button>
             <epmis:button id="NewRecord" imageCss="icon-add" value="周边" action="around()" datactrCode="loupan_around.edit" ></epmis:button>             
             <epmis:button id="NewRecord" imageCss="icon-add" value="推荐/取消" action="tjorqx()" datactrCode="loupan_tjorqx.edit" ></epmis:button>
             <epmis:button id="NewRecord" imageCss="icon-add" value="有礼/无礼" action="yl()" datactrCode="loupan_yl.edit" ></epmis:button>            
          	 <epmis:button id="NewRecord" imageCss="icon-add" value="增加" action="add()" datactrCode="loupan_add.edit" ></epmis:button>
          	 <epmis:button id="DeleteRecord" imageCss="icon-remove" value="删除" action="del()" datactrCode="loupan_delete.edit"  ></epmis:button>
          	 <epmis:button id="NewRecord" imageCss="icon-edit" value="修改" action="update()" datactrCode="loupan_update.edit" ></epmis:button>
             <epmis:button id="reload" imageCss="icon-reload" value="刷新" action="parent.location.reload()"></epmis:button>
          </td>
          <td width="50">&nbsp;</td>
       </tr>
  </table>
  </body>
</html>
