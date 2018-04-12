<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>查看文档</title>
</head>
<script type="text/javascript">
var pw="epmis";
//打开文件
function objinit(doc_url)
{
   TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
   TANGER_OCX_OBJ.Activate(true); 
   TANGER_OCX_OBJ.BeginOpenFromURL(doc_url+"?random="+Math.round(Math.random()*100)); 
 }
 

function DocumentOpened(){
	TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
 	var bks = TANGER_OCX_OBJ.ActiveDocument.Bookmarks;
	var bksCount = bks.Count;
	for(var j=1; j <= bksCount ; j++) //徇环标记
	{
		var name = bks.item(j).Name;
		var value="";
        var range  = TANGER_OCX_OBJ.ActiveDocument.Range(bks.item(j).range.Start,bks.item(j).range.End-1);
		if(name.indexOf("MY_TABLE")!=0){
			range.Text = "   ";//加个空格占位 bks(j).range
		}
		if(name.indexOf("PROJ_NAME")==0){
           value = '${UserInfo.getProjName()}';
           range.Text = range.Text.replace(trim(range.Text),value);
        }else if(name.indexOf("ACTUAL_NAME")==0){
           value = '${UserInfo.getActualName()}';
           range.Text = range.Text.replace(trim(range.Text),value);
        }else if(name.indexOf("CREATED_DATE")==0){
            var d = new Date();
            value = d.getFullYear() + "-" +(d.getMonth()+1) + "-" + d.getDate();
            range.Text = range.Text.replace(trim(range.Text),value);
	    }
	}
}
function InsertFile(url,fileName,icon,start){
	TANGER_OCX_OBJ.activedocument.application.Selection.Start = start;
	if( TANGER_OCX_OBJ.activedocument.application.Selection.Start==0){
 		  alert("请在word文档中鼠标选择一个插入点！");
 		  return;
 	}
 
  TANGER_OCX_OBJ.activedocument.application.Selection.InlineShapes.AddOLEObject("",url,false,true,icon,"0",fileName);
} 

function OpenInsertFile(){	
 	
    var width = "900"; //窗口宽度D:\\WORDICON.EXE
	var height = "500"; //窗口高度
	var wtop=(window.screen.height-height)/2;
	var wleft=(window.screen.width-width)/2;
	var urlTo = "${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/FS.jsp";
		window.open(urlTo,"","height=" + height + ",width=" + width + ", resizable=no,status=no,center=yes,top=" + wtop + ",left=" + wleft);		        	 
  // createSimpleWindow("addWindow","插入文档","${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/FS.jsp", 900, 670);

	//   TANGER_OCX_OBJ.activedocument.application.Selection.InlineShapes.AddOLEObject("","D:\\1.doc",false,true,"D:\\WORDICON.EXE","0","121.doc");
     //  TANGER_OCX_OBJ.activedocument.application.Selection.InlineShapes.AddOLEObject("","http://221.226.10.174:9999/app/KM/DOC/common/20150324171631503/20151008170846998.doc",false,true,"D:\\WORDICON.EXE","0","111.doc");
 //D:\\1.doc  Word.Document.8  http://192.168.0.109:8080/gpmis/KM/DOC/project/8740364D00AA4EB0AAEB1F216A3AF8FF/JSCX/20150615150556283/20150615150557625/20151009144641537.doc
}

function getStart(){
	var start = TANGER_OCX_OBJ.activedocument.application.Selection.Start;
	return start;
}

function InsertLanguage(language,start){
	TANGER_OCX_OBJ.activedocument.application.Selection.Start = start;
	if( TANGER_OCX_OBJ.activedocument.application.Selection.Start==0){
 		  alert("请在word文档中鼠标选择一个插入点！");
 		  return;
 	}
	TANGER_OCX_OBJ.activedocument.application.Selection.InsertBefore(language);
}



