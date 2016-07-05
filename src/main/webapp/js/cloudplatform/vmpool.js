var int;
/**
 * 点击【用一会】按钮在虚拟机池中获取虚拟机进行连接使用的前置条件，判断是否有在用
 */
function judgeUsingVmExist(dataid) {
	var params = {
		dataid : dataid
	};
	var url = rscloudmartHost+"/judgeUsingVmExist.htm";

	if (browserJudge() == false) {
		return false;
	}
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : url,
		data : params,
		error : function(data) { // 请求失败处理函数
			removeVmLoadingMsg("body");
			//TODO 错误页面
			alert('请求失败，错误信息：'+data);
		},
		success : function(data) {
			if (data.flag == 0) {
				removeVmLoadingMsg("body");
				openSpice(data.ip, data.port, data.timekey);
			} else if (data.flag == "notUsing") {
				int = setInterval("showLoading()", 1000);
				getVmViaSpice(data.flag, dataid,int);
			} else {
				if (confirm("您正在线使用另外一个软件，若继续申请会强制关闭，请及时保存好处理成果。是否继续？")) {
					int = setInterval("showLoading()", 1000);
					getVmViaSpice(data.flag, dataid,int);
				} else {
					removeVmLoadingMsg("body");
				}
			}
		}
	});

}

/**
 * 点击【用一会】按钮在虚拟机池中获取虚拟机进行连接使用
 * 
 * @param flag
 */
function getVmViaSpice(flag, dataid,int) {
	showLoading(int);
	var params = {
		flag : flag,
		dataid : dataid
	};
	var url = rscloudmartHost+"/getVmViaSpice.htm";

	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : url,
		data : params,
		error : function(data) { // 请求失败处理函数
			removeVmLoadingMsg("body");
			alert('请求失败，错误信息：'+data);
			int=window.clearInterval(int);
		},
		success : function(data) {
			removeVmLoadingMsg("body");
			if (data.code == 1) {
				int=window.clearInterval(int);
				openSpice(data.ip, data.port, data.timekey);
			} else if (data.code == -1) {// 返回排队页面
				int=window.clearInterval(int);
				openQueue(data.dataid);
			} else {
				int=window.clearInterval(int);
				//TODO 错误页面
				alert(data.message);
			}
		}
	});
}

function openSpice(ip, port, timekey) {
	var url = rscloudmartHost+"/webspice.htm?ip=" + ip + "&port=" + port + "&time=" + timekey;
	window.open(url);
}

function openQueue(dataid) {
	var url = rscloudmartHost+"/cloudplatform/queuepage.htm?dataid=" + dataid;
	window.open(url);
}

function showLoading(int){
	$("#loadingPannel").show();
	$.ajax({
		async : false,
		cache : false,
		type : 'get',
		dataType : "json",
		url : rscloudmartHost+"/vmProgress.htm",
		error : function(data) { // 请求失败处理函数
			alert('请求失败，错误信息：'+data);
		},
		success : function(data) {
			if (data.progress == "null") {
				$("#loadingPercentage").html("0");
				$("#loadingtext").html("正在连接");
			}
			if (data.progress == "100") {
				$("#loadingPercentage").html(data.progress);
				$("#loadingtext").html(data.text);
				int=window.clearInterval(int);
			}else{
				$("#loadingPercentage").html(data.progress);
				$("#loadingtext").html(data.text);
			}
		}
	});
}