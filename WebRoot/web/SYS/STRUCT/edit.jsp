<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>查看文档</title>
</head>
<script type="text/javascript">
var pw="epmis"
//打开文件
function objinit(doc_url)
{
  TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
  //alert(TANGER_OCX_OBJ)
  //window.setTimeout(function(){TANGER_OCX_OBJ.OpenFromUrl(doc_url)}, 2000);
 	  TANGER_OCX_OBJ.Activate(true);
  // TANGER_OCX_OBJ.OpenFromUrl(doc_url); 
   TANGER_OCX_OBJ.BeginOpenFromURL(doc_url+"?random="+Math.round(Math.random()*100)); 
 }

function DocumentOpened(){
	 TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
	 TANGER_OCX_OBJ.ActiveDocument.Unprotect(pw);
}
function SetTable(obj){
    if(obj.value=="MY_TABLE"){
         document.getElementById("tableSet").style.display="";
    }else{
         document.getElementById("tableSet").style.display="none";
    }
    
    if(obj.value == "MY_CHECKBOX"){
    	document.getElementById("checkBoxSet").style.display="";
    }else{
    	document.getElementById("checkBoxSet").style.display="none";
    }
}
function TANGER_OCX_SaveDoc_Gen(ntkoForm,fileUrl)
{ 
	try
	{	
			var params = "";
     	//调用控件的SaveToURL函数
		var retHTML = TANGER_OCX_OBJ.SaveToURL
		(
			"${ctx}/sys/struct/saveDoc.action?id=${param.id}&type=Tpl",  //此处为
			"uploadfile",	//文件输入域名称,可任选,不与其他<input type=file name=..>的name部分重复即可
			params, //可选的其他自定义数据－值对，以&分隔。如：myname=tanger&hisname=tom,一般为空
			fileUrl, //文件名,此处从表单输入获取，也可自定义
			ntkoForm //控件的智能确定功能可以允许同时确定选定的表单的所有数据.此处可使用id或者序号
		); //此函数会读取从服务器上返回的信息并确定到返回值中。
		
 
		 	alert("保存成功！");
	}catch(e){alert(e.name + ": " + e.number + ": " + e.message);}finally{
		
		 insertKey();
	}
}
 
