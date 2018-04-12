//得到项目名称
var pathName=window.document.location.pathname;  
var ProjName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
var url = ProjName;

document.write('<link href="'+url+'/webResources/mui/css/mui.min.css" rel="stylesheet" />');
document.write('<link href="'+url+'/webResources/mui/css/style.css" rel="stylesheet" />');  
document.write('<script type="text/javascript" src="'+url+'/webResources/mui/js/jquery-1.8.3.js"></script>');
document.write('<script src="'+url+'/webResources/mui/js/mui.min.js"></script>');
document.write('<script src="'+url+'/webResources/mui/js/mui.enterfocus.js"></script>');
document.write('<script src="'+url+'/webResources/mui/js/app.js"></script>');
document.write('<script src="'+url+'/webResources/mui/js/html.js"></script>');
document.write('<script src="'+url+'/webResources/mui/examVar.js"></script>');
document.write('<script src="'+url+'/webResources/mui/Base64.js"></script>');
document.write('<script src="'+url+'/webResources/mui/Base64Call.js"></script>');
document.write('<script src="'+url+'/webResources/mui/browse.js"></script>');
function isNull(pram) 
{
	if(pram==null || typeof(pram)=="undefined")
	{
		return false;
	}
	return true
};

function encodeStrByBase(str){
	var b = new Base64();
	var output=b.encode(str);
	return output;
}

