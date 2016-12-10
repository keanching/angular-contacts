<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@environment.getProperty('environment')" var="env" />

<c:if test="${env eq 'dev'}">
    <jsp:forward page="app.jsp" /> 
</c:if>

<c:if test="${env eq 'prd'}">
    <jsp:forward page="app-dist.jsp" /> 
</c:if>
