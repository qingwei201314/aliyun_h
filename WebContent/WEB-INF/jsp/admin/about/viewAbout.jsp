<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<link href="../css/shop/shop.css" rel="stylesheet" />
<title>关于我们</title>
</head>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/admin/topbar.jsp"%>
	<div class="container">
		<form action="${util.path}/admin/about/editAbout.do" method="get">
			<div class="hero-unit" >
				<pre style="margin-top: 10px;border: none;">${about.content}</pre>
			</div>

			<div class="actions" style="padding-left: 360px;">
				<input type="submit" class="btn primary" value="编辑" />
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$("#about").attr("class", "active");
	</script>
</body>
</html>