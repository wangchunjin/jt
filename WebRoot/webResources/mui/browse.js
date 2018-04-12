var u = navigator.userAgent;
if(u.indexOf("QQ")<0){
	mui.alert("请使用QQ或微信浏览器!","提示",function(){
		valBrowse();
	});
	//return false;
}

function valBrowse(){
	var u = navigator.userAgent;
	if(u.indexOf("QQ")<0){
		mui.alert("请使用QQ或微信浏览器!","提示",function(){
			valBrowse();
		});
		//return false;
	}
}
