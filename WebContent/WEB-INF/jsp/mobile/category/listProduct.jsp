<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <%@ include file="/commonjsp/head.jsp"%>
    <link href="${util.path}/css/mobile/shop/shop.css" rel="stylesheet"/>
    <script type="text/javascript" src="${util.path}/js/jquery-ias.js"></script>
    <title>产品列表</title>
</head>
<body>
<%@ include file="/commonjsp/mobile/topbar.jsp"%>

<div>
    <!-- Example row of columns -->
    <div class="row"  style="width: 100%;margin-left:0px;">
        <c:forEach var="productVo" items="${productVoList}">
            <div  style="width: 80%;margin: auto;">
                <h4 style="text-align:center;">${productVo.name}</h4>
                <p><a href="${util.path }/productM/viewProduct.do?productId=${productVo.id}&pageNo=1"><img
                        class="product" src="${util.statics}${util.repository}${productVo.path}${productVo.postfix}"
                        onError="this.src='${util.path}/img/product/none.jpg'"/></a></p>
            </div>
        </c:forEach>
    </div>

    <%@ include file="/commonjsp/mobile/footer.jsp"%>
</div>

<script type="text/javascript">
    $("#${categoryId}").attr("class", "active");
</script>
</body>
</html>