
sessionStorage.setItem("userId", "");
sessionStorage.setItem("token", "");
sessionStorage.setItem("module_name", "");
sessionStorage.setItem("module_code", "");

//查询token
var queryNewToken = url+"/api/exam/queryNewToken.do";
function queryToken(){
	$.ajax({
		url : queryNewToken,
		type: 'post',
		dataType: 'json',
		async : false,
		data:{
		},
		success: function(data){
			if(data.RESULT == "0"){
				sessionStorage.setItem("token", data.token);
			}
		}
	});
}


