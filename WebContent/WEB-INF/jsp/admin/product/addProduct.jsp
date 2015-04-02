<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<link href="${util.path}/css/shop/shop.css" rel="stylesheet" />
<link href="${util.path}/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${util.path}/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${util.path}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${util.path}/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${util.path}/ueditor/lang/zh-cn/zh-cn.js"></script>
<title>用户登录</title>
</head>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/admin/topbar.jsp"%>
	<div class="container">
		<form action="${util.path}/admin/product/saveProduct.do" method="post">
			<input type="hidden" name="id" value="${product.id}" />
			<fieldset>
				<div class="clearfix">
					<label for="xlInput" class="label_width">类别</label>
					<div class="div_margin">
						<select name="categoryId" class="xlarge">
							<c:forEach var="category" items="${categoryList}">
								<option value="${category.id}" <c:if test="${category.id.equals(categoryId)}">selected="selected"</c:if>>${category.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="clearfix">
					<label for="xlInput" class="label_width">名称</label>
					<div class="div_margin">
						<input type="text" id="name" name="name" value="${product.name}" size="30" maxlength="12" class="xlarge" />
					</div>
				</div>
				<div class="clearfix">
					<label for="xlInput" class="label_width">描述</label>
					<div class="div_margin">
					</div>
				</div>
				<div class="clearfix">
					<div style="margin: auto;width: 800px;">
						<script id="myEditor" name="description"  type="text/plain" style="width:800px;height:240px;">${product.description}</script>
					</div>
				</div>
			</fieldset>
			<div class="actions" style="padding-left: 360px;">
				<input type="submit" class="btn primary" value="保存" />
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$("#product").attr("class","active");
		UE.getEditor('myEditor');

		//验证信息
		new LiveValidation('name').add(Validate.Presence);
	</script>
</body>
</html>