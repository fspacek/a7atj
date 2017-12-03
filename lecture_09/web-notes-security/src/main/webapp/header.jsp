<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Simple Web Notebook</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/default.css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="header clearfix">
        <c:if test="${loggedUser != null}">
            <nav>
                <ul class="nav nav-pills pull-right">
                    <li role="presentation">
                        <div><c:out value="${loggedUser.owner.firstname}"/>
                            <c:out value="${loggedUser.owner.lastname}"/></div>
                    </li>
                    <li role="presentation">
                        <form method="POST" action="login">
                            <input name="logout" value="" type="hidden">
                            <button class="btn btn-default" type="submit">Logout</button>
                        </form>
                    </li>
                </ul>
            </nav>
        </c:if>
        <h3 class="text-muted">Notes</h3>
    </div>