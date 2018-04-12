/**
 * 
 * @param node
 * @param e
 * @return
 */
function findChildForAdmin(node, e){
	var moduleCode = node.id;
	window.open("list.action?moduleCode="+moduleCode,"module_right");
}

/**
 * 
 * @param node
 * @param e
 * @return
 */
function setModule(node,e){
	var moduleCode = node.id;
	var moduleName = node.text;
	var doc = window.parent.document.getElementsByTagName("iframe")["moduleWindow_frm"].contentWindow.document;
	//parent.document.frames["moduleWindow_frm"].document.getElementById("deptName").value = deptName;
	if(moduleCode!=doc.getElementById("parentModuleCode").value && moduleCode.indexOf(doc.getElementById("moduleCode").value)==-1){
		doc.getElementById("parentModuleCode").value = moduleName;
		doc.getElementById("parentModuleName").value = moduleCode;
		if(moduleCode=="0")
			moduleCode = "M";
		moduleService.createModuleCode(moduleCode,function(newCode){
			doc.getElementById("moduleCode").value = newCode;
			parent.close_win("ModuleTree");
        });   
	}else
		parent.close_win("ModuleTree");
}

function setModuleForFunc(node,e){
	var moduleCode = node.id;
	var moduleName = node.text;
	var doc = window.parent.document.getElementsByTagName("iframe")["win_functionPoint_frm"].contentWindow.document;
	doc.getElementById("moduleId").value = moduleCode;
	doc.getElementById("moduleName").innerHTML = moduleName;
	var funcPermission = doc.getElementById("funcPermission").value;
	if(funcPermission!=""){
		var pos1 = funcPermission.indexOf(":");
		var pos2 = funcPermission.lastIndexOf(":");
		
		if(pos2 > pos1){
			var temp = funcPermission.substring(pos2);
			funcPermission = "func:" + moduleCode + temp;
		}else{
			funcPermission = "func:" + moduleCode;
		}
	}else{
		funcPermission = "func:" + moduleCode;
	}
	
	doc.getElementById("funcPermission").value = funcPermission;
	parent.close_win("ModuleTree");
}