/**
 * 位置图js
 */

var map;
var oldzoom;

//设置底图
function setBaseLayersPosition(){
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
    map = new OpenLayers.Map('map-position', mapOptions);
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
//	map.zoomToExtent(feature[0].geometry.bounds);
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
	var url = dataproductHost+"/queryAreaGeomByAreaId_dataProduct";
	$.getJSON(url,params,function(data) {
			var format=new OpenLayers.Format.GeoJSON();
			var feature=format.read(data.data.geom);
			drawCustomExtendVector.addFeatures(feature);
			//feature[0].geometry.calculateBounds();
			//map.zoomToExtent(feature[0].geometry.bounds);
			map.zoomToExtent(feature[0].geometry.getBounds());
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
		//feature[0].geometry.calculateBounds();
		//map.zoomToExtent(feature[0].geometry.bounds);
		map.zoomToExtent(feature[0].geometry.getBounds());
		//map.moveTo(new OpenLayers.LonLat([map.center.lon, map.center.lat]),oldzoom+1.3,null);
}

/**
 * 坐标点转换成showLocationBounds(param)方法可用的参数
 * <br>返回{"type":"Polygon","coordinates":[[[x1,y1],[x2,y2]...[x5,y5]]]}
 * @param point x1 y1,x2 y2,x3 y3,x4 y4,x5 y5
 */
function rangePointToBounds(point) {
	var tempArr = point.split(",");
	var bounds = '{"type":"Polygon","coordinates":[[';
	var joinStr = "";
	for(var i = 0; i < tempArr.length; i++){
		joinStr += "[" + tempArr[i].replace(" ", ",") + "]" + (i == tempArr.length) ? "" : ",";
	}
	return bounds + joinStr + "]]}";
}
