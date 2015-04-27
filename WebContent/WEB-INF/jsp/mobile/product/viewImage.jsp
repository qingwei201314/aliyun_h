<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/commonjsp/head.jsp"%>
<title>${productName}</title>
</head>
<body style="text-align: center;">
	<img style="width: 100%; height: 100%;" id="opener"
		class="product_detail" src="${imageSrc}">
	<div>
		<a href="javascript:void(0)" id="returnBack" class="btn primary large" style="width: 120px;height: 50px;font-size: 20pt;padding-top: 18px;">返回</a>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#returnBack").click(function(){
				window.close();
			});
		});
	</script>
</body>
</html>