<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.usuario}">
<c:url value="login/login.jsp" var="url" />
<c:redirect url="${url}" />
</c:if>
<c:if test="${not empty sessionScope.usuario}">
<c:url value="controller.do?op=showct" var="url" />
<c:redirect url="${url}" />
</c:if>

