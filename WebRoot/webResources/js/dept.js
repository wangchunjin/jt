/**
 * 设置上层部门
 * 
 * @param node
 * @param e
 * @return
 */
function setParentDept(node, e) {
	var deptCode = node.id;
	var deptName = node.text;
	var doc = window.parent.document.getElementsByTagName("iframe")["edit_dept_frm"].contentWindow.document;

	doc.getElementById("deptName").value = deptName;
	if (deptCode == doc.getElementById("deptCode").value) {
		parent.close_win("setDept");
		return false;
	} else {
		doc.getElementById("deptCode").value = deptCode;
		deptService.createDeptCode(deptCode, function (newCode) {
			window.parent.document.getElementsByTagName("iframe")["edit_dept_frm"].contentWindow.document.getElementById("dept.deptCode").value = newCode;
			$(doc.getElementById("editDeptName")).attr("ajaxurl","checkDept.action?parentDeptCode="+deptCode+"&deptCode="+newCode)
			parent.close_win("setDept");
		});
	}
}

/**
 * 设置用户所属的部门
 * 
 * @param node
 * @param e
 * @return
 */
function setDept(node,e){
	var deptCode = node.id;
	var deptName = node.text;
	var doc = window.parent.document.getElementsByTagName("iframe")["edit_user_frm"].contentWindow.document;
	
	doc.getElementById("deptName").value = deptName;
	if (deptCode == doc.getElementById("deptCode").value) {
		parent.close_win("setUserDept");
		return false;
	} else {
		doc.getElementById("deptCode").value = deptCode;
		parent.close_win("setUserDept");
	}
}

/**
 * 查找下属部门列表
 * 
 * @param node
 * @param e
 * @return
 */
function findChildForAdmin(node, e){
	var deptCode = node.id;
	window.open("list.action?dept.deptCode="+deptCode,"dept_right");
}

/**
 * 查找所属用户
 * 
 * @param node
 * @param e
 * @return
 */
function findUserForAdmin(node, e){
	var deptCode = node.id;
	window.open("../user/list.action?user.deptCode="+deptCode,"user_right");
}