$(function(){
	$("#all").css({"height":"auto","width":"100%","overflow":"hidden","float":"left"});//追加样式
	if($(document).height() > $(window).height()){
		$("#all").css({"height":$(window).height()-35,"width":"100%","overflow":"auto","float":"left"});//追加样式
	}
	$(window).resize(function(){
		$("#all").css({"height":"auto","width":"100%","overflow":"auto","float":"left"});//追加样�

		if( $("#all").height() >= $(window).height()-35 ){
			$("#all").css({"height":$(window).height()-35,"width":"100%","overflow":"auto","float":"left"});//追加样式
		}else{
			$("#all").css({"height":$(window).height()-35,"width":"100%","overflow":"hidden","float":"left"});//追加样式
		}
	});
});