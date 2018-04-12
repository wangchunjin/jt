<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
 <script type="text/javascript">

	     function AddRecord(){
	        var currFrameId = "${param.currFrameId}";
	        
	        var carbid=$("#car\\.bid").val();
	        
	        if(carbid==null || carbid==""){
	        	$.messager.alert("错误","请选择品牌！");
 	        	return;
	        }
	        var carsysid=$("#car\\.carsysid").val();
	        if(carsysid==null || carsysid==""){
	        	$.messager.alert("错误","请选择车系！");
 	        	return;
	        }
	        var lid=$("#car\\.lid").val();
	        if(lid==null || lid==""){
	        	$.messager.alert("错误","请选择车款！");
 	        	return;
	        }
	        var cartypeida=$("#car\\.cartypeid").val();
	        if(cartypeida==null || cartypeida==""){
	        	$.messager.alert("错误","请选择车型！");
 	        	return;
	        }
			
			
			 
 	        var content = $("#sysm\\.content").val();
 	        
 	        if(content==null||content==""){
 	        	$.messager.alert("错误","车型信息不能为空！");
 	        	return;
 	        }
 	        
 	    
 	        
 	       
 	     

	        var res = $('#addForm').SubmitForm("${ctx}/sysm/tsysm/save.action");
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
	     }
	    
	     function changeValue1(){
		     	var obj=$("#car\\.bid").val();
		     		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID="+obj,"car\\.carsysid","",new Array("=--请选择--")); 
		     		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID=0","car\\.lid","",new Array("=--请选择--"));
		     		SetSelect("SELECT id,name FROM T_CARSYS WHERE isdel='0' and BID=0","car\\.cartypeid","",new Array("=--请选择--")); 
		     	
		     		
			}
			function changeValue2(){
		     	var obj=$("#car\\.carsysid").val();
		     		SetSelect("SELECT ID,TITLE FROM T_SORT WHERE isdel='0' and sid="+obj,"car\\.lid","",new Array("=--请选择--")); 
		     		SetSelect("SELECT ID,NAME FROM T_CARTYPE WHERE isdel='0' and sid=0","car\\.cartypeid","",new Array("=--请选择--")); 
		     		
			}
			function changeValue3(){
		     	
		     	var carsysid=$("#car\\.carsysid").val();
		    	var carbid=$("#car\\.bid").val();
		    	var carlid=$("#car\\.lid").val();
		     	SetSelect("SELECT id,title FROM t_car where isdel='0' and bid="+carbid+" and carsysid="+carsysid+" and lid="+carlid,"car\\.cartypeid","",new Array("=--请选择--")); 
		     	
		     		
			}
			
			function changvalue4(){
				var cid=$("#car\\.cartypeid").val();
				$("#oid").val(cid);
				
			}
			
			
 </script>

 </head>
  <body class="NewWinClass">

  <div style="height:430px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
		<tr  id="ppp">
				<th width="15%"><span style="color: red;">*</span>品牌：</th>
				<td width="12%">						
					<epmis:select id="car.bid" define="SELECT ID,TITLE FROM T_BRAND where isdel='0' " attr="style='width: 100%;'" onChange="changeValue1()" ></epmis:select>
				</td>
			</tr >
				
			<tr id="pppp">
				<th width="15%"><span style="color: red;">*</span>车系：</th>
				<td width="12%">						
					<epmis:select id="car.carsysid" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select>
				</td>
			
			</tr>
			
			<tr id="ppppp">
				<th width="15%"><span style="color: red;">*</span>车款：</th>
				<td width="12%">						
					<epmis:select id="car.lid" define="" attr="style='width: 100%;'" onChange="changeValue3()" ></epmis:select>
				</td>
				
				
			</tr>
			<tr id="pppppp">
				<th width="15%"><span style="color: red;">*</span>车型：</th>
				<td width="12%">
					<epmis:select id="car.cartypeid" define="" attr="style='width: 100%;'" onChange="changvalue4();"></epmis:select>
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;">*</span>车型信息：</th>
				<td width="30%">
					<textarea name="sysm.content" id="sysm.content" rows="5" cols="10"></textarea>
				</td>
				
				
			</tr>
			<tr>
				
				<td width="30%">
				<input class="form_text" type="hidden"  name="sysm.oid" id="oid"  value="">
				<input class="form_text" type="hidden"  name="sysm.title" id="title"  value="系统消息">
				<input class="form_text" type="hidden"  name="sysm.status" id="status"  value="0">
				<input class="form_text" type="hidden"  name="sysm.type" id="type"  value="1">
				<input class="form_text" type="hidden"  name="sysm.isduqu" id="isduqu"  value="0">
				<input class="form_text" type="hidden"  name="sysm.content" id="chuli"  value="1">
				</td>				
				</td>
				
				
			</tr>
			
			
       </table>
      
       
</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
