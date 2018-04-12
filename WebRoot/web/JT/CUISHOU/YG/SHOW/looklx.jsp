<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
    
 <script type="text/javascript">
	     function UpdateRecord(){
	        
	        
// 	        var res = $('#addForm').SubmitForm("${ctx}/clientinfo/tclientinfo/update.action");
	        
// 	 	  	if(res[0].result=="success"){
// 	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
// 	 		parent.close_win('eidtWindow');
// 			}
//  			else{
//  				parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
//  		 	}
	       
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
  <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
   <div style="height:550px;overflow: auto;" >
    <p style="color:gray;font-size: 20px;">通讯录信息</p>
    
    <table width="100%" align="center">
 	 <tr>
    	<td>
   			 <table width="100%">
      			<tr>
        			 <th align="center" >联系人姓名</th> 
				
 					<th  align="center" >联系号码</th> 
      			</tr>
      			<c:forEach items="${operator1}" var="map1">  
			 <tr>
				<td align="center" >
				${map1.pname}
				</td>
				
				<td align="center">
				${map1.phone}
				<input type="checkbox"></input>
				</td>
				<tr>
				
			</tr>  
			  </c:forEach>
   			 </table>
    	</td>
    	<td>
    		<table width="100%">
     		 <tr>
        		<th align="center" >联系人姓名</th> 
				
 					<th  align="center" >联系号码</th> 
      		 </tr>
      		 
      		 <c:forEach items="${operator2}" var="map2">  
			 <tr>
				<td align="center" >
				${map2.pname}
				</td>
				
				<td align="center">
				${map2.phone}
				<input type="checkbox"></input>
				</td>
				<tr>
				
			</tr>  
			  </c:forEach>
    		</table>
    	</td>
  </tr>
</table>
    
    
    
    
    
    
<!--    <table width="100%" align="center"> -->
<!--    <tr> -->
<!--    <td> -->
<!-- 		<table style=width: 49%;" > -->
<!-- 			<tr> -->
				
<!-- 				<th align="center" >联系人姓名</th> -->
				
<!-- 				<th  align="center" >联系号码</th> -->
				
				
<!-- 			</tr> -->
<!-- 			<c:forEach items="${operator}" var="map">   -->
<!-- 			 <tr> -->
<!-- 				<td align="center" > -->
<!-- 				${map.pname} -->
<!-- 				</td> -->
				
<!-- 				<td align="center"> -->
<!-- 				${map.phone} -->
<!-- 				<input type="checkbox"></input> -->
<!-- 				</td> -->
<!-- 				<tr> -->
				
<!-- 			</tr>   -->
<!-- 			  </c:forEach> -->
       		
			
<!--        </table> -->
<!--      <td></td> -->
      
<!--        <table style="width: 49%;" > -->
<!--        		 <tr> -->
				
<!-- 				<th align="center" >联系人姓名</th> -->
				
<!-- 				<th  align="center" >联系号码</th> -->
				
				
<!-- 			</tr> -->
<!-- 			<c:forEach items="${operator}" var="map">   -->
<!-- 			 <tr> -->
<!-- 				<td align="center" > -->
<!-- 				${map.pname} -->
<!-- 				</td> -->
				
<!-- 				<td align="center"> -->
<!-- 				${map.phone} -->
<!-- 				<input type="checkbox"></input> -->
<!-- 				</td> -->
<!-- 				<tr> -->
				
<!-- 			</tr>   -->
<!-- 			  </c:forEach> -->
       		
<!--        </table> -->
<!--        </td> -->
<!--        </tr> -->
<!--        </table> -->
</form>

<!-- 		<div class="BottomDiv"> -->
<!-- 			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a> -->
<!-- 			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				 -->
<!-- 		</div> -->
<div >

<!-- 	<iframe name="TwoFrame" width="100%" height="30px" id="TwoFrame" noresize="noresize" src="${ctx}/web/JT/CONNECT/RECORD/top.jsp?id=${param.rid}" frameborder="0" scrolling="no"></iframe> -->
<!-- 	<iframe name="TwoFrame" width="100%" height="400px" id="TwoFrame" noresize="noresize" src="${ctx}/web/JT/CONNECT/RECORD/selCodeType.jsp?id=${param.rid}" frameborder="0" scrolling="no"></iframe> -->
</div>
</div>
  </body>
</html>
