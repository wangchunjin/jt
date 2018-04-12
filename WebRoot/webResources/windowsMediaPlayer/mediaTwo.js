 // 请勿修改，否则可能出错
var scriptArgs = document.getElementById('showVideo').getAttribute('data');
var pathName=window.document.location.pathname; 
var ProjName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
	var userAgent = navigator.userAgent, 
				rMsie = /(msie\s|trident.*rv:)([\w.]+)/, 
				rFirefox = /(firefox)\/([\w.]+)/, 
				rOpera = /(opera).+version\/([\w.]+)/, 
				rChrome = /(chrome)\/([\w.]+)/, 
				rSafari = /version\/([\w.]+).*(safari)/;
				var browser;
				var version;
				var ua = userAgent.toLowerCase();
				function uaMatch(ua) {
					var match = rMsie.exec(ua);
					if (match != null) {
						return { browser : "IE", version : match[2] || "0" };
					}
					var match = rFirefox.exec(ua);
					if (match != null) {
						return { browser : match[1] || "", version : match[2] || "0" };
					}
					var match = rOpera.exec(ua);
					if (match != null) {
						return { browser : match[1] || "", version : match[2] || "0" };
					}
					var match = rChrome.exec(ua);
					if (match != null) {
						return { browser : match[1] || "", version : match[2] || "0" };
					}
					var match = rSafari.exec(ua);
					if (match != null) {
						return { browser : match[2] || "", version : match[1] || "0" };
					}
					if (match != null) {
						return { browser : "", version : "0" };
					}
				}
				var browserMatch = uaMatch(userAgent.toLowerCase());
				if (browserMatch.browser) {
					browser = browserMatch.browser;
					version = browserMatch.version;
				}
				//document.write(browser);
 
 
var classid = '6BF52A52-394A-11D3-B153-00C04F79FAA6';
if (browser=="IE"){
	document.write('<object id="mediaplayer"  classid="CLSID:6BF52A52-394A-11D3-B153-00C04F79FAA6" width="100%" height="'+scriptArgs+'" >'); 
	document.write('<param name="URL" value="">');
    document.write('<param name="rate" value="1">');
    document.write('<param name="balance" value="0">');
    document.write('<param name="currentPosition" value="0">');
    document.write('<param name="defaultFrame" value>');
    document.write('<param name="playCount" value="1">');
    document.write('<param name="autoStart" value="1">');
    document.write('<param name="currentMarker" value="0">');
    document.write('<param name="invokeURLs" value="-1">');
    document.write('<param name="baseURL" value>');
    document.write('<param name="volume" value="50">');
    document.write('<param name="mute" value="0">');
    document.write('<param name="uiMode" value="full">');
    document.write('<param name="stretchToFit" value="-1">');
    document.write('<param name="windowlessVideo" value="0">');
    document.write('<param name="enabled" value="-1">');
    document.write('<param name="enablePositionControls" value="1">');
    document.write('<param name="enableContextMenu" value="1">');
    document.write('<param name="fullScreen" value="0">');
    document.write('<param name="SAMIStyle" value>');
    document.write('<param name="SAMILang" value>');
    document.write('<param name="SAMIFilename" value>');
    document.write('<param name="captioningID" value>');
    document.write('<param name="enableErrorDialogs" value="1">'); 
	document.write('<strong>Error:</strong>You need <a href="'+ProjName+'/webResources/windowsMediaPlayer/wmpChrome.crx">Windows Media Player Plugin</a>'); 
	document.write('</object>'); 
} else  if(browser=="chrome"){
	document.write('<object id="mediaplayer"  type="application/x-ms-wmp" data="path" width="100%" height="'+scriptArgs+'" > ');
	document.write('<param name="src" value="" valuetype="ref" type="video/x-ms-wmp">'); 
	document.write('<param name="animationatStart" value="1">'); 
	document.write('<param name="transparentatStart" value="1">'); 
	document.write('<param name="autoStart" value="1">'); 
	document.write('<param name="ShowControls" value="0">'); 
	document.write('<param name="ShowDisplay" value="0">'); 
	document.write('<param name="ShowStatusBar" value="0">'); 
	document.write('<param name="playcount" value="1">'); 
	document.write('<param name="autoRewind" value="1">'); 
	document.write('<param name="displaysize" value="0">'); 
	document.write('<param name="fullScreen" value="1">');
	document.write('<param name="stretchtofit" value="1">'); 
	document.write('<param name="enableContextMenu" value="-1">'); 
	document.write('<param name="uiMode" value="full">'); 
	document.write('<strong>Error:</strong>You need <a href="'+ProjName+'/webResources/windowsMediaPlayer/wmpChrome.crx">Windows Media Player Plugin For Chrome</a>'); 
	document.write('</object>');
}
