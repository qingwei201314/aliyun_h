<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <%@ include file="/commonjsp/head.jsp"%>
    <link href="${util.path}/css/mobile/shop/shop.css" rel="stylesheet"/>
    <title>关于我们</title>
</head>
<body>
<%@ include file="/commonjsp/mobile/topbar.jsp"%>

<div style="width:100%;">
    <div style="width:90%;margin:auto;margin-top:15px;">
			<pre style="border: none;">
				${about.content}
			</pre>
    </div>
    <%@ include file="/commonjsp/mobile/footer.jsp"%>
</div>

<script type="text/javascript">
    $("#aboutUs").attr("class", "active");
</script>
</body>
</html>