var checkFormValue = function(formid){
	var form = document.getElementById(formid);;
	for(var i=0;i< form.elements.length;i++){
		var element = form.elements[i];
		if(!checkValue(element)){
			return false; 
		}
	}
	return true;
}
function invalidHandler(e){
    checkValue(e.target);
    e.stopPropagation();
    e.preventDefault();
}
var checkValue = function(element){
	if(element.checkValidity()){
		element.parentElement.className="form-group";
		return true;
	}else{
		element.parentElement.className="form-group has-error";
		alert(element.validationMessage);
		return false;
	}
}
function loadinvalidHandler(obj) {
	for(var i=0;i<obj.length;i++){
		var myform = document.getElementById(obj[i]);
		for(var i=0;i< myform.elements.length;i++){
			var elementTagName=myform.elements[i].tagName;
			if(elementTagName=="INPUT"){//只对input便签验证
				myform.elements[i].addEventListener("change",invalidHandler,false);
			}
		}
	}
}