<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<div class="row">
    <c:url value="/login" var="loginUrl"/>
    <form class="form-signin" method="POST" action="${loginUrl}">
        <h2 class="form-signin-heading">Please login</h2>
        <c:if test="${param.error != null}">
            <span class="text-warning">
                Invalid username and password.
            </span>
        </c:if>
        <c:if test="${param.logout != null}">
            <span class="text-success">
                You have been logged out.
            </span>
        </c:if>

        <input type="text" class="form-control" name="username" placeholder="Email Address" required="" autofocus=""/>
        <input type="password" class="form-control" name="password" placeholder="Password" required=""/>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>

        <div style="padding-top:15px; font-size:85%">
            Don't have an account!
            <a href="registration">
                Sign Up Here
            </a>
        </div>
    </form>
</div>
<jsp:include page="footer.jsp"/>