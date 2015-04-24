<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <%@ include file="/commonjsp/head.jsp"%>
    <link href="${util.path}/css/mobile/shop/shop.css" rel="stylesheet"/>
    <title>首页</title>
</head>
<body>
<%@ include file="/commonjsp/mobile/topbar.jsp"%>
<div>
    <div class="index_home">
        <div class="left_div">
            <img class="gate_img" src="${util.statics}${util.repository}${shop.gateUrl}"/>
        </div>
        <div class="right_div">
            <dl style="margin-bottom: 0px;padding-bottom:0px;">
                <dd>
                    <table class="gate_right">
                        <tr>
                            <td class="gate_right_td_left">联系人：</td>
                            <td>${shop.contact}</td>
                        </tr>
                        <tr>
                            <td class="gate_right_td_left">电话：</td>
                            <td>${userPhone}</td>
                        </tr>
                        <tr>
                            <td class="gate_right_td_left">名称：</td>
                            <td>${shop.name}</td>
                        </tr>
                        <tr>
                            <td class="gate_right_td_left" >地址：</td>
                            <td>
                                ${provinceTownCity}${shop.address}
                                <c:if test="${map!=null&& map.longitude!=null }">
                                    <a href="${util.path }/shop/mapM/viewMap.do?shopId=${shop.id}">查看地图</a>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                </dd>
            </dl>
        </div>
    </div>

    <div class="row" style="width: 100%;margin-left:0px;">
        <c:forEach var="productVo" items="${page.list }" end="${page.pageSize -1 }">
            <div style="width: 80%;margin: auto;">
                <h4 style="text-align:center;">${productVo.name}</h4>
                <p>
                    <a href="${util.path }/productM/viewProduct.do?productId=${productVo.id}&pageNo=1">
                        <img class="product" src="${util.statics}${util.repository}${productVo.path}${productVo.postfix}" onError="this.src='${util.path}/img/product/none.jpg'" />
                    </a>
                </p>
            </div>
        </c:forEach>
    </div>

    <%@ include file="/commonjsp/mobile/footer.jsp"%>
</div>
</body>
</html>