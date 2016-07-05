var map;
var vectors;
var cacheAreaRange;
var idArray;
var geomArray;  
var oldzoom;
$(document).ready(function(){
	//设置地图底图
	setBaseLayers();
	
	var flag=0;
	$(".dialog-window1").dialog({
		open:function(e,ui){
			if(flag==0){
				$(".ui-dialog-titlebar").remove();
				$(".dialog-window1").css({"padding":0});
				$(".dialog-window1").append("<button type='button' class='ui-dialog-titlebar-close dialog-close2' style='top: 15px;'></button>");
				flag=1;
			}
		},
		resizable:false,
		autoOpen:false,
		modal:true,
		width:"auto"
	});

	$("#imagePostion,#imagePostion_1,#productPostion,#productPostion_1").on("click",function(){
		$("*[dialog-name="+$(this).attr("dialog-target-name")+"]").dialog("open");
		var type =$(this).attr("type"),id="";
		if("1" == type){
			id=$(this).attr("imageOrderId");
		}else if("2" == type){
			id=$(this).attr("productOrderId");
		}else{
			alert("type值有误!");
		}
		getAllDataGemo(id,type);
	});
	
	$(document).on("click",".dialog-close2",function(){
		$(this).parents(".dialog-window1").dialog("close");
		//setBaseLayers();
	});
		   //getAllDataGemo(obj);
		     
		});
		
var layerServerURL = "http://210.77.87.225:8080/geowebcache/service/wms";
function getBaseLayer() {
	var baseLayer = new OpenLayers.Layer.WMS(
	"电子地图", layerServerURL, {
		layers : "mv-ne_sw-jpeg_90_2013_world_china-0gtscreen",
		format : "image/jpeg",
		styles : '',
		transparent : true
	}, {
		resolutions : [ 0.703125, 0.3515625, 0.17578125, 0.087890625,
				0.0439453125, 0.02197265625, 0.010986328125, 0.0054931640625,
				0.00274658203125, 0.001373291015625, 6.866455078125E-4,
				3.4332275390625E-4, 1.71661376953125E-4, 8.58306884765625E-5 ], // ,
		// 4.291534423828125E-5,
		// 2.1457672119140625E-5,
		// 1.0728836059570312E-5,
		// 5.364418029785156E-6],
		tileSize : new OpenLayers.Size(256, 256),
		tileOrigin : new OpenLayers.LonLat(-180, 90),
		isBaseLayer : true,
		// wrapDateLine:true,
		maxExtent : new OpenLayers.Bounds(-180.0, -90.0, 180.0, 90.0)
	});
	return baseLayer;
}

function setBaseLayers(){
	/* 设置中央地图区域的地图 */
    mapOptions = { 
   		 displayProjection : new OpenLayers.Projection("EPSG:4326"),
   		 units: "degrees",
   		// numZoomLevels:15,
   		 // allOverlays: true,
   		 controls: [],
   		 projection: new OpenLayers.Projection("EPSG:4326"),
	     maxExtent: new OpenLayers.Bounds(-180.0,-90.0,180.0,90.0)
   		 };
    map = new OpenLayers.Map('map1', mapOptions);
    var baseLayer= getBaseLayer();
    baseLayer.params.format = "image/jpeg";
	map.addLayer(baseLayer); 
	map.addControl(new OpenLayers.Control.Navigation());
	//map.addControl(new OpenLayers.Control.PanZoomBar({position: new OpenLayers.Pixel(2, 15)}));
	//鼠标当前坐标
	map.addControl(new OpenLayers.Control.MousePosition());
	
	// 定位到全国
	map.moveTo(new OpenLayers.LonLat(105.410562,31.209316),3);
	
	//增加画图控件
	//用户用来勾画想要查找的范围的工具
	drawCustomExtendVector= new OpenLayers.Layer.Vector("drawExtent",{
		 styleMap: new OpenLayers.StyleMap({
			"default":new OpenLayers.Style({
						 strokeColor: "#0000FF",
			 			 strokeOpacity:0.7,
						 fillColor : "#000000",
						 fillOpacity:0
						})
		 })
	}); 
	
	drawCustomExtendVector2= new OpenLayers.Layer.Vector("drawExtent",{
		 styleMap: new OpenLayers.StyleMap({
			"default":new OpenLayers.Style({
							 strokeWeight: 5,
							 strokeOpacity:1,
							 strokeColor : "#ffffff",
							 fillColor : "#993300",
							 fillOpacity:0.4
						})
		 })
	}); 
	oldzoom = map.zoom;
	map.addLayer(drawCustomExtendVector); 
	map.addLayer(drawCustomExtendVector2); 
	
}

	/***/
	function getAllDataGemo(orderDataId,type){
		//请求后台数据库
		var r = Math.floor(Math.random() * 9999 + 1);
		//var imageOrderId =$.trim($("#ImagePostion").attr("ImageOrderId"));  //获取定制化订单ID
		var params = {r:r,imageOrderId:orderDataId,type:type};
		var url ="./imageOrderDataList";
		$.getJSON(url,params,function(data) {
			var dataList= data.ImageData;
			//先清空
			if(drawCustomExtendVector2.features.length > 0) 
			 {
				drawCustomExtendVector2.removeAllFeatures();
			 }
				if(dataList[0].image_range!=null&&dataList[0].image_range!=""){
					//覆盖地区坐标点
					showAreaZoomByRangePoint(dataList[0].image_range);
				}else{
					alert("暂无地理信息!");
				}
			
		});
		
	}

	/**
	 * 显示数据库中数据的覆盖范围
	 */
	function showLocationBounds(bounds){
		var format=new OpenLayers.Format.GeoJSON();
		var feature=format.read(bounds);
		drawCustomExtendVector2.addFeatures(feature);
		feature[0].geometry.calculateBounds();
	}
   
	/**
	 * 根据数据覆盖范围坐标点，地区的空间尺度
	 * @param rangePoint
	 */
	function showAreaZoomByRangePoint(rangePoint){
		var format=new OpenLayers.Format.GeoJSON();
		var feature=format.read(rangePoint);
		drawCustomExtendVector.addFeatures(feature);
		feature[0].geometry.calculateBounds();
		map.zoomToExtent(feature[0].geometry.bounds);		  
	}
