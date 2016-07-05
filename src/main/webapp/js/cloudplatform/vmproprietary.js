/**
 * 点击个人中心云工作台的【使用】按钮进行连接虚拟机
 */
function connectProprietaryVm(vmid,workbenchid){
	var params = {vmid:vmid,workbenchid:workbenchid};
	var url = "./connectProprietaryVm";

	if(browserJudge()==false){
		return false;
	}
	$.ajax({
		async: false,
		cache: false,
		type: 'POST',
		dataType: "json",
		url: url,
		data:params,
		error: function () { //请求失败处理函数
//			removeloading("body");
			alert('请求失败');
		},
		success: function (data) {
//			removeloading("body");
			if(data.code == 0) {
				alert("连接错误请联系客服");
			}else{
				openSpice(data.ip,data.port,data.timekey);
			}
		}
	});
}

function openSpice(ip,port,timekey){
	var url = "./webspice.htm?ip="+ip+"&port="+port+"&time="+timekey;
	window.open(url);
}

//编辑备注信息
var editObj = {
		edit:function(t){
			$(t).text("保存").siblings("span").hide().siblings("input").show();
		},
		save:function(t){
			var content = $(t).siblings("input").val();
			$(t).text("编辑").siblings("span").text(content).show().siblings("input").hide();
		},
		faile:function(t){
			alert("保存失败！");
			var beforeContent = $(t).siblings("span").data("before");
			$(t).text("编辑").siblings("span").text(beforeContent).show().siblings("input").val(beforeContent).hide();
		}
};

var workbench = {workbenchId:"",remark:""};

$(".btn-edit").on("click",function(){
	var current = this;
	if("编辑" == $(this).text()){
		editObj.edit(this);
	}else if("保存" == $(this).text()){
		workbench.workbenchId = $(this).parents("tr").data("vmid");
		workbench.remark = $(this).siblings("input").val();
		$.ilajax({
			url:rscloudmartHost+"/ILupdateVmProprietaryRemark",
			data:workbench,
			dataType:"json",
			type:"POST",
			successfn:function(data){
				if("0" == data.code){
					editObj.faile(current);
				}else if("1" == data.code){
					editObj.save(current);
				}else{
					editObj.faile(current);
				}
			}
		});
	}
});

