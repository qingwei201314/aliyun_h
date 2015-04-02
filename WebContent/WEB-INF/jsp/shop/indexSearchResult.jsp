<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<link href="${util.path}/css/shop/shop.css" rel="stylesheet" />
<title>搜索</title>
</head>
<body>
<form id="pageForm" action="${util.path }/index/search.do" method="get">
	<div class="container">
		<div style="margin-top: 10px;display: table;padding-left: 127px;">
			<div style="float: left;margin-right: 18px;">
				<a href="${util.path}/user/login.do"><img src="${util.path}/img/logo.png" style="border:0;width: 150px;height: 58px;" /></a>
			</div>
			<div style="float: left;margin-top: 14px;">
				<input type="text" name="q" value="${q }" class="search_div_input" style="border-radius:0px;" />
				<input type="button" onclick="searchSubmit();" class="search_div_button" style="height: 34px;width: 100px;border-radius:0px;" />
			</div>
		</div>

		<div class="row" style="width: 960px;margin-top: 10px;">
			<c:forEach var="productVo" items="${page.list }" end="${page.pageSize -1}">
				<div class="span-one-third" style="height: 210px;">
					<h4>${productVo.name}</h4>
					<p>
						<a href="${util.path }/product/viewProduct.do?productId=${productVo.id}">
							<img class="product" src="${util.statics}${util.repository}${productVo.path}${productVo.postfix}" onError="this.src='${util.path}/img/product/none.jpg'" />
						</a>
					</p>
				</div>
			</c:forEach>
		</div>
		<!-- 分页-->
		<jsp:include page="/commonjsp/page.jsp">
			<jsp:param name="pageForm" value="pageForm" />
		</jsp:include>
	</div>
</form>
<script type="text/javascript">
	function searchSubmit(){
		document.getElementById("pageForm").submit();
	}
</script>
</body>
</html>