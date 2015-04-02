<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<link href="${util.path}/css/shop/shop.css" rel="stylesheet" />
<title>查看详细</title>
</head>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/topbar.jsp"%>
	
	<div class="container">
		<div class="hero-unit">
			<h1>${productVo.name }</h1>
			<pre style="margin-top: 10px;border: none;">
				${productVo.description }
			</pre>
		</div>

		<!-- 显示详细图片 -->
		<div id="img_detail" class="img_detail">
			<c:forEach var="image" items="${productVo.imageList }" end="${productVo.pageSize -1}">
			<dd>
				<h4 style="height: 0px;">&nbsp;</h4>
				<img onclick="showImage(this.src, '${productVo.name}')" class="product_detail" src="${util.statics }${util.repository }${image.path }${image.postfix}" />
			</dd>
			</c:forEach>
		</div>

		<!-- 加载更多信息 -->
		<c:if test="${productVo.hasNext}">
			<div id="loader" class="alert-message block-message success"
				style="margin-top: 28px; width: 400px; margin-left: auto; margin-right: auto;">
				<p style="text-align: center;"><img src="${util.path}/img/loader.gif" style="margin-right: 10px;vertical-align: top;">数据加载中</p>
			</div>
		</c:if>
		<%@ include file="/commonjsp/footer.jsp"%>
	</div>
	<script type="text/javascript">
		$("#${categoryId}").attr("class", "active");

		//点击图片时，弹出图片查看窗口
		function showImage(imageSrc,productName){
			$("body").addClass("backgroud_gray");
			var viewImagePath = "${util.path}/product/viewImage.do?imageSrc=" + imageSrc + "&productName="+productName;
			window.showModalDialog(viewImagePath,"", "dialogWidth:820px;dialogHeight:620px;status:no;");
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
	    				url: "${util.path}/product/moreImage.do",
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