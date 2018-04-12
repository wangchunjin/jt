<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>My JSP 'top.jsp' starting page</title>
	 <script type="text/javascript">
			function changePlace(obj){
				if(obj.id =="stage"){
		           SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='CITY' AND CODE_BELONGTO = '"+obj.value+"'  ORDER BY SEQ_NUM ASC,CODE_NAME ASC","city","",new Array("=--请选择--"));
		           SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='AREA' and 1=2 ORDER BY SEQ_NUM ASC,CODE_NAME ASC","area","",new Array("=--请选择--")); 
				}
				if(obj.id =="city"){
		           SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='AREA' and CODE_BELONGTO = '"+obj.value+"' ORDER BY SEQ_NUM ASC,CODE_NAME ASC","area","",new Array("=--请选择--")); 
				}
			}
		
		    function AddMap(){
			 	var currFrameId =  parent.frameElement.id; 
			  	var city = $("#stage").find("option:selected").text();
			  	alert(city);
				createSimpleWindow("addWindow","增加坐标","${ctx}/web/SYS/XMJS/AddMark.jsp?currFrameId="+currFrameId+"&city="+encodeURI(city), 850, 500);
			}
			function changestage(obj){
				if(obj.id =="projStage"){
					SetSelect(" SELECT CODE_SHORT_NAME,CODE_NAME FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE1' AND CODE_BELONGTO=(select id from cm_code where CODE_NAME='"+obj.value+"' AND CODE_TYPE ='PROJ_STAGE')  ORDER BY CODE_NAME","projStage1","",new Array("=--请选择--")) 		   		   
					
				}
			}
			function openWin(id){
				    var currFrameId = parent.frameElement.id;
					var wtop=(window.screen.height-530)/2;
					var wleft=(window.screen.width-550)/2;
			 		createSimpleWindow("selectProj","EPS显示","${ctx}/web/SYS/XMJS/projTree.jsp?currFrameId="+currFrameId, 600, 500);
			}
			$(function(){
				
				 //项目子类
				$('#projStageChaild').combo({
					editable:false
				});
				$('#projStageDivJH').appendTo($('#projStageChaild').combo('panel'));
				$('#projStageDivJH').click(function(){
					var v = "";
					var t = "";
					$('input[name="projStageJH"]:checked').each(function(){ 
						v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
						t += isStringNull(t)?","+$(this).attr("id"):$(this).attr("id");
					});
					$('#projStageChaild').combo('setValue', v).combo('setText', t);
				});
				//项目类别
				$('#projStageSel').combo({
					editable:false
				});
				$('#projStageDiv').appendTo($('#projStageSel').combo('panel'));
				$('#projStageDiv input').click(function(){
					var v = "";
					var t = "";
					$('input[name="projStageChk"]:checked').each(function(){ 
						v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
						t += isStringNull(t)?","+$(this).attr("id"):$(this).attr("id");
					});
					$('#projStageSel').combo('setValue', v).combo('setText', t);
				});
				
				//建设单位
				$('#vnmtId').combo({
					editable:false
				});
				$('#vnmtIdDiv').appendTo($('#vnmtId').combo('panel'));
				$('#vnmtIdDiv input').click(function(){
					var v = "";
					var t = "";
					$('input[name="vnmtChk"]:checked').each(function(){ 
						v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
						t += isStringNull(t)?","+$(this).attr("id"):$(this).attr("id");
					});
					$('#vnmtId').combo('setValue', v).combo('setText', t);
				}); 
				
				//省
				$('#stage').combo({
					editable:false
				});
				$('#stageDiv').appendTo($('#stage').combo('panel'));
				$('#stageDiv input').click(function(){
					var v = "";
					var t = "";
					$('input[name="stageChk"]:checked').each(function(){ 
						v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
						t += isStringNull(t)?","+$(this).attr("id"):$(this).attr("id");
					});
					$('#stage').combo('setValue', v).combo('setText', t);
				});
				//市
				$('#city').combo({
					editable:false
				});
				$('#cityDiv').appendTo($('#city').combo('panel'));
				$('#cityDiv').click(function(){
					var v = "";
					var t = "";
					$('input[name="cityJH"]:checked').each(function(){ 
						v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
						t += isStringNull(t)?","+$(this).attr("id"):$(this).attr("id");
					});
					$('#city').combo('setValue', v).combo('setText', t);
				});
				//区
				$('#area').combo({
					editable:false
				});
				$('#areaDiv').appendTo($('#area').combo('panel'));
				$('#areaDiv').click(function(){
					var v = "";
					var t = "";
					$('input[name="areaJH"]:checked').each(function(){ 
						v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
						t += isStringNull(t)?","+$(this).attr("id"):$(this).attr("id");
					});
					$('#area').combo('setValue', v).combo('setText', t);
				});
				
					//形象进度
				$('#projCmptPct').combo({
					editable:false
				});
				$('#projCmptPctDiv').appendTo($('#projCmptPct').combo('panel'));
				$('#projCmptPctDiv input').click(function(){
					var v = "";
					var t = "";
					$('input[name="projCmptPctChk"]:checked').each(function(){ 
						v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
						t += isStringNull(t)?","+$(this).attr("id"):$(this).attr("id");
					});
					$('#projCmptPct').combo('setValue', v).combo('setText', t);
				});
				parent.FourFrame.location = "${ctx}/web/SYS/XMJS/content.jsp";
			})
			function chageProjStage1(obj){
				$("#projStageC").html("");
				var v = "";
				$('input[name="projStageChk"]:checked').each(function(){
					v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
				});
				if(isStringNull(v)){
					var sql = "SELECT CODE_SHORT_NAME,CODE_NAME FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE1' AND CODE_BELONGTO in (select id from cm_code where CODE_NAME in ("+v+") AND CODE_TYPE ='PROJ_STAGE')  ORDER BY CODE_NAME ";
					var  res=GetXmlBySql(sql);
					if(res.length>0){
						var str = "";
						for(var i=0;i<res.length;i++){
							str +='<input type="checkbox" name="projStageJH"  value="'+res[i].CODE_SHORT_NAME+'" id="'+res[i].CODE_NAME+'"><span>'+res[i].CODE_NAME+'</span><br/>';
						}
						$("#projStageC").html(str);
					}
				}
			}
			function chageStage(obj){
				$("#cityQU").html("");
				var v = "";
				$('input[name="stageChk"]:checked').each(function(){
					v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
				});
				if(isStringNull(v)){
					var sql = "SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='CITY' AND CODE_BELONGTO IN ("+v+")  ORDER BY SEQ_NUM ASC,CODE_NAME ASC";
					var  res=GetXmlBySql(sql);
					if(res.length>0){
						var str = "";
						for(var i=0;i<res.length;i++){
							str +='<input type="checkbox" name="cityJH" onchange="chageCity(this)"  value="'+res[i].ID+'" id="'+res[i].NAME+'"><span>'+res[i].NAME+'</span><br/>';
						}
						$("#cityQU").html(str);
					}
				}
			}
			function chageCity(obj){
				$("#areaQU").html("");
				var v = "";
				$('input[name="cityJH"]:checked').each(function(){
					v += isStringNull(v)?",'"+$(this).val()+"'":"'"+$(this).val()+"'";
				});
				if(isStringNull(v)){
					var sql = "SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='AREA' and CODE_BELONGTO IN ("+v+") ORDER BY SEQ_NUM ASC,CODE_NAME ASC";
					var  res=GetXmlBySql(sql);
					if(res.length>0){
						var str = "";
						for(var i=0;i<res.length;i++){
							str +='<input type="checkbox" name="areaJH"  value="'+res[i].ID+'" id="'+res[i].NAME+'"><span>'+res[i].NAME+'</span><br/>';
						}
						$("#areaQU").html(str);
					}
				}
			}
			function docML(id){
		        var currFrameId =  parent.frameElement.id;
		 		createSimpleWindow("selectProj","奖项","${ctx}/web/SYS/XMJS/projTreeTwo.jsp?currFrameId="+currFrameId, 700, 400);
  			}
	 </script>
	 <style type="text/css">
	 	.panel-body {
	 		  height: 120px  !important ;
		}
		html{
    		overflow-y:auto;
    	}
	 </style>
  </head>
  
  <body class="NewWinClass" overflow="auto" width="100%" height="100%">
  <div style="padding-top:0px;" >
	<form method="post" action="" id="addForm" name="addForm">
		<table style="width: 100%;" border="0">
			<tr>
				<th width="15%">代码:</th>
				<td width="31%" ><input class="form_text"  name="code" id="code" value=""></td>
				<th width="15%">名称:</th>
				<td width="31%" colspan="3"><input class="form_text"  name="name" id="name" value="" ></td>
				<td width="*">&nbsp;</td>
			</tr>
			<tr>
				<th width="15%">形象进度:</th>
				<td width="31%" >
					<select id="projCmptPct" name="projCmptPct" style="width:300px" class='select'></select>
					<div id="projCmptPctDiv">
						<div style="padding:10px">
							<s:iterator value="projCmptPctList" var="cmptList">
								<input type="checkbox" name="projCmptPctChk"  value="${cmptList.ID}" id="${cmptList.NAME }"><span>${cmptList.NAME }</span><br/>
							</s:iterator>
						</div>
					</div>
				</td>
				<th width="15%">项目状态:</th>
				<td width="31%" colspan="3"><epmis:select id="contractTitle" define="未完工=未完工;已完工=已完工" value=""></epmis:select></td>
				<td width="*">&nbsp;</td>
			</tr>
			<tr>
				<th width="15%">开工日期:</th>
				<td width="31%" ><input class="form_text"  name="proceedDate" id="proceedDate" value="" style="width:30%" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})">-<input class="form_text"  name="proceedDate1" id="proceedDate1" value="" style="width:34%" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"></td>
				<th width="15%">完工日期:</th>
				<td width="31%" colspan="3"><input class="form_text"  name="completionDate" id="completionDate" value="" style="width:30%" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})">-<input class="form_text"  name="completionDate1" id="completionDate1" value="" style="width:30%" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"></td>
				<td width="*">&nbsp;</td>
			</tr>
			<tr>
				<th width="15%">工程地点:</th>
				<td width="31%" >
					 <table width="100%">
                            <tr>
                                 <td width="25%">
                                 	<select id="stage" name="stage" style="width:100%" class='select'></select>
									<div id="stageDiv">
										<div style="padding:10px">
											<s:iterator value="stageList" var="stage">
												<input type="checkbox" name="stageChk" onchange="chageStage(this)" value="${stage.ID}" id="${stage.NAME }"><span>${stage.NAME }</span><br/>
											</s:iterator>
										</div>
									</div>
                                 </td>
                                 <td width="8%" style="float: left;">省</td>
                                 <td width="25%">
                                 	<select id="city" name="city" style="width:100%" class='select'></select>
									<div id="cityDiv">
										<div style="padding:10px" id="cityQU">
										</div>
									</div>
                                 </td>
                                 <td width="8%" style="float: left;">市</td>
                                 <td width="25%">
                                 	<select id="area" name="area" style="width:100%" class='select'></select>
									<div id="areaDiv">
										<div style="padding:10px" id="areaQU">
										</div>
									</div>
                                 </td>
                                 <td width="8%" style="float: left;">区</td>
                            </tr>
                      </table>
				</td>
				<th width="15%">详细地址:</th>
				<td width="31%" colspan="3">
					<table style="width: 100%">
					         <tr>
					             <td style="width: 90%"><input class="form_text"  name="siteLocation" id="siteLocation" value=""></td>
					         </tr>		    
					</table>
				</td>
				<td width="*">&nbsp;</td>
			</tr>
			<tr>
				<th width="15%">监理项目负责人:</th>
				<td width="31%"><input class="label_text"   name="supervisor" id="supervisor" value=""></td>
				<th width="15%">EPS：</th> 
				<td width="31%" colspan="3">
					<epmis:text id="projInfo" displaytypeedit="1"  value="" define=""  ></epmis:text> 
				</td>
				<th width="10%" style="display: none;">签到有效半径：</th>
				<td width="13%" style="display: none;"><input class="label_text" style="width: 50%;"  name="radius" id="radius" value="">千米</td>
				<td width="*">&nbsp;</td>
			</tr>
			<tr>
				<th width="15%">监理负责人电话:</th>
				<td width="31%"><input class="label_text"   name="supervisorPhone" id="supervisorPhone" value=""></td>
				<th width="15%">建设单位:</th>
				<td width="31%" colspan="3">
					<select id="vnmtId" name="vnmtId" style="width:300px;"></select>
					<div id="vnmtIdDiv">
						<div style="padding:10px;width:100%">
							<s:iterator value="vnmtIdList" var="vnmt">
								<input type="checkbox" name="vnmtChk" onclick="" value="${vnmt.VNMT_ID }" id="${vnmt.COMPANY_NAME }"><span>${vnmt.COMPANY_NAME }</span><br/>
							</s:iterator>
						</div>
					</div>
				</td>
				<td width="*">&nbsp;</td>
			</tr>
			
			<tr>
				<th width="15%">工程结构:</th>
				<td width="31%" ><input class="form_text"  name="PROJ_FORM" id="PROJ_FORM" value=""></td>
				<th width="15%">项目类别:</th>
				<td width="11%"> 
					<select id="projStageSel" name="projStageSel" style="width:100%" class='select'></select>
					<div id="projStageDiv">
						<div style="padding:10px">
							<s:iterator value="projStageList" var="projStage">
								<input type="checkbox" name="projStageChk" onclick="chageProjStage1(this)" value="${projStage.CODE_SHORT_NAME }" id="${projStage.CODE_NAME }"><span>${projStage.CODE_NAME }</span><br/>
							</s:iterator>
						</div>
					</div>
				</td>
				<th width="12%">项目子类:</th>
				<td width="13%"> 
					<select id="projStageChaild" name="projStageChaild" onclick="changeChaild()" style="width:100%" class='select'></select>
					<div id="projStageDivJH">
						<div style="padding:10px" id="projStageC">
						</div>
					</div>
				</td>
				<td width="*">&nbsp;</td>
			</tr>
			<tr>
				<th width="15%">总投资(万元):</th>
				<td width="31%" ><input class="form_text"  name="GROSS_INVEST" id="GROSS_INVEST" value=""></td>
				<th width="15%">工程质量目标:</th>
				<td width="31%" colspan="3"><input class="form_text"  name="PROJ_QUALITY_DEST" id="PROJ_QUALITY_DEST" value=""></td>
			</tr>
			<tr>
				<th width="15%">监理费用(万元):</th>
				<td width="31%" ><input class="form_text"  name="PART_PROJ_INVEST" id="PART_PROJ_INVEST" value=""></td>
				<th width="15%">安全文明工地目标:</th>
				<td width="31%"  colspan="3" ><input class="form_text"  name="CULTURE_SITE" id="CULTURE_SITE" value=""></td>
			</tr>
			<tr>
				<th width="15%">获奖情况:</th>
				<td width="31%" class="">
					<table width='100%'>
						<tr>
							<td width='90%'>
								<input class='label_text' readOnly=true   id='docInfo_NAME' >
								<input type='hidden' id='docInfo' name='docInfo' value=''>
							</td>
							<td width='10%'>
								<img style='margin-left: 20px;cursor: pointer;' onclick="docML(this)" src='${ctx}/img/button/bg_edit_but.gif'>
							</td>
						</tr>
					</table>
				</td>
				<th width="15%">获奖时间:</th>
				<td width="31%"  colspan="3" >
					<input class="form_text"  name="winnerDate" id="winnerDate" value="" style="width:30%" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})">-
					<input class="form_text"  name="winnerDate1" id="winnerDate1" value="" style="width:34%" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})">
				</td>
			</tr>
		</table>
	</form>
	</div>
  </body>
</html>
