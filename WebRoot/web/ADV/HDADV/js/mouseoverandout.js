/**
 * 鼠标触碰和移出事件
 */
 var pathName=window.document.location.pathname;
 var ProjName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
//alert(pathName);
function on_Mouseover(obj){
	obj.style='color:white; width:49px; height:20px; border-color:HotPink;  border:2; background:url('+ProjName+'/img/124.png) no-repeat left top;';
}

function on_Mouseout(obj){
	obj.style='color:white; width:50px; height:20px;  border:0; background:url('+ProjName+'/img/123.png) no-repeat left top;';
}
function on_Mouseover1(obj){
	obj.style='color:white; width:80px; height:20px; border-color:HotPink;  border:2; background:url('+ProjName+'/img/124.png) no-repeat left top;';
}

function on_Mouseout1(obj){
	obj.style='color:white; width:100px; height:20px;  border:0; background:url('+ProjName+'/img/123.png) no-repeat left top;';
}