//增加标记
function NewBookmark()
{
	try{
		var range = TANGER_OCX_OBJ.ActiveDocument.Application.Selection.range;
		var start = range.Start;
		var end = range.End;
		if(start == 0 || end == 0)
		{
			alert("请选择要作标记的地方!");
			return;
		}
        
		var bookmark_name = $("#BOOKMARK_NAME").val();
		if(!isStringNull(bookmark_name)){
			alert("请选择标记!");
			return;
		}
		var temp = bookmark_name;
		var bookmark_cname =$("#BOOKMARK_NAME").find("option:selected").text();
		
		if(bookmark_name=="MY_CHECKBOX"){ //添加复选框
			    var checkBoxName = $("#checkBoxName").val();
			    if(!isStringNull(checkBoxName)){
					   alert("请输入名称！")
					   return;
		        }
			
			    
				var count = TANGER_OCX_OBJ.ActiveDocument.InlineShapes.Count;
			    if(count==0){
			    	bookmark_name = temp+"_0";//i为自定义序列	
			    }else{
					var boxnames ="";
					for(var c=1;c<=count;c++){
						boxnames = boxnames +","+TANGER_OCX_OBJ.ActiveDocument.InlineShapes.Item(c).OLEFormat.Object.Name;				 
					}
				    var flag = false;
				    for(var i=0; i<100; i++)
					{
				    	if(flag){
				    		break;
				    	}
						bookmark_name = temp+"_" + i;//i为自定义序列										 
						flag = true;
						if(boxnames.indexOf(bookmark_name)>=0){
							flag = false;
						}						 	
					}
			    }
			 var Boxobj = TANGER_OCX_OBJ.activedocument.application.Selection.InlineShapes.AddOLEControl("Forms.CheckBox.1") ;
			 Boxobj.OLEFormat.Object.Name=bookmark_name;
			 Boxobj.OLEFormat.Object.Caption=checkBoxName;
			 
			 Boxobj.OLEFormat.Object.BackColor="&H00C0FFFF";
 
			 Boxobj.OLEFormat.Object.AutoSize="true";  			
		}else{		
		//	if(bookmark_name =="MY_FIELD"||bookmark_name =="MY_TABLE"||bookmark_name =="MY_DATE")
		//	{	
				for(var i=0; i<100; i++)
				{
					bookmark_name = temp+"_" + i;//i为自定义序列
					if(!TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists(bookmark_name)) 
					{
						bookmark_cname = bookmark_cname + "-"+i;
					 	break;	
					}		
				}
		//	}
	
			if(TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists(bookmark_name))  
			{ 
				TANGER_OCX_OBJ.ActiveDocument.Application.Selection.GoTo(-1,0,0,bookmark_name);//转到书签
				if (!window.confirm("标记已存在,是否确定覆盖?"))
					return;
				else
				{
					TANGER_OCX_OBJ.SetBookmarkValue(bookmark_name,"");
					TANGER_OCX_OBJ.ActiveDocument.BookMarks.item(bookmark_name).Delete();
				}
			}
				AddBookmark(range,bookmark_name,bookmark_cname);
		}
	}catch(e){alert(e.number + ":" + e.description + "\n" + e.name + ": " + e.message);}finally{}
}
//增加标记
function AddBookmark(range,bookmark_name,bookmark_cname)
{
	try{ 
		var start = range.Start;
		var end = range.End;
		if(start == 0 || end == 0)
		{
			alert("请选择要作标记的地方!");
			return;
		}
		if(bookmark_name.indexOf("MY_TABLE")==0){
		  var row = $("#rowNum").val();
		  var col = $("#colNum").val();
		  if(isStringNull(row)&&isStringNull(col)){
			  	if(isNaN(row)||isNaN(col)){
				   alert("列数和行数必须为整数字！")
				   return;
		       }
	       }else{
	       	  alert("列数和行数不能为空！")
			   return;
	       }
        }
		TANGER_OCX_OBJ.ActiveDocument.Bookmarks.Add(bookmark_name,range); 
		if(TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists(bookmark_name)) 
		{   
		    if(bookmark_name.indexOf("MY_TABLE")!=0){
				TANGER_OCX_OBJ.SetBookmarkValue(bookmark_name,bookmark_cname+"  ");
			 	TANGER_OCX_OBJ.ActiveDocument.Application.Selection.GoTo(-1,0,0,bookmark_name);//转到书签
			}else{
			    TANGER_OCX_OBJ.SetBookmarkValue(bookmark_name,"                 ");
 			    var selection =  TANGER_OCX_OBJ.ActiveDocument.Application.Selection.GoTo(-1,0,0,bookmark_name);//转到书签
			    var table=selection.tables.add(range,row,col);
			    for(var i=-1;i>=-6;i--)
                { 
			       table.borders.item(i).LineStyle=1;			  
                }
			}
		
		}
		
	}catch(e){alert(e.name + ": " + e.number + ": " + e.message);}finally{}
}

