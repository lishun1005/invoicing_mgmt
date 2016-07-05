function getQueueCount(){
	var r=parseInt(10000*Math.random());
	$.ajax({
		url:rscloudmartHost+'/cloudplatform/getqueuecounter.htm',
		data:{dataid:$('#dataid').val(),r:r},
		dataType:'json',
		success:function(data){
			if(data.result==1){
				var queuemsg='您好，目前有'+data.data+'位用户在您前面等候着试用虚拟机，请耐心等候';
				$('#queuemsg').html(queuemsg);
			}
			else if(data.result==2){
				clearTimeout(g_queueHandler);
				var responseData=data.data.split(",");
				$('#queuemsg').html('恭喜用户，您已排队成功，可以正常试用该虚拟机,请在'+responseData[0]+'前使用');
				$('#btnFreeuse').click(function(){
					openVMMachine(responseData[1]);
				});
				$('#btnFreeuse_span').addClass('queuebutton_able');
			}
			else if(data.result==3){
				clearTimeout(g_queueHandler);
				$('#queuemsg').html('该资源您尚未排队申请，请先到工作台排队申请');
			}
		}
		
	});
}

var g_queueHandler=setInterval(getQueueCount,1000);