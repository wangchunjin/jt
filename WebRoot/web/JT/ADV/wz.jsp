<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
 <script type="text/javascript">
	     
	     function AddRecord(){
	  	     	 var currFrameId = "${param.currFrameId}";
	  	        var r2=/^[0-9]*$/;//判断是否是整数
	  	       
	 	        
	  	       var num= $("#num").val();
	 	        if(num==null||num==""){
	 	        	parent.$.messager.alert("错误","推荐位置不能为空！");
	 	        	return;
	 	        }
	 	        if(r2.test(num)==false){
	 	        	parent.$.messager.alert("错误","推荐位置只能是整数！");
	 	        	return;
	 	        }	    
	 	        if(num==0){ //输入为0时
	 	    		 
	 	        	parent.$.messager.alert("错误","推荐位置不能为0！");
	 		        	return;
	 	    	 }
	 	        var srow=$("#srow").val();  
	 	        
	 	        if(num>srow/1){
	 	        	parent.$.messager.alert("错误","推荐位置数大于当前位置数！");
	 	        	return;
	 	        }      
	 	        
	 	        
	 	        var lihai=$("#mingsuan").val();
	 	       	if(lihai/1<0.00000000000001){
	 	       		$("#lihai").val(1);//对排序数进行检测,若排序数小于0.00000000000001时，对数值处理机制赋值
	 	       		
	 	       	}
	 	        
	 	       
	  	        
	 	        var res = $('#addForm').SubmitForm("${ctx}/adv/tadv/wz.action");
	 	        
	 	 	  	if(res[0].result=="success"){
//	  	 	  		parent.GetIndexFrame("iframe_CARRR").location.reload();
	 				GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 	 	  		parent.close_win('WzWindow'); 		
	 			}
	  			else{
	  				parent.$.messager.alert("输入符号已存在!",res[0].result);
	  		 	}
	 	 
	 	     }
	 	     
	 	     function jisuan(){
	 	    	
	 	    	 
	 	    	 
	 	    	  var num= $("#num").val();//输入位置
	 	    	 
	 	    	 
	 	    	 var srow=$("#srow").val();//所有条数
	 	    	
	 	    	 if(num/1==srow/1){ //正好时
	 	    		 
	 	    	 	
	 	    		 if(srow==0){
	 						
//	  						var result = GetXmlBySql("SELECT num FROM T_pinpaituijian WHERE isdel='0' ORDER BY num ASC limit 0,1");
	 			    		 $("#mingsuan").val(1);
	 			    		 return;
	 					}
	 	    		 var num1=srow/1-1;
	 	    		//去最后一条数据的num
	 	    		
	 	    		 var result = GetXmlBySql("SELECT orderflag FROM t_adv WHERE isdel='0' ORDER BY orderflag ASC limit "+num1+",1");
	 	    		 $("#mingsuan").val(result[0].orderflag+1);
//	  	    		 return;
	 	    	 }
	 	    	 if(num/1==0){ //输入为0时
	 	    		 
	 	    		 $.messager.alert("错误","推荐位置不能为0！");
	 		         return;
	 	    	 }
	 	    	 
	 			if(num/1<=srow){ //输入位置数<=总条数
	 				
	 				if(num/1==1){
	 					
	 					 var num2=num/1-1;//下一个位置数
	 					var result2 = GetXmlBySql("SELECT orderflag FROM t_adv WHERE isdel='0'  ORDER BY orderflag ASC limit "+num2+",1");
	 					var hou=result2[0].orderflag;
	 					 var ji=(hou/1)/2;
	 					 
	 					 var r2=/^[0-9]*$/;//判断是否是整数
	 		    		 if(r2.test(ji)==true){
	 		    			 ji=ji/1+0.01;
	 		    		 }
	 		    		 $("#mingsuan").val(ji);
	 		    		 return;
	 				}
	 				
	 			
	 				//获取选中车型的orderflag
	 	    		 var rr = GetXmlBySql("SELECT orderflag FROM t_adv WHERE id="+${param.id });
	 	    		 var oo=rr[0].orderflag;
	 	    		 
	 	    		 	    		 
	 	    		 var numoo1=num/1-1;
	 	    		 
	 	    		 //获取输入位置车型的orderflag
	 	    		  var rr1 = GetXmlBySql("SELECT orderflag FROM t_adv WHERE isdel='0'  ORDER BY orderflag ASC limit "+numoo1+",1");
	 	    		 var oo1=rr1[0].orderflag;
	 	    		 
	 	    		 
	 	    		 
	 	    		 if(oo1>oo){
	 	    			 
	 	    			 var num1=num/1;//上一个位置数
	 		    		 var num2=num/1-1;//下一个位置数
	 		    		 
	 		    		 
	 		    		//上一个位置数的num
	 		    		 var result1 = GetXmlBySql("SELECT orderflag FROM t_adv WHERE isdel='0'  ORDER BY orderflag ASC limit "+num1+",1");
	 		    		//下一个位置数的num
	 		    		 var result2 = GetXmlBySql("SELECT orderflag FROM t_adv WHERE isdel='0'  ORDER BY orderflag ASC limit "+num2+",1");
	 		    		 var qian=result1[0].orderflag;
	 		    		
	 		    		 var hou=result2[0].orderflag;
	 		    		 
	 		    		 
	 		    		 var ji=(qian/1+hou/1)/2;
	 		    		 
	 		    		 var r2=/^[0-9]*$/;//判断是否是整数
	 		    		 if(r2.test(ji)==true){
	 		    			 ji=ji/1+0.01;
	 		    		 }
	 		    		 $("#mingsuan").val(ji);
	 		    		 return;
	 	    		 }
	 	    		 
	 	    		 
	 	    		 var num1=num/1-1;//上一个位置数	    		 
	 	    		 var num2=num/1-2;//下一个位置数
	 	    		 
	 	    		 
	 	    		//上一个位置数的num
	 	    		 var result1 = GetXmlBySql("SELECT orderflag FROM t_adv WHERE isdel='0'  ORDER BY orderflag ASC limit "+num1+",1");
	 	    		//下一个位置数的num
	 	    		 var result2 = GetXmlBySql("SELECT orderflag FROM t_adv WHERE isdel='0'  ORDER BY orderflag ASC limit "+num2+",1");
	 	    		 var qian=result1[0].orderflag;
	 	    		
	 	    		 var hou=result2[0].orderflag;	    		 
	 	    		 
	 	    		 var ji=(qian/1+hou/1)/2;
	 	    		 
	 	    		 
	 	    		 
	 	    		 var r2=/^[0-9]*$/;//判断是否是整数
	 	    		 if(r2.test(ji)==true){
	 	    			 
	 	    			 ji=ji/1+0.01;
	 	    		 }
	 	    		 $("#mingsuan").val(ji);    		 
	 	    		 return;
	 	    	 }
	  
	 	     }
	 	     
	 	    $(function(){	    	 
// 		    	  var type='${param.type}';    	
		    	 //所有条数
		    	 var result = GetXmlBySql("SELECT count(id)num FROM t_adv WHERE isdel='0'");
		    	 $("#srow").val(result[0].num);
		    	  
		    	 
		     });
	     
	     
 </script>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			
			<tr>
				<th width="20%"><span style="color: red;">*</span>推荐位置：</th>
				<td width="20%">
				<input class="form_text" type="hidden"  name="adv.id" id="id"  value="${param.id}" >				
				
				<input class="form_text"  name="numss" id="num"  value="" onchange="jisuan();"></td>
			</tr>
			
			<tr>
				<td>
					<input class="form_text" type="hidden" id="srow"  value="" onChange="">
					<!-- 排序数敬上 -->
					<input class="form_text" type="hidden" name="adv.orderflag"  id="mingsuan"  value="" >
					
					<!-- 数值处理机制 -->
					<input class="form_text" type="hidden" name="lihai"  id="lihai"  value="0" >
				</td>
			
			</tr>
			
       </table>
</form>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('WzWindow')">关闭<b></b></a>		
		</div>
  </body>
</html>
