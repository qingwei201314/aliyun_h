<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<script type="text/javascript" src="${util.path}/js/md5.js"></script>
<title>用户登录</title>
</head>
<body>
<body style="padding-top: 60px;">
	<div class="topbar">
		<div class="fill">
			<div class="container">
				<ul class="nav">
					<li class="active"><a href="#">用户登录</a></li>
					<li ><a href="${util.path }">返回首页</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container">
		<%@ include file="/commonjsp/validateMessage.jsp"%>
		<form id="myform" method="post"  onsubmit="passwordMd5();"
			action="${util.path}/user/loginValidate.do">
			<fieldset>
				<div class="clearfix">
					<label for="xlInput" style="width: 340px;">电话</label>
					<div class="input" style="margin-left: 360px;">
						<input type="text" id="phone" name="phone" size="30"
							maxlength="15" class="xlarge" />
					</div>
				</div>
				<div class="clearfix">
					<label for="xlInput" style="width: 340px;">密码</label>
					<div class="input" style="margin-left: 360px;">
						<input type="password" name="password" id="password" size="30"
							maxlength="32" class="xlarge" />
					</div>
				</div>
				<div class="actions" style="padding-left: 360px;">
					<input type="submit" class="btn primary" value="登录" />
				</div>
			</fieldset>
		</form>
	</div>
	<script type="text/javascript">
		new LiveValidation('phone').add(Validate.Presence);
		new LiveValidation('password').add(Validate.Presence);

		function passwordMd5(){
			$("#password").val(hex_md5($("#password").val()));
		}
	</script>
</body>
</html>