function TANGER_OCX_SaveDoc_Gen(ntkoForm,fileUrl)
{ 
	try
	{	 
	
		var officetplId = '${param.officetplId}';
		var base_link_id = '${param.base_link_id}';
		var officetplName = $("#officetplName").val();
			var params = GetUserDefinedInfo();
			var proj_id = '${UserInfo.getProjId()}';
			var currFrameId = '${param.currFrameId}';
			 var id = Page_GetGUID();
 	 	//调用控件的SaveToURL函数
		var retHTML = TANGER_OCX_OBJ.SaveToURL
		(
			"${ctx}/sys/struct/CreateDoc.action?base_link_id="+base_link_id+"&id="+id+"&base_master_key=${param.base_master_key}&type=Doc&folder_id=${param.folder_id}",  //此处为
			"uploadfile",	//文件输入域名称,可任选,不与其他<input type=file name=..>的name部分重复即可
			"officetplName="+officetplName, //可选的其他自定义数据－值对，以&分隔。如：myname=tanger&hisname=tom,一般为空
			fileUrl, //文件名,此处从表单输入获取，也可自定义
			ntkoForm //控件的智能确定功能可以允许同时确定选定的表单的所有数据.此处可使用id或者序号
		); //此函数会读取从服务器上返回的信息并确定到返回值中。
			// alert(retHTML)
		// if(retHTML[0].result=="success"){
			 var res = GetXmlBySql("INSERT INTO STRUCT_INFO(WID,INFO_ID,RELATEDATE,DOC_ID,PROJ_ID,OFFICETPL_ID)VALUES('"+Page_GetGUID()+"','"+Page_GetGUID()+"','"+params+"','"+id+"','"+proj_id+"','"+officetplId+"' )");
		     if(res[0].result=="success"){
		    	  alert("保存成功！")
		    	  GetIndexFrame(currFrameId).FourFrame.location.reload();  
		    	  window.location.href = "${ctx}/sys/struct/updateStructDoc.action?docId="+id;
		     }
	 //    }
	}catch(e){alert(e.name + ": " + e.number + ": " + e.message);}finally{}
}
 //			"${ctx}/sys/struct/CreateDoc.action?id="+id+"&type=Doc&base_master_key=${param.base_master_key}&folder_id=${param.folder_id}&officetplName="+officetplName,  //此处为
 
function GetUserDefinedInfo()
{
	var bookmarkvalue = new Array();
 
	try
	{
		 
			var bks = TANGER_OCX_OBJ.ActiveDocument.Bookmarks;
			var bksCount = bks.Count;
			var userDefined = '<record>';
			bookmarkvalue[0] = "";
			for(var j=1; j <= bksCount ; j++)    
			{ 
				var name = bks.item(j).Name;
                if(name.indexOf("PROJ_NAME")==0 || name.indexOf("ACTUAL_NAME")==0 || name.indexOf("MY_TABLE")==0 || name.indexOf("MY_DATE")==0 || name.indexOf("CREATED_DATE")==0  || name.indexOf("MY_FIELD")==0){
			        userDefined = userDefined + "<" + name + ">" + TANGER_OCX_OBJ.GetBookmarkValue(name) + "</" + name + ">";
				}
            //    else {
		      //  	userDefined = userDefined + "<" + name + ">" + TANGER_OCX_OBJ.GetBookmarkValue(name) + "</" + name + ">";
		        
		         //   alert(TANGER_OCX_OBJ.ActiveDocument.Tables.Count)
				 //  var table= TANGER_OCX_OBJ.ActiveDocument.Application.Selection.GoTo(-1,0,0,name).Tables(1);
				 //	var obj=table.rows.Count  ; 
				 //	alert(table.Cell(1,1).range.Text)
              
				 //	alert(table.rows(1).cells.Count)
				 //	return;
				 				 
			//	}
			}
			var count = TANGER_OCX_OBJ.ActiveDocument.InlineShapes.Count;
			for(var c=1;c<=count;c++){
				var boxName = TANGER_OCX_OBJ.ActiveDocument.InlineShapes.Item(c).OLEFormat.Object.Name;
				 if(boxName.indexOf("MY_CHECKBOX")==0){
					 userDefined = userDefined + "<" + boxName + ">" + TANGER_OCX_OBJ.ActiveDocument.InlineShapes.Item(c).OLEFormat.Object.Value + "</" + boxName + ">";
				 }
			}
			
			return  userDefined + "</record>";
		 
	}catch(e){alert(e.name + ": " + e.number + ": " + e.message);}finally{}
	return bookmarkvalue;
}
</script>
<style>
.ocxobjdiv{
position: absolute;
bottom: 10px;
left: 0px;
right: 0px;
top:40px;
}

</style>
<body onload="objinit('${param.url}')" >
   <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
	<tr height="30">
	   <td align="center">&nbsp;文档标题:&nbsp;<input type="text" id="officetplName"  style="width: 250px" value="${param.officetplName}">&nbsp;</td>
 		<td width="200"> 
 		    <epmis:button id="insert"   value="插入" action="OpenInsertFile()"  imageCss ="icon-add" /> 
			<epmis:button id="save"   value="保存" action="TANGER_OCX_SaveDoc_Gen('NTKO_FORM','${param.url }')"  imageCss ="icon-save" /> 
			<epmis:button id="return"   imageCss="icon-cancel" value="关闭" action="parent.close_win('AddDocWindow')" />&nbsp;&nbsp;&nbsp;&nbsp;
	    </td>
	</tr>
   </table>
 <div id="ocxobject" class="ocxobjdiv">
	<script src="${ctx}/webResources/ntko/ntkoofficecontrol.min.js"></script>
</div>
</body>
</html>