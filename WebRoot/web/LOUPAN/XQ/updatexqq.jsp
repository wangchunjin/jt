<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript" src="js/imagePreview.js"></script>
<script type="text/javascript">
	
	function UpdateRecord() {
// 		var currFrameId = "${param.currFrameId}";
// 		var content = tinyMCE.get('CONTENTT').getContent().replace(/"/g, "\'");
// 		$("#content").val(content);
		

	
	        
		//开盘时间
		var tprotime =""; 
		var stprotime=document.getElementsByName("protime").length;		
		var sum=1;
		$('input[name="protime"]').each(function(){ 
			if(sum==1){
				
			}else if(sum==stprotime){
				tprotime=tprotime+$(this).val();
			}else{
				tprotime=tprotime+$(this).val()+"xl";
			}
	
			sum+=1;
		});
		sum=1;
		$("#protime").val(tprotime);
		
		
		//售卖楼栋
		var tpromai =""; 
		var stpromai=document.getElementsByName("promai").length;		
		
		$('input[name="promai"]').each(function(){ 
			if(sum==1){
				
			}else if(sum==stpromai){
				tpromai=tpromai+$(this).val();
			}else{
				tpromai=tpromai+$(this).val()+"xl";
			}
	
			sum+=1;
		});
		sum=1;
		$("#promai").val(tpromai);
		
		
		//交房时间
		var tprojftime =""; 
		var stprojftime=document.getElementsByName("projftime").length;		
		
		$('input[name="projftime"]').each(function(){ 
			if(sum==1){
				
			}else if(sum==stprojftime){
				tprojftime=tprojftime+$(this).val();
			}else{
				tprojftime=tprojftime+$(this).val()+"xl";
			}
	
			sum+=1;
		});
		sum=1;
		$("#projftime").val(tprojftime);
		
		//分期信息
// 		var tprofq =""; 
// 		var stprofq=document.getElementsByName("projftime").length;		
		
// 		$('input[name="profq"]').each(function(){ 
// 			if(sum==1){
				
// 			}else if(sum==stprofq){
// 				tprofq=tprofq+$(this).val();
// 			}else{
// 				tprofq=tprofq+$(this).val()+"xl";
// 			}
	
// 			sum+=1;
// 		});
// 		sum=1;
// 		$("#profq").val(tprofq);
		
		//预售许可证
		var tproxk =""; 
		var stproxk=document.getElementsByName("proxk").length;		
		
		$('input[name="proxk"]').each(function(){ 
			if(sum==1){
				
			}else if(sum==stproxk){
				tproxk=tproxk+$(this).val();
			}else{
				tproxk=tproxk+$(this).val()+"xl";
			}
	
			sum+=1;
		});
		sum=1;
		$("#proxk").val(tproxk);
		
		
		
		//发证时间
		var tprofztime =""; 
		var stprofztime=document.getElementsByName("profztime").length;		
		
		$('input[name="profztime"]').each(function(){ 
			if(sum==1){
				
			}else if(sum==stprofztime){
				tprofztime=tprofztime+$(this).val();
			}else{
				tprofztime=tprofztime+$(this).val()+"xl";
			}
	
			sum+=1;
		});
		sum=1;
		$("#profztime").val(tprofztime);
		
		
		//发证时间
		var tpropro =""; 
		var stpropro=document.getElementsByName("propro").length;		
		
		$('input[name="propro"]').each(function(){ 
			if(sum==1){
				
			}else if(sum==stpropro){
				tpropro=tpropro+$(this).val();
			}else{
				tpropro=tpropro+$(this).val()+"xl";
			}
	
			sum+=1;
		});
		sum=1;
		$("#propro").val(tpropro);
		
		
	
		var res = $('#addForm').SubmitForm("${ctx}/prosalexqq/tprosalexqq/update.action");
		if (res[0].result == "success") {
			location.reload();
			parent.close_win('eidtWindow');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}

	}
	 $(function(){
		 //开盘时间
		 var tprotime="${proxq[0].protime }";		
		 var sprotime=tprotime.split("xl");
		 
		//售卖楼栋
		 var tpromai="${proxq[0].promai }";		
		 var spromai=tpromai.split("xl");
		 
		 
		//交房时间
		 var tprojftime="${proxq[0].projftime }";		
		 var sprojftime=tprojftime.split("xl");
		 
		 
		//分期信息
// 		 var tprofq="${proxq[0].profq }";		
// 		 var sprofq=tprofq.split("xl");
		 
		 for(var i=0;i<sprotime.length;i++){
			 var root = document.getElementById("tbody");
			  var allRows = root.getElementsByTagName('tr');
			  var allCells = allRows[0].getElementsByTagName('td');
			  var newRow = root.insertRow();
			  var newCell0 = newRow.insertCell();
			  var newCell1 = newRow.insertCell();
			  var newCell2 = newRow.insertCell();
			  var newCell3 = newRow.insertCell();
// 			  var newCell4 = newRow.insertCell();
			  newCell0.innerHTML +="<input name='protime' style='width: 100%;'  type='text' value='"+sprotime[i]+"' />";
			  newCell1.innerHTML +="<input name='promai' style='width: 100%;' type='text' value='"+spromai[i]+"' />";
			  newCell2.innerHTML +="<input name='projftime' style='width: 100%;'  type='text' value='"+sprojftime[i]+"' />";
			  newCell3.innerHTML= allCells[3].innerHTML;
// 			  newCell3.innerHTML +="<input name='profq'  type='text' value='"+sprofq[i]+"' />";
// 			  newCell4.innerHTML = allCells[4].innerHTML;
		 }
		 
		 //预售许可证
		 var tproxk="${proxq[0].proxk }";		
		 var sproxk=tproxk.split("xl");
		 
		//发证时间
		 var tprofztime="${proxq[0].profztime }";		
		 var sprofztime=tprofztime.split("xl");
		 
		 
		//绑定楼栋
		 var tpropro="${proxq[0].propro }";		
		 var spropro=tpropro.split("xl");
		 
		 for(var i=0;i<sproxk.length;i++){
			 var root = document.getElementById("tbodys");
			  var allRows = root.getElementsByTagName('tr');
			  var allCells = allRows[0].getElementsByTagName('td');
			  var newRow = root.insertRow();
			  var newCell0 = newRow.insertCell();
			  var newCell1 = newRow.insertCell();
			  var newCell2 = newRow.insertCell();
			  var newCell3 = newRow.insertCell();
			
			  newCell0.innerHTML +="<input name='proxk' style='width: 100%;' type='text' value='"+sproxk[i]+"' />";
			  newCell1.innerHTML +="<input name='profztime' style='width: 100%;' type='text' value='"+sprofztime[i]+"' />";
			  newCell2.innerHTML +="<input name='propro' style='width: 100%;'  type='text' value='"+spropro[i]+"' />";
			  newCell3.innerHTML = allCells[3].innerHTML;
		 }
		 
		 
	  });
	 
	
	 function addRow()
	 {
		 
// 		 var str="<tbody id='tbody'>"
// 			+"<tr >"
// 			+"<td><input id='a' type='text' /></td>"
// 			+"<td><input id='b' type='text' /></td>"
// 			+"<td><input id='c' type='text'/></td>"
// 			+"<td><input id='d' type='text'/></td>"
// 			+"<td><input type='button' value='remove' onclick='removeRow(this.parentNode.parentNode)'/></td>"
// 			+"</tr>"

// 			+"</tbody>";
// 			$("#t1").html(str);
			
	  var root = document.getElementById("tbody");
	  var allRows = root.getElementsByTagName('tr');
	  var allCells = allRows[0].getElementsByTagName('td');
	  var newRow = root.insertRow();
	  var newCell0 = newRow.insertCell();
	  var newCell1 = newRow.insertCell();
	  var newCell2 = newRow.insertCell();
	  var newCell3 = newRow.insertCell();
// 	  var newCell4 = newRow.insertCell();
	  newCell0.innerHTML = allCells[0].innerHTML;
	  newCell1.innerHTML = allCells[1].innerHTML;
	  newCell2.innerHTML = allCells[2].innerHTML;
	  newCell3.innerHTML = allCells[3].innerHTML;
// 	  newCell4.innerHTML = allCells[4].innerHTML;
	 
	 }
	 function addRow2()
	 {		
	  var root = document.getElementById("tbodys");
	  var allRows = root.getElementsByTagName('tr');
	  var allCells = allRows[0].getElementsByTagName('td');
	  var newRow = root.insertRow();
	  var newCell0 = newRow.insertCell();
	  var newCell1 = newRow.insertCell();
	  var newCell2 = newRow.insertCell();
	  var newCell3 = newRow.insertCell();
	
	  newCell0.innerHTML = allCells[0].innerHTML;
	  newCell1.innerHTML = allCells[1].innerHTML;
	  newCell2.innerHTML = allCells[2].innerHTML;
	  newCell3.innerHTML = allCells[3].innerHTML;
	 
	 
	 }
	 function removeRow(inputobj)  
	 {  
	     if(inputobj==null) return;  
	     var parentTD = inputobj.parentNode;  
	     var parentTR = parentTD.parentNode;  
	     var parentTBODY = parentTR.parentNode;  
	     parentTBODY.removeChild(parentTR);  
	 }

	  
