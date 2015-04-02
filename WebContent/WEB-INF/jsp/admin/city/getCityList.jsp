<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<option value="">请选择</option>
<c:forEach var="city" items="${cityList}">
	<option value="${city.id}">${city.name}</option>
</c:forEach>
