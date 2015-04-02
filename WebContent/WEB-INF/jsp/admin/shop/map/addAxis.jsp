<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2&ak=mQe3OOzL9rwQpf1hXMGO0afz"></script>
<script type="text/javascript" src="${util.path}/js/MarkerTool.js"></script>
<title>地图定位</title>
</head>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/admin/topbar.jsp"%>
	<div style="width: 100%; height: 450px; " id="container"></div>
<form action="${util.path }//admin/shop/map/reAxis.do">
	<div id="point" class="actions" style="padding-left: 360px;">
		<c:if test="${map==null }">
			<input type="button" value="开始标注" onclick="startPoint();" class="btn primary"/>
		</c:if>
		<c:if test="${map!=null }">
			<input type='submit' value='重新标注' class='btn primary'/>
		</c:if>
	</div>
</form>
<%@ include file="/commonjsp/footer.jsp"%>

<script type="text/javascript">
	$("#map").attr("class", "active");

	//点开始标注后执行的函数
	function startPoint(){
		mkrTool.open();
		//将按钮换成重新标注
		$("#point").html("<input type='submit' value='重新标注' class='btn primary'/>");
	}

	//地图开始
	var map = new BMap.Map("container");
	map.addControl(new BMap.NavigationControl()); 
	map.enableScrollWheelZoom();

	//如果已经有标注，则显示标注
	<c:if test="${map!=null && map.longitude!=null}">
		var point = new BMap.Point(${map.longitude}, ${map.latitude});
		var marker = new BMap.Marker(point);        // 创建标注    
		map.addOverlay(marker);  
		map.centerAndZoom(point, 12);
	</c:if>

	//如果没有标注，定位到省市区的位置
	<c:if test="${map==null}">
		var myGeo = new BMap.Geocoder();
		// 将地址解析结果显示在地图上,并调整地图视野
		myGeo.getPoint("${address}", function(point){
		  if (point) {
		    map.centerAndZoom(point, 12);
		  }
		  else{
			var point = new BMap.Point(116.404, 39.915);
			map.centerAndZoom(point, 12);
	      }
		}, "");
	</c:if>
	
	var mkrTool = new BMapLib.MarkerTool(map, {
		autoClose : true,
		followText : "标注所在位置"
	});
	//标注完成后触发的事件
	mkrTool.addEventListener("markend", function(e) {
		var longitude = e.marker.point.lng;
		var latitude = e.marker.point.lat;
		$.ajax({
			url: "${util.path}/admin/shop/map/saveMap.do",
			data: {
				longitude: longitude,
				latitude: latitude
			}
		});
	});
	//地图结束
</script>
</body>
</html>
