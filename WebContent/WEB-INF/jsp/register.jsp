<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户注册</title>
<style type="text/css">
.register {
	width: 100%;
	height: 90%;
	text-align: center;
}

.photoFrame {
	background-image: url(${util.path}/img/register/register.jpg);
	height: 768px;
	width: 571px;
	background-repeat: no-repeat;
	margin: 0 auto;
}

.photoFrame .div1{
	font-weight: bold;
	font-size: 17pt;
	padding-top: 190px;
	letter-spacing: 10px;
}

.photoFrame .div2{
	margin: 10px auto;
	text-align: left;
	width: 400px;
	font-size: 11pt;	
}

.photoFrame .div3{
	margin: 35px auto;
	width: 400px;
	font-size: 11pt;
}

</style>
</head>
<body>
	<div class="register">
		<div class="photoFrame">
			<div class="div1">&nbsp;注册说明</div>
			
			<div class="div2">
				<span style="margin-left: 32px;">注册本网站用户，每年必须支付人民币200元，没有其他费用。</span>
				<br/>注册完成后，用户具有以下功能：
				<ul style="margin-top: 2px;">
					<li>创建自己的企业网站；</li>
					<li>将产品展示在企业网站中；</li>
					<li>企业网站首页地址： gsj.im/您的电话号码</li>
				</ul>
				注意事项：
				<ul style="margin-top: 2px;">
					<li>用户有效期为：购买成功之日起，到第二年的同一日期止。例如：2014年1月28日购买成功，则使用期限为 2014年1月28日至2015年1月28日；</li>
					<li>用户需在有效期之内进行续费，逾期未续费的，网站管理员将对其网站进行关闭，关闭后用户所有信息将从本网站清除；</li>
					<li>用户禁止在企业网站中散布淫秽、色情、赌博、暴力、凶杀等违反法律法规的信息，违者将立即封ID；</li>
					<li>用户忘记密码。可直接发邮件到87863823@qq.com进行索取.网站管理员会在72小时内进行处理.</li>
				</ul>
			</div>
			<div class=div3>
				费用：¥200 有效期：${start } 至 ${end }
				<br/>
				<div style="float: right;padding-right: 12px;margin-top: 5px;">到《凯文信息》淘宝店<a onclick="openTaobao()" href="#" style="color: green;margin-left: 5px;">购买</a></div>
			</div>
			<div style="text-align: center;margin-top: 68px;"><a href="${util.path }" style="font-size: 10pt; color: green;">返回首页</a></div>
		</div>
	</div>
<script type="text/javascript">
	function openTaobao(){
		window.open("http://item.taobao.com/item.htm?spm=a1z10.3.w4002-5426925559.66.0mI7xm&id=37144133143");
	}
</script>
</body>
</html>