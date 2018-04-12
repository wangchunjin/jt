<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>上传文件</title>
</head>
<body>
<object id="fileUpload"
    codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab#version=10,0,22,87"
    height="310" width="560" align="middle"
    classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">
    <param name="_cx" value="13229" />
    <param name="_cy" value="8202" />
    <param name="flashvars" value="fileTypeDescription=JPG格式图片&fileTypes=*.jpg;*.jpeg;*.gif;*.png;*.bmp&completeFunction=UploadComplete()&fileSizeLimit=3000000&totalUploadSize=10240000&accounttype=0&uploadPage=flashupload.action?fileGroupGuid=${fileGroupGuid}&uploadType=jpg" />
    <param name="movie" value="webResources/js/FlashFileUpload.swf?ver=20090520" />
    <param name="src" value="webResources/js/FlashFileUpload.swf?ver=20090520" />
    <param name="wmode" value="transparent" />
    <param name="play" value="0" />
    <param name="loop" value="-1" />
    <param name="quality" value="high" />
    <param name="salign" value="lt" />
    <param name="menu" value="-1" />
    <param name="base" value="" />
    <param name="allowscriptaccess" value="sameDomain" />
    <param name="scale" value="noscale" />
    <param name="devicefont" value="0" />
    <param name="embedmovie" value="0" />
    <param name="bgcolor" value="" />
    <param name="swremote" value="" />
    <param name="moviedata" value="" />
    <param name="seamlesstabbing" value="1" />
    <param name="profile" value="0" />
    <param name="profileaddress" value="" />
    <param name="profileport" value="0" />
    <param name="allownetworking" value="all" />
    <param name="allowfullscreen" value="true" />
   
    <embed src="webResources/js/FlashFileUpload.swf?ver=20090520"
        flashvars="fileTypeDescription=JPG格式图片&fileTypes=*.jpg;*.jpeg;*.gif;*.png;*.bmp&completeFunction=UploadComplete()&fileSizeLimit=3000000&totalUploadSize=10240000&accounttype=0&uploadPage=flashupload.action?fileGroupGuid=${fileGroupGuid}&uploadType=jpg"
        quality="high"
        wmode="transparent"
        width="560"
        height="310"
        name="fileUpload"
        align="middle"
        allowscriptaccess="samedomain"
        type="application/x-shockwave-flash"
        pluginspage="http://www.macromedia.com/go/getflashplayer">
    </embed>

</object>
<script>
function UploadComplete()
{
	alert('上传成功！');
	parent.get_all_file('${fileGroupGuid}');
	window.close();
}

</script>

</body>
</html>