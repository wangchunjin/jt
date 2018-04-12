
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title></title>
    <style type="text/css">
    	th{
    		text-align: right;
    	}
    	table{
    		border-collapse:separate;
    		border-spacing: 10px;
    	}
    	c{
    		color:red;
    		font-weight: bold;
    	}
    </style>
 <script type="text/javascript">
 	 function formattime(){
 		 var date = new Date();
 		 var year = date.getFullYear();//4位数的年份
 		 var month = date.getMonth()+1;//月份从开始 所以要+1
 		 month=month>9?month:('0'+month);
 		 var day = date.getDate();//日
 		 day=day>9?day:('0'+day);
 		 //var day = date.getDay();//获取星期几
 		 var hours = date.getHours();//获取时
 		 hours=hours>9?hours:('0'+hours);
 		 var minutes = date.getMinutes();//分
 		 minutes=minutes>9?minutes:('0'+minutes);
 		 var seconds = date.getSeconds();//秒数
 		 seconds=seconds>9?seconds:('0'+seconds);
 		 var dateStr = year+"-"+month+"-"+day+" "+hours+":"+minutes;
 		 return dateStr;
 		 
 	 }
 
     function  editRecord(){ 
	    	 if($("#zhuangtai option:selected").val()==12){
	     		if($("#yonghunote").val()==""){
	     			 $.messager.alert("错误","备注不能为空！");
					 return;
	     		}
	    	 }
           	 var currFrameId = "${param.currFrameId}";
             var res = $('#editForm').SubmitForm("${ctx}/jp/sub/updateSubscrit.action");
	    		if(res[0].result=="success"){
	    			var objFrame = $("#"+currFrameId,parent.parent.document.body)[0].contentWindow;
                    objFrame.TwoFrame.location.reload();
                    parent.parent.close_win("editWindow");
	    		}else{
	    			$.messager.alert("错误",res[0].result);
	    		}
         }
     
     function showLoupan(){
              //createSimpleWindow("showWindow","楼盘信息","${ctx}/web/JP/SUB/PROSALE/FS.jsp",1300, 600);
              //parent.parent.parent.addTab('showWindow','楼盘信息',"/web/JP/PROSALE/FS.jsp");
              open_win("${ctx}/web/JP/SUB/ADD/content.jsp","楼盘信息","1500","600");
     }
     
     function open_win(url, title, w, h) {
         var iWidth = w;
         var iHeight = h;
         var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
         var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
         myWindow  = window.open(url, title, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
      }
     
    
     
     $(function(){
    	 var status = $("#zhuangtai").children("option:selected").val();
    	 if(status==12){
    		 var time = formattime();
    		 $("#quxiaotime").val(time);
    		 $("#yonghunote").val("");
    		 $("#yonghunote").attr("placeholder","请填写取消原因");
    	 }
    	 //获取当前时间：格式yyyy-MM-dd HH:mm:ss
    	 $("#zhuangtai").change(function(){
        	 var status = $(this).children("option:selected").val();
        	 if(status==12){
        		 var time = formattime();
        		 $("#quxiaotime").val(time);
        		 $("#yonghunote").val("");
        		 $("#yonghunote").attr("placeholder","请填写取消原因");
        	 }else{
        		 var note = '${subData.yonghunote}';
        		 $("#yonghunote").val(note);
        		 $("#quxiaotime").val("");
        	 }
         });
     })
     
   
       
    
</script>

  </head>
  
  <body  class="NewWinClass" id="proBody" style="overflow: auto;">
   <form method="post" action="" id="editForm" name="editForm" enctype="multipart/form-data">
		<table style="margin-top: 15px;width: 96%;" >
				<input class="label_text" style="width:300px;" type="hidden" readonly="readonly" name="sub.ordernum" id=ordernum value="${subData.ordernum}">
				<tr class="">
					<th width="12%">姓名：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.name" id="name" value="${subData.name}"></td>
					<th width="12%">手机号：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.phone" id="phone" value="${subData.phone}"></td>
				</tr>
				<tr class="">
					<th width="12%">年龄段：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.age" id="age" value="${subData.age}"></td>
					<th width="12%">意向区域：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.area" id="area" value="${subData.area}"></td>
				</tr>
				<tr class="">
					<th width="12%">购房用途：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.purpose" id="purpose" value="${subData.purpose}"></td>
					<th width="12%">预约楼盘：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;" readonly="readonly"  name="" onclick="showLoupan()" id="lpname" value="${subData.title}"></td>
				</tr>
				<tr class="">
					<th width="12%">意向楼盘：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.prosale" id="prosale" value="${subData.prosale}"></td>
					<th width="12%">预约人数：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.yuyuerenshu" id="yuyuerenshu" value="${subData.yuyuerenshu}"></td>
				</tr>
				<tr class="">
					<th width="12%">预约时间：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.datetime" id="datetime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true})" value="${subData.datetime}"></td>
					<th width="12%">预约地点：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.address" id="address" value="${subData.address}"></td>
				</tr>
				
				<tr class="">
					<th width="12%">状态：</th>
					<td width="38%" class="">
						<select class="label_text" style="width:300px;"  name="sub.zhuangtai" id="zhuangtai">
							<c:if test="${subData.zhuangtai eq 0}">
								<option value="0">待审核</option>
								<option value="1">审核不通过</option>
								<option value="2">待匹配</option>
								<!-- <option value="5">匹配完成</option>
								<option value="6">服务中</option>
								<option value="7">完成带看</option>
								<option value="8">下定</option>
								<option value="9">签约</option>
								<option value="10">按揭办理</option>
								<option value="11">完成按揭办理</option>
								<option value="12">取消</option> -->
							</c:if>
							<c:if test="${subData.zhuangtai eq 1}">
								<option value="1">审核不通过</option>
								<option value="2">待匹配</option>
								<!-- <option value="5">匹配完成</option>
								<option value="6">服务中</option>
								<option value="7">完成带看</option>
								<option value="8">下定</option>
								<option value="9">签约</option>
								<option value="10">按揭办理</option>
								<option value="11">完成按揭办理</option>
								<option value="12">取消</option> -->
							</c:if>
							<c:if test="${subData.zhuangtai eq 2}">
								<option value="2">待匹配</option>
								<option value="3">保存预约记录</option>
								<option value="4">匹配中</option>
								<!-- <option value="5">匹配完成</option>
								<option value="6">服务中</option>
								<option value="7">完成带看</option>
								<option value="8">下定</option>
								<option value="9">签约</option>
								<option value="10">按揭办理</option>
								<option value="11">完成按揭办理</option>
								<option value="12">取消</option> -->
							</c:if>
							<c:if test="${subData.zhuangtai eq 3}">
								<option value="3">保存预约记录</option>
								<option value="4">匹配中</option>
								<!-- <option value="5">匹配完成</option>
								<option value="6">服务中</option>
								<option value="7">完成带看</option>
								<option value="8">下定</option>
								<option value="9">签约</option>
								<option value="10">按揭办理</option>
								<option value="11">完成按揭办理</option>
								<option value="12">取消</option> -->
							</c:if>
							<c:if test="${subData.zhuangtai eq 4}">
								<option value="4">匹配中</option>
								<!-- <option value="5">匹配完成</option>
								<option value="6">服务中</option>
								<option value="7">完成带看</option>
								<option value="8">下定</option>
								<option value="9">签约</option>
								<option value="10">按揭办理</option>
								<option value="11">完成按揭办理</option>
								<option value="12">取消</option> -->
							</c:if>
							<c:if test="${subData.zhuangtai eq 5}">
								<option value="5">匹配完成</option>
								<option value="12">取消</option>
								
								
							</c:if>
						</select>
					</td>
					<th width="12%">取消时间：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;" readonly="readonly"  name="sub.quxiaotime" id="quxiaotime" value="${subData.quxiaotime}"></td>
				</tr>
				<tr class="">
					<th width="12%">预算：</th>
					<td width="38%" class=""><input class="label_text" style="width:300px;"  name="sub.budgets" id="budgets" value="${subData.budgets}"></td>
					
				</tr>
				<tr class="">
					<th width="12%">备注：</th>
					<td class="" colspan="4">
						<textarea rows="4" cols="20" class="label_textare" style="width:710px;" name="sub.yonghunote" id="yonghunote">${subData.yonghunote}</textarea>
					</td>
				</tr>
       </table>
       <input type="hidden" id="id" name="sub.id" value="${subData.id }">
       <input type="hidden" name="sub.loupanid" id="loupanid" value="${subData.loupanid}">
       </form>
			<div class="BottomDiv">

					<a href="###" class="btn_01" onclick="editRecord()">确定<b></b></a>				
					<a href="###" class="btn_01" onclick="parent.close_win('editWindow')">关闭<b></b></a>				
			</div>
  </body>
</html>
