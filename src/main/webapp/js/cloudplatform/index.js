
/**
 *	点击在线使用根据用户登录状况是弹出登录框还是正常操作的对话框
 */
function onlineuse(id,name){
    var selSoftwareData=getSelSoftwareData();
	selSoftwareData.id=id;
	selSoftwareData.name=name;
	
//	var result=changeLoginFlag(true,function(){
//		poponlineuse();
//	});
//	if(result){
//		poponlineuse();		
//	}	
	changeLoginFlag(true,function(){
		poponlineuse();
	});
}

/**
 *函数功能:弹出选择使用方式选择框
 *
 **/
function poponlineuse(){
	showdialog('onlineusedialog');
	
//	$.dialog({
//		title : "在线使用dialog",
//		content : data
//	})
}
g_selSoftwareData={};
/**
 * 获得选择软件的id及名字信息
 * @returns 对象：id:选择软件的id号
 * 			price：选择软件的价格
 * 			name:选择软件的名称
 */
function getSelSoftwareData(){
	
	return g_selSoftwareData;
}

function openVMMachine(id){
	showVmLoadingMsg(document.body);
	judgeUsingVmExist(id);
}
function onlineusesumbit(obj){
	
	
	var selValue=null;
	var selValue=$('input[name=freeUsed]:checked').val();
	var data=getSelSoftwareData();

	var id=data.id;
	var name=data.name;
	if(selValue=='free'){
		$(obj).parents(".dialog_bg").hide();
		openVMMachine(id);
		
		
	}	
	else if(selValue=='custom'){
		$(obj).parents(".dialog_bg").hide();
		
		$('#sDZSoftwareInfo').html(name);
		
		
		clearPreSoftwareInfo(id);
		$('#confirmsoftwaredialog_price_softwarename').html(name);
		disablenoworkplacedialognext();
		go2SelOption();
		
	}
	//$(obj).parents(".dialog_bg").remove();
}

function clearPreSoftwareInfo(){
	var data=getSelSoftwareData();
	var price=getSelSoftwarePrice(data.id);
	data.price=parseFloat(price);
	$('#noworkplacedialog_softwareprice').html('￥'+data.price.toFixed(2)+'元');
	$('#confirmsoftwaredialog_price_price').html('￥'+data.price.toFixed(2)+'元');
	$('input[name=raworkbenchtype]').attr('checked',false);
	$($('input[name=raworkbenchtype]')[0]).attr('checked',true);
	
	$('input[name=servicetime]').attr('checked',false);
	$($('input[name=servicetime]')[0]).attr('checked',true);
	$($('input[name=servicetime]')[0]).click();
	$('#totalprice').html('');
	$('#workbench_price').html('');
	disablenoworkplacedialognext();
	
}
/**
 * 获得当前选择软件价格
 */
function getSelSoftwarePrice(id){
	var sell_price=$(document.getElementById('hid_software_sell_price_'+id)).val();
	var price=sell_price;
	var promotions=$(document.getElementById('hid_software_promotions_'+id)).val();
	if(!g_isEmpty(promotions)){
		price=promotions;	
	}
	return price;
}
/**
 * 选择创建新的工作台
 *
 */
function go2SelOption(){
	showdialog('noworkplacedialog');
}
/**
 *计算工作台价格
 *
 **/
function caculateWorkbench(){
	var selSoftware=getSelSoftwareData();
	var raworkbenchtype=$('input[name=raworkbenchtype]:checked');
	var month=$('input[name=servicetime]:checked').val();
	selSoftware.workbenchtype=raworkbenchtype.val();
	if(raworkbenchtype!=null&&raworkbenchtype.length>0){
		selSoftware.workbenchid=raworkbenchtype[0].id;
	}
	selSoftware.month=month;
	var workbenchtype=raworkbenchtype.val();
	
	if(g_isEmpty(workbenchtype)||g_isEmpty(month)){
		return;
	}
	var dataid=selSoftware.id;
	disablenoworkplacedialognext();
	$.ajax(
		{
			url:rscloudmartHost+'/cloudplatform/caculateWorkbenchPrice.htm',
			data:{dataId:dataid,
				workbenchtype:workbenchtype,
				workbenchtypeid:selSoftware.workbenchid,
				month:month},
			dataType:'json',
			success:function(obj){
				var selSoftware=getSelSoftwareData();
				//var workbenchprice=parseFloat(obj.data);
				var totalprice=parseFloat(obj.data);
				if(isNaN(totalprice)){
					totalprice=0;
				}
				
				selSoftware.workbenchprice=totalprice-selSoftware.price;
				
				selSoftware.totalprice=totalprice;
				$('#workbench_price').html('￥'+selSoftware.workbenchprice.toFixed(2)+'元');
				$('#confirmsoftwaredialog_price_workbenchprice').html('￥'+selSoftware.workbenchprice.toFixed(2)+'元');
				$('#confirmsoftwaredialog_price_totalprice').html('￥'+selSoftware.totalprice.toFixed(2)+'元');
				$('#totalprice').html('￥'+selSoftware.totalprice.toFixed(2)+'元');
				enablenoworkplacedialognext();
			}			
		}
	);
}
function disablenoworkplacedialognext(){
	$('#noworkplacedialog_next').attr('disabled',true);
	$('#noworkplacedialog_next').addClass('unable');
}
function enablenoworkplacedialognext(){
	$('#noworkplacedialog_next').attr('disabled',false);
	$('#noworkplacedialog_next').removeClass('unable');
}
function confirmOrder(obj){
	var id=getSelSoftwareData().id;
	var workbenchtype=$('input[name=raworkbenchtype]:checked').next().html();
	var month=$('input[name=servicetime]:checked').val();
	$('#confirmsoftwaredialog_workbench').html(workbenchtype);
	$('#confirmsoftwaredialog_servicetime').html(month);
	$('#confirmsoftwaredialog_softwareimg_url').attr('src',$(document.getElementById('software_'+id)).attr('src'));
	$('#confirmsoftwaredialog_software_license').html($(document.getElementById('hid_software_lic_'+id)).val());
	$(obj).parents(".dialog_bg").hide();
	showdialog('confirmsoftwaredialog');
}
function joinPlatform(id,name){
	var selSoftware=getSelSoftwareData();
	selSoftware.id=id;
	selSoftware.name=name;
	selSoftware.price=parseFloat(getSelSoftwarePrice(id)).toFixed(2);
	$('#sDZSoftwareInfo').html(name);
	changeLoginFlag(true,function(){
		popJoinWorkbench();
	});
	
	
}