//验证标记
function DisplayBookmark()
{
	 
	try{
	    TANGER_OCX_OBJ.ActiveDocument.DeleteAllEditableRanges(-1);	
		var bks = TANGER_OCX_OBJ.ActiveDocument.Bookmarks; 
		var bksCount = bks.Count; 
		var infoCount="";
		for(var i=1; i <= bksCount ; i++)
		{
			var name = bks.item(i).Name;
			if(trim(name).indexOf("PROJ_NAME")==0 || trim(name).indexOf("ACTUAL_NAME")==0 || trim(name).indexOf("MY_TABLE")==0 || trim(name).indexOf("MY_DATE")==0 || trim(name).indexOf("CREATED_DATE")==0 || trim(name).indexOf("MY_FIELD")==0 ){
				var range = TANGER_OCX_OBJ.GetBookmarkValue(name);
				if(trim(name).indexOf("MY_TABLE")==0){
					name = trim(name);
					range ="自定义表格";
				}
				var _infoCount = ""; 
	 			 
				_infoCount+=range;
				_infoCount+="-"+name+"\n";
				infoCount+="\n活动"+i+":\n";
				infoCount+=_infoCount;
	 
				
				var start=bks.item(i).Range.Start;
				var end = bks.item(i).Range.End; 
			//	alert(start+"xx"+end)
				  TANGER_OCX_OBJ.ActiveDocument.Range(start,end-1).Select();
				 
 				  TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Editors.Add(-1);
			}
	    }	 
		 try{
			 
			// TANGER_OCX_OBJ.ActiveDocument.Protect(3,false,pw);
			// TANGER_OCX_OBJ.ActiveDocument.Protect(3, false, pw, false, true);
			   TANGER_OCX_OBJ.ActiveDocument.Protect(3, 0, pw);
		 }catch(e){}
	  
		if(isStringNull(infoCount))
		{
			alert(infoCount);
		}
		else
			alert("没找到任何合法的标记！");
	}catch(e){alert(e.name + ": " + e.number + ": " + e.message);}finally{}
}
function reNameBookmark(){
	 var id = '${param.id}';
	insertKey();
    var wtop=(window.screen.height-530)/2;
	var wleft=(window.screen.width-750)/2;
	window.open('${ctx}/web/SYS/STRUCT/OfficetplKeyInfo.jsp?id='+id,'','width=750 ,height=530,left='+wleft+',top='+wtop);

}

