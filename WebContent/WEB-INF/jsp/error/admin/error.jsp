<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<link href="${util.path}/uploadify/uploadify.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${util.path}/uploadify/jquery.uploadify.min.js"></script>
<title>出错了</title>
</head>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/admin/topbar.jsp"%>
	<div class="container">
		<div class="popover left" style="display: block; position: static;">
			<div class="inner" style="margin: 0 auto;">
				<h3 class="title">页面出错了。</h3>
				<div class="content">
					<p>对不起，页面出错了。</p>
				</div>
			</div>
		</div>

		<%@ include file="/commonjsp/footer.jsp"%>
	</div>
</body>
</html>