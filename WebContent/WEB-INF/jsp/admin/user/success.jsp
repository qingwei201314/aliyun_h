<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<script type="text/javascript" src="${util.path}/js/md5.js"></script>
<title>操作成功</title>
</head>
<body style="padding-top: 60px;">
	<div class="popover left" style="display: block; position: static;">
		<div class="inner" style="margin: 0 auto;">
			<h3 class="title">操作成功成功。</h3>
			<div class="content">
				<p>${displayMessage }</p>
				<p><a href="${util.path }">返回首页</a></p>
			</div>
		</div>
	</div>
</body>
</html>