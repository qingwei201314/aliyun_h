<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="${util.path}/css/main.css" rel="stylesheet" type="text/css" />
	<title>过山鲫</title>
</head>
<body>
<div style="width: 100%;text-align: right;font-size: 10pt;">
	<a href="${util.path }/user/login.do">登录</a>
	<a href="${util.path }/index/register.do">注册</a>
</div>
<form id="searchForm" action="${util.path }/index/search.do" method="get">
	<div class="logo_div">
		<a href="${util.path}/index/main.do"><img src="${util.path}/img/logo.png" style="border:0;" /></a>
	</div>
	<div>
		<div class="search_div" >
			<input type="text" name="q" class="search_div_input" />
			<input type="button" onclick="searchSubmit();" class="search_div_button" />
		</div>
	</div>
	<div style="text-align: center;margin-top: 100px;font-size: 8pt;color: #777777">
		大数据企业网站：你的电话号码就是您的企业网站！
	</div>
</form>

<div style="width: 100%;text-align:center;font-size: 8pt;color: #777777">
	<p>© 凯文信息 2014<a style="margin-left: 5px;color: #777777;text-decoration: none;" target="_blank" href="http://www.miitbeian.gov.cn/">粤ICP备14009815号</a></p>
</div>

<script type="text/javascript">
	function searchSubmit(){
		document.getElementById("searchForm").submit();
	}
</script>
</body>
</html>