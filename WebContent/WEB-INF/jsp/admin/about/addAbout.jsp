<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<script type="text/javascript"
	src="${util.path}/ueditor/ueditor.config.js"></script>
<script type="text/javascript"
	src="${util.path}/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${util.path}/ueditor/lang/zh-cn/zh-cn.js"></script>
<title>关于我们</title>
</head>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/admin/topbar.jsp"%>
	<div class="container">
		<form action="${util.path}/admin/about/saveAbout.do" method="post">
			<input type="hidden" name="id" value="${about.id}" />
			<fieldset>
				<div class="clearfix">
					<div style="margin: auto; width: 820px;">
						<script id="myEditor" name="content" type="text/plain"
							style="width:820px;height:310px;">${about.content}</script>
					</div>
				</div>
			</fieldset>
			<div class="actions" style="padding-left: 360px;">
				<input type="submit" class="btn primary" value="保存" />
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$("#about").attr("class", "active");
		UE.getEditor('myEditor');
	</script>
</body>
</html>