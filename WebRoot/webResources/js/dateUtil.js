function stringToDateTime(str){
    var tempStrs = str.split(" ");
    var dateStrs = tempStrs[0].split("-");
    var year = parseInt(dateStrs[0], 10);
    var month = parseInt(dateStrs[1], 10) - 1;
    var day = parseInt(dateStrs[2], 10);
    var timeStrs = tempStrs[1].split(":");
    var hour = parseInt(timeStrs [0], 10);
    var minute = parseInt(timeStrs[1], 10);
    var second = parseInt(timeStrs[2], 10);
    var date = new Date(year, month, day, hour, minute, second);
    return date;
}
//得到两个时间差值
function GetBetweenDateTime(str1,str2){ 
	return stringToDateTime(str1)-stringToDateTime(str2);
}

//得到两个时间差值
function GetBetweenDate(sDate1, sDate2){
	var aDate, oDate1, oDate2, iDays;   
	aDate = sDate1.split("-");
	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]); //转换为12-18-2002格式   
	aDate = sDate2.split("-");
	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
	iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 /24); //把相差的毫秒数转换为天数   
	return iDays;   
} 