function insertKey(){
	 var id = '${param.id}';
	  
	    var sql = "UPDATE STRUCT_OFFICETPL_KEY SET KEY_TYPE = 'OLD' WHERE OFFICETPL_ID = '"+id+"'";
	    var res=GetXmlBySql(sql);
	 
	     
	    var bks = TANGER_OCX_OBJ.ActiveDocument.Bookmarks; 
		var bksCount = bks.Count;  
		for(var i=1; i <= bksCount ; i++)
	    {
	     var short_name = bks.item(i).Name;
	     var display_name = TANGER_OCX_OBJ.GetBookmarkValue(short_name);
		 if(trim(short_name).indexOf("PROJ_NAME")==0 || trim(short_name).indexOf("ACTUAL_NAME")==0 || trim(short_name).indexOf("MY_TABLE")==0 || trim(short_name).indexOf("MY_DATE")==0 || trim(short_name).indexOf("CREATED_DATE")==0 || trim(short_name).indexOf("MY_FIELD")==0 ){
			     if(trim(short_name).indexOf("MY_TABLE")==0){
			    	 short_name = trim(short_name);
			    	 display_name ="自定义表格";
					}
			     
			      sql = "SELECT WID FROM STRUCT_OFFICETPL_KEY WHERE OFFICETPL_ID = '"+id+"' AND KEY_SHORT_NAME = '"+short_name+"'";
			      res=GetXmlBySql(sql);
			      if(res.length==0){
			    	 	 sql = "INSERT INTO STRUCT_OFFICETPL_KEY(WID,ID,KEY_SHORT_NAME,KEY_NAME,OFFICETPL_ID,KEY_TYPE)" +
						"VALUES('"+Page_GetGUID()+"','"+Page_GetGUID()+"','"+short_name+"','"+display_name+"','"+id+"','USER')";
						 res=GetXmlBySql(sql);
			      }else{
			    	     sql = "UPDATE STRUCT_OFFICETPL_KEY SET KEY_TYPE = 'USER',KEY_NAME='"+display_name+"'   WHERE OFFICETPL_ID = '"+id+"' AND KEY_SHORT_NAME = '"+short_name+"' ";
			             res=GetXmlBySql(sql);
			      }
			}
				
		}
		var checkBoxs = TANGER_OCX_OBJ.ActiveDocument.InlineShapes;
		var count = checkBoxs.Count;	 
		for(var c=1;c<=count;c++){
 	   var boxname =  checkBoxs.Item(c).OLEFormat.Object.Name;
		   if(boxname.indexOf("MY_CHECKBOX")==0){
			 var boxCaption =  checkBoxs.Item(c).OLEFormat.Object.Caption;
			 sql = "SELECT WID FROM STRUCT_OFFICETPL_KEY WHERE OFFICETPL_ID = '"+id+"' AND KEY_SHORT_NAME = '"+boxname+"'";
		      res=GetXmlBySql(sql);
		      if(res.length==0){
		    	 	 sql = "INSERT INTO STRUCT_OFFICETPL_KEY(WID,ID,KEY_SHORT_NAME,KEY_NAME,OFFICETPL_ID,KEY_TYPE)" +
					"VALUES('"+Page_GetGUID()+"','"+Page_GetGUID()+"','"+boxname+"','"+boxCaption+"','"+id+"','USER')";
					 res=GetXmlBySql(sql);
		      }else{
		    	     sql = "UPDATE STRUCT_OFFICETPL_KEY SET KEY_TYPE = 'USER',KEY_NAME='"+boxCaption+"' WHERE OFFICETPL_ID = '"+id+"' AND KEY_SHORT_NAME = '"+boxname+"' ";
		             res=GetXmlBySql(sql);
		      }
			}
		}		
	    sql = "DELETE FROM STRUCT_OFFICETPL_KEY WHERE KEY_TYPE = 'OLD' AND OFFICETPL_ID = '"+id+"'";
	    res=GetXmlBySql(sql);
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
<body onload="objinit('${param.url}');" >
   <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
	<tr height="30">
	   <td align="right">标记名称:</td>
		<td width="130"> &nbsp;&nbsp;<epmis:select id="BOOKMARK_NAME" define=" SELECT KEY_SHORT_NAME,KEY_NAME FROM STRUCT_OFFICETPL_KEY WHERE OFFICETPL_ID='' or OFFICETPL_ID IS NULL ORDER BY KEY_NAME" attr="style='width: 100px;'" onChange="SetTable(this)" ></epmis:select>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td width="200" id="tableSet" style="display: none;">行数：<input id="rowNum" style="width: 50px;" ></input>&nbsp;&nbsp;列数：<input id="colNum" style="width: 50px;"></input></td>
		<td width="200" id="checkBoxSet" style="display:none ;">名称：<input id="checkBoxName" style="width: 160px;" ></input></td>
		<td width="400"> 
			<epmis:button imageCss ="icon-add" id="graph1" value="增加标记"   action="NewBookmark()" />  
			<epmis:button id="graph1" value="标记验证"   action="DisplayBookmark()"  imageCss ="icon-search" />
			<epmis:button id="save"   value="保存" action="TANGER_OCX_SaveDoc_Gen('NTKO_FORM','${param.url }')"  imageCss ="icon-save" /> 
			<epmis:button id="rename" value="标记重命名"   action="reNameBookmark()"  imageCss ="icon-edit" />
			<epmis:button id="return"   imageCss="icon-cancel" value="关闭" action="parent.close_win('viewDocWindow')" />&nbsp;&nbsp;&nbsp;&nbsp;
	    </td>
	</tr>
   </table>
 <div id="ocxobject" class="ocxobjdiv">
	<script src="${ctx}/webResources/ntko/ntkoofficecontrol.min.js"></script>
</div>
   <input id="doc_grade" type="hidden" value="${param.DOC_GRADE}"/>
</body>
</html>