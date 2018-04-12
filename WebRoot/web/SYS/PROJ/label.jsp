<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
	 if(!isStringNull("${param.PROJ_ID}")){
	     $.messager.alert("错误","请选择项目！");
	     return;
	 } 
     var radius = $("#cmProj\\.radius").val();
	 if(isNaN(radius)){
	     $.messager.alert("错误","签到有效半径处请填写数字！");
	     return;
	 } 
	var place = "";
	if(isStringNull($("#stage").val())){
		place = "'"+$("#stage").val()+"'"
	}
	if(isStringNull($("#city").val())){
		place =place + ",'"+$("#city").val()+"'"
	}
	if(isStringNull($("#area").val())){
		place =place + ",'"+$("#area").val()+"'"
	}
   		   $("#cmProj\\.projPlace").val(place);
 
		    var res=  $("#labelForm").SubmitForm("UpdateProjInfo.action");
 
		    if(res[0].result=="success"){
		       window.location.reload();
 		       if(parent.TwoFrame.$('#ProjTree').length >0){
		    	    var node = parent.TwoFrame.$('#ProjTree').treegrid('getSelected');
			    	parent.TwoFrame.$('#ProjTree').treegrid('update',{
						id: node.PROJ_ID,
						row: {
							PROJ_SHORT_NAME: $("#cmProj\\.projShortName").val(),
							PROJ_NAME: $("#cmProj\\.projName").val(),
							PROJ_STAGE: $("#cmProj\\.projStage").val(),
							PROJ_CMPT_PCT: $("#cmProj\\.projCmptPct").val(),
							PROCEED_DATE: $("#cmProj\\.proceedDate").val(),
							COMPLETION_DATE: $("#cmProj\\.completionDate").val(),
						}
					});
			    	 var openProj = parent.OneFrame.OpenProjId;
			    	 if(node.PROJ_ID == openProj){				
			    		 parent.TwoFrame.$("tr[node-id='"+openProj+"']").find("span").filter(".icon-proj").addClass("icon-openproj").removeClass("icon-proj");
			    	 }
			    	
		    	}
		    	
		    }else{
		    	$.messager.alert("错误",res[0].result)
		    }
 
			   	
  }
  function initPlace(){
	   var Str = $("#cmProj\\.projPlace").val();
	  
	   if(!isStringNull(Str)){
		     SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='STATE'  ORDER BY SEQ_NUM ASC,CODE_NAME ASC","stage","",new Array("=--请选择--")) 
	   }else{
		  var ArrayStr = Str.split(",")
		  if(isStringNull(ArrayStr[0])){
		     SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='STATE'  ORDER BY SEQ_NUM ASC,CODE_NAME ASC","stage",ArrayStr[0].replaceAll("'",""),new Array("=--请选择--")) 
	         SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='CITY' AND CODE_BELONGTO = "+ArrayStr[0]+"  ORDER BY SEQ_NUM ASC,CODE_NAME ASC","city","",new Array("=--请选择--"))
		  }
		  if(isStringNull(ArrayStr[1])){
	         SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='CITY' AND CODE_BELONGTO = "+ArrayStr[0]+"  ORDER BY SEQ_NUM ASC,CODE_NAME ASC","city",ArrayStr[1].replaceAll("'",""),new Array("=--请选择--"))
	         SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='AREA' AND CODE_BELONGTO = "+ArrayStr[1]+" ORDER BY SEQ_NUM ASC,CODE_NAME ASC","area","",new Array("=--请选择--"))
		  }
		  if(isStringNull(ArrayStr[2])){
	         SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='AREA' AND CODE_BELONGTO = "+ArrayStr[1]+" ORDER BY SEQ_NUM ASC,CODE_NAME ASC","area",ArrayStr[2].replaceAll("'",""),new Array("=--请选择--"))
		  }
	  }
	   var Str2 = "${cmProj.projStage}";
	    if(!isStringNull(Str2)){
		     SetSelect(" SELECT CODE_SHORT_NAME,CODE_NAME FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE'  ORDER BY CODE_NAME","cmProj\\.projStage","",new Array("=--请选择--")) 
	   }else{
		   	SetSelect(" SELECT CODE_SHORT_NAME,CODE_NAME FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE'  ORDER BY CODE_NAME","cmProj\\.projStage",Str2,new Array("=--请选择--"));
		   	SetSelect(" SELECT CODE_SHORT_NAME,CODE_NAME FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE1' AND CODE_BELONGTO=(select id from cm_code where CODE_NAME='"+Str2+"' AND CODE_TYPE ='PROJ_STAGE')  ORDER BY CODE_NAME","cmProj\\.projStage1","",new Array("=--请选择--")) 		   		   
	   }
	    var Str3 = "${cmProj.projStage1}";
	     if(isStringNull(Str3)){
		   	SetSelect(" SELECT CODE_SHORT_NAME,CODE_NAME FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE1' AND CODE_BELONGTO=(select id from cm_code where CODE_NAME='"+Str2+"' AND CODE_TYPE ='PROJ_STAGE' )  ORDER BY CODE_NAME","cmProj\\.projStage1",Str3,new Array("=--请选择--")) 		   		   
	     }
    }
	function changePlace(obj){
		if(obj.id =="stage"){
           SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='CITY' AND CODE_BELONGTO = '"+obj.value+"'  ORDER BY SEQ_NUM ASC,CODE_NAME ASC","city","",new Array("=--请选择--"));
           SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='AREA' and 1=2 ORDER BY SEQ_NUM ASC,CODE_NAME ASC","area","",new Array("=--请选择--")); 
		}
		if(obj.id =="city"){
           SetSelect("SELECT ID,CODE_NAME NAME FROM CM_CODE WHERE CODE_TYPE ='AREA' and CODE_BELONGTO = '"+obj.value+"' ORDER BY SEQ_NUM ASC,CODE_NAME ASC","area","",new Array("=--请选择--")); 
		}
	} 
	function changestage(obj){
		if(obj.id =="cmProj.projStage"){
			SetSelect(" SELECT CODE_SHORT_NAME,CODE_NAME FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE1' AND CODE_BELONGTO=(select id from cm_code where CODE_NAME='"+obj.value+"' AND CODE_TYPE ='PROJ_STAGE')  ORDER BY CODE_NAME","cmProj\\.projStage1","",new Array("=--请选择--")) 		   		   
			
		}
	}
