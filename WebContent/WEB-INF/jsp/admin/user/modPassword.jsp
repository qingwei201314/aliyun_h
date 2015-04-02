<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<script type="text/javascript" src="${util.path}/js/md5.js"></script>
<title>修改密码</title>
</head>
<body>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/admin/topbar.jsp"%>
	<div class="container">
		<%@ include file="/commonjsp/validateMessage.jsp"%>
		<form id="myform" method="post" onsubmit="passwordMd5();"
			action="${util.path}/admin/shop/updatePassword.do">
			<fieldset>
				<div class="clearfix">
					<label for="xlInput" style="width: 340px;">原密码</label>
					<div class="input" style="margin-left: 360px;">
						<input type="password" id="password" name="password" size="30"
							maxlength="15" class="xlarge" />
					</div>
				</div>
				<div class="clearfix">
					<label for="xlInput" style="width: 340px;">新密码</label>
					<div class="input" style="margin-left: 360px;">
						<input type="password" name="newpassword" id="newpassword" size="30"
							maxlength="32" class="xlarge" />
					</div>
				</div>
				<div class="clearfix">
					<label for="xlInput" style="width: 340px;">确认新密码</label>
					<div class="input" style="margin-left: 360px;">
						<input type="password" name="repassword" id="repassword" size="30"
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
		new LiveValidation('password').add(Validate.Presence);
		new LiveValidation('newpassword').add(Validate.Presence);
		new LiveValidation('repassword').add(Validate.Presence);

		function passwordMd5(){
			$("#password").val(hex_md5($("#password").val()));
			$("#newpassword").val(hex_md5($("#newpassword").val()));
			$("#repassword").val(hex_md5($("#repassword").val()));
		}
	</script>
</body>
</html>