<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<link href="${util.path}/css/shop/shop.css" rel="stylesheet" />
<title>产品搜索</title>
</head>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/topbar.jsp"%>
	<div class="container">
		<div class="row" style="width: 960px;">
			<c:forEach var="productVo" items="${page.list }">
				<div class="span-one-third" style="height: 210px;">
					<h4>${productVo.name}</h4>
					<p>
						<a href="${util.path }/product/viewProduct.do?productId=${productVo.id}&pageNo=1">
							<img class="product" src="${util.statics}${util.repository}${productVo.path}${productVo.postfix}" onError="this.src='${util.path}/img/product/none.jpg'" />
						</a>
					</p>
				</div>
			</c:forEach>
		</div>
		<!-- 分页 -->
		<form id="pageForm" action="${util.path }/product/search.do">
			<input type="hidden" name="shopId" value="${shopId }" />
			<input type="hidden" name="q" value="${q }" />
			<jsp:include page="/commonjsp/page.jsp">
				<jsp:param name="pageForm" value="pageForm" />
			</jsp:include>
		</form>

		<%@ include file="/commonjsp/footer.jsp"%>
	</div>
</body>
</html>