function AddMap(){
	 var currFrameId =  parent.frameElement.id; 
	  var projId = $("#cmProj\\.projId").val();
	  var city = $("#city").find("option:selected").text();
	  if(isNull(projId)){
		  var mark = $("#cmProj\\.mark").val();
		 createSimpleWindow("addWindow","增加坐标","${ctx}/web/SYS/PROJ/MAP/AddMark.jsp?currFrameId="+currFrameId+"&ProjId="+projId+"&city="+encodeURI(city)+"&mark="+mark, 850, 500);
	  }else{
		  $.messager.alert("错误","请选择要增加坐标的项目")
	  }

}
  </script>
  <body class="LabelWinClass" onload="initPlace();SetDatactr('SYS_PROJ.QYXM.edit')">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
     	<table height="" style="width: 96%;">
     	                          
				<tr > 
				    <th width="12%">代码</th>
					<td width="38%"><input class="label_text"   name="cmProj.projShortName" id="cmProj.projShortName" value="${cmProj.projShortName}"></td>
					<th width="12%">名称</th>
					<td width="38%" colspan="3"><input class="label_text"   name="cmProj.projName" id="cmProj.projName" value="${cmProj.projName}"></td>
				</tr>
				<tr >
					<th width="12%">形象进度</th>
					<td width="38%"><epmis:select id="cmProj.projCmptPct" define="SELECT CODE_NAME ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='XX_JD' ORDER BY SEQ_NUM " value="${cmProj.projCmptPct}"></epmis:select> </td>
					<th width="12%">项目状态</th>
					<td width="38%" colspan="3"><epmis:select id="cmProj.contractTitle" define="未完工=未完工;已完工=已完工" value="${cmProj.contractTitle}"></epmis:select></td>
				</tr>
				<tr > 
					<th width="12%">开工日期</th>
					<td width="38%"><input class="label_text"    name="cmProj.proceedDate" id="cmProj.proceedDate"   value="${cmProj.proceedDate}"
                           	onfocus="DataEdit('yyyy-MM-dd','','cmProj.completionDate')" ></td>
					<th width="12%">完工日期</th>
					<td width="38%" colspan="3"><input class="label_text"   name="cmProj.completionDate" id="cmProj.completionDate"  value="${cmProj.completionDate}" 
							onfocus="DataEdit('yyyy-MM-dd','cmProj.proceedDate','')"   ></td>
				</tr>
				<tr >

					<th width="12%">工程地点</th>
					<td width="38%" >
                         <table width="100%">
                            <tr>
                                 <td width="25%"><epmis:select id="stage"   onChange="changePlace(this)" ></epmis:select></td>
                                 <td width="8%">省</td>
                                 <td width="25%"><epmis:select id="city"    onChange="changePlace(this)" ></epmis:select></td>
                                 <td width="8%">市</td>
                                 <td width="25%"><epmis:select id="area"    onChange="changePlace(this)" ></epmis:select></td>
                                 <td width="8%">区</td>
                            </tr>
                         </table>
                    </td>				
					<th width="12%">详细地址</th>
					<td width="38%" colspan="3">
					    <table style="width: 100%">
					         <tr>
					             <td style="width: 90%"><input class="label_text"  name="cmProj.siteLocation" id="cmProj.siteLocation" value="${cmProj.siteLocation}"></td>
					             <td style="width: 10%;padding-left: 10px; "  ><div style="  background-repeat:no-repeat; background: url(${ctx}/img/map_mark.png); background-position:-42px -21px; width:24px;height:25px; cursor: pointer; " onclick="AddMap()" title="${cmProj.mark eq '' ? '添加坐标' : cmProj.mark  }"  ></div></td>
					         </tr>		    
					    </table>
					    </td>

				</tr>
				<tr >
					<th width="12%">监理项目负责人</th>
					<td width="38%"><input class="label_text"   name="cmProj.supervisor" id="cmProj.supervisor" value="${cmProj.supervisor}"></td>
					<th width="10%">监理项目负责人电话</th>
					<td width="15%"><input class="label_text"   name="cmProj.supervisorPhone" id="cmProj.supervisorPhone" value="${cmProj.supervisorPhone}"></td>
					<th width="10%">签到有效半径</th>
					<td width="15%"><input class="label_text" style="width: 50%;"  name="cmProj.radius" id="cmProj.radius" value="${cmProj.radius}">千米</td>
				</tr>
				<tr >
					<th width="12%">建设单位</th>
					<td width="38%"> <epmis:select id="cmProj.vnmtId" define=" SELECT VNMT_ID,COMPANY_NAME FROM CM_VNMT WHERE VNMT_ID IN (SELECT VNMT_ID FROM CM_PROJ_JOIN WHERE PROJ_ID = '${cmProj.projId}')  ORDER BY COMPANY_NAME "  value="${cmProj.vnmtId}"></epmis:select></td>
					<th width="10%">项目类别</th>
					<td width="15%"> <epmis:select id="cmProj.projStage"   onChange="changestage(this)"> </epmis:select> </td>
					<th width="10%">项目子类</th>
					<td width="15%"> <epmis:select id="cmProj.projStage1"  onChange="changestage(this)"  ></epmis:select>  </td>
					
				</tr>	
				<input type="hidden"  name="cmProj.mark" id="cmProj.mark" value="${cmProj.mark}">
       </table>	
 
       <input type="hidden" id="cmProj.projId" name="cmProj.projId" value="${cmProj.projId}">	
       <input type="hidden" id="cmProj.projPlace" name="cmProj.projPlace" value="${cmProj.projPlace}">						
       </form>
     </div> 						
  </body>
</html>
