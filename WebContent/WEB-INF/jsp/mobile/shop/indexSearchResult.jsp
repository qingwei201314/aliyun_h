<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
<%@ include file="/commonjsp/head.jsp"%>
<link href="${util.path}/css/mobile/shop/shop.css" rel="stylesheet"/>
<title>搜索</title>
</head>
<body>
<form id="pageForm" action="${util.path }/indexM/search.do" method="get">
	<div>
		<div>
			<div style="float: left;width:30%;text-align: center;margin-bottom: 5%;margin-top: 5%;">
				<a href="${util.path}/user/login.do"><img src="${util.path}/img/logo.png" style="border:0;width:80%;" /></a>
			</div>
			<div style="float: left;width:70%;margin-bottom: 5%;margin-top: 5%;">
				<input type="text" name="q" value="${q }" class="search_div_input" style="border-radius:0px;width:50%;" />
				<input type="button" onclick="searchSubmit();" class="search_div_button" style="height: 34px;width: 100px;border-radius:0px;" />
			</div>
		</div>

		<div style="padding: 0px 2% 0px 5%;">
			<c:forEach var="productVo" items="${page.list }" end="${page.pageSize -1}">
				<div style="width: 31%;float:left;margin-right: 2%;">
					<h6 style="color:black;font-weight: normal;">${productVo.name}</h6>
					<p>
						<a href="${util.path }/productM/viewProduct.do?productId=${productVo.id}">
							<img class="product" src="${util.statics}${util.repository}${productVo.path}${productVo.postfix}" onError="this.src='${util.path}/img/product/none.jpg'" />
						</a>
					</p>
				</div>
			</c:forEach>
		</div>

		<!-- 分页-->
		<jsp:include page="/commonjsp/mobile/page.jsp">
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