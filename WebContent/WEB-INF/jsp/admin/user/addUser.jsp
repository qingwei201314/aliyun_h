<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<script type="text/javascript" src="${util.path}/js/md5.js"></script>
<title>新增用户</title>
</head>
<body style="padding-top: 60px;">
	<div class="topbar">
		<div class="fill">
			<div class="container">
				<ul class="nav">
					<li class="active"><a href="#">新增用户</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container">
		<%@ include file="/commonjsp/validateMessage.jsp"%>
		<form id="myform" method="post"
			action="${util.path}/admin/shop/addUser.do" onsubmit="md5password();">
			<input type="hidden" name="password" id = "password" />
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
						<input type="password" name="customPw" id="customPw" size="30"
							maxlength="32" class="xlarge" />
					</div>
				</div>
				<div class="actions" style="padding-left: 360px;">
					<input type="submit" class="btn primary" value="保存" />
				</div>
			</fieldset>
		</form>
	</div>
	<script type="text/javascript">
		new LiveValidation('phone').add(Validate.Presence);
		new LiveValidation('password').add(Validate.Presence);

		function md5password(){
			var passwordMd5 = hex_md5($("#customPw").val());
			$("#password").val(passwordMd5);
		}
	</script>
</body>
</html>