</script>
<style type="text/css">
#preview {
	width: 100px;
	height: 130px;
	overflow: hidden;
}

#imghead {
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
}
</style>
</head>
<body class="NewWinClass">
	 <div style="height:500px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 10px;margin-left:10px;width: 96%;" >
			<tr>
				<td>
					<input type="hidden" name="proxq.l_id" id="lid"  value="${param.lid }"/>
					<input type="hidden" name="proxq.id" id="id"  value="${proxq[0].id }"/>
				</td>
			</tr>
			<tr align="left">			
				<th width="10%"><span style="color: #ADD8E6;font-size:18px;">基本信息</span></th>
				<td width="20%">				
				</td>
				<th width="10%"></th>
				<td width="20%"></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;"></span>物业类型：</th>
				<td width="20%">				
				<input class="form_text"  name="proxq.wuyeleixing" id="wuyeleixing"  value="${proxq[0].wuyeleixing }" ></td>
				<th width="10%">参考价格：</th>
				<td width="20%"><input class="form_text"  name="proxq.price" id="price"  value="${proxq[0].price }" ></td>						
				
			</tr>
			
			<tr align="right">			
				<th width="10%"><span style="color: red;"></span>楼盘地址：</th>
				<td width="20%">	
				<input class="form_text"  name="proxq.prosaleaddress" id="prosaleaddress"  value="${proxq[0].prosaleaddress }" ></td>
				<th width="10%">售楼处地址：</th>
				<td width="20%"><input class="form_text"  name="proxq.buyaddress" id="buyaddress"  value="${proxq[0].buyaddress }" ></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;"></span>开发商：</th>
				<td width="20%">				
				<input class="form_text"  name="proxq.kaifashang" id="kaifashang"  value="${proxq[0].kaifashang }" ></td>
				<th width="10%">装修情况：</th>
				<td width="20%">
				<input class="form_text"  name="proxq.zx" id="zx"  value="${proxq[0].zx }" >
				</td>						
				
			</tr>
			<tr align="right">			
				
				<th width="10%">区域位置：</th>
				<td width="20%"><input class="form_text"  name="proxq.areaaddress" id="areaaddress"  value="${proxq[0].areaaddress }" ></td>						
				<th width="10%" style="display: none;"><span style="color: red;"></span>项目特色：</th>
				<td width="20%" style="display: none;">				
				<input class="form_text"  name="proxq.protese" id="protese"  value="${proxq[0].protese }" ></td>
			</tr>
			<tr align="left">			
				<th width="10%"><span style="color: #ADD8E6;font-size:18px;">开盘时间</span></th>
				<td width="20%">				
				</td>
				<th width="10%"></th>
				<td width="20%"></td>						
				
			</tr>
			<tr >		
				<td><input style="display: none;"  name='proxq.protime' id="protime" type='text' value=""/></td>
 				<td><input style="display: none;" name='proxq.promai' id="promai" type='text' value=""/></td>
 				<td><input style="display: none;" name='proxq.projftime' id="projftime" type='text'value=""/></td>
