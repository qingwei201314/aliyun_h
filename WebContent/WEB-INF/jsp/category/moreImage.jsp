<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach var="imageVo" items="${imageDtoList}" end="${pageSize -2 }">
	<dd>
		<h4>${imageVo.name}</h4>
		<img onclick="showImage(this.src, '${productVo.name}')" class="product_detail" src="${util.statics }${util.repository }${imageVo.path }${imageVo.postfix }" />
	</dd>
</c:forEach>

<input type="hidden" name="lastId" value="lastId_start${lastId }lastId_end" />

<c:if test="${imageDtoList==null || imageDtoList.size() <=0}">
	<p id='theLast' style="text-align: center;">已经是最后一页</p>
</c:if>