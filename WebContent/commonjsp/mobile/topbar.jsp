<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form action="${util.path }/productM/search.do" method="get" style="margin-bottom: 0px;">
<input type="hidden" name="shopId" value="${shop.id }"/>
<input type="hidden" name="pageNo" value="1"/>
<div class="topbar" style="position: static;">
	<div class="fill">
		<div class="container" style="width: 100%;">
			<a class="brand" style="margin-left: 5px;" href="${util.path}/${userPhone}">${shop.shortName}</a>
			<ul class="nav">
				<c:forEach var="category" items="${categoryList}">
					<li id="${category.id}"><a href="${util.path}/categoryM/listProduct.do?shopId=${shop.id}&categoryId=${category.id}&pageNo=1">${category.name}</a></li>
				</c:forEach>

				<li style="float: right;" data-dropdown="dropdown">
					<a href="#" class="dropdown-toggle">更多</a>
					<ul class="dropdown-menu">
						<c:if test="${categoryListHide != null}">
							<c:forEach var="category" items="${categoryListHide}">
								<li id="${category.id}"><a href="${util.path}/categoryM/listProduct.do?shopId=${shop.id}&categoryId=${category.id}&pageNo=1">${category.name}</a></li>
							</c:forEach>
						</c:if>
						<li id="aboutUs"><a href="${util.path }/aboutM/aboutUs.do?phone=${userPhone}">关于我们</a></li>
					</ul>
				</li>
			</ul>
			<!--
			<div style="float: right; width: 232px; margin-top: 6px;">
				<input type="text" name="q" value="${q }" style="line-height: 19px;float: left;padding-right: 28px;width: 195px;display: none;" />
				<span style="position: absolute;">
					<input type="submit" id="fdj" title="搜索" value="" />
				</span>
			</div>-->
		</div>
	</div>
</div>
</form>