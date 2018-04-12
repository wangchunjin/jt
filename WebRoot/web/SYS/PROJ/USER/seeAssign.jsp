<%@page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common/header.jsp"%>
 <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>增加分类码</title>
			
		<script>
	 function selectAllCheckBox()
	{
		var keywordsType_Num = document.all('moduleCode').value;
		var cb_select_table = document.getElementById("cb_select_table");
		for (var i = 0; i < keywordsType_Num; i++) 
		{
			if(cb_select_table.checked)
				document.all('cb_select_field[' + i + ']').checked=true;
			else
				document.all('cb_select_field[' + i + ']').checked=false;
		}
		
		queryIt();
	}
    function queryIt(obj)
   {
	var count = 0;
	var subsys_codes = '';

	var keywordsType_Num = document.all('moduleCode').value;
	var cb_select_table = document.getElementById("cb_select_table");
	for (var i = 0; i < keywordsType_Num; i++) 
	{
		if (document.all('cb_select_field[' + i + ']').checked) 
		{
			if (!isStringNull(subsys_codes)) 
			{
				subsys_codes = document.all('cb_select_field[' + i + ']').value;
			} else {
				subsys_codes = subsys_codes+","+document.all('cb_select_field[' + i + ']').value;
			}
			count++;
		}else
		{
			cb_select_table.checked = false;
		}
	} 
	if(count==keywordsType_Num)
	{
		cb_select_table.checked = true;
	}	
	var profile_id = $("#profile_id").val();
	var where =subsys_codes;
	var urlTo = "${ctx}/web/SYS/PROJ/USER/seeAssignTable.jsp?profile_id="+profile_id+"&where="+where;
	catcalIframe.location.href = urlTo ;
   }
        </script>
	</head>
	<body>
		<div   class="page_content" style="bottom: 40px;">
		<table width="100%">
		      <tr>
		          <td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin: 1px 0px 1px;" class="table-bg1" >
						<tr class="table-bg1" >
							<td width="10%">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<td align="right">
										选择组件:&nbsp;
									</td>
									<tr align="right">
										<td>
											<input type="hidden" name="moduleCode" value="8"> 
											<input type="hidden" id="profile_id" value="${param.profile_id }">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
							<td width="90%">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr align="left">
										<td><input type="checkbox" name="cb_select_table" id="cb_select_table" onclick="selectAllCheckBox()" checked="checked"/>&nbsp;全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td><input type="checkbox" name="cb_select_field[0]" value="DS" onclick="queryIt(this.value)" checked="checked"/>&nbsp;项目信息</td>										
										<td><input type="checkbox" name="cb_select_field[1]" value="SM" onclick="queryIt(this.value)" checked="checked"/>&nbsp;质量安全</td>
										<td><input type="checkbox" name="cb_select_field[2]" value="QC" onclick="queryIt(this.value)" checked="checked"/>&nbsp;进度管理</td>
										<td><input type="checkbox" name="cb_select_field[3]" value="CC" onclick="queryIt(this.value)" checked="checked"/>&nbsp;合同造价</td>
										
										
										
									</tr>
									<tr align="left">
									    <td><input type="checkbox" name="cb_select_field[4]" value="TM" onclick="queryIt(this.value)" checked="checked"/>&nbsp;职能部门</td>
										<td><input type="checkbox" name="cb_select_field[5]" value="KM" onclick="queryIt(this.value)" checked="checked"/>&nbsp;知识管理</td>
										<td><input type="checkbox" name="cb_select_field[6]" value="GM" onclick="queryIt(this.value)" checked="checked"/>&nbsp;在线考试</td>
										<td><input type="checkbox" name="cb_select_field[7]" value="CN" onclick="queryIt(this.value)" checked="checked"/>&nbsp;项目考核</td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
		          </td>
		      <tr>
		         <td >
		            <iframe id="catcalIframe" src="${ctx}/web/SYS/PROJ/USER/seeAssignTable.jsp?profile_id=${param.profile_id}&where=DS,SM,QC,CC,TM,KM,GM,CN" height="478px" frameborder="0" width="770px" > </iframe>
		         </td>
		      </tr>
		</table>
		</div>
			<div class="BottomDiv">
				 
					<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
			</div>
		<input type="hidden" id="profile_id" value="${param.profile_id}"/>
	</body>

</html>