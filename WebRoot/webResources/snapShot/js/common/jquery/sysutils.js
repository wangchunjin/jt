/**
 * js 工具类
 */
// js获取项目根路径 ${basePath} http://localhost:8080/xxx
var bp = function() {
	var path = window.location.href;
	var pathName = window.location.pathname;
	var hostPath = path.substring(0, path.indexOf(pathName))
	var projectName = pathName.substring(0,
			pathName.substring(1).indexOf('/') + 1);
	return (hostPath + projectName);
}

/**
 * 将form表单元素的值序列化成对象
 * 
 * @returns object
 */
var serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * F * 增加formatString功能
 * 
 * 使用方法：formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
var formatString = function(str) {
	for (var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展tree，使其支持平滑数据格式
 */
/**$.fn.tree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled, textFiled, parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]]
					&& data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};**/

/**
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展combotree，使其支持平滑数据格式
 */
/**$.fn.combotree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled, textFiled, parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]]
					&& data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};**/

/**
 * 格式化日期（不含时间）
 */
var formatterDate = function(date) {
	if (date == undefined) {
		return "";
	}
	/* json格式时间转js时间格式 */
	date = date.substr(1, date.length - 2);
	var obj = eval('(' + "{Date: new " + date + "}" + ')');
	var date = obj["Date"];
	if (date.getFullYear() < 1900) {
		return "";
	}

	var datetime = date.getFullYear()
			+ "-"// "年"
			+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
					+ (date.getMonth() + 1)) + "-"// "月"
			+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate());
	return datetime;
};
/**
 * 格式化日期（含时间"00:00:00"）
 */
var formatterDateTime = function(date) {
	if (date == undefined) {
		return "";
	}
	/* json格式时间转js时间格式 */
	date = date.substr(1, date.length - 2);
	var obj = eval('(' + "{Date: new " + date + "}" + ')');
	var date = obj["Date"];
	if (date.getFullYear() < 1900) {
		return "";
	}

	/* 把日期格式化 */
	var datetime = date.getFullYear()
			+ "-"// "年"
			+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
					+ (date.getMonth() + 1)) + "-"// "月"
			+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate())
			+ " " + "00:00:00";
	return datetime;
};
/**
 * 格式化去日期 yyyy-MM-dd HH:mm:ss
 */
var formatterTime = function(d) {
	if (d == undefined) {
		return "";
	}
//	console.info(d);
	/* json格式时间转js时间格式 */
	date = date.substr(1, d.length - 2);
	var obj = eval('(' + "{Date: new " + d + "}" + ')');
	var date = obj["Date"];
	if (date.getFullYear() < 1900) {
		return "";
	}

	var datetime = date.getFullYear()
			+ "-"// "年"
			+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
					+ (date.getMonth() + 1))
			+ "-"// "月"
			+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate())
			+ " "
			+ (date.getHours() < 10 ? "0" + date.getHours() : date.getHours())
			+ ":"
			+ (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
					.getMinutes())
			+ ":"
			+ (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
					.getSeconds());
	return datetime;
};