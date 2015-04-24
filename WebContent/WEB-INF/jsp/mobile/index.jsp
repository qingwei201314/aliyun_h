<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <link href="${util.path}/css/mobile/mobile.css" rel="stylesheet" type="text/css"/>
    <title>过山鲫</title>
</head>
<body>
<div style="width: 100%;">
    <a href="${util.path }/userM/login.do" class="login_register">登录</a>
    <a href="${util.path }/indexM/register.do" class="login_register float_right">注册</a>
</div>
<form id="searchForm" action="${util.path }/index/search.do" method="get">
    <div class="logo_div">
        <a href="${util.path}/indexM/main.do"><img src="${util.path}/img/logo.png" style="border:0;width: 80%"/></a>
    </div>
    <div style="width: 100%;">
        <div class="search_div">
            <input type="text" name="q" class="search_div_input"/>
            <input type="button" onclick="searchSubmit();" class="search_div_button"/>
        </div>
    </div>
    <div style="text-align: center;margin-top: 40%;font-size: 0.5em;color: #777777">
        大数据企业黄页：你的电话号码就是您的企业网站！
    </div>
</form>

<div style="width: 100%;text-align:center;font-size: 8pt;color: #777777">
    <p>© 凯文信息 2014<a style="margin-left: 5px;color: #777777;text-decoration: none;" target="_blank"
                     href="http://www.miitbeian.gov.cn/">粤ICP备14009815号</a></p>
</div>

<script type="text/javascript">
    function searchSubmit() {
        document.getElementById("searchForm").submit();
    }
</script>
</body>
</html>