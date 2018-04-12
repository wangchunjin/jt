function setArea(node,e){
	var ids = node.id.split("|");
	 var codeId = ids[0];
	 var itemValue = ids[3];
	 var itemText = node.text;
	 opener.document.getElementById("areaCode").value = itemValue;
	 opener.document.getElementById("areaName").value = itemText;
	 window.close();
}