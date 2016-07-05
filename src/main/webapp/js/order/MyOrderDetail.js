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
				$(".dialog-window1").append("<button type='button' class='ui-dialog-titlebar-close dialog-close1' style='top: 15px;'></button>");
				flag=1;
			}
		},
		resizable:false,
		autoOpen:false,
		modal:true,
		width:"auto"
	});

	$("*[dialog-target-name]").on("click",function(){
		$("*[dialog-name="+$(this).attr("dialog-target-name")+"]").dialog("open");
		//alert($(this).attr("orderDataId"));
		//setBaseLayers();
		getAllDataGemo($(this).attr("orderDataId"));
	});
	
	$(document).on("click",".dialog-close1",function(){
		$(this).parents(".dialog-window1").dialog("close");
		//map=null;
		//setBaseLayers();
	});
		   //getAllDataGemo(obj);
		     
		});
		
var layerServerURL = "http://210.77.87.225:8080/geowebcache/service/wms";
function getBaseLayer() {
	var baseLayer = new OpenLayers.Layer.WMS(
	// "电子地图", "http://58.252.5.46:8080/geowebcache/service/wms", {
	// "电子地图", "http://10.0.68.183:8080/geowebcache/service/wms", {
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
    map = new OpenLayers.Map('map', mapOptions);
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
	function getAllDataGemo(orderDataId){
		//请求后台数据库
		var r = Math.floor(Math.random() * 9999 + 1);
		var orderId =$("#orderId").val();  //获取订单ID
		var params = {r:r,orderId:orderId,orderDataId:orderDataId};
		var url ="./orderDataList";
		$.getJSON(url,params,function(data) {
			console.log(data);
			var dataList= data.listMap;
			var mapList=data.optionsMap;
			//先清空
			if(drawCustomExtendVector2.features.length > 0) 
			 {
				drawCustomExtendVector2.removeAllFeatures();
			 }
			for(var j=0;j<mapList.length;j++){
				if(mapList[j].EQS_range!=null&&mapList[j].EQS_range!=""){
					//覆盖地区坐标点
					showAreaZoomByRangePoint(mapList[j].EQS_range);
				}else if(mapList[j].EQS_area_no!=null&&mapList[j].EQS_area_no!=""){
					//根据地区ID
					showAreaZoomByAreaId(mapList[j].EQS_area_no);
				}
			}
			//先清空
			if(drawCustomExtendVector2.features.length > 0) 
			 {
				drawCustomExtendVector2.removeAllFeatures();
			 }
			for(var i=0;i<dataList.length;i++){
				//alert(dataList[i].range);
				showLocationBounds(dataList[i].range);
				cacheAreaRange = dataList[i].range;
			}
			//showLocationBounds(data.range);
			
		});
		
	}

	/**
	 * 显示数据库中数据的覆盖范围
	 */
	function showLocationBounds(bounds){
		//cleanAllFeatures();
		//bounds={"type":"Polygon","coordinates":[[[113.148225,42.55438889],[113.148225,36.03571667],[119.6972889,36.03571667],[119.6972889,42.55438889],[113.148225,42.55438889]]]};
		var format=new OpenLayers.Format.GeoJSON();
		var feature=format.read(bounds);
		drawCustomExtendVector2.addFeatures(feature);
		feature[0].geometry.calculateBounds();
		//map.zoomToExtent(feature[0].geometry.bounds);
		//map.moveTo(new OpenLayers.LonLat([map.center.lon, map.center.lat]),oldzoom+1.3,null);
	}
   
	/**
	 * 根据地区id显示地区的  空间尺度
	 * @param areaId
	 */
	function showAreaZoomByAreaId(areaId)
	{
		//请求后台数据库
		var r = Math.floor(Math.random() * 9999 + 1);
		var params = {r:r,areaId:areaId};
		var url = "./queryAreaGeomByAreaId_dataProduct";
		$.getJSON(url,params,function(data) {
				var format=new OpenLayers.Format.GeoJSON();
				var feature=format.read(data.data.geom);
				drawCustomExtendVector.addFeatures(feature);
				feature[0].geometry.calculateBounds();
				map.zoomToExtent(feature[0].geometry.bounds);
				//map.moveTo(new OpenLayers.LonLat([map.center.lon, map.center.lat]),oldzoom+1.3,null);
		});
	} 
   
	/**
	 * 根据数据覆盖范围坐标点，地区的空间尺度
	 * @param rangePoint
	 */
	function showAreaZoomByRangePoint(rangePoint){
		  var bounds='{"type":"Polygon","coordinates":[[';
			var str = rangePoint.split(",");
			for(var i = 0; i < str.length; i++)
			{
				var p1 = str[i].split(" ")[0];
				var p2 = str[i].split(" ")[1];
				bounds +="["+p1+", "+p2+"],";

			}
			bounds = bounds.substring(0, bounds.length-1);
			bounds +="]]}";
			//alert("bounds:"+bounds);
			var b = eval("(" + bounds + ")");
			var format=new OpenLayers.Format.GeoJSON();
			var feature=format.read(b);
			drawCustomExtendVector.addFeatures(feature);
			feature[0].geometry.calculateBounds();
			map.zoomToExtent(feature[0].geometry.bounds);
			map.moveTo(new OpenLayers.LonLat([map.center.lon, map.center.lat]),oldzoom+1.3,null);
		  
	}