/**
 * 加入工作台选择使用方式
 *
 */
function selJoinWorkbench(){		
	var joinworkbench=$('input[name=hadworkplacedialog_freeUsed]:checked').val();
	if(joinworkbench=="freeused"){
		var selWorkbenchValue=$('#sel_hadworkplacedialog_ownedworkbench').val();
		if(g_isEmpty(selWorkbenchValue)){
			alert('请选择已有的工作台');
			return;
		}
		else{
			var selSoftware=getSelSoftwareData();
			selSoftware.workbenchid=selWorkbenchValue;
			$('#sel_hadworkplacesoftwaredialog_workbenchsel').val(selWorkbenchValue);
			$('#s_hadworkplacesoftwaredialog_selSoftware').html(selSoftware.name);
			$('#s_hadworkplacesoftwaredialog_selPrice').html(selSoftware.price);
			showdialog('hadworkplacesoftwaredialog');
		}
	}
	else if(joinworkbench=="createnew"){
		clearPreSoftwareInfo();
		go2SelOption();
	}
	else{
		alert('请选择工作台或者创建新的工作台');
		return;
	}
}
function popJoinWorkbench(){
	var selSoftwareData=getSelSoftwareData();
	//如果没有加载过已有工作台数据，首先请求加载
	if(g_isEmpty(selSoftwareData.ownedworkbencloaded))
	{
		$.ajax({
			url:rscloudmartHost+'/listProprietaryVm.htm',
			dataType:'json',
			success:function(obj){
				if(obj.count>0){
					for(var i=0;i<obj.count;i++){
						var option="<option value='"+obj.result[i].workbenchId+"'>"+obj.result[i].nickname+"</option>";
						
						$('#sel_hadworkplacedialog_ownedworkbench').append(option);
						$('#sel_hadworkplacesoftwaredialog_workbenchsel').append(option);
					}
					}
				else{
					$('#hadworkplacedialog_ownedworkbench_holder').css('display','none');
					$('#createnew').attr('checked',true)
				}
				selSoftwareData.ownedworkbencloaded=true;
				showdialog('hadworkplacedialog');
			}
		});
	}
	else
		showdialog('hadworkplacedialog');
	
}
function closepopdialog(obj){
	$(obj).parents(".dialog_bg").hide();
}

/**
 * 用户提交订单购买工作台和软件
 */
function go2PayOrder(){
	$("#confirmsoftwaredialog").hide();
	$(".added-successfully_div").show().find(".added-successfully").show();
	var selSoftware=getSelSoftwareData();
	
	var submitArgs={dataId:selSoftware.id,
		workbenchtype:selSoftware.workbenchtype
		,workbenchid:selSoftware.workbenchid,
		month:selSoftware.month};
	window.open(rscloudmartHost+'/cloudplatform/pay4platform.htm?'+$.param(submitArgs));
	
}
/**
 *  单独购买软件
 */
function buySoftware(){
	var selSoftware=getSelSoftwareData();
	var workbenchid=$('#sel_hadworkplacesoftwaredialog_workbenchsel').val();
	var submitArgs={dataId:selSoftware.id,workbenchid:workbenchid};
	window.open(rscloudmartHost+'/cloudplatform/pay4software.htm?'+$.param(submitArgs));
}

/**
 * 显示等待进度条
 * @param id
 */
function showVmLoadingMsg(id){
	$(id).append("<div class='dialog_bg' style='display:block;' id='loadingPannel'><div class='used_type4' style='left: 30%;top: 30%;'><p class='instruction_p' >亲，请耐心等待：已完成了<span id='loadingPercentage'>0%</span></p><p class='instruction_p supplementary' id='loadingtext'>云工作台正努力地跑着...<span id='loadingtext'>正在连接</span></p></div></div>");
}

/**
 * 隐藏等待进度条
 * @param id
 */
function removeVmLoadingMsg(id){
	$(id+" .dialog_bg").hide();
}
