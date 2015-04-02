<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<link href="${util.path}/css/shop/shop.css" rel="stylesheet" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>地图位置</title>
<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#container {
	height: 100%
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=1.2&ak=Pv6hBzRYLwB9kjEGGIMXHr7B"></script>
</head>

<body>
<%@ include file="/commonjsp/topbar.jsp"%>
	<div id="container"></div>
	<script type="text/javascript">
		var map = new BMap.Map("container"); // 创建地图实例 
		var opts = {offset: new BMap.Size(3, 50)}; 
		map.addControl(new BMap.NavigationControl(opts)); 
		map.enableScrollWheelZoom(); 
		var point = new BMap.Point(${map.longitude}, ${map.latitude}); // 创建点坐标  
		var marker = new BMap.Marker(point);        // 创建标注    
		map.addOverlay(marker);   
		map.centerAndZoom(point, 16); // 初始化地图，设置中心点坐标和地图级别
	</script>
</body>
</html>
