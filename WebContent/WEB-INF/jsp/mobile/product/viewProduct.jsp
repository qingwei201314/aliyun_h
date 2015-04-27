<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
	<%@ include file="/commonjsp/head.jsp"%>
	<link href="${util.path}/css/mobile/shop/shop.css" rel="stylesheet"/>
<title>查看详细</title>
</head>
<body>
	<%@ include file="/commonjsp/mobile/topbar.jsp"%>
	
	<div>
		<div class="description">
			<h4>${productVo.name }</h4>
			<pre style="border: none;">
				${productVo.description }
			</pre>
		</div>
		<!-- 显示详细图片 -->
		<div id="img_detail" class="img_detail" style="margin-top: 15px;">
			<c:forEach var="image" items="${productVo.imageList }" end="${productVo.pageSize -1}">
				<dd>
					<h4 style="height: 0px;">&nbsp;</h4>
					<img onclick="showImage(this.src, '${productVo.name}')" class="product_detail" src="${util.statics }${util.repository }${image.path }${image.postfix}" />
				</dd>
			</c:forEach>
		</div>

		<!-- 加载更多信息 -->
		<c:if test="${productVo.hasNext}">
			<div id="loader" class="loader_class" >
				<p style="text-align: center;margin-top: 9px;"><img src="${util.path}/img/loader.gif" style="margin-right: 10px;vertical-align: top;">数据加载中</p>
			</div>
		</c:if>
		<%@ include file="/commonjsp/mobile/footer.jsp"%>
	</div>
	<script type="text/javascript">
		$("#${categoryId}").attr("class", "active");

		//点击图片时，弹出图片查看窗口
		function showImage(imageSrc,productName) {
			$("body").addClass("backgroud_gray");
			var viewImagePath = "${util.path}/productM/viewImage.do?imageSrc=" + imageSrc + "&productName=" + productName;
			window.open(viewImagePath, "", "width:100%;height:100%;status:no;");
			$("body").removeClass("backgroud_gray");
		}

		//加载更多页面
		<c:if test="${productVo.hasNext }">
		$(document).ready(function() {
			var startRow="${productVo.imageList[productVo.pageSize].id }";
			var result="";  //返回的页面
			$(window).scroll(function(){
				var scrollTop = $(this).scrollTop();
				var windowHeight = $(this).height();
				var scrollHeight = $(document).height();
				if(startRow != '' &&  scrollTop + windowHeight == scrollHeight){
					$.ajax({
						url: "${util.path}/productM/moreImage.do",
						data: {
							productId: "${productVo.id }",
							startRow: startRow,
							date: new Date()
						},
						success: function( data ) {
							result = data;
							if(result.indexOf("id='theLast'") <0){
								$("#img_detail").append(data);
							}
							else{
								$("#loader").html(result);
							}
							var start = result.indexOf("lastId_start");
							var end = result.indexOf("lastId_end");
							startRow = result.substring(start, end );
						}
					});
				}
			});
		});
		</c:if>
	</script>
</body>
</html>