<!--  				<td><input style="display: none;" name='proxq.profq' id="profq" type='text' value=""/></td> -->
				
			</tr>
			
			<tr  align="center"  style="background-color:#B0C4DE;">		
			
				<th width="20%">开盘时间</th>
				<th width="20%">售卖楼栋</th>
				<th width="20%">交房时间</th>
<!-- 				<th width="20%">分期信息</th> -->
				
			</tr>
			
			<tbody id='tbody'>
 			<tr style="display: none;">
 			<td><input name='protime'  type='text' style="width: 100%;"/></td>
 			<td><input name='promai' type='text' style="width: 100%;"/></td>
 			<td><input name='projftime' type='text' style="width: 100%;"/></td>
<!--  			<td><input name='profq' type='text'/></td> -->
 			<td><input type='button' value='删除' onclick='removeRow(this)'/></td>
 			</tr>

 			</tbody>
			
			<tr align="right">			
				<th width="10%"></th>
				<td width="20%">				
				</td>
				<th width="10%"><input type="button" value="增加" onclick="addRow()" /></th>
				<td width="20%">
				
				
				</td>						
				
			</tr>
			
			
			<tr align="left">			
				<th width="10%"><span style="color: #ADD8E6;font-size:18px;">规划信息</span></th>
				<td width="20%">				
				</td>
				<th width="10%"></th>
				<td width="20%"></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;"></span>建筑类型：</th>
				<td width="20%">
				<input class="form_text"  name="proxq.jianzhutype" id="jianzhutype"  value="${proxq[0].jianzhutype }" ></td>
				<th width="10%">规划户数：</th>
				<td width="20%"><input class="form_text"  name="proxq.husum" id="husum"  value="${proxq[0].husum }" ></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;"></span>绿化率：</th>
				<td width="20%">				
				<input class="form_text"  name="proxq.lvhua" id="lvhua"  value="${proxq[0].lvhua }" ></td>
				<th width="10%">产权年限：</th>
				<td width="20%"><input class="form_text"  name="proxq.chanquanyear" id="chanquanyear"  value="${proxq[0].chanquanyear }" ></td>						
				
			</tr>
			
			<tr align="right">
				<th width="10%"><span style="color: red;"></span>建筑面积：</th>
				<td width="20%">
				<input class="form_text"  name="proxq.buildingarea" id="buildingarea"  value="${proxq[0].buildingarea }" ></td>
				<th width="10%">容积率：</th>
				<td width="20%"><input class="form_text"  name="proxq.rongjil" id="rongjil"  value="${proxq[0].rongjil }" ></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;"></span>占地面积：</th>
				<td width="20%">				
				<input class="form_text"  name="proxq.zhandiarea" id="zhandiarea"  value="${proxq[0].zhandiarea }" ></td>
				<th width="10%" style="display: none;">楼盘户型：</th>
				<td width="20%" style="display: none;"><input class="form_text"  name="proxq.prosalehx" id="prosalehx"  value="${proxq[0].prosalehx }" ></td>						
				
			</tr>
			<tr align="right" style="display: none;">			
				<th width="10%"><span style="color: red;"></span>物业类型：</th>
				<td width="20%">				
				<input class="form_text"  name="proxq.wuyetype" id="wuyetype"  value="${proxq[0].wuyetype }" ></td>
				<th width="10%"></th>
				<td width="20%"></td>						
				
			</tr>
			<tr align="left">			
				<th width="10%"><span style="color: #ADD8E6;font-size:18px;">预售许可证</span></th>
				<td width="20%">				
				</td>
				<th width="10%"></th>
				<td width="20%"></td>						
				
			</tr>
			<tr  >			
				<td><input style="display: none;"  value="" name="proxq.proxk" id='proxk'  type='text' /></td>
	 			<td><input style="display: none;"  value="" name="proxq.profztime" id='profztime' type='text' /></td>
	 			<td><input style="display: none;"  value="" name="proxq.propro" id='propro' type='text'/></td> 			
			</tr>
			
			<tr  align="center"  style="background-color:#B0C4DE;">			
				<th width="20%">预售许可证</th>		
				<th width="20%">发证时间</th>
				<th width="20%">绑定楼栋</th>				
			</tr>
			
			<tbody id='tbodys'>
 			<tr style="display: none;">
 			<td><input name='proxk'  type='text' style="width: 100%;"/></td>
 			<td><input name='profztime' type='text' style="width: 100%;"/></td>
 			<td><input name='propro' type='text' style="width: 100%;"/></td> 			
 			<td><input type='button' value='删除' onclick='removeRow(this)'/></td>
 			</tr>

 			</tbody>
			
			<tr align="right">			
				<th width="10%"></th>
				<td width="20%">				
				</td>
				<th width="10%">
				<input type="button" value="增加" onclick="addRow2()" />
				</th>
				<td width="20%">
				
				
				</td>						
				
			</tr>
			
			
			
			
			<tr align="left">			
				<th width="10%"><span style="color: #ADD8E6;font-size:18px;">配套设施</span></th>
				<td width="20%">				
				</td>
				<th width="10%"></th>
				<td width="20%"></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;"></span>物业公司：</th>
				<td width="20%">
				<input class="form_text"  name="proxq.wuyegs" id="wuyegs"  value="${proxq[0].wuyegs }" ></td>
				<th width="10%"><span style="color: red;"></span>物业费：</th>
				<td width="20%">		
				<input class="form_text"  name="proxq.wuyemoney" id="wuyemoney"  value="${proxq[0].wuyemoney }" ></td>
				
			</tr>
			<tr align="right" style="display: none;">			
				<th width="10%">车位配比：</th>
				<td width="20%"><input class="form_text"  name="proxq.carpb" id="carpb"  value="${proxq[0].carpb }" ></td>						
				
				<th width="10%">供暖方式：</th>
				<td width="20%"><input class="form_text"  name="proxq.gongnuan" id="gongnuan"  value="${proxq[0].gongnuan }" ></td>						
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;"></span>供水方式：</th>
				<td width="20%">
				<input class="form_text"  name="proxq.gongshui" id="gongshui"  value="${proxq[0].gongshui }" ></td>
				<th width="10%">供电方式：</th>
				<td width="20%"><input class="form_text"  name="proxq.gongdian" id="gongdian"  value="${proxq[0].gongdian }" ></td>						
				
			</tr>
			<tr align="right">
				<th width="10%"><span style="color: red;"></span>规划车位：</th>
				<td width="20%">
				<input class="form_text"  name="proxq.carwei" id="carwei"  value="${proxq[0].carwei }" ></td>
				<th width="10%"></th>
				<td width="20%"></td>		
				
			</tr>
			<tr align="right">			
				<th width="10%"><span style="color: red;"></span>项目简介：</th>
				<td width="20%" colspan="3">
				<textarea  rows="8"  name="proxq.procontent" id="procontent"   >${proxq[0].procontent }</textarea></td>
									
				
			</tr>
       </table>
      
</form>
</div>
	<div class="BottomDiv">
		<epmis:button id="NewRecord" imageCss="icon-edit" value="确定" action="UpdateRecord()" datactrCode="loupan_lpxq.edit" ></epmis:button> 
<!-- 		<a -->
<!-- 			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a> -->
	</div>
</body>
</html>
