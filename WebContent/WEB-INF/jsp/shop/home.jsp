<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<link href="${util.path}/css/shop/shop.css" rel="stylesheet" />
<title>首页</title>
</head>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/topbar.jsp"%>
	<div class="container">
		<div class="index_home">
			<div class="left_div">
				<img class="gate_img" src="${util.statics}${util.repository}${shop.gateUrl}" />
			</div>
			<div class="right_div">
				<dl>
					<dt>
						<h1>电话：${userPhone}</h1>
					</dt>
					<dd>
						<table class="gate_right">
							<tr>
								<td class="gate_right_td_left">联系人：</td>
								<td>${shop.contact}</td>
							</tr>
							<tr>
								<td class="gate_right_td_left">名称：</td>
								<td>${shop.name}</td>
							</tr>
							<tr>
								<td class="gate_right_td_left" style="padding-top: 10px;">地址：</td>
								<td style="padding-top: 10px;">${provinceTownCity}${shop.address}</td>
							</tr>
						</table>
					</dd>
					<c:if test="${map!=null&& map.longitude!=null }">
					<dd style="text-align: right;">
						<a href="${util.path }/shop/map/viewMap.do?shopId=${shop.id}" class="btn primary large">查看地图</a>
					</dd>
					</c:if>
				</dl>
			</div>
		</div>

		<!-- Example row of columns -->
		<div class="row" style="width: 960px;">
			<c:forEach var="productVo" items="${page.list }" end="${page.pageSize -1 }">
				<div class="span-one-third">
					<h4>${productVo.name}</h4>
					<p>
						<a href="${util.path }/product/viewProduct.do?productId=${productVo.id}&pageNo=1">
							<img class="product" src="${util.statics}${util.repository}${productVo.path}${productVo.postfix}" onError="this.src='${util.path}/img/product/none.jpg'" />
						</a>
					</p>
				</div>
			</c:forEach>
		</div>

		<%@ include file="/commonjsp/footer.jsp"%>
	</div>
</body>
</html>