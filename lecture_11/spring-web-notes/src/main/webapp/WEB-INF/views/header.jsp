<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Simple Web Notebook</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/default.css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="header clearfix">
        <sec:authorize access="isAuthenticated()">
        <nav style="margin-top: 5px">
            <ul class="nav nav-pills pull-right">
                <li>
                    <a><sec:authentication property="principal.firstname"/>
                        <sec:authentication property="principal.lastname"/></a>
                </li>
                <li>
                    <c:url var="logoutUrl" value="/logout"/>
                    <form action="${logoutUrl}" id="logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                    </form>
                    <button class="btn btn-default" href="#" onclick="document.getElementById('logout').submit();">
                        Logout
                    </button>
                </li>
            </ul>
            </sec:authorize>
        </nav>

        <h3 class="text-muted">Notes</h3>
    </div>